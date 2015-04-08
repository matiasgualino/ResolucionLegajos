package com.resoluciones.legajos.model;

import java.util.List;

/**
 * Created by resoluciones on 9/2/15.
 */
public class Infraccion implements Cloneable {

    private String codigo;
    private String descripcion;
    private List<Sancion> sanciones;
    private Resolucion resolucion;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Sancion> getSanciones() {
        return sanciones;
    }

    public void setSanciones(List<Sancion> sanciones) {
        this.sanciones = sanciones;
    }

    public Resolucion getResolucion() {
        return resolucion;
    }

    public void setResolucion(Resolucion resolucion) {
        this.resolucion = resolucion;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
