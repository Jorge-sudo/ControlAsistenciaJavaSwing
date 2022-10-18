package com.login;

import Estudiante_TablaDeSQL.Conexion;
import com.sun.awt.AWTUtilities;
import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Recupera_Usuario extends javax.swing.JFrame {

    public Recupera_Usuario() {
        initComponents();
        this.setLocationRelativeTo(this);
        Shape forma = new RoundRectangle2D.Double(0, 0, this.getBounds().width, this.getBounds().height, 15, 15);
        AWTUtilities.setWindowShape(this, forma);
        
        
    }
    private boolean minimiza;
    int xMouse, yMouse;

    public void Ingresa(String codigo) {
        String codig = "";
        String user = "";
        String pass = "";
        try {
            String sql = "SELECT * FROM empleado WHERE codrecuperacion = '" + codigo + "'";
            Connection cn = Conexion.getConection();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                user = rs.getString(4);
                pass = rs.getString(5);
                codig = rs.getString(6);
            }

            if (codig.equals(codigo)) {
                mensajeExito("  CODIGO CORRECTO");
                String user1 = "USUARIO: " + user, pass1 = "CONTRASEÑA: " + pass;

                if (JOptionPane.showConfirmDialog(this, "SU USUARIO Y CONTRASEÑA SON:\n\n"
                        + "USUARIO: " + user + "\nCONTRASEÑA: " + pass + "\n\n¿GUARDAR DATOS?", "CÓDIGO DE RECUPERACIÓN", JOptionPane.YES_NO_OPTION,
                        JOptionPane.INFORMATION_MESSAGE) == JOptionPane.YES_OPTION) {
                    guardarDatos(user1, pass1);
                }
            } else {
                mensajeError("  CODIGO INCORRECTO");
            }

        } catch (SQLException ex) {
            mensajeError(" ERROR EN LA CONEXION CON LA BASE DE DATOS.");
        }
    }

    private void guardarDatos(String user, String pass) {
        JFileChooser ventana = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de texto (*.txt)", "txt");
        ventana.addChoosableFileFilter(filter);
        ventana.setFileFilter(filter);
        ventana.setDialogTitle("GUARDAR DATOS DE USUARIO");

        if (ventana.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File archivo = ventana.getSelectedFile();
                BufferedWriter bw;
                if (archivo.exists()) {
                    bw = new BufferedWriter(new FileWriter(archivo.getAbsoluteFile() + ".txt"));
                    bw.write(user);
                    bw.newLine();
                    bw.write(pass);
                } else {
                    bw = new BufferedWriter(new FileWriter(archivo.getAbsoluteFile() + ".txt"));
                    bw.write(user);
                    bw.newLine();
                    bw.write(pass);
                }
                bw.close();
            } catch (IOException ex) {
                Logger.getLogger(Recupera_Usuario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.dispose();
    }

    void mensajeInformacion(String msje) {
        JOptionPane.showMessageDialog(null, msje, "Informacion", JOptionPane.INFORMATION_MESSAGE);
    }

    void mensajeError(String msje) {
        JOptionPane.showMessageDialog(null, msje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    void mensajeExito(String msje) {
        JOptionPane.showMessageDialog(null, msje, "Operacion Exitosa", JOptionPane.PLAIN_MESSAGE, new ImageIcon("src/com/images/exito.png"));
    }
    
    void mensajeAdvertencia(String msje) {
        JOptionPane.showMessageDialog(null, msje, "Advertencia", HEIGHT);
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel3 = new org.edisoncor.gui.panel.Panel();
        jLabel4 = new javax.swing.JLabel();
        cerrar1 = new javax.swing.JButton();
        btnRecuperar = new javax.swing.JPanel();
        loginBtnTxt = new javax.swing.JLabel();
        btnIniciarSesion = new javax.swing.JPanel();
        loginBtnTxt1 = new javax.swing.JLabel();
        txtSalir = new javax.swing.JPanel();
        btnSalir = new javax.swing.JLabel();
        txtMinimizar = new javax.swing.JPanel();
        btnMinimizar = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        panel3.setBackground(new java.awt.Color(153, 153, 255));
        panel3.setForeground(new java.awt.Color(255, 255, 255));
        panel3.setColorPrimario(new java.awt.Color(232, 255, 236));
        panel3.setColorSecundario(new java.awt.Color(232, 255, 236));
        panel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        panel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 0, -1, 373));

        cerrar1.setToolTipText("<html> <head> <style> #contenedor{background:#00688B;color:white; padding-left:10px;padding-right:10px;margin:0; padding-top:5px;padding-bottom:5px;} </style> </head> <body> <h4 id=\"contenedor\">Cerrar</h4> </body> </html>");
        cerrar1.setBorder(null);
        cerrar1.setBorderPainted(false);
        cerrar1.setContentAreaFilled(false);
        cerrar1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        cerrar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cerrar1ActionPerformed(evt);
            }
        });
        panel3.add(cerrar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1239, 6, -1, 33));

        btnRecuperar.setBackground(new java.awt.Color(0, 102, 15));
        btnRecuperar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 15), 2, true));

        loginBtnTxt.setFont(new java.awt.Font("Roboto Condensed", 1, 14)); // NOI18N
        loginBtnTxt.setForeground(new java.awt.Color(255, 255, 255));
        loginBtnTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        loginBtnTxt.setText("Recuperar");
        loginBtnTxt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        loginBtnTxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loginBtnTxtMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginBtnTxtMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginBtnTxtMouseExited(evt);
            }
        });

        javax.swing.GroupLayout btnRecuperarLayout = new javax.swing.GroupLayout(btnRecuperar);
        btnRecuperar.setLayout(btnRecuperarLayout);
        btnRecuperarLayout.setHorizontalGroup(
            btnRecuperarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(loginBtnTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
        );
        btnRecuperarLayout.setVerticalGroup(
            btnRecuperarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(loginBtnTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        panel3.add(btnRecuperar, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 290, 110, 40));

        btnIniciarSesion.setBackground(new java.awt.Color(0, 102, 15));
        btnIniciarSesion.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 15), 2, true));

        loginBtnTxt1.setFont(new java.awt.Font("Roboto Condensed", 1, 14)); // NOI18N
        loginBtnTxt1.setForeground(new java.awt.Color(255, 255, 255));
        loginBtnTxt1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        loginBtnTxt1.setText("Iniciar Sesion");
        loginBtnTxt1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        loginBtnTxt1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loginBtnTxt1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginBtnTxt1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginBtnTxt1MouseExited(evt);
            }
        });

        javax.swing.GroupLayout btnIniciarSesionLayout = new javax.swing.GroupLayout(btnIniciarSesion);
        btnIniciarSesion.setLayout(btnIniciarSesionLayout);
        btnIniciarSesionLayout.setHorizontalGroup(
            btnIniciarSesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(loginBtnTxt1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
        );
        btnIniciarSesionLayout.setVerticalGroup(
            btnIniciarSesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(loginBtnTxt1, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        panel3.add(btnIniciarSesion, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 290, 120, 40));

        txtSalir.setBackground(new java.awt.Color(232, 255, 236));

        btnSalir.setBackground(new java.awt.Color(153, 255, 204));
        btnSalir.setFont(new java.awt.Font("Roboto Light", 1, 24)); // NOI18N
        btnSalir.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnSalir.setText("X");
        btnSalir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSalir.setPreferredSize(new java.awt.Dimension(40, 40));
        btnSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSalirMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSalirMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSalirMouseExited(evt);
            }
        });

        javax.swing.GroupLayout txtSalirLayout = new javax.swing.GroupLayout(txtSalir);
        txtSalir.setLayout(txtSalirLayout);
        txtSalirLayout.setHorizontalGroup(
            txtSalirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );
        txtSalirLayout.setVerticalGroup(
            txtSalirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 30, Short.MAX_VALUE)
        );

        panel3.add(txtSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 0, 30, 30));

        txtMinimizar.setBackground(new java.awt.Color(232, 255, 236));

        btnMinimizar.setBackground(new java.awt.Color(153, 255, 204));
        btnMinimizar.setFont(new java.awt.Font("Roboto Light", 1, 24)); // NOI18N
        btnMinimizar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnMinimizar.setText("_");
        btnMinimizar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMinimizar.setPreferredSize(new java.awt.Dimension(40, 40));
        btnMinimizar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMinimizarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnMinimizarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnMinimizarMouseExited(evt);
            }
        });

        javax.swing.GroupLayout txtMinimizarLayout = new javax.swing.GroupLayout(txtMinimizar);
        txtMinimizar.setLayout(txtMinimizarLayout);
        txtMinimizarLayout.setHorizontalGroup(
            txtMinimizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(txtMinimizarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnMinimizar, javax.swing.GroupLayout.DEFAULT_SIZE, 18, Short.MAX_VALUE)
                .addContainerGap())
        );
        txtMinimizarLayout.setVerticalGroup(
            txtMinimizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(txtMinimizarLayout.createSequentialGroup()
                .addComponent(btnMinimizar, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 4, Short.MAX_VALUE))
        );

        panel3.add(txtMinimizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 0, 30, -1));

        jLabel10.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLabel10MouseDragged(evt);
            }
        });
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel10MousePressed(evt);
            }
        });
        panel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 520, 30));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText("Para recuperar su usuario y contraseña debe");
        panel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 170, 420, 20));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setText(" introducir su código de recuperación.");
        panel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 190, 360, 20));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/images/admin.png"))); // NOI18N
        panel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, -1, -1));

        jLabel3.setBackground(new java.awt.Color(0, 102, 153));
        jLabel3.setFont(new java.awt.Font("Cambria", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 52, 0));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("RECUPERAR USUARIO");
        panel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 60, -1, -1));

        jLabel5.setBackground(new java.awt.Color(0, 102, 153));
        jLabel5.setFont(new java.awt.Font("Cambria", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 52, 0));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Y CONTRASEÑA");
        panel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 90, 200, -1));

        txtCodigo.setBackground(new java.awt.Color(255, 255, 255));
        txtCodigo.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        txtCodigo.setForeground(new java.awt.Color(153, 153, 153));
        txtCodigo.setText("         Ingresa tu Codigo de Recuperacion");
        txtCodigo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtCodigoMouseClicked(evt);
            }
        });
        panel3.add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 230, 310, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel3, javax.swing.GroupLayout.PREFERRED_SIZE, 510, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cerrar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cerrar1ActionPerformed

    }//GEN-LAST:event_cerrar1ActionPerformed

    private void loginBtnTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginBtnTxtMouseClicked
        String codigo = txtCodigo.getText().trim();
        if (codigo.equals("") || codigo.equals("INGRESA TU CODIGO DE RECUPERACION")) {
            mensajeAdvertencia("¡Debe Ingresar el Codigo de Recuperacion!");
            this.txtCodigo.requestFocus();
        } else {
            Ingresa(codigo);
        }
    }//GEN-LAST:event_loginBtnTxtMouseClicked

    private void loginBtnTxtMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginBtnTxtMouseEntered
        btnRecuperar.setBackground(new Color(0, 179, 36));
    }//GEN-LAST:event_loginBtnTxtMouseEntered

    private void loginBtnTxtMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginBtnTxtMouseExited
        btnRecuperar.setBackground(new Color(0, 102, 15));
    }//GEN-LAST:event_loginBtnTxtMouseExited

    private void loginBtnTxt1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginBtnTxt1MouseClicked
        Login_Usuario1 iniciarUs = new Login_Usuario1();
        iniciarUs.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_loginBtnTxt1MouseClicked

    private void loginBtnTxt1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginBtnTxt1MouseEntered
        btnIniciarSesion.setBackground(new Color(0, 179, 36));
    }//GEN-LAST:event_loginBtnTxt1MouseEntered

    private void loginBtnTxt1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginBtnTxt1MouseExited
        btnIniciarSesion.setBackground(new Color(0, 102, 15));
    }//GEN-LAST:event_loginBtnTxt1MouseExited

    private void jLabel10MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_jLabel10MouseDragged

    private void jLabel10MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_jLabel10MousePressed

    private void btnSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalirMouseClicked
        int seleccion = JOptionPane.showConfirmDialog(null, "¿Seguro de que quiere salir?",
                "Salir",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (seleccion == 0) {
//                this.dispose();
//                new Ventana().setVisible(true);
            System.exit(0);
        }
    }//GEN-LAST:event_btnSalirMouseClicked

    private void btnSalirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalirMouseEntered
        txtSalir.setBackground(Color.red);
        btnSalir.setForeground(Color.white);
    }//GEN-LAST:event_btnSalirMouseEntered

    private void btnSalirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalirMouseExited
        txtSalir.setBackground(new Color(232, 255, 236));
        btnSalir.setForeground(Color.black);
    }//GEN-LAST:event_btnSalirMouseExited

    private void btnMinimizarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMinimizarMouseClicked
        this.setExtendedState(ICONIFIED);
        if (!minimiza) {
            minimiza = false;
        } else {
            minimiza = true;
        }
    }//GEN-LAST:event_btnMinimizarMouseClicked

    private void btnMinimizarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMinimizarMouseEntered
        txtMinimizar.setBackground(Color.red);
        btnMinimizar.setForeground(Color.white);
    }//GEN-LAST:event_btnMinimizarMouseEntered

    private void btnMinimizarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMinimizarMouseExited
        txtMinimizar.setBackground(new Color(232, 255, 236));
        btnMinimizar.setForeground(Color.black);
    }//GEN-LAST:event_btnMinimizarMouseExited

    private void txtCodigoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCodigoMouseClicked
        if (txtCodigo.getText().equals("         Ingresa tu Codigo de Recuperacion")) {
            txtCodigo.setText("   ");
            txtCodigo.setForeground(Color.black);
        }
    }//GEN-LAST:event_txtCodigoMouseClicked

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Recupera_Usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Recupera_Usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Recupera_Usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Recupera_Usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Recupera_Usuario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btnIniciarSesion;
    private javax.swing.JLabel btnMinimizar;
    private javax.swing.JPanel btnRecuperar;
    private javax.swing.JLabel btnSalir;
    private javax.swing.JButton cerrar1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel loginBtnTxt;
    private javax.swing.JLabel loginBtnTxt1;
    private org.edisoncor.gui.panel.Panel panel3;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JPanel txtMinimizar;
    private javax.swing.JPanel txtSalir;
    // End of variables declaration//GEN-END:variables

}
