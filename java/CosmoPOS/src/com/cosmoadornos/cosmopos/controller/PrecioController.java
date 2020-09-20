/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmoadornos.cosmopos.controller;

import com.cosmoadornos.cosmopos.dao.PrecioDAO;
import com.cosmoadornos.cosmopos.model.Precio;
import java.util.ArrayList;

/**
 *
 * @author b41n
 */
public class PrecioController {
    PrecioDAO dao = new PrecioDAO();
    public boolean guardarPrecios(ArrayList<Precio> precios, int compraId){
        return dao.guardarPrecios(precios, compraId);
    }
    
}
