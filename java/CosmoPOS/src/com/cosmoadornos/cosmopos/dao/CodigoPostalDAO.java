/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmoadornos.cosmopos.dao;

import com.cosmoadornos.cosmopos.controller.DbCon;
import com.cosmoadornos.cosmopos.model.CodigoPostal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author b41n
 */
public class CodigoPostalDAO {
    CodigoPostal codigoPostal;
    private final DbCon dbcon = new DbCon();
    private Connection cn;
    private Statement st;
    private ResultSet rs;
    
    public ArrayList<CodigoPostal> obtenetCodigosPostales(){
        ArrayList<CodigoPostal> codigosPostales = new ArrayList();
        String sql ="SELECT departamento_id, departamento FROM departamentos ORDER BY departamento;";
        cn = dbcon.getConexion();
        try{
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            int i =0;
            System.out.println("Departamentos:");
            while(rs.next()){
                i = i+1;
                codigosPostales.add(new CodigoPostal(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3)// se agrega este dato
                ));
                System.out.println(i+": "+rs.getString(1));
                //actividades.add(new Actividad(rs.getString(1),rs.getString(2),rs.getString(3), ""));
            }
            return codigosPostales;
        }catch(java.sql.SQLException ex){
            JOptionPane.showMessageDialog(null,
                    "ERROR: "+ex);
            System.out.println(ex);
        }catch(NullPointerException e){
            JOptionPane.showMessageDialog(null,
                    "Asegúrese de establecer conexións la Base de Datos.");
        }
        return null;
    }
    
    public CodigoPostal buscarCodigoPostal(){
        String sql ="SELECT departamento_id, departamento FROM departamentos ORDER BY departamento;";
        cn = dbcon.getConexion();
        try{
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            int i =0;
            System.out.println("Departamentos:");
            while(rs.next()){
                i = i+1;
                codigoPostal = new CodigoPostal(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3)// se agrega este dato
                );
                System.out.println(i+": "+rs.getString(1));
                //actividades.add(new Actividad(rs.getString(1),rs.getString(2),rs.getString(3), ""));
            }
            return codigoPostal;
        }catch(java.sql.SQLException ex){
            JOptionPane.showMessageDialog(null,
                    "ERROR: "+ex);
            System.out.println(ex);
            return null;
        }catch(NullPointerException e){
            JOptionPane.showMessageDialog(null,
                    "Asegúrese de establecer conexións la Base de Datos.");
            return null;
        }
    }
    
}
