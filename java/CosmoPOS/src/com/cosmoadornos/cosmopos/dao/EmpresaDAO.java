/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmoadornos.cosmopos.dao;

import com.cosmoadornos.cosmopos.controller.DbCon;
import com.cosmoadornos.cosmopos.controller.DianController;
import com.cosmoadornos.cosmopos.model.CodigoPostal;
import com.cosmoadornos.cosmopos.model.Departamento;
import com.cosmoadornos.cosmopos.model.Dian;
import com.cosmoadornos.cosmopos.model.Empresa;
import com.cosmoadornos.cosmopos.model.Location;
import com.cosmoadornos.cosmopos.model.Municipio;
import com.cosmoadornos.cosmopos.model.Regimen;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author b41n
 */
public class EmpresaDAO {
    
    DianController dicon = new DianController();
    private Dian dian;
    
    Empresa empresa;
    public int peso;
    public String nombre;
    DbCon dbcon = new DbCon();
    Connection cn;
    String sql;
    
    // Actualizar método de insersión
    public boolean guardarDatosEmpresa(Empresa empresa){
        PreparedStatement pst;
        cn = dbcon.getConexion();
        sql="INSERT INTO empresas("
                + "nombre,"
                + "nit, "
                + "regimen, "
                + "direccion, "
                + "telefono, "
                + " VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        try{
            pst = cn.prepareStatement(sql);
            pst.setString(1, empresa.getCompanyName());
            pst.setString(2, empresa.getCompanyNIT());
            pst.setString(4, empresa.getDireccion());
            pst.setString(5, empresa.getTelefono());
            if(pst.executeUpdate()==1){
                System.out.println("Datos de la empresa guardados correctamente");
                return true;
            }
        }catch(SQLException e){
            System.out.println("Error de SQL: "+e);
        }
        return false;
    }
    
    public Empresa buscarDatosEmpresa(String nit){
        cn = dbcon.getConexion();
        String sqle = ""
                + "SELECT "
                + "E.empresa_id, "
                + "E.empresa_nombre, "
                + "E.empresa_nit, "
                + "E.empresa_dv, "
                + "E.tipo_persona_id, "
                + "D.departamento_id, "
                + "D.departamento_cod, "
                + "D.departamento_nombre, "
                + "M.municipio_id, "
                + "M.municipio_codigo AS mun_cod, "
                + "M.municipio_nombre, "
                + "M.tipo, "
                + "C.codigo_postal_id, "
                + "C.municipio_codigo AS mun_postal, "
                + "C.codigo_postal, "
                + "E.direccion_empresa, "
                //+ "I.direccion_empresa, "
                + "R.regimen_id, "
                + "R.regimen_cod, "
                + "R.regimen_nombre, "
                + "E.empresa_telefono, "
                + "E.empresa_email "
                + "FROM "
                + "empresas AS E, "
                + "departamentos AS D, "
                + "municipios AS M, "
                + "codigos_postales AS C, "
                //+ "direcciones AS I, "
                + "regimenes AS R "
                + "WHERE "
                + "D.departamento_id = E.departamento_id "
                + "AND M.municipio_id = E.municipio_id "
                + "AND C.codigo_postal_id = E.codigo_postal_id "
                //+ "AND I.direccion_id = E.direccion_id "
                + "AND R.regimen_id = E.regimen_id "
                + "AND E.empresa_nit = '"+nit+"'"
                + ";";
        Statement st;
        ResultSet rs;
        dian = dicon.obtenerDianEmpresa(nit);
        //this.dian = dicon.obtenerDianEmpresa(nit);
        try{
            //JOptionPane.showMessageDialog(null, "Resolucion: "+dian.getTextResol());
            st = cn.createStatement();
            rs = st.executeQuery(sqle);
            while(rs.next()){
                empresa = new Empresa(
                        rs.getInt("empresa_id"),
                        dian,
                        rs.getString("empresa_nombre"),
                        rs.getString("empresa_nit"),
                        new Location(
                                new Departamento(
                                        rs.getInt("departamento_id"),
                                        rs.getString("departamento_nombre"),
                                        rs.getInt("departamento_id")
                                ),
                                new Municipio( 
                                        rs.getInt("municipio_id"), 
                                        rs.getInt("departamento_cod"), 
                                        rs.getInt("mun_cod"), 
                                        rs.getString("municipio_nombre"),
                                        rs.getString("tipo")
                                ),
                                new CodigoPostal(
                                        rs.getInt("codigo_postal_id"), 
                                        rs.getInt("mun_postal"), 
                                        rs.getInt("codigo_postal")
                                )
                        ),
                        //empresa.setLocation(location);
                        new Regimen(
                                rs.getInt("regimen_id"),
                                rs.getString("regimen_cod"),
                                rs.getString("regimen_nombre")
                        ),
                        rs.getString("direccion_empresa"),
                        rs.getString("empresa_telefono"),
                        rs.getString("empresa_email")
                    );
            }
            return empresa;
        }catch(SQLException ex){
            System.out.println("Error obteniendo los datos de la empresa. "+ex);
            return null;
        }
    }
    
}
