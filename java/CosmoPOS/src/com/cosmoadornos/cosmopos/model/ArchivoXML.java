/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmoadornos.cosmopos.model;
import com.cosmoadornos.cosmopos.controller.Functions;

/**
 *
 * @author b41n
 */
public class ArchivoXML {
    
    private FacturaElectronica facturaElectronica;
    
    public ArchivoXML(FacturaElectronica facturaElectronica){
        this.facturaElectronica = facturaElectronica;
    }
    
    //public Factura getFacturaElectronica() {
    public FacturaElectronica getFacturaElectronica() {
        return facturaElectronica;
    }

    public void setFacturaElectronica(FacturaElectronica facturaElectronica) {
        this.facturaElectronica = facturaElectronica;
    }

}
