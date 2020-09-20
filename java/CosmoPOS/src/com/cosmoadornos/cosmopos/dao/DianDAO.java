/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmoadornos.cosmopos.dao;

import com.cosmoadornos.cosmopos.controller.DbCon;
import com.cosmoadornos.cosmopos.model.Dian;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author b41n
 */
public class DianDAO {
    
    Dian dian;
    
    DbCon dbcon = new DbCon();
    Connection cn;
    String sql;
    Statement st;
    ResultSet rs;
    PreparedStatement pst;
    
    /**
     * function obtenerDianEmpresa
     * @param CompanyNIT corresponde al nit de la epresa que emite la factura
     * @return Dian es el objeto que contiene la información de la empresa
     * a la que coresponda el CompanyNIT respecto a su información de la Dian
     */
    public Dian obtenerDianEmpresa(String CompanyNIT){
        //dian = new Dian();
        
        //dian = new Dian("SETP","23fb4da2-1ebc-4ac9-a89a-e5dcabd182a5", 58100,"Resolución de facturación DIAN No.", "18760000001", "2019-01-19", "2030-01-19", 990000000, 995000000, 1, 1, "800197268", "fc8eac422eba16e22ffd8c6f94b3f40a6e38162c",2);// ambiente de pruebas
        
        cn = dbcon.getConexion();
        try{
            sql ="SELECT "
                    + "prefix, "
                    + "software_id, "
                    + "software_pin, "
                    + "D.resolucion_txt, "
                    + "D.resolucion_numero, "
                    + "D.fechaDesde, "
                    + "D.fechaHasta, "
                    + "D.rango_inicial, "
                    + "D.rango_final, "
                    + "D.resolucion_estado, "
                    + "E.empresa_nit ,"
                    + "D.dian_nit, "
                    + "D.llave_tecnica, "
                    + "D.ambiente_ejecucion "
                    + "FROM dian AS D, "
                    + "empresas AS E "
                    + "WHERE E.empresa_id = D.empresa_id "
                    + "AND E.empresa_nit = '"+CompanyNIT+"';";
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()){
                dian = new Dian(
                        rs.getString("Prefix"),
                        rs.getString("software_id"),
                        rs.getInt("software_pin"),
                        rs.getString("resolucion_txt"),
                        rs.getString("resolucion_numero"),
                        rs.getString("fechaDesde"),
                        rs.getString("fechaHasta"),
                        rs.getInt("rango_inicial"),
                        rs.getInt("rango_final"),
                        rs.getString("resolucion_estado"),
                        rs.getInt("empresa_nit"),
                        rs.getString("dian_nit"),
                        rs.getString("llave_tecnica"),
                        rs.getInt("ambiente_ejecucion")
                        
                );
                //TipoCliente
                //Descuento
            }
            return dian;
        }catch(SQLException ex){
            System.out.println("Error de sql: "+ex);
            return null;
        }
        
        //dian = new Dian("SETP","23fb4da2-1ebc-4ac9-a89a-e5dcabd182a5", 58100,"Resolución de facturación DIAN No.", "18760000001", "2019-01-19", "2030-01-19", 990000000, 995000000, 1, 1, "", "",1);// ambiente de PRODUCCIÓN
        
    }
}
