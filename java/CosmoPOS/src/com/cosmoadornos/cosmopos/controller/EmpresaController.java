/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmoadornos.cosmopos.controller;

import com.cosmoadornos.cosmopos.dao.EmpresaDAO;
import com.cosmoadornos.cosmopos.model.Empresa;

/**
 *
 * @author b41n
 */
public class EmpresaController {
    Empresa empresa;
    EmpresaDAO dao = new EmpresaDAO();
    
    public boolean guardarDatosEmpresa(Empresa empresa){
        return dao.guardarDatosEmpresa(empresa);
    }
    
    public Empresa buscarDatosEmpresa(String nit){
        empresa = dao.buscarDatosEmpresa(nit);
        return empresa;
    }
    
}
