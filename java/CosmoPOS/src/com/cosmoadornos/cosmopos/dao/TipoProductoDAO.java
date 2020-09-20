/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmoadornos.cosmopos.dao;

import com.cosmoadornos.cosmopos.controller.DbCon;
import com.cosmoadornos.cosmopos.model.TipoProducto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author b41n
 */
public class TipoProductoDAO {
    
    private final DbCon dbcon = new DbCon();
    private Connection cn;
    private String sql;
    private ResultSet rs;
    private Statement st;
    private PreparedStatement pst;
    ArrayList<TipoProducto> tipos;
    
    public ArrayList<TipoProducto> buscarTiposProducto(){
        try{
            tipos = new ArrayList();
            cn = dbcon.getConexion();
            sql = "SELECT tipo_producto_id , medida_cod , tipo_producto FROM tipos_productos;";
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()){
                tipos.add(new TipoProducto(
                        rs.getInt("tipo_producto_id"),
                        rs.getString("medida_cod"),
                        rs.getString("tipo_producto")
                ));
            }
            return tipos;
        }catch(SQLException ex){
            System.out.println("ERROR SQL: "+ex);
        }
        return null;
    }
    
    public boolean guardarTipoProducto(){
        return false;
    }
    
    
}
