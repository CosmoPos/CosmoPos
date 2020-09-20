/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmoadornos.cosmopos.view;

import com.cosmoadornos.cosmopos.model.Permiso;
import com.cosmoadornos.cosmopos.model.Usuario;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author b41n
 */
public final class Main extends javax.swing.JFrame {
    
    
    ArrayList<Permiso> permisos = new ArrayList();
    public Usuario usuario = null;

    /**
     * Creates new form Main
     */
    public Main() {
        initComponents();
        verificarPermisos(permisos);
        setLocationRelativeTo(null);
        btnConfig.setEnabled(false);
        btnConfig.setVisible(false);
        btnProveedores.setEnabled(false);
        btnProveedores.setVisible(false);
        btnCompras.setEnabled(false);
        btnCompras.setVisible(false);
        btnProductos.setEnabled(false);
        btnProductos.setVisible(false);
        btnNomina.setEnabled(false);
        btnNomina.setVisible(false);
        btnClientes.setEnabled(false);
        btnClientes.setVisible(false);
        btnVentas.setEnabled(false);
        btnVentas.setVisible(false);
        btnInventario.setEnabled(false);
        btnInventario.setVisible(false);
        btnInformes.setEnabled(false);
        btnInformes.setVisible(false);
        btnUsuarios.setEnabled(false);
        btnUsuarios.setVisible(false);
    }
    
    
    public void verificarPermisos(ArrayList<Permiso> permisos){
        
        if(!permisos.isEmpty()){
            txtUsuario.setText(usuario.getUsuario());
            System.out.println("Usuario: " +usuario.getUsuario());
        }else{
            txtUsuario.setText("INICIE SESIÓN");
        }
        
        for(int i = 0 ; i <= permisos.size()-1; i++){
            System.out.println("Permiso:" +permisos.get(i).getPermiso());
            switch (permisos.get(i).getPermiso()) {
                case "CONFIGURACION":
                    btnConfig.setEnabled(true);
                    btnConfig.setVisible(true);
                    break;
                case "PROVEEDORES":
                    btnProveedores.setEnabled(true);
                    btnProveedores.setVisible(true);
                    break;
                case "COMPRAS":
                    btnCompras.setEnabled(true);
                    btnCompras.setVisible(true);
                    break;
                case "PRODUCTOS":
                    btnProductos.setEnabled(true);
                    btnProductos.setVisible(true);
                    break;
                case "NOMINA":
                    btnNomina.setEnabled(true);
                    btnNomina.setVisible(true);
                    break;
                case "CLIENTES":
                    btnClientes.setEnabled(true);
                    btnClientes.setVisible(true);
                    break;
                case "VENTAS":
                    btnVentas.setEnabled(true);
                    btnVentas.setVisible(true);
                    break;
                case "INVENTARIOS":
                    btnInventario.setEnabled(true);
                    btnInventario.setVisible(true);
                    break;
                case "INFORMES":
                    btnInformes.setEnabled(true);
                    btnInformes.setVisible(true);
                break;
                case "USUARIOS":
                    btnUsuarios.setEnabled(true);
                    btnUsuarios.setVisible(true);
                break;
                default:
                    JOptionPane.showMessageDialog(null, "No tiene los permisos suficientes,");
                    break;
            }
        }
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        btnConfig = new javax.swing.JButton();
        btnProveedores = new javax.swing.JButton();
        btnCompras = new javax.swing.JButton();
        btnNomina = new javax.swing.JButton();
        btnClientes = new javax.swing.JButton();
        btnVentas = new javax.swing.JButton();
        btnProductos = new javax.swing.JButton();
        btnInventario = new javax.swing.JButton();
        btnInformes = new javax.swing.JButton();
        btnUsuarios = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        txt_usuario = new javax.swing.JLabel();
        txtUsuario1 = new javax.swing.JTextField();
        txtUsuario = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(33, 33, 35));
        jPanel3.setBorder(null);
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnConfig.setBackground(new java.awt.Color(33, 33, 35));
        btnConfig.setFont(new java.awt.Font("DejaVu Sans Condensed", 1, 18)); // NOI18N
        btnConfig.setForeground(new java.awt.Color(255, 255, 255));
        btnConfig.setText("Configuración");
        btnConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfigActionPerformed(evt);
            }
        });
        jPanel3.add(btnConfig, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 210, 170, -1));

        btnProveedores.setBackground(new java.awt.Color(33, 33, 35));
        btnProveedores.setFont(new java.awt.Font("DejaVu Sans Condensed", 1, 18)); // NOI18N
        btnProveedores.setForeground(new java.awt.Color(255, 255, 255));
        btnProveedores.setText("Proveedores");
        btnProveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProveedoresActionPerformed(evt);
            }
        });
        jPanel3.add(btnProveedores, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 110, 170, -1));

        btnCompras.setBackground(new java.awt.Color(33, 33, 35));
        btnCompras.setFont(new java.awt.Font("DejaVu Sans Condensed", 1, 18)); // NOI18N
        btnCompras.setForeground(new java.awt.Color(255, 255, 255));
        btnCompras.setText("Compras");
        btnCompras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComprasActionPerformed(evt);
            }
        });
        jPanel3.add(btnCompras, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 110, 160, -1));

        btnNomina.setBackground(new java.awt.Color(33, 33, 35));
        btnNomina.setFont(new java.awt.Font("DejaVu Sans Condensed", 1, 18)); // NOI18N
        btnNomina.setForeground(new java.awt.Color(255, 255, 255));
        btnNomina.setText("Nómina");
        btnNomina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNominaActionPerformed(evt);
            }
        });
        jPanel3.add(btnNomina, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 210, 160, -1));

        btnClientes.setBackground(new java.awt.Color(33, 33, 35));
        btnClientes.setFont(new java.awt.Font("DejaVu Sans Condensed", 1, 18)); // NOI18N
        btnClientes.setForeground(new java.awt.Color(255, 255, 255));
        btnClientes.setText("Clientes");
        btnClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientesActionPerformed(evt);
            }
        });
        jPanel3.add(btnClientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 60, 170, -1));

        btnVentas.setBackground(new java.awt.Color(33, 33, 35));
        btnVentas.setFont(new java.awt.Font("DejaVu Sans Condensed", 1, 18)); // NOI18N
        btnVentas.setForeground(new java.awt.Color(255, 255, 255));
        btnVentas.setText("Ventas");
        btnVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVentasActionPerformed(evt);
            }
        });
        jPanel3.add(btnVentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, 160, -1));

        btnProductos.setBackground(new java.awt.Color(33, 33, 35));
        btnProductos.setFont(new java.awt.Font("DejaVu Sans Condensed", 1, 18)); // NOI18N
        btnProductos.setForeground(new java.awt.Color(255, 255, 255));
        btnProductos.setText("Productos");
        btnProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductosActionPerformed(evt);
            }
        });
        jPanel3.add(btnProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 160, 160, -1));

        btnInventario.setBackground(new java.awt.Color(33, 33, 35));
        btnInventario.setFont(new java.awt.Font("DejaVu Sans Condensed", 1, 18)); // NOI18N
        btnInventario.setForeground(new java.awt.Color(255, 255, 255));
        btnInventario.setText("Inventario");
        btnInventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInventarioActionPerformed(evt);
            }
        });
        jPanel3.add(btnInventario, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 160, 170, -1));

        btnInformes.setBackground(new java.awt.Color(33, 33, 35));
        btnInformes.setFont(new java.awt.Font("DejaVu Sans Condensed", 1, 18)); // NOI18N
        btnInformes.setForeground(new java.awt.Color(255, 255, 255));
        btnInformes.setText("Informes");
        jPanel3.add(btnInformes, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 260, 160, -1));

        btnUsuarios.setBackground(new java.awt.Color(33, 33, 35));
        btnUsuarios.setFont(new java.awt.Font("DejaVu Sans Condensed", 1, 18)); // NOI18N
        btnUsuarios.setForeground(new java.awt.Color(255, 255, 255));
        btnUsuarios.setText("Usuarios");
        btnUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuariosActionPerformed(evt);
            }
        });
        jPanel3.add(btnUsuarios, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 260, 170, -1));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 57, 440, 390));

        jPanel6.setBackground(new java.awt.Color(33, 33, 35));
        jPanel6.setBorder(null);
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_usuario.setFont(new java.awt.Font("DejaVu Sans", 1, 18)); // NOI18N
        txt_usuario.setForeground(new java.awt.Color(57, 113, 177));
        txt_usuario.setText("Usuario:");
        jPanel6.add(txt_usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 90, 30));

        txtUsuario1.setEditable(false);
        txtUsuario1.setBackground(new java.awt.Color(33, 33, 35));
        txtUsuario1.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        txtUsuario1.setForeground(new java.awt.Color(117, 117, 117));
        txtUsuario1.setText("X");
        txtUsuario1.setBorder(null);
        txtUsuario1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtUsuario1MouseClicked(evt);
            }
        });
        jPanel6.add(txtUsuario1, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 0, 20, 30));

        txtUsuario.setFont(new java.awt.Font("DejaVu Sans", 1, 14)); // NOI18N
        txtUsuario.setForeground(new java.awt.Color(255, 255, 255));
        jPanel6.add(txtUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, 290, 30));

        getContentPane().add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 440, 60));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVentasActionPerformed
        // TODO add your handling code here:
        //Ventas ventas = new Ventas();
        //ventas.setVisible(true);
        //VentasDark ventas = new VentasDark();
        VentasWhite ventas = new VentasWhite();
        ventas.setVisible(true);
    }//GEN-LAST:event_btnVentasActionPerformed

    private void btnConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfigActionPerformed
        // TODO add your handling code here:
        Config config = new Config();
        config.setVisible(true);
    }//GEN-LAST:event_btnConfigActionPerformed

    private void btnNominaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNominaActionPerformed
        // TODO add your handling code here:
        Nomina nomina = new Nomina();
        nomina.setVisible(true);
    }//GEN-LAST:event_btnNominaActionPerformed

    private void btnProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProveedoresActionPerformed
        // TODO add your handling code here:
        Proveedores proveedores = new Proveedores();
        proveedores.setVisible(true);
    }//GEN-LAST:event_btnProveedoresActionPerformed

    private void btnInventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInventarioActionPerformed
        // TODO add your handling code here:
        Inventario inventario = new Inventario();
        inventario.setVisible(true);
    }//GEN-LAST:event_btnInventarioActionPerformed

    private void btnClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientesActionPerformed
        // TODO add your handling code here:
        Clientes clientes = new Clientes();
        clientes.setVisible(true);
    }//GEN-LAST:event_btnClientesActionPerformed

    private void btnProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductosActionPerformed
        // TODO add your handling code here:
        Productos productos = new Productos();
        productos.setVisible(true);
    }//GEN-LAST:event_btnProductosActionPerformed

    private void btnComprasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComprasActionPerformed
        // TODO add your handling code here:
        Compras compras = new Compras();
        compras.setVisible(true);
    }//GEN-LAST:event_btnComprasActionPerformed

    private void btnUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuariosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUsuariosActionPerformed

    private void txtUsuario1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtUsuario1MouseClicked
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_txtUsuario1MouseClicked

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
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClientes;
    private javax.swing.JButton btnCompras;
    private javax.swing.JButton btnConfig;
    private javax.swing.JButton btnInformes;
    private javax.swing.JButton btnInventario;
    private javax.swing.JButton btnNomina;
    private javax.swing.JButton btnProductos;
    private javax.swing.JButton btnProveedores;
    private javax.swing.JButton btnUsuarios;
    private javax.swing.JButton btnVentas;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    public javax.swing.JLabel txtUsuario;
    private javax.swing.JTextField txtUsuario1;
    public javax.swing.JLabel txt_usuario;
    // End of variables declaration//GEN-END:variables
}