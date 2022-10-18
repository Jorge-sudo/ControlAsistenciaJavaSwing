package proyectoasistenciapersonal;

import Estudiante_TablaDeSQL.Conexion;
import Estudiante_TablaDeSQL.Estudiante;
import Estudiante_TablaDeSQL.EstudianteDAO_JDBC;
import java.awt.*;
import static java.awt.Color.white;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.logging.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Registro_Estudiante extends javax.swing.JFrame {

    Empleado emp; //declara un objeto emp de la clase Empleado
    DefaultTableModel dtm = new DefaultTableModel();
    int clickTabla;

    int xMouse, yMouse;
    private boolean minimiza;

    public Registro_Estudiante() {
        initComponents();

        this.setLocationRelativeTo(this);

        setBackground(new Color(0, 0, 0, 0));

        tablaEmpleados.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tablaEmpleados.getTableHeader().setOpaque(false);
        tablaEmpleados.getTableHeader().setBackground(new Color(32, 136, 203));
        tablaEmpleados.getTableHeader().setForeground(new Color(255, 255, 255));
        tablaEmpleados.setRowHeight(25);

//        try {
//            mostrarEmpleados();
//        } catch (SQLException ex) {
//            mensaje(" Error al conectar con la base de datos");
//        }

    }

    public void mostrarEmpleados() throws SQLException {
        EstudianteDAO_JDBC logica = new EstudianteDAO_JDBC();

        DefaultTableModel modelo;
        modelo = logica.mostrarClientes();
        tablaEmpleados.setModel(modelo);
    }

    void mensaje(String msje) {
        JOptionPane.showMessageDialog(null, msje);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        panel3 = new org.edisoncor.gui.panel.Panel();
        jLabel4 = new javax.swing.JLabel();
        cerrar1 = new javax.swing.JButton();
        txtSalir = new javax.swing.JPanel();
        btnSalir = new javax.swing.JLabel();
        txtMinimizar = new javax.swing.JPanel();
        btnMinimizar = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        userLabel = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        txtApellidos = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        passLabel = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JPasswordField();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        cmbCargo = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        brMasculino = new javax.swing.JRadioButton();
        brFemenino = new javax.swing.JRadioButton();
        loginBtn = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JLabel();
        ActualizarBtnColor = new javax.swing.JPanel();
        btnActualizar = new javax.swing.JLabel();
        RegresarBtnColor = new javax.swing.JPanel();
        btnRegresar = new javax.swing.JLabel();
        eliminatBtnColor1 = new javax.swing.JPanel();
        btnEliminar1 = new javax.swing.JLabel();
        passLabel1 = new javax.swing.JLabel();
        passLabel2 = new javax.swing.JLabel();
        txtPadre = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        txtMadre = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        txtContacto = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        title = new javax.swing.JLabel();
        favicon = new javax.swing.JLabel();
        logoname = new javax.swing.JLabel();
        DE = new javax.swing.JLabel();
        ASISTENCIA = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaEmpleados = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setUndecorated(true);
        setResizable(false);

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
            .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 30, Short.MAX_VALUE)
        );

        panel3.add(txtSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 0, 30, 30));

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

        panel3.add(txtMinimizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 0, 30, -1));

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
        panel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1020, 30));

        jPanel3.setBackground(new java.awt.Color(199, 241, 233));
        jPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        userLabel.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        userLabel.setForeground(new java.awt.Color(0, 0, 0));
        userLabel.setText("NOMBRE");
        jPanel3.add(userLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 70, -1));

        txtNombre.setBackground(new java.awt.Color(199, 241, 233));
        txtNombre.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtNombre.setForeground(new java.awt.Color(102, 102, 102));
        txtNombre.setText("Ingrese su nombre  ");
        txtNombre.setBorder(null);
        txtNombre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtNombreMousePressed(evt);
            }
        });
        jPanel3.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 20, 220, 30));

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        jPanel3.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 50, 260, 20));

        txtApellidos.setBackground(new java.awt.Color(199, 241, 233));
        txtApellidos.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtApellidos.setForeground(new java.awt.Color(102, 102, 102));
        txtApellidos.setText("Ingrese sus apellidos");
        txtApellidos.setBorder(null);
        txtApellidos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtApellidosMousePressed(evt);
            }
        });
        jPanel3.add(txtApellidos, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 70, 150, 30));

        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));
        jPanel3.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, 200, 20));

        jLabel1.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("APELLIDOS");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 80, -1));

        passLabel.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        passLabel.setForeground(new java.awt.Color(0, 0, 0));
        passLabel.setText("N. MADRE");
        jPanel3.add(passLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 180, 90, -1));

        txtCodigo.setBackground(new java.awt.Color(199, 241, 233));
        txtCodigo.setForeground(new java.awt.Color(102, 102, 102));
        txtCodigo.setText("********");
        txtCodigo.setBorder(null);
        txtCodigo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtCodigoMousePressed(evt);
            }
        });
        txtCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoActionPerformed(evt);
            }
        });
        jPanel3.add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 220, 120, 30));

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        jPanel3.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 250, 110, 20));

        jLabel2.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("COLOR:");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 350, 60, -1));

        cmbCargo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Verde", "Amarillo", "Azul", "Rosado", "Rojo" }));
        cmbCargo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCargoActionPerformed(evt);
            }
        });
        jPanel3.add(cmbCargo, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 350, -1, -1));

        jPanel2.setBackground(new java.awt.Color(199, 241, 233));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("GENERO"));
        jPanel2.setForeground(new java.awt.Color(0, 0, 0));
        jPanel2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        brMasculino.setBackground(new java.awt.Color(199, 241, 233));
        buttonGroup1.add(brMasculino);
        brMasculino.setForeground(new java.awt.Color(0, 0, 0));
        brMasculino.setText("MASCULINO");

        brFemenino.setBackground(new java.awt.Color(199, 241, 233));
        buttonGroup1.add(brFemenino);
        brFemenino.setForeground(new java.awt.Color(0, 0, 0));
        brFemenino.setText("FEMENINO");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(brMasculino)
                    .addComponent(brFemenino))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(brMasculino)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(brFemenino)
                .addContainerGap())
        );

        jPanel3.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 320, -1, -1));

        loginBtn.setBackground(new java.awt.Color(0, 134, 190));

        btnGuardar.setFont(new java.awt.Font("Roboto Black", 1, 14)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnGuardar.setText("GUARDAR");
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
            .addComponent(btnGuardar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        loginBtnLayout.setVerticalGroup(
            loginBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        jPanel3.add(loginBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 430, 100, 40));

        ActualizarBtnColor.setBackground(new java.awt.Color(0, 134, 190));

        btnActualizar.setFont(new java.awt.Font("Roboto Black", 1, 14)); // NOI18N
        btnActualizar.setForeground(new java.awt.Color(255, 255, 255));
        btnActualizar.setText("   ACTUALIZAR");
        btnActualizar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnActualizar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnActualizarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnActualizarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnActualizarMouseExited(evt);
            }
        });

        javax.swing.GroupLayout ActualizarBtnColorLayout = new javax.swing.GroupLayout(ActualizarBtnColor);
        ActualizarBtnColor.setLayout(ActualizarBtnColorLayout);
        ActualizarBtnColorLayout.setHorizontalGroup(
            ActualizarBtnColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnActualizar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
        );
        ActualizarBtnColorLayout.setVerticalGroup(
            ActualizarBtnColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnActualizar, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        jPanel3.add(ActualizarBtnColor, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 430, 110, 40));

        RegresarBtnColor.setBackground(new java.awt.Color(0, 134, 190));

        btnRegresar.setFont(new java.awt.Font("Roboto Black", 1, 14)); // NOI18N
        btnRegresar.setForeground(new java.awt.Color(255, 255, 255));
        btnRegresar.setText("       MENU");
        btnRegresar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnRegresar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRegresarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnRegresarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnRegresarMouseExited(evt);
            }
        });

        javax.swing.GroupLayout RegresarBtnColorLayout = new javax.swing.GroupLayout(RegresarBtnColor);
        RegresarBtnColor.setLayout(RegresarBtnColorLayout);
        RegresarBtnColorLayout.setHorizontalGroup(
            RegresarBtnColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnRegresar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        RegresarBtnColorLayout.setVerticalGroup(
            RegresarBtnColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnRegresar, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        jPanel3.add(RegresarBtnColor, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 490, 100, 40));

        eliminatBtnColor1.setBackground(new java.awt.Color(0, 134, 190));

        btnEliminar1.setFont(new java.awt.Font("Roboto Black", 1, 14)); // NOI18N
        btnEliminar1.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar1.setText("    ELIMINAR");
        btnEliminar1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnEliminar1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminar1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEliminar1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEliminar1MouseExited(evt);
            }
        });

        javax.swing.GroupLayout eliminatBtnColor1Layout = new javax.swing.GroupLayout(eliminatBtnColor1);
        eliminatBtnColor1.setLayout(eliminatBtnColor1Layout);
        eliminatBtnColor1Layout.setHorizontalGroup(
            eliminatBtnColor1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnEliminar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        eliminatBtnColor1Layout.setVerticalGroup(
            eliminatBtnColor1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnEliminar1, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        jPanel3.add(eliminatBtnColor1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 490, 100, 40));

        passLabel1.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        passLabel1.setForeground(new java.awt.Color(0, 0, 0));
        passLabel1.setText("CODIGO");
        jPanel3.add(passLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 230, 80, -1));

        passLabel2.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        passLabel2.setForeground(new java.awt.Color(0, 0, 0));
        passLabel2.setText("N. PADRE");
        jPanel3.add(passLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, 90, -1));

        txtPadre.setBackground(new java.awt.Color(199, 241, 233));
        txtPadre.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtPadre.setForeground(new java.awt.Color(102, 102, 102));
        txtPadre.setText("Ingrese nombre y apellido");
        txtPadre.setBorder(null);
        txtPadre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtPadreMousePressed(evt);
            }
        });
        jPanel3.add(txtPadre, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 120, 190, 30));

        jSeparator4.setForeground(new java.awt.Color(0, 0, 0));
        jPanel3.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 150, 200, 20));

        txtMadre.setBackground(new java.awt.Color(199, 241, 233));
        txtMadre.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtMadre.setForeground(new java.awt.Color(102, 102, 102));
        txtMadre.setText("Ingrese nombre y apellido");
        txtMadre.setBorder(null);
        txtMadre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtMadreMousePressed(evt);
            }
        });
        jPanel3.add(txtMadre, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 170, 180, 30));

        jSeparator5.setForeground(new java.awt.Color(0, 0, 0));
        jPanel3.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 200, 200, 20));

        txtContacto.setBackground(new java.awt.Color(199, 241, 233));
        txtContacto.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtContacto.setForeground(new java.awt.Color(102, 102, 102));
        txtContacto.setText("Ingrese el numero del padre");
        txtContacto.setBorder(null);
        txtContacto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtContactoMousePressed(evt);
            }
        });
        jPanel3.add(txtContacto, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 270, 170, 30));

        jLabel3.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Nro.CONTACTO");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 280, 120, -1));

        jSeparator6.setForeground(new java.awt.Color(0, 0, 0));
        jPanel3.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 300, 200, 20));

        panel3.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, 430, 560));

        title.setFont(new java.awt.Font("Roboto Black", 1, 24)); // NOI18N
        title.setForeground(new java.awt.Color(255, 255, 255));
        title.setText("REGISTRE  SUS  DATOS");
        panel3.add(title, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, -1, -1));

        favicon.setFont(new java.awt.Font("Roboto Black", 1, 24)); // NOI18N
        favicon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/images/favicon.png"))); // NOI18N
        panel3.add(favicon, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        logoname.setBackground(new java.awt.Color(255, 255, 255));
        logoname.setFont(new java.awt.Font("Roboto Medium", 1, 36)); // NOI18N
        logoname.setForeground(new java.awt.Color(255, 255, 255));
        logoname.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logoname.setText("REGISTRO");
        panel3.add(logoname, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 40, 210, 40));

        DE.setFont(new java.awt.Font("Roboto", 1, 36)); // NOI18N
        DE.setForeground(new java.awt.Color(255, 255, 255));
        DE.setText("DE");
        panel3.add(DE, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 40, -1, 40));

        ASISTENCIA.setFont(new java.awt.Font("Roboto", 1, 36)); // NOI18N
        ASISTENCIA.setForeground(new java.awt.Color(255, 255, 255));
        ASISTENCIA.setText("ALUMNOS");
        panel3.add(ASISTENCIA, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 90, -1, -1));

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
        tablaEmpleados.setFocusable(false);
        tablaEmpleados.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tablaEmpleados.setRowHeight(25);
        tablaEmpleados.setSelectionBackground(new java.awt.Color(128, 200, 191));
        tablaEmpleados.setShowVerticalLines(false);
        tablaEmpleados.getTableHeader().setReorderingAllowed(false);
        tablaEmpleados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaEmpleadosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaEmpleados);

        panel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 170, 500, 470));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel3, javax.swing.GroupLayout.PREFERRED_SIZE, 1020, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel3, javax.swing.GroupLayout.PREFERRED_SIZE, 687, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tablaEmpleadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaEmpleadosMouseClicked

    }//GEN-LAST:event_tablaEmpleadosMouseClicked

    private void btnEliminar1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminar1MouseExited
        eliminatBtnColor1.setBackground(new Color(0, 134, 190));
    }//GEN-LAST:event_btnEliminar1MouseExited

    private void btnEliminar1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminar1MouseEntered
        eliminatBtnColor1.setBackground(new Color(1, 96, 126));
    }//GEN-LAST:event_btnEliminar1MouseEntered

    private void btnEliminar1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminar1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminar1MouseClicked

    private void btnRegresarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegresarMouseExited
        RegresarBtnColor.setBackground(new Color(0, 134, 190));
    }//GEN-LAST:event_btnRegresarMouseExited

    private void btnRegresarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegresarMouseEntered
        RegresarBtnColor.setBackground(new Color(1, 96, 126));
    }//GEN-LAST:event_btnRegresarMouseEntered

    private void btnRegresarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegresarMouseClicked
        Menu menu = new Menu();
        menu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnRegresarMouseClicked

    private void btnActualizarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnActualizarMouseExited
        ActualizarBtnColor.setBackground(new Color(0, 134, 190));
    }//GEN-LAST:event_btnActualizarMouseExited

    private void btnActualizarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnActualizarMouseEntered
        ActualizarBtnColor.setBackground(new Color(1, 96, 126));
    }//GEN-LAST:event_btnActualizarMouseEntered

    private void btnActualizarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnActualizarMouseClicked
        limpiar();
    }//GEN-LAST:event_btnActualizarMouseClicked

    private void btnGuardarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseExited
        loginBtn.setBackground(new Color(0, 134, 190));
    }//GEN-LAST:event_btnGuardarMouseExited

    private void btnGuardarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseEntered
        loginBtn.setBackground(new Color(1, 96, 126));
    }//GEN-LAST:event_btnGuardarMouseEntered

    private void btnGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseClicked

        try {
            registrarEmpleado();
            mostrarEmpleados();
        } catch (SQLException ex) {
            mensaje(" Error al conectar con la base de datos");
        }

        //javax.swing.JOptionPane.showMessageDialog(this, "Intento de login con los datos:\nUsuario: " + txtNombre.getText() + "\nContraseña: " + String.valueOf(txtCodigo.getPassword()), "LOGIN", javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnGuardarMouseClicked

    private void cmbCargoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCargoActionPerformed

    }//GEN-LAST:event_cmbCargoActionPerformed

    private void txtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoActionPerformed

    }//GEN-LAST:event_txtCodigoActionPerformed

    private void txtCodigoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCodigoMousePressed
        if (String.valueOf(txtCodigo.getPassword()).equals("********")) {
            txtCodigo.setText("");
            txtCodigo.setForeground(Color.black);
        }
        if (txtNombre.getText().isEmpty()) {
            txtNombre.setText("Ingrese su nombre  ");
            txtNombre.setForeground(Color.gray);
        }
        if (txtApellidos.getText().isEmpty()) {
            txtApellidos.setText("Ingrese sus apellidos");
            txtApellidos.setForeground(Color.gray);
        }
        if (txtMadre.getText().isEmpty()) {
            txtMadre.setText("Ingrese nombre y apellido");
            txtMadre.setForeground(Color.gray);
        }
        if (txtPadre.getText().isEmpty()) {
            txtPadre.setText("Ingrese nombre y apellido");
            txtPadre.setForeground(Color.gray);
        }
        if (txtContacto.getText().isEmpty()) {
            txtContacto.setText("Ingrese el numero del padre");
            txtContacto.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txtCodigoMousePressed

    private void txtApellidosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtApellidosMousePressed
        if (txtApellidos.getText().equals("Ingrese sus apellidos")) {
            txtApellidos.setText("");
            txtApellidos.setForeground(Color.black);
        }
        if (String.valueOf(txtCodigo.getPassword()).isEmpty()) {
            txtCodigo.setText("********");
            txtCodigo.setForeground(Color.gray);
        }
        if (txtNombre.getText().isEmpty()) {
            txtNombre.setText("Ingrese su nombre  ");
            txtNombre.setForeground(Color.gray);
        }
        if (txtMadre.getText().isEmpty()) {
            txtMadre.setText("Ingrese nombre y apellido");
            txtMadre.setForeground(Color.gray);
        }
        if (txtPadre.getText().isEmpty()) {
            txtPadre.setText("Ingrese nombre y apellido");
            txtPadre.setForeground(Color.gray);
        }
        if (txtContacto.getText().isEmpty()) {
            txtContacto.setText("Ingrese el numero del padre");
            txtContacto.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txtApellidosMousePressed

    private void txtNombreMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNombreMousePressed
        if (txtNombre.getText().equals("Ingrese su nombre  ")) {
            txtNombre.setText("");
            txtNombre.setForeground(Color.black);
        }
        if (String.valueOf(txtCodigo.getPassword()).isEmpty()) {
            txtCodigo.setText("********");
            txtCodigo.setForeground(Color.gray);
        }
        if (txtApellidos.getText().isEmpty()) {
            txtApellidos.setText("Ingrese sus apellidos");
            txtApellidos.setForeground(Color.gray);
        }
        if (txtPadre.getText().isEmpty()) {
            txtPadre.setText("Ingrese nombre y apellido");
            txtPadre.setForeground(Color.gray);
        }
        if (txtMadre.getText().isEmpty()) {
            txtMadre.setText("Ingrese nombre y apellido");
            txtMadre.setForeground(Color.gray);
        }
        if (txtContacto.getText().isEmpty()) {
            txtContacto.setText("Ingrese el numero del padre");
            txtContacto.setForeground(Color.gray);
        }

    }//GEN-LAST:event_txtNombreMousePressed

    private void txtPadreMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPadreMousePressed
        if (txtPadre.getText().equals("Ingrese nombre y apellido")) {
            txtPadre.setText("");
            txtPadre.setForeground(Color.black);
        }
        if (String.valueOf(txtCodigo.getPassword()).isEmpty()) {
            txtCodigo.setText("********");
            txtCodigo.setForeground(Color.gray);
        }
        if (txtApellidos.getText().isEmpty()) {
            txtApellidos.setText("Ingrese sus apellidos");
            txtApellidos.setForeground(Color.gray);
        }
        if (txtMadre.getText().isEmpty()) {
            txtMadre.setText("Ingrese nombre y apellido");
            txtMadre.setForeground(Color.gray);
        }
        if (txtNombre.getText().isEmpty()) {
            txtNombre.setText("Ingrese su nombre  ");
            txtNombre.setForeground(Color.gray);
        }
        if (txtContacto.getText().isEmpty()) {
            txtContacto.setText("Ingrese el numero del padre");
            txtContacto.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txtPadreMousePressed

    private void txtMadreMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMadreMousePressed
        if (txtMadre.getText().equals("Ingrese nombre y apellido")) {
            txtMadre.setText("");
            txtMadre.setForeground(Color.black);
        }
        if (String.valueOf(txtCodigo.getPassword()).isEmpty()) {
            txtCodigo.setText("********");
            txtCodigo.setForeground(Color.gray);
        }
        if (txtApellidos.getText().isEmpty()) {
            txtApellidos.setText("Ingrese sus apellidos");
            txtApellidos.setForeground(Color.gray);
        }
        if (txtNombre.getText().isEmpty()) {
            txtNombre.setText("Ingrese su nombre  ");
            txtNombre.setForeground(Color.gray);
        }
        if (txtPadre.getText().isEmpty()) {
            txtPadre.setText("Ingrese nombre y apellido");
            txtPadre.setForeground(Color.gray);
        }
        if (txtContacto.getText().isEmpty()) {
            txtContacto.setText("Ingrese el numero del padre");
            txtContacto.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txtMadreMousePressed

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
        txtMinimizar.setBackground(new Color(0, 134, 190));
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

    private void txtContactoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtContactoMousePressed
        if (txtContacto.getText().equals("Ingrese el numero del padre")) {
            txtContacto.setText("");
            txtContacto.setForeground(Color.black);
        }
        if (String.valueOf(txtCodigo.getPassword()).isEmpty()) {
            txtCodigo.setText("********");
            txtCodigo.setForeground(Color.gray);
        }
        if (txtApellidos.getText().isEmpty()) {
            txtApellidos.setText("Ingrese sus apellidos");
            txtApellidos.setForeground(Color.gray);
        }
        if (txtNombre.getText().isEmpty()) {
            txtNombre.setText("Ingrese su nombre  ");
            txtNombre.setForeground(Color.gray);
        }
        if (txtPadre.getText().isEmpty()) {
            txtPadre.setText("Ingrese nombre y apellido");
            txtPadre.setForeground(Color.gray);
        }
        if (txtMadre.getText().isEmpty()) {
            txtMadre.setText("Ingrese nombre y apellido");
            txtMadre.setForeground(Color.gray);
        }

    }//GEN-LAST:event_txtContactoMousePressed

    int leerCodigo() {
        try {
            String aux = txtCodigo.getText().trim();
            int codigo = Integer.parseInt(aux);
            return codigo;
        } catch (Exception e) {
            return -99;
        }
    }

    String leerNombre() {
        try {
            String nombre = txtNombre.getText().trim();
            return nombre;
        } catch (Exception e) {
            return null;
        }
    }

    String leerApellidos() {
        try {
            String apellidos = txtApellidos.getText().trim();
            return apellidos;
        } catch (Exception e) {
            return null;
        }
    }

    void registrarEmpleado() {
        if (leerCodigo() == -99) {
            mensaje("debe ingresar un numero entero");
        } else if (leerNombre() == null) {
            mensaje("debe ingresar el nombre");
        } else if (leerApellidos() == null) {
            mensaje("debe ingresar apellidos");
        } else {
            String genero;
            String cargo;
            if (brMasculino.isSelected()) {
                genero = "M";
            } else {
                genero = "F";
            }
            cargo = (String) cmbCargo.getSelectedItem();

            String aux = txtCodigo.getText();
            int codigo = Integer.parseInt(aux);

            Connection conexion = null;
            try {
                conexion = Conexion.getConection();
                if (conexion.getAutoCommit()) {
                    //Esto hacemos para el AutoCommit no se inicialice solo ya que nosotros lo haremos manualmente 
                    conexion.setAutoCommit(false);
                }

//                EstudianteDAO_JDBC empleadoDao = new EstudianteDAO_JDBC(conexion);
//                Estudiante EmpleadoNuevo = new Estudiante(codigo, txtNombre.getText(), txtApellidos.getText(), genero, cargo);
//                empleadoDao.insertar(EmpleadoNuevo);

                conexion.commit();
                mensaje(" Su registro fue exitoso");
            } catch (SQLException ex) {
                mensaje(" Hay algun error revise los datos ingresados");
                try {
                    //Perso si sale un error entonces rollback se ejecutara y no se realizara ningun cambio
                    conexion.rollback();
                } catch (SQLException ex1) {
                    ex1.printStackTrace(System.out);
                }
            }

        }

    }

    void limpiar() {
        limpiarTabla();
        txtCodigo.setText(null);
        txtNombre.setText(null);
        txtApellidos.setText(null);
        brMasculino.setSelected(true);
        cmbCargo.setSelectedIndex(0);
        limpiarTabla();
    }

    void limpiarTabla() {
        for (int i = 0; i < tablaEmpleados.getRowCount(); i++) {
            dtm.removeRow(i);
            i -= 1;
        }
    }

    char leerGenero() {
        if (brMasculino.isSelected()) {
            return 'M';
        } else {
            return 'F';
        }
    }

    String leerCargo() {
        return ((String) cmbCargo.getSelectedItem());
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Registro_Estudiante.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Registro_Estudiante.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Registro_Estudiante.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Registro_Estudiante.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Registro_Estudiante().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ASISTENCIA;
    private javax.swing.JPanel ActualizarBtnColor;
    private javax.swing.JLabel DE;
    private javax.swing.JPanel RegresarBtnColor;
    private javax.swing.JRadioButton brFemenino;
    private javax.swing.JRadioButton brMasculino;
    private javax.swing.JLabel btnActualizar;
    private javax.swing.JLabel btnEliminar1;
    private javax.swing.JLabel btnGuardar;
    private javax.swing.JLabel btnMinimizar;
    private javax.swing.JLabel btnRegresar;
    private javax.swing.JLabel btnSalir;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton cerrar1;
    private javax.swing.JComboBox<String> cmbCargo;
    private javax.swing.JPanel eliminatBtnColor1;
    private javax.swing.JLabel favicon;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JPanel loginBtn;
    private javax.swing.JLabel logoname;
    private org.edisoncor.gui.panel.Panel panel3;
    private javax.swing.JLabel passLabel;
    private javax.swing.JLabel passLabel1;
    private javax.swing.JLabel passLabel2;
    private javax.swing.JTable tablaEmpleados;
    private javax.swing.JLabel title;
    private javax.swing.JTextField txtApellidos;
    private javax.swing.JPasswordField txtCodigo;
    private javax.swing.JTextField txtContacto;
    private javax.swing.JTextField txtMadre;
    private javax.swing.JPanel txtMinimizar;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPadre;
    private javax.swing.JPanel txtSalir;
    private javax.swing.JLabel userLabel;
    // End of variables declaration//GEN-END:variables
}
