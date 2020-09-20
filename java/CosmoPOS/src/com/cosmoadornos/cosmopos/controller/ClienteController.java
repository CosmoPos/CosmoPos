/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmoadornos.cosmopos.controller;

import com.cosmoadornos.cosmopos.dao.ClienteDAO;
import com.cosmoadornos.cosmopos.model.Cliente;
import java.util.ArrayList;

/**
 *
 * @author b41n
 */

public class ClienteController {
    Cliente cliente;
    ClienteDAO cliDAO = new ClienteDAO();
    ArrayList<Cliente> clientes;
    
    public boolean guardarCliente(Cliente cliente){
        //cliente = new Cliente();
        return cliDAO.guardarCliente(cliente);
    }
    
    public Cliente buscarCliente(String documento ){
        cliente =cliDAO.buscarCliente(documento);
        return cliente;
    }
    
    public ArrayList<Cliente> buscarClientes(){
        clientes = cliDAO.buscarClientes();
        return clientes;
    }
    
}
