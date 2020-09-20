/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmoadornos.cosmopos.dao;

import com.cosmoadornos.cosmopos.controller.DbCon;
import com.cosmoadornos.cosmopos.model.Precio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author b41n
 */
public class PrecioDAO {
    
    private final DbCon dbcon = new DbCon();
    private Connection cn;
    private PreparedStatement pst;
    private Statement st;
    private ResultSet rs;
    private String sql;
    
    public boolean guardarPrecios(ArrayList<Precio> precios, int compraId){
        JOptionPane.showMessageDialog(null, "Compra: "+ compraId);
        try{
            for(int i = 0 ; i <= precios.size()-1 ; i++){
                sql ="SELECT "
                        + "producto_id "
                        + "FROM "
                        + "productos "
                        + "WHERE "
                        + "producto_nombre = '"+precios.get(i).getProducto()+"' "
                        + "AND "
                        + "compra_id = "+compraId+";";
                System.out.println("SQL: "+sql);
                cn = dbcon.getConexion();
                st = cn.createStatement();
                rs = st.executeQuery(sql);
                while(rs.next()){
                    sql = "INSERT INTO precios_productos "
                            + "(sub_tipo_id, "
                            + "producto_precio, "
                            + "producto_referencia, "
                            + "producto_id)"
                            + "VALUES(?,?,?,?)";
                    try{
                        //cn = dbcon.getConexion();
                        pst = cn.prepareStatement(sql);
                        pst.setInt(1, precios.get(i).getSubTipoId());
                        pst.setInt(2, precios.get(i).getPrecio());
                        pst.setString(3, precios.get(i).getReferencia());
                        pst.setInt(4, rs.getInt("producto_id"));
                        if(pst.executeUpdate()==1){
                            System.out.println("Guardado: "+precios.get(i).getProducto());
                        }else{
                            System.out.println("Error guardando el precio");                                    
                        }
                    }catch(SQLException ex){
                        System.out.println("ERROR: "+ex);
                    }
                    System.out.println("Encontrado");
                }
            }
            return true;
        }
        catch(SQLException ex){
        System.out.println("ERROR: "+ex);
        }
        return false;
    }
    
    public static void main(String [] args){
        ArrayList<Precio> precios;
        int compraId =6;
        
        precios = new ArrayList();
        
        
        precios.add(new Precio(4,1500,"rten5p","TEST"));
        precios.add(new Precio(5,2500,"hon1","TEST"));
        precios.add(new Precio(6,2500,"hon1","TEST"));
        
        PrecioDAO dao = new PrecioDAO();
        if(dao.guardarPrecios(precios, compraId )){
            System.out.println("Proceso Exitoso.");
        }else{
            System.out.println("Proceso Fallido.");
        }
        
    }
    
}
