package com.resoluciones.legajos.model;

/**
 * Created by resoluciones on 9/2/15.
 */
public class Resolucion implements Cloneable {

    private String codigo;
    private Double uf;
    private Double importe;
    private String nota;
    private Integer puntos;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Double getUf() {
        return uf;
    }

    public void setUf(Double uf) {
        this.uf = uf;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public Integer getPuntos() {
        return puntos;
    }

    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
