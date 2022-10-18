package jPanel;

import Empleado_TablaDeSQL.EmpleadoDAO_JDBC;
import Estudiante_TablaDeSQL.Conexion;
import Estudiante_TablaDeSQL.EstudianteDAO_JDBC;
import Estudiante_TablaDeSQL.ExportarExel;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.*;
import java.awt.Color;
import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.WindowConstants;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class Reportes extends javax.swing.JPanel {

    public Reportes() {
        initComponents();

        Jtabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        Jtabla.getTableHeader().setOpaque(false);
        Jtabla.getTableHeader().setBackground(new Color(0,179,36));
        Jtabla.getTableHeader().setForeground(new Color(255, 255, 255));
        Jtabla.setRowHeight(25);
    }

    String tablaInsertar() {
        return ((String) cbxTablaExportar.getSelectedItem());
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
        Jtabla = new javax.swing.JTable();
        btnRegistrar = new javax.swing.JPanel();
        loginBtnTxt = new javax.swing.JLabel();
        cbxTablaExportar = new javax.swing.JComboBox<>();
        txtNivelP = new javax.swing.JLabel();
        panel_exel = new javax.swing.JPanel();
        btn_ExportarExel = new javax.swing.JLabel();
        panel_pdf = new javax.swing.JPanel();
        btn_ExportarPdf = new javax.swing.JLabel();

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
        logoname.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/images/reportes.png"))); // NOI18N
        logoname.setText("     REPORTES ");
        panel2.add(logoname, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 130));

        Jtabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        Jtabla.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Jtabla.setFocusable(false);
        Jtabla.setIntercellSpacing(new java.awt.Dimension(0, 0));
        Jtabla.setRowHeight(25);
        Jtabla.setSelectionBackground(new java.awt.Color(128, 200, 191));
        Jtabla.setShowVerticalLines(false);
        Jtabla.getTableHeader().setReorderingAllowed(false);
        Jtabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JtablaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Jtabla);

        panel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 770, 300));

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnRegistrarLayout.createSequentialGroup()
                .addGap(0, 27, Short.MAX_VALUE)
                .addComponent(loginBtnTxt))
        );
        btnRegistrarLayout.setVerticalGroup(
            btnRegistrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(loginBtnTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
        );

        panel2.add(btnRegistrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 170, 80, 30));

        cbxTablaExportar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Estudiante", "Empleado", "Tutor", "Asistencias" }));
        cbxTablaExportar.setMinimumSize(new java.awt.Dimension(133, 30));
        cbxTablaExportar.setPreferredSize(new java.awt.Dimension(133, 32));
        cbxTablaExportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxTablaExportarActionPerformed(evt);
            }
        });
        panel2.add(cbxTablaExportar, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 160, 150, 30));

        txtNivelP.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        txtNivelP.setForeground(new java.awt.Color(51, 51, 51));
        txtNivelP.setText("Tabla que quiere exportar:");
        panel2.add(txtNivelP, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 160, -1, 30));

        panel_exel.setBackground(new java.awt.Color(0, 155, 43));
        panel_exel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 155, 43), 2));
        panel_exel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_ExportarExel.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btn_ExportarExel.setForeground(new java.awt.Color(255, 255, 255));
        btn_ExportarExel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/images/Excel_2013_23480.png"))); // NOI18N
        btn_ExportarExel.setText("EXPORTAR");
        btn_ExportarExel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_ExportarExel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_ExportarExelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_ExportarExelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_ExportarExelMouseExited(evt);
            }
        });
        panel_exel.add(btn_ExportarExel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 130, 60));

        panel2.add(panel_exel, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 130, 140, 60));

        panel_pdf.setBackground(new java.awt.Color(0, 155, 43));
        panel_pdf.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 155, 43), 2));
        panel_pdf.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_ExportarPdf.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btn_ExportarPdf.setForeground(new java.awt.Color(255, 255, 255));
        btn_ExportarPdf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/images/pdf.png"))); // NOI18N
        btn_ExportarPdf.setText(" EXPORTAR");
        btn_ExportarPdf.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_ExportarPdf.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_ExportarPdfMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_ExportarPdfMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_ExportarPdfMouseExited(evt);
            }
        });
        panel_pdf.add(btn_ExportarPdf, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 60));

        panel2.add(panel_pdf, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 50, 140, 60));

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
                .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, 531, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
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

    private void JtablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JtablaMouseClicked

    }//GEN-LAST:event_JtablaMouseClicked

    private void loginBtnTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginBtnTxtMouseClicked
        String tabla = tablaInsertar();
        EmpleadoDAO_JDBC empleado = new EmpleadoDAO_JDBC();
        EstudianteDAO_JDBC estudiante = new EstudianteDAO_JDBC();
        if (tabla.equalsIgnoreCase("Empleado")) {
            empleado.exportar_Empleado(Jtabla);
        }else if (tabla.equals("Asistencias")){
            empleado.visualizar_Asistencias(Jtabla);
        }else if (tabla.equals("Tutor")){
            estudiante.exportar_Tutor(Jtabla);
        }else if (tabla.equals("Estudiante")){
            estudiante.exportar_Estudiante(Jtabla);
        }
    }//GEN-LAST:event_loginBtnTxtMouseClicked

    private void loginBtnTxtMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginBtnTxtMouseEntered
        btnRegistrar.setBackground(new Color(0,179,36));
    }//GEN-LAST:event_loginBtnTxtMouseEntered

    private void loginBtnTxtMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginBtnTxtMouseExited
        btnRegistrar.setBackground(new Color(0,102,15));
    }//GEN-LAST:event_loginBtnTxtMouseExited

    private void cbxTablaExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxTablaExportarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxTablaExportarActionPerformed

    private void btnRegistrarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegistrarMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRegistrarMouseEntered

    private void btn_ExportarExelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ExportarExelMouseClicked
        try {
            ExportarExel obj = new ExportarExel();
            obj.exportarExcel(Jtabla, tablaInsertar());
        } catch (IOException ex) {
            Logger.getLogger(ExportarExel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_ExportarExelMouseClicked

    private void btn_ExportarPdfMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ExportarPdfMouseEntered
        panel_pdf.setBackground(new Color(0,193,53));
    }//GEN-LAST:event_btn_ExportarPdfMouseEntered

    private void btn_ExportarExelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ExportarExelMouseEntered
        panel_exel.setBackground(new Color(0,193,53));
    }//GEN-LAST:event_btn_ExportarExelMouseEntered

    private void btn_ExportarPdfMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ExportarPdfMouseExited
        panel_pdf.setBackground(new Color(0,155,43));
    }//GEN-LAST:event_btn_ExportarPdfMouseExited

    private void btn_ExportarExelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ExportarExelMouseExited
        panel_exel.setBackground(new Color(0,155,43));
    }//GEN-LAST:event_btn_ExportarExelMouseExited

    private void btn_ExportarPdfMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ExportarPdfMouseClicked
       
        try {
            Connection con = Conexion.getConection();
//            String ruta = (String) JRLoader.loadObject(getClass().getResource("/Reporte/ReporteEmpleado.jrxml"));
//            System.out.println("ruta = " + ruta);
            JasperDesign jdesign = JRXmlLoader.load("C:\\Users\\JORGE\\Documents\\JAVA(NetBeans)\\PROYECTO_DE_ASISTENCIA_JDBC_PostgresSQL\\src\\Reporte\\ReporteEmpleado.jrxml");
            
            String consulta = "SELECT empleado.id_empleado, empleado.ci, profesor.aula,\n"
            + "persona.nombre, persona.apellidos, empleado.celular, empleado.genero, empleado.cargo\n"
            + "FROM (persona INNER JOIN empleado ON persona.ci=empleado.ci)\n"
            + "	INNER JOIN profesor ON profesor.ci=empleado.ci\n"
            + "ORDER BY persona.nombre;";
            
            JRDesignQuery query = new JRDesignQuery();
            query.setText(consulta);
            
            JasperReport report = JasperCompileManager.compileReport(jdesign);
            JasperPrint jprint = JasperFillManager.fillReport(report, null, con);
            
            JasperViewer.viewReport(jprint);
            
        } catch (JRException ex) {
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_ExportarPdfMouseClicked

    public void generarReporte() {
        Document doc = new Document();
        String path = "";
        try {

            JFileChooser j = new JFileChooser();
            j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int x = j.showSaveDialog(this);
            if (x == JFileChooser.APPROVE_OPTION) {
                path = j.getSelectedFile().getPath();
            }

            PdfWriter.getInstance(doc, new FileOutputStream(path + ".pdf"));

            com.itextpdf.text.Image header = com.itextpdf.text.Image.getInstance("src/com/images/user.png");
            header.scaleToFit(650, 1000);
            header.setAlignment(Chunk.ALIGN_CENTER);

            Paragraph parrafo = new Paragraph();
            //parrafo.setAlignment(Paragraph.ALIGN_CENTER);
            parrafo.add("Información del cliente \n \n");
            parrafo.setFont(FontFactory.getFont("Arial", 14, com.itextpdf.text.Font.BOLD, BaseColor.RED));

            PdfPTable tbl = new PdfPTable(8);

            doc.open();
            doc.add(header);
            doc.add(parrafo);

            tbl.addCell("ID");
            tbl.addCell("CI");
            tbl.addCell("NOMBRE");
            tbl.addCell("APELLIDOS");
            tbl.addCell("CELULAR");
            tbl.addCell("AULA");
            tbl.addCell("GENERO");
            tbl.addCell("CARGO");

            for (int i = 0; i < Jtabla.getRowCount(); i++) {
                String id = Jtabla.getValueAt(i, 0).toString();
                String ci = Jtabla.getValueAt(i, 1).toString();
                String aula = Jtabla.getValueAt(i, 2).toString();
                String nombre = Jtabla.getValueAt(i, 3).toString();
                String apellido = Jtabla.getValueAt(i, 4).toString();
                String celular = Jtabla.getValueAt(i, 5).toString();
                String genero = Jtabla.getValueAt(i, 6).toString();
                String cargo = Jtabla.getValueAt(i, 7).toString();

                tbl.addCell(id);
                tbl.addCell(ci);
                tbl.addCell(nombre);
                tbl.addCell(apellido);
                tbl.addCell(celular);
                tbl.addCell(aula);
                tbl.addCell(genero);
                tbl.addCell(cargo);

            }
            doc.add(tbl);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException | IOException ex) {
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        }

        doc.close();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Jtabla;
    private javax.swing.JPanel btnRegistrar;
    private javax.swing.JLabel btn_ExportarExel;
    private javax.swing.JLabel btn_ExportarPdf;
    public static javax.swing.JComboBox<String> cbxTablaExportar;
    private javax.swing.JButton cerrar1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel loginBtnTxt;
    private javax.swing.JLabel logoname;
    private org.edisoncor.gui.panel.Panel panel2;
    private javax.swing.JPanel panel_exel;
    private javax.swing.JPanel panel_pdf;
    public static javax.swing.JLabel txtNivelP;
    // End of variables declaration//GEN-END:variables
}
