/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmoadornos.cosmopos.view;


import com.cosmoadornos.cosmopos.controller.ClienteController;
import com.cosmoadornos.cosmopos.controller.DianController;
import com.cosmoadornos.cosmopos.controller.EmpresaController;
import com.cosmoadornos.cosmopos.controller.FacturaController;
import com.cosmoadornos.cosmopos.controller.InformeVentasController;
import com.cosmoadornos.cosmopos.controller.LineaController;
import com.cosmoadornos.cosmopos.controller.ProductoController;
import com.cosmoadornos.cosmopos.controller.UsuarioController;
import com.cosmoadornos.cosmopos.controller.VentaController;
import com.cosmoadornos.cosmopos.controller.ZDiarioController;
import com.cosmoadornos.cosmopos.model.Cliente;
import com.cosmoadornos.cosmopos.model.Dian;
import com.cosmoadornos.cosmopos.model.Empresa;
import com.cosmoadornos.cosmopos.model.Factura;
import com.cosmoadornos.cosmopos.model.Linea;
import com.cosmoadornos.cosmopos.model.Producto;
import com.cosmoadornos.cosmopos.model.TipoProducto;
import com.cosmoadornos.cosmopos.model.Usuario;
import com.cosmoadornos.cosmopos.model.Venta;
import com.cosmoadornos.cosmopos.model.ZDiario;
import com.itextpdf.text.DocumentException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.Insets;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Brayan Novoa <bnovoa.linux@gmail.com at cosmoadornos.com>
 */
public class VentasWhite extends javax.swing.JFrame {
    
    private Empresa empresa;
    EmpresaController empcon = new EmpresaController();
    
    
    LineaController licon = new LineaController();
    ArrayList<Linea> listaLineas;
    
    /*
    DeptoDAO dao = new DeptoDAO();
    ArrayList<Departamento> listaDepartamentos;
    */
    DefaultTableModel modelLineas;
    
    ArrayList<Usuario> listaUsuarios;
    
    ZDiarioController zdao = new ZDiarioController();
    
    ArrayList<Producto> lista;
    ArrayList<Venta> ventas;

    UsuarioController ucon = new UsuarioController();
    
    ArrayList<Producto> productos;
    
    DianController diancon = new DianController();
    
    VentaController ventaCon = new VentaController();
    FacturaController facon = new FacturaController();
    ProductoController procon = new ProductoController();
    ClienteController clicon = new ClienteController();
    DecimalFormat formatea = new DecimalFormat("###,###.##");
    //Datos básicos para el funcionamiento:
    // Empresa, DIAN, Productos, Clientes
    
    private String tipoFiltro;
    
    byte tipoVenta;
    int productoId;
    String medida;
    int departamentoId;
    
    
    String fecha;
    String hora /*="16:46:55"*/;
    
    Dian dian;
    public static Cliente cliente;
    Factura factura;
    
    Float facturaSubTotal;
    Float facturaIVA;
    Float facturaTotal;
    
    DefaultTableModel modelD, modelo, modelP;
    
    int factNum;
    
    
    int rowSelected;
    int rowProducto;
    
    
    Producto producto = null;
    Linea linea = null;
    
    
    //PrinterJob
    
    
    //Variables primitivas
    String productoNombre;
    
    float productoIVA;
    float precioSinIVA;
    float precio;
    float totalProducto;
    
    float IVA;
    float subTotal;
    float cantidad;
    String precioM;
    
    float subTotalVenta;
    float totalIVA;
    float totalVenta;
    
    String referencia;
    String subTipo;
    int subTipoId;
    int existencias;
    
    private int nroZ;
    
    //String[] venta ={producto, ""+precio+"", ""+cantidad+"", ""+IVA+"", ""+subTotal+"", ""+total+""};
    

    /**
     * Creates new form Main
     */
    public VentasWhite() {
        initComponents();
        setLocationRelativeTo(null);
        //filtroFactura.setEditable(false);
        //btnBuscarFactura.setEnabled(false);
        //btnConfig.setEnabled(false);
        if(validarZ()){
            setFecha(0);
            setHora();
            //nroZ=1;
        }else{
            bloquearTodo();
        }
    }
    private  boolean validarZ(){
        if(!ZDiarioController.validarZDiario()){
            JOptionPane.showMessageDialog(null, "Error iniciando la Z");
            int opt;
            opt = JOptionPane.showConfirmDialog(null, "Desea iniciar la numeración?");
            if(opt==0){
                iniciarInterface();
                String fechaV;
                fechaV = setFecha(0);
                nroZ=1;
                JOptionPane.showMessageDialog(null, "La z del día es: "+nroZ);
                return ZDiarioController.iniciarNumeracion(fechaV);
            }else if(opt == 1 || opt == 2){
                JOptionPane.showMessageDialog(null, "Para iniciar las ventas debe iniciar la numeración");
                bloquearTodo();
                return false;
            }
            return true;
        }else{
            iniciarInterface();
            nroZ =ZDiarioController.obtenerUltimoZDiario();
            if(nroZ>0){
                //JOptionPane.showMessageDialog(null, "La z del día es: "+nroZ);
                return true;
            }else{
                JOptionPane.showMessageDialog(null, "Error obteniendo la Z.");
                return false;
            }
        }
    }
    
    private void iniciarInterface(){
        setHora();
        //empresa = new Empresa();
        empresa = empcon.buscarDatosEmpresa("NIT EMPRESA");
        //factura.setEmpresa(empresa);
        //JOptionPane.showMessageDialog(null, "Resolucion:"+empresa.getDian().getTextResol());
        //dian = empresa.getDian();
        //dian = diancon.obtenerDianEmpresa("NIT EMPRESA"); //////////
        obtenerLineas();
        obtenerProductos();
        obtenerUsuarios();
        btnImprimirRecibo.setEnabled(false);
        resetFactura();
        
    }
    
    private void obtenerLineas(){
        listaLineas = licon.obtenerListaLineas();
        llenarTablaLineas();
    }
    private void llenarTablaLineas(){
        //String[] campos={"Departamento"};
        //modelLineas = new DefaultTableModel(null, campos);
        modelLineas = (DefaultTableModel) tbDeptos.getModel();
        modelLineas.setRowCount(0);
        
        for(int i=0; i<= listaLineas.size()-1; i++){
            String[] registro={
                listaLineas.get(i).getLineaNombre()
            };
            modelLineas.addRow(registro);
            tbDeptos.setRowHeight(i,30);
        }
        /*
        for(int i=0; i<= listaDepartamentos.size()-1; i++){
        }
        */
        //tbDeptos.setRowHeight(3, 60);
        tbDeptos.setModel(modelLineas);
    }
    
    private void obtenerUsuarios(){
        listaUsuarios = ucon.obtenerUsuarios();
        for(int i = 0 ; i <= listaUsuarios.size()-1 ; i++){
            cmbVendedores.addItem(listaUsuarios.get(i).getUsuario());
        }
    }
    
    private String setFecha(int evento){
        
        LocalDate f = LocalDate.now(); // Create a date object
        fecha = ""+f;
        System.out.println("Fecha:"+fecha); // Display the current date
        //JOptionPane.showMessageDialog(null,"Fecha: "+fecha);
        /*
        int ano, dia, mes;
        String fecham ="";
        ano = f.getYear();
        mes = f.getMonthValue();
        dia = f.getDayOfMonth();
        */
        switch (evento){
            case 0:
                //fecham = ano+"-"+mes+"-"+dia;
                txtFecha.setText(fecha);
                break;
            case 1:
                //dia+=1;
                fecha = ""+f;
                break;
            default :
                return fecha;
        }
        return fecha;
    }
    
    private String setHora(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMMM/yyyy hh:mm:s");
        Format format = new SimpleDateFormat("HH:mm:ss");
        this.hora = format.format(new Date());
        return hora;
    }
    
    private void bloquearTodo(){
        resetFactura();
        btnImprimirRecibo.setEnabled(false);
        btnAddCliente.setEnabled(false);
        btnBuscarFactura.setEnabled(false);
        btnBuscarCliente.setEnabled(false);
        filtroProducto.setEnabled(false);
        txtDocumento.setText("");
        btnFactura.setEnabled(false);
        btnRestar.setEnabled(false);
        btnSumar.setEnabled(false);
        clienteFiltro.setText("");
        clienteFiltro.setEditable(false);
        txtFiltroDepto.setText("");
        txtFiltroDepto.setEditable(false);
        txtMonto.setText("");
        txtMonto.setEnabled(false);
        btnAddDepto.setEnabled(false);
    }
    private void resetFactura(){
        factNum= facon.buscarUltimaFactura()+1;
        
        //factNum= facon.buscarUltimaFactura()+1;
        txtNroFactura.setText(""+factNum);
        btnNuevaVenta.setEnabled(false);
        btnBorrarProducto.setEnabled(false);
        btnAsignarDescuento.setEnabled(false);
        btnFEL.setEnabled(false);
        btnCerrarVenta.setEnabled(false);
        //btnImprimirRecibo.setEnabled(false);
        btnDevolucion.setEnabled(false);
        btnFactura.setEnabled(true);
        btnAddDepto.setEnabled(true);
        txtNombre.setText("VENTAS MOSTRADOR");
        txtDocumento.setText("222222222222");
    }
    
    private void activarFactura(){
        btnNuevaVenta.setEnabled(true);
        btnCerrarVenta.setEnabled(true);
        btnFEL.setEnabled(true);
    }
    
    private void obtenerProductos(){
        productos = new ArrayList();
        ventas = new ArrayList();
        lista = procon.buscarProductos();
        llenarTablaProductos();
        
    }
    
    public void llenarTablaProductos(){
        //String[] campos ={"Producto", "Referencia", "Medida", "Precio","Existencias"};
        //model = new DefaultTableModel(null,campos);
        modelP = (DefaultTableModel) tbProductos.getModel();
        
        for(int i = 0 ; i <= lista.size()-1 ; i++){
            productoNombre = lista.get(i).getNombre();
            subTipo = lista.get(i).getSubTipo();
            referencia = lista.get(i).getReferencia();
            precio = lista.get(i).getPrecio();
            //existencias = lista1.getExistencias();
            String[] registro = {
                referencia,
                productoNombre,
                subTipo,
                "$"+formatea.format(precio)
            };
            //LLenamos la tabla con los datos correspondientes
            modelP.addRow(registro);
            tbProductos.setRowHeight(i, 30);
        }
        tbProductos.setModel(modelP);
        formatoTablaProductos();
        
    }
    
    private void formatoTablaProductos(){
        DefaultTableCellRenderer rightRender = new DefaultTableCellRenderer();
        DefaultTableCellRenderer centerRender = new DefaultTableCellRenderer();
        rightRender.setHorizontalAlignment(JLabel.RIGHT);
        centerRender.setHorizontalAlignment(JLabel.CENTER);
        
        //tbProductos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tbProductos.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        
        tbProductos.getColumnModel().getColumn(0).setPreferredWidth(30);
        tbProductos.getColumnModel().getColumn(1).setPreferredWidth(600);//80
        //tbProductos.getColumnModel().getColumn(1).setCellRenderer(centerRender);
        tbProductos.getColumnModel().getColumn(2).setPreferredWidth(95);
        //tbProductos.getColumnModel().getColumn(2).setCellRenderer(centerRender);
        tbProductos.getColumnModel().getColumn(3).setPreferredWidth(70);
        tbProductos.getColumnModel().getColumn(3).setCellRenderer(rightRender);
        /*
        */
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        //headerRenderer.setBackground(new Color(48, 63, 159));//Dark
        //headerRenderer.setBackground(new Color(63,81,181));//Primary
        //headerRenderer.setBackground(new Color(68,138,255));//Accent
        headerRenderer.setBackground(new Color(197,202,233));//Light
        //headerRenderer.setBackground(new Color(239, 198, 46));

        for (int i = 0; i < tbProductos.getModel().getColumnCount(); i++) {
                tbProductos.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }
        for (int i = 0; i < tbFactura.getModel().getColumnCount(); i++) {
                tbFactura.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }
        for (int i = 0; i < tbDeptos.getModel().getColumnCount(); i++) {
                tbDeptos.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }
        /*
        */
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        pnlTop = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        btnReporteZ = new javax.swing.JButton();
        btnConfig = new javax.swing.JButton();
        btnAdmin = new javax.swing.JButton();
        btnCerrarSesion = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        txtFecha = new javax.swing.JLabel();
        pnlMain = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        btnNuevaVenta = new javax.swing.JButton();
        filtroFactura = new javax.swing.JTextField();
        btnBuscarFactura = new javax.swing.JButton();
        btnBorrarProducto = new javax.swing.JButton();
        btnCerrarVenta = new javax.swing.JButton();
        btnFEL = new javax.swing.JButton();
        btnImprimirRecibo = new javax.swing.JButton();
        btnDevolucion = new javax.swing.JButton();
        btnAddCliente = new javax.swing.JButton();
        btnAsignarDescuento = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtNroFactura = new javax.swing.JLabel();
        txtNombre = new javax.swing.JLabel();
        txtDocumento = new javax.swing.JLabel();
        cmbVendedores = new javax.swing.JComboBox<>();
        jPanel11 = new javax.swing.JPanel();
        clienteFiltro = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        btnBuscarCliente = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbDeptos = new javax.swing.JTable();
        txtFiltroDepto = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtMonto = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel17 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        btnAddDepto = new javax.swing.JButton();
        txtLineaProducto = new javax.swing.JLabel();
        txtValor = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        filtroProducto = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbProductos = new javax.swing.JTable();
        txtProducto = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtMedida = new javax.swing.JLabel();
        txtReferencia = new javax.swing.JLabel();
        filtroProductoCodigo = new javax.swing.JTextField();
        txtCantidad = new javax.swing.JTextField();
        btnRestar = new javax.swing.JButton();
        btnSumar = new javax.swing.JButton();
        btnFactura = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbFactura = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        pnlTop.setBackground(new java.awt.Color(33, 33, 35));
        pnlTop.setBorder(null);

        jLabel3.setFont(new java.awt.Font("DejaVu Sans", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(117, 117, 117));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("X");
        jLabel3.setBorder(null);
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        btnReporteZ.setBackground(new java.awt.Color(33, 33, 35));
        btnReporteZ.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cosmoadornos/cosmopos/icons/pdf.png"))); // NOI18N
        btnReporteZ.setBorder(null);
        btnReporteZ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReporteZActionPerformed(evt);
            }
        });

        btnConfig.setBackground(new java.awt.Color(33, 33, 35));
        btnConfig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cosmoadornos/cosmopos/icons/settings.png"))); // NOI18N
        btnConfig.setBorder(new RoundedBorder(10));
        btnConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfigActionPerformed(evt);
            }
        });

        btnAdmin.setBackground(new java.awt.Color(33, 33, 35));
        btnAdmin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cosmoadornos/cosmopos/icons/admin.png"))); // NOI18N
        btnAdmin.setBorder(null);

        btnCerrarSesion.setBackground(new java.awt.Color(33, 33, 35));
        btnCerrarSesion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cosmoadornos/cosmopos/icons/exitb.png"))); // NOI18N
        btnCerrarSesion.setBorder(null);
        btnCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarSesionActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("DejaVu Sans", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(57, 113, 177));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setText("Fecha:");
        jLabel10.setBorder(null);

        txtFecha.setFont(new java.awt.Font("DejaVu Sans", 1, 18)); // NOI18N
        txtFecha.setForeground(new java.awt.Color(255, 255, 255));
        txtFecha.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtFecha.setText("2019-10-16");

        javax.swing.GroupLayout pnlTopLayout = new javax.swing.GroupLayout(pnlTop);
        pnlTop.setLayout(pnlTopLayout);
        pnlTopLayout.setHorizontalGroup(
            pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTopLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(574, 574, 574)
                .addComponent(btnAdmin)
                .addGap(10, 10, 10)
                .addComponent(btnConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(btnReporteZ, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(96, 96, 96)
                .addComponent(btnCerrarSesion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 92, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlTopLayout.setVerticalGroup(
            pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTopLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(txtFecha))
                    .addComponent(btnAdmin)
                    .addComponent(btnConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReporteZ)
                    .addComponent(btnCerrarSesion))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(pnlTopLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(pnlTop, java.awt.BorderLayout.PAGE_START);

        pnlMain.setBackground(new java.awt.Color(255, 255, 255));
        pnlMain.setPreferredSize(new java.awt.Dimension(1080, 680));

        btnNuevaVenta.setBackground(new java.awt.Color(68, 138, 255));
        btnNuevaVenta.setFont(new java.awt.Font("DejaVu Sans", 1, 18)); // NOI18N
        btnNuevaVenta.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevaVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cosmoadornos/cosmopos/icons/new.png"))); // NOI18N
        btnNuevaVenta.setText("Nueva Venta");
        btnNuevaVenta.setBorder(null);
        btnNuevaVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaVentaActionPerformed(evt);
            }
        });

        filtroFactura.setFont(new java.awt.Font("DejaVu Sans", 1, 18)); // NOI18N
        filtroFactura.setForeground(new java.awt.Color(51, 51, 51));
        filtroFactura.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        filtroFactura.setText("Buscar Factura");
        filtroFactura.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        filtroFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filtroFacturaActionPerformed(evt);
            }
        });

        btnBuscarFactura.setBackground(new java.awt.Color(68, 138, 255));
        btnBuscarFactura.setFont(new java.awt.Font("DejaVu Sans", 1, 18)); // NOI18N
        btnBuscarFactura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cosmoadornos/cosmopos/icons/search.png"))); // NOI18N
        btnBuscarFactura.setBorder(null);
        btnBuscarFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarFacturaActionPerformed(evt);
            }
        });

        btnBorrarProducto.setBackground(new java.awt.Color(68, 138, 255));
        btnBorrarProducto.setFont(new java.awt.Font("DejaVu Sans", 1, 18)); // NOI18N
        btnBorrarProducto.setForeground(new java.awt.Color(255, 255, 255));
        btnBorrarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cosmoadornos/cosmopos/icons/clear.png"))); // NOI18N
        btnBorrarProducto.setBorder(null);
        btnBorrarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarProductoActionPerformed(evt);
            }
        });

        btnCerrarVenta.setBackground(new java.awt.Color(68, 138, 255));
        btnCerrarVenta.setFont(new java.awt.Font("DejaVu Sans", 1, 18)); // NOI18N
        btnCerrarVenta.setForeground(new java.awt.Color(255, 255, 255));
        btnCerrarVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cosmoadornos/cosmopos/icons/cash.png"))); // NOI18N
        btnCerrarVenta.setText("POS");
        btnCerrarVenta.setBorder(null);
        btnCerrarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarVentaActionPerformed(evt);
            }
        });

        btnFEL.setBackground(new java.awt.Color(68, 138, 255));
        btnFEL.setFont(new java.awt.Font("DejaVu Sans", 1, 18)); // NOI18N
        btnFEL.setForeground(new java.awt.Color(255, 255, 255));
        btnFEL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cosmoadornos/cosmopos/icons/mas.png"))); // NOI18N
        btnFEL.setText("FEL");
        btnFEL.setBorder(null);
        btnFEL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFELActionPerformed(evt);
            }
        });

        btnImprimirRecibo.setBackground(new java.awt.Color(68, 138, 255));
        btnImprimirRecibo.setFont(new java.awt.Font("DejaVu Sans", 1, 18)); // NOI18N
        btnImprimirRecibo.setForeground(new java.awt.Color(255, 255, 255));
        btnImprimirRecibo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cosmoadornos/cosmopos/icons/print.png"))); // NOI18N
        btnImprimirRecibo.setText("print");
        btnImprimirRecibo.setBorder(null);
        btnImprimirRecibo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirReciboActionPerformed(evt);
            }
        });

        btnDevolucion.setBackground(new java.awt.Color(68, 138, 255));
        btnDevolucion.setFont(new java.awt.Font("DejaVu Sans", 1, 18)); // NOI18N
        btnDevolucion.setForeground(new java.awt.Color(255, 255, 255));
        btnDevolucion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cosmoadornos/cosmopos/icons/cancel.png"))); // NOI18N
        btnDevolucion.setBorder(null);
        btnDevolucion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDevolucionActionPerformed(evt);
            }
        });

        btnAddCliente.setBackground(new java.awt.Color(68, 138, 255));
        btnAddCliente.setFont(new java.awt.Font("DejaVu Sans", 1, 18)); // NOI18N
        btnAddCliente.setForeground(new java.awt.Color(255, 255, 255));
        btnAddCliente.setText("addC");
        btnAddCliente.setBorder(null);
        btnAddCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddClienteActionPerformed(evt);
            }
        });

        btnAsignarDescuento.setBackground(new java.awt.Color(68, 138, 255));
        btnAsignarDescuento.setFont(new java.awt.Font("DejaVu Sans", 1, 18)); // NOI18N
        btnAsignarDescuento.setForeground(new java.awt.Color(255, 255, 255));
        btnAsignarDescuento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cosmoadornos/cosmopos/icons/discount.png"))); // NOI18N
        btnAsignarDescuento.setBorder(null);
        btnAsignarDescuento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAsignarDescuentoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(btnNuevaVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filtroFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscarFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBorrarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCerrarVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnFEL, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnImprimirRecibo, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDevolucion, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAddCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAsignarDescuento, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnBorrarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCerrarVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDevolucion, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                    .addComponent(btnBuscarFactura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(filtroFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnNuevaVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnAddCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnImprimirRecibo, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                    .addComponent(btnFEL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAsignarDescuento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(null);

        jLabel13.setFont(new java.awt.Font("DejaVu Sans", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(57, 113, 177));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Factura N°:");
        jLabel13.setBorder(null);

        jLabel11.setFont(new java.awt.Font("DejaVu Sans", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(57, 113, 177));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel11.setText("Cliente:");
        jLabel11.setBorder(null);

        jLabel12.setFont(new java.awt.Font("DejaVu Sans", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(57, 113, 177));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("Documento");
        jLabel12.setBorder(null);

        txtNroFactura.setFont(new java.awt.Font("DejaVu Sans", 1, 18)); // NOI18N
        txtNroFactura.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtNroFactura.setText("0000001");

        txtNombre.setFont(new java.awt.Font("DejaVu Sans", 1, 18)); // NOI18N
        txtNombre.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtNombre.setText("Nombres Y Apellidos");

        txtDocumento.setFont(new java.awt.Font("DejaVu Sans", 1, 18)); // NOI18N
        txtDocumento.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtDocumento.setText("222222222222");

        cmbVendedores.setBackground(new java.awt.Color(68, 138, 255));
        cmbVendedores.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar Asesor" }));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(null);

        clienteFiltro.setFont(new java.awt.Font("DejaVu Sans", 1, 18)); // NOI18N
        clienteFiltro.setText("222222222222");
        clienteFiltro.setBorder(null);
        clienteFiltro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clienteFiltroMouseClicked(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("DejaVu Sans", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(57, 113, 177));
        jLabel7.setText("Buscar Cliente");
        jLabel7.setBorder(null);

        btnBuscarCliente.setBackground(new java.awt.Color(33, 33, 35));
        btnBuscarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cosmoadornos/cosmopos/icons/search.png"))); // NOI18N
        btnBuscarCliente.setBorder(null);
        btnBuscarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                    .addComponent(clienteFiltro)
                    .addComponent(jSeparator1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnBuscarCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clienteFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 706, Short.MAX_VALUE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNroFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(txtDocumento)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbVendedores, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txtNombre))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtDocumento)
                            .addComponent(cmbVendedores)))
                    .addComponent(txtNroFactura, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.setBorder(null);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane5.setBorder(null);

        tbDeptos.setAutoCreateRowSorter(true);
        tbDeptos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        tbDeptos.setFont(new java.awt.Font("DejaVu Sans", 1, 18)); // NOI18N
        tbDeptos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "DEPARTAMENTOS"
            }
        ));
        tbDeptos.setGridColor(new java.awt.Color(255, 255, 255));
        tbDeptos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDeptosMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tbDeptos);

        txtFiltroDepto.setFont(new java.awt.Font("DejaVu Sans", 0, 18)); // NOI18N
        txtFiltroDepto.setText("Brscar...");
        txtFiltroDepto.setBorder(null);
        txtFiltroDepto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFiltroDeptoFocusLost(evt);
            }
        });
        txtFiltroDepto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtFiltroDeptoMouseClicked(evt);
            }
        });
        txtFiltroDepto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFiltroDeptoKeyReleased(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("DejaVu Sans", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(57, 113, 177));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("Valor:");
        jLabel16.setBorder(null);

        txtMonto.setFont(new java.awt.Font("DejaVu Sans", 0, 18)); // NOI18N
        txtMonto.setText("Valor");
        txtMonto.setBorder(null);
        txtMonto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMontoFocusLost(evt);
            }
        });
        txtMonto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtMontoMouseClicked(evt);
            }
        });
        txtMonto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMontoKeyReleased(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("DejaVu Sans", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(57, 113, 177));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel17.setText("Departamento");
        jLabel17.setBorder(null);

        btnAddDepto.setBackground(new java.awt.Color(68, 138, 255));
        btnAddDepto.setFont(new java.awt.Font("DejaVu Sans", 1, 18)); // NOI18N
        btnAddDepto.setForeground(new java.awt.Color(255, 255, 255));
        btnAddDepto.setText("Agregar");
        btnAddDepto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddDeptoActionPerformed(evt);
            }
        });

        txtLineaProducto.setFont(new java.awt.Font("DejaVu Sans", 1, 24)); // NOI18N
        txtLineaProducto.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        txtValor.setFont(new java.awt.Font("DejaVu Sans", 1, 24)); // NOI18N
        txtValor.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jScrollPane5))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(270, 270, 270)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtMonto, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtFiltroDepto))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtLineaProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(249, 249, 249)
                        .addComponent(btnAddDepto, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnAddDepto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel17)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtFiltroDepto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel16)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(txtLineaProducto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtValor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(306, 306, 306))
        );

        jTabbedPane1.addTab("Venta Rapida", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(null);
        jPanel3.setPreferredSize(new java.awt.Dimension(766, 532));

        filtroProducto.setBackground(new java.awt.Color(197, 202, 233));
        filtroProducto.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        filtroProducto.setForeground(new java.awt.Color(33, 33, 33));
        filtroProducto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        filtroProducto.setText("NOMBRE");
        filtroProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                filtroProductoMouseClicked(evt);
            }
        });
        filtroProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                filtroProductoKeyReleased(evt);
            }
        });

        tbProductos.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        tbProductos.setFont(new java.awt.Font("DejaVu Sans", 1, 18)); // NOI18N
        tbProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "REF", "ITEM", "UNIT", "PRECIO"
            }
        ));
        tbProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbProductosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbProductos);

        txtProducto.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        txtProducto.setForeground(new java.awt.Color(57, 113, 177));
        txtProducto.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtProducto.setText("Producto");

        txtPrecio.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        txtPrecio.setForeground(new java.awt.Color(57, 113, 177));
        txtPrecio.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtPrecio.setText("Precio");

        jPanel1.setBackground(new java.awt.Color(33, 33, 35));
        jPanel1.setBorder(null);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 227, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 99, Short.MAX_VALUE)
        );

        txtMedida.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        txtMedida.setForeground(new java.awt.Color(57, 113, 177));
        txtMedida.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtMedida.setText("Medida");

        txtReferencia.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        txtReferencia.setForeground(new java.awt.Color(57, 113, 177));
        txtReferencia.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtReferencia.setText("Ref.");

        filtroProductoCodigo.setBackground(new java.awt.Color(197, 202, 233));
        filtroProductoCodigo.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        filtroProductoCodigo.setForeground(new java.awt.Color(33, 33, 33));
        filtroProductoCodigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        filtroProductoCodigo.setText("CODIGO");
        filtroProductoCodigo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                filtroProductoCodigoMouseClicked(evt);
            }
        });
        filtroProductoCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                filtroProductoCodigoKeyReleased(evt);
            }
        });

        txtCantidad.setBackground(new java.awt.Color(197, 202, 233));
        txtCantidad.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        txtCantidad.setForeground(new java.awt.Color(33, 33, 33));
        txtCantidad.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCantidad.setText("1");
        txtCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadActionPerformed(evt);
            }
        });

        btnRestar.setBackground(new java.awt.Color(33, 33, 35));
        btnRestar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cosmoadornos/cosmopos/icons/menos.png"))); // NOI18N
        btnRestar.setBorder(null);
        btnRestar.setBorderPainted(false);
        btnRestar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRestarActionPerformed(evt);
            }
        });

        btnSumar.setBackground(new java.awt.Color(33, 33, 35));
        btnSumar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cosmoadornos/cosmopos/icons/mas.png"))); // NOI18N
        btnSumar.setBorder(null);
        btnSumar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSumarActionPerformed(evt);
            }
        });

        btnFactura.setBackground(new java.awt.Color(68, 138, 255));
        btnFactura.setFont(new java.awt.Font("DejaVu Sans", 1, 18)); // NOI18N
        btnFactura.setForeground(new java.awt.Color(255, 255, 255));
        btnFactura.setText("Agregar");
        btnFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFacturaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(filtroProductoCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(filtroProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCantidad, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnRestar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSumar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 478, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(44, 44, 44))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(481, 481, 481)
                                .addComponent(txtReferencia, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtMedida, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(btnFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btnSumar, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCantidad)
                            .addComponent(filtroProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(filtroProductoCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(btnRestar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(btnFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtProducto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtReferencia)
                            .addComponent(txtMedida)
                            .addComponent(txtPrecio))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(195, 195, 195)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Venta Detallada", jPanel3);

        tbFactura.setBorder(null);
        tbFactura.setFont(new java.awt.Font("Ubuntu", 0, 15)); // NOI18N
        tbFactura.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Producto", "Sin IVA", "IVA unitario", "Precio", "Cantidad", "Descuento", "SubTotal", "IVA", "Total"
            }
        ));
        tbFactura.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbFacturaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbFactura);

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(pnlMainLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jScrollPane2))
                            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(pnlMain, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private static class RoundedBorder implements Border {

        private final int radius;

        RoundedBorder(int radius) {
            this.radius = radius;
        }


        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
        }

        @Override
        public boolean isBorderOpaque() {
            return true;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.drawRoundRect(x, y, width-1, height-1, radius, radius);
        }
    }
    
    
    public void addProductoFactura(String precio, String depto){
        
    }
    
    
    private void agregarAFactura(byte tipoVenta){
        producto = null;
        linea = null;
        
        modelo = (DefaultTableModel) tbFactura.getModel();
        
        
        precioM = txtPrecio.getText();
        
        switch (tipoVenta) {
            case 0:// VENTA DETALLADA
                productoNombre = txtProducto.getText();
                //subTipo = txtMedida.getText();
                productoId = lista.get(rowProducto).getId();
                for(int i = 0 ; i<=lista.size()-1; i++){
                    if(lista.get(i).getNombre().contains(productoNombre) && lista.get(i).getSubTipo().contains(subTipo)){
                        productoId = lista.get(i).getId();
                        precio = lista.get(i).getPrecio();
                        referencia = lista.get(i).getReferencia();
                        subTipoId = lista.get(i).getSubTipoId();
                        subTipo = lista.get(i).getSubTipo();
                        //JOptionPane.showMessageDialog(null, "ID: "+ productoId);
                        break;
                    }
                }
                
                
                break;
                
            case 1://Venta por Lineas.
                
                for(int i = 0 ; i <=listaLineas.size()-1; i++){
                    if(listaLineas.get(i)
                            .getLineaNombre()
                            .contains(txtLineaProducto.getText())){
                            //departamentoId= listaLineas.get(i).getId();
                            productoId = listaLineas.get(i).getId();
                            break;
                    }
                }
                //departamentoId= listaDepartamentos.get(rowSelected).getId();
                subTipo = "VARIOS";
                subTipoId = 56;
                productoNombre = txtLineaProducto.getText();
                referencia = ""+productoId;
                
                precioM = txtValor.getText();
                precioM= precioM.replace(".", "");
                precioM= precioM.replace("$", "");
                //JOptionPane.showMessageDialog(null,"precioM: "+precioM);
                precio = Integer.parseInt(precioM);
                //JOptionPane.showMessageDialog(null,"precio: "+precio);
                
                cantidad =1;
                linea = new Linea(departamentoId, productoNombre);
                //JOptionPane.showMessageDialog(this, departamentoId);
                
                break;
            default:
                break;
        }
        
        //productoIVA = 0.00f;
        //producto = new Producto(productoId, subTipoId, precioSinIVA, productoIVA, precio);
        productoIVA = precio-(precio/1.19f);
        //precioM= precioM.replaceAll("\\.", "");
        //precioM= precioM.replaceAll("\\$", "");
        //precio = Float.parseFloat(precioM);
        cantidad = Float.parseFloat(txtCantidad.getText());
        totalProducto = precio*cantidad;
        precioSinIVA = precio-productoIVA;
        JOptionPane.showMessageDialog(null, "ID: "+ subTipoId);
        ///producto = new Producto(productoId, productoNombre, referencia, subTipo, precio, productoIVA, total);
        
        
        producto = new Producto(productoId, productoNombre, referencia, subTipo, subTipoId, precioSinIVA, productoIVA, precio);
        
        //cantidad = Integer.parseInt(txtCantidad.getText());
        //productos.add(new Producto(productoNombre, referencia,precio,cantidad));
        
        float tasa =19.00f;  // BASE DE DATOS
        
        IVA = (tasa/100)+1;
        
        totalVenta = precio*cantidad;
        
        subTotalVenta = totalVenta/IVA;
        
        totalIVA = totalVenta-subTotalVenta;
        
        
        
        /*
        // VALIDACIÓN PARA REDONDEO.
        // SE ELIMINA PUES SE CAMBIA DEL TIPO DE DATO int POR float
        // PARA ELIMINAR EL REDONDEO Y USAR LOS DECIMALES TRUNCADOS
        // A DOS DIGITOS COMO LO INDICA LA DIAN
        int subI = (int) subTotalVenta;
        float val = subTotalVenta-subI;
        
        if (val >.50){
            // suma decimal.
            subTotalVenta+=1;
        }else{
            totalIVA+=1;
        }
        */
        
        //JOptionPane.showMessageDialog(null, "Producto: "+rowProducto);
        
        // Usado cuando se trata de una venta por departamentos
        
        int descuento = 0;
        // Usado cuando se trata de una venta detallada
        
        TipoProducto tipo = new TipoProducto(1,"EA","cada");
        
        int nroFactura = Integer.parseInt(txtNroFactura.getText());
        ventas.add(new Venta(
                producto,
                cantidad,
                descuento,
                subTotalVenta,
                totalIVA,
                totalVenta,
                nroFactura,
                nroZ
        ));
        

        //addProductoFactura(precio, departamento);
        
        //String[] venta ={producto, valor, cantidad, IVA, subTotal, total};
        //model.addRow(new Object[]{producto, valor, cantidad, IVA, subTotal, total});
        
        
        //modelo.addRow(new Object[]{producto, precio, cantidad, IVA, subTotal, total});
        
        precioM = formatea.format(precio);
        
        //subTipo = txtMedida.getText();
        
        
        productos.add(producto);
        
        modelo.addRow(
                new Object[]{
                    productoNombre,
                    "$"+formatea.format(precioSinIVA),
                    "$"+formatea.format(productoIVA),
                    "$"+formatea.format(precio),
                    cantidad,
                    descuento,
                    "$"+formatea.format(subTotalVenta),
                    "$"+formatea.format(totalIVA),
                    "$"+formatea.format(totalVenta)
                }
        );
        for(int i = 0 ; i <= tbFactura.getColumnCount() ; i++){
            tbFactura.setRowHeight(i, 30);
        }
        
        
        //llenarTablaLineas();
        activarFactura();
        sumarFactura();
        //tbFactura.setModel(model);
    }
    public void sumarFactura(){
        facturaSubTotal =0.00f;
        facturaIVA =0.00f;
        facturaTotal =0.00f;
        for(int i = 0 ; i <= ventas.size()-1 ; i++){
            facturaSubTotal+=ventas.get(i).getSubtotal();
            facturaIVA+=ventas.get(i).getVrTotalIVA();
            facturaTotal+=ventas.get(i).getTotalVenta();
        }
        /*
        
        JOptionPane.showMessageDialog(this, "Sub-Total: "+facturaSubTotal
                +"\nIVA: " +facturaIVA
                +"\nTotal: "+facturaTotal);
        */
    }
    
    
    private void tbProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbProductosMouseClicked
        // TODO add your handling code here:
        JTable source = (JTable)evt.getSource();
        int row = source.rowAtPoint( evt.getPoint() );
        int column = source.columnAtPoint( evt.getPoint() );
        rowProducto = row;
        //JOptionPane.showMessageDialog(null,"Fila:" +lista.get(rowProducto).getNombre());
        //precio = Integer.parseInt(source.getModel().getValueAt(row,column+1)+"");
        //System.out.println("precio: "+precio);
        cantidad = Float.parseFloat(txtCantidad.getText());
        referencia = source.getModel().getValueAt(row, 0)+"";
        productoNombre = source.getModel().getValueAt(row, 1)+"";
        medida = source.getModel().getValueAt(row, 2)+"";
        if(productoNombre==null){
            precioM = source.getModel().getValueAt(row,column)+"";
            productoNombre="";
        }else{
            precioM = source.getModel().getValueAt(row,3)+"";
        }
        
        //precioM= precioM.replaceAll("\\.", "");
        //precioM= precioM.replaceAll("\\$", "");
        txtProducto.setText(productoNombre);
        txtMedida.setText(medida);
        txtReferencia.setText(referencia);
        txtPrecio.setText(""+precioM);
        
    }//GEN-LAST:event_tbProductosMouseClicked

    
    private void buscarCliente(){
        // Búsqueda de clientes
        cliente =clicon.buscarCliente(clienteFiltro.getText());
        if(cliente== null){
            int g = JOptionPane.showConfirmDialog(this, "Cliente no encontrado\n"+"Desea agregar un nuevo cliente?");
            
            if(g==1){
                //JOptionPane.showMessageDialog(null, "No agregar cliente.");
            }
            if(g==JOptionPane.OK_OPTION){
                //JOptionPane.showMessageDialog(null,"Agregar un nuevo cliente");
                guardarCliente();
            }
            
            
        }else{
            //cliente = new Cliente();
            cliente.setDocumento(clienteFiltro.getText());
            txtDocumento.setText(clienteFiltro.getText());
            txtNombre.setText(cliente.getNombres());
            JOptionPane.showMessageDialog(null,"Cliente encontrado.");
        }
    }
    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        // TODO add your handling code here:
        this.dispose();
        /*
        Main main = new Main();
        main.setVisible(true);
        */
    }//GEN-LAST:event_jLabel3MouseClicked

    private void txtCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadActionPerformed
        // TODO add your handling code here:
        txtCantidad.setText("");
    }//GEN-LAST:event_txtCantidadActionPerformed

    private void filtroFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filtroFacturaActionPerformed
        // TODO add your handling code here:
        filtroFactura.setText("");
    }//GEN-LAST:event_filtroFacturaActionPerformed

    public void mostrarRecibo() throws IOException, DocumentException{
        //ArrayList<Producto> productos = new ArrayList();
        //doc_equivalente_nro = facon.buscarUltimaFactura();
        /*
        cliente = new Cliente();
        cliente.setDocumento(Integer.parseInt(txtDocumento.getText()));
        cliente.setNombres(txtNombre.getText());
        //factura = new Factura();
        
        */
        //FacturaVenta facturar = new FacturaVenta();
        
        float totalSinImp = 168067.23f;
        float totalImp = 31932.77f;
        float totalMasImp = 200000.00f;
        
        //hora ="16:46:55";
        
        factura = new Factura(factNum, 
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
        
        //facturar.setVisible(true);
        //facturar.crearTicket(factura, fecha);
        
        String texto;
        //JOptionPane.showMessageDialog(null, "Resolucion:"+factura.getEmpresa().getDian().getTextResol());
        //texto = facon.crearTicket(factura);
        
        
        // Mejorar la presentación de este archivo PDF
        
        File archivoFactura = facon.crearTicketPDF(facturaPOS);
        
        mostrarArchivo(archivoFactura);
        //facon.crearTicket(factura);
        //area.setText(facon.imprimirFactura(factura));
    }
    
    public void mostrarArchivo(File file){
        try{
            Desktop.getDesktop().open(file);
            //Desktop.getDesktop().print(file);
            
        }catch(IOException e){
            JOptionPane.showMessageDialog(null,"ERROR: "+e);
        }
    }
    
    
    private void filtroProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_filtroProductoMouseClicked
        // TODO add your handling code here:
        filtroProducto.setText("");
    }//GEN-LAST:event_filtroProductoMouseClicked

    private void btnFELActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFELActionPerformed
        // TODO add your handling code here:
        FacturaElectronica ventaE = new FacturaElectronica();
        ventaE.setFactura(factura);
        ventaE.setVisible(true);
        
        /*
        FacturaElectronica fel;
        
        //FacturaElectronica fel2 = (FacturaElectronica) new Factura();
        
        ArchivoXMLController archcon = new ArchivoXMLController(new ArchivoXML(fel));
        JOptionPane.showMessageDialog(null, ""
                //+ archcon.invoiceLine(productos));
                //+ archcon.invoiceLine(ventas));
                + archcon);
        */
    }//GEN-LAST:event_btnFELActionPerformed

    private void btnImprimirReciboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirReciboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnImprimirReciboActionPerformed

    private void btnNuevaVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaVentaActionPerformed
        // TODO add your handling code here:
        nuevaVenta();
    }//GEN-LAST:event_btnNuevaVentaActionPerformed

    public void nuevaVenta(){
        //model = (DefaultTableModel) tbFactura.getModel();
        productos.removeAll(productos);
        ventas.removeAll(ventas);
        //area.setText("");
        
        //cliente = null;
        
        //productos = null;
        if(modelo!=null){
            modelo.setRowCount(0);
        }
        resetFactura();
        btnNuevaVenta.setEnabled(true);
        //tbFactura.remove(1);
        
    }
    
    private void btnBorrarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarProductoActionPerformed
        // TODO add your handling code here:
        modelo.removeRow(rowSelected);
        productos.remove(rowSelected);
        ventas.remove(rowSelected);
        btnBorrarProducto.setEnabled(false);
    }//GEN-LAST:event_btnBorrarProductoActionPerformed

    private void btnCerrarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarVentaActionPerformed
        // TODO add your handling code here:
        verificarCliente();
        
    }//GEN-LAST:event_btnCerrarVentaActionPerformed
    
    private boolean verificarCliente(){
        if(!txtDocumento.getText().equals("222222222222")){
            cliente=clicon.buscarCliente(txtDocumento.getText());
            crearFacturaPOS();
            //nuevaVenta();
            
        }else if(txtDocumento.getText().equals("222222222222")){
            cliente = new Cliente();
            cliente.setClienteId(1);
            cliente.setDocumento("222222222222");
            cliente.setNombres("CONSUMIDOR");
            cliente.setApellidos("FINAL");
            cliente.setTelefono(null);
            cliente.setDireccion(null);
            cliente.setEmail(null);
            cliente.setTipoDocumento("NA"); // PREGUNTAR AL CONTADOR
            crearFacturaPOS();
            //registrarVenta();
            //JOptionPane.showMessageDialog(null, "Debe asignar un cliente.");
        }else{
            JOptionPane.showMessageDialog(null, "Error inesperado asignando el cliente");
        }
        return false;
    }
    
    Factura facturaPOS;
    public boolean crearFacturaPOS(){
        facturaPOS = new Factura(
                factNum,
                empresa,
                fecha,
                hora,
                cliente,
                ventas,
                facturaSubTotal,
                facturaIVA,
                facturaTotal
        );
        if(facon.crearFacturaPOS(facturaPOS)){
            try {
                return registrarVenta();
                
                //JOptionPane.showMessageDialog(null, "Venta registrada en la base de datos");
            } catch (DocumentException | IOException ex) {
                Logger.getLogger(VentasWhite.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }else{
            JOptionPane.showMessageDialog(null, "Error registrando la factura.");
            return false;
        }
    }
    
    
    public boolean registrarVenta() throws IOException, DocumentException{
        if(ventaCon.registrarVentas(Integer.parseInt(txtNroFactura.getText()), ventas)){
                mostrarRecibo();
                //area.setAlignmentX(CENTER_ALIGNMENT);
                btnImprimirRecibo.setEnabled(true);
                btnFEL.setEnabled(true);
                btnCerrarVenta.setEnabled(false);
                btnDevolucion.setEnabled(false);
                btnAddDepto.setEnabled(false);
                btnFactura.setEnabled(false);
                cliente = null;    
                return true;
            }else{
                JOptionPane.showMessageDialog(null, "Error registrando las ventas.");
                return false;
            }
    }
    
    private void btnDevolucionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDevolucionActionPerformed
        // TODO add your handling code here:
        nuevaVenta();
    }//GEN-LAST:event_btnDevolucionActionPerformed

    private void btnBuscarFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarFacturaActionPerformed
        // TODO add your handling code here:
        nuevaVenta();
        ventas = new ArrayList();
        ventas = ventaCon.buscarVentasPOS(Integer.parseInt(filtroFactura.getText()));
        if(ventas.size()>0){
            modelo = (DefaultTableModel) tbFactura.getModel();
            
            for(int i = 0 ; i<= ventas.size()-1;i++){
                modelo.addRow(new Object[]{
                    ventas.get(i).getProducto().getId(),
                    "$"+formatea.format(1000),
                    ventas.get(i).getCantidad(),
                    "$"+formatea.format(ventas.get(i).getProducto().getProductoIVA()),
                    "$"+formatea.format(ventas.get(i).getSubtotal()),
                    "$"+formatea.format(ventas.get(i).getTotalVenta())
                });
            }
            //System.out.println("Ventas: "+ventas.size());
            btnDevolucion.setEnabled(true);
        }else{
            modelo.setRowCount(0);
            ventas.removeAll(ventas);
        }
    }//GEN-LAST:event_btnBuscarFacturaActionPerformed

    private void tbFacturaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbFacturaMouseClicked
        // TODO add your handling code here:
        btnBorrarProducto.setEnabled(true);
        JTable source = (JTable)evt.getSource();
        rowSelected = source.rowAtPoint( evt.getPoint() );
        
    }//GEN-LAST:event_tbFacturaMouseClicked

    private void btnReporteZActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReporteZActionPerformed
        // TODO add your handling code here:
        
        try {
            generarReporte(/*lista*/);
        } catch (DocumentException | FileNotFoundException ex) {
            Logger.getLogger(InformeVentasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnReporteZActionPerformed

    private void btnAddClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddClienteActionPerformed
        // TODO add your handling code here:
        guardarCliente();
    }//GEN-LAST:event_btnAddClienteActionPerformed

    private void btnConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfigActionPerformed
        // TODO add your handling code here:
        Config config = new Config();
        config.setVisible(true);
    }//GEN-LAST:event_btnConfigActionPerformed

    private void filtroProductoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_filtroProductoKeyReleased
        // TODO add your handling code here:
        tipoFiltro = "NOMBRE";
        filtrarProducto(tipoFiltro, filtroProducto.getText());

    }//GEN-LAST:event_filtroProductoKeyReleased

    private void btnAddDeptoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddDeptoActionPerformed
        // TODO add your handling code here:
        if(txtLineaProducto.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Debe seleccionar una línea");
        }else if(txtValor.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Debe asignar un precio");
        }else{
            tipoVenta=1;
            agregarAFactura(tipoVenta);
        }
    }//GEN-LAST:event_btnAddDeptoActionPerformed

    private void txtFiltroDeptoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtFiltroDeptoMouseClicked
        // TODO add your handling code here:
        txtFiltroDepto.setText("");
    }//GEN-LAST:event_txtFiltroDeptoMouseClicked

    private void txtFiltroDeptoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFiltroDeptoKeyReleased
        // TODO add your handling code here:
        filtrarLinea(txtFiltroDepto.getText());
    }//GEN-LAST:event_txtFiltroDeptoKeyReleased

    private void txtMontoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMontoMouseClicked
        // TODO add your handling code here:
        txtMonto.setText("");
    }//GEN-LAST:event_txtMontoMouseClicked

    private void tbDeptosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDeptosMouseClicked
        // TODO add your handling code here:
        JTable source = (JTable)evt.getSource();
        rowSelected = source.rowAtPoint( evt.getPoint() );
        agregarVentaDepartamento();
        
    }//GEN-LAST:event_tbDeptosMouseClicked

    private void txtMontoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMontoKeyReleased
        // TODO add your handling code here:
        //txtValor.setText("$"+txtMonto.getText());
        String monto;
        monto = txtMonto.getText();
        validarMonto(monto);
    }//GEN-LAST:event_txtMontoKeyReleased

    private boolean validarMonto(String monto){
        try{
            String montoM = monto;
            montoM = montoM.replaceAll("\\.", "");
            montoM = montoM.replaceAll("\\$", "");
            
            int valor = Integer.parseInt(montoM);
            //int valor = Integer.parseInt(txtMonto.getText());
            if(!montoM.equals("") && valor >0){
                /*
                // OPTIMIZACIÓN DE DIGITACIÓN
                if(valor>=10 && valor <=100){
                    
                    if(valor%2==0){
                        valor = valor*100;
                    }else if(valor%5==0){
                        valor = valor*10;
                    }else if(valor%2==1){
                        valor = valor*100;
                    }
                }
                */
                txtValor.setText("$"+formatea.format(valor));
                txtMonto.setText("$"+formatea.format(valor));
                
                //txtMonto.setText(""+valor);
            }else{
                txtMonto.setText("");
                txtValor.setText("");
                return false;
            }
            return true;
        }catch(NumberFormatException e){
            txtValor.setText("");
            txtMonto.setText("");
        }
        return false;
    }
    
    private void btnCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarSesionActionPerformed
        // TODO add your handling code here:
        cerrarVentaDiaria(nroZ);
    }//GEN-LAST:event_btnCerrarSesionActionPerformed

    private void filtroProductoCodigoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_filtroProductoCodigoMouseClicked
        // TODO add your handling code here:
        filtroProductoCodigo.setText("");
    }//GEN-LAST:event_filtroProductoCodigoMouseClicked

    private void filtroProductoCodigoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_filtroProductoCodigoKeyReleased
        // TODO add your handling code here:
        filtrarCodigo(filtroProductoCodigo.getText());
    }//GEN-LAST:event_filtroProductoCodigoKeyReleased

    private void filtrarCodigo(String texto){
        int cod = 0 ; 
        int codL = 0 ;
        String[] campos = {"REF", "ITEM", "UNIT", "PRECIO"};
        modelP = new DefaultTableModel(null, campos);
        if(!texto.equals("")){
            for(int i=0 ; i <=lista.size()-1; i++){
                if(lista.get(i).getReferencia().equals(texto)){
                    System.out.println("Codigo Encontrado: "+texto);
                    
                    String[] registro = {
                        lista.get(i).getReferencia(),
                        lista.get(i).getNombre(),
                        lista.get(i).getSubTipo(),
                        "$"+formatea.format(lista.get(i).getPrecio())
                    };
                    modelP.addRow(registro);
                }
            }
            tbProductos.setModel(modelP);
            for(int i = 0 ; i <= tbProductos.getRowCount()-1 ; i++){
                tbProductos.setRowHeight(i, 30);
            }
            formatoTablaProductos();
        }else{
            llenarTablaProductos();
        }
    }
    
    private void btnFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFacturaActionPerformed
        // TODO add your handling code here:
        if(txtProducto.getText().equals("Producto")){
            JOptionPane.showMessageDialog(this, "Debe seleccionar un producto de la lista.");
        }else{
            tipoVenta=0;
            agregarAFactura(tipoVenta);
        }
    }//GEN-LAST:event_btnFacturaActionPerformed

    private void btnSumarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSumarActionPerformed
        // TODO add your handling code here:
        cantidad= Float.parseFloat(txtCantidad.getText());
        cantidad = cantidad+1;
        txtCantidad.setText(""+cantidad);
    }//GEN-LAST:event_btnSumarActionPerformed

    private void btnRestarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRestarActionPerformed
        // TODO add your handling code here:
        cantidad= Float.parseFloat(txtCantidad.getText());
        if(cantidad>1){
            cantidad = cantidad-1;
            txtCantidad.setText(""+cantidad);
        }
    }//GEN-LAST:event_btnRestarActionPerformed

    private void clienteFiltroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clienteFiltroMouseClicked
        // TODO add your handling code here:
        if(!clienteFiltro.getText().equals("")){
            clienteFiltro.setText("");
        }
    }//GEN-LAST:event_clienteFiltroMouseClicked

    private void btnBuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarClienteActionPerformed
        // TODO add your handling code here:
        buscarCliente();
    }//GEN-LAST:event_btnBuscarClienteActionPerformed

    private void txtFiltroDeptoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFiltroDeptoFocusLost
        // TODO add your handling code here:
        txtFiltroDepto.setText("Buscar...");
    }//GEN-LAST:event_txtFiltroDeptoFocusLost

    private void txtMontoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMontoFocusLost
        // TODO add your handling code here:
        txtMonto.setText("Valor");
    }//GEN-LAST:event_txtMontoFocusLost

    private void btnAsignarDescuentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAsignarDescuentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAsignarDescuentoActionPerformed

    private void cerrarVentaDiaria(int nroZ){
        ZDiario zDiario = new ZDiario();
        
        nroZ +=1;
        String fecham =setFecha(1);
        
        
        zDiario.setzDiario(nroZ);
        zDiario.setzDiarioFecha(fecham);
        
        //JOptionPane.showMessageDialog(null, "Registro Siguiente: "+zDiario);
        //*
        if(zdao.guardarZDiario(zDiario)){
            JOptionPane.showMessageDialog(null, "Sesión terminada.");
            bloquearTodo();
            btnReporteZ.setEnabled(false);
            btnCerrarSesion.setEnabled(false);
        }else{
            JOptionPane.showMessageDialog(null, "ERROR cerrando Sesión.");
        }
        //*/
    }
    
    private void agregarVentaDepartamento(){
        txtLineaProducto.setText(tbDeptos.getValueAt(rowSelected,0).toString());
        /*
        for(int i =0 ; i <= listaDepartamentos.size()-1 ; i++){
        }
        */
    }
    public void filtrarLinea(String texto){
        int dept;
        modelD = (DefaultTableModel)tbDeptos.getModel();
        modelD.setRowCount(0);
        //System.out.println("productos: "+lista.size());
        try{
            if(!txtFiltroDepto.getText().equals("")){
                dept = Integer.parseInt(txtFiltroDepto.getText());
                for(int i=0 ; i <=listaLineas.size()-1; i++){
                    if(listaLineas
                            .get(i)
                            .getId()==dept
                            ){
                        String[] registro = {
                            listaLineas.get(i).getLineaNombre()
                        };
                        modelD.addRow(registro);
                        txtLineaProducto.setText(
                                listaLineas
                                        .get(i)
                                        .getLineaNombre());
                    }else {
                        //System.out.println("No contiene:"+lista.get(i).getNombre());
                    }
                }
                for(int i = 0 ; i <= tbDeptos.getColumnCount()-1 ; i++){
                    tbDeptos.setRowHeight(i, 30);
                }
                tbDeptos.setModel(modelD);
            }else {
                llenarTablaLineas();
                txtLineaProducto.setText("");
            }
        }catch(NumberFormatException e){
            System.out.println("Error: "+e);
            //modelD = new DefaultTableModel(null, campos);
            //modelD = (DefaultTableModel)tbDeptos.getModel();
            for(int i=0 ; i <=listaLineas.size()-1; i++){
                    if(listaLineas
                            .get(i)
                            .getLineaNombre()
                            .contains(txtFiltroDepto.getText())
                            ){
                        String[] registro = {
                            listaLineas.get(i).getLineaNombre()
                        };
                        modelD.addRow(registro);
                    }else {
                        //modelD.setRowCount(0);
                    }
                }
                for(int i = 0 ; i <=tbDeptos.getRowCount()-1; i++){
                    tbDeptos.setRowHeight(i, 30);
                }
            tbDeptos.setModel(modelD);
        }
    }
    
    
    public void filtrarProducto(String filtro, String texto){
        /*
        String[] campos = {"Producto", "Referencia", "Medida", "Precio"};
        modelP = new DefaultTableModel(null, campos);
        */
        modelP = (DefaultTableModel)tbProductos.getModel();
        modelP.setRowCount(0);
        try{
            
        
            for(int i=0 ; i <=lista.size()-1; i++){
                if(lista.get(i).getNombre().contains(texto)){
                    String[] registro = {
                        lista.get(i).getReferencia(),
                        lista.get(i).getNombre(),
                        lista.get(i).getSubTipo(),
                        "$"+formatea.format(lista.get(i).getPrecio())
                    };
                    modelP.addRow(registro);
                }else{
                    //System.out.println("No contiene:"+texto);
                }
            }
            tbProductos.setModel(modelP);
            //formatoTablaProductos();
            for(int i = 0 ; i <= tbProductos.getRowCount()-1 ; i++){
                tbProductos.setRowHeight(i,30);
            }
        
        }catch(Exception e){
            
        }
    }
    
    public void guardarCliente(){
        ClienteAgregar nuevoCliente = new ClienteAgregar();
        nuevoCliente.setVisible(true);
        ClienteAgregar.txtDocumento.setText(clienteFiltro.getText());
        
    }
    public void generarReporte(/*ArrayList<Producto> productos*/) throws DocumentException, FileNotFoundException{
        
        //InformeVentasController infocon = new InformeVentasController();
        String ruta;
        JFileChooser jfc = new JFileChooser();
        jfc.setCurrentDirectory(new File("/home/b41n/Escritorio"));
        //System.out.println("Nombre: "+lista.get(1).getNombre());
        //System.out.println("Nombre: "+lista.size());
        
        InformeVentas infoventas = new InformeVentas();
        
        infoventas.nroZ = nroZ;
        infoventas.setVisible(true);
        
        JOptionPane.showMessageDialog(null,"Z: "+ nroZ);
        /*
        int seleccion = jfc.showSaveDialog(null);
        if(seleccion == JFileChooser.APPROVE_OPTION){
            ruta = jfc.getSelectedFile().getAbsolutePath();
            infocon.generarReporte(/*lista,/ruta, nroZ/*, fecha/);
        }else{
            JOptionPane.showMessageDialog(null,"Ningún archivo seleccionado.");
        }
        */
        
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }else if("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentasWhite.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new VentasWhite().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddCliente;
    private javax.swing.JButton btnAddDepto;
    private javax.swing.JButton btnAdmin;
    private javax.swing.JButton btnAsignarDescuento;
    private javax.swing.JButton btnBorrarProducto;
    private javax.swing.JButton btnBuscarCliente;
    private javax.swing.JButton btnBuscarFactura;
    private javax.swing.JButton btnCerrarSesion;
    private javax.swing.JButton btnCerrarVenta;
    private javax.swing.JButton btnConfig;
    private javax.swing.JButton btnDevolucion;
    private javax.swing.JButton btnFEL;
    private javax.swing.JButton btnFactura;
    private javax.swing.JButton btnImprimirRecibo;
    private javax.swing.JButton btnNuevaVenta;
    private javax.swing.JButton btnReporteZ;
    private javax.swing.JButton btnRestar;
    private javax.swing.JButton btnSumar;
    private javax.swing.JTextField clienteFiltro;
    private javax.swing.JComboBox<String> cmbVendedores;
    private javax.swing.JTextField filtroFactura;
    private javax.swing.JTextField filtroProducto;
    private javax.swing.JTextField filtroProductoCodigo;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlTop;
    private javax.swing.JTable tbDeptos;
    private javax.swing.JTable tbFactura;
    private javax.swing.JTable tbProductos;
    private javax.swing.JTextField txtCantidad;
    public static javax.swing.JLabel txtDocumento;
    private static javax.swing.JLabel txtFecha;
    private javax.swing.JTextField txtFiltroDepto;
    private javax.swing.JLabel txtLineaProducto;
    private javax.swing.JLabel txtMedida;
    private javax.swing.JTextField txtMonto;
    public static javax.swing.JLabel txtNombre;
    private javax.swing.JLabel txtNroFactura;
    private javax.swing.JLabel txtPrecio;
    private javax.swing.JLabel txtProducto;
    private javax.swing.JLabel txtReferencia;
    private javax.swing.JLabel txtValor;
    // End of variables declaration//GEN-END:variables
}
