package com.resoluciones.legajos.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.ThemeSingleton;
import com.resoluciones.legajos.R;
import com.resoluciones.legajos.adapters.InfraccionAdapter;
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

public class LegajoActivity extends BaseActivity implements ResolucionDialog.ResolucionCallback {

    private Activity mActivity;
    private int cantidadInfracciones = 0;
    RecyclerView recList;
    InfraccionAdapter ia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legajo);

        mActivity = this;

        ThemeSingleton.get().positiveColor = ThemeSingleton.get().negativeColor = ThemeSingleton.get().neutralColor = R.color.colorPrimary;

        recList = (RecyclerView) findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        loadLegajo();

    }

    void loadLegajo() {
        TextView legajoTitle = (TextView) findViewById(R.id.legajoTitle);
        legajoTitle.setText("Nro: " + ApplicationHelper.i().getLegajo().getNumero() + " - " + ApplicationHelper.i().getLegajo().getNombrecompleto());
        TextView legajoDominio = (TextView) findViewById(R.id.legajo_dominio);
        legajoDominio.setText(ApplicationHelper.i().getLegajo().getDominio());
        TextView legajoFecha = (TextView) findViewById(R.id.legajo_fecha);
        legajoFecha.setText(ApplicationHelper.i().getLegajo().getFecha());
        TextView cantActas = (TextView) findViewById(R.id.cant_actas);
        cantActas.setText("" + ApplicationHelper.i().getLegajo().getActas().size());

        setCantidadInfracciones();
        setPuntosResueltos();
        setImporteResuelto();
        setInfraccionesResueltas();

        ia = new InfraccionAdapter(createInfraccionList());
        recList.setAdapter(ia);
    }

    void setCantidadInfracciones() {
        TextView cantInfracciones = (TextView) findViewById(R.id.cant_infracciones);

        int cantidadInfracciones  = 0;
        for (Acta a : ApplicationHelper.i().getLegajo().getActas()) {
            cantidadInfracciones  += a.getInfracciones().size();
        }
        this.cantidadInfracciones = cantidadInfracciones;
        cantInfracciones.setText("" + cantidadInfracciones );

    }

    void setPuntosResueltos() {
        TextView puntosResueltos = (TextView) findViewById(R.id.puntos_resueltos);

        int puntos = 0;
        for (Acta a : ApplicationHelper.i().getLegajo().getActas()) {
            for (Infraccion i : a.getInfracciones()) {
                if (i.getResolucion() != null) {
                    puntos += i.getResolucion().getPuntos();
                }
            }
        }

        puntosResueltos.setText("" + puntos);
        if (puntos >= 10) {
            puntosResueltos.setText("10");
            puntosResueltos.setTextColor(getResources().getColor(R.color.step_pager_current_unsolve));
        }

    }

    void setImporteResuelto() {
        TextView importeResuelto = (TextView) findViewById(R.id.importe_resuelto);

        Double importe = 0.0;
        for (Acta a : ApplicationHelper.i().getLegajo().getActas()) {
            for (Infraccion i : a.getInfracciones()) {
                if (i.getResolucion() != null) {
                    importe += i.getResolucion().getImporte();
                }
            }
        }

        importeResuelto.setText("" + importe);

    }

    void setInfraccionesResueltas() {
        TextView infraccionesResueltas = (TextView) findViewById(R.id.infracciones_resueltas);
        TextView finishButton = (TextView) findViewById(R.id.finish_button);

        int cantResueltas = 0;
        for (Acta a : ApplicationHelper.i().getLegajo().getActas()) {
            for (Infraccion i : a.getInfracciones()) {
                if (i.getResolucion() != null) {
                    cantResueltas++;
                }
            }
        }

        finishButton.setTextColor(getResources().getColor(R.color.colorPrimary));

        infraccionesResueltas.setText("" + cantResueltas);
        if (cantResueltas == cantidadInfracciones ) {
            infraccionesResueltas.setTextColor(getResources().getColor(R.color.step_pager_current_solve));
            finishButton.setText("FINALIZAR");
            finishButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finalizarLegajo(v);
                }
            });
        } else {
            infraccionesResueltas.setTextColor(getResources().getColor(R.color.step_pager_current_unsolve));
            finishButton.setText("SUSPENDER");
            finishButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    suspenderLegajo(v);
                }
            });
        }

    }

    public List<InfraccionInfo> createInfraccionList() {
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
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void infraccionView(View view) {
        Intent intent = new Intent(this, ActaActivity.class);
        intent.putExtra("TAG", view.getTag().toString());
        startActivity(intent);
    }

    public void solveView(View view) {
        new ResolucionDialog(mActivity, this).getDialogForView(view).show();
    }

    public void caratula(View view) {
        startActivity(new Intent(this, CaratulaActivity.class));
    }

    public void finalizarLegajo(View view) {
        startActivity(new Intent(this, FinishActivity.class));
    }

    public void suspenderLegajo(View view) {
        new ActaConsumer().suspenderResolucion(ApplicationHelper.i().getLegajo(), new Callback<String>() {
            @Override
            public void success(String legajo, Response response) {
                new MaterialDialog.Builder(mActivity)
                        .title("INFORMACIÓN")
                        .content("Se ha suspendido el legajo exitosamente! Será redireccionado al inicio.")
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

    @Override
    public void callbackReturn() {
        finish();
        startActivity(getIntent());
    }
}
