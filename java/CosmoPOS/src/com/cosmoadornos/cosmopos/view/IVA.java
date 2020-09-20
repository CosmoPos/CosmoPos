/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmoadornos.cosmopos.view;

/**
 *
 * @author b41n
 */
public class IVA {
    
    //  Algoritmo usado para identificar en cual variable se aplica el redondéo
    
    public static void main(String [] args){
        
        float tasa = 1.19f;
        
        float valor;
        float iva;
        float sub;
        
        valor = 2493;
        
        sub = valor/tasa;
        int subI = (int ) sub;
        
        iva = valor-sub;
        int ivaI = (int) iva;
        
        float val = sub-subI;
        
        if (val >.50){
            subI+=1;
            System.out.println("Redondeo en el subtotal: $"+subI);
        }else{
            ivaI+=1;
            System.out.println("Redondeo en el IVA: $"+ivaI);
        }
        
        String factura="---------------------------"
                + "\nINICIO DE LA VENTA"
                + "\n---------------------------"
                + "\nLos datos de la venta son los siguientes:"
                + "\nValor del producto: "+ valor
                + "\nSub Total: "+subI
                + "\nValor del IVA: "+ivaI
                + "\n---------------------------"
                + "\nFIN DE LA VENTA"
                + "\n---------------------------";
        System.out.println(factura);
    }
}
