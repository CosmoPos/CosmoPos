/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmoadornos.cosmopos.dao;

import com.cosmoadornos.cosmopos.controller.DbCon;
import com.cosmoadornos.cosmopos.controller.InformeVentasController;
import com.cosmoadornos.cosmopos.model.ZDiario;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
//import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author b41n
 */
public class ZDiarioDAO {
    
    
    private static final String LOGO = "src/com/cosmoadornos/cosmopos/icons/logorecibo.png";
    
    private String nit="NIT EMPRESA";
    private String direccion="DIRECCION EMPRESA";
    
    
    private static final DbCon DBCON = new DbCon();
    private static Connection cn;
    
    private static Statement st;
    private static ResultSet rs;
    private static PreparedStatement pst;
    
    private int regD;
    private int regP;
    private int tlrg;
    
    private int devolucion;
    
    private int ventaD;
    private int ventaP;
    private int ventaTotal;
    
    
    
    private static String sql;
    
    
    public static boolean validarZDiario(){
        sql ="SELECT z_diario FROM z_diarios;";
        try{
            cn = DBCON.getConexion();
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            if(rs.next()){
                System.out.println(rs.getInt("z_diario"));
                return true;
            }else{
                return false;
            }
        }catch(SQLException e){
            System.out.println("ERROR SQL: "+e);
            return false;
        }
    }
    
    public static boolean iniciarNumeracion(String fecha){
        sql = "INSERT INTO z_diarios(z_diario_id, z_diario, z_diario_fecha) VALUES (1,1,'"+fecha+"');";
        try{
            cn = DBCON.getConexion();
            pst = cn.prepareStatement(sql);
            if(pst.executeUpdate()==1){
                return true;
            }
        }catch(SQLException e){
            System.out.println("ERROR SQL: "+e);
        }
        return false;
    }
    
    public static int obtenerUltimoZDiario(){
        sql ="SELECT z_diario FROM z_diarios;";
        int last = 0;
        try{
            cn = DBCON.getConexion();
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            if(rs.last()){
                last = rs.getInt(1);
            }
            return last;
        }catch(SQLException e){
            System.out.println("ERROR SQL: "+e);
        }
        return 0;
    }
    
    
    
    public boolean guardarZDiario(ZDiario zDiario){
        sql = "INSERT INTO z_diarios (z_diario, z_diario_fecha) VALUES(?,?);";
        try{
            cn = DBCON.getConexion();
            pst = cn.prepareStatement(sql);
            pst.setInt(1, zDiario.getzDiario());
            pst.setString(2, zDiario.getzDiarioFecha());
            if(pst.executeUpdate()==1){
                return true;
            }
        }catch(SQLException e){
            System.out.println("ERROR SQL: \n"+e);
        }
        return false;
    }
    
    public String[] obtenerRegistros(int zDiarioId){
        String[] datos = null;
        sql ="SELECT venta_id FROM ventas WHERE z_diario_id = "+zDiarioId+";";
        int primero, ultimo;
        try{
            cn = DBCON.getConexion();
            datos = new String[2];
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            if(rs.first()){
                primero = rs.getInt(1);
                datos[0] =""+primero;
            }
            if(rs.last()){
                ultimo = rs.getInt(1);
                datos[1] =""+ultimo;
            }
        }catch(SQLException e){
            System.out.println("ERROR SQL: \n"+e);
        }
        return datos;
    }
    
    public int obtenerTotalRegistros(int zDiarioId){
        sql= "SELECT COUNT(venta_id) AS nro_registros FROM ventas WHERE z_diario_id = "+zDiarioId+";";
        try{
            cn = DBCON.getConexion();
            st =  cn.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()){
                return rs.getInt("nro_registros");
            }
        }catch(SQLException e){
            System.out.println("ERROR SQL: \n"+e);
        }
        return 0;
    }
    
    
    public boolean crearTicketZDiarioPDF(String ruta, int nroZ) throws IOException, DocumentException, FileNotFoundException{
        
        try{
            String linea = "-------------------------------------------------------";
            Font blodL = new Font(Font.FontFamily.COURIER, 18, Font.BOLD);
            Font italic = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC);
            Font italicb = new Font(Font.FontFamily.HELVETICA, 14, Font.ITALIC);
            Font blods = new Font(Font.FontFamily.COURIER, 12, Font.BOLD);
            
            
            
            
            Document documento = new Document();
            FileOutputStream archivo;
            File file= new File(ruta+".pdf");
            archivo = new FileOutputStream(file);
            PdfWriter.getInstance(documento, archivo);


            Rectangle mm58 = new Rectangle(230, 280);

            documento.setPageSize(mm58);
            documento.setMargins(5, 2, 2, 5);
            documento.open();


            Image logo = Image.getInstance(LOGO);

            PdfPTable tb;
            
            tb = new PdfPTable(1);
            
            tb.addCell(logo);
            tb.setWidthPercentage(100);

            documento.add(tb);


            Paragraph p = new Paragraph();
            Phrase pr;
            p.setAlignment(Element.ALIGN_CENTER);


            pr = new Phrase("NOMBRE DE LA EMPRESA\n", blodL);


            pr.add(new Phrase("NIT: ", blods));
            pr.add(new Phrase(nit, italicb));
            pr.add(new Phrase("\nDirección:", blods));
            pr.add(new Phrase(direccion, italic));
            pr.add(new Phrase("\nRÉGIMEN COMÚN", blods));
            pr.add(new Phrase("\nTIQUETE DE VENTA\n\n", blods));

            p.add(pr);

            documento.add(p);
            
            
            PdfPCell celda;
            
            tb = new PdfPTable(3);
            tb.setWidthPercentage(100);
            
            celda = new PdfPCell(new Phrase("Z"));
            celda.setBorder(Rectangle.NO_BORDER);
            tb.addCell(celda);
            
            celda = new PdfPCell(new Phrase("2019-07-31"));
            celda.setBorder(Rectangle.NO_BORDER);
            tb.addCell(celda);
            
            celda = new PdfPCell(new Phrase("18:41"));
            celda.setBorder(Rectangle.NO_BORDER);
            tb.addCell(celda);
            
            celda = new PdfPCell(new Phrase("567815"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            celda.setColspan(3);
            tb.addCell(celda);
            
            documento.add(tb);
            documento.add(new Phrase(linea));
            
            
            tb = new PdfPTable(3);
            tb.setWidthPercentage(100);
            tb.setSpacingAfter(0);
            celda = new PdfPCell(new Phrase("Z"));
            celda.setPaddingTop(-15);
            celda.setBorder(Rectangle.NO_BORDER);
            tb.addCell(celda);
            
            celda = new PdfPCell(new Phrase("Z DIARIO"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setPaddingTop(-15);
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            tb.addCell(celda);
            
            celda = new PdfPCell(new Phrase("2131"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setPaddingTop(-15);
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tb.addCell(celda);
            
            documento.add(tb);
            documento.add(new Phrase(linea));
            
            documento.newPage();
            
            tb = new PdfPTable(3);
            tb.setWidthPercentage(100);
            
            celda = new PdfPCell(new Phrase("Z"));
            celda.setBorder(Rectangle.NO_BORDER);
            tb.addCell(celda);
            
            celda = new PdfPCell(new Phrase("DEPARTAMENTOS"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setColspan(2);
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            tb.addCell(celda);
            
            documento.add(tb);
            
            regD = 0;
            try{
                cn = DBCON.getConexion();
                sql ="SELECT "
                    + "V.departamento_id, D.departamento, "
                    + "COUNT(V.departamento_id)AS registro, "
                    + "SUM(V.subtotal) AS subtotal, "
                    + "SUM(V.vr_iva) AS vr_iva, "
                    + "SUM(V.total) AS total "
                    + "FROM ventas AS V, "
                    + "departamentos AS D, "
                    + "facturas AS F "
                    + "WHERE "
                    + "V.departamento_id = D.departamento_id "
                    + "AND F.factura_id = V.factura_id "
                    + "AND V.z_diario_id = "+nroZ+" "
                    + "GROUP BY D.departamento_id "
                    + "ORDER BY D.departamento_id ASC;";
                
                st =cn.createStatement();
                rs = st.executeQuery(sql);
                

                tb = new PdfPTable(2);
                tb.setWidthPercentage(100);
                
                String producto;
                int subTotal;
                int vrIva;
                while(rs.next()){
                    
                    
                    
                    //int id = rs.getInt("producto_id");
                    subTotal = rs.getInt("subtotal");
                    vrIva = rs.getInt("vr_iva");
                    ventaD = rs.getInt("total");
                    regD = rs.getInt("registro");

                    
                    producto = rs.getString("departamento");
                    celda = new PdfPCell(new Phrase(producto));
                    celda.setBorder(Rectangle.NO_BORDER);
                    celda.setHorizontalAlignment(Element.ALIGN_LEFT);
                    celda.setColspan(2);
                    tb.addCell(celda);

                    celda = new PdfPCell(new Phrase(""+regD));
                    celda.setBorder(Rectangle.NO_BORDER);
                    celda.setHorizontalAlignment(Element.ALIGN_LEFT);
                    tb.addCell(celda);
                    
                    celda = new PdfPCell(new Phrase("$ "+ventaD));
                    celda.setBorder(Rectangle.NO_BORDER);
                    celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    tb.addCell(celda);
                    
                    ventaTotal+= ventaD;
                    tlrg+=regD;
                    
                }
                documento.add(tb);
                    
                
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null,"ERROR SQL"+e);
            }// FIN DEL INFORME DE LOS DEPARTAMENTOS
            
            tb = new PdfPTable(2);
            tb.setWidthPercentage(100);
            regD = tlrg;
            tlrg = 0;
            
            ventaD = ventaTotal;
            ventaTotal = 0;
            celda= new PdfPCell(new Phrase("TL"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            tb.addCell(celda);
            
            
            celda= new PdfPCell(new Phrase(""+regD));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tb.addCell(celda);
            
            celda= new PdfPCell(new Phrase("$ "+ventaD));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            celda.setColspan(2);
            tb.addCell(celda);
            
            
            documento.add(new Paragraph(new Phrase(linea)));
            documento.add(tb);
            documento.add(new Paragraph(new Phrase(linea)));
            
            
            
            //documento.newPage();
            
            tb = new PdfPTable(3);
            tb.setWidthPercentage(100);
            
            celda = new PdfPCell(new Phrase("Z"));
            celda.setBorder(Rectangle.NO_BORDER);
            tb.addCell(celda);
            
            celda = new PdfPCell(new Phrase("PRODUCTOS"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setColspan(2);
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            tb.addCell(celda);
            
            documento.add(tb);
            
            regP = 0;
            try{// INICIO DEL INFORME DE LOS PRODUCTOS
                cn = DBCON.getConexion();
                sql ="SELECT P.producto_id, P.producto_nombre,"
                    + " COUNT(V.producto_id)AS registro,"
                    + " SUM(V.subtotal) AS subtotal,"
                    + " SUM(V.vr_iva) AS vr_iva,"
                    + " SUM(V.total) AS total"
                    + " FROM ventas AS V,"
                    + " productos AS P,"
                    + " facturas AS F"
                    + " WHERE V.producto_id=P.producto_id "
                    + "AND F.factura_id=V.factura_id "
                    + "AND V.z_diario_id = "+nroZ+" "
                    + "GROUP BY P.producto_id "
                    + "ORDER BY P.producto_id ASC;";
                
                st =cn.createStatement();
                rs = st.executeQuery(sql);
                
                //String producto;
                
                /*
                if(rs.wasNull()){
                    System.out.println("Producto no encontrado. ");
                }
                */

                //PdfPTable tb = new PdfPTable(2);
                tb = new PdfPTable(2);
                
                
                String producto;
                int subTotal;
                int vrIva;
                while(rs.next()){
                    
                    
                    
                    //int id = rs.getInt("producto_id");
                    subTotal = rs.getInt("subtotal");
                    vrIva = rs.getInt("vr_iva");
                    ventaP = rs.getInt("total");
                    regP = rs.getInt("registro");

                    
                    producto = rs.getString("producto_nombre");
                    celda = new PdfPCell(new Phrase(producto));
                    celda.setBorder(Rectangle.NO_BORDER);
                    celda.setHorizontalAlignment(Element.ALIGN_LEFT);
                    celda.setColspan(2);
                    tb.addCell(celda);

                    celda = new PdfPCell(new Phrase(""+regP));
                    celda.setBorder(Rectangle.NO_BORDER);
                    celda.setHorizontalAlignment(Element.ALIGN_LEFT);
                    tb.addCell(celda);
                    
                    celda = new PdfPCell(new Phrase("$ "+ventaP));
                    celda.setBorder(Rectangle.NO_BORDER);
                    celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    tb.addCell(celda);
                    
                    tb.setWidthPercentage(100);
                    //p.add(pr);
                    //documento.add(p);
                    tlrg+= regP;
                    ventaTotal+= ventaP;
                    
                }
                documento.add(tb);
                //p.add(pr);
                //documento.add(p);
                    
                
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null,"ERROR SQL"+e);
            }
            
            regP = tlrg;
            tlrg = regD + regP;
            
            ventaP = ventaTotal;
            
            ventaTotal = ventaD + ventaP;
            
            Rectangle paginaLarga = new Rectangle(230, 800);
            documento.setPageSize(paginaLarga);
            documento.newPage();
            
            tb = new PdfPTable(2);
            tb.setWidthPercentage(100);
            
            celda= new PdfPCell(new Phrase("TL"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            tb.addCell(celda);
            
            
            celda= new PdfPCell(new Phrase(""+regP));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tb.addCell(celda);
            
            celda= new PdfPCell(new Phrase("$ "+ventaP));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            celda.setColspan(2);
            celda.setPaddingBottom(-10);
            tb.addCell(celda);
            
            
            documento.add(new Paragraph(new Phrase(linea)));
            documento.add(tb);
            documento.add(new Paragraph(new Phrase(linea)));
            
            tb = new PdfPTable(3);
            tb.setWidthPercentage(100);
            tb.setSpacingAfter(0);
            celda = new PdfPCell(new Phrase("Z"));
            celda.setBorder(Rectangle.NO_BORDER);
            tb.addCell(celda);
            
            celda = new PdfPCell(new Phrase("TOT. FIJOS"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            tb.addCell(celda);
            
            celda = new PdfPCell(new Phrase("2131"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tb.addCell(celda);
            
            celda = new PdfPCell(new Phrase("0001011 "));
            celda.setColspan(3);
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            celda.setPaddingBottom(10);
            tb.addCell(celda);
            
            documento.add(tb);
            //documento.add(new Phrase(linea));
            
            
            tb = new PdfPTable(2);
            tb.setWidthPercentage(100);
            
            celda = new PdfPCell(new Phrase("BRUTO"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            tb.addCell(celda);
            
            
            celda = new PdfPCell(new Phrase(""+tlrg));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tb.addCell(celda);
            
            celda = new PdfPCell(new Phrase("$ "+ventaTotal));
            celda.setColspan(2);
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tb.addCell(celda);
            
            
            documento.add(tb);
            
            
            tb = new PdfPTable(2);
            tb.setWidthPercentage(100);
            
            celda = new PdfPCell(new Phrase("NETO"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            tb.addCell(celda);
            
            
            celda = new PdfPCell(new Phrase(""+tlrg));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tb.addCell(celda);
            
            celda = new PdfPCell(new Phrase("$ "+ventaTotal));
            celda.setColspan(2);
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tb.addCell(celda);
            
            
            celda = new PdfPCell(new Phrase("EFECTIVO"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            tb.addCell(celda);
            
            
            celda = new PdfPCell(new Phrase("$ "+ventaTotal));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tb.addCell(celda);
            
            
            
            //PREGUNTAR SI LA SIGUIENTE INFORMACIÓN ES NECESARIA:
            
            
            
            // QUÉ ES CARGEC
            celda = new PdfPCell(new Phrase("CARGEC"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            tb.addCell(celda);
            
            
            celda = new PdfPCell(new Phrase("$ 0"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tb.addCell(celda);
            
            
            
            
            celda = new PdfPCell(new Phrase("CHEQUES"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            tb.addCell(celda);
            
            
            celda = new PdfPCell(new Phrase("$ 0"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tb.addCell(celda);
            
            
            
            celda = new PdfPCell(new Phrase("CRID(1)"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            tb.addCell(celda);
            
            
            celda = new PdfPCell(new Phrase("$ 0"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tb.addCell(celda);
            
            
            
            celda = new PdfPCell(new Phrase("CRID(2)"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            tb.addCell(celda);
            
            
            celda = new PdfPCell(new Phrase("$ 0"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tb.addCell(celda);
            
            
            celda = new PdfPCell(new Phrase("CRID(3)"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            tb.addCell(celda);
            
            
            celda = new PdfPCell(new Phrase("$ 0"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tb.addCell(celda);
            
            
            
            celda = new PdfPCell(new Phrase("CRID(4)"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            tb.addCell(celda);
            
            
            celda = new PdfPCell(new Phrase("$ 0"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tb.addCell(celda);
            
            
            //PREGUNTAR SI LA INFORMACIÓN ANTERIOR ES NECESARIA:

            documento.add(tb);
            //documento.newPage();
            documento.add(new Phrase(linea));
            
            
            
            tb = new PdfPTable(2);
            tb.setWidthPercentage(100);
            
            //PREGUNTAR SI LA SIGUIENTE INFORMACIÓN ES NECESARIA:
            
            
            
            // QUÉ ES 
            celda = new PdfPCell(new Phrase("C-1"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            tb.addCell(celda);
            
            
            
            celda = new PdfPCell(new Phrase("$ 0"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tb.addCell(celda);
            
            
            
            // QUÉ ES 
            celda = new PdfPCell(new Phrase("C-2"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            tb.addCell(celda);
            
            
            celda = new PdfPCell(new Phrase("$ 0"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tb.addCell(celda);
            
            
            // QUÉ ES 
            celda = new PdfPCell(new Phrase("CECA1"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            tb.addCell(celda);
            
            
            celda = new PdfPCell(new Phrase("*0"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tb.addCell(celda);
            
            
            // QUÉ ES 
            celda = new PdfPCell(new Phrase("CECK1"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            tb.addCell(celda);
            
            
            celda = new PdfPCell(new Phrase("*0"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tb.addCell(celda);
            
            // QUÉ ES 
            celda = new PdfPCell(new Phrase("CECA2"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            tb.addCell(celda);
            
            
            celda = new PdfPCell(new Phrase("*0"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tb.addCell(celda);
            
            
            // QUÉ ES 
            celda = new PdfPCell(new Phrase("CECK2"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            tb.addCell(celda);
            
            
            celda = new PdfPCell(new Phrase("*0"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tb.addCell(celda);
            
            
            // QUÉ ES 
            celda = new PdfPCell(new Phrase("DC"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            tb.addCell(celda);
            
            
            celda = new PdfPCell(new Phrase("$ 0"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tb.addCell(celda);
            
            celda = new PdfPCell(new Phrase("$ 0"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setColspan(2);
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tb.addCell(celda);
            
            
            
            // ESTO ES LO ÚNICO QUE VEO QUE SIRVE DE ESTA PARTE
            celda = new PdfPCell(new Phrase("DEVOLUCIÓNES"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            tb.addCell(celda);
            
            
            celda = new PdfPCell(new Phrase("$ 614.100"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tb.addCell(celda);
            
            
            
            celda = new PdfPCell(new Phrase("REDONDÉO"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            tb.addCell(celda);
            
            
            celda = new PdfPCell(new Phrase("$ 0"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tb.addCell(celda);
            
            
            celda = new PdfPCell(new Phrase("CANCELADO"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            tb.addCell(celda);
            
            
            celda = new PdfPCell(new Phrase("0"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tb.addCell(celda);
            
            
            celda = new PdfPCell(new Phrase("$ 0"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setColspan(2);
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tb.addCell(celda);
            
            
            //PREGUNTAR SI LA INFORMACIÓN ANTERIOR ES NECESARIA:
            
            documento.add(tb);
            
            
            
            //documento.newPage();
            documento.add(new Phrase(linea));
            
            
            
            // ESTA ES LA ÚNICA SECCIÓN QUE VEO IMPORTANTE
            tb = new PdfPTable(2);
            tb.setWidthPercentage(100);
            
            
            
            // QUÉ ES 
            celda = new PdfPCell(new Phrase("BASE VENTA"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            tb.addCell(celda);
            
            
            
            celda = new PdfPCell(new Phrase("$ 0"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tb.addCell(celda);
            
            
            
            // QUÉ ES 
            celda = new PdfPCell(new Phrase("IVA "+"19"+" %"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            tb.addCell(celda);
            
            
            celda = new PdfPCell(new Phrase("$ 0"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tb.addCell(celda);
            
            
            // QUÉ ES 
            celda = new PdfPCell(new Phrase("BASE2"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            tb.addCell(celda);
            
            
            celda = new PdfPCell(new Phrase("$ 0"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tb.addCell(celda);
            
            
            // QUÉ ES 
            celda = new PdfPCell(new Phrase("IVA2"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            tb.addCell(celda);
            
            
            celda = new PdfPCell(new Phrase("$ 0"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tb.addCell(celda);
            
            // QUÉ ES 
            celda = new PdfPCell(new Phrase("EXENTO"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            tb.addCell(celda);
            
            
            celda = new PdfPCell(new Phrase("$ 0"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tb.addCell(celda);
            
            
            // QUÉ ES 
            celda = new PdfPCell(new Phrase("EXENTO"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            tb.addCell(celda);
            
            
            celda = new PdfPCell(new Phrase("$ 0"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tb.addCell(celda);
            
            // QUÉ ES 
            celda = new PdfPCell(new Phrase("SIN TASA"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            tb.addCell(celda);
            
            
            celda = new PdfPCell(new Phrase("$ 0"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tb.addCell(celda);
            
            
            celda = new PdfPCell(new Phrase("567515"+"--->"+"567815"));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setColspan(2);
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tb.addCell(celda);
            
            
            documento.add(tb);

            
            
            
            
            //documento.add(new Phrase(linea));
            //documento.add(new Phrase(linea));
            //documento.add(new Phrase(linea));
            //documento.add(new Phrase(linea));
            
            documento.close();
            archivo.close();
            mostrarArchivo(file);
            
            return true;
            
        }catch(IOException e){
            JOptionPane.showMessageDialog(null,"Error generando el reporte.\n"+e);
            Logger.getLogger(InformeVentasController.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return false;
    }
    
    public void mostrarArchivo(File archivo){
        try{
            Desktop.getDesktop().open(archivo);
        }catch(IOException e){
            JOptionPane.showMessageDialog(null,"ERROR: "+e);
        }
    }
    
    public static void main(String [] args){
        
        ZDiarioDAO dao = new ZDiarioDAO();
        
        int nroRegistros = dao.obtenerTotalRegistros(1);
        
        try {
            boolean imprimible = dao.crearTicketZDiarioPDF("/home/b41n/Escritorio/Z-Diario",1);
            
            System.out.println(imprimible);
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(ZDiarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Registros: "+nroRegistros);
        
        /*
        String[] z = dao.obtenerRegistros(1);
        
        for (int i = 0; i <=z.length-1; i++){
            System.out.println(z[i]);
        }
        */
    }
    
}
