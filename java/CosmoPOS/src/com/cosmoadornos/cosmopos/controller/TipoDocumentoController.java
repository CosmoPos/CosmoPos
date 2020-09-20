/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmoadornos.cosmopos.controller;

import com.cosmoadornos.cosmopos.dao.TipoDocumentoDAO;
import com.cosmoadornos.cosmopos.model.TipoDocumento;
import java.util.ArrayList;

/**
 *
 * @author b41n
 */
public class TipoDocumentoController {
    
    ArrayList<TipoDocumento> lista;
    TipoDocumentoDAO dao = new TipoDocumentoDAO();
    
    public ArrayList<TipoDocumento> obtenerTiposDocumentos(){
        lista = dao.obtenerTiposDocumentos();
        return lista;
    }
    
}
