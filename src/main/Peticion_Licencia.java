package main;

import Empleado_TablaDeSQL.Asistencia;
import Empleado_TablaDeSQL.EmpleadoDAO_JDBC;
import Estudiante_TablaDeSQL.Conexion;
import com.sun.awt.AWTUtilities;
import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import static java.awt.image.ImageObserver.HEIGHT;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Peticion_Licencia extends javax.swing.JFrame {

    private boolean minimiza;
    int xMouse, yMouse;

    public Peticion_Licencia() {
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

    int comprobarEmpleado(long ci) {
        Connection conexion;
        long aux = 0;
        int r = 0;
        if (txtDescripcion.getText().equals("")) {
            mensajeAdvertencia("¡Ingrese su Descripcion!");
        } else if (txtFechaFin.getDate() == null) {
            mensajeAdvertencia("¡Ingrese la fecha!");
        } else {
            try {
                conexion = Conexion.getConection();
                if (conexion.getAutoCommit()) {
                    conexion.setAutoCommit(false);
                }
                String sql = "SELECT ci FROM persona WHERE ci = " + ci + ";";
                Statement stmt = conexion.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while(rs.next()){
                     aux = rs.getLong(1);
                }
                if (ci == aux) {
                    r = 1;
                } else {
                    mensajeError("¡Error el Empleado no existe!");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Peticion_Licencia.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return r;
    }

    void registrarLicencia() {
        String aux;
        long ci;
        int contador = 0;
        Connection conexion = null;
        try {
            conexion = Conexion.getConection();
            if (conexion.getAutoCommit()) {
                //Esto hacemos para el AutoCommit no se inicialice solo ya que nosotros lo haremos manualmente 
                conexion.setAutoCommit(false);
            }
            EmpleadoDAO_JDBC empleadoDao = new EmpleadoDAO_JDBC();
            aux = txtCI.getText();
            ci = Long.parseLong(aux);
            java.util.Date utilDate = txtFechaFin.getDate();
            java.sql.Date Fecha = new java.sql.Date(utilDate.getTime());
            empleadoDao.registrarLicencia(ci, txtDescripcion.getText());
            Asistencia asistencia = new Asistencia(ci, null, Fecha, "Licencia", null);
            contador = empleadoDao.registrarAsistencia(asistencia);
            conexion.commit();
            mensajeExito("¡Se Registro con exito la Licencia!");
        } catch (SQLException ex) {
            System.out.println("ex = " + ex);
        }
    }
    
    long leerCI() {
        try {
            String aux = txtCI.getText().trim();
            long ID = Long.parseLong(aux);
            return ID;
        } catch (Exception e) {
            return -99;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        panel2 = new org.edisoncor.gui.panel.Panel();
        jLabel4 = new javax.swing.JLabel();
        cerrar1 = new javax.swing.JButton();
        loginBtn = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        txtFechaFin = new com.toedter.calendar.JDateChooser();
        txtCI = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        userLabel1 = new javax.swing.JLabel();
        title = new javax.swing.JLabel();
        favicon = new javax.swing.JLabel();
        Title = new javax.swing.JPanel();
        txtMinimizar = new javax.swing.JPanel();
        btnMinimizar = new javax.swing.JLabel();
        txtSalir = new javax.swing.JPanel();
        btnSalir = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(242, 242, 242));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel2.setBackground(new java.awt.Color(255, 255, 255));
        panel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 41), 1, true));
        panel2.setForeground(new java.awt.Color(37, 165, 47));
        panel2.setColorPrimario(new java.awt.Color(232, 255, 236));
        panel2.setColorSecundario(new java.awt.Color(232, 255, 236));
        panel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        panel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 0, -1, 373));

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
        panel2.add(cerrar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1239, 6, -1, 33));

        loginBtn.setBackground(new java.awt.Color(0, 102, 15));
        loginBtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 15), 2));

        btnGuardar.setFont(new java.awt.Font("Roboto Black", 1, 14)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnGuardar.setText("REGISTRAR");
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGuardarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGuardarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGuardarMouseExited(evt);
            }
        });

        javax.swing.GroupLayout loginBtnLayout = new javax.swing.GroupLayout(loginBtn);
        loginBtn.setLayout(loginBtnLayout);
        loginBtnLayout.setHorizontalGroup(
            loginBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnGuardar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
        );
        loginBtnLayout.setVerticalGroup(
            loginBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        panel2.add(loginBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 180, 100, -1));

        jLabel1.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Descripcion");
        panel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, 120, 20));

        txtDescripcion.setBackground(new java.awt.Color(255, 255, 255));
        txtDescripcion.setColumns(20);
        txtDescripcion.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtDescripcion.setForeground(new java.awt.Color(51, 51, 51));
        txtDescripcion.setRows(5);
        jScrollPane1.setViewportView(txtDescripcion);

        panel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, 340, 100));

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("FECHA DE LICENCIA ");
        panel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 50, -1, -1));
        panel2.add(txtFechaFin, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 50, 100, -1));

        txtCI.setBackground(new java.awt.Color(232, 255, 236));
        txtCI.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtCI.setForeground(new java.awt.Color(102, 102, 102));
        txtCI.setText("Ingrese su cedula de identidad");
        txtCI.setBorder(null);
        txtCI.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtCIMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtCIMouseReleased(evt);
            }
        });
        txtCI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCIKeyTyped(evt);
            }
        });
        panel2.add(txtCI, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, 180, 30));

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        panel2.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 190, 10));

        userLabel1.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        userLabel1.setForeground(new java.awt.Color(51, 51, 51));
        userLabel1.setText("CI ");
        panel2.add(userLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 30, -1, -1));

        jPanel1.add(panel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 119, 609, 260));

        title.setFont(new java.awt.Font("Cambria", 1, 30)); // NOI18N
        title.setForeground(new java.awt.Color(0, 52, 0));
        title.setText("PETICION DE LICENCIAS");
        jPanel1.add(title, new org.netbeans.lib.awtextra.AbsoluteConstraints(161, 42, -1, 30));

        favicon.setFont(new java.awt.Font("Roboto Black", 1, 24)); // NOI18N
        favicon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/images/buscar.png"))); // NOI18N
        jPanel1.add(favicon, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 20, -1, 93));

        Title.setBackground(new java.awt.Color(242, 242, 242));
        Title.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                TitleMouseDragged(evt);
            }
        });
        Title.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                TitleMousePressed(evt);
            }
        });
        Title.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtMinimizar.setBackground(new java.awt.Color(242, 242, 242));

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

        Title.add(txtMinimizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 0, -1, -1));

        txtSalir.setBackground(new java.awt.Color(242, 242, 242));

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

        Title.add(txtSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 0, -1, -1));

        jPanel1.add(Title, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 650, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cerrar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cerrar1ActionPerformed

    }//GEN-LAST:event_cerrar1ActionPerformed

    private void btnGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseClicked
        if (leerCI() != -99) {
            String aux = txtCI.getText();
            long ci = Long.parseLong(aux);
            int r = comprobarEmpleado(ci);
            if (r == 1) {
                registrarLicencia();
            }
        }else{
            mensajeAdvertencia("¡Ingrese su CI!");
        }

    }//GEN-LAST:event_btnGuardarMouseClicked

    private void btnGuardarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseEntered
        loginBtn.setBackground(new Color(0, 179, 36));
    }//GEN-LAST:event_btnGuardarMouseEntered

    private void btnGuardarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseExited
        loginBtn.setBackground(new Color(0, 102, 15));
    }//GEN-LAST:event_btnGuardarMouseExited

    private void txtCIMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCIMousePressed

        if (txtCI.getText().equals("Ingrese su cedula de identidad")) {
            txtCI.setText("");
            txtCI.setForeground(Color.black);
        }
    }//GEN-LAST:event_txtCIMousePressed

    private void txtCIMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCIMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCIMouseReleased

    private void txtCIKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCIKeyTyped
        char validar = evt.getKeyChar();

        if (Character.isLetter(validar)) {
            getToolkit().beep();
            evt.consume();

            mensajeAdvertencia("¡Debe Ingresar solo Numeros!");
        }
    }//GEN-LAST:event_txtCIKeyTyped

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
        txtMinimizar.setBackground(new Color(242, 242, 242));
        btnMinimizar.setForeground(Color.black);
    }//GEN-LAST:event_btnMinimizarMouseExited

    private void btnSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalirMouseClicked
        int seleccion = JOptionPane.showConfirmDialog(null, "¿Seguro de que quiere volver menu?",
                "Kinder \"PATITAS SUAVES\"",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (seleccion == 0) {
            this.dispose();
            //            new Menu1().setVisible(true);
        }
    }//GEN-LAST:event_btnSalirMouseClicked

    private void btnSalirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalirMouseEntered
        txtSalir.setBackground(Color.red);
        btnSalir.setForeground(Color.white);
    }//GEN-LAST:event_btnSalirMouseEntered

    private void btnSalirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalirMouseExited
        txtSalir.setBackground(new Color(242, 242, 242));
        btnSalir.setForeground(Color.black);
    }//GEN-LAST:event_btnSalirMouseExited

    private void TitleMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TitleMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_TitleMouseDragged

    private void TitleMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TitleMousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_TitleMousePressed

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Peticion_Licencia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Peticion_Licencia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Peticion_Licencia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Peticion_Licencia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Peticion_Licencia().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Title;
    private javax.swing.JLabel btnGuardar;
    private javax.swing.JLabel btnMinimizar;
    private javax.swing.JLabel btnSalir;
    private javax.swing.JButton cerrar1;
    private javax.swing.JLabel favicon;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPanel loginBtn;
    private org.edisoncor.gui.panel.Panel panel2;
    private javax.swing.JLabel title;
    private javax.swing.JTextField txtCI;
    private javax.swing.JTextArea txtDescripcion;
    private com.toedter.calendar.JDateChooser txtFechaFin;
    private javax.swing.JPanel txtMinimizar;
    private javax.swing.JPanel txtSalir;
    private javax.swing.JLabel userLabel1;
    // End of variables declaration//GEN-END:variables
}
