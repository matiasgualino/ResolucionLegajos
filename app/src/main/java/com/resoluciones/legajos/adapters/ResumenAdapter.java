package com.resoluciones.legajos.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.resoluciones.legajos.R;
import com.resoluciones.legajos.adapters.core.InfraccionInfo;
import com.resoluciones.legajos.model.Sancion;

import java.util.List;

/**
 * Created by resoluciones on 26/2/15.
 */
public class ResumenAdapter extends RecyclerView.Adapter<ResumenAdapter.ResumenViewHolder> {

    private List<InfraccionInfo> infraccionList;
    View itemView;
    public ResumenAdapter(List<InfraccionInfo> infraccionList) {
        this.infraccionList = infraccionList;
    }

    @Override
    public int getItemCount() {
        return infraccionList.size();
    }

    public static class ResumenViewHolder extends RecyclerView.ViewHolder {

        protected TextView resumenInfraccionTituloCodigo;
        protected TextView resumenActaInfraccion;
        protected TextView resumenCodigoResolucion;
        protected TextView resumenResolucionNota;
        protected TextView resumenResolucionPuntos;
        protected TextView resumenResolucionPuntosTitle;
        protected TextView resumenResolucionUf;
        protected TextView resumenResolucionUfTitle;
        protected TextView resumenResolucionImporte;
        protected TextView modifyButton;

        public ResumenViewHolder(View v) {
            super(v);
            resumenInfraccionTituloCodigo =  (TextView) v.findViewById(R.id.resumen_infraccion_titulo_codigo);
            resumenActaInfraccion =  (TextView) v.findViewById(R.id.resumen_acta_infraccion);
            resumenCodigoResolucion =  (TextView) v.findViewById(R.id.resumen_codigo_resolucion);
            resumenResolucionNota =  (TextView) v.findViewById(R.id.resumen_resolucion_nota);
            resumenResolucionPuntos =  (TextView) v.findViewById(R.id.resumen_resolucion_puntos);
            resumenResolucionPuntosTitle =  (TextView) v.findViewById(R.id.resumen_resolucion_puntos_title);
            resumenResolucionUf =  (TextView) v.findViewById(R.id.resumen_resolucion_uf);
            resumenResolucionUfTitle =  (TextView) v.findViewById(R.id.resumen_resolucion_uf_title);
            resumenResolucionImporte =  (TextView) v.findViewById(R.id.resumen_resolucion_importe);
            modifyButton =  (TextView) v.findViewById(R.id.modify_button);
        }
    }

    @Override
    public void onBindViewHolder(ResumenViewHolder resumenViewHolder, int i) {
        InfraccionInfo ii = infraccionList.get(i);
        resumenViewHolder.resumenInfraccionTituloCodigo.setText(ii.getInfraccion().getDescripcion() + " - " + ii.getInfraccion().getCodigo());
        resumenViewHolder.resumenActaInfraccion.setText(ii.getActa().getNumero() + " - " + ii.getActa().getDescripcion());
        String resolucionNombre = "";
        for (Sancion s : ii.getInfraccion().getSanciones()) {
            if (s.getCodigo().equals(ii.getInfraccion().getResolucion().getCodigo())) {
                resolucionNombre = s.getDescripcion();
                break;
            }
        }
        resumenViewHolder.resumenCodigoResolucion.setText(resolucionNombre + " (" + ii.getInfraccion().getResolucion().getCodigo() + ")");
        resumenViewHolder.resumenResolucionNota.setText(ii.getInfraccion().getResolucion().getNota());
        if (ii.getInfraccion().getResolucion().getPuntos() == 0) {
            resumenViewHolder.resumenResolucionPuntos.setVisibility(View.GONE);
            resumenViewHolder.resumenResolucionPuntosTitle.setVisibility(View.GONE);
        } else {
            resumenViewHolder.resumenResolucionPuntos.setVisibility(View.VISIBLE);
            resumenViewHolder.resumenResolucionPuntosTitle.setVisibility(View.VISIBLE);
            if (ii.getInfraccion().getResolucion().getPuntos() >= 10) {
                resumenViewHolder.resumenResolucionPuntos.setTextColor(itemView.getResources().getColor(R.color.step_pager_current_unsolve));
            } else {
                resumenViewHolder.resumenResolucionPuntos.setTextColor(itemView.getResources().getColor(R.color.black));
            }
            resumenViewHolder.resumenResolucionPuntos.setText(ii.getInfraccion().getResolucion().getPuntos() + "");
        }
        if (ii.getInfraccion().getResolucion().getUf() > 0) {
            resumenViewHolder.resumenResolucionUfTitle.setText("Unidades fijas (" + ii.getActa().getUfcosto() + "):");
            resumenViewHolder.resumenResolucionUf.setText(ii.getInfraccion().getResolucion().getUf() + "");
            resumenViewHolder.resumenResolucionUfTitle.setVisibility(View.VISIBLE);
            resumenViewHolder.resumenResolucionUf.setVisibility(View.VISIBLE);
        } else {
            resumenViewHolder.resumenResolucionUfTitle.setVisibility(View.GONE);
            resumenViewHolder.resumenResolucionUf.setVisibility(View.GONE);
        }
        resumenViewHolder.resumenResolucionImporte.setText(ii.getInfraccion().getResolucion().getImporte() + "");
        resumenViewHolder.modifyButton.setTag(ii.getActa().getNumero() + "-XXX-" + ii.getInfraccion().getCodigo());
    }

    @Override
    public ResumenViewHolder  onCreateViewHolder(ViewGroup viewGroup, int i) {
        itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.resumen_card_view, viewGroup, false);
        return new ResumenViewHolder (itemView);
    }

}
