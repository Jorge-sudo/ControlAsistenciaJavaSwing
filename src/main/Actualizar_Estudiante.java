package main;

import Empleado_TablaDeSQL.Persona;
import Estudiante_TablaDeSQL.Conexion;
import Estudiante_TablaDeSQL.Estudiante;
import Estudiante_TablaDeSQL.EstudianteDAO_JDBC;
import Estudiante_TablaDeSQL.Tutor;
import com.sun.awt.AWTUtilities;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import jPanel.Tabla_Empleado;
import jPanel.Tabla_Estudiante;
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

public class Actualizar_Estudiante extends javax.swing.JFrame {

    private boolean minimiza;
    int xMouse, yMouse;

    String rutaEstudiante = null;

    Enumeration cuantosPuertos;//guarda la lista de los puertos Disponibles 
    HashMap portMap = new HashMap();//Guarda la info del arduino

    int id_persona;
    int id_estudiante;

    boolean masculino = Tabla_Estudiante.masculino;
    boolean femenino = Tabla_Estudiante.femenino;

    boolean tutor1 = Tabla_Estudiante.tutor1;
    boolean tutor2 = Tabla_Estudiante.tutor2;
    
    public static long ci_estudiante; 

    String estudiante_update;

    public Actualizar_Estudiante() {
        initComponents();

        this.setLocationRelativeTo(this);
        Shape forma = new RoundRectangle2D.Double(0, 0, this.getBounds().width, this.getBounds().height, 15, 15);
        AWTUtilities.setWindowShape(this, forma);

        estudiante_update = Tabla_Estudiante.estudiante_update;
        InsertarDatosPersona(estudiante_update);
        InsertarDatosEstudiante(estudiante_update);

    }

    void actualiar_Estudiante(String ruta) {
        int persona;
        int estudiante;
        Connection conexion = null;
        try {
            conexion = Conexion.getConection();
            if (conexion.getAutoCommit()) {
                //Esto hacemos para el AutoCommit no se inicialice solo ya que nosotros lo haremos manualmente 
                conexion.setAutoCommit(false);
            }

            EstudianteDAO_JDBC estudianteDao = new EstudianteDAO_JDBC(conexion);
            
            Persona actualizar_persona = new Persona(id_persona, leerCI(), leerNombre(), leerApellidos());

            Estudiante actualizar_estudiante = new Estudiante(id_estudiante, leerAula(), leerTutor(), leerGenero(), 
                    leerCI());
            
            persona = estudianteDao.actualizarPersona(actualizar_persona, ruta);
            estudiante = estudianteDao.actualizarEstudiante(actualizar_estudiante);

            conexion.commit();
            mensajeExito(" Se actualizo " + (persona + estudiante)/2 + " Estudiante.");

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
    
    String leerGenero() {
        if (brMasculino.isSelected()) {
            return "M";
        } else {
            return "F";
        }
    }
    
    int leerTutor() {
        if (br1.isSelected()) {
            return 1;
        } else {
            return 2;
        }
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
    
    String leerAula() {
        return ((String) cmbAula.getSelectedItem());
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
                ci_estudiante = rs.getLong(2);
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

    public void InsertarDatosEstudiante(String ci) {
        String aula;
        int tutor;
        try {
            String sql = "SELECT * FROM estudiante WHERE ci = '" + ci + "'";
            Connection cn = Conexion.getConection();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                id_estudiante = rs.getInt(1);
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        panel2 = new org.edisoncor.gui.panel.Panel();
        jLabel4 = new javax.swing.JLabel();
        cerrar1 = new javax.swing.JButton();
        userLabel = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        txtApellidos = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        cmbAula = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        loginBtn = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JLabel();
        brMasculino = new javax.swing.JRadioButton("MASCULINO",this.masculino);
        brFemenino = new javax.swing.JRadioButton("FEMENINO", this.femenino);
        jLabel12 = new javax.swing.JLabel();
        txtCI = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        userLabel1 = new javax.swing.JLabel();
        Imagen = new javax.swing.JLabel();
        btnAbrirImagenEstudiante = new javax.swing.JPanel();
        AbrirImagenEstudiante = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton("2", this.tutor2);
        br1 = new javax.swing.JRadioButton("1", this.tutor1);
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

        userLabel.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        userLabel.setForeground(new java.awt.Color(0, 0, 0));
        userLabel.setText("Nombre del Estudiante");
        panel2.add(userLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, -1, -1));

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
        panel2.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, 180, 30));

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        panel2.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 190, 10));

        jLabel1.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Apellidos del Estudiante ");
        panel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 180, 20));

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
        panel2.add(txtApellidos, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 170, 170, 30));

        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));
        panel2.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, 190, 10));

        jLabel7.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Genero del Estudiante");
        panel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 140, 170, -1));

        cmbAula.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Verde", "Amarillo", "Azul", "Rosado", "Rojo" }));
        cmbAula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbAulaActionPerformed(evt);
            }
        });
        panel2.add(cmbAula, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 250, -1, -1));

        jLabel2.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Aula");
        panel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 250, 60, -1));

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

        panel2.add(loginBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 250, 110, -1));

        brMasculino.setBackground(new java.awt.Color(232, 255, 236));
        buttonGroup1.add(brMasculino);
        brMasculino.setForeground(new java.awt.Color(0, 0, 0));
        brMasculino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brMasculinoActionPerformed(evt);
            }
        });
        panel2.add(brMasculino, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 170, -1, -1));

        brFemenino.setBackground(new java.awt.Color(232, 255, 236));
        buttonGroup1.add(brFemenino);
        brFemenino.setForeground(new java.awt.Color(0, 0, 0));
        panel2.add(brFemenino, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 170, -1, -1));

        jLabel12.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Nro. Tutores");
        panel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 230, -1, 20));

        txtCI.setBackground(new java.awt.Color(232, 255, 236));
        txtCI.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtCI.setForeground(new java.awt.Color(102, 102, 102));
        txtCI.setText("Ingrese su cedula de identidad");
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
        panel2.add(txtCI, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 70, 180, 30));

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        panel2.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 100, 190, 10));

        userLabel1.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        userLabel1.setForeground(new java.awt.Color(0, 0, 0));
        userLabel1.setText("CI. del Estudiante");
        panel2.add(userLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 40, -1, -1));
        panel2.add(Imagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 100, 100, 90));

        btnAbrirImagenEstudiante.setBackground(new java.awt.Color(0, 102, 15));
        btnAbrirImagenEstudiante.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 15)));

        AbrirImagenEstudiante.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        AbrirImagenEstudiante.setForeground(new java.awt.Color(255, 255, 255));
        AbrirImagenEstudiante.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        AbrirImagenEstudiante.setText("Abrir Imagen");
        AbrirImagenEstudiante.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AbrirImagenEstudiante.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AbrirImagenEstudianteMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                AbrirImagenEstudianteMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                AbrirImagenEstudianteMouseExited(evt);
            }
        });

        javax.swing.GroupLayout btnAbrirImagenEstudianteLayout = new javax.swing.GroupLayout(btnAbrirImagenEstudiante);
        btnAbrirImagenEstudiante.setLayout(btnAbrirImagenEstudianteLayout);
        btnAbrirImagenEstudianteLayout.setHorizontalGroup(
            btnAbrirImagenEstudianteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(AbrirImagenEstudiante, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
        );
        btnAbrirImagenEstudianteLayout.setVerticalGroup(
            btnAbrirImagenEstudianteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(AbrirImagenEstudiante, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        panel2.add(btnAbrirImagenEstudiante, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 60, 90, 30));

        jLabel17.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 0));
        jLabel17.setText("Imagen del Estudiante");
        panel2.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 20, 160, 20));

        jRadioButton1.setBackground(new java.awt.Color(232, 255, 236));
        buttonGroup2.add(jRadioButton1);
        jRadioButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioButton1MouseClicked(evt);
            }
        });
        panel2.add(jRadioButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 260, -1, -1));

        br1.setBackground(new java.awt.Color(232, 255, 236));
        buttonGroup2.add(br1);
        panel2.add(br1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 260, -1, -1));

        urlImagen.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        urlImagen.setForeground(new java.awt.Color(51, 51, 51));
        panel2.add(urlImagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 200, 220, 20));

        jPanel1.add(panel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 710, 320));

        title.setFont(new java.awt.Font("Cambria", 1, 36)); // NOI18N
        title.setForeground(new java.awt.Color(0, 52, 0));
        title.setText("ACTUALIZAR ESTUDIANTE ");
        jPanel1.add(title, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, -1, 30));

        favicon.setFont(new java.awt.Font("Roboto Black", 1, 24)); // NOI18N
        favicon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/images/favicon_1.png"))); // NOI18N
        jPanel1.add(favicon, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, 30));

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, txtMinimizarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnMinimizar, javax.swing.GroupLayout.DEFAULT_SIZE, 18, Short.MAX_VALUE)
                .addContainerGap())
        );
        txtMinimizarLayout.setVerticalGroup(
            txtMinimizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(txtMinimizarLayout.createSequentialGroup()
                .addComponent(btnMinimizar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, Short.MAX_VALUE)
                .addContainerGap())
        );

        Title.add(txtMinimizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 0, 30, -1));

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

        Title.add(txtSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 0, -1, -1));

        jPanel1.add(Title, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void cmbAulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbAulaActionPerformed

    }//GEN-LAST:event_cmbAulaActionPerformed

    private void btnGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseClicked
        String url = null;
        url = urlImagen.getText();
        actualiar_Estudiante(url);
    }//GEN-LAST:event_btnGuardarMouseClicked

    private void btnGuardarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseEntered
        loginBtn.setBackground(new Color(0, 179, 36));
    }//GEN-LAST:event_btnGuardarMouseEntered

    private void btnGuardarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseExited
        loginBtn.setBackground(new Color(0, 102, 15));
    }//GEN-LAST:event_btnGuardarMouseExited

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

    private void AbrirImagenEstudianteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AbrirImagenEstudianteMouseClicked
        JFileChooser j = new JFileChooser();
        j.setCurrentDirectory(new File("Imagenes/"));
        int ap = j.showOpenDialog(this);

        if (ap == JFileChooser.APPROVE_OPTION) {
            rutaEstudiante = j.getSelectedFile().getAbsolutePath();
            Imagen.setIcon(new ImageIcon(rutaEstudiante));
            urlImagen.setText(rutaEstudiante);
        }
    }//GEN-LAST:event_AbrirImagenEstudianteMouseClicked

    private void AbrirImagenEstudianteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AbrirImagenEstudianteMouseEntered
        btnAbrirImagenEstudiante.setBackground(new Color(0, 179, 36));
    }//GEN-LAST:event_AbrirImagenEstudianteMouseEntered

    private void AbrirImagenEstudianteMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AbrirImagenEstudianteMouseExited
        btnAbrirImagenEstudiante.setBackground(new Color(0, 102, 15));
    }//GEN-LAST:event_AbrirImagenEstudianteMouseExited

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

    private void brMasculinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_brMasculinoActionPerformed

    }//GEN-LAST:event_brMasculinoActionPerformed

    private void jRadioButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton1MouseClicked
        if (tutor1 == true) {
            System.out.println("tutor1 = " + tutor1);
            Registrar_Tutor2 nuevotutor2 = new Registrar_Tutor2();
            nuevotutor2.setVisible(true);
        }
        tutor1 = false;
    }//GEN-LAST:event_jRadioButton1MouseClicked

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Actualizar_Estudiante.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Actualizar_Estudiante.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Actualizar_Estudiante.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Actualizar_Estudiante.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Actualizar_Estudiante().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AbrirImagenEstudiante;
    private javax.swing.JLabel Imagen;
    private javax.swing.JPanel Title;
    private javax.swing.JRadioButton br1;
    private javax.swing.JRadioButton brFemenino;
    private javax.swing.JRadioButton brMasculino;
    private javax.swing.JPanel btnAbrirImagenEstudiante;
    private javax.swing.JLabel btnGuardar;
    private javax.swing.JLabel btnMinimizar;
    private javax.swing.JLabel btnSalir;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton cerrar1;
    private javax.swing.JComboBox<String> cmbAula;
    private javax.swing.JLabel favicon;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JPanel loginBtn;
    private org.edisoncor.gui.panel.Panel panel2;
    private javax.swing.JLabel title;
    private javax.swing.JTextField txtApellidos;
    private javax.swing.JTextField txtCI;
    private javax.swing.JPanel txtMinimizar;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JPanel txtSalir;
    private javax.swing.JLabel urlImagen;
    private javax.swing.JLabel userLabel;
    private javax.swing.JLabel userLabel1;
    // End of variables declaration//GEN-END:variables
}
