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
public class Venta extends Producto{
    
    int productoId;
    int departamentoId;
    int subtotal;
    int vrIva;
    int facturaId;
    int zDiario;
    public Venta(){
        
    }
    
    public Venta(int productoId, int departamentoId, float cantidad, int descuento, int subtotal, int vrIva, int total, int facturaId, int zDiario){
        this.productoId = productoId;
        this.departamentoId = departamentoId;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
        this.vrIva = vrIva;
        this.total = total;
        this.facturaId = facturaId;
        this.zDiario = zDiario;
    }
    
    public Venta(int productoId, int departamentoId, float cantidad, int subtotal, int vrIva, int total, int facturaId){
        this.productoId = productoId;
        this.departamentoId = departamentoId;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
        this.vrIva = vrIva;
        this.total = total;
        this.facturaId = facturaId;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public int getDepartamentoId() {
        return departamentoId;
    }

    public void setDepartamentoId(int departamentoId) {
        this.departamentoId = departamentoId;
    }

    @Override
    public float getCantidad() {
        return cantidad;
    }

    @Override
    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public int getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }

    public int getVrIva() {
        return vrIva;
    }

    public void setVrIva(int vrIva) {
        this.vrIva = vrIva;
    }

    public int getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(int facturaId) {
        this.facturaId = facturaId;
    }

    public int getzDiario() {
        return zDiario;
    }

    public void setzDiario(int zDiario) {
        this.zDiario = zDiario;
    }
    
}
