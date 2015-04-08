package com.resoluciones.legajos.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.resoluciones.legajos.R;
import com.resoluciones.legajos.core.ActaConsumer;
import com.resoluciones.legajos.model.Legajo;
import com.resoluciones.legajos.util.ApplicationHelper;
import com.resoluciones.legajos.util.JsonUtil;
import com.resoluciones.legajos.util.SaveSharedPreference;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends BaseActivity {

    Activity mActivity;
    CardView savedCardView;
    CardView obtainCardView;
    ActaConsumer consumer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = this;
        consumer = new ActaConsumer();

        TextView titleTextView = (TextView) findViewById(R.id.titleTextView);
        titleTextView.setText("Bienvenido " + SaveSharedPreference.getUsername(this));

        savedCardView = (CardView) mActivity.findViewById(R.id.cardViewContinue);
        obtainCardView = (CardView) mActivity.findViewById(R.id.cardViewObtain);
        Legajo legajo = SaveSharedPreference.getLegajo(this);
        if (legajo != null) {
            ApplicationHelper.i().setLegajo(legajo);
            TextView continueDescriptionTextView = (TextView) findViewById(R.id.continueDescriptionTextView);
            continueDescriptionTextView.setText(continueDescriptionTextView.getText().toString().replace("XXXXX", legajo.getNumero()));
            savedCardView.setVisibility(View.VISIBLE);
            obtainCardView.setVisibility(View.GONE);
        } else {
            savedCardView.setVisibility(View.GONE);
            obtainCardView .setVisibility(View.VISIBLE);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void eliminarLegajoActual(View view) {
        new MaterialDialog.Builder(mActivity)
                .title("ATENCION")
                .content("Se va a eliminar el legajo actual y todas las resoluciones hechas en él hasta el momento. Desea continuar?")
                .positiveText("SI")
                .negativeText("NO")
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        SaveSharedPreference.deleteLegajo(mActivity);
                        savedCardView.setVisibility(View.GONE);
                        obtainCardView .setVisibility(View.VISIBLE);
                    }
                })
                .show();
    }

    public void continuarLegajoActual(View view) {
        startActivity(new Intent(mActivity, LegajoActivity.class));
    }

    public void getLegajo(View view) {

        consumer.getAllLegajos(new Callback<List<Legajo>>() {
            @Override
            public void success(List<Legajo> legajos, Response response) {
                if (legajos.size() == 0) {
                    new MaterialDialog.Builder(mActivity)
                            .title("INFORMACIÓN")
                            .content("No hay legajos asignados.")
                            .positiveText("OK")
                            .show();
                } else if (legajos.size() == 1) {
                    consumer.getLegajo(legajos.get(0).getId(), new Callback<Legajo>() {
                        @Override
                        public void success(Legajo legajo, Response response) {
                            SaveSharedPreference.saveLegajo(mActivity, legajo);
                            ApplicationHelper.i().setLegajo(legajo);
                            startActivity(new Intent(mActivity, LegajoActivity.class));
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            new MaterialDialog.Builder(mActivity)
                                    .title("ERROR")
                                    .content(error.getMessage())
                                    .positiveText("OK")
                                    .show();
                        }
                    });
                } else {
                    String jsonLegajos = JsonUtil.getInstance().toJson(legajos);
                    Intent toLegajosListIntent = new Intent(mActivity, LegajosListActivity.class);
                    toLegajosListIntent.putExtra("legajos", jsonLegajos);
                    startActivity(toLegajosListIntent);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                new MaterialDialog.Builder(mActivity)
                        .title("ERROR")
                        .content(error.getMessage())
                        .positiveText("OK")
                        .show();
            }
        });
    }

    public void logout(View view) {
        SaveSharedPreference.logoutUser(this);
    }

}