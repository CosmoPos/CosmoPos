/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmoadornos.cosmopos.controller;

import com.cosmoadornos.cosmopos.dao.ProductoDAO;
import com.cosmoadornos.cosmopos.model.Producto;
import java.util.ArrayList;

/**
 *
 * @author b41n
 */
public class ProductoController {
    ProductoDAO dao = new ProductoDAO();
    
    public int obtenerUltimoProducto(){
        return dao.obtenerUltimoProducto();
    }
    
    public boolean guardarProductos(ArrayList<Producto> productos, int compraId){
        return dao.guardarProductos(productos, compraId);
    }
    public boolean guardarProducto(Producto producto){
        return dao.guardarProducto(producto);
    }
    
    public ArrayList<Producto> buscarProductos(){
        ArrayList<Producto> productos = dao.buscarProductos();
        return productos;
    }
    
    
    public ArrayList<Producto> buscarProductosCombo(int proveedorId){
        ArrayList<Producto> productos = dao.buscarProductosCombo(proveedorId);
        return productos;
    }
    
    
}
