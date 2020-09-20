/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmoadornos.cosmopos.controller;

import com.cosmoadornos.cosmopos.dao.CodigoPostalDAO;
import com.cosmoadornos.cosmopos.dao.DeptoDAO;
import com.cosmoadornos.cosmopos.dao.MunicipioDAO;
import com.cosmoadornos.cosmopos.model.CodigoPostal;
import com.cosmoadornos.cosmopos.model.Departamento;
import com.cosmoadornos.cosmopos.model.Municipio;
import java.util.ArrayList;

/**
 *
 * @author b41n
 */
public class LocationController {
    DeptoDAO dedao = new DeptoDAO();
    MunicipioDAO ciuDAO = new MunicipioDAO();
    CodigoPostalDAO codao = new CodigoPostalDAO();
    
    Municipio municipio;
    private ArrayList<Departamento> departamentos;
    private ArrayList<Municipio> municipios;
    private ArrayList<CodigoPostal> codigosPostales;
    private ArrayList<String> direcciones;
    
    public ArrayList<Departamento> buscarDepartamentos(){
        departamentos = dedao.obtenerDepartamentos();// actualizar DAO
        return departamentos;
    }
    
    public ArrayList<CodigoPostal> buscarCodugosPostales(){
        codigosPostales = codao.obtenetCodigosPostales();
        return codigosPostales;
    }
    
    
    public ArrayList<Municipio> buscarCiudades(){
        municipios = ciuDAO.buscarMunicipios();
        return municipios;
    }
}
