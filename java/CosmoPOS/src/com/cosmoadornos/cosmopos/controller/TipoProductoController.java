/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmoadornos.cosmopos.controller;

import com.cosmoadornos.cosmopos.dao.TipoProductoDAO;
import com.cosmoadornos.cosmopos.model.TipoProducto;
import java.util.ArrayList;

/**
 *
 * @author b41n
 */
public class TipoProductoController {
    
    ArrayList<TipoProducto> tipos;
    TipoProductoDAO dao = new TipoProductoDAO();
    
    public ArrayList<TipoProducto> buscarTiposProducto(){
        tipos = dao.buscarTiposProducto();
        return tipos;
    }
    
    
    public boolean guardarTipoProducto(){
        return dao.guardarTipoProducto();
    }
    
}
