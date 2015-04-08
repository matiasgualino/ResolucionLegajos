package com.resoluciones.legajos.model;

import java.util.List;

/**
 * Created by resoluciones on 9/2/15.
 */
public class Legajo {

    private String numero;
    private String fecha;
    private String dominio;
    private String nombrecompleto;
    private String du;
    private String observaciones;
    private String id;
    private List<Acta> actas;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDominio() {
        return dominio;
    }

    public void setDominio(String dominio) {
        this.dominio = dominio;
    }

    public String getNombrecompleto() {
        return nombrecompleto;
    }

    public void setNombrecompleto(String nombrecompleto) {
        this.nombrecompleto = nombrecompleto;
    }

    public String getDU() {
        return du;
    }

    public void setDU(String DU) {
        this.du = DU;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public List<Acta> getActas() {
        return actas;
    }

    public void setActas(List<Acta> actas) {
        this.actas = actas;
    }

}
