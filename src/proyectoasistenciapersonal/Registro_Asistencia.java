/*
FORMULARIO PARA REGISTRAR INGRESOS DE LA ASISTENCIA
DE UN Empleado. SE REGISTRARA EN UN ARCHIVO DE TEXTO
 */
package proyectoasistenciapersonal;

import Estudiante_TablaDeSQL.Conexion;
import comunicacionserial.ArduinoExcepcion;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import jssc.SerialPortException;
import panamahitek.Arduino.PanamaHitek_Arduino;

public class Registro_Asistencia extends javax.swing.JFrame {

    String horaExacta;
    String codigo;

    private boolean minimiza;
    int xMouse, yMouse;

    File ArchivoAsistencia;
    FileWriter ArchivoAsistenciaW;
    PrintWriter pw;
    Date diaHora = new Date();
    DateFormat formatoFecha = new SimpleDateFormat("dd/MM/YYYY");
    DateFormat formatoHora = new SimpleDateFormat("HH:mm");
    Ingreso regas;

    PanamaHitek_Arduino conexion = new PanamaHitek_Arduino();
    SerialPortEventListener listener = new SerialPortEventListener() {
        @Override
        public void serialEvent(SerialPortEvent spe) {
            try {
                if (conexion.isMessageAvailable()) {
                    codigo = conexion.printMessage();
                    Registra(codigo);
                }
            } catch (Exception ex) {
                mensaje("Error al imprimir datos del Arduino");
            }
        }

    };

    public Registro_Asistencia() {
        initComponents();
        this.setLocationRelativeTo(this);

        try {
            conexion.arduinoRXTX("COM3", 9600, listener);
        } catch (Exception ex) {
            mensaje(" Error en la conexion con el Arduino ");
        }

        //etiFechaActual.setText(formatoFecha.format(diaHora));
        Timer tiempo = new Timer(100, new hora());
        tiempo.start();
    }

    public void Registra(String codigo) {
        String codigoaux = "";
        try {
            String sql = "SELECT * FROM empleados WHERE codigo = '" + codigo + "'";
            Connection cn = Conexion.getConection();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                codigoaux = rs.getString(12);
            }

            if (codigoaux.equals(codigo)) {
                conexion.sendData(codigo);
                insertarDatos(codigo);
                mensaje("Se registro correctamente su asistencia");
            } else {
                conexion.sendData("null");
                mensaje("Codigo incorrecto");
            }

        } catch (SQLException ex) {
            mensaje(" Error en la conexion con la base de datos");
        } catch (Exception ex) {
            mensaje(" Error al enviar datos al monitor serial");
        }
    }

    public void insertarDatos(String codigo) {
        InputStream is;
        ImageIcon foto;
        String nombre = "", apellido = "", cargo = "";
        try {
            String sql = "SELECT * FROM empleados WHERE codigo = '" + codigo + "'";
            Connection cn = Conexion.getConection();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                nombre = rs.getString(5);
                apellido = rs.getString(6);
                cargo = rs.getString(10);
                is = rs.getBinaryStream(11);
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
            }
            etiNombre.setText(nombre);
            etiApellido.setText(apellido);
            etiCargo.setText(cargo);
            etiResultado.setText(" PERSONA REGISTRADA CON EXITO ");

        } catch (SQLException ex) {
            mensaje(" Error al insertar datos de la asistencia");
        }
    }

    public void mensaje(String msje) {
        JOptionPane.showMessageDialog(null, msje);
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


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        favicon = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        panel3 = new org.edisoncor.gui.panel.Panel();
        txtSalir = new javax.swing.JPanel();
        btnSalir = new javax.swing.JLabel();
        txtMinimizar = new javax.swing.JPanel();
        btnMinimizar = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cerrar1 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        etiApellido = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        horaCorriendo = new javax.swing.JLabel();
        RegistroDeEntrada = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        fecha = new com.toedter.calendar.JDateChooser();
        btnRegistrar = new javax.swing.JPanel();
        txtRegistrar = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnMenu = new javax.swing.JPanel();
        txtMenu = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        etiCargo = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        etiImagen = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        etiEmpleado1 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        etiResultado = new javax.swing.JLabel();
        etiNombre = new javax.swing.JLabel();

        favicon.setFont(new java.awt.Font("Roboto Black", 1, 24)); // NOI18N
        favicon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/images/favicon.png"))); // NOI18N

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("REGISTRO");
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel3.setBackground(new java.awt.Color(153, 153, 255));
        panel3.setForeground(new java.awt.Color(255, 255, 255));
        panel3.setColorPrimario(new java.awt.Color(0, 134, 190));
        panel3.setColorSecundario(new java.awt.Color(153, 255, 204));
        panel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        panel3.add(txtSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 0, -1, -1));

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

        panel3.add(txtMinimizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 0, 30, -1));
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
        panel3.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 280, 250, 20));

        etiApellido.setBackground(new java.awt.Color(255, 255, 255));
        etiApellido.setFont(new java.awt.Font("Roboto Medium", 1, 18)); // NOI18N
        etiApellido.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        etiApellido.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        panel3.add(etiApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 250, 170, 30));

        jLabel6.setFont(new java.awt.Font("Arial Black", 1, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("PERSONA REGISTRADA:");
        panel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 250, 200, 30));

        horaCorriendo.setFont(new java.awt.Font("Arial Black", 0, 24)); // NOI18N
        panel3.add(horaCorriendo, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 170, 180, 50));

        RegistroDeEntrada.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        RegistroDeEntrada.setForeground(new java.awt.Color(255, 255, 255));
        RegistroDeEntrada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/images/4310247.png"))); // NOI18N
        RegistroDeEntrada.setText("REGISTRO DE ASISTENCIA ");
        panel3.add(RegistroDeEntrada, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 40, 460, 110));

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
        panel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 730, 40));

        fecha.setBackground(new java.awt.Color(0, 134, 190));
        fecha.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        fecha.setForeground(new java.awt.Color(0, 0, 0));
        panel3.add(fecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 180, 100, 30));

        btnRegistrar.setBackground(new java.awt.Color(0, 134, 190));

        txtRegistrar.setFont(new java.awt.Font("Roboto Black", 1, 14)); // NOI18N
        txtRegistrar.setForeground(new java.awt.Color(255, 255, 255));
        txtRegistrar.setText("    REGISTRAR");
        txtRegistrar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txtRegistrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtRegistrarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtRegistrarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txtRegistrarMouseExited(evt);
            }
        });

        javax.swing.GroupLayout btnRegistrarLayout = new javax.swing.GroupLayout(btnRegistrar);
        btnRegistrar.setLayout(btnRegistrarLayout);
        btnRegistrarLayout.setHorizontalGroup(
            btnRegistrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtRegistrar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
        );
        btnRegistrarLayout.setVerticalGroup(
            btnRegistrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtRegistrar, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        panel3.add(btnRegistrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 530, 110, 40));

        jLabel7.setFont(new java.awt.Font("Arial Black", 1, 13)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("HORA:");
        panel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 180, 90, 30));

        btnMenu.setBackground(new java.awt.Color(0, 134, 190));

        txtMenu.setFont(new java.awt.Font("Roboto Black", 1, 14)); // NOI18N
        txtMenu.setForeground(new java.awt.Color(255, 255, 255));
        txtMenu.setText("      MENU");
        txtMenu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        txtMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtMenuMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtMenuMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txtMenuMouseExited(evt);
            }
        });

        javax.swing.GroupLayout btnMenuLayout = new javax.swing.GroupLayout(btnMenu);
        btnMenu.setLayout(btnMenuLayout);
        btnMenuLayout.setHorizontalGroup(
            btnMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtMenu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
        );
        btnMenuLayout.setVerticalGroup(
            btnMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtMenu, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        panel3.add(btnMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 530, 90, -1));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ingreso", "Salida" }));
        panel3.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 190, -1, -1));

        jLabel8.setFont(new java.awt.Font("Arial Black", 1, 13)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("FECHA:");
        panel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 180, 60, 30));

        jLabel9.setFont(new java.awt.Font("Arial Black", 1, 13)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("REGISTRO:");
        panel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, 90, 40));

        jLabel2.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("IMAGEN :");
        panel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 240, 80, 30));

        jSeparator3.setBackground(new java.awt.Color(0, 0, 0));
        panel3.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 410, 210, 20));

        etiCargo.setBackground(new java.awt.Color(255, 255, 255));
        etiCargo.setFont(new java.awt.Font("Roboto Medium", 1, 18)); // NOI18N
        etiCargo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        panel3.add(etiCargo, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 380, 220, 30));

        jLabel10.setFont(new java.awt.Font("Arial Black", 1, 13)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("CARGO:");
        panel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 380, 70, 30));
        panel3.add(etiImagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 290, 130, 130));

        jSeparator4.setBackground(new java.awt.Color(0, 0, 0));
        panel3.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 340, 210, 20));

        etiEmpleado1.setBackground(new java.awt.Color(255, 255, 255));
        etiEmpleado1.setFont(new java.awt.Font("Roboto Medium", 1, 18)); // NOI18N
        etiEmpleado1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        panel3.add(etiEmpleado1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 310, 190, 30));

        jLabel12.setFont(new java.awt.Font("Arial Black", 1, 13)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("ALUMNO:");
        panel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 310, 80, 30));

        etiResultado.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        etiResultado.setForeground(new java.awt.Color(0, 0, 0));
        panel3.add(etiResultado, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 450, 480, 50));

        etiNombre.setBackground(new java.awt.Color(255, 255, 255));
        etiNombre.setFont(new java.awt.Font("Roboto Medium", 1, 18)); // NOI18N
        etiNombre.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        etiNombre.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        panel3.add(etiNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 250, 100, 30));

        getContentPane().add(panel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 730, 590));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cerrar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cerrar1ActionPerformed

    }//GEN-LAST:event_cerrar1ActionPerformed

    private void jLabel11MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_jLabel11MouseDragged

    private void jLabel11MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_jLabel11MousePressed

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

    private void txtRegistrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtRegistrarMouseClicked

        Date fechaActual = fecha.getDate();
        if (fechaActual == null) {
            mensaje("Ingrese la fecha !");
        } else {
            String fecha = formatoFecha.format(fechaActual);
            System.out.println("fechaa = " + fecha);

            String horaActual = hora();
            System.out.println("hora = " + horaActual);

            System.out.println("");
        }
    }//GEN-LAST:event_txtRegistrarMouseClicked

    private void txtRegistrarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtRegistrarMouseEntered
        btnRegistrar.setBackground(new Color(1, 96, 126));
    }//GEN-LAST:event_txtRegistrarMouseEntered

    private void txtRegistrarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtRegistrarMouseExited
        btnRegistrar.setBackground(new Color(0, 134, 190));
    }//GEN-LAST:event_txtRegistrarMouseExited

    private void txtMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMenuMouseClicked
        Menu menu = new Menu();
        menu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_txtMenuMouseClicked

    private void txtMenuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMenuMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMenuMouseEntered

    private void txtMenuMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMenuMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMenuMouseExited

    
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Registro_Asistencia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Registro_Asistencia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Registro_Asistencia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Registro_Asistencia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Registro_Asistencia().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel RegistroDeEntrada;
    private javax.swing.JPanel btnMenu;
    private javax.swing.JLabel btnMinimizar;
    private javax.swing.JPanel btnRegistrar;
    private javax.swing.JLabel btnSalir;
    private javax.swing.JButton cerrar1;
    private javax.swing.JLabel etiApellido;
    private javax.swing.JLabel etiCargo;
    private javax.swing.JLabel etiEmpleado1;
    private javax.swing.JLabel etiImagen;
    private javax.swing.JLabel etiNombre;
    private javax.swing.JLabel etiResultado;
    private javax.swing.JLabel favicon;
    private com.toedter.calendar.JDateChooser fecha;
    private javax.swing.JLabel horaCorriendo;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private org.edisoncor.gui.panel.Panel panel3;
    private javax.swing.JLabel txtMenu;
    private javax.swing.JPanel txtMinimizar;
    private javax.swing.JLabel txtRegistrar;
    private javax.swing.JPanel txtSalir;
    // End of variables declaration//GEN-END:variables

}
