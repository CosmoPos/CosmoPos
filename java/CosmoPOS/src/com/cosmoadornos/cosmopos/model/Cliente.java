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
public class Cliente {
    
    int clienteId;
    String documento;
    int tipoDocumentoId;
    String nombres;
    String apellidos;
    Location location;
    String direccion;
    String telefono;
    String email;
    String tipoDocumento;
    
    public Cliente(String documento){
        this.documento = documento;
    }
    
    public Cliente(){   
    }

    public Cliente(String documento, String nombres, String apellidos/*, Location location*/, String direccion, String telefono) {
        this.documento = documento;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.telefono = telefono;
    }
    
    public Cliente(String documento, String nombres, String apellidos){
        this.documento = documento;
        this.nombres = nombres;
        this.apellidos = apellidos;
    }
    
    public Cliente(String documento, String nombres, String apellidos, String email){
        this.documento = documento;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
    }
    
    public Cliente(String documento, int tipoDocumentoId, String nombres, String apellidos, String telefono, String email){
        this.documento = documento;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.email = email;
    }

    public Cliente(int clienteId, String documento,int tipoDocumentoId, String nombres, String apellidos, String telefono, String email) {
        this.clienteId = clienteId;
        this.documento = documento;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.email = email;
    }
    
        public Cliente(int clienteId, String documento, String nombres, String apellidos, String telefono, String email) {
        this.clienteId = clienteId;
        this.documento = documento;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.email = email;
    }
    
    public void mostrarCliente(){
        System.out.print(this);
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public int getTipoDocumentoId() {
        return tipoDocumentoId;
    }

    public void setTipoDocumentoId(int tipoDocumentoId) {
        this.tipoDocumentoId = tipoDocumentoId;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

}
