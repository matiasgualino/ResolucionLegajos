package com.resoluciones.legajos.adapters.core;

import com.resoluciones.legajos.model.Acta;
import com.resoluciones.legajos.model.Infraccion;

/**
 * Created by resoluciones on 21/2/15.
 */
public class InfraccionInfo {

    private Acta acta;
    private Infraccion infraccion;

    public Acta getActa() {
        return acta;
    }

    public void setActa(Acta acta) {
        this.acta = acta;
    }

    public Infraccion getInfraccion() {
        return infraccion;
    }

    public void setInfraccion(Infraccion infraccion) {
        this.infraccion = infraccion;
    }
}
