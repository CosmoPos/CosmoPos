/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmoadornos.cosmopos.model;

/**
 *
 * @author b41n
 */
public class Location {
    Departamento departamento;
    Municipio minucipio;
    CodigoPostal codigoPostal;

    public Location(Departamento departamento, Municipio minucipio, CodigoPostal codigoPostal) {
        this.departamento = departamento;
        this.minucipio = minucipio;
        this.codigoPostal = codigoPostal;
    }

    public Municipio getMinucipio() {
        return minucipio;
    }

    public void setMinucipio(Municipio minucipio) {
        this.minucipio = minucipio;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public CodigoPostal getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(CodigoPostal codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

}
