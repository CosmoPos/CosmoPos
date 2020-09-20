/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmoadornos.cosmopos.model;

import java.util.ArrayList;

/**
 *
 * @author b41n
 */
public class Factura {
    
    int id;
    int factNum;
    float totalSinImp;
    float totalImp;
    float totalMasImp;
    Empresa empresa;
    //Empresa clienteEmpresa;
    String facturaFecha;
    String facturaHora;
    Cliente cliente;
    //ArrayList<Producto> productos;
    ArrayList<Venta> ventas;
    
    /*
    public Factura(){
        
    }
    */
    
    public Factura(int factNum, Empresa empresa, String facturaFecha, String facturaHora, Cliente cliente /*ArrayList<Producto> productos*/, ArrayList<Venta> ventas, float totalSinImp, float totalImp, float totalMasImp){
        this.empresa = empresa;
        this.facturaFecha = facturaFecha;
        this.facturaHora = facturaHora;
        this.cliente = cliente;
        //this.productos = productos;
        this.ventas = ventas;
        this.factNum = factNum;
        this.totalSinImp = totalSinImp;
        this.totalImp = totalImp;
        this.totalMasImp = totalMasImp;
    }
    
    // Este método se puede usar para imprimir la factura en un formato simple
    public void imprimirFactura(){
        System.out.println("********************");
        System.out.println("********************");
        System.out.println("********************");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFactNum() {
        return factNum;
    }

    public void setFactNum(int factNum) {
        this.factNum = factNum;
    }

    public float getTotalSinImp() {
        return totalSinImp;
    }

    public void setTotalSinImp(float totalSinImp) {
        this.totalSinImp = totalSinImp;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public String getFacturaFecha() {
        return facturaFecha;
    }

    public void setFacturaFecha(String facturaFecha) {
        this.facturaFecha = facturaFecha;
    }

    public String getFacturaHora() {
        return facturaHora;
    }

    public void setFacturaHora(String facturaHora) {
        this.facturaHora = facturaHora;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
/*
    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }
*/
    public ArrayList<Venta> getVentas() {
        return ventas;
    }

    public void setVentas(ArrayList<Venta> ventas) {
        this.ventas = ventas;
    }

    public float getTotalImp() {
        return totalImp;
    }

    public void setTotalImp(float totalImp) {
        this.totalImp = totalImp;
    }

    public float getTotalMasImp() {
        return totalMasImp;
    }

    public void setTotalMasImp(float totalMasImp) {
        this.totalMasImp = totalMasImp;
    }
    

}