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
public class Dian {
    int id;
    private String Prefix;
    private String SoftwareId;
    private int Pin;
    String textResol;
    String InvoiceAuthorization;
    String StartDate;
    String EndDate;
    int StartRange;
    int EndRange;
    String stateRes;
    int empresaId;
    String AuthorizationProviderID; // nit de la DIAN
    int ProfileExecutionID;// Ambiente de ejecucion 1 Produccipon 2 Pruebas
    private String llaveTecnica; // LLave técnica de la numeración
    
    public Dian(
            String Prefix, // PREFIJO DE LA NUMERACIÓN
            String SofweareId, //Id del Software
            int Pin, // Pin del Software
            String textResol, // tEXTO DE LA RESOLUCIÓN
            String InvoiceAuthorization,// NUMERO DE LA RESOLUCIÓN 
            String StartDate, // INICIO DE LA RESOLUCION
            String EndDate,  // FIN DE LA RESOLUCION
            int StartRange, // RANGO INICIAL DE LA NUMERACION
            int EndRange, // RANGO FINAL DE LA NUMERACION
            String stateRes, // ESTADO DE LA RESOLUCIÓN
            int empresaId, //CODIGO DE LA EMPRESA QUE USA EL SOFTWARE -- INTERNO
            String AuthorizationProviderID, // NIT DE LA DIAN
            String llaveTecnica, // Llave técnica del software. - ver pagina del usuario en la dian
            int ProfileExecutionID
    ){
        this.Prefix = Prefix;
        this.SoftwareId = SofweareId;
        this.Pin = Pin;
        this.textResol = textResol;
        this.InvoiceAuthorization= InvoiceAuthorization;
        this.StartDate = StartDate;
        this.EndDate = EndDate;
        this.StartRange = StartRange;
        this.EndRange = EndRange;
        this.stateRes = stateRes;
        this.empresaId = empresaId;
        this.AuthorizationProviderID = AuthorizationProviderID;
        this.llaveTecnica = llaveTecnica;
        this.ProfileExecutionID = ProfileExecutionID;
    }
    
    public Dian(/*String rango, */String textResol){
        this.textResol = textResol;
        //this.rango = rango;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrefix() {
        return Prefix;
    }

    public void setPrefix(String Prefix) {
        this.Prefix = Prefix;
    }

    public String getSoftwareId() {
        return SoftwareId;
    }

    public void setSoftwareId(String SoftwareId) {
        this.SoftwareId = SoftwareId;
    }

    public int getPin() {
        return Pin;
    }

    public void setPin(int Pin) {
        this.Pin = Pin;
    }

    public String getTextResol() {
        return textResol;
    }

    public void setTextResol(String textResol) {
        this.textResol = textResol;
    }

    public String getInvoiceAuthorization() {
        return InvoiceAuthorization;
    }

    public void setInvoiceAuthorization(String InvoiceAuthorization) {
        this.InvoiceAuthorization = InvoiceAuthorization;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String StartDate) {
        this.StartDate = StartDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String EndDate) {
        this.EndDate = EndDate;
    }

    public int getStartRange() {
        return StartRange;
    }

    public void setStartRange(int StartRange) {
        this.StartRange = StartRange;
    }

    public int getEndRange() {
        return EndRange;
    }

    public void setEndRange(int EndRange) {
        this.EndRange = EndRange;
    }

    public String getStateRes() {
        return stateRes;
    }

    public void setStateRes(String stateRes) {
        this.stateRes = stateRes;
    }

    public int getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(int empresaId) {
        this.empresaId = empresaId;
    }

    public String getAuthorizationProviderID() {
        return AuthorizationProviderID;
    }

    public void setAuthorizationProviderID(String AuthorizationProviderID) {
        this.AuthorizationProviderID = AuthorizationProviderID;
    }

    public int getProfileExecutionID() {
        return ProfileExecutionID;
    }

    public void setProfileExecutionID(int ProfileExecutionID) {
        this.ProfileExecutionID = ProfileExecutionID;
    }

    public String getLlaveTecnica() {
        return llaveTecnica;
    }

    public void setLlaveTecnica(String llaveTecnica) {
        this.llaveTecnica = llaveTecnica;
    }

}
