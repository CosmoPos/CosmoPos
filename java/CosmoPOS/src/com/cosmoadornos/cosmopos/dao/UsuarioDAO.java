/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmoadornos.cosmopos.dao;

import com.cosmoadornos.cosmopos.controller.DbCon;
import com.cosmoadornos.cosmopos.model.Permiso;
import com.cosmoadornos.cosmopos.model.Usuario;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author b41n
 */
public class UsuarioDAO {
    private final DbCon dbcon = new DbCon();
    private Connection cn;
    private String sql;
    private Statement st;
    private ResultSet rs;
    
    public String iniciarSesion(Usuario usuario){
        
        
        sql ="SELECT tipo_usuario FROM usuarios AS U, tipos_usuarios AS T WHERE U.tipo_usuario_id = T.tipo_usuario_id AND U.nombre_usuario ='"+usuario.getUsuario()+"' AND U.passwd ='"+usuario.getPassword()+"';";
        try{
            cn = dbcon.getConexion();
            System.out.println(usuario.getUsuario());
            System.out.println(usuario.getPassword());
            
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            
            while(rs.next()){
                System.out.println("Usuario y contraseñas Correctas");
                System.out.println("Tipo de usuario: "+rs.getString(1));
                return rs.getString("tipo_usuario");
            }
            
        }catch(SQLException e){
            System.out.println("Error SQL:"+e);
        }
        return null;
    }
    
    public ArrayList<Permiso> obtenerPermisos(String tipoUsuario){
        sql = "SELECT P.nombre_permiso FROM "
                + "permisos AS P, "
                + "tipos_usuarios AS T, "
                + "tipos_usuarios_permisos AS UP "
                + "WHERE UP.tipo_usuario_id= T.tipo_usuario_id "
                + "AND UP.permiso_id = P.permiso_id "
                + "AND T.tipo_usuario = '"+tipoUsuario+"';";
        try{
            cn = dbcon.getConexion();
            ArrayList<Permiso> permisos = new ArrayList();
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()){
            permisos.add(new Permiso(rs.getString("nombre_permiso")));
            }
            return permisos;
        }catch(SQLException ex){
            System.out.println("ERROR de SQL: "+ex);
        }
        
        /*
        permisos.add(new Permiso("CONFIGURACION"));
        permisos.add(new Permiso("PROVEEDORES"));
        permisos.add(new Permiso("COMPRAS"));
        permisos.add(new Permiso("PRODUCTOS"));
        permisos.add(new Permiso("NOMINA"));
        permisos.add(new Permiso("CLIENTES"));
        permisos.add(new Permiso("VENTAS"));
        permisos.add(new Permiso("INVENTARIOS"));
        */
        
        return null;
    }
    
    public ArrayList<Usuario> obtenerUsuarios(){
        sql ="SELECT U.usuario_id, U.nombre_usuario, T.tipo_usuario_id FROM usuarios AS U, tipos_usuarios AS T WHERE U.tipo_usuario_id = T.tipo_usuario_id AND T.tipo_usuario ='Vendedor';";
        try{
            cn = dbcon.getConexion();
            ArrayList<Usuario> usuarios = new ArrayList();
            //System.out.println(usuario.getPassword());
            
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            
            while(rs.next()){
                usuarios.add(new Usuario(
                        rs.getInt("usuario_id"),
                        rs.getString("nombre_usuario"),
                        rs.getInt("tipo_usuario_id")
                ));
            }
            return usuarios;
        }catch(SQLException e){
            System.out.println("Error SQL:"+e);
        }
        return null;
    }
    
    
    public static void main(String []args){
        /*
        UsuarioDAO u = new UsuarioDAO();
        Usuario user = new Usuario("Brayan","Novoa");
        
        
        String tipoU = u.iniciarSesion(user);
        if(tipoU!=null){
            JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso." );
            JOptionPane.showMessageDialog(null, "Tipo de usuario: "+tipoU);
        }else{
            JOptionPane.showMessageDialog(null, "Error de acceso.");
        }
        */
    }
    
}
