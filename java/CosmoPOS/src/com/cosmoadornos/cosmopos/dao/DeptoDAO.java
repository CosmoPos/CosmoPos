/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmoadornos.cosmopos.dao;

import com.cosmoadornos.cosmopos.controller.DbCon;
import com.cosmoadornos.cosmopos.model.Departamento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Brayan Novoa <bnovoa.linux@gmail.com at cosmoadornos.com>
 */
public class DeptoDAO {
    
    DbCon dbcon = new DbCon();
    
    public boolean guardarDepartamento(Departamento departamento){
        String registro="INSERT INTO departamentos (departamento) VALUES (?);";
        PreparedStatement pst;
        try{
            pst =dbcon.getConexion().prepareStatement(registro);
            pst.setString(1, departamento.getNombre());
            if(pst.executeUpdate()==1){
                System.out.println("Departamento guardado.");
                return true;
            }
        }catch(SQLException sqle){
            System.out.println("Error guardando el departamento. "+sqle);
        }
        
        return false;
    }
    
    public ArrayList<Departamento> obtenerDepartamentos(){
        ArrayList<Departamento> departamentos = new ArrayList();
        String sql ="SELECT departamento_id, departamento FROM departamentos ORDER BY departamento;";
        Connection cn = dbcon.getConexion();
        try{
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            int i =0;
            System.out.println("Departamentos:");
            while(rs.next()){
                i = i+1;
                departamentos.add(new Departamento(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3)// se agrega este dato
                ));
                System.out.println(i+": "+rs.getString(1));
                //actividades.add(new Actividad(rs.getString(1),rs.getString(2),rs.getString(3), ""));
            }
            return departamentos;
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
    
}
