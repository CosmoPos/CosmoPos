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
public class ZDiario {
    
    int zDiarioId;
    int zDiario;
    String zDiarioFecha;

    public ZDiario(){
        
    }
    
    public ZDiario(int zDiario, String zDiarioFecha) {
        this.zDiario = zDiario;
        this.zDiarioFecha = zDiarioFecha;
    }

    public int getzDiarioId() {
        return zDiarioId;
    }

    public void setzDiarioId(int zDiarioId) {
        this.zDiarioId = zDiarioId;
    }

    public int getzDiario() {
        return zDiario;
    }

    public void setzDiario(int zDiario) {
        this.zDiario = zDiario;
    }

    public String getzDiarioFecha() {
        return zDiarioFecha;
    }

    public void setzDiarioFecha(String zDiarioFecha) {
        this.zDiarioFecha = zDiarioFecha;
    }
    
}
