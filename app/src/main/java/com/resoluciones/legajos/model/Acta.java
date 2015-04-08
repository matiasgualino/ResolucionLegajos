package com.resoluciones.legajos.model;

import java.util.List;

/**
 * Created by resoluciones on 9/2/15.
 */
public class Acta implements Cloneable {

    private String numero;
    private String descripcion;
    private Double ufcosto;
    private Double importeminimo;
    private String imagen;
    private List<Infraccion> infracciones;


    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getUfcosto() {
        return ufcosto;
    }

    public void setUfcosto(Double ufcosto) {
        this.ufcosto = ufcosto;
    }

    public Double getImporteminimo() {
        return importeminimo;
    }

    public void setImporteminimo(Double importeminimo) {
        this.importeminimo = importeminimo;
    }

    public List<Infraccion> getInfracciones() {
        return infracciones;
    }

    public void setInfracciones(List<Infraccion> infracciones) {
        this.infracciones = infracciones;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
