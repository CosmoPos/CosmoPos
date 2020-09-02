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
public class Proveedor {
    
    int id;
    String proveedorNombre;
    String proveedorTelefono;
    String proveedorDireccion;
    int ciudadId;
    String ciudad;
    String proveedorCuenta;
    
    public Proveedor(){
        
    }

    // Constructor utilizado para llenar los combos
    public Proveedor(int id, String proveedorNombre) {
        this.id = id;
        this.proveedorNombre = proveedorNombre;
    }

    // Constructor utilizado para llenar las listas
    public Proveedor(int id, String proveedorNombre, String proveedorTelefono, String proveedorDireccion, int ciudadId, String proveedorCuenta) {
        this.id = id;
        this.proveedorNombre = proveedorNombre;
        this.proveedorTelefono = proveedorTelefono;
        this.proveedorDireccion = proveedorDireccion;
        this.ciudadId = ciudadId;
        this.proveedorCuenta = proveedorCuenta;
    }
    
    public Proveedor(int id, String proveedorNombre, String proveedorTelefono, String proveedorDireccion, String ciudad, String proveedorCuenta) {
        this.id = id;
        this.proveedorNombre = proveedorNombre;
        this.proveedorTelefono = proveedorTelefono;
        this.proveedorDireccion = proveedorDireccion;
        this.ciudad = ciudad;
        this.proveedorCuenta = proveedorCuenta;
    }

    // Consstructor usado para gusrdar un nuevo proveedor
    public Proveedor(String proveedorNombre, String proveedorTelefono, String proveedorDireccion, String ciudad, String proveedorCuenta) {
        this.proveedorNombre = proveedorNombre;
        this.proveedorTelefono = proveedorTelefono;
        this.proveedorDireccion = proveedorDireccion;
        this.ciudad = ciudad;
        this.proveedorCuenta = proveedorCuenta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProveedorNombre() {
        return proveedorNombre;
    }

    public void setProveedorNombre(String proveedorNombre) {
        this.proveedorNombre = proveedorNombre;
    }

    public String getProveedorTelefono() {
        return proveedorTelefono;
    }

    public void setProveedorTelefono(String proveedorTelefono) {
        this.proveedorTelefono = proveedorTelefono;
    }

    public String getProveedorDireccion() {
        return proveedorDireccion;
    }

    public void setProveedorDireccion(String proveedorDireccion) {
        this.proveedorDireccion = proveedorDireccion;
    }

    public int getCiudadId() {
        return ciudadId;
    }

    public void setCiudadId(int ciudadId) {
        this.ciudadId = ciudadId;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
    
    public String getProveedorCuenta() {
        return proveedorCuenta;
    }

    public void setProveedorCuenta(String proveedorCuenta) {
        this.proveedorCuenta = proveedorCuenta;
    }
    
}
