/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmoadornos.cosmopos.dao;

import com.cosmoadornos.cosmopos.controller.DbCon;
import com.cosmoadornos.cosmopos.model.SubTipo;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author b41n
 */
public class SubTipoDAO {
    DbCon dbcon = new DbCon();
    Connection cn;
    Statement st;
    ResultSet rs;
    String sql;
    ArrayList<SubTipo> subTipos;
    
    public ArrayList<SubTipo> buscarSubTipos(){
        sql = "SELECT sub_tipo_id, tipo_producto_id, sub_tipo FROM sub_tipos;";
        System.out.println("CONSULTA: "+sql);
        try{
            cn = dbcon.getConexion();
            subTipos = new ArrayList();
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()){
                subTipos.add(new SubTipo(
                        rs.getInt("sub_tipo_id"),
                        rs.getInt("tipo_producto_id"),
                        rs.getString("sub_tipo")
                ));
            }
            return subTipos;
        }catch(SQLException ex){
            System.out.println("ERROR SQL: "+ex);
        }
        return null;
    }
    
}
