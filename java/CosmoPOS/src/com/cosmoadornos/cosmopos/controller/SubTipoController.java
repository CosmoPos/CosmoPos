/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmoadornos.cosmopos.controller;

import com.cosmoadornos.cosmopos.dao.SubTipoDAO;
import com.cosmoadornos.cosmopos.model.SubTipo;
import java.util.ArrayList;

/**
 *
 * @author b41n
 */
public class SubTipoController {
    ArrayList<SubTipo> subTipos;
    SubTipoDAO dao = new SubTipoDAO();
    
    public ArrayList<SubTipo> buscarSubTipos(){
        subTipos = dao.buscarSubTipos();
        return subTipos;
    }
    
}
