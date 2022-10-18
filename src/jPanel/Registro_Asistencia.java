package jPanel;

import Estudiante_TablaDeSQL.Conexion;
import Empleado_TablaDeSQL.Asistencia;
import Empleado_TablaDeSQL.EmpleadoDAO_JDBC;
import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.awt.Color;
import static java.awt.Frame.ICONIFIED;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import static java.awt.image.ImageObserver.HEIGHT;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import main.Peticion_Licencia;
import panamahitek.Arduino.PanamaHitek_Arduino;
import proyectoasistenciapersonal.Ingreso;

/**
 *
 * @author JORGE
 */
public class Registro_Asistencia extends javax.swing.JPanel {

    Enumeration cuantosPuertos;//guarda la lista de los puertos Disponibles 
    HashMap portMap = new HashMap();//Guarda la info del arduino

    String horaExacta;
    String codigo;

    private boolean minimiza;
    int xMouse, yMouse;

    long CI_Empleado = 0;
    long CI_Tutor = 0;

    Date FechaDate = new Date();
    Date HoraDate = new Date();
    DateFormat formatoFecha = new SimpleDateFormat("dd/MM/YYYY");
    DateFormat formatoHora = new SimpleDateFormat("HH:mm");

    java.sql.Date fechaF;
    java.sql.Date HoraF;

    Ingreso regas;

    String aux;

    int resultado;
    
    int contadorAsistencias;

    PanamaHitek_Arduino conexion = new PanamaHitek_Arduino();
    SerialPortEventListener listener = new SerialPortEventListener() {
        @Override
        public void serialEvent(SerialPortEvent spe) {
            try {
                if (conexion.isMessageAvailable()) {
                    codigo = conexion.printMessage().trim();
                    if (!codigo.equalsIgnoreCase("Control Inicializado...")) {
                        System.out.println("codigo = " + codigo);
                        resultado = RegistraEmpleado(codigo);
                        if (resultado == 0) {
                            RegistraTutor(codigo);
                        }
                    }
                }
            } catch (Exception ex) {
                mensaje("Error al imprimir datos del Arduino");
            }
        }

    };
    EmpleadoDAO_JDBC empleadoDao = new EmpleadoDAO_JDBC();

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

    public Registro_Asistencia() {
        initComponents();

        etiFechaActual.setText(formatoFecha.format(FechaDate));
        Timer tiempo = new Timer(100, new hora());
        tiempo.start();

    }

    public int RegistraEmpleado(String codigo) {
        int r = 0;
        String codigoaux = null;
        int contador = 0;
        InputStream is;
        ImageIcon foto;
        try {
            String sql = "SELECT persona.ci, persona.nombre||' '||persona.apellidos, persona.foto, empleado.cargo, empleado.codtarjeta\n"
                    + "FROM persona INNER JOIN empleado ON persona.ci = empleado.ci\n"
                    + "WHERE empleado.codtarjeta = '" + codigo + "';";
            Connection cn = Conexion.getConection();
            if(cn.getAutoCommit()){
                cn.setAutoCommit(false);
            }
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                CI_Empleado = rs.getLong(1);
                etiNombreCompleto.setText(rs.getString(2));
                is = rs.getBinaryStream(3);
                if (is != null) {
                    try {
                        BufferedImage bi = ImageIO.read(is);
                        foto = new ImageIcon(bi);
                        Image img = foto.getImage();
                        //(ancho,alto,mensaje)
                        Image newimg = img.getScaledInstance(130, 130, java.awt.Image.SCALE_SMOOTH);

                        ImageIcon newicon = new ImageIcon(newimg);
                        etiImagen.setIcon(newicon);
                    } catch (IOException ex) {
                        etiImagen.setText("No Imagen");
                    }
                } else {
                    etiImagen.setText("No Imagen");
                }
                etiCargo.setText(rs.getString(4));
                codigoaux = rs.getString(5);
            }
            if (codigoaux.equals(codigo)) {
                conexion.sendData(codigo);
                //insertarDatos(CI);

                java.util.Date utilDate = new java.util.Date();
                java.sql.Date Fecha = new java.sql.Date(utilDate.getTime());
                java.sql.Time Hora = new java.sql.Time(utilDate.getTime());

                Asistencia asistencia = new Asistencia(CI_Empleado, Hora, Fecha, "Asistio", leerOcasion());
                contador = empleadoDao.registrarAsistencia(asistencia);
                this.contadorAsistencias += contador;
                etiResultado.setText("SE REGISTRO " + this.contadorAsistencias + " ASISTENCIA");
                r = 1;
                etiEstudiante.setText("");
            }
            cn.commit();
            rs.close();
            st.close();
            cn.close();

        } catch (SQLException ex) {
            mensaje(" Error en la conexion con la base de datos");
        } catch (Exception ex) {
//            mensaje(" Error");
        }
        return r;
    }

    public void RegistraTutor(String codigo) {
        String codigoaux = null;
        int contador = 0;
        long ci_estudiante = 0;
        InputStream is;
        ImageIcon foto;
        try {
            String sql = "SELECT persona.ci, persona.nombre||' '||persona.apellidos, persona.foto, tutor.ci_estudiante, tutor.codtarjeta\n"
                    + "FROM persona INNER JOIN tutor ON persona.ci = tutor.ci\n"
                    + "WHERE tutor.codtarjeta = '" + codigo + "';";
            Connection cn = Conexion.getConection();
            if(cn.getAutoCommit()){
                cn.setAutoCommit(false);
            }
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                CI_Tutor = rs.getLong(1);
                etiNombreCompleto.setText(rs.getString(2));
                is = rs.getBinaryStream(3);
                if (is != null) {
                    try {
                        BufferedImage bi = ImageIO.read(is);
                        foto = new ImageIcon(bi);
                        Image img = foto.getImage();
                        //(ancho,alto,mensaje)
                        Image newimg = img.getScaledInstance(130, 130, java.awt.Image.SCALE_SMOOTH);

                        ImageIcon newicon = new ImageIcon(newimg);
                        etiImagen.setIcon(newicon);
                    } catch (IOException ex) {
                        etiImagen.setText("No Imagen");
                    }
                } else {
                    etiImagen.setText("No Imagen");
                }
                ci_estudiante = rs.getLong(4);
                codigoaux = rs.getString(5);
            }
            if (codigoaux.equals(codigo)) {
                conexion.sendData(codigo);
                java.util.Date utilDate = new java.util.Date();
                java.sql.Date Fecha = new java.sql.Date(utilDate.getTime());
                java.sql.Time Hora = new java.sql.Time(utilDate.getTime());

                Asistencia asistencia = new Asistencia(CI_Tutor, Hora, Fecha, "Asistio", leerOcasion());
                contador = empleadoDao.registrarAsistencia(asistencia);
                this.contadorAsistencias += contador;
                etiResultado.setText("SE REGISTRO " + this.contadorAsistencias + " ASISTENCIA");
                insertarDatosEstudiante(ci_estudiante);
                etiCargo.setText("");
            } else {
                conexion.sendData("null");
                etiResultado.setText("Codigo incorrecto");
            }
            cn.commit();
        } catch (SQLException ex) {
            mensaje(" Error en la conexion con la base de datos");
        } catch (Exception ex) {
        }
    }

    public void insertarDatosEstudiante(long CI) {
        try {
            String sql = "SELECT persona.nombre||' '||persona.apellidos FROM persona WHERE ci = " + CI;
            Connection cn = Conexion.getConection();
            if(cn.getAutoCommit()){
                cn.setAutoCommit(false);
            }
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                etiEstudiante.setText(rs.getString(1));
            }
            cn.commit();
            rs.close();
            st.close();
            cn.close();
        } catch (SQLException ex) {
            mensaje(" Error al insertar datos de la persona");
        }
    }

    public void mensaje(String msje) {
        JOptionPane.showMessageDialog(null, msje);
    }

    String leerOcasion() {
        return ((String) ocasion.getSelectedItem());
    }

    class hora implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Date sistemaHora = new Date();
            String pmAm = "hh:mm:ss a";
            SimpleDateFormat formato = new SimpleDateFormat(pmAm);
            Calendar now = Calendar.getInstance();
            horaCorriendo.setText(String.format(formato.format(sistemaHora), now));
        }
    }

    String hora() {
        Date sistemaHora = new Date();
        String pmAm = "hh:mm:ss a";
        SimpleDateFormat formato = new SimpleDateFormat(pmAm);
        Calendar now = Calendar.getInstance();
        horaCorriendo.setText(String.format(formato.format(sistemaHora), now));

        horaExacta = String.format(formato.format(sistemaHora), now);

        return horaExacta;
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

        panel3 = new org.edisoncor.gui.panel.Panel();
        jLabel4 = new javax.swing.JLabel();
        cerrar1 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        horaCorriendo = new javax.swing.JLabel();
        ocasion = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        etiCargo = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        etiImagen = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        etiEstudiante = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        etiResultado = new javax.swing.JLabel();
        etiNombreCompleto = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        etiFechaActual = new javax.swing.JLabel();
        btnConectar = new javax.swing.JPanel();
        txtConectar = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnLicencia = new javax.swing.JPanel();
        txtLicencia = new javax.swing.JLabel();
        RegistroDeEntrada = new javax.swing.JLabel();

        setBackground(new java.awt.Color(242, 242, 242));

        panel3.setBackground(new java.awt.Color(255, 255, 255));
        panel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 41), 1, true));
        panel3.setForeground(new java.awt.Color(255, 255, 255));
        panel3.setColorPrimario(new java.awt.Color(232, 255, 236));
        panel3.setColorSecundario(new java.awt.Color(232, 255, 236));
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

        jSeparator2.setBackground(new java.awt.Color(0, 0, 0));
        panel3.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 220, 250, 20));

        jLabel6.setFont(new java.awt.Font("Arial Black", 1, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("PERSONA REGISTRADA:");
        panel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 190, 200, 30));

        horaCorriendo.setFont(new java.awt.Font("Arial Black", 0, 24)); // NOI18N
        panel3.add(horaCorriendo, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 110, 180, 50));

        ocasion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ingreso", "Salida" }));
        panel3.add(ocasion, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 130, -1, -1));

        jLabel8.setFont(new java.awt.Font("Arial Black", 1, 13)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setText("FECHA:");
        panel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 120, 60, 30));

        jLabel9.setFont(new java.awt.Font("Arial Black", 1, 13)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 51, 51));
        jLabel9.setText("REGISTRO:");
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });
        panel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 120, 90, 40));

        jLabel2.setFont(new java.awt.Font("Arial Black", 1, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("LICENCIA:");
        panel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 60, 100, 20));

        jSeparator3.setBackground(new java.awt.Color(0, 0, 0));
        panel3.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 340, 210, 20));

        etiCargo.setBackground(new java.awt.Color(255, 255, 255));
        etiCargo.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        etiCargo.setForeground(new java.awt.Color(255, 0, 0));
        etiCargo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        panel3.add(etiCargo, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 310, 220, 30));

        jLabel10.setFont(new java.awt.Font("Arial Black", 1, 13)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setText("CARGO:");
        panel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 310, 70, 30));
        panel3.add(etiImagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 230, 130, 130));

        jSeparator4.setBackground(new java.awt.Color(0, 0, 0));
        panel3.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 280, 220, 20));

        etiEstudiante.setBackground(new java.awt.Color(255, 255, 255));
        etiEstudiante.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        etiEstudiante.setForeground(new java.awt.Color(255, 0, 0));
        etiEstudiante.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        panel3.add(etiEstudiante, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 250, 230, 30));

        jLabel12.setFont(new java.awt.Font("Arial Black", 1, 13)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 51, 51));
        jLabel12.setText("ESTUDIANTE:");
        panel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 250, 120, 30));

        etiResultado.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        etiResultado.setForeground(new java.awt.Color(0, 153, 0));
        panel3.add(etiResultado, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 360, 330, 30));

        etiNombreCompleto.setBackground(new java.awt.Color(255, 255, 255));
        etiNombreCompleto.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        etiNombreCompleto.setForeground(new java.awt.Color(255, 0, 0));
        etiNombreCompleto.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        etiNombreCompleto.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        panel3.add(etiNombreCompleto, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 190, 280, 30));

        jLabel11.setFont(new java.awt.Font("Arial Black", 1, 13)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 51, 51));
        jLabel11.setText("HORA:");
        panel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 120, 60, 30));

        etiFechaActual.setFont(new java.awt.Font("Roboto Medium", 1, 24)); // NOI18N
        panel3.add(etiFechaActual, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 120, 160, 30));

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

        panel3.add(btnConectar, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 60, 90, 30));

        jLabel3.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("IMAGEN :");
        panel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 190, 80, 30));

        jLabel5.setFont(new java.awt.Font("Arial Black", 1, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("ARDUINO :");
        panel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 60, 100, 20));

        btnLicencia.setBackground(new java.awt.Color(0, 102, 15));
        btnLicencia.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 15), 2));

        txtLicencia.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtLicencia.setForeground(new java.awt.Color(255, 255, 255));
        txtLicencia.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtLicencia.setText("Licencia");
        txtLicencia.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        txtLicencia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtLicenciaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtLicenciaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txtLicenciaMouseExited(evt);
            }
        });

        javax.swing.GroupLayout btnLicenciaLayout = new javax.swing.GroupLayout(btnLicencia);
        btnLicencia.setLayout(btnLicenciaLayout);
        btnLicenciaLayout.setHorizontalGroup(
            btnLicenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtLicencia, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
        );
        btnLicenciaLayout.setVerticalGroup(
            btnLicenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtLicencia, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
        );

        panel3.add(btnLicencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 60, -1, -1));

        RegistroDeEntrada.setFont(new java.awt.Font("Cambria", 1, 36)); // NOI18N
        RegistroDeEntrada.setForeground(new java.awt.Color(0, 52, 0));
        RegistroDeEntrada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/images/registroasistencia.png"))); // NOI18N
        RegistroDeEntrada.setText("REGISTRO DE ASISTENCIA ");
        RegistroDeEntrada.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RegistroDeEntradaMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(panel3, javax.swing.GroupLayout.PREFERRED_SIZE, 809, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(RegistroDeEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 607, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(RegistroDeEntrada)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel3, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cerrar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cerrar1ActionPerformed

    }//GEN-LAST:event_cerrar1ActionPerformed

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked

    }//GEN-LAST:event_jLabel9MouseClicked

    private void RegistroDeEntradaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RegistroDeEntradaMouseClicked

    }//GEN-LAST:event_RegistroDeEntradaMouseClicked

    private void txtConectarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtConectarMouseClicked
        if (txtConectar.getText().equals("Conectar")) {
            try {
                conexion.arduinoRXTX(obtenerPuerto(), 9600, listener);
                txtConectar.setText("Desconectar");
                mensajeExito(" ¡Se conecto el Arduino con Exito! ");
            } catch (Exception ex) {
            }

        } else if (txtConectar.getText().equals("Desconectar")) {
            try {
                conexion.killArduinoConnection();
                txtConectar.setText("Conectar");
                mensajeExito(" ¡Se desconecto el Arduino con Exito! ");
            } catch (Exception ex) {
            }
        }
    }//GEN-LAST:event_txtConectarMouseClicked

    private void txtConectarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtConectarMouseEntered
        btnConectar.setBackground(new Color(0, 179, 36));
    }//GEN-LAST:event_txtConectarMouseEntered

    private void txtConectarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtConectarMouseExited
        btnConectar.setBackground(new Color(0, 102, 15));
    }//GEN-LAST:event_txtConectarMouseExited

    private void txtLicenciaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtLicenciaMouseClicked
        Peticion_Licencia licencia = new Peticion_Licencia();
        licencia.setVisible(true);
    }//GEN-LAST:event_txtLicenciaMouseClicked

    private void txtLicenciaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtLicenciaMouseEntered
        btnLicencia.setBackground(new Color(0, 179, 36));
    }//GEN-LAST:event_txtLicenciaMouseEntered

    private void txtLicenciaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtLicenciaMouseExited
        btnLicencia.setBackground(new Color(0, 102, 15));
    }//GEN-LAST:event_txtLicenciaMouseExited


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel RegistroDeEntrada;
    private javax.swing.JPanel btnConectar;
    private javax.swing.JPanel btnLicencia;
    private javax.swing.JButton cerrar1;
    private javax.swing.JLabel etiCargo;
    private javax.swing.JLabel etiEstudiante;
    private javax.swing.JLabel etiFechaActual;
    private javax.swing.JLabel etiImagen;
    private javax.swing.JLabel etiNombreCompleto;
    private javax.swing.JLabel etiResultado;
    private javax.swing.JLabel horaCorriendo;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JComboBox<String> ocasion;
    private org.edisoncor.gui.panel.Panel panel3;
    private javax.swing.JLabel txtConectar;
    private javax.swing.JLabel txtLicencia;
    // End of variables declaration//GEN-END:variables
}
