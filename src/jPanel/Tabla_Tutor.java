package jPanel;

import Estudiante_TablaDeSQL.*;
import java.awt.Color;
import java.awt.Font;
import static java.awt.image.ImageObserver.HEIGHT;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import main.Actualizar_Tutor;
import proyectoasistenciapersonal.Empleado;

/**
 * @author JORGE
 */
public class Tabla_Tutor extends javax.swing.JPanel {

    Empleado emp; //declara un objeto emp de la clase Empleado
    DefaultTableModel dtm = new DefaultTableModel();
    int clickTabla;
    public static String tutor_update = "";

    public Tabla_Tutor() {
        initComponents();

        setBackground(new Color(0, 0, 0, 0));

        tablaEmpleados.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaEmpleados.getTableHeader().setOpaque(false);
        tablaEmpleados.getTableHeader().setBackground(new Color(0, 179, 36));
        tablaEmpleados.getTableHeader().setForeground(new Color(255, 255, 255));
        tablaEmpleados.setRowHeight(25);

        mostrarTutores();
    }

    public void mostrarTutores() {
        Connection conexion = null;
        try {
            conexion = Conexion.getConection();
            if (conexion.getAutoCommit()) {
                //Esto hacemos para el AutoCommit no se inicialice solo ya que nosotros lo haremos manualmente 
                conexion.setAutoCommit(false);
            }
            EstudianteDAO_JDBC empleado = new EstudianteDAO_JDBC(conexion);
            empleado.visualizar_Tutor(tablaEmpleados);
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

    public void Ingresa(String contraseña) {
        String cargo = null;
        try {
            String sql = "SELECT * FROM empleado WHERE contraseña = '" + contraseña + "'";
            Connection cn = Conexion.getConection();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                cargo = rs.getString(9);
            }

            if (cargo != null) {
                if ("Regente(a)".equals(cargo)) {
                    mensajeError("¡Usted No tiene Autorizacion!");
                } else {
                    mensajeExito("¡Podra Actualizar sus Datos!");
                    Actualizar_Tutor tutor_update_ = new Actualizar_Tutor();
                    tutor_update_.setVisible(true);
                }
            } else {
                mensajeError("¡No existe este Usuario!");
            }

        } catch (SQLException ex) {
            mensajeError(" Error en la conexion con la base de datos");
        }
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

        jPanel1.setBackground(new java.awt.Color(242, 242, 242));

        panel2.setBackground(new java.awt.Color(204, 255, 255));
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
        logoname.setText("    TABLA DE TUTORES");
        panel2.add(logoname, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 600, 90));

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
        tablaEmpleados.getTableHeader().setReorderingAllowed(false);
        tablaEmpleados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaEmpleadosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaEmpleados);

        panel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 774, 390));

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
                tutor_update = tablaEmpleados.getValueAt(tablaEmpleados.getSelectedRow(), 1).toString();
                System.out.println(tablaEmpleados.getValueAt(tablaEmpleados.getSelectedRow(), 1).toString());
                String contraseña = JOptionPane.showInputDialog("Ingrese su Contraseña, para poder modificar sus datos");
                if (!contraseña.equals("")) {
                    Ingresa(contraseña);
                }
            }
        } catch (Exception e) {
        }

    }//GEN-LAST:event_tablaEmpleadosMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cerrar1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel logoname;
    private org.edisoncor.gui.panel.Panel panel2;
    private javax.swing.JTable tablaEmpleados;
    // End of variables declaration//GEN-END:variables
}
