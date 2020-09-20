/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmoadornos.cosmopos.controller;

import java.math.BigInteger; 
import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException;
import com.cosmoadornos.cosmopos.dao.DianDAO;
import com.cosmoadornos.cosmopos.dao.EmpresaDAO;
import com.cosmoadornos.cosmopos.model.ArchivoXML;
import com.cosmoadornos.cosmopos.model.Cliente;
import com.cosmoadornos.cosmopos.model.Dian;
import com.cosmoadornos.cosmopos.model.Empresa;
import com.cosmoadornos.cosmopos.model.Factura;
import com.cosmoadornos.cosmopos.model.FacturaElectronica;
import com.cosmoadornos.cosmopos.model.PagoForma;
import com.cosmoadornos.cosmopos.model.PagoMedio;
import com.cosmoadornos.cosmopos.model.ResponsabilidadFiscal;
import com.cosmoadornos.cosmopos.model.Venta;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import org.xml.sax.SAXException;
//import javax.swing.JOptionPane;

/**
 *
 * @author b41n
 */
public class ArchivoXMLController {
    private static final String XML_UNSIGNED ="/home/b41n/Escritorio/FRAS/unsigned.xml";
    private String errorXML;
    private String errorFile;
    //private final ArchivoXML xml;
    private final FacturaElectronica fel;
    private final Empresa empresa;
    //private final Dian dian;
    private final Empresa empresaCliente;
    //private ArrayList<Producto> productos;
    private final ArrayList<Venta> ventas;
    private  String ID;
    private String CUFE;
    
    public ArchivoXMLController(ArchivoXML archivoXML){
        this.fel = archivoXML.getFacturaElectronica();
        this.empresa = archivoXML.getFacturaElectronica().getFactura().getEmpresa();
        //this.dian = archivoXML.getFacturaElectronica().getFactura().getDian();
        this.empresaCliente = archivoXML.getFacturaElectronica().getEmpresaCliente();
        //this.productos = archivoXML.getFacturaElectronica().getFactura().getVentas();
        this.ventas = archivoXML.getFacturaElectronica().getFactura().getVentas();
        //System.out.println(archivoXML.getFacturaElectronica().getDian().getPrefix());
        System.out.println(archivoXML.getFacturaElectronica().getFacturaNro());
        //this.ID ="";
        this.ID = archivoXML.getFacturaElectronica().getFactura().getEmpresa().getDian().getPrefix()+(
                archivoXML.getFacturaElectronica().getFactura().getEmpresa().getDian().getStartRange()
                +archivoXML.getFacturaElectronica().getFactura().getFactNum()
                );
        //this.xml = archivoXML;
    }
    
    private String ecribirArchivoXML(){
        try{
            // VARIABLE QUE CONTIENE LA FACTURA EN FORMATO XML
            String textoXML;
            // FUNCIÓN QUE DEFINE LOS NAMESPACES
            textoXML =xmlNameSpaces();
            // ABRIMOS LA PRIMERA EXTENSIÓN CONTENIDA EN EL INVOICE
            textoXML+=openUBLExtensions();
            textoXML+=openUBLExtension();
            textoXML+=openDianExtension();
            textoXML+=InvoiceControl();
            textoXML+=generarQR();
            textoXML+=closeDianExtension();
            textoXML+=closeUBLExtension();
            //textoXML+=insertarFirmaDigital();
            textoXML+=closeUBLExtensions();
            textoXML+=InvoiceInfo();
            textoXML+=AccountingSupplierParty();
            textoXML+=AccountingCustomerParty();
            textoXML+=PaymentMeans();
            textoXML+=TaxTotal();
            
            textoXML+=LegalMonetaryTotal();
            
            textoXML+=InvoiceLine();
            
            textoXML+=closeInvoice();
            return textoXML;
            
        }catch(Exception e){
            System.out.println("Error construyendo el texto del archivo");
        }
        return null;
    }
    
    public static boolean validarArchivoXML(File xmlFile){
        
        File ValidationFile = new File("/home/b41n/Escritorio/Files/herramientas/CAJA/XSD/maindoc/UBL-Invoice-2.1.xsd");
        boolean flag = true;
        if(!xmlFile.getName().equals("")){
            try{
                validateXML(xmlFile, ValidationFile);
                return flag;
            }catch(IOException | SAXException e){
                flag = false;
                System.out.println("Error validando el archivo.\nError: "+e.getMessage());
                return flag;
            }
        }else{
            System.out.println("Archivo valido: "+flag);
            return flag;
        }
    }
    
    public String firmarArchivoXML(){
        try{
        String textoXML;
            textoXML =xmlNameSpaces();
            textoXML+=openUBLExtensions();
            textoXML+=openUBLExtension();
            textoXML+=openDianExtension();
            textoXML+=InvoiceControl();
            textoXML+=generarQR();
            textoXML+=closeDianExtension();
            textoXML+=closeUBLExtension();
            textoXML+=insertarFirmaDigital();
            textoXML+=closeUBLExtensions();
            textoXML+=InvoiceInfo();
            textoXML+=AccountingSupplierParty();
            textoXML+=AccountingCustomerParty();
            textoXML+=PaymentMeans();
            textoXML+=TaxTotal();
            textoXML+=LegalMonetaryTotal();
            textoXML+=InvoiceLine();
            textoXML+=closeInvoice();
            return textoXML;
        }catch(Exception e){
            errorXML = "Error construyendo el formato xml.\nERROR: "+e.getMessage();
            return errorXML;
        }
    }

    public static File crearArchivoXML(String ruta, String txt){
        String errorFile;
        try{
            File xmlFile = new File(ruta);
            xmlFile.createNewFile();
            try (FileWriter fileWriter = new FileWriter(xmlFile)) {
                fileWriter.write(txt);
                return xmlFile;
            }catch(FileNotFoundException e){
                errorFile ="Error escribiendo el archivo: "+ruta;
                System.out.println(errorFile);
                return null;
            }
        }catch(IOException e){
            errorFile ="Error creando el archivo "+ruta+"\n"+e.getMessage();
            System.out.println(errorFile);
            return null;
        }
    }

    public void imprimirTextoXML(){
        msg(ecribirArchivoXML());
    }
    
    private String xmlNameSpaces(){
        String txt;
        try{
            txt ="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>";
            txt+="\n<Invoice \n";
            txt+="\txmlns=\"urn:oasis:names:specification:ubl:schema:xsd:Invoice-2\" \n";
            txt+="\txmlns:cac=\"urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2\" \n";
            txt+="\txmlns:cbc=\"urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2\"  \n";
            txt+="\txmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" \n";
            txt+="\txmlns:ext=\"urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2\" \n";
            txt+="\txmlns:sts=\"dian:gov:co:facturaelectronica:Structures-2-1\" \n";
            txt+="\txmlns:xades=\"http://uri.etsi.org/01903/v1.3.2#\" \n";
            txt+="\txmlns:xades141=\"http://uri.etsi.org/01903/v1.4.1#\" \n";
            txt+="\txmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" \n";
            
            //  VALIDACIÓN DEL DOCUMENTO LOCALMENTE AMBIENTE DE PRUEBAS
           // txt+="\txsi:schemaLocation=\"urn:oasis:names:specification:ubl:schema:xsd:Invoice-2     file:///home/b41n/Escritorio/herramientas/CAJA/XSD/maindoc/UBL-Invoice-2.1.xsd>\n";
            //  VALIDACIÓN DEL DOCUMENTO LOCALMENTE AMBIENTE DE PRODUCCIÓN
            txt+="\txsi:schemaLocation=\"urn:oasis:names:specification:ubl:schema:xsd:Invoice-2     http://docs.oasis-open.org/ubl/os-UBL-2.1/xsd/maindoc/UBL-Invoice-2.1.xsd\">\n";
            return txt;
        }catch(Exception e){
            errorFile ="Error generando el bloque: "+"openInvoice";
            return errorFile;
        }
    }
    
    private String closeInvoice(){
        String txt ="";
        try{
            txt+="</Invoice>";
            return txt;
        }catch(Exception e){
            System.out.println("Error cerrando el bloque: "+"Invoice");
            return null;
        }
    }
    
    private String openUBLExtensions(){
        String txt ="";
        try{
            txt+="\t<ext:UBLExtensions>\n";
            return txt;
        }catch(Exception e){
            System.out.println("Error abriendo el bloque: "+"xmlNameSpaces");
        }
        return null;
    }
    
    private String closeUBLExtensions(){
        String txt ="";
        try{
            txt+="\t</ext:UBLExtensions>\n";
            return txt;
        }catch(Exception e){
            System.out.println("Error generando el bloque: "+"</ext:UBLExtensions>");
        }
        return null;
    }
    
    private String openUBLExtension(){
        String txt ="";
        try{
            txt+="\t\t<ext:UBLExtension>\n";
            txt+="\t\t\t<ext:ExtensionContent>\n";
            return txt;
        }catch(Exception e){
            System.out.println("Error generando el bloque: "+"<ext:UBLExtension>");
        }
        return null;
    }
    
    private String closeUBLExtension(){
        String txt ="";
        try{
            txt+="\t\t\t</ext:ExtensionContent>\n";
            txt+="\t\t</ext:UBLExtension>\n";
            return txt;
        }catch(Exception e){
            System.out.println("Error generando el bloque: "+"</ext:UBLExtension>");
        }
        return null;
    }
    
    private String openDianExtension(){
        String txt ="";
        try{
            txt+="\t\t\t\t<sts:DianExtensions>\n";
            return txt;
        }catch(Exception e){
            System.out.println("Error generando el bloque: "+"openDianExtension");
        }
        return null;
    }
    
    private String InvoiceControl(){
        String txt ="";
        try{
            txt+="\t\t\t\t\t<sts:InvoiceControl>\n";
            txt+="\t\t\t\t\t\t<sts:InvoiceAuthorization>"+empresa.getDian().getInvoiceAuthorization()+"</sts:InvoiceAuthorization>\n";
            txt+="\t\t\t\t\t\t<sts:AuthorizationPeriod>\n";
            txt+="\t\t\t\t\t\t\t<cbc:StartDate>"+empresa.getDian().getStartDate()+"</cbc:StartDate>\n";
            txt+="\t\t\t\t\t\t\t<cbc:EndDate>"+empresa.getDian().getEndDate()+"</cbc:EndDate>\n";
            txt+="\t\t\t\t\t\t</sts:AuthorizationPeriod>\n";
            txt+="\t\t\t\t\t\t<sts:AuthorizedInvoices>\n";
            txt+="\t\t\t\t\t\t\t<sts:Prefix>"+empresa.getDian().getPrefix()+"</sts:Prefix>\n";
            txt+="\t\t\t\t\t\t\t<sts:From>"+empresa.getDian().getStartRange()+"</sts:From>\n";
            txt+="\t\t\t\t\t\t\t<sts:To>"+empresa.getDian().getEndRange()+"</sts:To>\n";
            txt+="\t\t\t\t\t\t</sts:AuthorizedInvoices>\n";
            txt+="\t\t\t\t\t</sts:InvoiceControl>\n";
            txt+="\t\t\t\t\t<sts:InvoiceSource>\n";
            txt+="\t\t\t\t\t\t<cbc:IdentificationCode listAgencyID=\"6\" listAgencyName=\"United Nations Economic Commission for Europe\" listSchemeURI=\"urn:oasis:names:specification:ubl:codelist:gc:CountryIdentificationCode-2.1\">CO</cbc:IdentificationCode>\n";
            txt+="\t\t\t\t\t</sts:InvoiceSource>\n";
            txt+="\t\t\t\t\t<sts:SoftwareProvider>\n";
            txt+="\t\t\t\t\t\t<sts:ProviderID schemeAgencyID=\"195\" schemeAgencyName=\"CO, DIAN (Dirección de Impuestos y Aduanas Nacionales)\" schemeID=\"4\" schemeName=\"31\">"+empresa.getCompanyNIT()+"</sts:ProviderID>\n";
            txt+="\t\t\t\t\t\t<sts:SoftwareID schemeAgencyID=\"195\" schemeAgencyName=\"CO, DIAN (Dirección de Impuestos y Aduanas Nacionales)\">"+empresa.getDian().getSoftwareId()+"</sts:SoftwareID>\n";
            txt+="\t\t\t\t\t</sts:SoftwareProvider>\n";
            String SoftwareSecurityCode = empresa.getDian().getSoftwareId()+empresa.getDian().getPin();
            SoftwareSecurityCode = stringToSha384(SoftwareSecurityCode);
            txt+="\t\t\t\t\t<sts:SoftwareSecurityCode schemeAgencyID=\"195\" schemeAgencyName=\"CO, DIAN (Dirección de Impuestos y Aduanas Nacionales)\">\n";
            txt+="\t\t\t\t\t\t"+SoftwareSecurityCode+"\n";
            txt+="\t\t\t\t\t</sts:SoftwareSecurityCode>\n";
            txt+="\t\t\t\t\t<sts:AuthorizationProvider>\n";
            txt+="\t\t\t\t\t\t<sts:AuthorizationProviderID schemeAgencyID=\"195\" schemeAgencyName=\"CO, DIAN (Dirección de Impuestos y Aduanas Nacionales)\" schemeID=\"4\" schemeName=\"31\">"+empresa.getDian().getAuthorizationProviderID()+"</sts:AuthorizationProviderID>\n";
            txt+="\t\t\t\t\t</sts:AuthorizationProvider>\n";
            return txt;
        }catch(Exception e){
            txt = "Error generando el bloque: "+"InvoiceControl"+e.getMessage();
            return txt;
        }
    }
    
    private String closeDianExtension(){
        String txt = "";
        try{
            txt+="\t\t\t\t</sts:DianExtensions>\n";
            return txt;
        }catch(Exception e){
            System.out.println("Error generando el bloque: "+"openDianExtension");
        }
        return null;
    }
    
    /**
     * Realizar validación del tipo de impuesto
    */
    
    private String calcularCUFE(){
        DecimalFormat df = new DecimalFormat("0.00");
        String codImp1 = "01";  // Traer de la base de datos
        //double valImp1 = fel.getTotalImp();// Total del IVA con 2 decimales
        String codImp2 = "04";// Impuesto Nacional Al Consumo
        double valImp2 = 0;
        String codImp3 = "03";// ICA
        float valImp3 = 0.00f;
        String v2 = df.format(valImp2);
        v2 = v2.replace(",", ".");
        String v3 = df.format(valImp3);
        v3 = v3.replace(",", ".");
        String taxAmount = df.format(fel.getFactura().getTotalImp());
        taxAmount = taxAmount.replace(",", ".");
        String payableAmount = df.format(fel.getFactura().getTotalMasImp());
        payableAmount = payableAmount.replace(",", ".");
        String txt;
            String cufe;
        try{
            /**
             * @IMPORTANTE
             *    ! este CUFE solo es para pruebas legibles.
             *    ! El CUFE funcional se concatena sin saltos de linea y se le aplica el algoritmo SHA-384
             */
            
              /*cufe =
                     "FACTURA  : "+ID+"\n"//Número de factura.(prefijo concatenado con el número de la factura)
                    +"FECHA    : "+ fel.getFacturaFecha()+"\n" // Fecha de factura.
                    +"HORA     : "+ fel.getFacturaHora()+"\n"  // Hora de la factura incluyendo GMT.
                    +"PRECIO   : "+ fel.getTotalSinImp()+ "\n"// Valor de la Factura sin Impuestos, con punto decimal, 
                    //con decimales truncados a dos (2) dígitos, sin separadores de miles, ni símbolo pesos.
                    +"COD IVA  : "+ codImp1+" \n" // 01 Este valor es fijo.
                    +"IVA      : "+ taxAmount+ "\n"// Valor impuesto 01 - IVA, con punto decimal, con decimales truncados 
                    //a dos (2) dígitos, sin separadores de miles, ni símbolo pesos.. Si no esta referenciado el impuesto
                    // 01 – IVA este valor se representa con 0.00
                    +"COD INC  : "+ codImp2+" \n" // 04 Este valor es fijo.
                    +"INC      : "+ v2+" \n"      // Valor impuesto 04 - Impuesto Nacional al Consumo, con punto 
                    //decimal, con decimales truncados a dos (2) dígitos, sin separadores de miles, ni símbolo pesos. 
                    //Si no esta referenciado el impuesto 04- INC este valor se representa con 0.00
                    +"COD ICA  : "+ codImp3+" \n" // 03 Este valor es fijo.
                    +"ICA      : "+ v3+"\n"       //Valor impuesto 03 - ICA, con punto decimal, con decimales 
                    //truncados a dos (2) dígitos, sin separadores de miles, ni símbolo pesos. Si no esta 
                    //referenciado el impuesto 03 - ICA este valor se representa con 0.00
                    +"TOTAL    : "+ payableAmount+"\n" // Valor Total, con punto decimal, con decimales truncados 
                    //  a dos (2) dígitos, sin separadores de miles, ni símbolo pesos.
                    +"EMISOR   : "+ empresa.getCompanyNIT()+"\n"// NIT del Facturador Electrónico sin puntos 
                    //  ni guiones, sin digito de verificación.
                    +"ADQUIERE : "+ cliente.getDocumento()+"\n"//Número de identificación del adquirente sin 
                    //  puntos ni guiones, sin digito de verificación.
                    +"TOKEN    :"+ dian.getLlaveTecnica()+"\n"// Clave técnica del rango de facturación.
                    +"AMBIENTE :"+ dian.getProfileExecutionID()+"\n"// Número de identificación del ambiente utilizado 
                    //  por el contribuyente para emitir la factura 1 ambiente de Producción, 2 ambiente de Pruebas
                ;*/
                // Licencia Apache 2.0 https://github.com/CosmoPos/CosmoPos/Examples/CUFE
             // FUNCIONAL COMENTARIADO PARA PRUEBAS ÁGILES
            cufe =ID+""
                    + fel.getFactura().getFacturaFecha()+""
                    + fel.getFactura().getFacturaHora()+""
                    + taxAmount+ ""// Total sin impuestos""
                    //+ fel.getTotalSinImp()+ "\n"// Total sin impuestos""
                    + codImp1+""
                    + taxAmount+""
                    + codImp2+""
                    + v2+""
                    + codImp3+""
                    + v3+""
                    + payableAmount+"" // <>
                    + empresa.getCompanyNIT()+""
                    + empresaCliente.getCompanyNIT()+""
                    + empresa.getDian().getLlaveTecnica()+""
                    + empresa.getDian().getProfileExecutionID()+""// Hambiente del software
            ;
            cufe = stringToSha384(cufe);
            this.CUFE = cufe;
            //this.CUFE=cufe;
            //JOptionPane.showMessageDialog(null, "CUFE: "+CUFE);
            //txt+="CUFE ANTES DEL SHA 384:\n"+cufe+"";
            //cufe = stringToSha384(cufe);
            //|txt+="CUFE DESPUES DEL SHA:\n"+cufe+"";
            txt = cufe;
            return txt;
            //return cufe;
        }catch(HeadlessException e){
            txt="Error generando el bloque: "+"CUFE";
            return txt+"\n"+e.getMessage();
        }
    }
    
    private String generarQR(){
        String txt ="";
        //String URL = "https://catalogo-vpfe-hab.dian.gov.co/Document/FindDocument?documentKey="; // incluir en el modelo Dian
        try{
            calcularCUFE();
            String URL = "https://catalogo-vpfe.dian.gov.co/document/searchqr?documentkey="; // incluir en el modelo Dian
            String QRCode = "NroFactura="+ID+"\n"
                    + "\t\t\t\t\t\tNitFacturador="+empresa.getCompanyNIT()+"\n"
                    + "\t\t\t\t\t\tNitAdquiriente="+empresaCliente.getCompanyNIT()+"\n"
                    + "\t\t\t\t\t\tFechaFactura="+fel.getFactura().getFacturaFecha()+"\n"
                    + "\t\t\t\t\t\tValorTotalFactura="+fel.getFactura().getTotalMasImp()+"\n"
                    + "\t\t\t\t\t\tCUFE="+CUFE+"\n"
                    + "\t\t\t\t\t\tURL="+URL+CUFE;
            txt+="\t\t\t\t\t<sts:QRCode>\n";
            txt+="\t\t\t\t\t\t"+QRCode+"\n";
            txt+="\t\t\t\t\t</sts:QRCode>\n";
            return txt;
        }catch(Exception e){
            txt="Error generando el bloque: "+"CUFE";
            return txt+"\n"+e.getMessage();
        }
    }
    
    public String InvoiceInfo(){
        String txt = "";
        try{
            txt+="\t<cbc:UBLVersionID>UBL 2.1</cbc:UBLVersionID>\n";
            //Documento Invoice - Factura Electronica 10 = Estandar
            String CustomizationID = "10";
            txt+="\t<cbc:CustomizationID>"+CustomizationID+"</cbc:CustomizationID>\n";
            txt+="\t<cbc:ProfileID>DIAN 2.1</cbc:ProfileID>\n";
            txt+="\t<cbc:ProfileExecutionID>"+empresa.getDian().getProfileExecutionID()+"</cbc:ProfileExecutionID>\n";
            txt+="\t<cbc:ID>"+ID+"</cbc:ID>\n";
            txt+="\t<cbc:UUID schemeID=\"2\" schemeName=\"CUFE-SHA384\">\n";
            txt+="\t\t"+CUFE+"\n";
            txt+="\t</cbc:UUID>\n";
            txt+="\t<cbc:IssueDate>"+fel.getFactura().getFacturaFecha()+"</cbc:IssueDate>\n";
            //String i = fel.getFacturaHora()+"-05:00";
            txt+="\t<cbc:IssueTime>"+fel.getFactura().getFacturaHora()+"</cbc:IssueTime>\n";// -05:00 colombia
            // Tipo de factura //Factura electrónica de Venta
            String InvoiceTypeCode = "01";
            txt+="\t<cbc:InvoiceTypeCode>"+InvoiceTypeCode+"</cbc:InvoiceTypeCode>\n";
            txt+="\t<cbc:DocumentCurrencyCode listAgencyID=\"6\" listAgencyName=\"United Nations Economic Commission for Europe\" listID=\"ISO 4217 Alpha\">COP</cbc:DocumentCurrencyCode>\n";
            txt+="\t<cbc:LineCountNumeric>"+fel.getFactura().getVentas().size()+"</cbc:LineCountNumeric>\n";
            txt+="\t<cac:InvoicePeriod>\n";
            String InvoicePeriodStartDate = "2019-05-01"; //Fecha de inicio del periodo de facturación //Primer día del mes
            txt+="\t\t<cbc:StartDate>"+InvoicePeriodStartDate+"</cbc:StartDate>\n";
            String InvoicePeriodEndDate = "2019-05-30"; // Fecha de fin del periodo de facturación // Último día del mes
            txt+="\t\t<cbc:EndDate>"+InvoicePeriodEndDate+"</cbc:EndDate>\n";
            txt+="\t</cac:InvoicePeriod>\n";
            return txt;
        }catch(Exception e){
            txt="Error formando el texto XML";
            return txt+"\n"+e.getMessage();
        }
    }
    
    private String AccountingSupplierParty(){
        String txt ="";
        /**
         * @AdditionalAccountID
         * Identificador de tipo de organización 1 Persona jurídica, 2 Persona Natural
        */
        // Se hace necesario manejarlo en base de datos
        String AdditionalAccountID = "2";
        try{
            // ****** Inicia Bloque con la información de quien emite la factura
            txt+="\t<cac:AccountingSupplierParty>\n";
            txt+="\t\t<cbc:AdditionalAccountID>"+AdditionalAccountID+"</cbc:AdditionalAccountID>\n";
            txt+="\t\t<cac:Party>\n";// Obtenemos el código CIIU de la empresa
            /*  SALDRÁ EN UNA VERSIÓN POSTERIOR
            txt+="\t\t\t<cbc:IndustryClasificationCode>"+empresa.getIndustryClasificationCode()+"</cbc:IndustryClasificationCode>\n";
            */
            txt+="\t\t\t<cac:PartyName>\n";
            txt+="\t\t\t\t<cbc:Name>"+empresa.getCompanyName()+"</cbc:Name>\n";// Obtenemos el nombre de la empresa
            txt+="\t\t\t</cac:PartyName>\n";
            
            
            // *** Bloque con la localización de la empresa que emite la factura.
            txt+="\t\t\t<cac:PhysicalLocation>\n";
            txt+="\t\t\t\t<cac:Address>\n";
            txt+="\t\t\t\t\t<cbc:ID>"+empresa.getLocation().getMinucipio().getCodMuni()+"</cbc:ID>\n";
            
            txt+="\t\t\t\t\t<cbc:CityName>"+empresa.getLocation().getMinucipio().getNombre()+"</cbc:CityName>\n";
            txt+="\t\t\t\t\t<cbc:CountrySubentity>"+empresa.getLocation().getDepartamento().getNombre()+"</cbc:CountrySubentity>\n";
            txt+="\t\t\t\t\t<cbc:CountrySubentityCode>"+empresa.getLocation().getDepartamento().getDeptoCod()+"</cbc:CountrySubentityCode>\n";
            
            txt+="\t\t\t\t\t<cac:AddressLine><cbc:Line>"+empresa.getDireccion()+"</cbc:Line></cac:AddressLine>\n";
            txt+="\t\t\t\t\t<cac:Country>\n";
            txt+="\t\t\t\t\t\t<cbc:IdentificationCode>CO</cbc:IdentificationCode>\n";
            txt+="\t\t\t\t\t\t<cbc:Name languageID=\"es\">Colombia</cbc:Name>\n";
            txt+="\t\t\t\t\t</cac:Country>\n";
            txt+="\t\t\t\t</cac:Address>\n";
            txt+="\t\t\t</cac:PhysicalLocation>\n";
            // *** Bloque con la localización de la empresa que emite la factura.
            // *** Grupo de información tributaria del emisor
            txt+="\t\t\t<cac:PartyTaxScheme>\n";
            txt+="\t\t\t\t<cbc:RegistrationName>"+empresa.getCompanyName()+"</cbc:RegistrationName>\n";
            txt+="\t\t\t\t<cbc:CompanyID schemeAgencyID=\"195\" schemeAgencyName=\"CO, DIAN (Dirección de Impuestos y Aduanas Nacionales)\" schemeID=\"4\" schemeName=\"31\">"+empresa.getCompanyNIT()+"</cbc:CompanyID>\n";
            // Manejar por base de datos
            /**
             * @TaxLevelCode
             * Obligaciones o responasbilidades del contribuyente;
             * incluye el régimen al que pertenece el emisor
             * O-48 = Impuesto sobre las ventas – IVA
             * O-52 = Facturador electrónico
             * O-33 = Impuesto Nacional al consumo
             * A-22 = Exportador
             * A-23 = Importador
            */
            
            ArrayList<ResponsabilidadFiscal>responsabilidades;
            responsabilidades = new ArrayList();
            // PREGUNTAR AL CONTADOR
            responsabilidades.add(new ResponsabilidadFiscal(4, "O-05", "Impuesto de renta y complementario régimen ordinario"));
            // PREGUNTAR AL CONTADOR
            responsabilidades.add(new ResponsabilidadFiscal(25, "O-42", "Obligado a llevar contabilidad"));
            responsabilidades.add(new ResponsabilidadFiscal(26, "O-48", "Impuesto sobre las ventas – IVA"));
            
            String taxLevelCode ="";
            
            for(int i = 0 ; i <= responsabilidades.size()-1 ; i++){
                taxLevelCode+=responsabilidades.get(i).getResponsabilidadCodigo();
                if(i<responsabilidades.size()-1){
                    taxLevelCode+=";";
                }
            }
            //JOptionPane.showMessageDialog(null, taxLevelCode);
            //String TaxLevelCode ="0-48;0-52"; // BASE DE DATOS
            //String TaxLevelCode ="0-48;0-52"; // BASE DE DATOS
            txt+="\t\t\t\t<cbc:TaxLevelCode listName=\"05\">"+taxLevelCode+"</cbc:TaxLevelCode>\n";
            // *** Grupo de información para informar la dirección fiscal del emisor.
            txt+="\t\t\t\t<cac:RegistrationAddress>\n";
            txt+="\t\t\t\t<cbc:ID>"+empresa.getLocation().getMinucipio().getCodMuni()+"</cbc:ID>\n";
            txt+="\t\t\t\t<cbc:CityName>"+empresa.getLocation().getMinucipio().getNombre()+"</cbc:CityName>\n";
            txt+="\t\t\t\t<cbc:CountrySubentity>"+empresa.getLocation().getDepartamento().getNombre()+"</cbc:CountrySubentity>\n";
            txt+="\t\t\t\t<cbc:CountrySubentityCode>"+empresa.getLocation().getDepartamento().getDeptoCod()+"</cbc:CountrySubentityCode>\n";
            txt+="\t\t\t\t<cac:AddressLine>\n";
            txt+="\t\t\t\t\t<cbc:Line>"+empresa.getDireccion()+"</cbc:Line>\n";
            txt+="\t\t\t\t</cac:AddressLine>\n";
            txt+="\t\t\t\t<cac:Country>\n";
            txt+="\t\t\t\t<cbc:IdentificationCode>CO</cbc:IdentificationCode>\n";
            txt+="\t\t\t\t\t<cbc:Name languageID=\"es\">Colombia</cbc:Name>\n";
            txt+="\t\t\t\t</cac:Country>\n";
            txt+="\t\t\t\t</cac:RegistrationAddress>\n";
            // *** Grupo de detalles tributarios del emisor
            txt+="\t\t\t\t<cac:TaxScheme>\n";
            txt+="\t\t\t\t\t<cbc:ID>01</cbc:ID>\n";
            txt+="\t\t\t\t\t<cbc:Name>IVA</cbc:Name>\n";
            txt+="\t\t\t\t</cac:TaxScheme>\n";
            txt+="\t\t\t</cac:PartyTaxScheme>\n";
            // *** Finaliza bloque  de Impuestos
            txt+="\t\t\t<cac:PartyLegalEntity>\n";
            txt+="\t\t\t\t<cbc:RegistrationName>"+empresa.getCompanyName()+"</cbc:RegistrationName>\n";
            txt+="\t\t\t\t<cbc:CompanyID schemeAgencyID=\"195\" schemeAgencyName=\"CO, DIAN (Dirección de Impuestos y Aduanas Nacionales)\" schemeID=\"9\" schemeName=\"CompanyNITCode\">"+empresa.getCompanyNIT()+"</cbc:CompanyID>\n";
            txt+="\t\t\t</cac:PartyLegalEntity>\n";
            txt+="\t\t\t<cac:Contact>\n";
            txt+="\t\t\t\t<cbc:Name>"+empresa.getCompanyName()+"</cbc:Name>\n";
            txt+="\t\t\t\t<cbc:Telephone>"+empresa.getTelefono()+"</cbc:Telephone>\n";
            txt+="\t\t\t\t<cbc:ElectronicMail>"+empresa.getEmail()+"</cbc:ElectronicMail>\n";
            txt+="\t\t\t\t<cbc:Note>Información de contacto</cbc:Note>\n";
            txt+="\t\t\t</cac:Contact>\n";
            txt+="\t\t</cac:Party>\n";
            txt+="\t</cac:AccountingSupplierParty>\n";
            // ****** Finaliza Bloque con la información del adquiriente
            return txt;
        }catch(Exception e){
            txt="Error generando el bloque: "+"AccountingSupplierParty";
            return txt+"\n"+e.getMessage();
        }
    }
    
    private String AccountingCustomerParty(){
        String txt ="";
        try{
            txt+="\t<cac:AccountingCustomerParty>\n";
            // Natural o juridico: 1 natural
            txt+="\t\t<cbc:AdditionalAccountID>1</cbc:AdditionalAccountID>\n";
            txt+="\t\t<cac:Party>\n";
            
            txt+="\t\t<cac:PartyIdentification>\n";
            txt+="\t\t\t<cbc:ID schemeName=\"13\">"+empresaCliente.getCompanyNIT()+"</cbc:ID>\n";
            txt+="\t\t</cac:PartyIdentification>\n";
            txt+="\t\t<cac:PartyName>\n";
            txt+="\t\t<cbc:Name>"+empresaCliente.getCompanyName()+"</cbc:Name>\n";
            txt+="\t\t</cac:PartyName>\n";
            txt+="\t\t<cac:PhysicalLocation>\n";
            txt+="\t\t\t<cac:Address>\n";
            txt+="\t\t\t\t<cbc:ID>"+empresaCliente.getLocation().getMinucipio().getCodMuni()+"</cbc:ID>\n";
            txt+="\t\t\t\t<cbc:CityName>"+empresaCliente.getLocation().getMinucipio().getNombre()+"</cbc:CityName>\n";
            txt+="\t\t\t\t<cbc:PostalZone>"+empresaCliente.getLocation().getCodigoPostal().getCodigo()+"</cbc:PostalZone>\n";
            txt+="\t\t\t\t<cbc:CountrySubentity>"+empresaCliente.getLocation().getDepartamento().getNombre()+"</cbc:CountrySubentity>\n";
            txt+="\t\t\t\t<cbc:CountrySubentityCode>"+empresaCliente.getLocation().getDepartamento().getNombre()+"</cbc:CountrySubentityCode>\n";
            txt+="\t\t\t\t<cac:AddressLine>\n";
            txt+="\t\t\t\t\t<cbc:Line>"+empresaCliente.getDireccion()+"</cbc:Line>\n";
            txt+="\t\t\t\t</cac:AddressLine>\n";
            txt+="\t\t\t\t<cac:Country>\n";
            txt+="\t\t\t\t\t<cbc:IdentificationCode>CO</cbc:IdentificationCode>\n";
            txt+="\t\t\t\t\t<cbc:Name languageID=\"es\">COLOMBIA</cbc:Name>\n";
            txt+="\t\t\t\t</cac:Country>\n";
            txt+="\t\t\t</cac:Address>\n";
            txt+="\t\t</cac:PhysicalLocation>\n";
            
            txt+="\t\t<cac:PartyTaxScheme>\n";
            txt+="\t\t\t<cbc:RegistrationName>"+empresaCliente.getCompanyName()+"</cbc:RegistrationName>\n";
            txt+="\t\t\t<cbc:CompanyID schemeID=\"0\" schemeName=\"13\" schemeAgencyID=\"195\" schemeAgencyName=\"CO, DIAN (Dirección de Impuestos y Aduanas Nacionales)\">"+empresaCliente.getCompanyNIT()+"</cbc:CompanyID>\n";
            
            
            
            
            ArrayList<ResponsabilidadFiscal>responsabilidades;
            responsabilidades = new ArrayList();
            responsabilidades.add(new ResponsabilidadFiscal(27, "O-49", "No responsable de IVA"));
            String taxLevelCode ="";
            
            for(int i = 0 ; i <= responsabilidades.size()-1 ; i++){
                taxLevelCode+=responsabilidades.get(i).getResponsabilidadCodigo();
                if(i<responsabilidades.size()-1){
                    taxLevelCode+=";";
                }
            }
            // BASE DE DATOS
            txt+="\t\t\t<cbc:TaxLevelCode listName=\"49\">"+taxLevelCode+"</cbc:TaxLevelCode>\n";
            txt+="\t\t\t<cac:RegistrationAddress>\n";
            txt+="\t\t\t\t<cbc:ID>"+empresaCliente.getLocation().getMinucipio().getCodMuni()+"</cbc:ID>\n";
            txt+="\t\t\t\t<cbc:CityName>"+empresaCliente.getLocation().getMinucipio().getNombre()+"</cbc:CityName>\n";
            txt+="\t\t\t\t<cbc:PostalZone>"+empresaCliente.getLocation().getCodigoPostal().getCodigo()+"</cbc:PostalZone>\n";
            txt+="\t\t\t\t<cbc:CountrySubentity>"+empresaCliente.getLocation().getDepartamento().getNombre()+"</cbc:CountrySubentity>\n";
            txt+="\t\t\t\t<cbc:CountrySubentityCode>"+empresaCliente.getLocation().getDepartamento().getDeptoCod()+"50</cbc:CountrySubentityCode>\n";
            txt+="\t\t\t\t<cac:AddressLine>\n";
            txt+="\t\t\t\t\t<cbc:Line>"+empresaCliente.getDireccion()+"</cbc:Line>\n";
            txt+="\t\t\t\t</cac:AddressLine>\n";
            txt+="\t\t\t\t<cac:Country>\n";
            txt+="\t\t\t\t\t<cbc:IdentificationCode>CO</cbc:IdentificationCode>\n";
            txt+="\t\t\t\t\t<cbc:Name>COLOMBIA</cbc:Name>\n";
            txt+="\t\t\t\t</cac:Country>\n";
            txt+="\t\t\t</cac:RegistrationAddress>\n";
            txt+="\t\t\t<cac:TaxScheme>\n";
            // PREGUNTARL AL CONTADOR
            //txt+="\t\t\t\t<cbc:ID>ZY</cbc:ID>\n";
            //txt+="\t\t\t\t<cbc:Name>No causa</cbc:Name>\n";
            // PREGUNTARL AL CONTADOR
            txt+="\t\t\t\t<cbc:ID>01</cbc:ID>\n";
            txt+="\t\t\t\t<cbc:Name>IVA</cbc:Name>\n";
            txt+="\t\t\t</cac:TaxScheme>\n";
            txt+="\t\t</cac:PartyTaxScheme>\n";
            
            txt+="\t\t<cac:PartyLegalEntity>\n";
            txt+="\t\t\t<cbc:RegistrationName>"+empresaCliente.getCompanyName()+"</cbc:RegistrationName>\n";
            txt+="\t\t\t<cbc:CompanyID schemeID=\"0\" schemeName=\"13\" schemeAgencyID=\"195\" schemeAgencyName=\"CO, DIAN (Dirección de Impuestos y Aduanas Nacionales)\">"+empresaCliente.getCompanyNIT()+"</cbc:CompanyID>\n";
            txt+="\t\t</cac:PartyLegalEntity>\n";
            txt+="\t\t<cac:Contact>\n";
            txt+="\t\t\t<cbc:Telephone>"+empresaCliente.getTelefono()+"</cbc:Telephone>\n";
            txt+="\t\t\t<cbc:ElectronicMail>"+empresaCliente.getEmail()+"</cbc:ElectronicMail>\n";
            txt+="\t\t</cac:Contact>\n";
            txt+="\t\t</cac:Party>\n";
            txt+="\t</cac:AccountingCustomerParty>\n";
            // **** finaliza el bloque que contiene la información del adquiriente
            return txt;
        }catch(Exception e){
            txt ="Error generando el bloque: "+"AccountingCustomerParty";
            return txt+"\n"+e.getMessage();
        }
    }
    
    private String PaymentMeans(){
        String txt ="";
        int forma =1, medio=42;
        //int forma =fel.getFormaPago();// BASE DE DATOS
        //int medio =fel.getMedioPago();// BASE DE DATOS
        try{
            txt+="\t<cac:PaymentMeans>\n";
            //txt+="\t\t<cbc:ID>"+fel.getFormaPago()+"</cbc:ID>\n";// Base de datos
            txt+="\t\t<cbc:ID>"+forma+"</cbc:ID>\n";// 
            //txt+="\t\t<cbc:PaymentMeansCode>"+fel.getMedioPago()+"</cbc:PaymentMeansCode>\n";// Base de datos
            txt+="\t\t<cbc:PaymentMeansCode>"+medio+"</cbc:PaymentMeansCode>\n";//VARCHAR ZZZ ACUERDO MUTUO
            txt+="\t</cac:PaymentMeans>\n";
            return txt;
        }catch(Exception e){
            txt = "Error generando el bloque: "+"<cac:PaymentMeans>";
            if(forma ==0){
                txt+="Falta la forma de pago.";
            }
            if(medio==0){
                txt+="Falta el medio de pago.";
            }
            return txt+"\n"+e.getMessage();
        }
    }
    
    private String TaxTotal(){
        String txt ="";
        try{
            txt+="\t<cac:TaxTotal>\n";
            // VALIDAR LOS DECIMALES TRUNCADOS A DOS DECIMALES
            txt+="\t\t<cbc:TaxAmount currencyID=\"COP\">"+fel.getFactura().getTotalImp()+"</cbc:TaxAmount>\n";// TOTAL VR IVA 
            txt+="\t\t<cac:TaxSubtotal>\n";
            // VALIDAR LOS DECIMALES TRUNCADOS A DOS DECIMALES
            txt+="\t\t\t<cbc:TaxableAmount currencyID=\"COP\">"+fel.getFactura().getTotalSinImp()+"</cbc:TaxableAmount>\n"; // BASE GRAVABLE
            // VALIDAR LOS DECIMALES TRUNCADOS A DOS DECIMALES
            txt+="\t\t\t<cbc:TaxAmount currencyID=\"COP\">"+fel.getFactura().getTotalImp()+"</cbc:TaxAmount>\n";
            txt+="\t\t\t<cac:TaxCategory>\n";
            // MANEJAR POR BASE DATOS
            // VALIDAR LOS DECIMALES TRUNCADOS A DOS DECIMALES
            txt+="\t\t\t\t<cbc:Percent>19.00</cbc:Percent>\n";// BASE DE DATOS
            txt+="\t\t\t\t<cac:TaxScheme>\n";
            txt+="\t\t\t\t\t<cbc:ID>01</cbc:ID>\n";// BASE DE DATOS
            txt+="\t\t\t\t\t<cbc:Name>IVA</cbc:Name>\n";// BASE DE DATOS
            txt+="\t\t\t\t</cac:TaxScheme>\n";
            txt+="\t\t\t</cac:TaxCategory>\n";
            txt+="\t\t</cac:TaxSubtotal>\n";
            txt+="\t</cac:TaxTotal>\n";
            
            // IMPUESTO AL CONSUMO
            txt+="\t<cac:TaxTotal> <cbc:TaxAmount currencyID=\"COP\">0.00</cbc:TaxAmount> <cac:TaxSubtotal> <cbc:TaxableAmount currencyID=\"COP\">0.00</cbc:TaxableAmount> <cbc:TaxAmount currencyID=\"COP\">0.00</cbc:TaxAmount> <cac:TaxCategory> <cbc:Percent>0.00</cbc:Percent> <cac:TaxScheme> <cbc:ID>04</cbc:ID> <cbc:Name>Impuesto al Consumo</cbc:Name> </cac:TaxScheme> </cac:TaxCategory> </cac:TaxSubtotal> </cac:TaxTotal>\n";// BASE DE DATOS
            txt+="\t<cac:TaxTotal> <cbc:TaxAmount currencyID=\"COP\">0.00</cbc:TaxAmount> <cac:TaxSubtotal> <cbc:TaxableAmount currencyID=\"COP\">0.00</cbc:TaxableAmount> <cbc:TaxAmount currencyID=\"COP\">0.00</cbc:TaxAmount> <cac:TaxCategory> <cbc:Percent>0.00</cbc:Percent> <cac:TaxScheme> <cbc:ID>03</cbc:ID> <cbc:Name>Industria Comercio Avisos</cbc:Name> </cac:TaxScheme> </cac:TaxCategory> </cac:TaxSubtotal> </cac:TaxTotal>\n";// BASE DE DATOS
            return txt;
        }catch(Exception e){
            System.out.println("Error generando el bloque: "+"<cac:TaxTotal>");
        }
        return null;
    }
    
    private String LegalMonetaryTotal(){
        String txt ="";
        try{
            // EJEMPLO DE LA FACTURA DE SUPLY ADORNOS
            txt+="\t<cac:LegalMonetaryTotal>\n";
            txt+="\t\t<cbc:LineExtensionAmount currencyID=\"COP\">"+fel.getFactura().getTotalSinImp()+"</cbc:LineExtensionAmount>\n";//Subtotal Base gravable
            txt+="\t\t<cbc:TaxExclusiveAmount currencyID=\"COP\">"+fel.getFactura().getTotalSinImp()+"</cbc:TaxExclusiveAmount>\n";// Subtotal Base gravable
            txt+="\t\t<cbc:TaxInclusiveAmount currencyID=\"COP\">"+fel.getFactura().getTotalMasImp()+"</cbc:TaxInclusiveAmount>\n"; // TOTAL MAS IMPUESTOS
            txt+="\t\t<cbc:AllowanceTotalAmount currencyID=\"COP\">0.00</cbc:AllowanceTotalAmount>\n";// DESCUENTOS
            txt+="\t\t<cbc:ChargeTotalAmount currencyID=\"COP\">0.00</cbc:ChargeTotalAmount>\n";// RECARGOS
            txt+="\t\t<cbc:PayableAmount currencyID=\"COP\">"+fel.getFactura().getTotalMasImp()+"</cbc:PayableAmount>\n"; // VALOR TOTAL DE LA OPERACIÓN
            txt+="\t</cac:LegalMonetaryTotal>\n";
            return txt;
        }catch(Exception e){
            System.out.println("Error generando el bloque: "+"<cac:LegalMonetaryTotal>");
        }
        return null;
    }
    
    private String InvoiceLine(){
        
        String txt ="";
        //System.out.println(xml.getFactura().getVentas().size());
        try{
            //Recorremos la lista de productos para imprimir los valores.
            for(int i = 0 ; i <= ventas.size()-1 ; i++){
                txt+="\t<cac:InvoiceLine>\n";
                txt+="\t\t<cbc:ID>"+(i+1)+"</cbc:ID>\n";
                // BASE DE DATOS LOS unitCode
                txt+="\t\t<cbc:InvoicedQuantity unitCode=\"EA\">"+ventas.get(i).getCantidad()+"</cbc:InvoicedQuantity>\n";
                txt+="\t\t<cbc:LineExtensionAmount currencyID=\"COP\">"+ventas.get(i).getProducto().getTotal()+"</cbc:LineExtensionAmount>\n";
                int descuento = 0;
                int cargo = 0;
                boolean d;
                if(descuento>0){
                    //VERIFICAMOS SI EXISTE UN DESCUENTO
                    d = false;
                    txt+="\t\t<cac:AllowanceCharge>\n";
                    txt+="\t\t\t<cbc:ID>$AllowanceChargeID</cbc:ID>\n";
                    txt+="\t\t\t<cbc:ChargeIndicator>"+d+"</cbc:ChargeIndicator>\n";
                    txt+="\t\t\t<cbc:BaseAmount currencyID=\"COP\">$TotalProductoMenosDescuento</cbc:BaseAmount>\n";
                    txt+="\t\t\t<cbc:MultiplierFactorNumeric>$DescuentoPorcentaje</cbc:MultiplierFactorNumeric>\n";
                    txt+="\t\t\t<cbc:Amount currencyID=\"COP\">$DescuentoTotalProducto</cbc:Amount>\n";
                    txt+="\t\t</cac:AllowanceCharge>\n";
                }else if(cargo>0){
                    //VERIFICAMOS SI EXISTE UN VARGO
                    d = true;
                    txt+="\t\t<cac:AllowanceCharge>\n";
                    txt+="\t\t\t<cbc:ID>$AllowanceChargeID</cbc:ID>\n";
                    txt+="\t\t\t<cbc:ChargeIndicator>"+d+"</cbc:ChargeIndicator>\n";
                    txt+="\t\t</cac:AllowanceCharge>\n";
                }else if(descuento ==0){
                    // ENVIAMOS LOS DATOS DEL PRODUCTO
                    txt+="\t\t<cac:TaxTotal>\n";
                    txt+="\t\t\t<cbc:TaxAmount currencyID=\"COP\">"+ventas.get(i).getProducto().getProductoIVA()+"</cbc:TaxAmount>\n";
                    txt+="\t\t\t<cac:TaxSubtotal>\n";
                    txt+="\t\t\t\t<cbc:TaxableAmount currencyID=\"COP\">"+ventas.get(i).getProducto().getPrecio()+"</cbc:TaxableAmount>\n";
                    txt+="\t\t\t\t<cbc:TaxAmount currencyID=\"COP\">"+ventas.get(i).getProducto().getProductoIVA()+"</cbc:TaxAmount>\n";
                    txt+="\t\t\t\t<cbc:Percent>19</cbc:Percent>\n";// BASE DE DATOS
                    txt+="\t\t\t\t<cac:TaxCategory>\n";
                    txt+="\t\t\t\t\t<cac:TaxScheme>\n";
                    txt+="\t\t\t\t\t\t<cbc:ID>01</cbc:ID>\n";// BASE DE DATOS
                    txt+="\t\t\t\t\t\t<cbc:Name>IVA</cbc:Name>\n";// BASE DE DATOS
                    txt+="\t\t\t\t\t</cac:TaxScheme>\n";
                    txt+="\t\t\t\t</cac:TaxCategory>\n";
                    txt+="\t\t\t</cac:TaxSubtotal>\n";
                    txt+="\t\t</cac:TaxTotal>\n";
                    
                    txt+="\t\t<cac:Item>\n";
                    txt+="\t\t<cbc:Description>"+fel.getFactura().getVentas().get(i).getProducto().getNombre()+"</cbc:Description>\n";
                    txt+="\t\t<cac:StandardItemIdentification>\n";
                    txt+="\t\t\t<cbc:ID schemeID=\"\">"+ventas.get(i).getProducto().getId()+"</cbc:ID>\n";
                    txt+="\t\t</cac:StandardItemIdentification>\n";
                    txt+="\t\t</cac:Item>\n";
                    
                    txt+="\t\t<cac:Price>\n";
                    txt+="\t\t\t<cbc:PriceAmount currencyID=\"COP\">"+ventas.get(i).getProducto().getPrecio()+"</cbc:PriceAmount>\n";
                    txt+="\t\t\t<cbc:BaseQuantity unitCode=\"DZN\">"+ventas.get(i).getCantidad()+"</cbc:BaseQuantity>\n";
                    txt+="\t\t</cac:Price>\n";
                    txt+="\t</cac:InvoiceLine>\n";                    
                }
            }
            return txt;
        }catch(Exception e){
            return "Error formando el bloque: <cac:InvoiceLine>\n"+e.getMessage();
        }
    }
    
    private static String insertarFirmaDigital(){
        //String txt ="";
        String txt;
        File xmlUnsigned = new File(XML_UNSIGNED);
        try{
            //txt+=openUBLExtension();
            // IMPLEMENTAR FIRMA DIGITAL
            //txt+="\t\t\t\t"+"NUESTRA FRIMA DIGITAL VA AQUÍ"+"\n";
            FirmaController fircon = new FirmaController();
            txt = fircon.firmarArchivoXML(xmlUnsigned);
            
            //txt+= firmaController.getFirma();
            //txt+=closeUBLExtension();
            return txt;
        }catch(Exception e){
            System.out.println("");
            txt ="Error al insertar nuestra firma digital";
            return txt+"\n"+e.getMessage();
        }
    }
    
    public void msg(String msg){
        //JOptionPane.showMessageDialog(null, msg);
        System.out.println(msg);
    }
    
    
    //public static void validateXML(File xmlFile, File validationFile) throws SAXException, IOException{
    public static void validateXML(File xmlFile, File validationFile) throws SAXException, IOException{
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        //JOptionPane.showMessageDialog(null, validationFile.getName());
        //JOptionPane.showMessageDialog(null, xmlFile.getName());
        ((schemaFactory.newSchema(validationFile)).newValidator()).validate(new StreamSource(xmlFile));
    }
    
    private String stringToSha384(String text){
        try { 
            // getInstance() method is called with algorithm SHA-384 
            MessageDigest md = MessageDigest.getInstance("SHA-384");
            // digest() method is called
            // to calculate message digest of the input string
            // returned as array of byte
            byte[] messageDigest = md.digest(text.getBytes());
            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);
            // Convert message digest into hex value
            String hashtext = no.toString(16);
            // Add preceding 0s to make it 32 bit
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            // return the HashText
            return hashtext;
        }
        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void main(String[] args){
        DianDAO dianDAO = new DianDAO();
        // instanciamos el objeto Dian
        Dian dian;
        dian = dianDAO.obtenerDianEmpresa("NIT EMPRESA");
        
        EmpresaDAO empdao = new EmpresaDAO();
        Empresa empresa = empdao.buscarDatosEmpresa("NIT EMPRESA");
        
        // Obtenemos los tatos de la DIAN del el cliente
        
        
        Cliente cliente = new Cliente("NIT CLIENTE");
        
        Empresa clienteEmpresa;
        clienteEmpresa = empdao.buscarDatosEmpresa("NIT CLIENTE");
        
        //int forma =1;
        //int medio =41;
        //int nFactura=990000000;
        
        VentaController ventacon = new VentaController();
        
        
        ArrayList<Venta> ventas;
        ventas = ventacon.buscarVentasFEL(8);
        
        
        
        /*
        ArrayList<Producto> productos = new ArrayList();
        for(int i = 0 ; i <= ventas.size()-1; i++){
            //JOptionPane.showMessageDialog(null, ventas.get(i).getNombre());
            productos.add(new Producto(
                    ventas.get(i).getProducto().getId(), 
                    ventas.get(i).getProducto().getNombre(), 
                    ventas.get(i).getProducto().getReferencia(), 
                    ventas.get(i).getProducto().getSubTipo(),
                    ventas.get(i).getProducto().getPrecio(), 
                    ventas.get(i).getProducto().getProductoIVA(),
                    ventas.get(i).getProducto().getTotal()
            ));            
            JOptionPane.showMessageDialog(null, ventas.get(i).getCantidad());
            JOptionPane.showMessageDialog(null, ventas.get(i).getCantidad());
        }
        */
        
        String fecha="2020-08-26", hora="11:33:30-05:00";
        
        Factura factura;
        int factNum = 2;
        float totalSinImp = 1500000.00f;
        float totalImp = 285000.00f;
        float totalMasImp = 1785000.00f;
        
        factura = new Factura(
                factNum,
                empresa,
                fecha,
                hora,
                cliente,
                //productos,
                ventas,
                totalSinImp,
                totalImp,
                totalMasImp
        );
        
        PagoController pacon = new PagoController();
        
        PagoForma pagoForma= new PagoForma(1,"Efectivo");// Base Datos
        PagoMedio pagoMedio = new PagoMedio(1,"Acuerdo Mutuo"); // Base Datos
        
        FacturaElectronica fe = new FacturaElectronica(
                factNum,
                factura,
                clienteEmpresa,
                pagoForma,
                pagoMedio
        );
        // CORONAMOS ESTE SEGMENTO
        
        ArchivoXML archivo = new ArchivoXML(fe);
        ArchivoXMLController control = new ArchivoXMLController(archivo);
        //control.msg(control.ecribirArchivoXML());
        String xmlText =control.ecribirArchivoXML();
        String ruta ="/home/b41n/CosmoAdornos/Software/CosmoPOS/src/validation/unSigned.xml";
        System.out.println("RUTA:"+ruta);
        System.out.println("TEXTO:"+xmlText);
        File xmlFile = crearArchivoXML(ruta, xmlText);
        
        String firmaDigital;
        if(validarArchivoXML(xmlFile)){
            JOptionPane.showMessageDialog(null, "Archivo XML validado correctamente\n El archivo procede a ser firmado");
            
            //firmaDigital = insertarFirmaDigital();
        }
        //File xmlFile = new File(doc);
        System.out.println("Archivo: "+xmlFile.getName());
        
        
        /*
        */
        
        /*
        try{
           //validateXML(archivoXML, ValidationFile);
            validateXML(xmlFile, ValidationFile);
            
        }catch(SAXException | IOException e){
            flag = false;
        }
        System.out.println("Archivo valido: "+flag);
        */
        
        //System.out.println(archivoXML);
        
        
        
        //System.out.println(generarTextoXML());
        //control.msg(control.generarTextoXML(archivo));
        //control.msg(control.calcularCUFE());
        //control.msg(control.generarQR());
        //control.msg(control.invoiceLine(ventas));
        //control.msg(control.invoiceLine(productos));
        //System.out.println(control.generarTextoXML(archivo));
        //System.out.println(generarTextoXML());
        //System.out.println(generarTextoXML());
        //control.msg(control.generarTextoXML(archivo));
        //control.msg(control.calcularCUFE());
        //control.msg(control.generarQR());
        //control.msg(control.invoiceLine(ventas));
        //control.msg(control.invoiceLine(productos));
        //System.out.println(control.generarTextoXML(archivo));
        //System.out.println(generarTextoXML());
    }

}

