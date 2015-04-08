package com.resoluciones.legajos.dialogs;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.resoluciones.legajos.R;
import com.resoluciones.legajos.model.Acta;
import com.resoluciones.legajos.model.Infraccion;
import com.resoluciones.legajos.model.Resolucion;
import com.resoluciones.legajos.model.Sancion;
import com.resoluciones.legajos.util.ApplicationHelper;
import com.resoluciones.legajos.util.SaveSharedPreference;

/**
 * Created by resoluciones on 22/2/15.
 */
public class ResolucionDialog {
    EditText resolucionNota;
    EditText resolucionPuntos;
    EditText resolucionValor;
    TextView resolucionPuntosLabel;
    TextView resolucionValorLabel;
    View positiveAction;

    Sancion currentSancion;
    Resolucion currentResolucion;
    boolean importeChecked = true;
    Acta currentActa;

    Context context;

    public interface ResolucionCallback{
        void callbackReturn();
    }

    ResolucionCallback resolucionCallback;

    public ResolucionDialog(Context context, ResolucionCallback callback) {
        this.context = context;
        resolucionCallback = callback;
    }

    public MaterialDialog.Builder getDialogForView(View view) {
        final Infraccion current = getInfraccionForView(view);

        CharSequence[] possiblesSolutions = new CharSequence[current.getSanciones().size()];
        int i = 0;
        for (Sancion s : current.getSanciones()) {
            possiblesSolutions[i] = s.getDescripcion();
            i++;
        }

        return new MaterialDialog.Builder(context)
                .title("Resoluciones posibles")
                .items(possiblesSolutions)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        dialog.dismiss();
                        currentSancion = current.getSanciones().get(which);
                        currentResolucion = current.getResolucion();
                        MaterialDialog sancionDialog = new MaterialDialog.Builder(context)
                                .title(currentSancion.getDescripcion() + " (" + currentSancion.getCodigo() + ")")
                                .customView(R.layout.dialog_resolucion, true)
                                .positiveText("ACEPTAR")
                                .negativeText(android.R.string.cancel)
                                .callback(new MaterialDialog.ButtonCallback() {
                                    @Override
                                    public void onPositive(MaterialDialog dialog) {

                                        try {
                                            Infraccion infraccionResuelta = (Infraccion) current.clone();
                                            Resolucion resolucion = new Resolucion();
                                            resolucion.setCodigo(currentSancion.getCodigo());
                                            resolucion.setNota(resolucionNota.getText().toString());

                                            if (resolucionPuntos.getVisibility() == View.GONE) {
                                                resolucion.setPuntos(0);
                                            } else {
                                                resolucion.setPuntos(Integer.valueOf(resolucionPuntos.getText().toString()));
                                            }

                                            if (importeChecked) {
                                                resolucion.setUf(0.0);
                                                resolucion.setImporte(Double.valueOf(resolucionValor.getText().toString()));
                                            } else {
                                                resolucion.setUf(Double.valueOf(resolucionValor.getText().toString()));
                                                resolucion.setImporte(resolucion.getUf() * currentActa.getUfcosto());
                                            }

                                            infraccionResuelta.setResolucion(resolucion);

                                            Acta actaNueva = (Acta) currentActa.clone();
                                            actaNueva.getInfracciones().remove(current);
                                            actaNueva.getInfracciones().add(infraccionResuelta);

                                            ApplicationHelper.i().getLegajo().getActas().remove(currentActa);
                                            ApplicationHelper.i().getLegajo().getActas().add(actaNueva);

                                            SaveSharedPreference.saveLegajo(context, ApplicationHelper.i().getLegajo());
                                            resolucionCallback.callbackReturn();
                                        } catch (Exception ex) {
                                            new MaterialDialog.Builder(context)
                                                    .title("ERROR")
                                                    .content("Ocurrio un error y no se pudo resolver la infracción. Por favor, vuelve a intentarlo nuevamente.")
                                                    .positiveText("OK")
                                                    .show();
                                        }
                                    }

                                    @Override
                                    public void onNegative(MaterialDialog dialog) {
                                    }
                                }).build();

                        positiveAction = sancionDialog.getActionButton(DialogAction.POSITIVE);

                        resolucionNota = (EditText) sancionDialog.getCustomView().findViewById(R.id.resolucion_nota);
                        resolucionPuntos = (EditText) sancionDialog.getCustomView().findViewById(R.id.resolucion_puntos);
                        resolucionPuntos.addTextChangedListener(new TextWatcher() {

                            public void afterTextChanged(Editable s) {
                                if (resolucionPuntos.getText().toString().isEmpty()) {
                                    positiveAction.setEnabled(false);
                                } else {
                                    Integer puntos = Integer.valueOf(resolucionPuntos.getText().toString());
                                    if (puntos < currentSancion.getPuntosmin() || puntos > currentSancion.getPuntosmax()) {
                                        positiveAction.setEnabled(false);
                                    } else {
                                        positiveAction.setEnabled(true);
                                    }
                                }
                            }

                            public void beforeTextChanged(CharSequence s, int start,
                                                          int count, int after) {
                            }

                            public void onTextChanged(CharSequence s, int start,
                                                      int before, int count) {

                            }
                        });

                        resolucionPuntosLabel = (TextView) sancionDialog.getCustomView().findViewById(R.id.resolucion_puntos_label);

                        resolucionValor = (EditText) sancionDialog.getCustomView().findViewById(R.id.resolucion_value);
                        resolucionValor.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if (!importeChecked) {
                                    if (resolucionValor.getText().toString().isEmpty()) {
                                        positiveAction.setEnabled(false);
                                    } else {
                                        Double uf = Double.valueOf(resolucionValor.getText().toString());
                                        if (uf < currentSancion.getUfmin() || uf > currentSancion.getUfmax()) {
                                            positiveAction.setEnabled(false);
                                        } else {
                                            positiveAction.setEnabled(true);
                                        }
                                    }
                                } else {
                                    if (resolucionValor.getText().toString().isEmpty()) {
                                        positiveAction.setEnabled(false);
                                    } else {
                                        positiveAction.setEnabled(true);
                                    }
                                }
                            }
                        });

                        resolucionValorLabel = (TextView) sancionDialog.getCustomView().findViewById(R.id.resolucion_value_label);

                        if (currentSancion.getPuntosmax() <= 0) {
                            resolucionPuntosLabel.setVisibility(View.GONE);
                            resolucionPuntos.setVisibility(View.GONE);
                        } else {
                            resolucionPuntosLabel.setVisibility(View.VISIBLE);
                            resolucionPuntosLabel.setText("Puntos (" + currentSancion.getPuntosmin() + " - " + currentSancion.getPuntosmax() + ")");
                            resolucionPuntos.setText("" + currentSancion.getPuntosmin());
                            resolucionPuntos.setVisibility(View.VISIBLE);
                        }
                        resolucionPuntos.setText("0");
                        resolucionValor.setText("0");
                        positiveAction.setEnabled(true);

                        RadioButton rbI = (RadioButton) sancionDialog.getCustomView().findViewById(R.id.radio_importe);
                        rbI.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onRadioButtonClicked(v);
                            }
                        });

                        RadioButton rbU = (RadioButton) sancionDialog.getCustomView().findViewById(R.id.radio_uf);
                        rbU.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onRadioButtonClicked(v);
                            }
                        });

                        if (currentResolucion != null) {
                            resolucionPuntos.setText(currentResolucion.getPuntos() + "");
                            if (currentResolucion.getUf() == 0) {
                                if (currentResolucion.getImporte() == 0) {
                                    resolucionValor.setText("0");
                                } else {
                                    resolucionValor.setText(currentResolucion.getImporte() + "");
                                }
                            } else {
                                resolucionValor.setText(currentResolucion.getUf() + "");
                            }
                            resolucionNota.setText(currentResolucion.getNota());
                        }

                        sancionDialog.show();
                    }
                })
                .positiveText("SELECCIONAR");
    }

    public Acta getActaForTag(String tag) {
        try {
            String[] actaInfraccion = tag.split("-XXX-");
            for (Acta a : ApplicationHelper.i().getLegajo().getActas()) {
                if (a.getNumero().equals(actaInfraccion[0])) {
                    return a;
                }
            }
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    public Infraccion getInfraccionForTag(String tag) {
        try {
            String[] actaInfraccion = tag.split("-XXX-");
            for (Acta a : ApplicationHelper.i().getLegajo().getActas()) {
                if (a.getNumero().equals(actaInfraccion[0])) {
                    for (Infraccion i : a.getInfracciones()) {
                        if (i.getCodigo().equals(actaInfraccion[1])) {
                            return i;
                        }
                    }
                }
            }
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    public Infraccion getInfraccionForView(View view) {
        Infraccion current = null;
        try {
            String[] actaInfraccion = view.getTag().toString().split("-XXX-");
            for (Acta a : ApplicationHelper.i().getLegajo().getActas()) {
                if (a.getNumero().equals(actaInfraccion[0])) {
                    for (Infraccion i : a.getInfracciones()) {
                        if (i.getCodigo().equals(actaInfraccion[1])) {
                            currentActa = a;
                            current = i;
                        }
                    }
                }
            }
            return current;
        } catch (Exception ex) {
            return null;
        }
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.radio_importe:
                if (checked) {
                    resolucionValorLabel.setText("Importe ($)");
                    importeChecked = true;
                }
                break;
            case R.id.radio_uf:
                if (checked) {
                    resolucionValorLabel.setText("Unidades fijas (" + currentSancion.getUfmin() + " - " + currentSancion.getUfmax() + ")");
                    resolucionValor.setText(""+currentSancion.getUfmin());
                    importeChecked = false;
                }
                break;
        }
    }
}
