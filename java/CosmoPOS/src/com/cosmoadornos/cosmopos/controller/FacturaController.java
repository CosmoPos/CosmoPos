/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmoadornos.cosmopos.controller;

import com.cosmoadornos.cosmopos.dao.FacturaDAO;
import com.cosmoadornos.cosmopos.dao.VentaDAO;
import com.cosmoadornos.cosmopos.model.Cliente;
import com.cosmoadornos.cosmopos.model.Dian;
import com.cosmoadornos.cosmopos.model.Empresa;
import com.cosmoadornos.cosmopos.model.Factura;
import com.cosmoadornos.cosmopos.model.Venta;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;




/**
 *
 * @author b41n
 */
public class FacturaController {
    
    Empresa empresa;
            
    
    //private static final String LOGO = "/home/b41n/.CosmoPos/icons/logorecibo.png";
    // Actualizar Su logo
    private static final String LOGO = "src/com/cosmoadornos/cosmopos/icons/SU-LOGO.png";
    //private static final String DEST = "results/objects/FacturaTest.pdf";
    
    
    
    private String impreso;// almacena el formato de la factura
    
    private Factura factura;
    
    private float precio;
    
    private float total;
    private float tIva;
    private float subTotal;
    
    float IVA;
    int subI;
    int ivaI;
    FacturaDAO dao = new FacturaDAO();
    
    
    public int buscarUltimaFactura(){
        return dao.buscarUltimaFactura();
    }
    
    public boolean crearFacturaPOS(Factura facturaPOS){
       return dao.crearFacturaPOS(facturaPOS);
   }
    
   /*
   MÉTODO ANTIGUO
   public boolean crearFactura(int nroFactura, String fechaFactura, String horaFactura, int clienteId){
       return dao.crearFactura(nroFactura, fechaFactura,horaFactura, clienteId);
   }
   */
   
    
   
    public boolean guardarFactura(Factura factura){
        factura.getEmpresa().getDian().getInvoiceAuthorization();
        return false;
    }
    
    public String crearTicket(Factura factura) throws IOException,
            DocumentException {
        impreso ="___________________\n";
        impreso +=""+factura.getEmpresa().getCompanyName()+"\n";
        impreso +="___________________\n";
        impreso +="NIT:"+factura.getEmpresa().getCompanyNIT()+"\n";
        impreso +="RÉGIMEN COMÚN\n";
        impreso +="Dirección: "+factura.getEmpresa().getDireccion()+"\n";
        impreso +="___________________\n";
        impreso +="Factura No."+factura.getFactNum()+"\n";
        impreso +="Nombres: "+factura.getCliente().getNombres()+"\n";
        impreso +="Apellidos: "+factura.getCliente().getApellidos()+"\n";
        impreso +="Documento: "+factura.getCliente().getDocumento()+"\n";
        impreso +="Dirección: "+factura.getCliente().getDireccion()+"\n";
        impreso +="Municipio: "+factura.getEmpresa().getLocation().getMinucipio().getNombre()+"\n";
        impreso +="Teléfono: "+factura.getCliente().getTelefono()+"\n";
        impreso +="___________________\n";
        impreso +=" Detalle de la factura \n";
        impreso +="___________________\n";
        int nArt=0;
        total =0;
        

        
        for(int i = 0;  i<=factura.getVentas().size()+1 ;i++){
            
            nArt += factura.getVentas().get(i).getCantidad();
            precio=factura.getVentas().get(i).getProducto().getPrecio();
            subTotal = factura.getVentas().get(i).getSubtotal();
            total+=factura.getVentas().get(i).getTotalVenta();
            //total+=subTotal;
            
            impreso+=factura.getVentas().get(i).getProducto().getNombre()+"\n";
            impreso+="Referencia :\t"+factura.getVentas().get(i).getProducto().getReferencia();
            impreso+="\nMedida.:\t"+factura.getVentas().get(i).getProducto().getSubTipo();
            impreso+="\nCantidad.:\t"+nArt;
            impreso+="\nPrecio: \t$"+precio;
            impreso+="\nSubtotal:\t$"+subTotal+"    \n";
            impreso +="___________________\n";
        }
        tIva = factura.getTotalImp();
        //tIva = (int) (total-(total/1.19));
        subTotal= factura.getTotalSinImp();
        //subTotal= total-tIva;
        impreso +="ARTICULOS:\t"+nArt+"\n";
        impreso +="Subtotal:\t$"+subTotal+"\n";
        impreso +="IVA:\t$"+tIva+"\n";
        impreso +="TOTAL:\t$"+total/*total*/+"\n";
        impreso +="-------------------------------------------\n";
        //Resolución DIAN
        //JOptionPane.showMessageDialog(null, "");
        impreso +=factura.getEmpresa().getDian().getTextResol()+" ";
        impreso +=factura.getEmpresa().getDian().getInvoiceAuthorization();
        impreso +=" Del "+factura.getEmpresa().getDian().getStartDate();
        impreso +=" desde "+factura.getEmpresa().getDian().getStartRange();
        impreso +=" hasta "+factura.getEmpresa().getDian().getEndRange();
        impreso +=", resolución válida hasta "+factura.getEmpresa().getDian().getEndDate();
        impreso +="\n Gracias por su compra";
        impreso +="\n cosmoadornos.com";
        impreso +="\n Software CosmoPos";
        
        
        
        String ruta = System.getProperty("user.dir");
        File carpeta = new File(ruta+"/CosmoPos/Tickets");
        ruta = carpeta.getAbsolutePath();
        if(carpeta.exists()){
            //JOptionPane.showMessageDialog(null,ruta);
            //JOptionPane.showMessageDialog(null,"RUTA: "+"user.dir: "+ruta);
            System.out.println("user.dir: "+ruta);
            System.out.println("existe: ");
        }else{
            //JOptionPane.showMessageDialog(null,"RUTA: "+ruta);
            System.out.println("No existe. ");
            System.out.println("Creando la carpeta: ");
            carpeta.mkdirs();
        }
        //File file = new File(DEST);
        carpeta.getParentFile().mkdirs();
        new FacturaController().crearTicketPDF(impreso, factura.getVentas().size());
        
        return impreso;
    }
    
    
    public File crearTicketPDF(Factura factura) throws IOException,
            DocumentException {
        
        Document documento = new Document();
        
        String ruta = System.getProperty("user.dir");
        File carpeta = new File(ruta+"/CosmoPos/Tickets");
        ruta = carpeta.getAbsolutePath();
        if(carpeta.exists()){
            //JOptionPane.showMessageDialog(null,ruta);
            //JOptionPane.showMessageDialog(null,"RUTA: "+"user.dir: "+ruta);
            System.out.println("user.dir: "+ruta);
            System.out.println("existe: ");
        }else{
            //JOptionPane.showMessageDialog(null,"RUTA: "+ruta);
            System.out.println("No existe. ");
            System.out.println("Creando la carpeta: ");
            carpeta.mkdirs();
        }
        
        File file = new File(ruta+"/POS"+factura.getFactNum()+".pdf");
        //File file = new File(ruta+"/"+factura.getDoc_equivalente_nro()+".pdf");
        FileOutputStream archivo = new FileOutputStream(file);
        
        
        PdfWriter.getInstance(documento, archivo);
        
        Font blodL = new Font(Font.FontFamily.COURIER, 18, Font.BOLD);
        //Font blodR = new Font(Font.FontFamily.COURIER, 12, Font.BOLD);
        Font italic = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC);
        Font italicb = new Font(Font.FontFamily.HELVETICA, 14, Font.ITALIC);
        Font blods = new Font(Font.FontFamily.COURIER, 12, Font.BOLD);
        Font blod = new Font(Font.FontFamily.COURIER, 15, Font.BOLD);
        Font under = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.ITALIC | Font.UNDERLINE);
        
        //PdfWriter.getInstance(documento, new FileOutputStream(dest));
        // IMPRESORA PEQUEÑA
        //Rectangle mm58 = new Rectangle(230, 620/*+(100*items)*/);
        //IMPRESORA DE 85 MM
        Rectangle mm58 = new Rectangle(300, 620/*+(100*items)*/);
        //Rectangle pageProd = new Rectangle(230, 230/*+(100*items)*/);
        //Rectangle mm58 = new Rectangle(168, 300/*+(100*items)*/);
        
        documento.setPageSize(mm58);
        documento.setMargins(5, 2, 2, 5);
        documento.open();
        documento.newPage();
        
        Image logo = Image.getInstance(LOGO);
        
        PdfPTable tbl = new PdfPTable(1);
        tbl.addCell(logo);
        tbl.setWidthPercentage(100);
        
        documento.add(tbl);
        
        //Paragraph textoFactura = new Paragraph(impreso, italic);
        //textoFactura.setAlignment(Element.ALIGN_CENTER);
        
        //documento.add(textoFactura);
        
        
        
        Paragraph p = new Paragraph();
        Phrase pr;
        p.setAlignment(Element.ALIGN_CENTER);
        
        
        pr = new Phrase(factura.getEmpresa().getCompanyName()+"\n", blodL);
       
        
        pr.add(new Phrase("NIT: ", blods));
        pr.add(new Phrase(factura.getEmpresa().getCompanyNIT(), italicb));
        pr.add(new Phrase("\nDirección:", blods));
        pr.add(new Phrase(factura.getEmpresa().getDireccion(), italic));
        pr.add(new Phrase("\nRÉGIMEN COMÚN", blods));
        //p.add(new Phrase(""));
        //p.add(new Phrase(""));
        
        //p.add(pr);
        
        p.add(pr);
        
        documento.add(p);
        
        p = new Paragraph();
        pr = new Phrase();
        pr.add(new Phrase());
        //p.add(new Phrase(""));
        
       
        pr.add(new Phrase("Factura No. ", blods));
        
        //p.setAlignment(Element.ALIGN_CENTER);
        
        
        pr.add(new Phrase(""+factura.getFactNum(), italicb));
        
        pr.add(new Phrase("\nCliente: ", blods));
        pr.add(new Phrase(factura.getCliente().getNombres()+ " " +factura.getCliente().getApellidos(), italicb));
        
        
        pr.add(new Phrase("\nDocumento: ", blods));
        pr.add(new Phrase(factura.getCliente().getDocumento(), italicb));
        
        pr.add(new Phrase("\nDirección: ", blods));
        pr.add(new Phrase(factura.getCliente().getDireccion(), italicb));
        
        pr.add(new Phrase("\nCiudad: ", blods));
        pr.add(new Phrase(factura.getEmpresa().getLocation().getMinucipio().getNombre(), italicb));
        
        pr.add(new Phrase("\nTeléfono: ", blods));
        pr.add(new Phrase(factura.getCliente().getTelefono(), italicb));
        
        
        p.add(pr);
        documento.add(p);
        
        //documento.setPageSize(pageProd);
        int alturaPagina;
        
        
        Rectangle pageProd;
        
        //pageProd = new Rectangle(230, 40/*+(100*items)*/);
        //documento.setPageSize(pageProd);
        
        PdfPTable tbp = new PdfPTable(3);
        
        tbp.setWidthPercentage(100);
        
        PdfPCell celda;
        
        celda = new PdfPCell(new Phrase("Información de los productos" ,blods));
        //celda.setPaddingLeft(0);
        celda.setBorder(Rectangle.NO_BORDER);
        //celda.setPaddingTop(-12);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setColspan(3);
        //celda.setRowspan(4);
        tbp.addCell(celda);
        
        //documento.add(tbp);
        
        celda = new PdfPCell(new Phrase("Descripción" ,blods));
        celda.setHorizontalAlignment(Element.ALIGN_LEFT);
        celda.setColspan(2);
        tbp.addCell(celda);

        celda = new PdfPCell(new Phrase("Detalle" ,blods));
        
        celda.setHorizontalAlignment(Element.ALIGN_LEFT);
        tbp.addCell(celda);
        
        documento.add(new Paragraph(new Phrase("\n")));
        
        documento.add(tbp);
        
        
        //alturaPagina = 110*productos.size()-1;
        //pageProd = new Rectangle(230, alturaPagina/*+(100*items)*/);
        //documento.setPageSize(pageProd);
        
        
        //documento.newPage();

        p = new Paragraph();
        pr = new Phrase();
        pr.add(new Phrase());
        
        int nArt=0;
        total =0;
        int ppp =0;
        for(int i = 0;  i<factura.getVentas().size() ; i++){
            documento.setMargins(5, 2, 2, 5);
            
            //ppp+=1;
            tbp = new PdfPTable(3);
            tbp.setWidthPercentage(100);
            //nArt += factura.getVentas().get(i).getCantidad();
            JOptionPane.showMessageDialog(null, "Precio:");
            precio=factura.getVentas().get(i).getProducto().getPrecio();
            subTotal=(int) (nArt);
            total+=subTotal;
            
            pr =new Phrase();
            pr.add(new Phrase());
            
            //pr.add(new Phrase(productos.get(i).getNombre(), italic));
            
            celda = new PdfPCell(new Phrase(factura.getVentas().get(i).getProducto().getNombre(), blods));
            //celda = new PdfPCell(new Phrase("NOMBRE", blods));
            //celda.setPaddingLeft(0);
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda.setColspan(3);
            tbp.addCell(celda);
            
            pr.add(new Phrase("Referencia", italicb));
            pr.add(new Phrase("\nMedida", italicb));
            pr.add(new Phrase("\nCantidad", italicb));
            pr.add(new Phrase("\nPrecio:", italicb));
            pr.add(new Phrase("\nSubtotal:", italicb));
            
            celda = new PdfPCell(new Phrase(pr));
            //celda.setPaddingLeft(0);
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            celda.setColspan(2);
            tbp.addCell(celda);
            
            impreso=factura.getVentas().get(i).getProducto().getReferencia();
            impreso+="\n"+factura.getVentas().get(i).getProducto().getSubTipo();
            impreso+="\n"+factura.getVentas().get(i).getCantidad();
            impreso+="\n$"+precio;
            impreso+="\n$"+(int) subTotal+"\n";
            
            celda = new PdfPCell(new Phrase(impreso, italicb));
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbp.addCell(celda);
            
            
            /*
            if(ppp==2){
                ppp=0;
                documento.newPage();
            }
            */
        documento.add(tbp);
        //ppp+=1;
        }
        //documento.setPageSize(mm58);
        //documento.newPage();
        
        
        
        tIva = (int) (total-(total/1.19));
        subTotal= total-tIva;
        impreso ="ARTICULOS:\t"+nArt+"\n";
        impreso +="Subtotal:\t$"+subTotal+"\n";
        impreso +="IVA:\t$"+tIva+"\n";
        impreso +="TOTAL:\t$"+total/*total*/+"\n";
        impreso +="-------------------------------------------\n";
        //Resolución DIAN
        impreso +=factura.getEmpresa().getDian().getTextResol()+" ";
        impreso +=factura.getEmpresa().getDian().getInvoiceAuthorization();
        impreso +=" Del "+factura.getEmpresa().getDian().getStartDate();
        impreso +=" desde "+factura.getEmpresa().getDian().getStartRange();
        impreso +=" hasta "+factura.getEmpresa().getDian().getEndRange();
        impreso +=", resolución válida hasta "+factura.getEmpresa().getDian().getEndDate();
        impreso +="\n Gracias por su compra";
        impreso +="\n cosmoadornos.com";
        impreso +="\n Software CosmoPos";
        
        pr = new Phrase();
        pr.add(new Phrase(impreso));
        p.add(pr);

        documento.add(p);
        
        
        documento.close();
        archivo.close();
        
        
        //mostrarArchivo(file);
        
        return file;
    }
    
    public void crearTicketPDF(String impreso, int items)throws IOException, DocumentException {
        Document documento = new Document();
        File file = new File("results/objects/test.pdf");
        if(!file.exists()){
            file.getParentFile().mkdirs();
        }
        FileOutputStream archivo = new FileOutputStream(file);
        
        
        PdfWriter.getInstance(documento, archivo);
        
        Font blod = new Font(Font.FontFamily.COURIER, 6, Font.BOLD);
        Font italic = new Font(Font.FontFamily.HELVETICA, 9, Font.ITALIC);
        Font under = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.ITALIC | Font.UNDERLINE);
        
        //PdfWriter.getInstance(documento, new FileOutputStream(dest));
        Rectangle mm58 = new Rectangle(168, 300/*+(100*items)*/);
        
        documento.setPageSize(mm58);
        documento.setMargins(5, 2, 2, 5);
        documento.open();
        documento.newPage();
        
        Image logo = Image.getInstance(LOGO);
        Paragraph pLogo = new Paragraph();
        pLogo.add(logo);
        documento.add(pLogo);
        
        Paragraph textoFactura = new Paragraph(impreso, italic);
        //textoFactura.setAlignment(Element.ALIGN_CENTER);
        
        documento.add(textoFactura);
        
        documento.close();
        mostrarArchivo(file);
        
        
    }
    
    
    public void crearFacturaPDF(Factura factura, String fecha)throws IOException, DocumentException, SQLException {
        this.factura = factura;
        EmpresaController datosCon = new EmpresaController();
        empresa = datosCon.buscarDatosEmpresa("NIT EMPRESA");
        
        
        float left = 30;
        float right = 30;
        float top = 60;
        float bottom = 20;
        
        Document documento = new Document(PageSize.A4, left, right, top, bottom);
        
        File file = new File("results/objects/FacturaEmpresa.pdf");
        FileOutputStream archivo = new FileOutputStream(file);
        PdfWriter.getInstance(documento, archivo);
        
        documento.open();
        
        documento.setMargins(left, right, 0, bottom);
        
        Font blod = new Font(Font.FontFamily.COURIER, 6, Font.BOLD);
        Font italic = new Font(Font.FontFamily.HELVETICA, 6, Font.ITALIC);
        Font under = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.ITALIC | Font.UNDERLINE);
        
        
        PdfPCell celda;
        
        PdfPTable tbEmpresa = new PdfPTable(9);
        tbEmpresa.setWidthPercentage(100);
            
        celda = new PdfPCell(new Phrase("ESTABLECIMIENTO DE COMERCIO", blod));
        celda.setHorizontalAlignment(Element.ALIGN_LEFT);
        celda.setColspan(5);
        tbEmpresa.addCell(celda);

        celda = new PdfPCell(new Phrase("", blod));
        celda.setColspan(4);
        celda.setRowspan(4);
        Image logo = Image.getInstance(LOGO);
        celda.addElement(logo);
        tbEmpresa.addCell(celda);
        
        celda = new PdfPCell(new Phrase("NIT:"+empresa.getCompanyNIT()
                +" REG. "+empresa.getRegimen(), blod));
        celda.setHorizontalAlignment(Element.ALIGN_LEFT);
        celda.setColspan(5);
        tbEmpresa.addCell(celda);

        celda = new PdfPCell(new Phrase(empresa.getDireccion()
                +" Tel: "+empresa.getTelefono(), blod));
        celda.setHorizontalAlignment(Element.ALIGN_LEFT);
        celda.setColspan(5);
        tbEmpresa.addCell(celda);

        celda = new PdfPCell(new Phrase("Cliente", blod));
        celda.setHorizontalAlignment(Element.ALIGN_LEFT);
        celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tbEmpresa.addCell(celda);

        celda = new PdfPCell(new Phrase(factura.getCliente().getNombres()+" "+factura.getCliente().getApellidos(), italic));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
        celda.setColspan(4);
        tbEmpresa.addCell(celda);
        
        
        celda = new PdfPCell(new Phrase("Documento", blod));
        celda.setHorizontalAlignment(Element.ALIGN_LEFT);
        tbEmpresa.addCell(celda);

        celda = new PdfPCell(new Phrase(factura.getCliente().getTipoDocumento()+": "+factura.getCliente().getDocumento(), italic));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setColspan(4);
        tbEmpresa.addCell(celda);


        

        celda = new PdfPCell(new Phrase("Fecha: "+fecha, blod));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setColspan(2);
        tbEmpresa.addCell(celda);

        celda = new PdfPCell(new Phrase("Hora: "+"15:55", blod));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setColspan(2);
        tbEmpresa.addCell(celda);

        


        celda = new PdfPCell(new Phrase("Dirección", blod));
        celda.setHorizontalAlignment(Element.ALIGN_LEFT);
        tbEmpresa.addCell(celda);

        celda = new PdfPCell(new Phrase(factura.getCliente().getDireccion(), blod));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setColspan(4);
        tbEmpresa.addCell(celda);

        celda = new PdfPCell(new Phrase("Factura No. "+factura.getFactNum(), blod));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setColspan(2);
        tbEmpresa.addCell(celda);

        celda = new PdfPCell(new Phrase("", blod));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setColspan(2);
        tbEmpresa.addCell(celda);
        
        
        /*
        celda = new PdfPCell(new Phrase("Detalle de la factura", blod));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setColspan(9);
        tbEmpresa.addCell(celda);
        */
        
        
        documento.add(tbEmpresa);
        
        
        
        
        PdfPTable tbProductos = new PdfPTable(9);
        
        tbProductos.setWidthPercentage(100);
        
        celda = new PdfPCell(new Phrase("Detalle de la factura", blod));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setColspan(9);
        tbProductos.addCell(celda);
        
        
        celda = new PdfPCell(new Phrase("Producto",blod));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setColspan(3);
        tbProductos.addCell(celda);
        
        
        
        celda = new PdfPCell(new Phrase("Referencia",blod));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        tbProductos.addCell(celda);
        
        celda = new PdfPCell(new Phrase("Medida",blod));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        tbProductos.addCell(celda);
        
        celda = new PdfPCell(new Phrase("Cantidad",blod));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        tbProductos.addCell(celda);
        
        
        celda = new PdfPCell(new Phrase("Precio",blod));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        tbProductos.addCell(celda);
        
        celda = new PdfPCell(new Phrase("IVA",blod));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        tbProductos.addCell(celda);
        
        celda = new PdfPCell(new Phrase("Total",blod));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        tbProductos.addCell(celda);
        
        
        //LLenamos la tabla con los productos de la factura.

        total =0;
        subTotal=0;
        int sT=0;
        int iT =0;
        for(int i = 0 ; i <= factura.getVentas().size()-1 ; i++){
            
            //t = (int) c*p;
            
            celda = new PdfPCell(new Phrase(factura.getVentas().get(i).getProducto().getNombre(),italic));
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            celda.setColspan(3);
            tbProductos.addCell(celda);

            celda = new PdfPCell(new Phrase(factura.getVentas().get(i).getProducto().getReferencia(), italic));
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbProductos.addCell(celda);

            celda = new PdfPCell(new Phrase(factura.getVentas().get(i).getProducto().getSubTipo(), italic));
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbProductos.addCell(celda);

            celda = new PdfPCell(new Phrase(""+factura.getVentas().get(i).getCantidad(), italic));
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbProductos.addCell(celda);

            celda = new PdfPCell(new Phrase("$"+factura.getVentas().get(i).getProducto().getPrecio(), italic));
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbProductos.addCell(celda);
            
            
            
            
            
            precio = factura.getVentas().get(i).getProducto().getPrecio();
            //nArt += productos.get(i).getCantidad();
            
            
            float c = factura.getVentas().get(i).getCantidad();
            
            subTotal=(int) (precio*c);
            total+=subTotal;
            
            float tasa = 1.19f;

            total = (int) (precio*c);

            subTotal = total/tasa;

            subI = (int) subTotal;

            IVA = total-subTotal;


            ivaI = (int) IVA;
            
            float val = subTotal-subI;

            //ivaI=total-(int)(total/1.19);
            
            if (val >.50){
                // suma decimal.
                subI+=1;
            }else{
                ivaI+=1;
            }
            iT +=ivaI;
            sT += subI;
            
            
            celda = new PdfPCell(new Phrase("$"+ivaI,italic));
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbProductos.addCell(celda);

            //Calculamos el total ???
            celda = new PdfPCell(new Phrase("$"+(int)total,italic));
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbProductos.addCell(celda);
            
            
        }
        
        
        
        documento.add(tbProductos);
        
        PdfPTable tbTotales = new PdfPTable(9);
        tbTotales.setWidthPercentage(100);
        
        
        celda = new PdfPCell(new Phrase(setTextoResolucionDian(),blod));
        celda.setHorizontalAlignment(Element.ALIGN_LEFT);
        celda.setColspan(7);
        celda.setRowspan(4);
        tbTotales.addCell(celda);
        
        celda = new PdfPCell(new Phrase("Subtotal",blod));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        //celda.setColspan(2);
        tbTotales.addCell(celda);
        
        celda = new PdfPCell(new Phrase("$"+sT,italic));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        tbTotales.addCell(celda);
        
        
        celda = new PdfPCell(new Phrase("Descuento",blod));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        //celda.setColspan(2);
        tbTotales.addCell(celda);
        
        celda = new PdfPCell(new Phrase("(-)$",italic));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        tbTotales.addCell(celda);
        
        celda = new PdfPCell(new Phrase("IVA",blod));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        //celda.setColspan(2);
        tbTotales.addCell(celda);
        
        celda = new PdfPCell(new Phrase("$"+iT,italic));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        tbTotales.addCell(celda);
        
        celda = new PdfPCell(new Phrase("Total",blod));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        //celda.setColspan(2);
        tbTotales.addCell(celda);
        
        celda = new PdfPCell(new Phrase("$"+total,italic));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        tbTotales.addCell(celda);
        
        
        documento.add(tbTotales);
        
        documento.close();
        //mostrarArchivo(file);
    }
    
    
    public void mostrarArchivo(File file){
        try{
            Desktop.getDesktop().open(file);
        }catch(IOException e){
            JOptionPane.showMessageDialog(null,"ERROR: "+e);
        }
    }
    
    
    private String setTextoResolucionDian(){
        String txtResolucion;
        //Resolución DIAN
        
        txtResolucion =factura.getEmpresa().getDian().getTextResol()+" ";
        txtResolucion +=factura.getEmpresa().getDian().getInvoiceAuthorization();
        txtResolucion +=" Del "+factura.getEmpresa().getDian().getStartDate();
        txtResolucion +=" desde "+factura.getEmpresa().getDian().getStartRange();
        txtResolucion +=" hasta "+factura.getEmpresa().getDian().getEndRange();
        txtResolucion +=", resolución válida hasta "+factura.getEmpresa().getDian().getEndDate();
        txtResolucion +="\n Gracias por su compra";
        txtResolucion +="\n cosmoadornos.com";
        txtResolucion +="\n Software CosmoPos";
        return txtResolucion;
    } 
    
    
    
    
    
    public static void main(String[] args) throws IOException,
            DocumentException,
            SQLException {
        FacturaController facon = new FacturaController();
        
        DianController diancon = new DianController();
        
        Dian dian = diancon.obtenerDianEmpresa("NIT EMPRESA");
        
        Cliente cliente;
        int nFactura = facon.buscarUltimaFactura();
        
        System.out.println("Factura: "+nFactura);
        
        /*
        cliente.setDocumento("1121871348");
        cliente.setDireccion("CRA. 30A N.39-06");
        cliente.setNombres("Brayan");
        cliente.setApellidos("Novoa");
        cliente.setTipoDocumento("CC");
        cliente.setTelefono("3197467681");
        */
        
        cliente = new Cliente(
                "1121871348-4",
                "Brayan Mauricio",
                "Novoa Salazar",
                "CRA. 30A N.39-06",
                "3197467681"
        );
        

        /*
        ArrayList<Producto> productos = new ArrayList();
        productos.add(new Producto("Agujetero Tapestry 1822 Blister X6", ".35.", "50GR", 1785000.00f, 1 ,3000));
        productos.add(new Producto("Agujetero Tapestry 1822 Blister X6", ".35.", "50GR", 3000, 1 ,3000));
        
        productos.add(new Producto("Agujetero Tapestry 1822 Blister X6", ".35.", "50GR", 1, 3000 ,3000));
        
        
        productos.add(new Producto("Agujetero Tapestry 1822 Blister X6", ".35.", "50GR", 1, 3000 ,3000));
        productos.add(new Producto("Agujetero Tapestry 1822 Blister X6", ".35.", "50GR", 1, 3000 ,3000));
        
        
        
        productos.add(new Producto("Agujetero Tapestry 1822 Blister X6", ".35.", "50GR", 1, 3000 ,3000));
        productos.add(new Producto("Agujetero Tapestry 1822 Blister X6", ".35.", "50GR", 1, 3000 ,3000));
        
        
        productos.add(new Producto("Hilo Oasis", ".35.", "50GR", 1, 3000 ,3000));
        productos.add(new Producto("Lana Dominó", ".234.", "UNIDAD", 1, 12000 ,12000));
        
        
        
        productos.add(new Producto("Hilo Oasis", ".35.", "50GR", 1, 3000 ,3000));
        productos.add(new Producto("Lana Dominó", ".234.", "UNIDAD", 1, 12000 ,12000));
        productos.add(new Producto("Hilo Oasis", ".35.", "50GR", 1, 3000 ,3000));
        productos.add(new Producto("Lana Dominó", ".234.", "UNIDAD", 1, 12000 ,12000));
        productos.add(new Producto("Hilo Oasis", ".35.", "50GR", 1, 3000 ,3000));
        productos.add(new Producto("Lana Dominó", ".234.", "UNIDAD", 1, 12000 ,12000));
        
        
        productos.add(new Producto(25000,7,"Hilo Oasis"));
        productos.add(new Producto(2500,7,"Hilo Oasis"));
        productos.add(new Producto(500,3,"Docena Boton"));
        productos.add(new Producto(500,4,"Hilo Maacrame"));
        productos.add(new Producto(500,3,"Docena Boton"));
        productos.add(new Producto(500,4,"Hilo Maacrame"));
        */
        
        EmpresaController empcon = new EmpresaController();
        
        
        Empresa empresa = empcon.buscarDatosEmpresa("Nit Empresa");
        
        ArrayList<Venta> ventas;
        VentaDAO dao = new VentaDAO();
        ventas = dao.buscarVentasFEL(1);
        
        String fecha ="2020-08-25";
        String hora ="16:46:55";
        float totalSinImp =1500000.00f,totalImp=285000.00f, totalMasImp=1785000.00f;//
        
        Factura factura = new Factura(nFactura, empresa, fecha, hora, cliente, ventas, totalSinImp, totalImp, totalMasImp);
        
        
        
        //facon.crearTicket(dian, cliente, productos, doc_equivalente_nro);
        
        
        //facon.crearTicket(factura);
        facon.crearTicketPDF(factura);
        
        /*
        String texto = facon.crearTicket(factura);
        System.out.print(texto);
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new FacturaController().crearTicketPDF(DEST, texto, productos.size());  
        new FacturaController().crearTicketPDF(DEST, texto, productos.size());  
        */
        //facon.crearFacturaPDF(factura, "mm-dd-aaaa");
        
    }
    
}