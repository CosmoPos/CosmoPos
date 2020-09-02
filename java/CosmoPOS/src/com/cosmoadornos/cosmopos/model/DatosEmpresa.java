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
public class DatosEmpresa  extends Empresa{

    public DatosEmpresa(int id,Dian dian, String CompanyName, String CompanyNIT, Location location, Regimen regimen, String telefono, String email) {
        super(id, dian, CompanyName, CompanyNIT, location, regimen, telefono, email);
    }
    
}
