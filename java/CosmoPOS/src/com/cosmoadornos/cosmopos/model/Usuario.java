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
public class Usuario {
    int usuarioId;
    String usuario;
    String password;
    String tipoUsuario;
    int tipoUsuarioId;
    
    public Usuario(){
    }
    
    public Usuario(String usuario, String password){
        this.usuario = usuario;
        this.password = password;
    }
    
    // Constructor usado para la lista de vendedores.

    public Usuario(int usuarioId, String usuario, int tipoUsuarioId) {
        this.usuarioId = usuarioId;
        this.usuario = usuario;
        this.tipoUsuarioId = tipoUsuarioId;
    }    

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    
}
