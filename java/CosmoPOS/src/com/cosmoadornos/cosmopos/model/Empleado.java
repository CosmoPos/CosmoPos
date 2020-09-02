/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmoadornos.cosmopos.model;

import java.util.ArrayList;

/**
 *
 * @author b41n
 */
public class Empleado extends Persona{
    
    ArrayList<Permiso> permisos;
    
    public Empleado(){
        
    }
    
    public Empleado(ArrayList<Permiso> permisos){
        this.permisos = permisos;
    }

    public ArrayList<Permiso> getPermisos() {
        return permisos;
    }

    public void setPermisos(ArrayList<Permiso> permisos) {
        this.permisos = permisos;
    }
    
}
