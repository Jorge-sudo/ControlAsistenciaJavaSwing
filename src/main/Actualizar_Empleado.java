package main;

import Empleado_TablaDeSQL.Empleado;
import Empleado_TablaDeSQL.EmpleadoDAO_JDBC;
import Empleado_TablaDeSQL.Persona;
import Empleado_TablaDeSQL.Profesor;
import java.awt.Color;
import java.io.File;
import java.sql.*;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import Estudiante_TablaDeSQL.Conexion;
import com.sun.awt.AWTUtilities;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import jPanel.Tabla_Empleado;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import javax.imageio.ImageIO;
import panamahitek.Arduino.PanamaHitek_Arduino;

public class Actualizar_Empleado extends javax.swing.JFrame {

    String ruta = null;
    String user = "", empleado_update = "";
    private boolean minimiza;
    int xMouse, yMouse;

    boolean masculino = Tabla_Empleado.masculino;
    boolean femenino = Tabla_Empleado.femenino;

    int id_persona;
    int id_empleado;
    int id_profesor = 0;

    String codigoTarjeta = null;
    String aux;
    String arduinoConexion = null;
    Enumeration cuantosPuertos;//guarda la lista de los puertos Disponibles 
    HashMap portMap = new HashMap();//Guarda la info del arduino

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
                        codigoTarjeta = aux;
                        System.out.println(" Codigo = " + codigoTarjeta);
                        RFID.setText("TARJETA NUEVA REGISTRADA..!");
                    }

                }
            } catch (Exception ex) {
                mensajeError("Error al imprimir datos del Arduino");
            }
        }

    };

    public Actualizar_Empleado() {
        initComponents();
        empleado_update = Tabla_Empleado.empleado_update;
        this.setLocationRelativeTo(this);
        Shape forma = new RoundRectangle2D.Double(0, 0, this.getBounds().width, this.getBounds().height, 15, 15);
        AWTUtilities.setWindowShape(this, forma);
        InsertarDatosPersona(empleado_update);
        InsertarDatosEmpleado(empleado_update);
        InsertarDatosProfesor(empleado_update);
        System.out.println(" Codigo = " + codigoTarjeta);
    }

    public void InsertarDatosEmpleado(String ci) {
        String cargo;
        try {
            String sql = "SELECT * FROM empleado WHERE ci = '" + ci + "'";
            Connection cn = Conexion.getConection();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                id_empleado = rs.getInt(1);
                txtCI.setText(rs.getString(2));
                codigoTarjeta = rs.getString(3);
                if (codigoTarjeta != null) {
                    RFID.setText("TARJETA ACTUAL REGISTRADA..!");
                }
                txtUsuario.setText(rs.getString(4));
                txtPassword.setText(rs.getString(5));
                txtCodigoRec.setText(rs.getString(6));
                txtCelular.setText(rs.getString(7));
                cargo = rs.getString(9);
                switch (cargo) {
                    case "Secretario(a)":
                        cmbCargo.setSelectedIndex(0);
                        break;
                    case "Regente(a)":
                        cmbCargo.setSelectedIndex(1);
                        break;
                    case "Portero(a)":
                        cmbCargo.setSelectedIndex(2);
                        break;
                    case "Profesor(a)":
                        cmbCargo.setSelectedIndex(3);
                        break;
                    case "Director(a)":
                        cmbCargo.setSelectedIndex(4);
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

    public void InsertarDatosProfesor(String ci) {
        String aula;
        try {
            String sql = "SELECT * FROM profesor WHERE ci = '" + ci + "'";
            Connection cn = Conexion.getConection();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                id_profesor = rs.getInt(1);
                aula = rs.getString(3);
                switch (aula) {
                    case "Verde":
                        cmbAula.setSelectedIndex(0);
                        break;
                    case "Amarillo":
                        cmbAula.setSelectedIndex(1);
                        break;
                    case "Azul":
                        cmbAula.setSelectedIndex(2);
                        break;
                    case "Rosado":
                        cmbAula.setSelectedIndex(3);
                        break;
                    case "Rojo":
                        cmbAula.setSelectedIndex(4);
                        break;
                    case "Ninguno":
                        cmbAula.setSelectedIndex(5);
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

    public void actualizarEmpleado(String ruta) {
        int Empleado = 0, Persona = 0, Profesor = 0;

        Connection conexion = null;
        try {
            conexion = Conexion.getConection();
            if (conexion.getAutoCommit()) {
                //Esto hacemos para el AutoCommit no se inicialice solo ya que nosotros lo haremos manualmente 
                conexion.setAutoCommit(false);
            }

            EmpleadoDAO_JDBC empleadoDao = new EmpleadoDAO_JDBC(conexion);
            
            Persona actualizarPersona = new Persona(id_persona, leerCI(), leerNombre(), leerApellidos());

            Empleado actualizarEmpleado = new Empleado(id_empleado,leerCI(), codigoTarjeta, leerUsuario(), leerPassword()
                    ,leerCodRecuperacion(),leerCelular(), leerGenero(), leerCargo());
            
            Profesor profesor = new Profesor(id_profesor, leerCI(), leerAula());

            Persona = empleadoDao.actualizarPersona(actualizarPersona, ruta);
            Empleado = empleadoDao.actualizarEmpleado(actualizarEmpleado);
            if ("Profesor(a)".equals(leerCargo())) {
                Profesor = empleadoDao.actualizarProfesor(profesor);
                mensajeExito(" Se Actualizo " + (Empleado + Persona + Profesor) / 3 + " Usuario con exito.");
            } else {
                mensajeExito(" Se Actualizo " + (Empleado + Persona) / 2 + " Usuario con exito.");
            }
            conexion.commit();
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

    void limpiar() {
        txtPassword.setText(null);
        txtNombre.setText(null);
        txtApellidos.setText(null);
        brMasculino.setSelected(true);
        cmbAula.setSelectedIndex(0);
        txtCodigoRec.setText(null);
        txtUsuario.setText(null);
        txtCelular.setText(null);
        cmbAula.setSelectedIndex(0);
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

    String leerAula() {
        return ((String) cmbAula.getSelectedItem());
    }

    String leerPassword() {
        try {
            String codigo = txtPassword.getText().trim();
            return codigo;
        } catch (Exception e) {
            return null;
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
        txtSalir = new javax.swing.JPanel();
        btnSalir = new javax.swing.JLabel();
        txtMinimizar = new javax.swing.JPanel();
        btnMinimizar = new javax.swing.JLabel();
        Title = new javax.swing.JPanel();
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
        cmbAula = new javax.swing.JComboBox<>();
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
        brMasculino = new javax.swing.JRadioButton("MASCULINO",this.masculino);
        brFemenino = new javax.swing.JRadioButton("FEMENINO", this.femenino);
        txtCI = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        userLabel2 = new javax.swing.JLabel();
        btnConectar = new javax.swing.JPanel();
        txtConectar = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(242, 242, 242));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnSalirMousePressed(evt);
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

        jPanel1.add(txtSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 0, -1, -1));

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

        jPanel1.add(txtMinimizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 0, 30, -1));

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

        javax.swing.GroupLayout TitleLayout = new javax.swing.GroupLayout(Title);
        Title.setLayout(TitleLayout);
        TitleLayout.setHorizontalGroup(
            TitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 850, Short.MAX_VALUE)
        );
        TitleLayout.setVerticalGroup(
            TitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        jPanel1.add(Title, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 850, 40));

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
        title.setText("ACTUALIZAR EMPLEADO");
        panel2.add(title, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 580, 130));

        userLabel.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        userLabel.setForeground(new java.awt.Color(0, 0, 0));
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
        passLabel.setForeground(new java.awt.Color(0, 0, 0));
        passLabel.setText("APELLIDOS");
        panel2.add(passLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 300, -1, -1));

        btnRegistrar.setBackground(new java.awt.Color(0, 102, 15));
        btnRegistrar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 121, 10), 2));

        loginBtnTxt.setFont(new java.awt.Font("Roboto Condensed", 1, 14)); // NOI18N
        loginBtnTxt.setForeground(new java.awt.Color(255, 255, 255));
        loginBtnTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        loginBtnTxt.setText("ACTUALIZAR");
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
            .addComponent(loginBtnTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
        );
        btnRegistrarLayout.setVerticalGroup(
            btnRegistrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(loginBtnTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        panel2.add(btnRegistrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 480, 100, 40));

        passLabel1.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        passLabel1.setForeground(new java.awt.Color(0, 0, 0));
        passLabel1.setText("GENERO");
        panel2.add(passLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 380, -1, 30));

        cmbAula.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Verde", "Amarillo", "Azul", "Rosado", "Rojo", "Ninguno" }));
        cmbAula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbAulaActionPerformed(evt);
            }
        });
        panel2.add(cmbAula, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 470, -1, -1));

        jLabel3.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("ELEGIR FOTO :");
        panel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 180, 110, -1));

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
        passLabel2.setForeground(new java.awt.Color(0, 0, 0));
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
        userLabel1.setForeground(new java.awt.Color(0, 0, 0));
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
        passLabel3.setForeground(new java.awt.Color(0, 0, 0));
        passLabel3.setText("CODIGO DE RECUPERACION");
        panel2.add(passLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 300, -1, -1));

        jSeparator6.setForeground(new java.awt.Color(0, 0, 0));
        panel2.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 360, 200, 10));

        cmbCargo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Secretario(a)", "Regente(a)", "Portero(a)", "Profesor(a)", "Director(a)" }));
        cmbCargo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCargoActionPerformed(evt);
            }
        });
        panel2.add(cmbCargo, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 470, -1, -1));

        jLabel5.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("CARGO :");
        panel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 470, 60, 20));

        passLabel4.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        passLabel4.setForeground(new java.awt.Color(0, 0, 0));
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
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("CURSO:");
        panel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 470, 60, -1));

        btnAbrirImagen.setBackground(new java.awt.Color(0, 102, 15));
        btnAbrirImagen.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 15), 2));

        txtAbrirImagen.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtAbrirImagen.setForeground(new java.awt.Color(255, 255, 255));
        txtAbrirImagen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtAbrirImagen.setText("Actualizar Imagen");
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
            .addComponent(txtAbrirImagen, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
        );
        btnAbrirImagenLayout.setVerticalGroup(
            btnAbrirImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtAbrirImagen, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
        );

        panel2.add(btnAbrirImagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 210, 110, 30));

        urlImagen.setForeground(new java.awt.Color(0, 0, 0));
        panel2.add(urlImagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 350, 290, 30));
        panel2.add(Imagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 250, 100, 90));

        RFID.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        RFID.setForeground(new java.awt.Color(204, 0, 0));
        RFID.setText("NO REGISTRADO");
        panel2.add(RFID, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 420, 280, 30));

        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Tarjeta RFID");
        panel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 400, 80, -1));

        brMasculino.setBackground(new java.awt.Color(232, 255, 236));
        buttonGroup1.add(brMasculino);
        brMasculino.setForeground(new java.awt.Color(0, 0, 0));
        brMasculino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brMasculinoActionPerformed(evt);
            }
        });
        panel2.add(brMasculino, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 410, -1, -1));

        brFemenino.setBackground(new java.awt.Color(232, 255, 236));
        buttonGroup1.add(brFemenino);
        brFemenino.setForeground(new java.awt.Color(0, 0, 0));
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
        userLabel2.setForeground(new java.awt.Color(0, 0, 0));
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

        jPanel1.add(panel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 820, 540));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 614, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void TitleMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TitleMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_TitleMouseDragged

    private void TitleMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TitleMousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_TitleMousePressed

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
        if (txtCI.getText().isEmpty()) {
            txtCI.setText("Ingrese su cedula de identidad");
            txtCI.setForeground(new Color(51, 51, 51));
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

    private void loginBtnTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginBtnTxtMouseClicked

        String url = null;
        url = urlImagen.getText();
        actualizarEmpleado(url);

    }//GEN-LAST:event_loginBtnTxtMouseClicked

    private void loginBtnTxtMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginBtnTxtMouseEntered
        btnRegistrar.setBackground(new Color(0, 179, 36));
    }//GEN-LAST:event_loginBtnTxtMouseEntered

    private void loginBtnTxtMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginBtnTxtMouseExited
        btnRegistrar.setBackground(new Color(0, 102, 15));
    }//GEN-LAST:event_loginBtnTxtMouseExited

    private void cmbAulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbAulaActionPerformed

    }//GEN-LAST:event_cmbAulaActionPerformed

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

    private void txtUsuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsuarioKeyTyped

    }//GEN-LAST:event_txtUsuarioKeyTyped

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

    private void cmbCargoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCargoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbCargoActionPerformed

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

    private void txtApellidosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidosKeyTyped
        char validar = evt.getKeyChar();

        if (Character.isDigit(validar)) {
            getToolkit().beep();
            evt.consume();

            mensajeAdvertencia("¡Debe Ingresar solo Texto!");
        }
    }//GEN-LAST:event_txtApellidosKeyTyped

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

    private void txtCelularKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelularKeyTyped
        char validar = evt.getKeyChar();

        if (Character.isLetter(validar)) {
            getToolkit().beep();
            evt.consume();

            mensajeAdvertencia("¡Debe Ingresar solo Numeros!");
        }
    }//GEN-LAST:event_txtCelularKeyTyped

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
        btnAbrirImagen.setBackground(new Color(0, 179, 36));
    }//GEN-LAST:event_txtAbrirImagenMouseEntered

    private void txtAbrirImagenMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtAbrirImagenMouseExited
        btnAbrirImagen.setBackground(new Color(0, 102, 15));
    }//GEN-LAST:event_txtAbrirImagenMouseExited

    private void brMasculinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_brMasculinoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_brMasculinoActionPerformed

    private void brFemeninoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_brFemeninoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_brFemeninoActionPerformed

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

    private void btnSalirMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalirMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSalirMousePressed

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Actualizar_Empleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Actualizar_Empleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Actualizar_Empleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Actualizar_Empleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Actualizar_Empleado().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Imagen;
    private javax.swing.JLabel RFID;
    private javax.swing.JPanel Title;
    private javax.swing.JRadioButton brFemenino;
    private javax.swing.JRadioButton brMasculino;
    private javax.swing.JPanel btnAbrirImagen;
    private javax.swing.JPanel btnConectar;
    private javax.swing.JLabel btnMinimizar;
    private javax.swing.JPanel btnRegistrar;
    private javax.swing.JLabel btnSalir;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton cerrar1;
    private javax.swing.JComboBox<String> cmbAula;
    private javax.swing.JComboBox<String> cmbCargo;
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
    private javax.swing.JPanel txtMinimizar;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JPanel txtSalir;
    private javax.swing.JTextField txtUsuario;
    private javax.swing.JLabel urlImagen;
    private javax.swing.JLabel userLabel;
    private javax.swing.JLabel userLabel1;
    private javax.swing.JLabel userLabel2;
    // End of variables declaration//GEN-END:variables
}
