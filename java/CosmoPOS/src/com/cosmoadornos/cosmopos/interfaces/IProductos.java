/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmoadornos.cosmopos.interfaces;

import com.cosmoadornos.cosmopos.model.Producto;
import java.util.ArrayList;

/**
 *
 * @author b41n
 */
public interface IProductos {
    
    //public boolean guardarProducto(Producto producto);
    
    public boolean guardarProductos(ArrayList<Producto> producto, int compraId);
    
    public ArrayList<Producto> buscarProductos();
    
}
