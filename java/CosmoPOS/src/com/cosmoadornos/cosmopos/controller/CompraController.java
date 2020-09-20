/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmoadornos.cosmopos.controller;

import com.cosmoadornos.cosmopos.dao.CompraDAO;
import com.cosmoadornos.cosmopos.model.Compra;

/**
 *
 * @author b41n
 */
public class CompraController {
    
    CompraDAO dao = new CompraDAO();
    
    public boolean guardarCompra(Compra compra){
        return dao.guardarCompra(compra);
    }
    
    public int buscarCompraId(int proveedorId, int facturaNro){
        return dao.buscarCompraId(proveedorId, facturaNro);
    }
    
}
