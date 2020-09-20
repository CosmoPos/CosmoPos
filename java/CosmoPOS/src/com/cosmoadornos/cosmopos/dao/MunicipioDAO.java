/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmoadornos.cosmopos.dao;

import com.cosmoadornos.cosmopos.controller.DbCon;
import com.cosmoadornos.cosmopos.model.Municipio;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author b41n
 */
public class MunicipioDAO {
    
    DbCon dbcon = new DbCon();
    Connection cn;
    Statement st;
    ResultSet rs;
    String sql;
    
    Municipio municipio;
    private ArrayList<Municipio> ciudades;
    
    public ArrayList<Municipio> buscarMunicipios(){
        ciudades = new ArrayList();
        sql = "SELECT * FROM ciudades;";
        try{
            cn = dbcon.getConexion();
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()){
                ciudades.add(new Municipio(
                        rs.getInt("municipio_id"),
                        rs.getInt("DeptoCode"),
                        rs.getInt("municipio_codigo"),
                        rs.getString("municipio_nombre"),
                        rs.getString("tipo")
                ));
            }
            return ciudades;
        }catch(SQLException ex){
            System.out.println("Error SQL: "+ex);
        }
        return null;
    }
    
    public Municipio getMunicipio(){
        municipio= new Municipio(2458, 50, 50001, "VILLAVICENCIO", "urbano");
        return municipio;
    }
    
}
