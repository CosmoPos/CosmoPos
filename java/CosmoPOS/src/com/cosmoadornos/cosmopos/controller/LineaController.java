/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmoadornos.cosmopos.controller;

import com.cosmoadornos.cosmopos.dao.LineaDAO;
import com.cosmoadornos.cosmopos.model.Linea;
import java.util.ArrayList;

/**
 *
 * @author b41n
 */
public class LineaController {
    ArrayList<Linea> listaLineas;
    LineaDAO dao = new LineaDAO();
    
    public ArrayList<Linea> obtenerListaLineas(){
        return dao.obtenerListaLineas();
    }
}
