/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmoadornos.cosmopos.dao;

import com.cosmoadornos.cosmopos.controller.DbCon;
import com.cosmoadornos.cosmopos.model.Compra;
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
public class CompraDAO {
    
    Connection cn;
    DbCon dbcon = new DbCon();
    ArrayList<Compra> compras;
    PreparedStatement pst;
    Statement st;
    ResultSet rs;
    String sql;

    public boolean guardarCompra(Compra compra){
        cn = dbcon.getConexion();
        /*
        JOptionPane.showMessageDialog(null,"proveedor: "+compra.getProveedorId()+
                "\nFactura: "+compra.getFacturaNo());
        */
        try{
            sql ="INSERT INTO compras(proveedor_id, factura_no) VALUES(?,?);";
            pst = cn.prepareStatement(sql);
            pst.setInt(1, compra.getProveedorId());
            pst.setInt(2, compra.getFacturaNo());
            System.out.println("SQL: "+sql);
            if(pst.executeUpdate()==1){
                System.out.println("");
                System.out.println("");
            }else{    
            }
            return true;
        }catch(SQLException ex){
            System.out.println("Error SQL:"+ ex+"\n"+sql);
        }
        return false;
    }
    
    public int buscarCompraId(int proveedorId, int facturaNro){
        int compraId;
        sql = "SELECT compra_id FROM compras WHERE proveedor_id = "+proveedorId+" AND factura_no = "+facturaNro+";";
        System.out.println("SQL: "+sql);
        try{
            cn = dbcon.getConexion();
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()){
                compraId = rs.getInt("compra_id");
                return compraId;
            }
        }catch(SQLException ex){
            System.out.println("ERROR SQL: "+ex);
        }
        return 0;
    }
    
    public static void main(String [] args){
        CompraDAO dao = new CompraDAO();
        System.out.println("Id de la compra: "+dao.buscarCompraId(1, 134657));
    }
    
}
