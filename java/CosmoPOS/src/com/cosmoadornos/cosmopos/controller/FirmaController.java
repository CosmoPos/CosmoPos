/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmoadornos.cosmopos.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.swing.JOptionPane;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.xml.security.signature.XMLSignature;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author b41n
 */
public class FirmaController {
    private String firma;
    
    /*
    File archivoXML;
    public FirmaController(File archivoXML){
        this.archivoXML = archivoXML;
    }
    */

    public String firmarArchivoXML(File archivoXML){
        
        try{
            //Parametros del keystore
            String keyStoreType = "JKS";  // Tipo del almacen de llaves
            
            String keyStoreFile = "/home/b41n/Escritorio/MiAlmacen.jks"; // ruta del almacén
            String keyStorePass = "miAlmacen"; // llave del almacén
            String keyAlias = "miAlmacen";// Nombre
            String keyPass = "miAlmacen";// Clave
            String certificateAlias = "miAlmacen";
            
            //Asignamos la ruta del archivo que vamos a firmar.
            File signatureFile = new File(archivoXML.getAbsolutePath());
            //File signatureFile = new File(archivoXML.getAbsolutePath());
            ///////////////////Creación del certificado//////////////////////////////
            
            // Inicializamos el almacen de llaves con el valor por defecto "JKS"
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            
            // Ubicación del almacén de llaves
            FileInputStream fileInputStream = new FileInputStream(keyStoreFile);
            
            // Cargamos y Abrimos el almacen de llaves
            //load(InputStream, char[]) 
            keyStore.load(fileInputStream, keyStorePass.toCharArray());
            
            PrivateKey privateKey = (PrivateKey) keyStore.getKey(keyAlias, keyPass.toCharArray());
            // Validamos que la clave pública exista
            if (privateKey == null) {
                throw new RuntimeException("Private key is null");
            }
            
            //
            X509Certificate x509Certificate = (X509Certificate) keyStore.getCertificate(certificateAlias);
            
            // ver que mierda es esto, cascara de redundancia ?
            //El baseURI es la URI que se utiliza para anteponer a URIs relativos
            String BaseURI = signatureFile.toURI().toURL().toString();
            
            
            // Librería para manejar nodos en XML
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            //Firma XML genera espacio para los nombres o tag
            documentBuilderFactory.setNamespaceAware(true);
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            
            
            
            
            File file = new File("/home/b41/Escritorio/FRAS/2.xml");
            //File file = new File("/home/b41/Escritorio/FRAS/2.xml");
            //Node nodo = document.createTextNode("");
            
            
            
            FileReader fileReader = new FileReader(file);
            //FileReader fileReader = new FileReader(archivoXML);
            
            String txt = fileReader.toString();
            
            Document document = documentBuilder.newDocument();
            JOptionPane.showMessageDialog(null,"Texto :"+txt);
            Node nodo = document.createTextNode(txt);
            
            //2do grupo
            Element UBLExtension = document.createElementNS("", "ext:UBLExtension");
            
            document.appendChild(UBLExtension);
            
            Element ExtensionContent = document.createElementNS("", "ext:ExtensionContent");
            
            XMLSignature sig = new XMLSignature(document, BaseURI, XMLSignature.ALGO_ID_SIGNATURE_RSA);
            
            ExtensionContent.appendChild(sig.getElement());

            
            String firm = (sig.getElement()).toString();
            JOptionPane.showMessageDialog(null,"FIRMA :"+ firm);
            
            Transformer tf = TransformerFactory.newInstance().newTransformer();

            FileOutputStream fileOutputStream = new FileOutputStream(signatureFile);
            
            return firma;
        }catch(IOException | RuntimeException | KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException | CertificateException | ParserConfigurationException | TransformerConfigurationException | XMLSecurityException e){
            String error;
            error = e.getMessage();
            return error;
        }
    }
    
    
    
    public static void main(String[]srgs){
        FirmaController firmador = new FirmaController();
        
        File archivoXML = new File("");
        
        String firma;
        
        firma = firmador.firmarArchivoXML(archivoXML);
        
        System.out.println(firma);
        //firmador.firmarArchivoXML(archivoXML);
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

}