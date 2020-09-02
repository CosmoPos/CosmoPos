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
public class Precio {
    
    int id;
    int subTipoId;
    String subTipo;
    String referencia;
    int precio;
    int productoId;
    String producto;
    
    public Precio(){
        
    }

    public Precio(int id, int subTipoId, int precio) {
        this.id = id;
        this.subTipoId = subTipoId;
        this.precio = precio;
    }
    
    public Precio(int subTipoId, int precio, String referencia, String producto){
        this.subTipoId = subTipoId;
        this.precio = precio;
        this.referencia = referencia;
        this.producto = producto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSubTipoId() {
        return subTipoId;
    }

    public void setSubTipoId(int subTipoId) {
        this.subTipoId = subTipoId;
    }

    public String getSubTipo() {
        return subTipo;
    }

    public void setSubTipo(String subTipo) {
        this.subTipo = subTipo;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }
    
}
