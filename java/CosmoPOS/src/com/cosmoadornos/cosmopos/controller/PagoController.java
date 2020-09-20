/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmoadornos.cosmopos.controller;

import com.cosmoadornos.cosmopos.dao.PagoDAO;
import com.cosmoadornos.cosmopos.model.Pago;
import com.cosmoadornos.cosmopos.model.PagoForma;
import com.cosmoadornos.cosmopos.model.PagoMedio;
import java.util.ArrayList;

/**
 *
 * @author b41n
 */
public class PagoController {
    PagoDAO dao = new PagoDAO();
    
    public ArrayList<PagoForma> getFormaPago(){
        return dao.getFormaPago();
    }
    
    public ArrayList<PagoMedio> getMedioPago(){
        return dao.getMedioPago();
    }
    
}
