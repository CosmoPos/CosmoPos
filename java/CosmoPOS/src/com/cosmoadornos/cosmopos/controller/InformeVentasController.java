/*
 * Esta aplicación fue desarrollada por Brayan Novoa.
 * Si desea ponerse en contacto por alguna duda no
 * dude en escribir al siguiente correo.
 * bnovoa.linux@gmail.com
 */
package com.cosmoadornos.cosmopos.controller;

import com.cosmoadornos.cosmopos.model.Empresa;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author b41n
 */
public class InformeVentasController {
    
    ZDiarioController zdao = new ZDiarioController();
    private final DbCon DBCON = new DbCon();
    private Connection cn;
    Empresa empresa;
    //DatosEmpresa datos;
    
    public static final String LOGO = "src/com/cosmoadornos/cosmopos/icons/cosmo.png";
    private String sql;
    protected Statement st;
    protected ResultSet rs;
    protected ResultSet rse;
    
    private int subTotal = 0;
    private int vrIva = 0;
    
    private int subTotalP = 0;
    private int subTotalD = 0;
    private int subTotalT = 0;
    
    private int vrIvaP = 0;
    private int vrIvaD = 0;
    private int vrIvaT = 0;
    
    private int totalP = 0;
    private int totalD = 0;
    private int totalT = 0;
    
    private int registros = 0;
    //ArrayList<Producto> productos = new ArrayList();
    

    public InformeVentasController() {
    
    }
    
    public boolean generarReporte(/*ArrayList<Producto> productos,*/ String ruta, int nroZ/*, String fecha*/) throws DocumentException, FileNotFoundException, SQLException{
        //productos = new ArrayList();
        
        String[] z = zdao.obtenerRegistros(nroZ);
        registros = zdao.obtenerTotalRegistros(nroZ);
        
        LocalDate fechaF = LocalDate.now(); // Create a date object
        
        Document documento = new Document(PageSize.A4, 0f, 0f, 60f, 0f);
        FileOutputStream archivo;
        File file= new File(ruta+".pdf");
        archivo = new FileOutputStream(file);
        PdfWriter.getInstance(documento, archivo);
        try{
            cn= DBCON.getConexion();
            
            
             // Creates some fonts
            Font blod = new Font(Font.FontFamily.COURIER, 8,
                    Font.BOLD);
            Font italic = new Font(Font.FontFamily.HELVETICA, 10,
                    Font.ITALIC);
            Font red = new Font(Font.FontFamily.TIMES_ROMAN, 12,
                    Font.ITALIC | Font.UNDERLINE, BaseColor.RED);
            
            documento.open();
            
            
            PdfPCell celda;
            
            
            EmpresaController empCon = new EmpresaController();
            
            
            empresa = empCon.buscarDatosEmpresa("NIT EMPRESA");
            //datos = datosCon.buscarDatosEmpresa();
            
            System.out.println("Empresa: "+empresa.getCompanyNIT());
            //documento.add(new Paragraph("Empresa: "+datos.getNombre()));
            
            
            //Creación de la tabla que contiene los datos de la empresa.
            
            PdfPTable tbEmpresa = new PdfPTable(9);
            tbEmpresa.setWidthPercentage(95);
            
            celda = new PdfPCell(new Phrase("Informe de ventas de la facturación POS"));
            //celda = new PdfPCell(new Phrase("INFORME: "+"Informe de ventas de la facturación POS", blod));
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda.setColspan(9);
            tbEmpresa.addCell(celda);
            
            celda = new PdfPCell(new Phrase("Empresa: "+"NOMBRE DE LA EMPRESA", blod));
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            celda.setColspan(6);
            tbEmpresa.addCell(celda);
            
            celda = new PdfPCell(new Phrase("", blod));
            celda.setColspan(3);
            celda.setRowspan(4);
            Image logo = Image.getInstance(LOGO);
            celda.addElement(logo);
            tbEmpresa.addCell(celda);

            /*
            celda = new PdfPCell(new Phrase("???", blod));
            celda.setRowspan(4);
            //celda.setRowspan(7);
            tbEmpresa.addCell(celda);
            */
            celda = new PdfPCell(new Phrase("NIT:"+empresa.getCompanyNIT()+"DIR:"+
                    empresa.getDireccion()+" TEL: "+empresa.getTelefono(), blod));
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            celda.setColspan(6);
            tbEmpresa.addCell(celda);
            
            celda = new PdfPCell(new Phrase(" REG. "+empresa.getRegimen().getRegimenNombre(), blod));
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            celda.setColspan(6);
            tbEmpresa.addCell(celda);
            
            celda = new PdfPCell(new Phrase("Responsabilidades Fiscales: "+"Lista de responsabilidades", blod));
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            celda.setColspan(6);
            tbEmpresa.addCell(celda);
            
            /*
            celda = new PdfPCell(new Phrase("VENTAS", blod));
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            celda.setColspan(5);
            tbEmpresa.addCell(celda);
            */
            
            celda = new PdfPCell(new Phrase("Fecha del\ninfrome", blod));
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
            tbEmpresa.addCell(celda);
            
            /*
            celda = new PdfPCell(new Phrase(""+empresa.getRegimen().getRegimenNombre(), blod));
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            tbEmpresa.addCell(celda);
            */
            
            
            int dia, mes, ano;
            
            dia = fechaF.getDayOfMonth();
            mes = fechaF.getMonthValue();
            ano = fechaF.getYear();
            
            /*
            celda = new PdfPCell(new Phrase(""+dia, blod));
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbEmpresa.addCell(celda);
            
            celda = new PdfPCell(new Phrase(""+mes, blod));
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbEmpresa.addCell(celda);
            
            celda = new PdfPCell(new Phrase(""+ano, blod));
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbEmpresa.addCell(celda);
            */
            
            celda = new PdfPCell(new Phrase("DD\n"+dia, blod));
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbEmpresa.addCell(celda);
            
            celda = new PdfPCell(new Phrase("MM\n"+mes, blod));
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbEmpresa.addCell(celda);
            
            celda = new PdfPCell(new Phrase("AAAA\n"+ano, blod));
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbEmpresa.addCell(celda);
            
            celda = new PdfPCell(new Phrase("", blod));
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda.setRowspan(3);
            tbEmpresa.addCell(celda);
            
            
            celda = new PdfPCell(new Phrase("Numero Z", blod));
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            tbEmpresa.addCell(celda);
            
            celda = new PdfPCell(new Phrase(""+nroZ, blod));
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbEmpresa.addCell(celda);
            
            // Fila que contirnr la fecha y el registro inicial:
            
            celda = new PdfPCell(new Phrase("Fecha del informe:", blod));
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            celda.setColspan(2);
            tbEmpresa.addCell(celda);
            
            
            
            celda = new PdfPCell(new Phrase("RG. inicial", blod));
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            tbEmpresa.addCell(celda);
            
            celda = new PdfPCell(new Phrase(""+z[0], blod));
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbEmpresa.addCell(celda);
            
            celda = new PdfPCell(new Phrase("Software:", blod));
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            celda.setColspan(5);
            tbEmpresa.addCell(celda);
            
            celda = new PdfPCell(new Phrase("RG. final", blod));
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            tbEmpresa.addCell(celda);
            
            celda = new PdfPCell(new Phrase(""+z[1], blod));
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbEmpresa.addCell(celda);
            
            celda = new PdfPCell(new Phrase("VENTAS POR PRODUCTO:", blod));
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda.setColspan(9);
            tbEmpresa.addCell(celda);
            
            /*
            celda = new PdfPCell(new Phrase(""+datos, blod));
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            celda.setColspan(4);
            tbEmpresa.addCell(celda);
            
            
            
            celda = new PdfPCell(new Phrase("", blod));
            tbEmpresa.addCell("Informe Fiscal"+"común");
            tbEmpresa.addCell("Régimen de IVA:"+"común");
            */
            documento.add(tbEmpresa);
            
            //Creación de la tabla que contiene las ventas
            
            PdfPTable tbVentas = new PdfPTable(9);
            tbVentas.setWidthPercentage(95);
            
            PdfPTable tbTitulos = new PdfPTable(9);
            tbTitulos.setWidthPercentage(95);
            
            
            celda = new PdfPCell(new Phrase("PRODUCTO",blod));
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
            celda.setRowspan(2);
            tbTitulos.addCell(celda);
            
            celda = new PdfPCell(new Phrase("DESCRIPCON",blod));
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
            celda.setRowspan(2);
            tbTitulos.addCell(celda);
            
            celda = new PdfPCell(new Phrase("TIPO DE OPERACION",blod));
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
            celda.setColspan(3);
            tbTitulos.addCell(celda);
            
            
            celda = new PdfPCell(new Phrase("TARIFA",blod));
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
            celda.setRowspan(2);
            tbTitulos.addCell(celda);
            
            celda = new PdfPCell(new Phrase("SUBTOTAL",blod));
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
            celda.setRowspan(2);
            tbTitulos.addCell(celda);
            
            celda = new PdfPCell(new Phrase("V/r IVA",blod));
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
            celda.setRowspan(2);
            tbTitulos.addCell(celda);
            
            celda = new PdfPCell(new Phrase("TOTAL",blod));
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
            celda.setRowspan(2);
            tbTitulos.addCell(celda);
            
            
            celda = new PdfPCell(new Phrase("GRAVADA",blod));
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
            tbTitulos.addCell(celda);
            
            celda = new PdfPCell(new Phrase("EXCENTA",blod));
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
            tbTitulos.addCell(celda);
            
            celda = new PdfPCell(new Phrase("EXCLUIDA",blod));
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
            tbTitulos.addCell(celda);
            
            try{
                documento.add(tbTitulos);
                sql ="SELECT P.producto_id, P.producto_nombre,"
                + " SUM(V.total_sin_iva) AS total_sin_iva,"
                + " SUM(V.total_iva) AS total_iva,"
                + " SUM(V.total_con_iva) AS total_con_iva"
                + " FROM ventas AS V,"
                + " productos AS P,"
                + " facturas AS F"
                + " WHERE V.producto_id=P.producto_id"
                + " AND F.factura_id=V.factura_id "
                //+ "AND F.fecha=\""+fecha+"\" "
                + " AND V.z_diario_id = "+nroZ+" "
                + " GROUP BY P.producto_id "
                + " ORDER BY P.producto_id ASC;";

                st =cn.createStatement();
                rs = st.executeQuery(sql);
                int id;
                String producto;
                /*
                if(rs.wasNull()){
                    System.out.println("Producto no encontrado. ");
                }
                */
                System.out.println(sql);
                while(rs.next()){
                    id = rs.getInt("producto_id");
                    producto = rs.getString("producto_nombre");
                    subTotal = rs.getInt("total_sin_iva");
                    vrIva = rs.getInt("total_iva");
                    totalP = rs.getInt("total_con_iva");

                    /*
                    System.out.println(""+id);
                    System.out.println(""+producto);
                    System.out.println(""+subTotal);
                    System.out.println(""+vrIva);
                    System.out.println(""+totalP);
                    System.out.println("Agregando Producto Al informe");
                    */

                    celda = new PdfPCell(new Phrase(""+id,italic));
                    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
                    celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tbVentas.addCell(celda);

                    celda = new PdfPCell(new Phrase(producto,italic));
                    celda.setHorizontalAlignment(Element.ALIGN_LEFT);
                    celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tbVentas.addCell(celda);

                    celda = new PdfPCell(new Phrase("",italic));
                    tbVentas.addCell(celda);

                    celda = new PdfPCell(new Phrase("",italic));
                    tbVentas.addCell(celda);

                    celda = new PdfPCell(new Phrase("",italic));
                    tbVentas.addCell(celda);


                    celda = new PdfPCell(new Phrase("19%",blod));
                    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
                    celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tbVentas.addCell(celda);

                    celda = new PdfPCell(new Phrase(""+subTotal,italic));
                    celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tbVentas.addCell(celda);


                    celda = new PdfPCell(new Phrase(""+vrIva,italic));
                    celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tbVentas.addCell(celda);

                    celda = new PdfPCell(new Phrase(""+totalP,italic));
                    celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tbVentas.addCell(celda);

                    subTotalP += subTotal;
                    vrIvaP +=vrIva;
                    totalT += totalP;
                }
                totalP = totalT;

                celda = new PdfPCell(new Phrase("TOTAL VENTAS POR PRODUCTO", blod));
                celda.setHorizontalAlignment(Element.ALIGN_LEFT);
                celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
                celda.setColspan(6);
                tbVentas.addCell(celda);

                celda = new PdfPCell(new Phrase(""+subTotalP ,blod));
                celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
                tbVentas.addCell(celda);

                celda = new PdfPCell(new Phrase(""+vrIvaP, blod));
                celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
                tbVentas.addCell(celda);

                celda = new PdfPCell(new Phrase(""+totalP, blod));
                celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
                tbVentas.addCell(celda);

                totalT = 0;
                subTotalT = 0;

                celda = new PdfPCell(new Phrase("VENTAS POR DEPARTAMENTO:", blod));
                celda.setHorizontalAlignment(Element.ALIGN_CENTER);
                celda.setColspan(9);
                tbVentas.addCell(celda);

                documento.add(tbVentas);

            //} Cierre del for removido.
            }catch(SQLException ex){
                Logger.getLogger(InformeVentasController.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error SQL: "+ex);
            }
            
            
            PdfPTable tbDeptos = new PdfPTable(9);
            tbDeptos.setWidthPercentage(95);
            
            
            
            //documento.add(tbDeptos);
            
            try{
                documento.add(tbTitulos);
                /*
                for(int i = 0 ; i <= count-1; i++){
                    //productoId=i+1;
                    
                    System.out.println("Índice del array: "+(i+1));
                    System.out.println("Id:"+productos.get(i).getId());
                    System.out.println("Nombre:"+productos.get(i).getNombre());
                    */
                
                    //productoId = i;
                    
                    // Actualizar
                    sql ="SELECT "
                            + "P.producto_id, "
                            + "L.linea_id, "
                            + "L.linea_nombre, "
                            //+ "P.producto_nombre, "
                            + "SUM(V.total_sin_iva) AS total_sin_iva, "
                            + "SUM(V.total_iva) AS total_iva, "
                            + "SUM(V.total_con_iva) AS total_con_iva "
                            + "FROM ventas AS V, "
                            + "lineas AS L, "
                            + "productos AS P, "
                            + "facturas AS F "
                            + "WHERE "
                            + "V.producto_id=P.producto_id "
                            + "AND "
                            + "P.linea_id=L.linea_id "
                            + "AND "
                            + "F.factura_id=V.factura_id "
                            //+ "AND F.fecha=\""+fecha+"\" "
                            + "AND V.z_diario_id = "+nroZ+" "
                            + "GROUP BY L.linea_id "
                            + "ORDER BY L.linea_id ASC;";
                    
                    st =cn.createStatement();
                    rs = st.executeQuery(sql);
                    int id;
                    String producto;
                    
                    /*
                    if(rs.wasNull()){
                        System.out.println("Producto no encontrado. ");
                    }
                    */
                    
                    while(rs.next()){
                        //System.out.println(sql);
                        System.out.println("Producto encontrado. ");
                        id = rs.getInt("L.linea_id");
                        producto = rs.getString("L.linea_nombre");
                        subTotal = rs.getInt("total_sin_iva");
                        vrIva = rs.getInt("total_iva");
                        totalD = rs.getInt("total_con_iva");
                        
                        celda = new PdfPCell(new Phrase(""+id,italic));
                        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
                        celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        tbDeptos.addCell(celda);

                        celda = new PdfPCell(new Phrase(producto,italic));
                        celda.setHorizontalAlignment(Element.ALIGN_LEFT);
                        celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        tbDeptos.addCell(celda);

                        celda = new PdfPCell(new Phrase("",italic));
                        tbDeptos.addCell(celda);

                        celda = new PdfPCell(new Phrase("",italic));
                        tbDeptos.addCell(celda);

                        celda = new PdfPCell(new Phrase("",italic));
                        tbDeptos.addCell(celda);


                        celda = new PdfPCell(new Phrase("19%",blod));
                        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
                        celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        tbDeptos.addCell(celda);

                        celda = new PdfPCell(new Phrase(""+subTotal,italic));
                        celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        tbDeptos.addCell(celda);


                        celda = new PdfPCell(new Phrase(""+vrIva,italic));
                        celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        tbDeptos.addCell(celda);

                        celda = new PdfPCell(new Phrase(""+totalD,italic));
                        celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        tbDeptos.addCell(celda);
                    
                        subTotalD += subTotal;
                        vrIvaD +=vrIva;
                        totalT += totalD;
                    }
                    
                    totalD = totalT;
                    subTotalT = subTotalD +subTotalP;
                    vrIvaT = vrIvaD + vrIvaP;
                    totalT = totalP + totalD;
                    
                    celda = new PdfPCell(new Phrase("TOTAL VENTAS POR DEPARTAMENTO", blod));
                    celda.setHorizontalAlignment(Element.ALIGN_LEFT);
                    celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    celda.setColspan(6);
                    tbDeptos.addCell(celda);

                    celda = new PdfPCell(new Phrase(""+subTotalD ,blod));
                    celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    tbDeptos.addCell(celda);

                    celda = new PdfPCell(new Phrase(""+vrIvaD, blod));
                    celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    tbDeptos.addCell(celda);

                    celda = new PdfPCell(new Phrase(""+totalD, blod));
                    celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    tbDeptos.addCell(celda);

                    documento.add(tbDeptos);
                        
                
                //} Cierre del for removido.
                }catch(SQLException ex){
                    Logger.getLogger(InformeVentasController.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Error SQL: "+ex);
                }
            
            
            //documento.add(tbTitulos);

            PdfPTable tbSumaTotales = new PdfPTable(9);
            tbSumaTotales.setWidthPercentage(95);
            
            
            celda = new PdfPCell(new Phrase("TOTAL VENTAS PRODUCTOS & DEPARTAMENTOS", blod));
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
            celda.setColspan(6);
            tbSumaTotales.addCell(celda);

            celda = new PdfPCell(new Phrase(""+subTotalT ,blod));
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbSumaTotales.addCell(celda);

            celda = new PdfPCell(new Phrase(""+vrIvaT, blod));
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbSumaTotales.addCell(celda);

            celda = new PdfPCell(new Phrase(""+totalT, blod));
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbSumaTotales.addCell(celda);

            documento.add(tbSumaTotales);
            
            
            PdfPTable tbTotales = new PdfPTable(9);
            tbTotales.setWidthPercentage(95);
            
            
            /*
            
            celda = new PdfPCell(new Phrase("FACTURAS MANUALES", blod));
            celda.setColspan(6);
            tbTotales.addCell(celda);
            
            
            celda = new PdfPCell(new Phrase("", blod));
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            tbTotales.addCell(celda);
            
            celda = new PdfPCell(new Phrase("", blod));
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbTotales.addCell(celda);
            
            celda = new PdfPCell(new Phrase("", blod));
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbTotales.addCell(celda);
            
            
            celda = new PdfPCell(new Phrase("No Factura", blod));
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            tbTotales.addCell(celda);
            
            celda = new PdfPCell(new Phrase("Inicial", blod));
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbTotales.addCell(celda);
            
            celda = new PdfPCell(new Phrase("Final", blod));
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbTotales.addCell(celda);
            
            celda = new PdfPCell(new Phrase("", blod));
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            celda.setColspan(6);
            tbTotales.addCell(celda);
            
            celda = new PdfPCell(new Phrase("", blod));
            celda.setColspan(9);
            tbTotales.addCell(celda);
            
            celda = new PdfPCell(new Phrase("TOTAL FACTURA MANUAL", blod));
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            celda.setColspan(6);
            tbTotales.addCell(celda);
            
            celda = new PdfPCell(new Phrase("subtotal", blod));
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbTotales.addCell(celda);
            
            celda = new PdfPCell(new Phrase("vr/iva", blod));
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbTotales.addCell(celda);
            
            celda = new PdfPCell(new Phrase("total", blod));
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbTotales.addCell(celda);
            
            celda = new PdfPCell(new Phrase("TOTAL Z - FACTURAS MANUALES", blod));
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            celda.setColspan(6);
            tbTotales.addCell(celda);
            
            celda = new PdfPCell(new Phrase("subtotal", blod));
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbTotales.addCell(celda);
            
            celda = new PdfPCell(new Phrase("vr/iva", blod));
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbTotales.addCell(celda);
            
            celda = new PdfPCell(new Phrase("total", blod));
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbTotales.addCell(celda);
            */
            
            PdfPTable tbFinal = new PdfPTable(9);
            tbFinal.setWidthPercentage(95);
            
            
            
            celda = new PdfPCell(new Phrase("Medio Pago", blod));
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbFinal.addCell(celda);
            
            celda = new PdfPCell(new Phrase("No. Registros", blod));
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbFinal.addCell(celda);
            celda = new PdfPCell(new Phrase("Valor Operación", blod));
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            tbFinal.addCell(celda);
            
            celda = new PdfPCell(new Phrase("", blod));
            celda.setColspan(6);
            celda.setRowspan(6);
            tbFinal.addCell(celda);
            
            celda = new PdfPCell(new Phrase("Efectivo", blod));
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            tbFinal.addCell(celda);
            
            celda = new PdfPCell(new Phrase(""+registros, blod));
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbFinal.addCell(celda);
            
            celda = new PdfPCell(new Phrase(""+totalT, blod));
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbFinal.addCell(celda);
            
            
            celda = new PdfPCell(new Phrase("Cheques", blod));
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            tbFinal.addCell(celda);
            
            celda = new PdfPCell(new Phrase("0", blod));
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbFinal.addCell(celda);
            
            celda = new PdfPCell(new Phrase("$", blod));
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbFinal.addCell(celda);
            
            
            celda = new PdfPCell(new Phrase("Débito", blod));
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            tbFinal.addCell(celda);
            
            celda = new PdfPCell(new Phrase("0", blod));
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbFinal.addCell(celda);
            
            celda = new PdfPCell(new Phrase("$", blod));
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbFinal.addCell(celda);
            
            
            celda = new PdfPCell(new Phrase("Crédito", blod));
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            tbFinal.addCell(celda);
            
            celda = new PdfPCell(new Phrase("0", blod));
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbFinal.addCell(celda);
            
            celda = new PdfPCell(new Phrase("$", blod));
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbFinal.addCell(celda);
            
            
            celda = new PdfPCell(new Phrase("TOTALES", blod));
            celda.setHorizontalAlignment(Element.ALIGN_LEFT);
            tbFinal.addCell(celda);
            
            celda = new PdfPCell(new Phrase(""+registros, blod));
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbFinal.addCell(celda);
            
            celda = new PdfPCell(new Phrase("$"+totalT, blod));
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tbFinal.addCell(celda);
            
            //documento.add(tbVentas);
            
            //documento.add(tbDeptos);
            
            documento.add(tbTotales);
            
            documento.add(tbFinal);
                
            documento.close();
            archivo.close();
            mostrarReporte(file);
            //Retorna verdadero en caso que se haya creado el documento.
            return true;
            //Fin del try que genera el documento.
        }catch(IOException e){
            JOptionPane.showMessageDialog(null,"Error generando el reporte.\n"+e);
            Logger.getLogger(InformeVentasController.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }
    
    public void mostrarReporte(File archivo){
        try{
            Desktop.getDesktop().open(archivo);
        }catch(IOException e){
            JOptionPane.showMessageDialog(null,"ERROR: "+e);
        }
    }

    
    
    public static void main(String []args) {
        String ruta ="/home/b41n/Escritorio/archivo";
        String fecha="2020-01-17";
        
        
        InformeVentasController infocon = new InformeVentasController();
        /*
        ArrayList<Producto> listaProductos = new ArrayList();
        listaProductos.add(new Producto(1,"1","",1,1));
        listaProductos.add(new Producto(2,"2"));
        listaProductos.add(new Producto(3,"3"));
        listaProductos.add(new Producto(4,"4"));
        listaProductos.add(new Producto(5,"5"));
        listaProductos.add(new Producto(6,"6"));
        listaProductos.add(new Producto(7,"7"));
        listaProductos.add(new Producto(10,"10"));
        */
        try {
            infocon.generarReporte(/*listaProductos, */ruta, 1/*, fecha*/);
        } catch (DocumentException | FileNotFoundException | SQLException ex) {
            Logger.getLogger(InformeVentasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
