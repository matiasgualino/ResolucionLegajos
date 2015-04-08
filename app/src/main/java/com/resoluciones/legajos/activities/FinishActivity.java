package com.resoluciones.legajos.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.ThemeSingleton;
import com.resoluciones.legajos.R;
import com.resoluciones.legajos.adapters.ResumenAdapter;
import com.resoluciones.legajos.adapters.core.InfraccionInfo;
import com.resoluciones.legajos.core.ActaConsumer;
import com.resoluciones.legajos.dialogs.ResolucionDialog;
import com.resoluciones.legajos.model.Acta;
import com.resoluciones.legajos.model.Infraccion;
import com.resoluciones.legajos.util.ApplicationHelper;
import com.resoluciones.legajos.util.SaveSharedPreference;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class FinishActivity extends ActionBarActivity implements ResolucionDialog.ResolucionCallback {

    private Activity mActivity;
    RecyclerView recList;
    ResumenAdapter ra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
        mActivity = this;

        ThemeSingleton.get().positiveColor = ThemeSingleton.get().negativeColor = ThemeSingleton.get().neutralColor = R.color.colorPrimary;

        recList = (RecyclerView) findViewById(R.id.resumen_cardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        loadResumen();
    }

    public void loadResumen() {
        Double importeRes = 0.0;
        Integer puntosRes = 0;
        Double ufRes = 0.0;
        for (Acta a : ApplicationHelper.i().getLegajo().getActas()) {
            for (Infraccion i : a.getInfracciones()) {
                importeRes += i.getResolucion().getImporte();
                puntosRes += i.getResolucion().getPuntos();
                ufRes += i.getResolucion().getUf();
            }
        }

        TextView legajoTitle = (TextView) findViewById(R.id.resumen_legajoTitle);
        legajoTitle.setText("Nro: " + ApplicationHelper.i().getLegajo().getNumero() + " - " + ApplicationHelper.i().getLegajo().getNombrecompleto());
        TextView puntosResueltos = (TextView) findViewById(R.id.resumen_puntos_resueltos);
        if (puntosRes >= 10) {
            puntosRes = 10;
            puntosResueltos.setTextColor(getResources().getColor(R.color.step_pager_current_unsolve));
        } else {
            puntosResueltos.setTextColor(getResources().getColor(R.color.white));
        }
        puntosResueltos.setText("" + puntosRes);
        TextView importeResuelto = (TextView) findViewById(R.id.resumen_importe_resuelto);
        importeResuelto.setText("" + importeRes);
        TextView ufResueltas = (TextView) findViewById(R.id.resumen_uf_resueltas);
        ufResueltas.setText("" + ufRes);

        ra = new ResumenAdapter(createResumenList());
        recList.setAdapter(ra);
    }

    public List<InfraccionInfo> createResumenList() {
        List<InfraccionInfo> list = new ArrayList<InfraccionInfo>();
        for (Acta a : ApplicationHelper.i().getLegajo().getActas()) {
            for (Infraccion infraccion : a.getInfracciones()) {
                InfraccionInfo i = new InfraccionInfo();
                i.setActa(a);
                i.setInfraccion(infraccion);
                list.add(i);
            }
        }
        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void modificarResolucion(View view) {
        new ResolucionDialog(mActivity, this).getDialogForView(view).show();
    }

    @Override
    public void callbackReturn() {
        finish();
        startActivity(getIntent());
    }

    public void confirmarLegajo(View view) {
        new ActaConsumer().finalizarResolucion(ApplicationHelper.i().getLegajo(), new Callback<String>() {
                    @Override
                    public void success(String legajo, Response response) {
                        new MaterialDialog.Builder(mActivity)
                                .title("FELICITACIONES")
                                .content("Se ha cerrado el legajo exitosamente! Será redireccionado al inicio.")
                                .positiveText("OK")
                                .callback(new MaterialDialog.ButtonCallback() {
                                    @Override
                                    public void onPositive(MaterialDialog dialog) {
                                        SaveSharedPreference.deleteLegajo(mActivity);
                                        mActivity.startActivity(new Intent(mActivity, MainActivity.class));
                                        mActivity.finish();
                                    }
                                })
                                .show();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        new MaterialDialog.Builder(mActivity)
                                .title("ERROR")
                                .content("Ha ocurrido un error, por favor vuelve a intentarlo nuevamente.")
                                .positiveText("OK")
                                .show();
                    }
                });
    }

}
