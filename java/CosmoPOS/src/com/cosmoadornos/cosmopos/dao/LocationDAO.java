/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmoadornos.cosmopos.dao;

import com.cosmoadornos.cosmopos.controller.DbCon;
import com.cosmoadornos.cosmopos.model.CodigoPostal;
import com.cosmoadornos.cosmopos.model.Departamento;
import com.cosmoadornos.cosmopos.model.Location;
import com.cosmoadornos.cosmopos.model.Municipio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author b41n
 */
public class LocationDAO {
    DbCon dbcon = new DbCon();
    private Connection cn;
    private ResultSet rs;
    private Statement st;
    private PreparedStatement pst;
    String sql;
    Location location;
    Departamento departamento;
    Municipio municipio;
    CodigoPostal codigoPostal;
    
    public boolean guardarLocation(){
        return false;
    }
    
    public Location buscarLocation(){
        sql = "";
        return null;
    } 
}
