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
public class FacturaElectronica /*extends Factura*/{
    int facturaNro;
    Factura factura;
    Empresa empresaCliente;
    String CUFE;
    PagoForma pagoForma;
    PagoMedio pagoMedio;

    public FacturaElectronica(int facturaNro, Factura factura, Empresa empresaCliente, PagoForma pagoForma, PagoMedio pagoMedio) {
        this.facturaNro = facturaNro;
        this.factura = factura;
        this.empresaCliente = empresaCliente;
        this.pagoForma = pagoForma;
        this.pagoMedio = pagoMedio;
    }

    public int getFacturaNro() {
        return facturaNro;
    }

    public void setFacturaNro(int facturaNro) {
        this.facturaNro = facturaNro;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public Empresa getEmpresaCliente() {
        return empresaCliente;
    }

    public void setEmpresaCliente(Empresa empresaCliente) {
        this.empresaCliente = empresaCliente;
    }

    public String getCUFE() {
        return CUFE;
    }

    public void setCUFE(String CUFE) {
        this.CUFE = CUFE;
    }

    public PagoForma getPagoForma() {
        return pagoForma;
    }

    public void setPagoForma(PagoForma pagoForma) {
        this.pagoForma = pagoForma;
    }

    public PagoMedio getPagoMedio() {
        return pagoMedio;
    }

    public void setPagoMedio(PagoMedio pagoMedio) {
        this.pagoMedio = pagoMedio;
    }

}
