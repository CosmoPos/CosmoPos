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
public class Empresa {
    private int id;
    private Dian dian;
    ArrayList<ResponsabilidadFiscal> responsabilidades;
    String CompanyName;
    String CompanyNIT;
    Location location;
    Regimen regimen;
    String direccion;
    String telefono;
    String email;
    //int IndustryClasificationCode;
    
    public Empresa(int id, Dian dian, String CompanyName, String CompanyNIT/*, int IndustryClasificationCode]*/,Location location, Regimen regimen, String direccion, String telefono, String email){
        this.id = id;
        this.dian = dian;
        this.CompanyName = CompanyName;
        this.CompanyNIT = CompanyNIT;
        //this.IndustryClasificationCode = IndustryClasificationCode;
        this.location = location;
        this.regimen = regimen;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String CompanyName) {
        this.CompanyName = CompanyName;
    }

    public String getCompanyNIT() {
        return CompanyNIT;
    }

    public void setCompanyNIT(String CompanyNIT) {
        this.CompanyNIT = CompanyNIT;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Regimen getRegimen() {
        return regimen;
    }

    public void setRegimen(Regimen regimen) {
        this.regimen = regimen;
    }

    public Dian getDian() {
        return dian;
    }

    public void setDian(Dian dian) {
        this.dian = dian;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /*public int getIndustryClasificationCode() {
        return IndustryClasificationCode;
    }

    public void setIndustryClasificationCode(int IndustryClasificationCode) {
        this.IndustryClasificationCode = IndustryClasificationCode;
    }*/

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
