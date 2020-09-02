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
public class TipoDocumento {
    
    int tipoDocumentoId;
    String tipoDocumento;
    String descripcion;
    
    public TipoDocumento(String tipoDocumento, String descripcion){
        this.tipoDocumento = tipoDocumento;
        this.descripcion = descripcion;
    }

    public TipoDocumento(int tipoDocumentoId, String tipoDocumento, String descripcion) {
        this.tipoDocumentoId = tipoDocumentoId;
        this.tipoDocumento = tipoDocumento;
        this.descripcion = descripcion;
    }

    public int getTipoDocumentoId() {
        return tipoDocumentoId;
    }

    public void setTipoDocumentoId(int tipoDocumentoId) {
        this.tipoDocumentoId = tipoDocumentoId;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
