/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmoadornos.cosmopos.dao;

import com.cosmoadornos.cosmopos.controller.DbCon;
import com.cosmoadornos.cosmopos.model.TipoDocumento;
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
public class TipoDocumentoDAO {
    
    DbCon dbcon = new DbCon();
    private Connection cn;
    private Statement st;
    private ResultSet rs;
    private PreparedStatement pst;
    private String sql;
    
    
    public ArrayList<TipoDocumento> obtenerTiposDocumentos(){
        ArrayList<TipoDocumento> lista = new ArrayList();
        sql = "SELECT tipo_documento_id, tipo_documento, descripcion FROM tipos_documentos;";
        try{
            cn = dbcon.getConexion();
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()){
                lista.add(new TipoDocumento(
                        rs.getInt("tipo_documento_id"),
                        rs.getString("tipo_documento"),
                        rs.getString("descripcion")
                ));
            }
            return lista;
        }catch(SQLException ex){
            System.out.println("ERROR de SQL: "+ex);
        }
        return null;
    }
}
