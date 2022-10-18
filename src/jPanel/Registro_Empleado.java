package jPanel;

import Empleado_TablaDeSQL.Empleado;
import Empleado_TablaDeSQL.EmpleadoDAO_JDBC;
import java.awt.Color;
import java.io.File;
import java.sql.*;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import Estudiante_TablaDeSQL.Conexion;
import Empleado_TablaDeSQL.Profesor;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.util.Enumeration;
import java.util.HashMap;
import panamahitek.Arduino.PanamaHitek_Arduino;

public class Registro_Empleado extends javax.swing.JPanel {

    String ruta = null;
    String codigoTarjeta;
    String aux;
    String arduinoConexion = null;

    

    Enumeration cuantosPuertos;//guarda la lista de los puertos Disponibles 
    HashMap portMap = new HashMap();//Guarda la info del arduino

    Empleado empleadonuevo = new Empleado();

    PanamaHitek_Arduino conexion = new PanamaHitek_Arduino();
    SerialPortEventListener listener = new SerialPortEventListener() {
        @Override
        public void serialEvent(SerialPortEvent spe) {
            try {
                if (conexion.isMessageAvailable()) {
                    aux = conexion.printMessage().trim();
                    if (aux.equalsIgnoreCase("Control Inicializado...")) {
                        arduinoConexion = aux;
                    }else{
                        codigoTarjeta = aux;
                        System.out.println(" Codigo = " + codigoTarjeta);
                        RFID.setText("REGISTRADO..!");
                    }

                }
            } catch (Exception ex) {
                mensajeError("Error al imprimir datos del Arduino");
            }
        }

    };

    public Registro_Empleado() {
        initComponents();

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

    public void registraEmpleado(String ruta) {
        int Empleado,Persona,Profesor;
        if(leerCI() == -99){
            mensajeAdvertencia("¡Ingrese su Cedula de Identidad!");
        } else if ("Ingrese su nombre".equals(leerNombre()) || "".equals(leerNombre())) {
            mensajeAdvertencia("¡Ingrese su Nombre!");
        } else if ("Ingrese sus apellidos".equals(leerApellidos()) || "".equals(leerApellidos())) {
            mensajeAdvertencia("¡Ingrese sus Apellidos!");
        } else if (leerCelular() == -99) {
            mensajeAdvertencia("¡Debe ingresar su Nro de Celular!");
        } else if ("Ingrese su nombre de usuario".equals(leerUsuario()) || "".equals(leerUsuario())) {
            mensajeAdvertencia("¡Ingrese su Usuario!");
        } else if ("********".equals(leerPassword()) || "".equals(leerPassword())) {
            mensajeAdvertencia("¡Ingrese su Contraseña!");
        } else if ("********".equals(leerCodRecuperacion()) || "".equals(leerCodRecuperacion())) {
            mensajeAdvertencia("¡Ingrese su codigo de recuperacion!");
        } else if (this.ruta == null) {
            mensajeAdvertencia("¡Suba su Fotografia!");
        } else if (this.arduinoConexion == null) {
            mensajeAdvertencia("¡Conecte el Arduino para registrar la tarjeta RFID!");
        } else if ("NO REGISTRADO".equals(RFID.getText().trim())) {
            mensajeAdvertencia("¡Pase la Tarjeta RFID!");
        } else {

            Connection conexion = null;
            try {
                conexion = Conexion.getConection();
                if (conexion.getAutoCommit()) {
                    //Esto hacemos para el AutoCommit no se inicialice solo ya que nosotros lo haremos manualmente 
                    conexion.setAutoCommit(false);
                }

                EmpleadoDAO_JDBC empleadoDao = new EmpleadoDAO_JDBC(conexion);

                Empleado empleadoNuevo = new Empleado(leerCI(), leerNombre(), leerApellidos(), codigoTarjeta, leerUsuario(),
                        leerPassword(), leerCodRecuperacion(), leerCelular(), leerGenero(), leerCargo());

                Profesor profesor = new Profesor(leerCI(), leerAula());

                Persona = empleadoDao.insertarPersona(empleadoNuevo, ruta);
                Empleado = empleadoDao.insertarEmpleado(empleadoNuevo);
                if("Profesor(a)".equals(leerCargo())){
                    Profesor = empleadoDao.insertarProfesor(profesor);
                    mensajeExito(" Se Inserto " + (Empleado + Persona + Profesor)/3  + " Usuario con exito.");
                }else{
                    mensajeExito(" Se Inserto " + (Empleado + Persona)/2  + " Usuario con exito.");
                }
                conexion.commit();
                limpiar();
            } catch (SQLException ex) {
                mensajeError(" Hay algun error revise los datos ingresados");
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
        txtCI.setText(null);
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

    String leerAula() {
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
        } catch (NumberFormatException e) {
            return -99;
        }
    }

    long leerCI() {
        try {
            String aux = txtCI.getText().trim();
            long ID = Long.parseLong(aux);
            return ID;
        } catch (NumberFormatException e) {
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

    public String obtenerPuerto() {
        //nos trae la cantidad de puertos disponibles 
        cuantosPuertos = CommPortIdentifier.getPortIdentifiers();
        String todo = "";
        //recorremos la lista de los puertos siponibles
        while (cuantosPuertos.hasMoreElements()) {
            //el puerto solito
            CommPortIdentifier solito = (CommPortIdentifier) cuantosPuertos.nextElement();
            if (solito.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                portMap.put(solito.getName(), solito);
                todo += solito.getName();
                //com1,com2,com3,

                System.out.println("Puerto encontrado: " + solito.getName());
            }
        }//LLAVE WHILE
        return todo;
    }//LLAVE OBTENER LISTa 

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        panel2 = new org.edisoncor.gui.panel.Panel();
        jLabel4 = new javax.swing.JLabel();
        cerrar1 = new javax.swing.JButton();
        title = new javax.swing.JLabel();
        userLabel = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        passLabel = new javax.swing.JLabel();
        btnRegistrar = new javax.swing.JPanel();
        loginBtnTxt = new javax.swing.JLabel();
        passLabel1 = new javax.swing.JLabel();
        cmbCurso = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
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
        RFID = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        brMasculino = new javax.swing.JRadioButton();
        brFemenino = new javax.swing.JRadioButton();
        txtCI = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        userLabel2 = new javax.swing.JLabel();
        btnConectar = new javax.swing.JPanel();
        txtConectar = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(242, 242, 242));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel2.setBackground(new java.awt.Color(255, 255, 255));
        panel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 41), 1, true));
        panel2.setForeground(new java.awt.Color(255, 255, 255));
        panel2.setColorPrimario(new java.awt.Color(232, 255, 236));
        panel2.setColorSecundario(new java.awt.Color(232, 255, 236));
        panel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        panel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 0, -1, 595));

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
        panel2.add(cerrar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(912, 6, -1, 33));

        title.setFont(new java.awt.Font("Cambria", 1, 36)); // NOI18N
        title.setForeground(new java.awt.Color(0, 52, 0));
        title.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/images/supervisor.png"))); // NOI18N
        title.setText("  REGISTRO DE USUARIOS");
        panel2.add(title, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 580, 130));

        userLabel.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        userLabel.setForeground(new java.awt.Color(51, 51, 51));
        userLabel.setText("NOMBRE");
        panel2.add(userLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 230, -1, -1));

        txtNombre.setBackground(new java.awt.Color(232, 255, 236));
        txtNombre.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtNombre.setForeground(new java.awt.Color(51, 51, 51));
        txtNombre.setText("Ingrese su nombre ");
        txtNombre.setBorder(null);
        txtNombre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtNombreMousePressed(evt);
            }
        });
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });
        panel2.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 260, 190, 30));

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        panel2.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 290, 190, 10));

        passLabel.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        passLabel.setForeground(new java.awt.Color(51, 51, 51));
        passLabel.setText("APELLIDOS");
        panel2.add(passLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 300, -1, -1));

        btnRegistrar.setBackground(new java.awt.Color(0, 102, 15));
        btnRegistrar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 121, 10), 2));

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
            .addComponent(loginBtnTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
        );
        btnRegistrarLayout.setVerticalGroup(
            btnRegistrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(loginBtnTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        panel2.add(btnRegistrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 460, 100, 40));

        passLabel1.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        passLabel1.setForeground(new java.awt.Color(51, 51, 51));
        passLabel1.setText("GENERO");
        panel2.add(passLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 380, -1, 30));

        cmbCurso.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Verde", "Amarillo", "Azul", "Rosado", "Rojo", "Ninguno" }));
        cmbCurso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCursoActionPerformed(evt);
            }
        });
        panel2.add(cmbCurso, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 470, -1, -1));

        jLabel3.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("ELEGIR FOTO :");
        panel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 180, 120, -1));

        txtPassword.setBackground(new java.awt.Color(232, 255, 236));
        txtPassword.setForeground(new java.awt.Color(51, 51, 51));
        txtPassword.setText("********");
        txtPassword.setBorder(null);
        txtPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtPasswordMousePressed(evt);
            }
        });
        panel2.add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 260, 200, 30));

        jSeparator4.setForeground(new java.awt.Color(0, 0, 0));
        panel2.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 290, 200, 10));

        passLabel2.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        passLabel2.setForeground(new java.awt.Color(51, 51, 51));
        passLabel2.setText("CONTRASEÑA");
        panel2.add(passLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 230, -1, -1));

        jSeparator5.setForeground(new java.awt.Color(0, 0, 0));
        panel2.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 220, 200, 10));

        txtUsuario.setBackground(new java.awt.Color(232, 255, 236));
        txtUsuario.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtUsuario.setForeground(new java.awt.Color(51, 51, 51));
        txtUsuario.setText("Ingrese su nombre de usuario");
        txtUsuario.setBorder(null);
        txtUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtUsuarioMousePressed(evt);
            }
        });
        txtUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtUsuarioKeyTyped(evt);
            }
        });
        panel2.add(txtUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 190, 200, 30));

        userLabel1.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        userLabel1.setForeground(new java.awt.Color(51, 51, 51));
        userLabel1.setText("USUARIO");
        panel2.add(userLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 160, -1, -1));

        txtCodigoRec.setBackground(new java.awt.Color(232, 255, 236));
        txtCodigoRec.setForeground(new java.awt.Color(51, 51, 51));
        txtCodigoRec.setText("********");
        txtCodigoRec.setBorder(null);
        txtCodigoRec.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtCodigoRecMousePressed(evt);
            }
        });
        panel2.add(txtCodigoRec, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 330, 200, 30));

        passLabel3.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        passLabel3.setForeground(new java.awt.Color(51, 51, 51));
        passLabel3.setText("CODIGO DE RECUPERACION");
        panel2.add(passLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 300, -1, -1));

        jSeparator6.setForeground(new java.awt.Color(0, 0, 0));
        panel2.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 360, 200, 10));

        cmbCargo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Secretario(a)", "Regente(a)", "Profesor(a)", "Director(a)" }));
        cmbCargo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCargoActionPerformed(evt);
            }
        });
        panel2.add(cmbCargo, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 470, -1, -1));

        jLabel5.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("CARGO :");
        panel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 470, 60, 20));

        passLabel4.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        passLabel4.setForeground(new java.awt.Color(51, 51, 51));
        passLabel4.setText("Nro. CELULAR");
        panel2.add(passLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 370, -1, -1));

        jSeparator7.setForeground(new java.awt.Color(0, 0, 0));
        panel2.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 360, 190, 10));

        txtApellidos.setBackground(new java.awt.Color(232, 255, 236));
        txtApellidos.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtApellidos.setForeground(new java.awt.Color(51, 51, 51));
        txtApellidos.setText("Ingrese sus apellidos");
        txtApellidos.setBorder(null);
        txtApellidos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtApellidosMousePressed(evt);
            }
        });
        txtApellidos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidosKeyTyped(evt);
            }
        });
        panel2.add(txtApellidos, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 330, 190, 30));

        jSeparator8.setForeground(new java.awt.Color(0, 0, 0));
        panel2.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 430, 190, 10));

        txtCelular.setBackground(new java.awt.Color(232, 255, 236));
        txtCelular.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtCelular.setForeground(new java.awt.Color(51, 51, 51));
        txtCelular.setText("Ingrese su numero de celular");
        txtCelular.setBorder(null);
        txtCelular.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtCelularMousePressed(evt);
            }
        });
        txtCelular.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelularKeyTyped(evt);
            }
        });
        panel2.add(txtCelular, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 400, 190, 30));

        jLabel6.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("CURSO:");
        panel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 470, 60, -1));

        btnAbrirImagen.setBackground(new java.awt.Color(0, 102, 15));
        btnAbrirImagen.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 15), 2));

        txtAbrirImagen.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtAbrirImagen.setForeground(new java.awt.Color(255, 255, 255));
        txtAbrirImagen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtAbrirImagen.setText("Abrir Imagen");
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
            .addComponent(txtAbrirImagen, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
        );
        btnAbrirImagenLayout.setVerticalGroup(
            btnAbrirImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtAbrirImagen, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
        );

        panel2.add(btnAbrirImagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 210, 90, 30));

        urlImagen.setForeground(new java.awt.Color(0, 0, 0));
        panel2.add(urlImagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 350, 290, 30));
        panel2.add(Imagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 250, 100, 90));

        RFID.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        RFID.setForeground(new java.awt.Color(204, 0, 0));
        RFID.setText("NO REGISTRADO");
        panel2.add(RFID, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 420, 190, 30));

        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText("Tarjeta RFID");
        panel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 400, 80, -1));

        brMasculino.setBackground(new java.awt.Color(232, 255, 236));
        buttonGroup1.add(brMasculino);
        brMasculino.setForeground(new java.awt.Color(51, 51, 51));
        brMasculino.setText("MASCULINO");
        brMasculino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brMasculinoActionPerformed(evt);
            }
        });
        panel2.add(brMasculino, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 410, -1, -1));

        brFemenino.setBackground(new java.awt.Color(232, 255, 236));
        buttonGroup1.add(brFemenino);
        brFemenino.setForeground(new java.awt.Color(51, 51, 51));
        brFemenino.setText("FEMENINO");
        brFemenino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brFemeninoActionPerformed(evt);
            }
        });
        panel2.add(brFemenino, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 410, 100, -1));

        txtCI.setBackground(new java.awt.Color(232, 255, 236));
        txtCI.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtCI.setForeground(new java.awt.Color(51, 51, 51));
        txtCI.setText("Ingrese su cedula de identidad");
        txtCI.setBorder(null);
        txtCI.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtCIMousePressed(evt);
            }
        });
        txtCI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCIActionPerformed(evt);
            }
        });
        txtCI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCIKeyTyped(evt);
            }
        });
        panel2.add(txtCI, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 190, 190, 30));

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        panel2.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 220, 190, 10));

        userLabel2.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        userLabel2.setForeground(new java.awt.Color(51, 51, 51));
        userLabel2.setText("CI");
        panel2.add(userLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, -1, -1));

        btnConectar.setBackground(new java.awt.Color(0, 102, 15));
        btnConectar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 15), 2));

        txtConectar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtConectar.setForeground(new java.awt.Color(255, 255, 255));
        txtConectar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtConectar.setText("Conectar");
        txtConectar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        txtConectar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtConectarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtConectarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txtConectarMouseExited(evt);
            }
        });

        javax.swing.GroupLayout btnConectarLayout = new javax.swing.GroupLayout(btnConectar);
        btnConectar.setLayout(btnConectarLayout);
        btnConectarLayout.setHorizontalGroup(
            btnConectarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtConectar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
        );
        btnConectarLayout.setVerticalGroup(
            btnConectarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtConectar, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
        );

        panel2.add(btnConectar, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 90, 90, 30));

        jLabel2.setFont(new java.awt.Font("Arial Black", 1, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 0, 0));
        jLabel2.setText("ARDUINO :");
        panel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 50, 100, 20));

        jPanel1.add(panel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 820, 520));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 570, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtAbrirImagenMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtAbrirImagenMouseExited
        btnAbrirImagen.setBackground(new Color(0,102,15));
    }//GEN-LAST:event_txtAbrirImagenMouseExited

    private void txtAbrirImagenMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtAbrirImagenMouseEntered
        btnAbrirImagen.setBackground(new Color(0,179,36));
    }//GEN-LAST:event_txtAbrirImagenMouseEntered

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
        if (txtCI.getText().isEmpty()) {
            txtCI.setText("Ingrese su cedula de identidad");
            txtCI.setForeground(new Color(51, 51, 51));
        }
    }//GEN-LAST:event_txtCelularMousePressed

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
        if (txtCI.getText().isEmpty()) {
            txtCI.setText("Ingrese su cedula de identidad");
            txtCI.setForeground(new Color(51, 51, 51));
        }
    }//GEN-LAST:event_txtApellidosMousePressed

    private void cmbCargoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCargoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbCargoActionPerformed

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
        if (txtCI.getText().isEmpty()) {
            txtCI.setText("Ingrese su cedula de identidad");
            txtCI.setForeground(new Color(51, 51, 51));
        }
    }//GEN-LAST:event_txtCodigoRecMousePressed

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
        if (txtCI.getText().isEmpty()) {
            txtCI.setText("Ingrese su cedula de identidad");
            txtCI.setForeground(new Color(51, 51, 51));
        }
    }//GEN-LAST:event_txtUsuarioMousePressed

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
        if (txtCI.getText().isEmpty()) {
            txtCI.setText("Ingrese su cedula de identidad");
            txtCI.setForeground(new Color(51, 51, 51));
        }
    }//GEN-LAST:event_txtPasswordMousePressed

    private void brFemeninoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_brFemeninoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_brFemeninoActionPerformed

    private void brMasculinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_brMasculinoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_brMasculinoActionPerformed

    private void cmbCursoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCursoActionPerformed

    }//GEN-LAST:event_cmbCursoActionPerformed

    private void loginBtnTxtMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginBtnTxtMouseExited
        btnRegistrar.setBackground(new Color(0,102,15));
    }//GEN-LAST:event_loginBtnTxtMouseExited

    private void loginBtnTxtMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginBtnTxtMouseEntered
        btnRegistrar.setBackground(new Color(0,179,36));
    }//GEN-LAST:event_loginBtnTxtMouseEntered

    private void loginBtnTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginBtnTxtMouseClicked

        String url = urlImagen.getText();
        registraEmpleado(url);
        
    }//GEN-LAST:event_loginBtnTxtMouseClicked

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
        if (txtCI.getText().isEmpty()) {
            txtCI.setText("Ingrese su cedula de identidad");
            txtCI.setForeground(new Color(51, 51, 51));
        }
    }//GEN-LAST:event_txtNombreMousePressed

    private void cerrar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cerrar1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cerrar1ActionPerformed

    private void txtCIMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCIMousePressed
        if (txtCI.getText().equals("Ingrese su cedula de identidad")) {
            txtCI.setText("");
            txtCI.setForeground(Color.black);
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
        if (txtNombre.getText().isEmpty()) {
            txtNombre.setText("Ingrese su nombre ");
            txtNombre.setForeground(new Color(51, 51, 51));
        }
    }//GEN-LAST:event_txtCIMousePressed

    private void txtCIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCIActionPerformed

    }//GEN-LAST:event_txtCIActionPerformed

    private void txtConectarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtConectarMouseClicked
        if (txtConectar.getText().equals("Conectar")) {
            try {
                conexion.arduinoRXTX(obtenerPuerto(), 9600, listener);
                txtConectar.setText("Desconectar");
                mensajeExito(" ¡Se conecto el Arduino con Exito! ");
            } catch (Exception ex) {
                //mensajeError(" ¡Error al conectar con el Arduino! ");
            }

        } else if (txtConectar.getText().equals("Desconectar")) {
            try {
                conexion.killArduinoConnection();
                txtConectar.setText("Conectar");
                mensajeExito(" ¡Se desconecto el Arduino con Exito! ");
            } catch (Exception ex) {
                //mensajeError(" ¡Error al desconectar el Arduino! ");
            }
        }
    }//GEN-LAST:event_txtConectarMouseClicked

    private void txtConectarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtConectarMouseEntered
        btnConectar.setBackground(new Color(0,179,36));
    }//GEN-LAST:event_txtConectarMouseEntered

    private void txtConectarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtConectarMouseExited
        btnConectar.setBackground(new Color(0,102,15));
    }//GEN-LAST:event_txtConectarMouseExited

    private void txtCIKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCIKeyTyped
        char validar = evt.getKeyChar();
        
        if(Character.isLetter(validar)){
            getToolkit().beep();
            evt.consume();
            
            mensajeAdvertencia("¡Debe Ingresar solo Numeros!");
        }
    }//GEN-LAST:event_txtCIKeyTyped

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
        char validar = evt.getKeyChar();
        
        if(Character.isDigit(validar)){
            getToolkit().beep();
            evt.consume();
            
            mensajeAdvertencia("¡Debe Ingresar solo Texto!");
        }
    }//GEN-LAST:event_txtNombreKeyTyped

    private void txtApellidosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidosKeyTyped
        char validar = evt.getKeyChar();
        
        if(Character.isDigit(validar)){
            getToolkit().beep();
            evt.consume();
            
            mensajeAdvertencia("¡Debe Ingresar solo Texto!");
        }
    }//GEN-LAST:event_txtApellidosKeyTyped

    private void txtUsuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsuarioKeyTyped
       
    }//GEN-LAST:event_txtUsuarioKeyTyped

    private void txtCelularKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelularKeyTyped
        char validar = evt.getKeyChar();
        
        if(Character.isLetter(validar)){
            getToolkit().beep();
            evt.consume();
            
            mensajeAdvertencia("¡Debe Ingresar solo Numeros!");
        }
    }//GEN-LAST:event_txtCelularKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Imagen;
    private javax.swing.JLabel RFID;
    private javax.swing.JRadioButton brFemenino;
    private javax.swing.JRadioButton brMasculino;
    private javax.swing.JPanel btnAbrirImagen;
    private javax.swing.JPanel btnConectar;
    private javax.swing.JPanel btnRegistrar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton cerrar1;
    private javax.swing.JComboBox<String> cmbCargo;
    private javax.swing.JComboBox<String> cmbCurso;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JLabel loginBtnTxt;
    private org.edisoncor.gui.panel.Panel panel2;
    private javax.swing.JLabel passLabel;
    private javax.swing.JLabel passLabel1;
    private javax.swing.JLabel passLabel2;
    private javax.swing.JLabel passLabel3;
    private javax.swing.JLabel passLabel4;
    private javax.swing.JLabel title;
    private javax.swing.JLabel txtAbrirImagen;
    private javax.swing.JTextField txtApellidos;
    private javax.swing.JTextField txtCI;
    private javax.swing.JTextField txtCelular;
    private javax.swing.JPasswordField txtCodigoRec;
    private javax.swing.JLabel txtConectar;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsuario;
    private javax.swing.JLabel urlImagen;
    private javax.swing.JLabel userLabel;
    private javax.swing.JLabel userLabel1;
    private javax.swing.JLabel userLabel2;
    // End of variables declaration//GEN-END:variables
}
