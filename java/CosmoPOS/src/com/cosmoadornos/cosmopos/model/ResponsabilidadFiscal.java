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
public class ResponsabilidadFiscal {
    int responsabilidadid;
    String responsabilidadCodigo;
    String responsabilidadNombre;

    public ResponsabilidadFiscal(int responsabilidadid, String responsabilidadCodigo, String responsabilidadNombre) {
        this.responsabilidadid = responsabilidadid;
        this.responsabilidadCodigo = responsabilidadCodigo;
        this.responsabilidadNombre = responsabilidadNombre;
    }

    public int getResponsabilidadid() {
        return responsabilidadid;
    }

    public void setResponsabilidadid(int responsabilidadid) {
        this.responsabilidadid = responsabilidadid;
    }

    public String getResponsabilidadCodigo() {
        return responsabilidadCodigo;
    }

    public void setResponsabilidadCodigo(String responsabilidadCodigo) {
        this.responsabilidadCodigo = responsabilidadCodigo;
    }

    public String getResponsabilidadNombre() {
        return responsabilidadNombre;
    }

    public void setResponsabilidadNombre(String responsabilidadNombre) {
        this.responsabilidadNombre = responsabilidadNombre;
    }
    
}
