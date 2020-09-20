/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmoadornos.cosmopos.dao;

import com.cosmoadornos.cosmopos.controller.DbCon;
import com.cosmoadornos.cosmopos.model.Factura;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author b41n
 */
public class FacturaDAO {
    
    
    private final DbCon dbcon = new DbCon();
    private Connection cn;
    
    private PreparedStatement pst;
    private Statement st;
    private ResultSet rs;
    private String sql;
    
    public boolean crearFacturaPOS(Factura factura){
        try{
            sql ="INSERT INTO facturas(nro_factura, factura_subtotal, factura_iva, factura_total, factura_fecha, factura_hora, cliente_id) VALUES("
                    +factura.getFactNum()+ ","
                    +factura.getTotalSinImp()+ ","
                    +factura.getTotalImp()+ ","
                    +factura.getTotalMasImp()+ ","
                    + "'"+factura.getFacturaFecha()+"',"
                    + "'"+factura.getFacturaHora()+"',"
                    + ""+factura.getCliente().getClienteId()+");";
            System.out.println("SQL: "+sql);
            
            pst = cn.prepareStatement(sql);
            if(pst.executeUpdate()==1){
                System.out.println("Factura No. "+factura.getFactNum()+" Creada correctamente.");
            }
            return true;/**/
        }catch(SQLException ex){
                System.out.println("Error creando la factura.");
                System.out.println("ERROR SQL:" +ex);
            return false;
        }
    }
    
    
    
    public boolean crearFactura(int nroFactura, String facturaFecha, String facturaHora, int clienteId/*,  ArrayList<Venta> ventas*/){
        try{
            sql ="INSERT INTO facturas(nro_factura, factura_fecha, factura_hora, cliente_id) VALUES("
                    +nroFactura+ ","
                    + "'"+facturaFecha+"',"
                    + "'"+facturaHora+"',"
                    + ""+clienteId+");";
            System.out.println("SQL: "+sql);
            
            pst = cn.prepareStatement(sql);
            if(pst.executeUpdate()==1){
                System.out.println("Factura No. "+nroFactura+" Creada correctamente.");
                return true;/**/
            }
        }catch(SQLException ex){
                System.out.println("Error creando la factura.");
                System.out.println("ERROR SQL:" +ex);
        }
        return false;
    }
    
    public int buscarUltimaFactura(){
        sql ="SELECT nro_factura FROM facturas;";
        int last=0;
        try{
            cn = dbcon.getConexion();
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            if(rs.last()){
                last = rs.getInt(1);
                //JOptionPane.showMessageDialog(null, "ID: "+rs.getInt(1));
                //System.out.println("última factura: " +last);
            }
            return last;
        }catch(SQLException ex){
            
        }
        return 0;
    }
    
}
