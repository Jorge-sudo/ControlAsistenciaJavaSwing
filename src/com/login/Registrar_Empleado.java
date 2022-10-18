package com.login;

import Empleado_TablaDeSQL.Empleado;
import Empleado_TablaDeSQL.EmpleadoDAO_JDBC;
import java.awt.Color;
import java.io.File;
import java.sql.*;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import Estudiante_TablaDeSQL.Conexion;

public class Registrar_Empleado extends javax.swing.JFrame {

    String ruta = null;
    int xMouse, yMouse;
    private boolean minimiza;

    public Registrar_Empleado() {
        initComponents();
        this.setLocationRelativeTo(this);
    }

    void mensaje(String msje) {
        JOptionPane.showMessageDialog(null, msje);
    }

    void registrarEmpleado() {
        if (leerCelular() == -99) {
            mensaje("debe ingresar un numero entero");
        } else if (leerNombre() == null) {
            mensaje("debe ingresar el nombre");
        } else if (leerApellidos() == null) {
            mensaje("debe ingresar apellidos");
        } else if (leerUsuario() == null) {
            mensaje("debe ingresas usuario");
        } else if (leerPassword() == null) {
            mensaje("debe ingresar su contraseña");
        } else if (leerCodRecuperacion() == null) {
            mensaje("debe ingresar su codigo de recuperacion");
        } else {
            String genero = leerGenero();
            String cargo = leerCargo();
            String curso = leerCurso();

            Connection conexion = null;
            try {
                conexion = Conexion.getConection();
                if (conexion.getAutoCommit()) {
                    //Esto hacemos para el AutoCommit no se inicialice solo ya que nosotros lo haremos manualmente 
                    conexion.setAutoCommit(false);
                }

                EmpleadoDAO_JDBC empleadoDao = new EmpleadoDAO_JDBC(conexion);
                Empleado empleadoNuevo = new Empleado();
//                Empleado empleadoNuevo = new Empleado(leerUsuario(), leerPassword(), leerCodRecuperacion(),
//                        leerNombre(), leerApellidos(), leerCelular(), genero, curso, cargo);
//                empleadoDao.insertarEmpleado(empleadoNuevo, ruta);

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
        txtPassword.setText(null);
        txtNombre.setText(null);
        txtApellidos.setText(null);
        brMasculino.setSelected(true);
        cmbCurso.setSelectedIndex(0);
        txtCodigoRec.setText(null);
        txtUsuario.setText(null);
        txtCelular.setText(null);
        cmbCurso.setSelectedIndex(0);
        urlImagen.setText(null);
    }

    String leerGenero() {
        if (brMasculino.isSelected()) {
            return "M";
        } else {
            return "F";
        }
    }

    String leerCargo() {
        return ((String) cmbCargo.getSelectedItem());
    }

    String leerCurso() {
        return ((String) cmbCurso.getSelectedItem());
    }

    String leerPassword() {
        try {
            String codigo = txtPassword.getText().trim();
            return codigo;
        } catch (Exception e) {
            return null;
        }
    }

    int leerCelular() {
        try {
            String aux = txtCelular.getText().trim();
            int celular = Integer.parseInt(aux);
            return celular;
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

    String leerUsuario() {
        try {
            String usuario = txtUsuario.getText().trim();
            return usuario;
        } catch (Exception e) {
            return null;
        }
    }

    String leerCodRecuperacion() {
        try {
            String recuperacion = txtCodigoRec.getText().trim();
            return recuperacion;
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        panel3 = new org.edisoncor.gui.panel.Panel();
        jLabel4 = new javax.swing.JLabel();
        cerrar1 = new javax.swing.JButton();
        title = new javax.swing.JLabel();
        userLabel = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        passLabel = new javax.swing.JLabel();
        btnRegistrar = new javax.swing.JPanel();
        loginBtnTxt = new javax.swing.JLabel();
        txtMinimizar = new javax.swing.JPanel();
        btnMinimizar = new javax.swing.JLabel();
        txtSalir = new javax.swing.JPanel();
        btnSalir = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        passLabel1 = new javax.swing.JLabel();
        cmbCurso = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        brMasculino = new javax.swing.JRadioButton();
        brFemenino = new javax.swing.JRadioButton();
        txtPassword = new javax.swing.JPasswordField();
        jSeparator4 = new javax.swing.JSeparator();
        passLabel2 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        txtUsuario = new javax.swing.JTextField();
        userLabel1 = new javax.swing.JLabel();
        txtCodigoRec = new javax.swing.JPasswordField();
        passLabel3 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        cmbCargo = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        btnIniciarSesion = new javax.swing.JPanel();
        loginBtnTxt1 = new javax.swing.JLabel();
        passLabel4 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        txtApellidos = new javax.swing.JTextField();
        jSeparator8 = new javax.swing.JSeparator();
        txtCelular = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        btnAbrirImagen = new javax.swing.JPanel();
        txtAbrirImagen = new javax.swing.JLabel();
        urlImagen = new javax.swing.JLabel();
        Imagen = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        panel3.setBackground(new java.awt.Color(112, 222, 200));
        panel3.setForeground(new java.awt.Color(255, 255, 255));
        panel3.setColorPrimario(new java.awt.Color(0, 134, 190));
        panel3.setColorSecundario(new java.awt.Color(153, 255, 204));
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

        title.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        title.setForeground(new java.awt.Color(255, 255, 255));
        title.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/images/supervisor.png"))); // NOI18N
        title.setText("  REGISTRAR  PERSONAL");
        panel3.add(title, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, 490, 130));

        userLabel.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        userLabel.setForeground(new java.awt.Color(0, 0, 0));
        userLabel.setText("NOMBRE");
        panel3.add(userLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, -1, -1));

        txtNombre.setBackground(new java.awt.Color(53, 176, 194));
        txtNombre.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtNombre.setForeground(new java.awt.Color(51, 51, 51));
        txtNombre.setText("Ingrese su nombre ");
        txtNombre.setBorder(null);
        txtNombre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtNombreMousePressed(evt);
            }
        });
        panel3.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 190, 270, 30));

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        panel3.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 220, 280, 10));

        passLabel.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        passLabel.setForeground(new java.awt.Color(0, 0, 0));
        passLabel.setText("APELLIDOS");
        panel3.add(passLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 230, -1, -1));

        btnRegistrar.setBackground(new java.awt.Color(0, 134, 190));

        loginBtnTxt.setFont(new java.awt.Font("Roboto Condensed", 1, 14)); // NOI18N
        loginBtnTxt.setForeground(new java.awt.Color(255, 255, 255));
        loginBtnTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        loginBtnTxt.setText("REGISTRAR");
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
            .addComponent(loginBtnTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
        );
        btnRegistrarLayout.setVerticalGroup(
            btnRegistrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(loginBtnTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        panel3.add(btnRegistrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 590, 110, 40));

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

        panel3.add(txtMinimizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 0, 30, -1));

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

        panel3.add(txtSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 0, 30, 30));

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
        panel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 390, 40));

        passLabel1.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        passLabel1.setForeground(new java.awt.Color(0, 0, 0));
        passLabel1.setText("GENERO");
        panel3.add(passLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 410, -1, 30));

        cmbCurso.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Verde", "Amarillo", "Azul", "Rosado", "Rojo" }));
        cmbCurso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCursoActionPerformed(evt);
            }
        });
        panel3.add(cmbCurso, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 500, -1, -1));

        jLabel3.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("ELERGIR FOTO :");
        panel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 380, 120, -1));

        jPanel2.setBackground(new java.awt.Color(112, 222, 200));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true));
        jPanel2.setForeground(new java.awt.Color(0, 0, 0));
        jPanel2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        brMasculino.setBackground(new java.awt.Color(112, 222, 200));
        buttonGroup1.add(brMasculino);
        brMasculino.setForeground(new java.awt.Color(0, 0, 0));
        brMasculino.setText("MASCULINO");
        brMasculino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brMasculinoActionPerformed(evt);
            }
        });
        jPanel2.add(brMasculino, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        brFemenino.setBackground(new java.awt.Color(112, 222, 200));
        buttonGroup1.add(brFemenino);
        brFemenino.setForeground(new java.awt.Color(0, 0, 0));
        brFemenino.setText("FEMENINO");
        brFemenino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brFemeninoActionPerformed(evt);
            }
        });
        jPanel2.add(brFemenino, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 100, -1));

        panel3.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 440, 120, 80));

        txtPassword.setBackground(new java.awt.Color(71, 190, 196));
        txtPassword.setForeground(new java.awt.Color(51, 51, 51));
        txtPassword.setText("********");
        txtPassword.setBorder(null);
        txtPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtPasswordMousePressed(evt);
            }
        });
        panel3.add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 260, 270, 30));

        jSeparator4.setForeground(new java.awt.Color(0, 0, 0));
        panel3.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 290, 290, 10));

        passLabel2.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        passLabel2.setForeground(new java.awt.Color(0, 0, 0));
        passLabel2.setText("CONTRASEÑA");
        panel3.add(passLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 230, -1, -1));

        jSeparator5.setForeground(new java.awt.Color(0, 0, 0));
        panel3.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 220, 280, 10));

        txtUsuario.setBackground(new java.awt.Color(53, 176, 194));
        txtUsuario.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtUsuario.setForeground(new java.awt.Color(51, 51, 51));
        txtUsuario.setText("Ingrese su nombre de usuario");
        txtUsuario.setBorder(null);
        txtUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtUsuarioMousePressed(evt);
            }
        });
        panel3.add(txtUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 190, 270, 30));

        userLabel1.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        userLabel1.setForeground(new java.awt.Color(0, 0, 0));
        userLabel1.setText("USUARIO");
        panel3.add(userLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 160, -1, -1));

        txtCodigoRec.setBackground(new java.awt.Color(89, 204, 198));
        txtCodigoRec.setForeground(new java.awt.Color(51, 51, 51));
        txtCodigoRec.setText("********");
        txtCodigoRec.setBorder(null);
        txtCodigoRec.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtCodigoRecMousePressed(evt);
            }
        });
        panel3.add(txtCodigoRec, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 330, 270, 30));

        passLabel3.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        passLabel3.setForeground(new java.awt.Color(0, 0, 0));
        passLabel3.setText("CODIGO DE RECUPERACION");
        panel3.add(passLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 300, -1, -1));

        jSeparator6.setForeground(new java.awt.Color(0, 0, 0));
        panel3.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 360, 290, 10));

        cmbCargo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Secretario(a)", "Regente(a)", "Portero(a)", "Profesor(a)", "Director(a)" }));
        cmbCargo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCargoActionPerformed(evt);
            }
        });
        panel3.add(cmbCargo, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 430, -1, -1));

        jLabel5.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("CARGO :");
        panel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 430, 60, 20));

        btnIniciarSesion.setBackground(new java.awt.Color(0, 134, 190));

        loginBtnTxt1.setFont(new java.awt.Font("Roboto Condensed", 1, 14)); // NOI18N
        loginBtnTxt1.setForeground(new java.awt.Color(255, 255, 255));
        loginBtnTxt1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        loginBtnTxt1.setText("INICIAR SESION");
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
            .addComponent(loginBtnTxt1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
        );
        btnIniciarSesionLayout.setVerticalGroup(
            btnIniciarSesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(loginBtnTxt1, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        panel3.add(btnIniciarSesion, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 590, 130, -1));

        passLabel4.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        passLabel4.setForeground(new java.awt.Color(0, 0, 0));
        passLabel4.setText("Nro. CELULAR");
        panel3.add(passLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 300, -1, -1));

        jSeparator7.setForeground(new java.awt.Color(0, 0, 0));
        panel3.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 290, 280, 10));

        txtApellidos.setBackground(new java.awt.Color(71, 190, 196));
        txtApellidos.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtApellidos.setForeground(new java.awt.Color(51, 51, 51));
        txtApellidos.setText("Ingrese sus apellidos");
        txtApellidos.setBorder(null);
        txtApellidos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtApellidosMousePressed(evt);
            }
        });
        panel3.add(txtApellidos, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 260, 270, 30));

        jSeparator8.setForeground(new java.awt.Color(0, 0, 0));
        panel3.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 360, 280, 10));

        txtCelular.setBackground(new java.awt.Color(89, 204, 198));
        txtCelular.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtCelular.setForeground(new java.awt.Color(51, 51, 51));
        txtCelular.setText("Ingrese su numero de celular");
        txtCelular.setBorder(null);
        txtCelular.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtCelularMousePressed(evt);
            }
        });
        panel3.add(txtCelular, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 330, 270, 30));

        jLabel6.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("CURSO:");
        panel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 500, 60, -1));

        btnAbrirImagen.setBackground(new java.awt.Color(0, 134, 190));

        txtAbrirImagen.setFont(new java.awt.Font("Roboto Condensed", 1, 14)); // NOI18N
        txtAbrirImagen.setForeground(new java.awt.Color(255, 255, 255));
        txtAbrirImagen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtAbrirImagen.setText("ABRIR IMAGEN");
        txtAbrirImagen.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        txtAbrirImagen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtAbrirImagenMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtAbrirImagenMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txtAbrirImagenMouseExited(evt);
            }
        });

        javax.swing.GroupLayout btnAbrirImagenLayout = new javax.swing.GroupLayout(btnAbrirImagen);
        btnAbrirImagen.setLayout(btnAbrirImagenLayout);
        btnAbrirImagenLayout.setHorizontalGroup(
            btnAbrirImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtAbrirImagen, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
        );
        btnAbrirImagenLayout.setVerticalGroup(
            btnAbrirImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtAbrirImagen, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        panel3.add(btnAbrirImagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 410, 120, 30));

        urlImagen.setForeground(new java.awt.Color(0, 0, 0));
        panel3.add(urlImagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 540, 290, 30));
        panel3.add(Imagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 450, 100, 90));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel3, javax.swing.GroupLayout.PREFERRED_SIZE, 698, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel3, javax.swing.GroupLayout.PREFERRED_SIZE, 654, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cerrar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cerrar1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cerrar1ActionPerformed

    private void txtNombreMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNombreMousePressed
        if (txtNombre.getText().equals("Ingrese su nombre ")) {
            txtNombre.setText("");
            txtNombre.setForeground(Color.black);
        }
        if (txtApellidos.getText().isEmpty()) {
            txtApellidos.setText("Ingrese sus apellidos");
            txtApellidos.setForeground(new Color(51, 51, 51));
        }
        if (txtCelular.getText().isEmpty()) {
            txtCelular.setText("Ingrese su numero de celular");
            txtCelular.setForeground(new Color(51, 51, 51));
        }
        if (txtUsuario.getText().isEmpty()) {
            txtUsuario.setText("Ingrese su nombre de usuario");
            txtUsuario.setForeground(new Color(51, 51, 51));
        }
        if (String.valueOf(txtPassword.getPassword()).isEmpty()) {
            txtPassword.setText("********");
            txtPassword.setForeground(new Color(51, 51, 51));
        }
        if (String.valueOf(txtCodigoRec.getPassword()).isEmpty()) {
            txtCodigoRec.setText("********");
            txtCodigoRec.setForeground(new Color(51, 51, 51));
        }
    }//GEN-LAST:event_txtNombreMousePressed

    private void loginBtnTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginBtnTxtMouseClicked
        Conexion conn = new Conexion();
        String url = urlImagen.getText();
//        if (url.trim().length() != 0 && nombre.trim().length() != 0) {
//            conn.guardar(ruta, nombre);
//            VerTabla v = new VerTabla();
//            v.visualizar_tabla(tabla);
//        } else {
//            JOptionPane.showMessageDialog(null, "No debe dejar los campos vacios: Nombre e Imagen");
//        }

        registrarEmpleado();
        limpiar();
    }//GEN-LAST:event_loginBtnTxtMouseClicked

    private void loginBtnTxtMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginBtnTxtMouseEntered
        btnRegistrar.setBackground(new Color(0, 156, 223));
    }//GEN-LAST:event_loginBtnTxtMouseEntered

    private void loginBtnTxtMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginBtnTxtMouseExited
        btnRegistrar.setBackground(new Color(0, 134, 190));
    }//GEN-LAST:event_loginBtnTxtMouseExited

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

    private void jLabel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_jLabel1MouseDragged

    private void jLabel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_jLabel1MousePressed

    private void cmbCursoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCursoActionPerformed

    }//GEN-LAST:event_cmbCursoActionPerformed

    private void txtPasswordMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPasswordMousePressed
        if (String.valueOf(txtPassword.getPassword()).equals("********")) {
            txtPassword.setText("");
            txtPassword.setForeground(Color.black);
        }
        if (txtNombre.getText().isEmpty()) {
            txtNombre.setText("Ingrese su nombre ");
            txtNombre.setForeground(new Color(51, 51, 51));
        }
        if (txtApellidos.getText().isEmpty()) {
            txtApellidos.setText("Ingrese sus apellidos");
            txtApellidos.setForeground(new Color(51, 51, 51));
        }
        if (txtCelular.getText().isEmpty()) {
            txtCelular.setText("Ingrese su numero de celular");
            txtCelular.setForeground(new Color(51, 51, 51));
        }
        if (txtUsuario.getText().isEmpty()) {
            txtUsuario.setText("Ingrese su nombre de usuario");
            txtUsuario.setForeground(new Color(51, 51, 51));
        }
        if (String.valueOf(txtCodigoRec.getPassword()).isEmpty()) {
            txtCodigoRec.setText("********");
            txtCodigoRec.setForeground(new Color(51, 51, 51));
        }
    }//GEN-LAST:event_txtPasswordMousePressed

    private void txtUsuarioMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtUsuarioMousePressed
        if (txtUsuario.getText().equals("Ingrese su nombre de usuario")) {
            txtUsuario.setText("");
            txtUsuario.setForeground(Color.black);
        }
        if (txtNombre.getText().isEmpty()) {
            txtNombre.setText("Ingrese su nombre ");
            txtNombre.setForeground(new Color(51, 51, 51));
        }
        if (txtApellidos.getText().isEmpty()) {
            txtApellidos.setText("Ingrese sus apellidos");
            txtApellidos.setForeground(new Color(51, 51, 51));
        }
        if (txtCelular.getText().isEmpty()) {
            txtCelular.setText("Ingrese su numero de celular");
            txtCelular.setForeground(new Color(51, 51, 51));
        }
        if (String.valueOf(txtPassword.getPassword()).isEmpty()) {
            txtPassword.setText("********");
            txtPassword.setForeground(new Color(51, 51, 51));
        }
        if (String.valueOf(txtCodigoRec.getPassword()).isEmpty()) {
            txtCodigoRec.setText("********");
            txtCodigoRec.setForeground(new Color(51, 51, 51));
        }
    }//GEN-LAST:event_txtUsuarioMousePressed

    private void txtCodigoRecMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCodigoRecMousePressed
        if (String.valueOf(txtCodigoRec.getPassword()).equals("********")) {
            txtCodigoRec.setText("");
            txtCodigoRec.setForeground(Color.black);
        }
        if (txtNombre.getText().isEmpty()) {
            txtNombre.setText("Ingrese su nombre ");
            txtNombre.setForeground(new Color(51, 51, 51));
        }
        if (txtApellidos.getText().isEmpty()) {
            txtApellidos.setText("Ingrese sus apellidos");
            txtApellidos.setForeground(new Color(51, 51, 51));
        }
        if (txtCelular.getText().isEmpty()) {
            txtCelular.setText("Ingrese su numero de celular");
            txtCelular.setForeground(new Color(51, 51, 51));
        }
        if (txtUsuario.getText().isEmpty()) {
            txtUsuario.setText("Ingrese su nombre de usuario");
            txtUsuario.setForeground(new Color(51, 51, 51));
        }
        if (String.valueOf(txtPassword.getPassword()).isEmpty()) {
            txtPassword.setText("********");
            txtPassword.setForeground(new Color(51, 51, 51));
        }
    }//GEN-LAST:event_txtCodigoRecMousePressed

    private void cmbCargoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCargoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbCargoActionPerformed

    private void loginBtnTxt1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginBtnTxt1MouseExited
        btnIniciarSesion.setBackground(new Color(0, 134, 190));
    }//GEN-LAST:event_loginBtnTxt1MouseExited

    private void loginBtnTxt1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginBtnTxt1MouseEntered
        btnIniciarSesion.setBackground(new Color(0, 156, 223));
    }//GEN-LAST:event_loginBtnTxt1MouseEntered

    private void loginBtnTxt1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginBtnTxt1MouseClicked
        Login_Usuario iniciarUs = new Login_Usuario();
        iniciarUs.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_loginBtnTxt1MouseClicked

    private void brFemeninoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_brFemeninoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_brFemeninoActionPerformed

    private void brMasculinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_brMasculinoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_brMasculinoActionPerformed

    private void txtApellidosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtApellidosMousePressed
        if (txtApellidos.getText().equals("Ingrese sus apellidos")) {
            txtApellidos.setText("");
            txtApellidos.setForeground(Color.black);
        }
        if (txtNombre.getText().isEmpty()) {
            txtNombre.setText("Ingrese su nombre ");
            txtNombre.setForeground(new Color(51, 51, 51));
        }
        if (txtCelular.getText().isEmpty()) {
            txtCelular.setText("Ingrese su numero de celular");
            txtCelular.setForeground(new Color(51, 51, 51));
        }
        if (txtUsuario.getText().isEmpty()) {
            txtUsuario.setText("Ingrese su nombre de usuario");
            txtUsuario.setForeground(new Color(51, 51, 51));
        }
        if (String.valueOf(txtPassword.getPassword()).isEmpty()) {
            txtPassword.setText("********");
            txtPassword.setForeground(new Color(51, 51, 51));
        }
        if (String.valueOf(txtCodigoRec.getPassword()).isEmpty()) {
            txtCodigoRec.setText("********");
            txtCodigoRec.setForeground(new Color(51, 51, 51));
        }
    }//GEN-LAST:event_txtApellidosMousePressed

    private void txtCelularMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCelularMousePressed
        if (txtCelular.getText().equals("Ingrese su numero de celular")) {
            txtCelular.setText("");
            txtCelular.setForeground(Color.black);
        }
        if (txtNombre.getText().isEmpty()) {
            txtNombre.setText("Ingrese su nombre ");
            txtNombre.setForeground(new Color(51, 51, 51));
        }
        if (txtApellidos.getText().isEmpty()) {
            txtApellidos.setText("Ingrese sus apellidos");
            txtApellidos.setForeground(new Color(51, 51, 51));
        }
        if (txtUsuario.getText().isEmpty()) {
            txtUsuario.setText("Ingrese su nombre de usuario");
            txtUsuario.setForeground(new Color(51, 51, 51));
        }
        if (String.valueOf(txtPassword.getPassword()).isEmpty()) {
            txtPassword.setText("********");
            txtPassword.setForeground(new Color(51, 51, 51));
        }
        if (String.valueOf(txtCodigoRec.getPassword()).isEmpty()) {
            txtCodigoRec.setText("********");
            txtCodigoRec.setForeground(new Color(51, 51, 51));
        }

    }//GEN-LAST:event_txtCelularMousePressed

    private void txtAbrirImagenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtAbrirImagenMouseClicked
        JFileChooser j = new JFileChooser();
        j.setCurrentDirectory(new File("Imagenes/"));
        int ap = j.showOpenDialog(this);

        if (ap == JFileChooser.APPROVE_OPTION) {
            ruta = j.getSelectedFile().getAbsolutePath();
            Imagen.setIcon(new ImageIcon(ruta));
            urlImagen.setText(ruta);
        }

    }//GEN-LAST:event_txtAbrirImagenMouseClicked

    private void txtAbrirImagenMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtAbrirImagenMouseEntered
        btnAbrirImagen.setBackground(new Color(0, 156, 223));
    }//GEN-LAST:event_txtAbrirImagenMouseEntered

    private void txtAbrirImagenMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtAbrirImagenMouseExited
        btnIniciarSesion.setBackground(new Color(0, 134, 190));
    }//GEN-LAST:event_txtAbrirImagenMouseExited

    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Registrar_Empleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Registrar_Empleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Registrar_Empleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Registrar_Empleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Registrar_Empleado().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Imagen;
    private javax.swing.JRadioButton brFemenino;
    private javax.swing.JRadioButton brMasculino;
    private javax.swing.JPanel btnAbrirImagen;
    private javax.swing.JPanel btnIniciarSesion;
    private javax.swing.JLabel btnMinimizar;
    private javax.swing.JPanel btnRegistrar;
    private javax.swing.JLabel btnSalir;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton cerrar1;
    private javax.swing.JComboBox<String> cmbCargo;
    private javax.swing.JComboBox<String> cmbCurso;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JLabel loginBtnTxt;
    private javax.swing.JLabel loginBtnTxt1;
    private org.edisoncor.gui.panel.Panel panel3;
    private javax.swing.JLabel passLabel;
    private javax.swing.JLabel passLabel1;
    private javax.swing.JLabel passLabel2;
    private javax.swing.JLabel passLabel3;
    private javax.swing.JLabel passLabel4;
    private javax.swing.JLabel title;
    private javax.swing.JLabel txtAbrirImagen;
    private javax.swing.JTextField txtApellidos;
    private javax.swing.JTextField txtCelular;
    private javax.swing.JPasswordField txtCodigoRec;
    private javax.swing.JPanel txtMinimizar;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JPanel txtSalir;
    private javax.swing.JTextField txtUsuario;
    private javax.swing.JLabel urlImagen;
    private javax.swing.JLabel userLabel;
    private javax.swing.JLabel userLabel1;
    // End of variables declaration//GEN-END:variables
}
