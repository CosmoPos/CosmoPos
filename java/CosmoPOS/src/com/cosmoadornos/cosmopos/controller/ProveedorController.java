/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmoadornos.cosmopos.controller;

import com.cosmoadornos.cosmopos.dao.ProveedorDAO;
import com.cosmoadornos.cosmopos.model.Proveedor;
import java.util.ArrayList;

/**
 *
 * @author b41n
 */
public class ProveedorController {
    ProveedorDAO proDAO= new ProveedorDAO();
    
    public ArrayList<Proveedor> buscarProveedores(){
        ArrayList<Proveedor> proveedores = proDAO.buscarProveedores();
        return proveedores;
    }
    
    public ArrayList<Proveedor> buscarProveedoresCombo(){
        ArrayList<Proveedor> proveedores = proDAO.buscarProveedoresCombo();
        return proveedores;
    }
    
    public boolean guardarProveedor(Proveedor proveedor){
        return proDAO.guardarProveedor(proveedor);
    }
    
    public static void main(String [] args){
        ProveedorController pro = new ProveedorController();
        ArrayList<Proveedor> provs = pro.buscarProveedores();
        
        for(int i = 0; i <= provs.size()-1 ; i++){
            System.out.println(provs.get(i).getProveedorNombre());
            System.out.println(provs.get(i).getCiudad());
            
        }
    }
    
}
