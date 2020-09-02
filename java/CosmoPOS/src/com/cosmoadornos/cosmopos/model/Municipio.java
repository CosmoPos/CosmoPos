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
public class Municipio {
    
    int ciudadId;
    int codDepto;
    int codMuni;
    String nombre;
    String tipo;

    public Municipio(int ciudadId, int codDepto, int codMuni, String nombre, String tipo) {
        this.ciudadId = ciudadId;
        this.codDepto = codDepto;
        this.codMuni = codMuni;
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public int getCiudadId() {
        return ciudadId;
    }

    public void setCiudadId(int ciudadId) {
        this.ciudadId = ciudadId;
    }

    public int getCodDepto() {
        return codDepto;
    }

    public void setCodDepto(int codDepto) {
        this.codDepto = codDepto;
    }

    public int getCodMuni() {
        return codMuni;
    }

    public void setCodMuni(int codMuni) {
        this.codMuni = codMuni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
