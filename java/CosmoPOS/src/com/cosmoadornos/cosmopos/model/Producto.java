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
    int compraId;
    String nombre;
    String referencia;
    int precio;
    float cantidad;
    int descuento;
    int total;  
    
    
    public Producto(){
    }
    
    /*
    public Producto(String nombre, int precio){
        this.nombre = nombre;
        this.precio = precio;
    }
    */
    
    public Producto(String nombre){
        this.nombre = nombre;
    }

    public Producto(String nombre, String referencia, int precio, float cantidad) {
        this.referencia = referencia;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }
    
    public Producto(String nombre, String referencia, String subTipo, float cantidad, int precio, int total) {
        this.nombre = nombre;
        this.referencia = referencia;
        this.subTipo = subTipo;
        this.cantidad = cantidad;
        this.precio = precio;
        this.total = total;
    }
    
    public Producto(int id, String nombre, String referencia, int precio, int cantidad) {
        this.id = id;
        this.referencia = referencia;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }
    
    public Producto(int id, String nombre){
        this.id = id;
        this.nombre = nombre;
    }
    
    public Producto(int precio, int cantidad, String nombre){
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }
    public Producto(String nombre, int precio, float cantidad){
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }
    
    
    public Producto(int id, String nombre, String subTipo, String referencia,  int precio){
        this.id = id;
        this.nombre = nombre;
        this.subTipo = subTipo;
        this.referencia = referencia;
        this.precio = precio;
    }
    
    
    public Producto(String referencia, String nombre, int precio){
        this.referencia = referencia;
        this.nombre = nombre;
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

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public int getDescuento() {
        return descuento;
    }

    public void setDescuento(int descuento) {
        this.descuento = descuento;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}
