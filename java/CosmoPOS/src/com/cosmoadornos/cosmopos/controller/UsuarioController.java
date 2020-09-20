/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmoadornos.cosmopos.controller;

import com.cosmoadornos.cosmopos.dao.UsuarioDAO;
import com.cosmoadornos.cosmopos.model.Usuario;
import java.util.ArrayList;

/**
 *
 * @author b41n
 */
public class UsuarioController {
    UsuarioDAO dao = new UsuarioDAO();
    
    public ArrayList<Usuario> obtenerUsuarios(){
        ArrayList<Usuario> usuarios = dao.obtenerUsuarios();
        return usuarios;
    }
    
}
