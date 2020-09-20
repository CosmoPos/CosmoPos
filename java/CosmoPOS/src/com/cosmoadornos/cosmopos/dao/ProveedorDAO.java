/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmoadornos.cosmopos.dao;

import com.cosmoadornos.cosmopos.controller.DbCon;
import com.cosmoadornos.cosmopos.model.Proveedor;
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
public class ProveedorDAO {
    DbCon dbcon = new DbCon();
    private Connection cn;
    private String sql;
    private PreparedStatement pst;
    private Statement st;
    private ResultSet rs;
    private ArrayList<Proveedor> proveedores;
    
    public boolean guardarProveedor(Proveedor proveedor){
        sql ="INSERT INTO proveedores("
                + "proveedor_nombre, "
                + "proveedor_telefono, "
                + "proveedor_direccion, "
                + "ciudad_id, "
                + "proveedor_cuenta) "
                + "VALUES(?,?,?,("
                + "SELECT ciudad_id FROM ciudades "
                + "WHERE ciudad_nombre = '"+proveedor.getCiudad()+"'),?);";
        try{
            cn = dbcon.getConexion();
            pst = cn.prepareStatement(sql);
            pst.setString(1, proveedor.getProveedorNombre());
            pst.setString(2, proveedor.getProveedorTelefono());
            pst.setString(3, proveedor.getProveedorDireccion());
            //pst.setString(4, proveedor.getCiudad());
            pst.setString(4, proveedor.getProveedorCuenta());
            System.out.println("SQL: "+sql);
            if(pst.executeUpdate()==1){
                System.out.println("OK.");
                System.out.println("SQL: "+sql);
                return true;
            }else{
                System.out.println("Error.");
                return false;
            }
        }catch(SQLException ex){
            System.out.println("Error. "+ex);
            
        }
        return false;
    }
    
    public ArrayList<Proveedor> buscarProveedores(){
        sql="SELECT "
                + "P.proveedor_id,"
                + "P.proveedor_nombre, "
                + "P.proveedor_telefono, "
                + "P.proveedor_direccion, "
                + "C.ciudad_nombre, "
                + "P.proveedor_cuenta "
                + "FROM "
                + "ciudades AS C, "
                + "proveedores AS P "
                + "WHERE "
                + "P.ciudad_id = C.ciudad_id;";
        try{
            cn = dbcon.getConexion();
            proveedores = new ArrayList();
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()){
                proveedores.add(new Proveedor(
                        rs.getInt("proveedor_id"),
                        rs.getString("proveedor_nombre"),
                        rs.getString("proveedor_telefono"),
                        rs.getString("proveedor_direccion"),
                        rs.getString("ciudad_nombre"),
                        rs.getString("proveedor_cuenta")
                ));
            }
            return proveedores;
        }catch(SQLException ex){
            System.out.println("Error de sql: "+ex);
        }
        return null;
    }
    
    public ArrayList<Proveedor> buscarProveedoresCombo(){
        sql="SELECT "
                + "proveedor_id,"
                + "proveedor_nombre "
                + "FROM "
                + "proveedores";
        try{
            cn = dbcon.getConexion();
            proveedores = new ArrayList();
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()){
                proveedores.add(new Proveedor(
                        rs.getInt("proveedor_id"),
                        rs.getString("proveedor_nombre")
                ));
            }
            return proveedores;
        }catch(SQLException ex){
            System.out.println("Error de sql: "+ex);
        }
        return null;
    }
    
    public static void main(String [] args){
        ProveedorDAO pro = new ProveedorDAO();
        Proveedor p = new Proveedor(
                "Nombre",
                "Telefono",
                "Direccion",
                "BOGOTÁ",
                "Cuenta"
        );
        
        pro.guardarProveedor(p);
        
    }
    
}
