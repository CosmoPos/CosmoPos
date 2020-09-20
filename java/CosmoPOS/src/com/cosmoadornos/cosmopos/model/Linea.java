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
public class Linea {
    int id;
    String lineaNombre;
    String descripcion;
    int estado;

    public Linea(String lineaNombre, String descripcion, int estado) {
        this.lineaNombre = lineaNombre;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public Linea(int id, String lineaNombre, String descripcion, int estado) {
        this.id = id;
        this.lineaNombre = lineaNombre;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public Linea(int id, String lineaNombre) {
        this.id = id;
        this.lineaNombre = lineaNombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLineaNombre() {
        return lineaNombre;
    }

    public void setLineaNombre(String lineaNombre) {
        this.lineaNombre = lineaNombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
}
