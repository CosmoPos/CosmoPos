/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmoadornos.cosmopos.dao;

import com.cosmoadornos.cosmopos.controller.DbCon;
import com.cosmoadornos.cosmopos.model.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.cosmoadornos.cosmopos.interfaces.IProductos;

/**
 *
 * @author b41n
 */
public class ProductoDAO implements IProductos {
    private final DbCon dbcon = new DbCon();
    private String sql;
    private Statement st;
    private ResultSet rs;
    private PreparedStatement pst;
    private Connection cn;
    
    public int obtenerUltimoProducto(){
        int productoId = 0;
        try{
            sql ="SELECT producto_id FROM productos;";
            cn = dbcon.getConexion();
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            if(rs.last()){
                productoId = rs.getInt(1);
            }
        }catch(SQLException e){
            
        }
        return productoId;
    }
    
    @Override
    public boolean guardarProductos(ArrayList<Producto> productos, int compraId){
        try{
            for(int i = 0 ; i <= productos.size()-1; i++){
                sql = "INSERT INTO productos (producto_nombre, compra_id) VALUES (?,?)";
                cn = dbcon.getConexion();
                pst = cn.prepareStatement(sql);
                pst.setString(1, productos.get(i).getNombre());
                pst.setInt(2, compraId);
                System.out.println("SQL: "+pst);
                if(pst.executeUpdate()==1){
                    System.out.println("Guardado.");
                }else{
                    System.out.println("No guardado.");
                }
            }
            return true;
        }
        catch(SQLException ex){

        }
        return false;
    }
    public boolean guardarProducto(Producto producto){
        sql = "INSERT INTO productos";
        try{
            cn = dbcon.getConexion();
            pst = cn.prepareStatement(sql);
            pst.setString(0, sql);
            
        }catch(SQLException ex){
            
        }
        return false;
    }
    
    @Override
    public ArrayList<Producto> buscarProductos(){
        ArrayList<Producto> productos = new ArrayList();
        sql = "SELECT P.producto_id, "
                + "P.producto_nombre, "
                + "S.sub_tipo, "
                + "S.sub_tipo_id, "
                + "R.producto_referencia, "
                + "R.producto_precio "
                + "FROM "
                + "precios_productos AS R, "
                + "sub_tipos AS S, "
                + "productos AS P "
                + "WHERE "
                + "P.producto_id = R.producto_id "
                + "AND S.sub_tipo_id = R.sub_tipo_id "
                + "AND P.estado_producto = 'ACTIVO'"
                + "GROUP BY "
                + "P.producto_nombre,"
                + "S.sub_tipo "
                + "ORDER BY P.producto_id;";
        
        cn = dbcon.getConexion();
        try{
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()){
                productos.add(new Producto(
                        rs.getInt("producto_id"),
                        rs.getString("producto_nombre"),
                        rs.getString("sub_tipo"),
                        rs.getInt("sub_tipo_id"),
                        rs.getString("producto_referencia"),
                        rs.getFloat("producto_precio")
                ));
            }
            System.out.println(""+sql);
            return productos;
        }catch(SQLException ex){
            System.out.println("Error de SQL: "+ex);
        }
        return null;
    }
    
    
    public ArrayList<Producto> buscarProductosCombo(int proveedorId){
        ArrayList<Producto> productos = new ArrayList();
        sql = "SELECT "
                + "P.producto_id, "
                + "P.producto_nombre "
                + "FROM "
                + "productos AS P, "
                + "compras AS C, "
                + "proveedores AS R "
                + "WHERE "
                + "R.proveedor_id = C.proveedor_id "
                + "AND "
                + "P.compra_id = C.compra_id "
                + "AND "
                + "R.proveedor_id ="+proveedorId+";";
        try{
            cn = dbcon.getConexion();
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()){
                productos.add(new Producto(
                        rs.getInt("producto_id"),
                        rs.getString("producto_nombre")
                ));
            }
            return productos;
        }catch(SQLException ex){
            System.out.println("Error de SQL: "+ex);
        }
        return null;
    }
    
    public static void main(String []args){
        ProductoDAO dao = new ProductoDAO();
        int compraId =1;
        ArrayList<Producto> productos = new ArrayList();
        productos.add(new Producto("Hilo 500 yds cal 120"));
        productos.add(new Producto("Hilo 2000 yds cal 120"));
        productos.add(new Producto("Hilo 2000 yds cal 750"));
        if(dao.guardarProductos(productos, compraId)){
            System.out.println("Proceso Exitoso.");
        }else{
            System.out.println("Proceso Fallido.");
        }
        
    }
    
}
