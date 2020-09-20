/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmoadornos.cosmopos.dao;

import com.cosmoadornos.cosmopos.controller.DbCon;
import com.cosmoadornos.cosmopos.model.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
//import javax.swing.JOptionPane;

/**
 *
 * @author b41n
 */
public class ClienteDAO {
    
    Cliente cliente;
    ArrayList<Cliente> clientes;
    protected DbCon dbcon = new DbCon();
    protected Connection cn;
    String sql;
    Statement st;
    ResultSet rs;
    PreparedStatement pst;

    public boolean guardarCliente(Cliente cliente){
        cn = dbcon.getConexion();
        System.out.println(cliente);
        sql ="INSERT INTO clientes("
                + "documento, "
                + "tipo_documento_id, "
                + "nombres, "
                + "apellidos, "
                + "telefono, "
                + "direccion, "
                + "email) "
                + "VALUES(?,?,?,?,?,?,?);";
        
        try{
            pst = cn.prepareStatement(sql);
            
            pst.setString(1, cliente.getDocumento());
            pst.setInt(2, cliente.getTipoDocumentoId());
            pst.setString(3, cliente.getNombres());
            pst.setString(4, cliente.getApellidos());
            pst.setString(5, cliente.getTelefono());
            pst.setString(6, cliente.getDireccion());
            pst.setString(7, cliente.getEmail());
            if(pst.executeUpdate()==1){
                return true;
                //System.out.println("OK.");
            }
            
        }catch(SQLException ex){
            System.out.println("Error SQL:\n"+ ex);
        }
        return false;
    }
    
    
    public Cliente buscarCliente(String documento){
        cn = dbcon.getConexion();
        cliente = new Cliente();
        try{
            sql ="SELECT "
                    + "C.cliente_id, "
                    + "T.tipo_documento, "
                    + "C.nombres, "
                    + "C.apellidos, "
                    + "C.telefono, "
                    + "C.direccion, "
                    + "C.email "
                    + "FROM clientes AS C, "
                    + "tipos_documentos AS T "
                    + "WHERE "
                    + "C.documento = '"+documento+"' "
                    + "AND "
                    + "C.tipo_documento_id = T.tipo_documento_id;";
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()){
                System.out.println();
                cliente.setClienteId(rs.getInt("C.cliente_id"));
                cliente.setDocumento(documento);
                cliente.setNombres(rs.getString("C.nombres"));
                cliente.setApellidos(rs.getString("C.apellidos"));
                cliente.setTelefono(rs.getString("C.telefono"));
                cliente.setDireccion(rs.getString("C.direccion"));
                cliente.setEmail(rs.getString("c.email"));
                cliente.setTipoDocumento(rs.getString("T.tipo_documento"));
                System.out.println(cliente.getNombres());
                System.out.println(cliente.getApellidos());
                System.out.println(cliente.getTelefono());
                System.out.println(cliente.getEmail());
                //TipoCliente
                //Descuento
                return cliente;
            }
        }catch(SQLException ex){
            System.out.println("Error de sql: "+ex);
        }
        return null;
    }
    
    public ArrayList<Cliente> buscarClientes(){
        cn = dbcon.getConexion();
        clientes = new ArrayList();
        sql ="SELECT cliente_id, "
                + "documento, "
                + "nombres, "
                + "apellidos, "
                + "telefono, "
                + "email "
            + "FROM "
                + "clientes;";
        try{
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()){
                clientes.add(new Cliente(
                        rs.getInt("cliente_id"),
                        rs.getString("documento"),
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        rs.getString("telefono"),
                        rs.getString("email")
                ));
            }
            return clientes;
        }catch(SQLException ex){
            System.out.println("Error: "+ex);
            //System.out.println("SQL: "+sql);
        }
        return null;
    }
    
    
    public static void main(String [] args){
        
        
        ClienteDAO cliente = new ClienteDAO();
        
        Cliente cli = new Cliente();
        
        //System.out.println(cliente.buscarClientes());
        //cliente.buscarClientes();
        
        /*
        
        cli.setDocumento(123456789);
        cli.setNombres("Nombre Cliente");
        cli.setApellidos("Apellido Cliente");
        cli.setTelefono("6666666");
        cli.setEmail("mmm@gmail.com");
        
        if(cliente.guardarCliente(cli)){
            System.out.println("Cliente guardado: "+cli.getNombres());
        }
        
        if(cliente.buscarCliente("1121871348")== null){
            JOptionPane.showMessageDialog(null,"Cliente no encontrado");
        }else{
            JOptionPane.showMessageDialog(null,"Cliente encontrado.");
        }
        */
    }
    
}
