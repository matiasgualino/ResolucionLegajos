package com.resoluciones.legajos.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.resoluciones.legajos.R;
import com.resoluciones.legajos.model.Acta;
import com.resoluciones.legajos.model.Infraccion;
import com.resoluciones.legajos.util.ApplicationHelper;

/**
 * Created by resoluciones on 16/2/15.
 */
public class CardBackFragment extends Fragment {
    public CardBackFragment() {
    }

    Acta acta;
    Infraccion infraccion;

    public void setActa(Acta acta) {
        this.acta = acta;
    }

    public void setInfraccion(Infraccion infraccion) {
        this.infraccion = infraccion;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_back, container, false);

        // Load legajo metadata
        TextView legajoNombreCompleto = (TextView) view.findViewById(R.id.card_back_legajo_nombre_completo);
        legajoNombreCompleto.setText(ApplicationHelper.i().getLegajo().getNombrecompleto());
        TextView legajoNumero= (TextView) view.findViewById(R.id.card_back_legajo_numero);
        legajoNumero.setText(ApplicationHelper.i().getLegajo().getNumero());
        TextView legajoDominio= (TextView) view.findViewById(R.id.card_back_legajo_dominio);
        legajoDominio.setText(ApplicationHelper.i().getLegajo().getDominio());
        TextView legajoFecha = (TextView) view.findViewById(R.id.card_back_legajo_fecha);
        legajoFecha.setText(ApplicationHelper.i().getLegajo().getFecha());
        TextView legajoDU = (TextView) view.findViewById(R.id.card_back_legajo_du);
        legajoDU.setText(ApplicationHelper.i().getLegajo().getDU());

        // Load Acta metadata
        TextView actaNumero = (TextView) view.findViewById(R.id.card_back_acta_numero);
        actaNumero.setText(acta.getNumero());
        TextView actaDescripcion = (TextView) view.findViewById(R.id.card_back_acta_descripcion);
        actaDescripcion.setText(acta.getDescripcion());
        TextView actaImporteMinimo = (TextView) view.findViewById(R.id.card_back_acta_importe_minimo);
        actaImporteMinimo.setText("" + acta.getImporteminimo());

        // Load Infraccion metadata
        TextView infraccionDescripcion = (TextView) view.findViewById(R.id.card_back_infraccion_descripcion);
        infraccionDescripcion.setText(infraccion.getDescripcion());
        TextView infraccionCodigo = (TextView) view.findViewById(R.id.card_back_infraccion_codigo);
        infraccionCodigo.setText(infraccion.getCodigo());

        return view;
    }

}