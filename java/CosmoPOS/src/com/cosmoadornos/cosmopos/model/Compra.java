/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmoadornos.cosmopos.model;

import java.util.Date;

/**
 *
 * @author b41n
 */
public class Compra {
    
    int id;
    int proveedorId;
    int facturaNo;
    Date fechaC;
    Date fechaV;
    
    public Compra(){
        
    }
    
    public Compra(int proveedorId, int facturaNo) {
        this.proveedorId = proveedorId;
        this.facturaNo = facturaNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(int proveedorId) {
        this.proveedorId = proveedorId;
    }

    public int getFacturaNo() {
        return facturaNo;
    }

    public void setFacturaNo(int facturaNo) {
        this.facturaNo = facturaNo;
    }

    public Date getFechaC() {
        return fechaC;
    }

    public void setFechaC(Date fechaC) {
        this.fechaC = fechaC;
    }

    public Date getFechaV() {
        return fechaV;
    }

    public void setFechaV(Date fechaV) {
        this.fechaV = fechaV;
    }
    
}
