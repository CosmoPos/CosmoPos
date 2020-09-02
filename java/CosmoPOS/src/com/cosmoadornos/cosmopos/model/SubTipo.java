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
public class SubTipo {
    
    int subTipoId;
    int tipoProductoId;
    String tipo;
    
    public SubTipo(){
        
    }

    public SubTipo(int subTipoId, int tipoProductoId, String tipo) {
        this.subTipoId = subTipoId;
        this.tipoProductoId = tipoProductoId;
        this.tipo = tipo;
    }

    public int getSubTipoId() {
        return subTipoId;
    }

    public void setSubTipoId(int subTipoId) {
        this.subTipoId = subTipoId;
    }

    public int getTipoProductoId() {
        return tipoProductoId;
    }

    public void setTipoProductoId(int tipoProductoId) {
        this.tipoProductoId = tipoProductoId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
}
