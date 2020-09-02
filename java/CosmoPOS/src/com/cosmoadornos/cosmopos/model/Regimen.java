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
public class Regimen {
    int id;
    String regimenCod;
    String regimenNombre;

    public Regimen(String regimenCod, String regimenNombre) {
        this.regimenCod = regimenCod;
        this.regimenNombre = regimenNombre;
    }

    public Regimen(int id, String regimenCod, String regimenNombre) {
        this.id = id;
        this.regimenCod = regimenCod;
        this.regimenNombre = regimenNombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegimenCod() {
        return regimenCod;
    }

    public void setRegimenCod(String regimenCod) {
        this.regimenCod = regimenCod;
    }

    public String getRegimenNombre() {
        return regimenNombre;
    }

    public void setRegimenNombre(String regimenNombre) {
        this.regimenNombre = regimenNombre;
    }
    
}
