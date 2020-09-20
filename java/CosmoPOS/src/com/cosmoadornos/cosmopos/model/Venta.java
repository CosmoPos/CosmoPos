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
public class Venta{
    
    Producto producto;
    TipoProducto tipoProducto;
    int subTipoId;
    float descuento;
    float cantidad;
    float subtotal;
    float vrTotalIVA;
    float totalVenta;
    int facturaId;
    int zDiario;

    public Venta() {
    }
    // CONSTRUCTOR PARA GUARDAR LAS VENTAS
    public Venta(Producto producto, float cantidad, float descuento, float subtotal, float vrTotalIVA, float totalVenta, int facturaId, int zDiario){
        this.producto = producto;
        this.cantidad = cantidad;
        this.descuento = cantidad;
        this.subtotal = subtotal;
        this.vrTotalIVA = vrTotalIVA;
        this.totalVenta = totalVenta;
        this.facturaId = facturaId;
        this.zDiario = zDiario;
    }
    
    /*
    public Venta(Producto producto, Linea linea, int subtotal, int vrTotalIVA, int facturaId, int zDiario) {
        this.producto = producto;
        this.linea = linea;
        this.subtotal = subtotal;
        this.vrTotalIVA = vrTotalIVA;
        this.facturaId = facturaId;
        this.zDiario = zDiario;
    }
    */

    public Venta(Producto producto, int subTipoId ,float cantidad, float descuento, float subtotal, float vrTotalIVA, float totalVenta, int facturaId, int zDiario){
        this.producto = producto;
        this.subTipoId = subTipoId;
        this.cantidad = cantidad;
        this.descuento = cantidad;
        this.subtotal = subtotal;
        this.vrTotalIVA = vrTotalIVA;
        this.totalVenta = totalVenta;
        this.facturaId = facturaId;
        this.zDiario = zDiario;
    }
    /*
    public Venta(Producto producto, Linea linea, float cantidad, int subtotal, int vrTotalIVA, int totalVenta, int facturaId){
        this.producto = producto;
        this.linea = linea;
        this.subtotal = subtotal;
        this.vrTotalIVA = vrTotalIVA;
        this.totalVenta = totalVenta;
        this.facturaId = facturaId;
    }
    */

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public TipoProducto getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(TipoProducto tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public float getDescuento() {
        return descuento;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public float getVrTotalIVA() {
        return vrTotalIVA;
    }

    public void setVrTotalIVA(float vrTotalIVA) {
        this.vrTotalIVA = vrTotalIVA;
    }

    public float getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(float totalVenta) {
        this.totalVenta = totalVenta;
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
