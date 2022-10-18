package proyectoasistenciapersonal;

import java.awt.Color;

public class Menu extends javax.swing.JFrame {

    private boolean minimiza;
    int xMouse, yMouse;
    
    public Menu() {
        initComponents();
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel3 = new org.edisoncor.gui.panel.Panel();
        jLabel4 = new javax.swing.JLabel();
        cerrar1 = new javax.swing.JButton();
        txtSalir = new javax.swing.JPanel();
        btnSalir = new javax.swing.JLabel();
        txtMinimizar = new javax.swing.JPanel();
        btnMinimizar = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnReportes = new javax.swing.JButton();
        btnRegistroAsistencia = new javax.swing.JButton();
        btnRegistroDocente = new javax.swing.JButton();
        btnRegistroAlumnos = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel3.setBackground(new java.awt.Color(153, 153, 255));
        panel3.setForeground(new java.awt.Color(255, 255, 255));
        panel3.setColorPrimario(new java.awt.Color(0, 134, 190));
        panel3.setColorSecundario(new java.awt.Color(153, 255, 204));
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

        txtSalir.setBackground(new java.awt.Color(0, 134, 190));

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
            .addGroup(txtSalirLayout.createSequentialGroup()
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 24, Short.MAX_VALUE)
                .addContainerGap())
        );

        panel3.add(txtSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 0, 30, 30));

        txtMinimizar.setBackground(new java.awt.Color(0, 134, 190));

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

        panel3.add(txtMinimizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 0, 30, -1));

        jLabel11.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLabel11MouseDragged(evt);
            }
        });
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel11MousePressed(evt);
            }
        });
        panel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 830, 30));

        jLabel1.setFont(new java.awt.Font("Arial Black", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("REGISTRO Y CONTROL DE ASISTENCIA ");
        panel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 40, -1, -1));

        btnReportes.setBackground(new java.awt.Color(62, 183, 195));
        btnReportes.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnReportes.setForeground(new java.awt.Color(51, 51, 51));
        btnReportes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/images/reportes.png"))); // NOI18N
        btnReportes.setText("REPORTES");
        btnReportes.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnReportes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnReportesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnReportesMouseExited(evt);
            }
        });
        btnReportes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportesActionPerformed(evt);
            }
        });
        panel3.add(btnReportes, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 110, 370, 140));

        btnRegistroAsistencia.setBackground(new java.awt.Color(62, 183, 195));
        btnRegistroAsistencia.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnRegistroAsistencia.setForeground(new java.awt.Color(51, 51, 51));
        btnRegistroAsistencia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/images/registroasistencia.png"))); // NOI18N
        btnRegistroAsistencia.setText("REGISTRO ASISTENCIA");
        btnRegistroAsistencia.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnRegistroAsistencia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRegistroAsistenciaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnRegistroAsistenciaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnRegistroAsistenciaMouseExited(evt);
            }
        });
        btnRegistroAsistencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistroAsistenciaActionPerformed(evt);
            }
        });
        panel3.add(btnRegistroAsistencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 380, 150));

        btnRegistroDocente.setBackground(new java.awt.Color(62, 183, 195));
        btnRegistroDocente.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnRegistroDocente.setForeground(new java.awt.Color(51, 51, 51));
        btnRegistroDocente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/images/user.png"))); // NOI18N
        btnRegistroDocente.setText("REGISTRO DE DOCENTES");
        btnRegistroDocente.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnRegistroDocente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRegistroDocenteMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnRegistroDocenteMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnRegistroDocenteMouseExited(evt);
            }
        });
        btnRegistroDocente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistroDocenteActionPerformed(evt);
            }
        });
        panel3.add(btnRegistroDocente, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 380, 140));

        btnRegistroAlumnos.setBackground(new java.awt.Color(62, 183, 195));
        btnRegistroAlumnos.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnRegistroAlumnos.setForeground(new java.awt.Color(51, 51, 51));
        btnRegistroAlumnos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/images/oficina.png"))); // NOI18N
        btnRegistroAlumnos.setText("REGISTRO DE ALUMNOS");
        btnRegistroAlumnos.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnRegistroAlumnos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRegistroAlumnosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnRegistroAlumnosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnRegistroAlumnosMouseExited(evt);
            }
        });
        btnRegistroAlumnos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistroAlumnosActionPerformed(evt);
            }
        });
        panel3.add(btnRegistroAlumnos, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 270, 370, 150));

        getContentPane().add(panel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 830, 440));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistroAlumnosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistroAlumnosActionPerformed
        Registro_Estudiante regAlumno = new Registro_Estudiante();
        regAlumno.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnRegistroAlumnosActionPerformed

    private void btnRegistroDocenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistroDocenteActionPerformed
        Registro_De_Empleados registro = new Registro_De_Empleados();
        registro.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnRegistroDocenteActionPerformed

    private void btnRegistroAsistenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistroAsistenciaActionPerformed
        Registro_Asistencia registro = new Registro_Asistencia();
        registro.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnRegistroAsistenciaActionPerformed

    private void btnReportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportesActionPerformed

    }//GEN-LAST:event_btnReportesActionPerformed

    private void cerrar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cerrar1ActionPerformed

    }//GEN-LAST:event_cerrar1ActionPerformed

    private void btnSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalirMouseClicked
        System.exit(0);
    }//GEN-LAST:event_btnSalirMouseClicked

    private void btnSalirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalirMouseEntered
        txtSalir.setBackground(Color.red);
        btnSalir.setForeground(Color.white);
    }//GEN-LAST:event_btnSalirMouseEntered

    private void btnSalirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalirMouseExited
        txtSalir.setBackground(new Color(0, 134, 190));
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
        txtMinimizar.setBackground(new Color(0,134,190));
        btnMinimizar.setForeground(Color.black);
    }//GEN-LAST:event_btnMinimizarMouseExited

    private void jLabel11MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_jLabel11MouseDragged

    private void jLabel11MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_jLabel11MousePressed

    private void btnRegistroDocenteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegistroDocenteMouseEntered
        btnRegistroDocente.setBackground(new Color(0,134,190));
    }//GEN-LAST:event_btnRegistroDocenteMouseEntered

    private void btnReportesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReportesMouseEntered
        btnReportes.setBackground(new Color(0,134,190));
    }//GEN-LAST:event_btnReportesMouseEntered

    private void btnRegistroAsistenciaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegistroAsistenciaMouseEntered
        btnRegistroAsistencia.setBackground(new Color(0,134,190));
    }//GEN-LAST:event_btnRegistroAsistenciaMouseEntered

    private void btnRegistroAlumnosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegistroAlumnosMouseEntered
        btnRegistroAlumnos.setBackground(new Color(0,134,190));
    }//GEN-LAST:event_btnRegistroAlumnosMouseEntered

    private void btnRegistroDocenteMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegistroDocenteMouseExited
         btnRegistroDocente.setBackground(new Color(62,183,195));
    }//GEN-LAST:event_btnRegistroDocenteMouseExited

    private void btnReportesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReportesMouseExited
        btnReportes.setBackground(new Color(62,183,195));
    }//GEN-LAST:event_btnReportesMouseExited

    private void btnRegistroAsistenciaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegistroAsistenciaMouseExited
        btnRegistroAsistencia.setBackground(new Color(62,183,195));
    }//GEN-LAST:event_btnRegistroAsistenciaMouseExited

    private void btnRegistroAlumnosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegistroAlumnosMouseExited
        btnRegistroAlumnos.setBackground(new Color(62,183,195));
    }//GEN-LAST:event_btnRegistroAlumnosMouseExited

    private void btnRegistroAsistenciaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegistroAsistenciaMouseClicked
        Registro_Asistencia registro = new Registro_Asistencia();
        registro.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnRegistroAsistenciaMouseClicked

    private void btnRegistroAlumnosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegistroAlumnosMouseClicked
        Registro_Estudiante registro = new Registro_Estudiante();
        registro.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnRegistroAlumnosMouseClicked

    private void btnRegistroDocenteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegistroDocenteMouseClicked
        
    }//GEN-LAST:event_btnRegistroDocenteMouseClicked

    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btnMinimizar;
    private javax.swing.JButton btnRegistroAlumnos;
    private javax.swing.JButton btnRegistroAsistencia;
    private javax.swing.JButton btnRegistroDocente;
    private javax.swing.JButton btnReportes;
    private javax.swing.JLabel btnSalir;
    private javax.swing.JButton cerrar1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel4;
    private org.edisoncor.gui.panel.Panel panel3;
    private javax.swing.JPanel txtMinimizar;
    private javax.swing.JPanel txtSalir;
    // End of variables declaration//GEN-END:variables
}
