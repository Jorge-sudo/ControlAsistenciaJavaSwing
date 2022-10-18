package main;

import Empleado_TablaDeSQL.Persona;
import Estudiante_TablaDeSQL.*;
import static Estudiante_TablaDeSQL.Conexion.close;
import static Estudiante_TablaDeSQL.Conexion.getConection;
import com.sun.awt.AWTUtilities;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import jPanel.Tabla_Tutor;
import java.awt.Color;
import java.sql.*;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import static java.awt.image.ImageObserver.HEIGHT;
import java.io.File;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import panamahitek.Arduino.PanamaHitek_Arduino;

public class Registrar_Tutor2 extends javax.swing.JFrame {

    private boolean minimiza;
    int xMouse, yMouse;

    String rutaTutor2 = null;

    String arduinoConexion = null;

    Enumeration cuantosPuertos;//guarda la lista de los puertos Disponibles 
    HashMap portMap = new HashMap();//Guarda la info del arduino

    String codTarjeta2;
    String aux;

    Long ci_estudiante;

    PanamaHitek_Arduino conexion = new PanamaHitek_Arduino();
    SerialPortEventListener listener = new SerialPortEventListener() {
        @Override
        public void serialEvent(SerialPortEvent spe) {
            try {

                if (conexion.isMessageAvailable()) {
                    aux = conexion.printMessage().trim();
                    if (aux.equalsIgnoreCase("Control Inicializado...")) {
                        arduinoConexion = aux;
                    } else {
                        codTarjeta2 = aux;
                        System.out.println("codigo 1 = " + codTarjeta2);
                        RFID1.setText(" REGISTRADO..!");
                    }
                }

            } catch (Exception ex) {
                mensajeError("Error al imprimir datos del Arduino");
            }
        }

    };

    public Registrar_Tutor2() {
        initComponents();

        this.setLocationRelativeTo(this);
        Shape forma = new RoundRectangle2D.Double(0, 0, this.getBounds().width, this.getBounds().height, 15, 15);
        AWTUtilities.setWindowShape(this, forma);

        ci_estudiante = Actualizar_Estudiante.ci_estudiante;
        String ci = Long.toString(ci_estudiante);
        txtCI.setText(ci);

    }

    int registroTutor2() {
        int a = 0;
        int persona;
        int tutor;
        if (leerCITutor2() == -99) {
            mensajeAdvertencia("¡Ingrese la Cedula de Identidad del Tutor 1!");
        } else if ("Ingrese su nombre".equals(leerNombreT2()) || "".equals(leerNombreT2())) {
            mensajeAdvertencia("¡Ingrese el Nombre del Tutor !");
        } else if ("Ingrese sus apellidos".equals(leerApellidoT2()) || "".equals(leerApellidoT2())) {
            mensajeAdvertencia("¡Ingrese el Apellido del Tutor !");
        } else if (leerCelularT2() == -99) {
            mensajeAdvertencia("¡Ingrese el Numero de Celular del Tutor 1!");
        } else if (this.rutaTutor2 == null) {
            mensajeAdvertencia("¡Ingrese la Fotografia del Tutor !");
        } else if (arduinoConexion == null) {
            mensajeAdvertencia("¡Conecte el Arduino para registrar la tarjeta RFID!");
        } else if ("NO REGISTRADO".equals(RFID1.getText().trim())) {
            mensajeAdvertencia("¡Pase la tarjeta RFID del Tutor 1!");
        } else {

            Connection conexion = null;
            try {
                conexion = Conexion.getConection();
                if (conexion.getAutoCommit()) {
                    //Esto hacemos para el AutoCommit no se inicialice solo ya que nosotros lo haremos manualmente 
                    conexion.setAutoCommit(false);
                }

                EstudianteDAO_JDBC estudianteDao = new EstudianteDAO_JDBC(conexion);

                Tutor tutor2 = new Tutor(leerCITutor2(), leerNombreT2(), leerApellidoT2(), leerCI(), 2, codTarjeta2,
                        leerCelularT2(), leerParentescoT2());
                persona = estudianteDao.insertarPersona(tutor2, rutaTutor2);
                tutor = estudianteDao.insertarTutor(tutor2);

                conexion.commit();
                mensajeExito(" Se registro " + (persona + tutor) / 2 + " Tutor.");
                a = 1;
            } catch (SQLException ex) {
                mensajeError(" Hay algun error revise los datos ingresados " + ex);
                try {
                    //Perso si sale un error entonces rollback se ejecutara y no se realizara ningun cambio
                    conexion.rollback();
                } catch (SQLException ex1) {
                    ex1.printStackTrace(System.out);
                }
            }

        }

        return a;
    }

    long leerCI() {
        try {
            String aux = txtCI.getText().trim();
            long codigo = Integer.parseInt(aux);
            return codigo;
        } catch (NumberFormatException e) {
            return -99;
        }
    }

    String leerParentescoT2() {
        return ((String) parentesco1.getSelectedItem());
    }

    long leerCITutor2() {
        try {
            String aux = txtCITutor1.getText().trim();
            long codigo = Integer.parseInt(aux);
            return codigo;
        } catch (NumberFormatException e) {
            return -99;
        }
    }

    String leerApellidoT2() {
        try {
            String nombre = txt1TutorApellidos.getText().trim();
            return nombre;
        } catch (Exception e) {
            return null;
        }
    }

    String leerNombreT2() {
        try {
            String nombre = txt1TutorNombre.getText().trim();
            return nombre;
        } catch (Exception e) {
            return null;
        }
    }

    int leerCelularT2() {
        try {
            String aux = txt1TutorCelular.getText().trim();
            int codigo = Integer.parseInt(aux);
            return codigo;
        } catch (Exception e) {
            return -99;
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

    void actualiar_Estudiante() {
        String aux_;
        long ci = ci_estudiante;
        Connection conn;
        PreparedStatement stmt;
        int registros = 0;
        
        try {
            String SQL_UPDATE_ESTUDIANTE = "UPDATE estudiante\n"
                    + "	SET tutor = ?\n"
                    + "	WHERE ci = ?;";
            conn = Conexion.getConection();
            stmt = conn.prepareStatement(SQL_UPDATE_ESTUDIANTE);
            //Sustituimos los parametros de la misma forma que insertar

            stmt.setInt(1, 2);
            stmt.setLong(2, ci);

            //Esto es para que actualice el estado de la base de datos 
            registros = stmt.executeUpdate();
        } catch (SQLException ex) {
            mensajeError("Error " + ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        panel2 = new org.edisoncor.gui.panel.Panel();
        jLabel4 = new javax.swing.JLabel();
        cerrar1 = new javax.swing.JButton();
        passLabel2 = new javax.swing.JLabel();
        txt1TutorNombre = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        passLabel = new javax.swing.JLabel();
        txt1TutorApellidos = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        txt1TutorCelular = new javax.swing.JTextField();
        jSeparator7 = new javax.swing.JSeparator();
        btnAbrirImagenTutor1 = new javax.swing.JPanel();
        AbrirImagenTutor1 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        ImagenTutor1 = new javax.swing.JLabel();
        loginBtn = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        RFID1 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        parentesco1 = new javax.swing.JComboBox<>();
        passLabel5 = new javax.swing.JLabel();
        txtCITutor1 = new javax.swing.JTextField();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        btnConectar = new javax.swing.JPanel();
        txtConectar = new javax.swing.JLabel();
        urlImagen = new javax.swing.JLabel();
        txtCI = new javax.swing.JTextField();
        userLabel1 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
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

        passLabel2.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        passLabel2.setForeground(new java.awt.Color(0, 0, 0));
        passLabel2.setText("Nombre Tutor");
        panel2.add(passLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 20, 140, 20));

        txt1TutorNombre.setBackground(new java.awt.Color(232, 255, 236));
        txt1TutorNombre.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txt1TutorNombre.setForeground(new java.awt.Color(102, 102, 102));
        txt1TutorNombre.setText("Ingrese su nombre");
        txt1TutorNombre.setBorder(null);
        txt1TutorNombre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txt1TutorNombreMousePressed(evt);
            }
        });
        txt1TutorNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt1TutorNombreKeyTyped(evt);
            }
        });
        panel2.add(txt1TutorNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 50, 180, 30));

        jSeparator4.setForeground(new java.awt.Color(0, 0, 0));
        panel2.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 80, 190, 20));

        passLabel.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        passLabel.setForeground(new java.awt.Color(0, 0, 0));
        passLabel.setText("Apellidos Tutor");
        panel2.add(passLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 110, 140, -1));

        txt1TutorApellidos.setBackground(new java.awt.Color(232, 255, 236));
        txt1TutorApellidos.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txt1TutorApellidos.setForeground(new java.awt.Color(102, 102, 102));
        txt1TutorApellidos.setText("Ingrese sus apellidos");
        txt1TutorApellidos.setBorder(null);
        txt1TutorApellidos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txt1TutorApellidosMousePressed(evt);
            }
        });
        txt1TutorApellidos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt1TutorApellidosKeyTyped(evt);
            }
        });
        panel2.add(txt1TutorApellidos, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 140, 170, 30));

        jSeparator5.setForeground(new java.awt.Color(0, 0, 0));
        panel2.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 170, 190, 20));

        jLabel5.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Nro. Cel. Tutor");
        panel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 200, 150, -1));

        txt1TutorCelular.setBackground(new java.awt.Color(232, 255, 236));
        txt1TutorCelular.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txt1TutorCelular.setForeground(new java.awt.Color(102, 102, 102));
        txt1TutorCelular.setText("Ingrese su numero de telefono");
        txt1TutorCelular.setBorder(null);
        txt1TutorCelular.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txt1TutorCelularMousePressed(evt);
            }
        });
        txt1TutorCelular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt1TutorCelularActionPerformed(evt);
            }
        });
        txt1TutorCelular.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt1TutorCelularKeyTyped(evt);
            }
        });
        panel2.add(txt1TutorCelular, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, 180, 30));

        jSeparator7.setForeground(new java.awt.Color(0, 0, 0));
        panel2.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, 190, 20));

        btnAbrirImagenTutor1.setBackground(new java.awt.Color(0, 102, 15));
        btnAbrirImagenTutor1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 15)));

        AbrirImagenTutor1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        AbrirImagenTutor1.setForeground(new java.awt.Color(255, 255, 255));
        AbrirImagenTutor1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        AbrirImagenTutor1.setText("Abrir Imagen");
        AbrirImagenTutor1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AbrirImagenTutor1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AbrirImagenTutor1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                AbrirImagenTutor1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                AbrirImagenTutor1MouseExited(evt);
            }
        });

        javax.swing.GroupLayout btnAbrirImagenTutor1Layout = new javax.swing.GroupLayout(btnAbrirImagenTutor1);
        btnAbrirImagenTutor1.setLayout(btnAbrirImagenTutor1Layout);
        btnAbrirImagenTutor1Layout.setHorizontalGroup(
            btnAbrirImagenTutor1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(AbrirImagenTutor1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
        );
        btnAbrirImagenTutor1Layout.setVerticalGroup(
            btnAbrirImagenTutor1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(AbrirImagenTutor1, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        panel2.add(btnAbrirImagenTutor1, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 50, 90, 30));

        jLabel9.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Imagen del Tutor");
        panel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 20, 160, 20));
        panel2.add(ImagenTutor1, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 90, 100, 90));

        loginBtn.setBackground(new java.awt.Color(0, 102, 15));
        loginBtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 15), 2));

        btnGuardar.setFont(new java.awt.Font("Roboto Black", 1, 14)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnGuardar.setText("REGISTRAR");
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
            .addComponent(btnGuardar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
        );
        loginBtnLayout.setVerticalGroup(
            loginBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        panel2.add(loginBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 250, 110, -1));

        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Tarjeta RFID Tutor");
        panel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 250, 120, -1));

        RFID1.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        RFID1.setForeground(new java.awt.Color(204, 0, 0));
        RFID1.setText("NO REGISTRADO");
        panel2.add(RFID1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 270, 160, 30));

        jLabel13.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("Parentesco Tutor ");
        panel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 200, 140, -1));

        parentesco1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Madre", "Padre", "Abuelo", "Abuela", "Tio", "Tia", "Otro" }));
        parentesco1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                parentesco1ActionPerformed(evt);
            }
        });
        panel2.add(parentesco1, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 200, -1, -1));

        passLabel5.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        passLabel5.setForeground(new java.awt.Color(0, 0, 0));
        passLabel5.setText("CI. Tutor");
        panel2.add(passLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, 120, 20));

        txtCITutor1.setBackground(new java.awt.Color(232, 255, 236));
        txtCITutor1.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtCITutor1.setForeground(new java.awt.Color(102, 102, 102));
        txtCITutor1.setText("Ingrese su cedula de identidad");
        txtCITutor1.setBorder(null);
        txtCITutor1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtCITutor1MousePressed(evt);
            }
        });
        txtCITutor1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCITutor1KeyTyped(evt);
            }
        });
        panel2.add(txtCITutor1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 180, 30));

        jSeparator6.setForeground(new java.awt.Color(0, 0, 0));
        panel2.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 190, 20));

        jLabel8.setFont(new java.awt.Font("Arial Black", 1, 13)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 0, 0));
        jLabel8.setText("ARDUINO :");
        panel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 20, 100, 20));

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

        panel2.add(btnConectar, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 60, 90, 30));

        urlImagen.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        urlImagen.setForeground(new java.awt.Color(51, 51, 51));
        panel2.add(urlImagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 190, 270, 20));

        txtCI.setEditable(false);
        txtCI.setBackground(new java.awt.Color(232, 255, 236));
        txtCI.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtCI.setForeground(new java.awt.Color(255, 0, 0));
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
        panel2.add(txtCI, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 180, 30));

        userLabel1.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        userLabel1.setForeground(new java.awt.Color(0, 0, 0));
        userLabel1.setText("CI. del Estudiante");
        panel2.add(userLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, -1, -1));

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        panel2.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 190, 10));

        jPanel1.add(panel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 780, 320));

        title.setFont(new java.awt.Font("Cambria", 1, 36)); // NOI18N
        title.setForeground(new java.awt.Color(0, 52, 0));
        title.setText("REGISTRAR TUTOR 2");
        jPanel1.add(title, new org.netbeans.lib.awtextra.AbsoluteConstraints(105, 46, -1, 30));

        favicon.setFont(new java.awt.Font("Roboto Black", 1, 24)); // NOI18N
        favicon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/images/favicon_1.png"))); // NOI18N
        jPanel1.add(favicon, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 46, -1, 30));

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

        Title.add(txtMinimizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 0, 30, -1));

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

        Title.add(txtSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 0, -1, -1));

        jPanel1.add(Title, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 830, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cerrar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cerrar1ActionPerformed

    }//GEN-LAST:event_cerrar1ActionPerformed

    private void txt1TutorNombreMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt1TutorNombreMousePressed
        if (txt1TutorNombre.getText().equals("Ingrese su nombre")) {
            txt1TutorNombre.setText("");
            txt1TutorNombre.setForeground(Color.black);
        }
        if (txt1TutorApellidos.getText().isEmpty()) {
            txt1TutorApellidos.setText("Ingrese sus apellidos");
            txt1TutorApellidos.setForeground(Color.gray);
        }
        if (txt1TutorCelular.getText().isEmpty()) {
            txt1TutorCelular.setText("Ingrese su numero de telefono");
            txt1TutorCelular.setForeground(Color.gray);
        }
        if (txtCITutor1.getText().isEmpty()) {
            txtCITutor1.setText("Ingrese su cedula de identidad");
            txtCITutor1.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txt1TutorNombreMousePressed

    private void txt1TutorNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt1TutorNombreKeyTyped
        char validar = evt.getKeyChar();

        if (Character.isDigit(validar)) {
            getToolkit().beep();
            evt.consume();

            mensajeAdvertencia("¡Debe Ingresar solo Texto!");
        }
    }//GEN-LAST:event_txt1TutorNombreKeyTyped

    private void txt1TutorApellidosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt1TutorApellidosMousePressed
        if (txt1TutorApellidos.getText().equals("Ingrese sus apellidos")) {
            txt1TutorApellidos.setText("");
            txt1TutorApellidos.setForeground(Color.black);
        }
        if (txt1TutorNombre.getText().isEmpty()) {
            txt1TutorNombre.setText("Ingrese su nombre");
            txt1TutorNombre.setForeground(Color.gray);
        }
        if (txt1TutorCelular.getText().isEmpty()) {
            txt1TutorCelular.setText("Ingrese su numero de telefono");
            txt1TutorCelular.setForeground(Color.gray);
        }
        if (txtCITutor1.getText().isEmpty()) {
            txtCITutor1.setText("Ingrese su cedula de identidad");
            txtCITutor1.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txt1TutorApellidosMousePressed

    private void txt1TutorApellidosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt1TutorApellidosKeyTyped
        char validar = evt.getKeyChar();

        if (Character.isDigit(validar)) {
            getToolkit().beep();
            evt.consume();

            mensajeAdvertencia("¡Debe Ingresar solo Texto!");
        }
    }//GEN-LAST:event_txt1TutorApellidosKeyTyped

    private void txt1TutorCelularMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt1TutorCelularMousePressed
        if (txt1TutorCelular.getText().equals("Ingrese su numero de telefono")) {
            txt1TutorCelular.setText("");
            txt1TutorCelular.setForeground(Color.black);
        }
        if (txt1TutorNombre.getText().isEmpty()) {
            txt1TutorNombre.setText("Ingrese su nombre");
            txt1TutorNombre.setForeground(Color.gray);
        }
        if (txt1TutorApellidos.getText().isEmpty()) {
            txt1TutorApellidos.setText("Ingrese sus apellidos");
            txt1TutorApellidos.setForeground(Color.gray);
        }
        if (txtCITutor1.getText().isEmpty()) {
            txtCITutor1.setText("Ingrese su cedula de identidad");
            txtCITutor1.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txt1TutorCelularMousePressed

    private void txt1TutorCelularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt1TutorCelularActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt1TutorCelularActionPerformed

    private void txt1TutorCelularKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt1TutorCelularKeyTyped
        char validar = evt.getKeyChar();

        if (Character.isLetter(validar)) {
            getToolkit().beep();
            evt.consume();

            mensajeAdvertencia("¡Debe Ingresar solo Numeros!");
        }
    }//GEN-LAST:event_txt1TutorCelularKeyTyped

    private void AbrirImagenTutor1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AbrirImagenTutor1MouseClicked
        JFileChooser j = new JFileChooser();
        j.setCurrentDirectory(new File("Imagenes/"));
        int ap = j.showOpenDialog(this);

        if (ap == JFileChooser.APPROVE_OPTION) {
            rutaTutor2 = j.getSelectedFile().getAbsolutePath();
            ImagenTutor1.setIcon(new ImageIcon(rutaTutor2));
            urlImagen.setText(rutaTutor2);
        }
    }//GEN-LAST:event_AbrirImagenTutor1MouseClicked

    private void AbrirImagenTutor1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AbrirImagenTutor1MouseEntered
        btnAbrirImagenTutor1.setBackground(new Color(0, 179, 36));
    }//GEN-LAST:event_AbrirImagenTutor1MouseEntered

    private void AbrirImagenTutor1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AbrirImagenTutor1MouseExited
        btnAbrirImagenTutor1.setBackground(new Color(0, 102, 15));
    }//GEN-LAST:event_AbrirImagenTutor1MouseExited

    private void btnGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseClicked
        try {
            int a = registroTutor2();
            if (a == 1) {
                actualiar_Estudiante();
            }
            this.dispose();
        } catch (Exception e) {
            mensajeError(" Error al registrar datos " +e);
        }
    }//GEN-LAST:event_btnGuardarMouseClicked

    private void btnGuardarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseEntered
        loginBtn.setBackground(new Color(0, 179, 36));
    }//GEN-LAST:event_btnGuardarMouseEntered

    private void btnGuardarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseExited
        loginBtn.setBackground(new Color(0, 102, 15));
    }//GEN-LAST:event_btnGuardarMouseExited

    private void parentesco1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_parentesco1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_parentesco1ActionPerformed

    private void txtCITutor1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCITutor1MousePressed
        if (txtCITutor1.getText().equals("Ingrese su cedula de identidad")) {
            txtCITutor1.setText("");
            txtCITutor1.setForeground(Color.black);
        }
        if (txt1TutorNombre.getText().isEmpty()) {
            txt1TutorNombre.setText("Ingrese su nombre");
            txt1TutorNombre.setForeground(Color.gray);
        }
        if (txt1TutorApellidos.getText().isEmpty()) {
            txt1TutorApellidos.setText("Ingrese sus apellidos");
            txt1TutorApellidos.setForeground(Color.gray);
        }
        if (txt1TutorCelular.getText().isEmpty()) {
            txt1TutorCelular.setText("Ingrese su numero de telefono");
            txt1TutorCelular.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txtCITutor1MousePressed

    private void txtCITutor1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCITutor1KeyTyped
        char validar = evt.getKeyChar();

        if (Character.isLetter(validar)) {
            getToolkit().beep();
            evt.consume();

            mensajeAdvertencia("¡Debe Ingresar solo Numeros!");
        }
    }//GEN-LAST:event_txtCITutor1KeyTyped

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
        btnConectar.setBackground(new Color(0, 179, 36));
    }//GEN-LAST:event_txtConectarMouseEntered

    private void txtConectarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtConectarMouseExited
        btnConectar.setBackground(new Color(0, 102, 15));
    }//GEN-LAST:event_txtConectarMouseExited

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
        txtMinimizar.setBackground(new Color(242,242,242));
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
        txtSalir.setBackground(new Color(242,242,242));
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

    private void txtCIMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCIMousePressed

        if (txt1TutorNombre.getText().isEmpty()) {
            txt1TutorNombre.setText("Ingrese su nombre");
            txt1TutorNombre.setForeground(Color.gray);
        }
        if (txt1TutorApellidos.getText().isEmpty()) {
            txt1TutorApellidos.setText("Ingrese sus apellidos");
            txt1TutorApellidos.setForeground(Color.gray);
        }
        if (txt1TutorCelular.getText().isEmpty()) {
            txt1TutorCelular.setText("Ingrese su numero de telefono");
            txt1TutorCelular.setForeground(Color.gray);
        }
        if (txtCITutor1.getText().isEmpty()) {
            txtCITutor1.setText("Ingrese su cedula de identidad");
            txtCITutor1.setForeground(Color.gray);
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

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Registrar_Tutor2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Registrar_Tutor2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Registrar_Tutor2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Registrar_Tutor2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Registrar_Tutor2().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AbrirImagenTutor1;
    private javax.swing.JLabel ImagenTutor1;
    private javax.swing.JLabel RFID1;
    private javax.swing.JPanel Title;
    private javax.swing.JPanel btnAbrirImagenTutor1;
    private javax.swing.JPanel btnConectar;
    private javax.swing.JLabel btnGuardar;
    private javax.swing.JLabel btnMinimizar;
    private javax.swing.JLabel btnSalir;
    private javax.swing.JButton cerrar1;
    private javax.swing.JLabel favicon;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JPanel loginBtn;
    private org.edisoncor.gui.panel.Panel panel2;
    private javax.swing.JComboBox<String> parentesco1;
    private javax.swing.JLabel passLabel;
    private javax.swing.JLabel passLabel2;
    private javax.swing.JLabel passLabel5;
    private javax.swing.JLabel title;
    private javax.swing.JTextField txt1TutorApellidos;
    private javax.swing.JTextField txt1TutorCelular;
    private javax.swing.JTextField txt1TutorNombre;
    private javax.swing.JTextField txtCI;
    private javax.swing.JTextField txtCITutor1;
    private javax.swing.JLabel txtConectar;
    private javax.swing.JPanel txtMinimizar;
    private javax.swing.JPanel txtSalir;
    private javax.swing.JLabel urlImagen;
    private javax.swing.JLabel userLabel1;
    // End of variables declaration//GEN-END:variables
}
