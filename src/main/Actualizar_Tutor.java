 
package main;

import Empleado_TablaDeSQL.Empleado;
import Empleado_TablaDeSQL.EmpleadoDAO_JDBC;
import Empleado_TablaDeSQL.Persona;
import Empleado_TablaDeSQL.Profesor;
import Estudiante_TablaDeSQL.Conexion;
import Estudiante_TablaDeSQL.EstudianteDAO_JDBC;
import Estudiante_TablaDeSQL.Tutor;
import com.sun.awt.AWTUtilities;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import jPanel.Tabla_Tutor;
import java.awt.Color;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import static java.awt.image.ImageObserver.HEIGHT;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import panamahitek.Arduino.PanamaHitek_Arduino;

public class Actualizar_Tutor extends javax.swing.JFrame {
    
    private boolean minimiza;
    int xMouse, yMouse;
    
    String rutaEstudiante = null;
    String rutaTutor1 = null;
    String rutaTutor2 = null;
    
    String arduinoConexion = null;

    Enumeration cuantosPuertos;//guarda la lista de los puertos Disponibles 
    HashMap portMap = new HashMap();//Guarda la info del arduino
    
    String codTarjeta = null;
    String aux;
    
    int id_persona;
    int id_tutor;

    int contadorestudiante;
    int contadortutor1;
    int contadortutor2;
    
    String tutor_update;

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
                        codTarjeta = aux;
                        System.out.println("codigo 1 = " + codTarjeta);
                        RFID.setText("TARJETA NUEVA REGISTRADA..!");
                    }
                }

            } catch (Exception ex) {
                mensajeError("Error al imprimir datos del Arduino");
            }
        }

    };

    public Actualizar_Tutor() {
        initComponents();
        
        this.setLocationRelativeTo(this);
        Shape forma = new RoundRectangle2D.Double(0, 0, this.getBounds().width, this.getBounds().height, 15, 15);
        AWTUtilities.setWindowShape(this, forma);
        
        tutor_update = Tabla_Tutor.tutor_update;
        InsertarDatosPersona(tutor_update);
        InsertarDatosTutor(tutor_update); 
    }
    
    public void actualizarTutor(String ruta) {
        int Tutor = 0, Persona = 0;

        Connection conexion = null;
        try {
            conexion = Conexion.getConection();
            if (conexion.getAutoCommit()) {
                //Esto hacemos para el AutoCommit no se inicialice solo ya que nosotros lo haremos manualmente 
                conexion.setAutoCommit(false);
            }

            EstudianteDAO_JDBC tutorDao = new EstudianteDAO_JDBC(conexion);
            
            Persona actualizarPersona = new Persona(id_persona, leerCI(), leerNombre(), leerApellidos());
            Tutor actualizarTutor = new Tutor(id_tutor, codTarjeta, leerCelular(), leerParentesco(), leerCI());
            
            Persona = tutorDao.actualizarPersona(actualizarPersona, ruta);
            Tutor = tutorDao.actualizarTutor(actualizarTutor);
            conexion.commit();
            mensajeExito(" Se Actualizo " + (Persona + Tutor) / 2 + " Usuario con exito.");
        } catch (SQLException ex) {
            mensajeError(" Hay algun error revise los datos ingresados");
            try {
                //Perso si sale un error entonces rollback se ejecutara y no se realizara ningun cambio
                conexion.rollback();
            } catch (SQLException ex1) {
                System.out.println("ex1 = " + ex1);
            }
        }

    }
    
    public void InsertarDatosPersona(String ci) {
        InputStream is;
        ImageIcon foto;
        try {
            String sql = "SELECT * FROM persona WHERE ci = '" + ci + "'";
            Connection cn = Conexion.getConection();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                id_persona = rs.getInt(1);
                txtCI.setText(rs.getString(2));
                txtNombre.setText(rs.getString(3));
                txtApellidos.setText(rs.getString(4));
                is = rs.getBinaryStream(5);
                if (is != null) {
                    try {
                        BufferedImage bi = ImageIO.read(is);
                        foto = new ImageIcon(bi);
                        Image img = foto.getImage();
                        //(ancho,alto,mensaje)
                        Image newimg = img.getScaledInstance(100, 90, java.awt.Image.SCALE_SMOOTH);
                        ImageIcon newicon = new ImageIcon(newimg);
                        Imagen.setIcon(newicon);
                    } catch (IOException ex) {
                        Imagen.setText("No Imagen");
                    }
                } else {
                    Imagen.setText("No Imagen");
                }
            }
            cn.close();
            st.close();
            rs.close();

        } catch (SQLException ex) {
            mensajeError(" Error al cargar Usuario");
        }
    }
    
    public void InsertarDatosTutor(String ci) {
        String parentesco;
        try {
            String sql = "SELECT * FROM tutor WHERE ci = '" + ci + "'";
            Connection cn = Conexion.getConection();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                id_tutor = rs.getInt(1);
                codTarjeta = rs.getString(5);  
                if(codTarjeta != null){
                    RFID.setText("TARJETA ACTUAL REGISTRADA..!");
                }
                txtCelular.setText(rs.getString(6));
                parentesco = rs.getString(7);
                switch (parentesco) {
                    case "Madre":
                        cmbparentesco.setSelectedIndex(0);
                        break;
                    case "Padre":
                        cmbparentesco.setSelectedIndex(1);
                        break;
                    case "Abuelo":
                        cmbparentesco.setSelectedIndex(2);
                        break;
                    case "Abuela":
                        cmbparentesco.setSelectedIndex(3);
                        break;
                    case "Tio":
                        cmbparentesco.setSelectedIndex(4);
                        break;
                    case "Tia":
                        cmbparentesco.setSelectedIndex(5);
                        break;
                    case "Otro":
                        cmbparentesco.setSelectedIndex(6);
                        break;
                    default:
                        break;
                }
            }
            cn.close();
            st.close();
            rs.close();

        } catch (SQLException ex) {
            mensajeError(" Error al cargar Usuario");
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
    
    int leerCelular() {
        try {
            String aux = txtCelular.getText().trim();
            int celular = Integer.parseInt(aux);
            return celular;
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
    
    String leerApellidos() {
        try {
            String apellidos = txtApellidos.getText().trim();
            return apellidos;
        } catch (Exception e) {
            return null;
        }
    }
    
    String leerParentesco() {
        return ((String) cmbparentesco.getSelectedItem());
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

        jPanel1 = new javax.swing.JPanel();
        panel2 = new org.edisoncor.gui.panel.Panel();
        jLabel4 = new javax.swing.JLabel();
        cerrar1 = new javax.swing.JButton();
        passLabel2 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        passLabel = new javax.swing.JLabel();
        txtApellidos = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        txtCelular = new javax.swing.JTextField();
        jSeparator7 = new javax.swing.JSeparator();
        btnAbrirImagenTutor1 = new javax.swing.JPanel();
        AbrirImagenTutor1 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        Imagen = new javax.swing.JLabel();
        loginBtn = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        RFID = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        cmbparentesco = new javax.swing.JComboBox<>();
        passLabel5 = new javax.swing.JLabel();
        txtCI = new javax.swing.JTextField();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        btnConectar = new javax.swing.JPanel();
        txtConectar = new javax.swing.JLabel();
        urlImagen = new javax.swing.JLabel();
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
        panel2.add(passLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 40, 140, 20));

        txtNombre.setBackground(new java.awt.Color(232, 255, 236));
        txtNombre.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtNombre.setForeground(new java.awt.Color(102, 102, 102));
        txtNombre.setText("Ingrese su nombre");
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
        panel2.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, 180, 30));

        jSeparator4.setForeground(new java.awt.Color(0, 0, 0));
        panel2.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 100, 190, 20));

        passLabel.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        passLabel.setForeground(new java.awt.Color(0, 0, 0));
        passLabel.setText("Apellidos Tutor");
        panel2.add(passLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 130, 140, -1));

        txtApellidos.setBackground(new java.awt.Color(232, 255, 236));
        txtApellidos.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtApellidos.setForeground(new java.awt.Color(102, 102, 102));
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
        panel2.add(txtApellidos, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 160, 170, 30));

        jSeparator5.setForeground(new java.awt.Color(0, 0, 0));
        panel2.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 190, 190, 20));

        jLabel5.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Nro. Cel. Tutor");
        panel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, 150, -1));

        txtCelular.setBackground(new java.awt.Color(232, 255, 236));
        txtCelular.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtCelular.setForeground(new java.awt.Color(102, 102, 102));
        txtCelular.setText("Ingrese su numero de telefono");
        txtCelular.setBorder(null);
        txtCelular.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtCelularMousePressed(evt);
            }
        });
        txtCelular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelularActionPerformed(evt);
            }
        });
        txtCelular.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelularKeyTyped(evt);
            }
        });
        panel2.add(txtCelular, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 180, 30));

        jSeparator7.setForeground(new java.awt.Color(0, 0, 0));
        panel2.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 190, 20));

        btnAbrirImagenTutor1.setBackground(new java.awt.Color(0, 102, 15));
        btnAbrirImagenTutor1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 15)));

        AbrirImagenTutor1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        AbrirImagenTutor1.setForeground(new java.awt.Color(255, 255, 255));
        AbrirImagenTutor1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        AbrirImagenTutor1.setText("Actualizar Imagen");
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
            .addComponent(AbrirImagenTutor1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
        );
        btnAbrirImagenTutor1Layout.setVerticalGroup(
            btnAbrirImagenTutor1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(AbrirImagenTutor1, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        panel2.add(btnAbrirImagenTutor1, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 50, 120, 30));

        jLabel9.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Imagen del Tutor");
        panel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 20, 160, 20));
        panel2.add(Imagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 90, 100, 90));

        loginBtn.setBackground(new java.awt.Color(0, 102, 15));
        loginBtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 15), 2));

        btnGuardar.setFont(new java.awt.Font("Roboto Black", 1, 14)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnGuardar.setText("ACTUALIZAR");
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

        panel2.add(loginBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 240, 110, -1));

        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Tarjeta RFID Tutor");
        panel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 230, 120, -1));

        RFID.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        RFID.setForeground(new java.awt.Color(204, 0, 0));
        RFID.setText("NO REGISTRADO");
        panel2.add(RFID, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 250, 300, 30));

        jLabel13.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("Parentesco Tutor ");
        panel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 250, 140, -1));

        cmbparentesco.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Madre", "Padre", "Abuelo", "Abuela", "Tio", "Tia", "Otro" }));
        cmbparentesco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbparentescoActionPerformed(evt);
            }
        });
        panel2.add(cmbparentesco, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 250, -1, -1));

        passLabel5.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        passLabel5.setForeground(new java.awt.Color(0, 0, 0));
        passLabel5.setText("CI. Tutor");
        panel2.add(passLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 120, 20));

        txtCI.setBackground(new java.awt.Color(232, 255, 236));
        txtCI.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtCI.setForeground(new java.awt.Color(102, 102, 102));
        txtCI.setText("Ingrese su cedula de identidad");
        txtCI.setBorder(null);
        txtCI.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtCIMousePressed(evt);
            }
        });
        txtCI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCIKeyTyped(evt);
            }
        });
        panel2.add(txtCI, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 180, 30));

        jSeparator6.setForeground(new java.awt.Color(0, 0, 0));
        panel2.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 190, 20));

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
        panel2.add(urlImagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 190, 280, 30));

        jPanel1.add(panel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 780, 310));

        title.setFont(new java.awt.Font("Cambria", 1, 36)); // NOI18N
        title.setForeground(new java.awt.Color(0, 52, 0));
        title.setText("ACTUALIZAR TUTOR");
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
    
    private void cerrar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cerrar1ActionPerformed

    }//GEN-LAST:event_cerrar1ActionPerformed

    private void txtNombreMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNombreMousePressed
        if (txtNombre.getText().equals("Ingrese su nombre")) {
            txtNombre.setText("");
            txtNombre.setForeground(Color.black);
        }
        if (txtApellidos.getText().isEmpty()) {
            txtApellidos.setText("Ingrese sus apellidos");
            txtApellidos.setForeground(Color.gray);
        }
        if (txtCelular.getText().isEmpty()) {
            txtCelular.setText("Ingrese su numero de telefono");
            txtCelular.setForeground(Color.gray);
        }
        if (txtCI.getText().isEmpty()) {
            txtCI.setText("Ingrese su cedula de identidad");
            txtCI.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txtNombreMousePressed

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
        char validar = evt.getKeyChar();

        if (Character.isDigit(validar)) {
            getToolkit().beep();
            evt.consume();

            mensajeAdvertencia("¡Debe Ingresar solo Texto!");
        }
    }//GEN-LAST:event_txtNombreKeyTyped

    private void txtApellidosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtApellidosMousePressed
        if (txtApellidos.getText().equals("Ingrese sus apellidos")) {
            txtApellidos.setText("");
            txtApellidos.setForeground(Color.black);
        }
        if (txtNombre.getText().isEmpty()) {
            txtNombre.setText("Ingrese su nombre");
            txtNombre.setForeground(Color.gray);
        }
        if (txtCelular.getText().isEmpty()) {
            txtCelular.setText("Ingrese su numero de telefono");
            txtCelular.setForeground(Color.gray);
        }
        if (txtCI.getText().isEmpty()) {
            txtCI.setText("Ingrese su cedula de identidad");
            txtCI.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txtApellidosMousePressed

    private void txtApellidosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidosKeyTyped
        char validar = evt.getKeyChar();

        if (Character.isDigit(validar)) {
            getToolkit().beep();
            evt.consume();

            mensajeAdvertencia("¡Debe Ingresar solo Texto!");
        }
    }//GEN-LAST:event_txtApellidosKeyTyped

    private void txtCelularMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCelularMousePressed
        if (txtCelular.getText().equals("Ingrese su numero de telefono")) {
            txtCelular.setText("");
            txtCelular.setForeground(Color.black);
        }
        if (txtNombre.getText().isEmpty()) {
            txtNombre.setText("Ingrese su nombre");
            txtNombre.setForeground(Color.gray);
        }
        if (txtApellidos.getText().isEmpty()) {
            txtApellidos.setText("Ingrese sus apellidos");
            txtApellidos.setForeground(Color.gray);
        }
        if (txtCI.getText().isEmpty()) {
            txtCI.setText("Ingrese su cedula de identidad");
            txtCI.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txtCelularMousePressed

    private void txtCelularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelularActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelularActionPerformed

    private void txtCelularKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelularKeyTyped
        char validar = evt.getKeyChar();

        if (Character.isLetter(validar)) {
            getToolkit().beep();
            evt.consume();

            mensajeAdvertencia("¡Debe Ingresar solo Numeros!");
        }
    }//GEN-LAST:event_txtCelularKeyTyped

    private void AbrirImagenTutor1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AbrirImagenTutor1MouseClicked
        JFileChooser j = new JFileChooser();
        j.setCurrentDirectory(new File("Imagenes/"));
        int ap = j.showOpenDialog(this);

        if (ap == JFileChooser.APPROVE_OPTION) {
            rutaTutor1 = j.getSelectedFile().getAbsolutePath();
            Imagen.setIcon(new ImageIcon(rutaTutor1));
        }
    }//GEN-LAST:event_AbrirImagenTutor1MouseClicked

    private void AbrirImagenTutor1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AbrirImagenTutor1MouseEntered
        btnAbrirImagenTutor1.setBackground(new Color(0, 179, 36));
    }//GEN-LAST:event_AbrirImagenTutor1MouseEntered

    private void AbrirImagenTutor1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AbrirImagenTutor1MouseExited
        btnAbrirImagenTutor1.setBackground(new Color(0, 102, 15));
    }//GEN-LAST:event_AbrirImagenTutor1MouseExited

    private void btnGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseClicked
        String url = null;
        url = urlImagen.getText();
        actualizarTutor(url);
    }//GEN-LAST:event_btnGuardarMouseClicked

    private void btnGuardarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseEntered
        loginBtn.setBackground(new Color(0, 179, 36));
    }//GEN-LAST:event_btnGuardarMouseEntered

    private void btnGuardarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseExited
        loginBtn.setBackground(new Color(0, 102, 15));
    }//GEN-LAST:event_btnGuardarMouseExited

    private void cmbparentescoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbparentescoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbparentescoActionPerformed

    private void txtCIMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCIMousePressed
        if (txtCI.getText().equals("Ingrese su cedula de identidad")) {
            txtCI.setText("");
            txtCI.setForeground(Color.black);
        }
        if (txtNombre.getText().isEmpty()) {
            txtNombre.setText("Ingrese su nombre");
            txtNombre.setForeground(Color.gray);
        }
        if (txtApellidos.getText().isEmpty()) {
            txtApellidos.setText("Ingrese sus apellidos");
            txtApellidos.setForeground(Color.gray);
        }
        if (txtCelular.getText().isEmpty()) {
            txtCelular.setText("Ingrese su numero de telefono");
            txtCelular.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txtCIMousePressed

    private void txtCIKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCIKeyTyped
        char validar = evt.getKeyChar();

        if (Character.isLetter(validar)) {
            getToolkit().beep();
            evt.consume();

            mensajeAdvertencia("¡Debe Ingresar solo Numeros!");
        }
    }//GEN-LAST:event_txtCIKeyTyped

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

    private void TitleMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TitleMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_TitleMouseDragged

    private void TitleMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TitleMousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_TitleMousePressed

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
        txtMinimizar.setBackground(new Color(149,255,179));
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
        txtSalir.setBackground(new Color(149,255,179));
        btnSalir.setForeground(Color.black);
    }//GEN-LAST:event_btnSalirMouseExited

    
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Actualizar_Tutor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Actualizar_Tutor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Actualizar_Tutor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Actualizar_Tutor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Actualizar_Tutor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AbrirImagenTutor1;
    private javax.swing.JLabel Imagen;
    private javax.swing.JLabel RFID;
    private javax.swing.JPanel Title;
    private javax.swing.JPanel btnAbrirImagenTutor1;
    private javax.swing.JPanel btnConectar;
    private javax.swing.JLabel btnGuardar;
    private javax.swing.JLabel btnMinimizar;
    private javax.swing.JLabel btnSalir;
    private javax.swing.JButton cerrar1;
    private javax.swing.JComboBox<String> cmbparentesco;
    private javax.swing.JLabel favicon;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JPanel loginBtn;
    private org.edisoncor.gui.panel.Panel panel2;
    private javax.swing.JLabel passLabel;
    private javax.swing.JLabel passLabel2;
    private javax.swing.JLabel passLabel5;
    private javax.swing.JLabel title;
    private javax.swing.JTextField txtApellidos;
    private javax.swing.JTextField txtCI;
    private javax.swing.JTextField txtCelular;
    private javax.swing.JLabel txtConectar;
    private javax.swing.JPanel txtMinimizar;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JPanel txtSalir;
    private javax.swing.JLabel urlImagen;
    // End of variables declaration//GEN-END:variables
}
