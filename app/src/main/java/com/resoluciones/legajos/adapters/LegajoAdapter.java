package com.resoluciones.legajos.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.resoluciones.legajos.R;
import com.resoluciones.legajos.model.Legajo;

import java.util.List;

/**
 * Created by mgualino on 24/3/15.
 */
public class LegajoAdapter extends RecyclerView.Adapter<LegajoAdapter.LegajoViewHolder> {

    private List<Legajo> legajosList;

    public LegajoAdapter (List<Legajo> legajosList) {
        this.legajosList = legajosList;
    }

    @Override
    public int getItemCount() {
        return legajosList.size();
    }

    @Override
    public void onBindViewHolder(LegajoViewHolder legajoViewHolder, int i) {
        Legajo legajo = legajosList.get(i);
        legajoViewHolder.dominio.setText(legajo.getDominio());
        legajoViewHolder.fecha.setText(legajo.getFecha());
        legajoViewHolder.DU.setText(legajo.getDU());
        legajoViewHolder.selectButton.setTag(legajo.getId());
        legajoViewHolder.title.setText("Nro: " + legajo.getNumero() + " - " + legajo.getNombrecompleto());
    }

    @Override
    public LegajoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.legajo_card_view, viewGroup, false);
        return new LegajoViewHolder(itemView);
    }


    public static class LegajoViewHolder extends RecyclerView.ViewHolder {

        protected TextView title;
        protected TextView dominio;
        protected TextView fecha;
        protected TextView DU;
        protected TextView selectButton;

        public LegajoViewHolder(View v) {
            super(v);
            title =  (TextView) v.findViewById(R.id.legajo_cardTitle);
            dominio= (TextView)  v.findViewById(R.id.legajo_cardDominio);
            fecha = (TextView)  v.findViewById(R.id.legajo_cardFecha);
            DU = (TextView) v.findViewById(R.id.legajo_cardDU);
            selectButton = (TextView) v.findViewById(R.id.legajo_cardSelectButton);
        }
    }

}
