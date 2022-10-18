package jPanel;

import Estudiante_TablaDeSQL.Conexion;
import Empleado_TablaDeSQL.EmpleadoDAO_JDBC;
import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import main.Actualizar_Empleado;
import proyectoasistenciapersonal.Empleado;

/**
 *
 * @author JORGE
 */
public class Tabla_Empleado extends javax.swing.JPanel {

    Empleado emp; //declara un objeto emp de la clase Empleado
    //  File archi;
    DefaultTableModel dtm = new DefaultTableModel();
    int clickTabla;
    public static String empleado_update = "";
    
    public static boolean masculino = false;
    public static boolean femenino = false;

    public Tabla_Empleado() {
        initComponents();

        setBackground(new Color(0, 0, 0, 0));

        tablaEmpleados.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaEmpleados.getTableHeader().setOpaque(false);
        tablaEmpleados.getTableHeader().setBackground(new Color(0, 179, 36));
        tablaEmpleados.getTableHeader().setForeground(new Color(255, 255, 255));
        tablaEmpleados.setRowHeight(25);

        

    }

    public void mostrarProfesor() {
        Connection conexion = null;
        try {
            conexion = Conexion.getConection();
            if (conexion.getAutoCommit()) {
                //Esto hacemos para el AutoCommit no se inicialice solo ya que nosotros lo haremos manualmente 
                conexion.setAutoCommit(false);
            }
            EmpleadoDAO_JDBC empleado = new EmpleadoDAO_JDBC(conexion);
            empleado.visualizar_Profesor(tablaEmpleados);
//        tablaEmpleados.setModel(modelo);
            conexion.commit();
        } catch (SQLException ex) {
            mensajeError(" Hay algun error " + ex);
            try {
                //Perso si sale un error entonces rollback se ejecutara y no se realizara ningun cambio
                conexion.rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace(System.out);
            }
        } catch (Exception ex) {
            mensajeError(" Hay algun error " + ex);
        }
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

    public void Ingresa(String contraseña, String ci) {
        String password = "";
        String genero;
        String aula;
        try {
            String sql = "SELECT * FROM empleado WHERE ci = '" + ci + "'";
            Connection cn = Conexion.getConection();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                password = rs.getString(5);
                genero = rs.getString(8);
                if(genero.equals("M")){
                    masculino = true;
                }else if (genero.equals("F")){
                    femenino = true;
                }
            }

            if (password.equals(contraseña)) {
                mensajeExito(" Podra Actualizar sus Datos");
                Actualizar_Empleado empleado_update_ = new Actualizar_Empleado();
                empleado_update_.setVisible(true);
            } else if (contraseña.equals("")) {

            } else {
                mensajeError("Contraseña Incorrecta");
            }

        } catch (SQLException ex) {
            mensajeError(" Error en la conexion con la base de datos");
        }
    }
    
    String tablaInsertar() {
        return ((String) cmbtabla.getSelectedItem());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        panel2 = new org.edisoncor.gui.panel.Panel();
        jLabel4 = new javax.swing.JLabel();
        cerrar1 = new javax.swing.JButton();
        logoname = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaEmpleados = new javax.swing.JTable();
        cmbtabla = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        btnRegistrar = new javax.swing.JPanel();
        loginBtnTxt = new javax.swing.JLabel();

        setBackground(new java.awt.Color(191, 240, 194));

        jPanel1.setBackground(new java.awt.Color(242, 242, 242));

        panel2.setBackground(new java.awt.Color(255, 255, 255));
        panel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 41), 1, true));
        panel2.setForeground(new java.awt.Color(255, 255, 255));
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

        logoname.setBackground(new java.awt.Color(255, 255, 255));
        logoname.setFont(new java.awt.Font("Cambria", 1, 36)); // NOI18N
        logoname.setForeground(new java.awt.Color(0, 52, 0));
        logoname.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logoname.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/images/mantenimiento.png"))); // NOI18N
        logoname.setText("     TABLA DE USUARIOS");
        panel2.add(logoname, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 520, 90));

        tablaEmpleados.setForeground(new java.awt.Color(51, 51, 51));
        tablaEmpleados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tablaEmpleados.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tablaEmpleados.setFocusable(false);
        tablaEmpleados.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tablaEmpleados.setRowHeight(25);
        tablaEmpleados.setSelectionBackground(new java.awt.Color(168, 255, 181));
        tablaEmpleados.setSelectionForeground(new java.awt.Color(102, 102, 102));
        tablaEmpleados.setShowVerticalLines(false);
        tablaEmpleados.setSurrendersFocusOnKeystroke(true);
        tablaEmpleados.getTableHeader().setReorderingAllowed(false);
        tablaEmpleados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaEmpleadosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaEmpleados);

        panel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 770, 330));

        cmbtabla.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Empleados", "Profesores" }));
        panel2.add(cmbtabla, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 120, -1, -1));

        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("LISTAR:");
        panel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 120, -1, -1));

        btnRegistrar.setBackground(new java.awt.Color(0, 102, 15));
        btnRegistrar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 15), 2));
        btnRegistrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnRegistrarMouseEntered(evt);
            }
        });

        loginBtnTxt.setFont(new java.awt.Font("Roboto Condensed", 1, 14)); // NOI18N
        loginBtnTxt.setForeground(new java.awt.Color(255, 255, 255));
        loginBtnTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        loginBtnTxt.setText("Buscar");
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

        javax.swing.GroupLayout btnRegistrarLayout = new javax.swing.GroupLayout(btnRegistrar);
        btnRegistrar.setLayout(btnRegistrarLayout);
        btnRegistrarLayout.setHorizontalGroup(
            btnRegistrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(loginBtnTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
        );
        btnRegistrarLayout.setVerticalGroup(
            btnRegistrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(loginBtnTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
        );

        panel2.add(btnRegistrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 120, 80, 30));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, 814, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(panel2, javax.swing.GroupLayout.DEFAULT_SIZE, 528, Short.MAX_VALUE)
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cerrar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cerrar1ActionPerformed

    }//GEN-LAST:event_cerrar1ActionPerformed

    private void tablaEmpleadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaEmpleadosMouseClicked

        try {
            if (evt.getClickCount() == 1) {
                empleado_update = tablaEmpleados.getValueAt(tablaEmpleados.getSelectedRow(), 1).toString();
                System.out.println(empleado_update);
                String contraseña = JOptionPane.showInputDialog("Ingrese su Contraseña, para poder modificar sus datos");
                Ingresa(contraseña, empleado_update);
            }
        } catch (Exception e) {
            
        }

    }//GEN-LAST:event_tablaEmpleadosMouseClicked

    private void loginBtnTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginBtnTxtMouseClicked
        String tabla = tablaInsertar();
        EmpleadoDAO_JDBC empleado = new EmpleadoDAO_JDBC();
        if (tabla.equalsIgnoreCase("Empleados")) {
            try {
                empleado.visualizar_Empleado(tablaEmpleados);
            } catch (SQLException ex) {
                Logger.getLogger(Tabla_Empleado.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if (tabla.equals("Profesores")){
           mostrarProfesor();
        }
    }//GEN-LAST:event_loginBtnTxtMouseClicked

    private void loginBtnTxtMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginBtnTxtMouseEntered
        btnRegistrar.setBackground(new Color(0,179,36));
    }//GEN-LAST:event_loginBtnTxtMouseEntered

    private void loginBtnTxtMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginBtnTxtMouseExited
        btnRegistrar.setBackground(new Color(0,102,15));
    }//GEN-LAST:event_loginBtnTxtMouseExited

    private void btnRegistrarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegistrarMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRegistrarMouseEntered


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btnRegistrar;
    private javax.swing.JButton cerrar1;
    private javax.swing.JComboBox<String> cmbtabla;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel loginBtnTxt;
    private javax.swing.JLabel logoname;
    private org.edisoncor.gui.panel.Panel panel2;
    private javax.swing.JTable tablaEmpleados;
    // End of variables declaration//GEN-END:variables
}
