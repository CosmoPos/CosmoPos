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
public class CodigoPostal {
    int id;
    int municipioCod;
    int codigo;

    public CodigoPostal(int id, int municipioCod, int codigo) {
        this.id = id;
        this.municipioCod = municipioCod;
        this.codigo = codigo;
    }

    public CodigoPostal(int municipioCod, int codigo) {
        this.municipioCod = municipioCod;
        this.codigo = codigo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMunicipioCod() {
        return municipioCod;
    }

    public void setMunicipioCod(int municipioCod) {
        this.municipioCod = municipioCod;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

}
