/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmoadornos.cosmopos.dao;

import com.cosmoadornos.cosmopos.model.Venta;
import com.cosmoadornos.cosmopos.controller.DbCon;
import com.cosmoadornos.cosmopos.model.Linea;
import com.cosmoadornos.cosmopos.model.Producto;
import com.cosmoadornos.cosmopos.model.TipoProducto;
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
public class VentaDAO {

DbCon dbcon = new DbCon();
    Connection cn;
    PreparedStatement pst;
    Statement st;
    ResultSet rs;
    String sql;
    
    public boolean registrarVentas(int nroFactura, ArrayList<Venta> ventas){
        System.out.println("Nro Factura: "+nroFactura);
        System.out.println("Productos: "+ventas.size());
        System.out.println("");
            
        try{
            cn = dbcon.getConexion();
            for(int i = 0 ; i <=ventas.size()-1 ; i++){
                System.out.println("producto: "+(i+1));
                
                sql = "INSERT INTO ventas("
                        + "producto_id, "
                        + "sub_tipo_id, "
                        + "cantidad, "
                        + "precio_sin_iva, "
                        + "precio_iva, "
                        + "precio_con_iva, "
                        + "total_sin_iva, "
                        + "total_iva, "
                        + "total_con_iva, "
                        + "factura_id, "
                        + "z_diario_id"
                        + ")VALUES(?,?,?,?,?,?,?,?,?,?,?);";
                pst = cn.prepareStatement(sql);
                
                pst.setInt(1, ventas.get(i).getProducto().getId());
                pst.setInt(2, ventas.get(i).getProducto().getSubTipoId());
                pst.setFloat(3, ventas.get(i).getCantidad());
                
                pst.setFloat(4, ventas.get(i).getProducto().getPrecio());
                pst.setFloat(5, ventas.get(i).getProducto().getProductoIVA());
                pst.setFloat(6, ventas.get(i).getProducto().getTotal());
                
                pst.setFloat(7, ventas.get(i).getSubtotal());
                pst.setFloat(8, ventas.get(i).getVrTotalIVA());
                pst.setFloat(9, ventas.get(i).getTotalVenta());
                //JOptionPane.showMessageDialog(null, ventas.get(i).getTotalVenta());
                pst.setInt(10, ventas.get(i).getFacturaId());
                pst.setInt(11, ventas.get(i).getzDiario());
                    System.out.println("");
                if(pst.executeUpdate()==1){
                    System.out.println("Venta registrada en la base de datos");
                    //return true;
                }else{
                    System.out.println("Error Registrando la venta.");
                    return false;
                }
            }
            return true;
        }catch(SQLException ex){
            System.out.println("Error de SQL: "+ex);
        }
        return false;
    }
    
    
    public ArrayList<Venta> buscarVentas(int facturaId){
        try{
            ArrayList<Venta> ventas = new ArrayList();
            sql ="select producto_id, sub_tipo_id, cantidad, precio_sin_iva, precio_iva, precio_con_iva, factura_id, z_diario_id FROM ventas WHERE factura_id =(SELECT factura_id FROM facturas WHERE nro_factura ="+facturaId+");";
            cn = dbcon.getConexion();
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            Producto producto = null;
            Linea linea = null;
            while(rs.next()){
                ventas.add(new Venta(
                        new Producto(
                                1950,
                                "PRUEBA CONTROLADOR", 
                                ".1950.", 
                                "UNIDAD", 
                                1500000.00f, 
                                285000.00f, 
                                1785000.00f
                        ),
                        1,  // IMPLEMENTAR  SUB_TIPO_ID
                        /*new Linea(
                                50, 
                                "LINEAPRUEBA"
                        ),*/
                        //rs.getInt("producto_id"),
                        //rs.getInt("linea_id"),
                        
                        rs.getFloat("cantidad"),
                        0.00f,// DESCUENTO
                        rs.getFloat("subtotal"),
                        rs.getFloat("vr_iva"),
                        rs.getFloat("total"),
                        rs.getInt("factura_id"),
                        rs.getInt("z_diario_id")
                ));
            }
            return ventas;
        }catch(SQLException ex){
            System.out.println("ERROR DE QSL: "+ex);
        }
        return null;
    }
    
    public ArrayList<Venta> buscarVentasPOS(int facturaId){
        try{
            ArrayList<Venta> ventas = new ArrayList();
            sql ="select producto_id, linea_id, cantidad, subtotal, vr_iva, total, factura_id, z_diario_id FROM ventas WHERE factura_id =(SELECT factura_id FROM facturas WHERE nro_factura ="+facturaId+");";
            cn = dbcon.getConexion();
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            Producto producto = null;
            Linea linea = null;
            while(rs.next()){
                ventas.add(new Venta(
                        new Producto(
                                1950,
                                "PRUEBA CONTROLADOR", 
                                ".1950.", 
                                "UNIDAD", 
                                1500000.00f, 
                                285000.00f, 
                                1785000.00f
                        ),
                        1,  // IMPLEMENTAR  SUB_TIPO_ID
                        /*new Linea(
                                50, 
                                "LINEAPRUEBA"
                        ),*/
                        //rs.getInt("producto_id"),
                        //rs.getInt("linea_id"),
                        
                        rs.getFloat("cantidad"),
                        0.00f,// DESCUENTO
                        rs.getFloat("subtotal"),
                        rs.getFloat("vr_iva"),
                        rs.getFloat("total"),
                        rs.getInt("factura_id"),
                        rs.getInt("z_diario_id")
                ));
            }
            return ventas;
        }catch(SQLException ex){
            System.out.println("ERROR DE QSL: "+ex);
        }
        return null;
    }
    
    
    public ArrayList<Venta> buscarVentasFEL(int facturaId){
        try{
            ArrayList<Venta> ventas = new ArrayList();
            //sql ="SELECT producto_id, sub_tipo_id, cantidad, precio_sin_iva, precio_iva, precio_con_iva, factura_id, z_diario_id FROM ventas WHERE factura_id =(SELECT factura_id FROM facturas WHERE nro_factura ="+facturaId+");";
            sql="SELECT "
                    + "V.venta_id, "
                    + "P.producto_id, "
                    + "P.producto_nombre, "
                    + "R.producto_referencia, "
                    + "S.sub_tipo, "
                    + "V.sub_tipo_id, "
                    + "V.precio_sin_iva, "
                    + "V.precio_iva, "
                    + "V.precio_con_iva, "
                    + "V.cantidad, "
                    + "V.precio_sin_iva, "
                    + "V.precio_iva, "
                    + "V.precio_con_iva, "
                    + "V.factura_id, "
                    + "F.factura_subtotal, "
                    + "F.factura_iva, "
                    + "F.factura_total, "
                    + "Z.z_diario_id "
                    + "FROM "
                    + "productos AS P, "
                    + "precios_productos AS R, "
                    + "sub_tipos AS S, "
                    + "ventas AS V, "
                    + "facturas AS F, "
                    + "z_diarios AS Z "
                    + "WHERE "
                    + "P.producto_id = V.producto_id "
                    + "AND V.producto_id = R.producto_id "
                    + "AND P.producto_id = R.producto_id "
                    + "AND S.sub_tipo_id = V.sub_tipo_id "
                    + "AND V.sub_tipo_id = R.sub_tipo_id "
                    + "AND F.factura_id = V.factura_id "
                    + "AND F.factura_id ="+facturaId+";";
            cn = dbcon.getConexion();
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            Producto producto = null;
            Linea linea = null;
            while(rs.next()){
                ventas.add(new Venta(
                        new Producto(
                                rs.getInt("producto_id"),
                                rs.getString("producto_nombre"), 
                                rs.getString("producto_referencia"), 
                                rs.getString("sub_tipo"), 
                                rs.getFloat("precio_sin_iva"),// PRECIO SIN IVA 
                                rs.getFloat("precio_iva"),//PRECIO IVA
                                rs.getFloat("precio_con_iva")//PRECIO CON IVA
                        ),
                        rs.getInt("sub_tipo_id"),  // IMPLEMENTAR
                        /*new Linea(
                                50, 
                                "LINEAPRUEBA"
                        ),*/
                        //rs.getInt("producto_id"),
                        //rs.getInt("linea_id"),
                        
                        rs.getFloat("cantidad"),
                        0.00f,// DESCUENTO
                        rs.getFloat("factura_subtotal"),
                        rs.getFloat("factura_iva"),
                        rs.getFloat("factura_total"),
                        rs.getInt("factura_id"),
                        rs.getInt("z_diario_id")
                ));
            }
            return ventas;
        }catch(SQLException ex){
            System.out.println("ERROR DE QSL: "+ex);
        }
        return null;
    }
    
    
    public static void main(String []args){
        
        VentaDAO dao = new VentaDAO();
        
        ArrayList<Venta> ventas;
        ventas = dao.buscarVentasFEL(14);
        //System.out.println("Ventas"+ ventas.get(0).getTotal());
        /*
        
        
        
        ventas.add(new Venta(1,10000,200,1700,1));
        ventas.add(new Venta(1,800,200,1700,1));
        ventas.add(new Venta(1,1000,200,1700,1));
        ventas.add(new Venta(1,1900,200,1700,1));
        
        
        
        int fraNo =1;
        
        if(ventacon.registrarVentas(fraNo, ventas)){
            JOptionPane.showMessageDialog(null,"Ventas registradas.");
        }else{
            System.out.println("Error guardadno");
        }
        
        //ventacon.crearFactura(1, "2019/10/03", 1121871348, ventas);
        */
    }
    
}
