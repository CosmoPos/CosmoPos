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
    String direccion;

    public Location(Departamento departamento, Municipio minucipio, CodigoPostal codigoPostal, String direccion) {
        this.departamento = departamento;
        this.minucipio = minucipio;
        this.codigoPostal = codigoPostal;
        this.direccion = direccion;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

}
