/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmoadornos.cosmopos.controller;

import com.cosmoadornos.cosmopos.dao.DianDAO;
import com.cosmoadornos.cosmopos.model.Dian;

/**
 *
 * @author b41n
 */
public class DianController {
    
    DianDAO dao = new DianDAO();
    
    public Dian obtenerDianEmpresa(String CompanyNIT){
        return dao.obtenerDianEmpresa(CompanyNIT);
    }
    
    public boolean guardarDianEmpresa(Dian dian){
        return false;
    }
    
}
