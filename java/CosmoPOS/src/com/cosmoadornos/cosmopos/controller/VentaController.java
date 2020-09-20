/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmoadornos.cosmopos.controller;

import com.cosmoadornos.cosmopos.dao.VentaDAO;
import com.cosmoadornos.cosmopos.model.Venta;
import java.util.ArrayList;

/**
 *
 * @author b41n
 */
public class VentaController {
    
    VentaDAO dao = new VentaDAO();
    ArrayList<Venta> ventas;
    //Venta venta;
    
    public boolean registrarVentas(int nroFactura, ArrayList<Venta> ventas){
        return dao.registrarVentas(nroFactura, ventas);
    }
    
    public ArrayList<Venta> buscarVentasPOS(int nroFactura){
        ventas = dao.buscarVentasPOS(nroFactura);
        return ventas;
    }
    
    public ArrayList<Venta> buscarVentasFEL(int nroFactura){
        ventas = dao.buscarVentasFEL(nroFactura);
        return ventas;
    }
    
    /*
    
    FALTA REALIZAR IMPLEMENTACIÓN
    public ArrayList<Venta> buscarVentasPOS(String fechaDesde, String fechaHasta){
        ventas = new ArrayList();
        // buscar Ventas POS
        ventas = dao.buscarVentasPOS(fechaDesde, fechaHasta);
        return ventas;
    }
    
    
    
    public ArrayList<Venta> buscarVentasFEL(String fechaDesde, String fechaHasta){
        ventas = new ArrayList();
        // buscar Ventas POS
        ventas = dao.buscarVentasFEL(fechaDesde, fechaHasta);
        return ventas;
    }
    */
}
