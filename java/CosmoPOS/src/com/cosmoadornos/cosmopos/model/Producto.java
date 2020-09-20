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
public class Producto {
    
    int id;
    String subTipo;
    int subTipoId;
    int compraId;
    String nombre;
    String referencia;
    int descuento;
    float precio;
    float productoIVA;
    float total;
    
    public Producto(String nombre){
        this.nombre = nombre;
    }

    public Producto(int id,String nombre, String referencia, String subTipo, int subTipoId, float precio, float productoIVA, float total) {
        this.id = id;
        this.nombre = nombre;
        this.referencia = referencia;
        this.subTipo = subTipo;
        this.subTipoId = subTipoId;
        this.precio = precio;
        this.productoIVA = productoIVA;
        this.total = total;
    }


    
    public Producto(String nombre, String referencia, float precio) {
        this.referencia = referencia;
        this.nombre = nombre;
        this.precio = precio;
    }
    
    public Producto(int id,String nombre, String referencia, String subTipo, float precio, float productoIVA, float total) {
        this.id = id;
        this.nombre = nombre;
        this.referencia = referencia;
        this.subTipo = subTipo;
        this.precio = precio;
        this.total = total;
    }
    
    public Producto(int id, String nombre, String referencia, float precio) {
        this.id = id;
        this.referencia = referencia;
        this.nombre = nombre;
        this.precio = precio;
    }
    
    public Producto(int id, String nombre){
        this.id = id;
        this.nombre = nombre;
    }
    
    
    public Producto(String nombre, float precio){
        this.nombre = nombre;
        this.precio = precio;
    }
    
    // CONSTRUCTOR PARA LA LISTA DETALLADA
    public Producto(int id, String nombre, String subTipo, int subTipoId, String referencia,  float precio){
        this.id = id;
        this.nombre = nombre;
        this.subTipo = subTipo;
        this.subTipoId = subTipoId;
        this.referencia = referencia;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubTipo() {
        return subTipo;
    }

    public void setSubTipo(String subTipo) {
        this.subTipo = subTipo;
    }

    public int getSubTipoId() {
        return subTipoId;
    }

    public void setSubTipoId(int subTipoId) {
        this.subTipoId = subTipoId;
    }

    public int getCompraId() {
        return compraId;
    }

    public void setCompraId(int compraId) {
        this.compraId = compraId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public float getProductoIVA() {
        return productoIVA;
    }

    public void setProductoIVA(float productoIVA) {
        this.productoIVA = productoIVA;
    }

    public int getDescuento() {
        return descuento;
    }

    public void setDescuento(int descuento) {
        this.descuento = descuento;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

}
