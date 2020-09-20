/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmoadornos.cosmopos.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Wilson Novoa
 */
public class DbCon {
    private String dbcError;
    private final String DATABASE ="CosmoPos";
    //private final String DATABASE ="dev_CosmoPos";
    private final String USERNAME ="user";
    private final String PASSWORD ="password";
    private final String HOST = "127.0.0.1";
    private final String PORT = "3306";
    private final String CLASSNAME ="com.mysql.jdbc.Driver";
    private final String JDBC_MYSQL_DRIVER="jdbc:mysql://";
    private final String URL = JDBC_MYSQL_DRIVER+HOST+":"+PORT+"/"+DATABASE;
    
    private Connection cn=null;
    
    public Connection getConexion(){
    try{
            Class.forName(CLASSNAME);
            cn=DriverManager.getConnection(URL+"?autoReconnect=true&",USERNAME,PASSWORD);
            System.out.println("Conectado a la base de datos");
            return cn;
        }catch(ClassNotFoundException | SQLException e){
            dbcError =e.getMessage();
            System.err.println("ERROR :s "+e);
            //closeConexion();
            JOptionPane.showMessageDialog(null, "No se puede acceder a la base de datos.\n"+e);
        }
        
        try {
            if(cn == null || cn.isClosed()){
                cn=DriverManager.getConnection(URL+"?autoReconnect=true&",USERNAME,PASSWORD);
                System.out.println("Conexion Reestablecida");
                return cn;
            }else{
                return null;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error estableciendo conección con el servidor: "+HOST
                    + "\nPor favor vuelva a intentarlo."
                    + "\nSi el error persiste comuníquese con el área de soporte."
                    + "\nGracias.\nERROR: "+e.getLocalizedMessage());
            return null;
        }
    }

    public Connection closeConexion(){
        if (cn != null) {
                try {
                    cn.close();
                } catch (SQLException e) { 
                    System.out.println("EROOR: "+e);
                }
            }
        System.out.println("Conexión cerrada");
        return null;
    }
    

    public static void main (String []args){
        DbCon dbcon = new DbCon();
        Connection cn = dbcon.getConexion();
        //dbcon.closeConexion();
    }

}
