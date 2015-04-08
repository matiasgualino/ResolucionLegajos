package com.resoluciones.legajos.model;

/**
 * Created by resoluciones on 9/2/15.
 */
public class Sancion implements Cloneable {

    private String codigo;
    private String descripcion;
    private Double ufmin;
    private Double ufmax;
    private Integer puntosmin;
    private Integer puntosmax;

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

    public Double getUfmin() {
        return ufmin;
    }

    public void setUfmin(Double ufmin) {
        this.ufmin = ufmin;
    }

    public Double getUfmax() {
        return ufmax;
    }

    public void setUfmax(Double ufmax) {
        this.ufmax = ufmax;
    }

    public Integer getPuntosmin() {
        return puntosmin;
    }

    public void setPuntosmin(Integer puntosmin) {
        this.puntosmin = puntosmin;
    }

    public Integer getPuntosmax() {
        return puntosmax;
    }

    public void setPuntosmax(Integer puntosmax) {
        this.puntosmax = puntosmax;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
