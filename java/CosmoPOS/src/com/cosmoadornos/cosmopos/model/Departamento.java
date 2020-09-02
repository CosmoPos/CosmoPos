/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmoadornos.cosmopos.model;

/**
 *
 * @author Brayan Novoa <bnovoa.linux@gmail.com at cosmoadornos.com>
 */
public class Departamento {
    
    int id;
    int deptoCod;
    String nombre;
    
    public Departamento(String nombre, int deptoCod){
        this.nombre=nombre;
        this.deptoCod=deptoCod;
    }
    
    public Departamento(int id, String nombre, int deptoCod){
        this.id=id;
        this.nombre=nombre;
        this.deptoCod=deptoCod;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDeptoCod() {
        return deptoCod;
    }

    public void setDeptoCod(int deptoCod) {
        this.deptoCod = deptoCod;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
