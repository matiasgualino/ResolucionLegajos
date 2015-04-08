package com.resoluciones.legajos.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.resoluciones.legajos.R;
import com.resoluciones.legajos.adapters.core.InfraccionInfo;

import java.util.List;

/**
 * Created by resoluciones on 21/2/15.
 */
public class InfraccionAdapter extends RecyclerView.Adapter<InfraccionAdapter.InfraccionViewHolder> {

    private List<InfraccionInfo> infraccionList;

    public InfraccionAdapter(List<InfraccionInfo> infraccionList) {
        this.infraccionList = infraccionList;
    }

    @Override
    public int getItemCount() {
        return infraccionList.size();
    }

    @Override
    public void onBindViewHolder(InfraccionViewHolder infraccionViewHolder, int i) {
        InfraccionInfo ii = infraccionList.get(i);
        infraccionViewHolder.codigoInfraccion.setText(ii.getInfraccion().getCodigo());
        infraccionViewHolder.actaInfraccion.setText(ii.getActa().getNumero() + " - " + ii.getActa().getDescripcion());
        infraccionViewHolder.infraccionImporteMinimo.setText("" + ii.getActa().getImporteminimo());
        infraccionViewHolder.infraccionUfCosto.setText("" + ii.getActa().getUfcosto());
        infraccionViewHolder.infraccionTitulo.setText(ii.getInfraccion().getDescripcion());
        infraccionViewHolder.viewButton.setTag(ii.getActa().getNumero() + "-XXX-" + ii.getInfraccion().getCodigo());
        infraccionViewHolder.solveButton.setTag(ii.getActa().getNumero() + "-XXX-" + ii.getInfraccion().getCodigo());

        if (ii.getInfraccion().getResolucion() != null) {
            infraccionViewHolder.infraccionTitulo.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.solve, 0);
            //infraccionViewHolder.infraccionLayout.setBackgroundColor(infraccionViewHolder.itemView.getResources().getColor(R.color.step_pager_current_solve));
        } else {
            //infraccionViewHolder.infraccionLayout.setBackgroundColor(infraccionViewHolder.itemView.getResources().getColor(R.color.step_pager_current_unsolve));
            infraccionViewHolder.infraccionTitulo.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.unsolve, 0);
        }
    }

    @Override
    public InfraccionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.infraccion_card_view, viewGroup, false);
        return new InfraccionViewHolder(itemView);
    }


    public static class InfraccionViewHolder extends RecyclerView.ViewHolder {

        protected TextView codigoInfraccion;
        protected TextView actaInfraccion;
        protected TextView infraccionImporteMinimo;
        protected TextView infraccionUfCosto;
        protected TextView infraccionTitulo;
        protected TextView viewButton;
        protected TextView solveButton;

        public InfraccionViewHolder(View v) {
            super(v);
            codigoInfraccion =  (TextView) v.findViewById(R.id.codigo_infraccion);
            actaInfraccion = (TextView)  v.findViewById(R.id.acta_infraccion);
            infraccionImporteMinimo = (TextView)  v.findViewById(R.id.infraccion_importe_minimo);
            infraccionUfCosto = (TextView) v.findViewById(R.id.infraccion_uf_costo);
            infraccionTitulo = (TextView) v.findViewById(R.id.infraccion_titulo);
            viewButton = (TextView) v.findViewById(R.id.view_button);
            solveButton = (TextView) v.findViewById(R.id.solve_button);
        }
    }

}
