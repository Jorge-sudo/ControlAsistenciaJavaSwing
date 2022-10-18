    
package main;

import com.login.Login_Usuario1;
import com.sun.awt.AWTUtilities;
import jPanel.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Menu1 extends javax.swing.JFrame {

    private boolean minimiza;
    int xMouse, yMouse;
    
    public Menu1() {
        initComponents();
        this.setLocationRelativeTo(this);
        Shape forma = new RoundRectangle2D.Double(0, 0, this.getBounds().width,  this.getBounds().height, 15, 15);
        AWTUtilities.setWindowShape(this, forma);
        
         //Abrir sección
        Principal p1 = new Principal();
        p1.setSize(860, 570);
        p1.setLocation(0,0);

        content.removeAll();
        content.add(p1, BorderLayout.CENTER);
        content.revalidate();
        content.repaint();
    }
    
    void setColor(JPanel panel){
        panel.setBackground(new Color(21,101,192));
    }
    void resetColor(JPanel panel){
        panel.setBackground(new Color(18,90,173));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Background = new javax.swing.JPanel();
        Menu = new javax.swing.JPanel();
        btnPrincipal = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnRegistroEstudiante = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnRegistroAsistencia = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btnReportes = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JSeparator();
        btnRegistroUsuario = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        app_name = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator8 = new javax.swing.JSeparator();
        btnEstudiantes = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jSeparator11 = new javax.swing.JSeparator();
        btnUsuarios = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jSeparator17 = new javax.swing.JSeparator();
        jSeparator18 = new javax.swing.JSeparator();
        jSeparator19 = new javax.swing.JSeparator();
        btnTutores = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jSeparator12 = new javax.swing.JSeparator();
        jSeparator10 = new javax.swing.JSeparator();
        content = new javax.swing.JPanel();
        Header = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtSalir = new javax.swing.JPanel();
        btnSalir = new javax.swing.JLabel();
        txtMinimizar = new javax.swing.JPanel();
        btnMinimizar = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        Title = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Background.setBackground(new java.awt.Color(255, 255, 255));
        Background.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Menu.setBackground(new java.awt.Color(0, 102, 20));
        Menu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnPrincipal.setBackground(new java.awt.Color(0, 153, 41));
        btnPrincipal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 41), 2));
        btnPrincipal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPrincipal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnPrincipalMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnPrincipalMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnPrincipalMousePressed(evt);
            }
        });
        btnPrincipal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/images/home-outline.png"))); // NOI18N
        btnPrincipal.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 30, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Principal");
        btnPrincipal.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 30));

        Menu.add(btnPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 220, 50));

        btnRegistroEstudiante.setBackground(new java.awt.Color(0, 153, 41));
        btnRegistroEstudiante.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 41), 2));
        btnRegistroEstudiante.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRegistroEstudiante.setPreferredSize(new java.awt.Dimension(270, 51));
        btnRegistroEstudiante.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnRegistroEstudianteMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnRegistroEstudianteMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnRegistroEstudianteMousePressed(evt);
            }
        });
        btnRegistroEstudiante.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/images/account-multiple.png"))); // NOI18N
        btnRegistroEstudiante.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 30, 30));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Registro de Estudiantes");
        btnRegistroEstudiante.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 170, 30));

        Menu.add(btnRegistroEstudiante, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 220, -1));

        btnRegistroAsistencia.setBackground(new java.awt.Color(0, 153, 41));
        btnRegistroAsistencia.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 41), 2));
        btnRegistroAsistencia.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRegistroAsistencia.setPreferredSize(new java.awt.Dimension(270, 51));
        btnRegistroAsistencia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnRegistroAsistenciaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnRegistroAsistenciaMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnRegistroAsistenciaMousePressed(evt);
            }
        });
        btnRegistroAsistencia.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/images/calendar-multiple-check.png"))); // NOI18N
        btnRegistroAsistencia.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 30, 30));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Registro de Asistencia");
        btnRegistroAsistencia.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 160, 30));

        Menu.add(btnRegistroAsistencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, 220, -1));

        btnReportes.setBackground(new java.awt.Color(0, 153, 41));
        btnReportes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 41), 2));
        btnReportes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReportes.setPreferredSize(new java.awt.Dimension(270, 51));
        btnReportes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnReportesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnReportesMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnReportesMousePressed(evt);
            }
        });
        btnReportes.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/images/book-open-page-variant.png"))); // NOI18N
        btnReportes.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 30, 30));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Reportes");
        btnReportes.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 30));

        jSeparator9.setBackground(new java.awt.Color(255, 255, 255));
        jSeparator9.setForeground(new java.awt.Color(255, 255, 255));
        btnReportes.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 470, 250, 10));

        Menu.add(btnReportes, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 590, 220, -1));

        btnRegistroUsuario.setBackground(new java.awt.Color(0, 153, 41));
        btnRegistroUsuario.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 41), 2));
        btnRegistroUsuario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRegistroUsuario.setPreferredSize(new java.awt.Dimension(270, 51));
        btnRegistroUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnRegistroUsuarioMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnRegistroUsuarioMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnRegistroUsuarioMousePressed(evt);
            }
        });
        btnRegistroUsuario.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/images/account-multiple.png"))); // NOI18N
        btnRegistroUsuario.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 30, 30));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Registro de Usuarios");
        btnRegistroUsuario.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 160, 30));

        Menu.add(btnRegistroUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 220, -1));

        app_name.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        app_name.setForeground(new java.awt.Color(255, 255, 255));
        app_name.setText("Control De Asistencia");
        Menu.add(app_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, 40));

        jSeparator4.setBackground(new java.awt.Color(255, 255, 255));
        jSeparator4.setForeground(new java.awt.Color(255, 255, 255));
        Menu.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 250, 10));

        jSeparator5.setBackground(new java.awt.Color(255, 255, 255));
        jSeparator5.setForeground(new java.awt.Color(255, 255, 255));
        Menu.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 250, 10));

        jSeparator6.setBackground(new java.awt.Color(255, 255, 255));
        jSeparator6.setForeground(new java.awt.Color(255, 255, 255));
        Menu.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, 250, 10));

        jSeparator7.setBackground(new java.awt.Color(255, 255, 255));
        jSeparator7.setForeground(new java.awt.Color(255, 255, 255));
        Menu.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 440, 250, 10));

        jSeparator8.setBackground(new java.awt.Color(255, 255, 255));
        jSeparator8.setForeground(new java.awt.Color(255, 255, 255));
        Menu.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 250, 10));

        btnEstudiantes.setBackground(new java.awt.Color(0, 153, 41));
        btnEstudiantes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 41), 2));
        btnEstudiantes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEstudiantes.setPreferredSize(new java.awt.Dimension(270, 51));
        btnEstudiantes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEstudiantesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEstudiantesMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnEstudiantesMousePressed(evt);
            }
        });
        btnEstudiantes.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/images/book-open-page-variant.png"))); // NOI18N
        btnEstudiantes.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 30, 30));

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Estudiantes");
        btnEstudiantes.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 30));

        jSeparator11.setBackground(new java.awt.Color(255, 255, 255));
        jSeparator11.setForeground(new java.awt.Color(255, 255, 255));
        btnEstudiantes.add(jSeparator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 470, 250, 10));

        Menu.add(btnEstudiantes, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 450, 220, -1));

        btnUsuarios.setBackground(new java.awt.Color(0, 153, 41));
        btnUsuarios.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 41), 2));
        btnUsuarios.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuarios.setPreferredSize(new java.awt.Dimension(270, 51));
        btnUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnUsuariosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnUsuariosMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnUsuariosMousePressed(evt);
            }
        });
        btnUsuarios.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/images/book-open-page-variant.png"))); // NOI18N
        btnUsuarios.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 30, 30));

        jLabel38.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setText("Empleados");
        btnUsuarios.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 30));

        Menu.add(btnUsuarios, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 380, 220, -1));

        jSeparator17.setBackground(new java.awt.Color(255, 255, 255));
        jSeparator17.setForeground(new java.awt.Color(255, 255, 255));
        Menu.add(jSeparator17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 510, 250, 10));

        jSeparator18.setBackground(new java.awt.Color(255, 255, 255));
        jSeparator18.setForeground(new java.awt.Color(255, 255, 255));
        Menu.add(jSeparator18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 650, 250, 10));

        jSeparator19.setBackground(new java.awt.Color(255, 255, 255));
        jSeparator19.setForeground(new java.awt.Color(255, 255, 255));
        Menu.add(jSeparator19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 580, 250, 20));

        btnTutores.setBackground(new java.awt.Color(0, 153, 41));
        btnTutores.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 41), 2));
        btnTutores.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTutores.setPreferredSize(new java.awt.Dimension(270, 51));
        btnTutores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnTutoresMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnTutoresMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnTutoresMousePressed(evt);
            }
        });
        btnTutores.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/images/book-open-page-variant.png"))); // NOI18N
        btnTutores.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 30, 30));

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Tutores");
        btnTutores.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 30));

        jSeparator12.setBackground(new java.awt.Color(255, 255, 255));
        jSeparator12.setForeground(new java.awt.Color(255, 255, 255));
        btnTutores.add(jSeparator12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 470, 250, 10));

        Menu.add(btnTutores, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 520, 220, -1));

        jSeparator10.setBackground(new java.awt.Color(255, 255, 255));
        jSeparator10.setForeground(new java.awt.Color(255, 255, 255));
        Menu.add(jSeparator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 250, 10));

        Background.add(Menu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 270, 680));

        content.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout contentLayout = new javax.swing.GroupLayout(content);
        content.setLayout(contentLayout);
        contentLayout.setHorizontalGroup(
            contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 860, Short.MAX_VALUE)
        );
        contentLayout.setVerticalGroup(
            contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 570, Short.MAX_VALUE)
        );

        Background.add(content, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 110, 860, 570));

        Header.setBackground(new java.awt.Color(0, 153, 41));
        Header.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Administración de Estudiantes/Control De Asistencia/Registros De Estudiantes");
        Header.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, 30));

        txtSalir.setBackground(new java.awt.Color(0, 153, 41));

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

        Header.add(txtSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 0, -1, -1));

        txtMinimizar.setBackground(new java.awt.Color(0, 153, 41));

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

        Header.add(txtMinimizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 0, 30, -1));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/images/logout_9089422222.jpg"))); // NOI18N
        jLabel6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });
        Header.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 0, 30, 30));

        Background.add(Header, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 0, -1, 110));

        Title.setBackground(new java.awt.Color(255, 255, 255));
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

        javax.swing.GroupLayout TitleLayout = new javax.swing.GroupLayout(Title);
        Title.setLayout(TitleLayout);
        TitleLayout.setHorizontalGroup(
            TitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1060, Short.MAX_VALUE)
        );
        TitleLayout.setVerticalGroup(
            TitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        Background.add(Title, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1060, 40));

        getContentPane().add(Background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPrincipalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrincipalMouseEntered
        btnPrincipal.setBackground(new Color(0, 191,53));
    }//GEN-LAST:event_btnPrincipalMouseEntered

    private void btnPrincipalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrincipalMouseExited
        btnPrincipal.setBackground(new Color(0,153,41));
    }//GEN-LAST:event_btnPrincipalMouseExited

    private void btnPrincipalMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrincipalMousePressed
         //Abrir sección
        Principal p1 = new Principal();
        p1.setSize(860, 570);
        p1.setLocation(0,0);

        content.removeAll();
        content.add(p1, BorderLayout.CENTER);
        content.revalidate();
        content.repaint();
        
    }//GEN-LAST:event_btnPrincipalMousePressed

    private void btnRegistroEstudianteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegistroEstudianteMouseEntered
        btnRegistroEstudiante.setBackground(new Color(0, 191,53));
    }//GEN-LAST:event_btnRegistroEstudianteMouseEntered

    private void btnRegistroEstudianteMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegistroEstudianteMouseExited
        btnRegistroEstudiante.setBackground(new Color(0,153,41));
    }//GEN-LAST:event_btnRegistroEstudianteMouseExited

    private void btnRegistroEstudianteMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegistroEstudianteMousePressed
      
        // Abrir sección
        Registro_Estudiante p1 = new Registro_Estudiante();
        p1.setSize(860, 570);
        p1.setLocation(0,0);

        content.removeAll();
        content.add(p1, BorderLayout.CENTER);
        content.revalidate();
        content.repaint();
    }//GEN-LAST:event_btnRegistroEstudianteMousePressed

    private void btnRegistroAsistenciaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegistroAsistenciaMouseEntered
        btnRegistroAsistencia.setBackground(new Color(0, 191,53));
    }//GEN-LAST:event_btnRegistroAsistenciaMouseEntered

    private void btnRegistroAsistenciaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegistroAsistenciaMouseExited
        btnRegistroAsistencia.setBackground(new Color(0,153,41));
    }//GEN-LAST:event_btnRegistroAsistenciaMouseExited

    private void btnRegistroAsistenciaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegistroAsistenciaMousePressed
        
        // Abrir sección
        Registro_Asistencia p1 = new Registro_Asistencia();
        p1.setSize(860, 570);
        p1.setLocation(0,0);

        content.removeAll();
        content.add(p1, BorderLayout.CENTER);
        content.revalidate();
        content.repaint();
    }//GEN-LAST:event_btnRegistroAsistenciaMousePressed

    private void btnReportesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReportesMouseEntered
        btnReportes.setBackground(new Color(0, 191,53));
    }//GEN-LAST:event_btnReportesMouseEntered

    private void btnReportesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReportesMouseExited
        btnReportes.setBackground(new Color(0,153,41));
    }//GEN-LAST:event_btnReportesMouseExited

    private void btnReportesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReportesMousePressed
        
        // Abrir sección
        Reportes p1 = new Reportes();
        p1.setSize(860, 570);
        p1.setLocation(0,0);

        content.removeAll();
        content.add(p1, BorderLayout.CENTER);
        content.revalidate();
        content.repaint();
    }//GEN-LAST:event_btnReportesMousePressed

    private void btnRegistroUsuarioMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegistroUsuarioMouseEntered
        btnRegistroUsuario.setBackground(new Color(0, 191,53));
    }//GEN-LAST:event_btnRegistroUsuarioMouseEntered

    private void btnRegistroUsuarioMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegistroUsuarioMouseExited
        btnRegistroUsuario.setBackground(new Color(0,153,41));
    }//GEN-LAST:event_btnRegistroUsuarioMouseExited

    private void btnRegistroUsuarioMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegistroUsuarioMousePressed
        
         //Abrir sección
        Registro_Empleado p1 = new Registro_Empleado();
        p1.setSize(860, 570);
        p1.setLocation(0,0);

        content.removeAll();
        content.add(p1, BorderLayout.CENTER);
        content.revalidate();
        content.repaint();
    }//GEN-LAST:event_btnRegistroUsuarioMousePressed

    private void TitleMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TitleMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x-xMouse,y-yMouse);
    }//GEN-LAST:event_TitleMouseDragged

    private void TitleMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TitleMousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_TitleMousePressed

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
        txtSalir.setBackground(new Color(0,153,41));
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
        txtMinimizar.setBackground(new Color(0,153,41));
        btnMinimizar.setForeground(Color.black);
    }//GEN-LAST:event_btnMinimizarMouseExited

    private void btnEstudiantesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEstudiantesMouseEntered
        btnEstudiantes.setBackground(new Color(0, 191,53));
    }//GEN-LAST:event_btnEstudiantesMouseEntered

    private void btnEstudiantesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEstudiantesMouseExited
        btnEstudiantes.setBackground(new Color(0,153,41));
    }//GEN-LAST:event_btnEstudiantesMouseExited

    private void btnEstudiantesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEstudiantesMousePressed
         //Abrir sección
        Tabla_Estudiante p1 = new Tabla_Estudiante();
        p1.setSize(860, 570);
        p1.setLocation(0,0);

        content.removeAll();
        content.add(p1, BorderLayout.CENTER);
        content.revalidate();
        content.repaint();
    }//GEN-LAST:event_btnEstudiantesMousePressed

    private void btnUsuariosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUsuariosMouseEntered
        btnUsuarios.setBackground(new Color(0, 191,53));
    }//GEN-LAST:event_btnUsuariosMouseEntered

    private void btnUsuariosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUsuariosMouseExited
        btnUsuarios.setBackground(new Color(0,153,41));
    }//GEN-LAST:event_btnUsuariosMouseExited

    private void btnUsuariosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUsuariosMousePressed
         //Abrir sección
        Tabla_Empleado p1 = new Tabla_Empleado();
        p1.setSize(860, 570);
        p1.setLocation(0,0);

        content.removeAll();
        content.add(p1, BorderLayout.CENTER);
        content.revalidate();
        content.repaint();
    }//GEN-LAST:event_btnUsuariosMousePressed

    private void btnTutoresMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTutoresMouseEntered
        btnTutores.setBackground(new Color(0, 191,53));
    }//GEN-LAST:event_btnTutoresMouseEntered

    private void btnTutoresMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTutoresMouseExited
        btnTutores.setBackground(new Color(0,153,41));
    }//GEN-LAST:event_btnTutoresMouseExited

    private void btnTutoresMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTutoresMousePressed
         //Abrir sección
        Tabla_Tutor p1 = new Tabla_Tutor();
        p1.setSize(860, 570);
        p1.setLocation(0,0);

        content.removeAll();
        content.add(p1, BorderLayout.CENTER);
        content.revalidate();
        content.repaint();
    }//GEN-LAST:event_btnTutoresMousePressed

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        int seleccion = JOptionPane.showConfirmDialog(null, "¿Seguro de que quiere Cerrar Session?",
            "Salir",
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (seleccion == 0) {
            Login_Usuario1 login = new Login_Usuario1();
            login.setVisible(true);
            this.dispose();
        }

    }//GEN-LAST:event_jLabel6MouseClicked

  
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Menu1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Background;
    private javax.swing.JPanel Header;
    private javax.swing.JPanel Menu;
    private javax.swing.JPanel Title;
    private javax.swing.JLabel app_name;
    private javax.swing.JPanel btnEstudiantes;
    private javax.swing.JLabel btnMinimizar;
    private javax.swing.JPanel btnPrincipal;
    private javax.swing.JPanel btnRegistroAsistencia;
    private javax.swing.JPanel btnRegistroEstudiante;
    private javax.swing.JPanel btnRegistroUsuario;
    private javax.swing.JPanel btnReportes;
    private javax.swing.JLabel btnSalir;
    private javax.swing.JPanel btnTutores;
    private javax.swing.JPanel btnUsuarios;
    public static javax.swing.JPanel content;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator18;
    private javax.swing.JSeparator jSeparator19;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JPanel txtMinimizar;
    private javax.swing.JPanel txtSalir;
    // End of variables declaration//GEN-END:variables
}
