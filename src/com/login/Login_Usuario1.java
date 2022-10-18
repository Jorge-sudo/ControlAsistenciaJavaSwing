package com.login;

import Estudiante_TablaDeSQL.Conexion;
import com.sun.awt.AWTUtilities;
import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import static java.awt.image.ImageObserver.HEIGHT;
import java.sql.*;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import main.*;

public class Login_Usuario1 extends javax.swing.JFrame {

    int xMouse, yMouse;
    private boolean minimiza;

    public Login_Usuario1() {
        initComponents();
        this.setLocationRelativeTo(this);
        Shape forma = new RoundRectangle2D.Double(0, 0, this.getBounds().width, this.getBounds().height, 15, 15);
        AWTUtilities.setWindowShape(this, forma);

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

    public void Ingresa(String usuario, String pas) {
        String user = "", pass = "";
        String cargo = null;
        try {
            String sql = "SELECT * FROM empleado WHERE usuario = '" + usuario + "'";
            Connection cn = Conexion.getConection();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                user = rs.getString(4);
                pass = rs.getString(5);
                cargo = rs.getString(9);
            }

            if (user.equals(usuario) && pass.equals(pas)) {
                if (cargo.trim().equals("Director(a)")) {
                    mensajeExito("Se ingreso con exito al sistema");
                    Menu1 menu = new Menu1();
                    menu.setVisible(true);
                }else{
                    mensajeExito("Se ingreso con exito al sistema");
                    Menu2 menu = new Menu2();
                    menu.setVisible(true);
                }

                this.dispose();
            } else {
                if (usuario.equals("Ingrese su nombre de usuario") || usuario.equals("")) {
                    mensajeAdvertencia("¡Ingrese su usuario!");
                    this.txtUsuario.requestFocus();
                } else if (pas.equals("********") || pas.equals("")) {
                    mensajeAdvertencia("¡Ingrese su contraseña!");
                    this.txtPassword.requestFocus();
                } else {
                    mensajeError("¡Contraseña o Usuario Incorrecto!");
                }
            }

        } catch (SQLException ex) {
            mensajeError(" Error en la conexion con la base de datos");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel3 = new org.edisoncor.gui.panel.Panel();
        jLabel4 = new javax.swing.JLabel();
        cerrar1 = new javax.swing.JButton();
        title = new javax.swing.JLabel();
        userLabel = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        passLabel = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        jSeparator2 = new javax.swing.JSeparator();
        loginBtn = new javax.swing.JPanel();
        loginBtnTxt = new javax.swing.JLabel();
        aqui = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtSalir = new javax.swing.JPanel();
        btnSalir = new javax.swing.JLabel();
        txtMinimizar = new javax.swing.JPanel();
        btnMinimizar = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setUndecorated(true);
        setResizable(false);

        panel3.setBackground(new java.awt.Color(153, 153, 255));
        panel3.setForeground(new java.awt.Color(255, 255, 255));
        panel3.setColorPrimario(new java.awt.Color(232, 255, 236));
        panel3.setColorSecundario(new java.awt.Color(232, 255, 236));
        panel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        panel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 0, -1, 595));

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
        panel3.add(cerrar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(912, 6, -1, 33));

        title.setFont(new java.awt.Font("Cambria", 1, 24)); // NOI18N
        title.setForeground(new java.awt.Color(0, 0, 0));
        title.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/images/Login2.png"))); // NOI18N
        title.setText("    Inicia sesión");
        panel3.add(title, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 280, 100));

        userLabel.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        userLabel.setForeground(new java.awt.Color(0, 0, 0));
        userLabel.setText("USUARIO");
        panel3.add(userLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 140, -1, -1));

        txtUsuario.setBackground(new java.awt.Color(232, 255, 236));
        txtUsuario.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtUsuario.setForeground(new java.awt.Color(102, 102, 102));
        txtUsuario.setText("Ingrese su nombre de usuario");
        txtUsuario.setBorder(null);
        txtUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtUsuarioMousePressed(evt);
            }
        });
        panel3.add(txtUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 170, 250, 30));

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        panel3.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 200, 270, 20));

        passLabel.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        passLabel.setForeground(new java.awt.Color(0, 0, 0));
        passLabel.setText("CONTRASEÑA");
        panel3.add(passLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 220, -1, -1));

        txtPassword.setBackground(new java.awt.Color(232, 255, 236));
        txtPassword.setForeground(new java.awt.Color(102, 102, 102));
        txtPassword.setText("********");
        txtPassword.setBorder(null);
        txtPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtPasswordMousePressed(evt);
            }
        });
        panel3.add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 250, 250, 30));

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        panel3.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 280, 270, 10));

        loginBtn.setBackground(new java.awt.Color(0, 102, 15));
        loginBtn.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 15), 2, true));

        loginBtnTxt.setFont(new java.awt.Font("Roboto Condensed", 1, 14)); // NOI18N
        loginBtnTxt.setForeground(new java.awt.Color(255, 255, 255));
        loginBtnTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        loginBtnTxt.setText("Ingresar");
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

        javax.swing.GroupLayout loginBtnLayout = new javax.swing.GroupLayout(loginBtn);
        loginBtn.setLayout(loginBtnLayout);
        loginBtnLayout.setHorizontalGroup(
            loginBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(loginBtnTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
        );
        loginBtnLayout.setVerticalGroup(
            loginBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(loginBtnTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        panel3.add(loginBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 310, 110, 40));

        aqui.setBackground(new java.awt.Color(102, 102, 102));
        aqui.setForeground(new java.awt.Color(51, 51, 51));
        aqui.setText("¿Olvidaste tú usuario o contraseña?");
        aqui.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        aqui.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                aquiMouseMoved(evt);
            }
        });
        aqui.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                aquiMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                aquiMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                aquiMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                aquiMousePressed(evt);
            }
        });
        panel3.add(aqui, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 370, 210, 20));

        jLabel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLabel1MouseDragged(evt);
            }
        });
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel1MousePressed(evt);
            }
        });
        panel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 360, 40));

        txtSalir.setBackground(new java.awt.Color(232, 255, 236));

        btnSalir.setBackground(new java.awt.Color(63, 55, 192));
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

        panel3.add(txtSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 0, -1, -1));

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

        panel3.add(txtMinimizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 0, 30, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel3, javax.swing.GroupLayout.PREFERRED_SIZE, 431, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel3, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loginBtnTxtMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginBtnTxtMouseEntered

        loginBtn.setBackground(new Color(0, 179, 36));
    }//GEN-LAST:event_loginBtnTxtMouseEntered

    private void loginBtnTxtMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginBtnTxtMouseExited
        loginBtn.setBackground(new Color(0, 102, 15));

    }//GEN-LAST:event_loginBtnTxtMouseExited

    private void txtUsuarioMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtUsuarioMousePressed
        if (txtUsuario.getText().equals("Ingrese su nombre de usuario")) {
            txtUsuario.setText("");
            txtUsuario.setForeground(Color.black);
        }
        if (String.valueOf(txtPassword.getPassword()).isEmpty()) {
            txtPassword.setText("********");
            txtPassword.setForeground(new Color(102, 102, 102));
        }
    }//GEN-LAST:event_txtUsuarioMousePressed

    private void txtPasswordMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPasswordMousePressed
        if (String.valueOf(txtPassword.getPassword()).equals("********")) {
            txtPassword.setText("");
            txtPassword.setForeground(Color.black);
        }
        if (txtUsuario.getText().isEmpty()) {
            txtUsuario.setText("Ingrese su nombre de usuario");
            txtUsuario.setForeground(new Color(102, 102, 102));
        }
    }//GEN-LAST:event_txtPasswordMousePressed

    private void loginBtnTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginBtnTxtMouseClicked

        String us = txtUsuario.getText().trim();
        String pas = txtPassword.getText().trim();
        Ingresa(us, pas);
    }//GEN-LAST:event_loginBtnTxtMouseClicked


    private void aquiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_aquiMouseClicked
        Recupera_Usuario recupera = new Recupera_Usuario();
        recupera.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_aquiMouseClicked

    private void aquiMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_aquiMouseMoved

    }//GEN-LAST:event_aquiMouseMoved

    private void cerrar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cerrar1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cerrar1ActionPerformed

    private void jLabel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_jLabel1MousePressed

    private void jLabel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_jLabel1MouseDragged

    private void aquiMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_aquiMouseEntered
        aqui.setForeground(Color.gray);
    }//GEN-LAST:event_aquiMouseEntered

    private void aquiMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_aquiMouseExited
        aqui.setForeground(new Color(51, 51, 51));
    }//GEN-LAST:event_aquiMouseExited

    private void aquiMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_aquiMousePressed

    }//GEN-LAST:event_aquiMousePressed

    private void btnSalirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalirMouseExited
        txtSalir.setBackground(new Color(232, 255, 236));
        btnSalir.setForeground(Color.black);
    }//GEN-LAST:event_btnSalirMouseExited

    private void btnSalirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalirMouseEntered
        txtSalir.setBackground(Color.red);
        btnSalir.setForeground(Color.white);
    }//GEN-LAST:event_btnSalirMouseEntered

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

    private void btnMinimizarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMinimizarMouseExited
        txtMinimizar.setBackground(new Color(232, 255, 236));
        btnMinimizar.setForeground(Color.black);
    }//GEN-LAST:event_btnMinimizarMouseExited

    private void btnMinimizarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMinimizarMouseEntered
        txtMinimizar.setBackground(Color.red);
        btnMinimizar.setForeground(Color.white);
    }//GEN-LAST:event_btnMinimizarMouseEntered

    private void btnMinimizarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMinimizarMouseClicked
        this.setExtendedState(ICONIFIED);
        if (!minimiza) {
            minimiza = false;
        } else {
            minimiza = true;
        }
    }//GEN-LAST:event_btnMinimizarMouseClicked

    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login_Usuario1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login_Usuario1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login_Usuario1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login_Usuario1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login_Usuario1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel aqui;
    private javax.swing.JLabel btnMinimizar;
    private javax.swing.JLabel btnSalir;
    private javax.swing.JButton cerrar1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPanel loginBtn;
    private javax.swing.JLabel loginBtnTxt;
    private org.edisoncor.gui.panel.Panel panel3;
    private javax.swing.JLabel passLabel;
    private javax.swing.JLabel title;
    private javax.swing.JPanel txtMinimizar;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JPanel txtSalir;
    private javax.swing.JTextField txtUsuario;
    private javax.swing.JLabel userLabel;
    // End of variables declaration//GEN-END:variables
}
