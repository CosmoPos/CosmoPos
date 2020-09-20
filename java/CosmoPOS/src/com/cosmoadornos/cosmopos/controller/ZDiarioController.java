/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmoadornos.cosmopos.controller;

import com.cosmoadornos.cosmopos.dao.ZDiarioDAO;
import com.cosmoadornos.cosmopos.model.ZDiario;
import com.itextpdf.text.DocumentException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author b41n
 */
public class ZDiarioController {
    ZDiarioDAO dao = new ZDiarioDAO();
    
    
    public static boolean validarZDiario(){
        return ZDiarioDAO.validarZDiario();
    }
    
    public static boolean iniciarNumeracion(String fecha){
        return ZDiarioDAO.iniciarNumeracion(fecha);
    }
    
    public static int obtenerUltimoZDiario(){
        return ZDiarioDAO.obtenerUltimoZDiario();
    }
    
    public boolean guardarZDiario(ZDiario zDiario){
        return dao.guardarZDiario(zDiario);
    }
    
    public String[] obtenerRegistros(int zDiarioId){
        return dao.obtenerRegistros(zDiarioId);
    }
    
    public int obtenerTotalRegistros(int zDiarioId){
        return dao.obtenerTotalRegistros(zDiarioId);
    }
    
    public boolean crearTicketZDiarioPDF(String ruta, int nroZ){
        try {
            return dao.crearTicketZDiarioPDF(ruta, nroZ);
        } catch (DocumentException | FileNotFoundException ex) {
            Logger.getLogger(ZDiarioController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ZDiarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
}
