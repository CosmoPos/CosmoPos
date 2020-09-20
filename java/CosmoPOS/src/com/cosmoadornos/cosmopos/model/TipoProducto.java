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
public class TipoProducto {
    
    int id;
    String medidaCod;
    String tipo;

    public TipoProducto() {
    }

    public TipoProducto(int id, String medidaCod, String tipo) {
        this.id = id;
        this.medidaCod = medidaCod;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMedidaCod() {
        return medidaCod;
    }

    public void setMedidaCod(String medidaCod) {
        this.medidaCod = medidaCod;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
}
