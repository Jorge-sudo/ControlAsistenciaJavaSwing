package jPanel;

import Estudiante_TablaDeSQL.*;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.awt.Color;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import panamahitek.Arduino.PanamaHitek_Arduino;
import proyectoasistenciapersonal.Empleado;

public class Registro_Estudiante extends javax.swing.JPanel {

    Empleado emp; //declara un objeto emp de la clase Empleado
    DefaultTableModel dtm = new DefaultTableModel();
    int clickTabla;

    String rutaEstudiante = null;
    String rutaTutor1 = null;
    String rutaTutor2 = null;

    String arduinoConexion = null;

    Enumeration cuantosPuertos;//guarda la lista de los puertos Disponibles 
    HashMap portMap = new HashMap();//Guarda la info del arduino

    String codTarjeta1;
    String codTarjeta2;
    String aux;


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
                        codTarjeta1 = aux;
                        System.out.println("codigo 1 = " + codTarjeta1);
                        RFID1.setText(" REGISTRADO..!");
                    }
                }
                if (conexion.isMessageAvailable()) {
                    aux = conexion.printMessage().trim();
                    if (aux.equalsIgnoreCase("Control Inicializado...")) {
                        arduinoConexion = aux;
                    } else {
                        codTarjeta2 = aux;
                        System.out.println("codigo 2 = " + codTarjeta2);
                        RFID2.setText(" REGISTRADO..!");
                    }
                }

            } catch (Exception ex) {
                mensajeError("Error al imprimir datos del Arduino");
            }
        }

    };

    public Registro_Estudiante() {
        initComponents();
        setBackground(new Color(0, 0, 0, 0));

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


    void registroEstudiante() {
        int contadorestudiante;
        int contadortutor1;
        int contadortutor2;
        if ("Ingrese su nombre".equals(leerNombre()) || "".equals(leerNombre())) {
            mensajeAdvertencia("¡Ingrese el Nombre del Estudiante!");
        } else if ("Ingrese sus apellidos".equals(leerApellidos()) || "".equals(leerApellidos())) {
            mensajeAdvertencia("¡Ingrese el Apellido del Estudiante!");
        } else if (leerCI() == -99) {
            mensajeAdvertencia("¡Ingrese la Cedula de Identidad del Estudiante!");
        } else if (leerCITutor1() == -99) {
            mensajeAdvertencia("¡Ingrese la Cedula de Identidad del Tutor 1!");
        } else if ("Ingrese su nombre".equals(leerNombreT1()) || "".equals(leerNombreT1())) {
            mensajeAdvertencia("¡Ingrese el Nombre del Tutor 1!");
        } else if ("Ingrese sus apellidos".equals(leerApellidoT1()) || "".equals(leerApellidoT1())) {
            mensajeAdvertencia("¡Ingrese el Apellido del Tutor 1!");
        } else if (leerCelularT1() == -99) {
            mensajeAdvertencia("¡Ingrese el Numero de Celular del Tutor 1!");
        } else if (this.rutaEstudiante == null) {
            mensajeAdvertencia("¡Ingrese la Fotografia del Estudiante!");
        } else if (this.rutaTutor1 == null) {
            mensajeAdvertencia("¡Ingrese la Fotografia del Tutor 1!");
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

                if (leerTutor() == 1) {

                    Estudiante estudianteNuevo = new Estudiante(leerCI(), leerNombre(), leerApellidos(), leerAula(),
                            1, leerGenero());
                    estudianteDao.insertarPersona(estudianteNuevo, rutaEstudiante);
                    contadorestudiante = estudianteDao.insertarEstudiante(estudianteNuevo);

                    Tutor tutor1 = new Tutor(leerCITutor1(), leerNombreT1(), leerApellidoT1(), leerCI(), 1, codTarjeta1,
                            leerCelularT1(), leerParentesco1());
                    estudianteDao.insertarPersona(tutor1, rutaTutor1);
                    contadortutor1 = estudianteDao.insertarTutor(tutor1);

                    conexion.commit();
                    mensajeExito(" Se registro " + contadorestudiante + " Estudiante y " + contadortutor1 + " Tutor.");
                } else {
                    if (leerCITutor2() == -99) {
                        mensajeAdvertencia("¡Ingrese su Cedula de Identidad del Tutor 2!");
                    } else if ("Ingrese su nombre".equals(leerNombreT2()) || "".equals(leerNombreT2())) {
                        mensajeAdvertencia("¡Ingrese el Nombre del Tutor 2!");
                    } else if ("Ingrese sus apellidos".equals(leerApellidoT2()) || "".equals(leerApellidoT2())) {
                        mensajeAdvertencia("¡Ingrese el Apellido del Tutor 2!");
                    } else if (leerCelularT2() == -99) {
                        mensajeAdvertencia("¡Ingrese el Numero del Celular del Tutor 2!");
                    } else if (this.rutaTutor2 == null) {
                        mensajeAdvertencia("¡Ingrese la Fotografia del Tutor 2!");
                    } else if ("NO REGISTRADO".equals(RFID2.getText().trim())) {
                        mensajeAdvertencia("¡Pase la tarjeta RFID del  Tutor 2!");
                    } else {
                        Estudiante estudianteNuevo = new Estudiante(leerCI(), leerNombre(), leerApellidos(), leerAula(),
                                2, leerGenero());
                        estudianteDao.insertarPersona(estudianteNuevo, rutaEstudiante);
                        contadorestudiante = estudianteDao.insertarEstudiante(estudianteNuevo);

                        Tutor tutor1 = new Tutor(leerCITutor1(), leerNombreT1(), leerApellidoT1(), leerCI(), 1, codTarjeta1,
                                leerCelularT1(), leerParentesco1());
                        estudianteDao.insertarPersona(tutor1, rutaTutor1);
                        contadortutor1 = estudianteDao.insertarTutor(tutor1);

                        Tutor tutor2 = new Tutor(leerCITutor2(), leerNombreT2(), leerApellidoT2(), leerCI(), 2, codTarjeta2,
                                leerCelularT2(), leerParentesco2());
                        estudianteDao.insertarPersona(tutor2, rutaTutor2);
                        contadortutor2 = estudianteDao.insertarTutor(tutor2);

                        conexion.commit();
                        mensajeExito(" Se registro " + contadorestudiante + " Estudiante y " + (contadortutor1 + contadortutor2)
                                + " Tutores.");
                    }

                }

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

    }

    void limpiar() {
        txtCI.setText(null);
        txtNombreEstudiante.setText(null);
        txtApellidoEstudiante.setText(null);
        txt1TutorNombre.setText(null);
        txt2TutorNombre.setText(null);
        txt1TutorApellidos.setText(null);
        txt2TutorApellidos.setText(null);
        txt1TutorCelular.setText(null);
        brMasculino.setSelected(true);
        parentesco1.setSelectedIndex(0);
        parentesco2.setSelectedIndex(0);
        Tutor1.setSelectedIndex(0);
        aula.setSelectedIndex(0);
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

    long leerCITutor1() {
        try {
            String aux = txtCITutor1.getText().trim();
            long codigo = Integer.parseInt(aux);
            return codigo;
        } catch (NumberFormatException e) {
            return -99;
        }
    }

    long leerCITutor2() {
        try {
            String aux = txtCITutor2.getText().trim();
            long codigo = Integer.parseInt(aux);
            return codigo;
        } catch (NumberFormatException e) {
            return -99;
        }
    }

    String leerNombre() {
        try {
            String nombre = txtNombreEstudiante.getText().trim();
            return nombre;
        } catch (Exception e) {
            return null;

        }
    }

    String leerApellidos() {
        try {
            String apellidos = txtApellidoEstudiante.getText().trim();
            return apellidos;
        } catch (Exception e) {
            return null;
        }
    }

    String leerNombreT1() {
        try {
            String nombre = txt1TutorNombre.getText().trim();
            return nombre;
        } catch (Exception e) {
            return null;
        }
    }

    String leerNombreT2() {
        try {
            String nombre = txt2TutorNombre.getText().trim();
            return nombre;
        } catch (Exception e) {
            return null;
        }
    }

    String leerApellidoT1() {
        try {
            String nombre = txt1TutorApellidos.getText().trim();
            return nombre;
        } catch (Exception e) {
            return null;
        }
    }

    String leerApellidoT2() {
        try {
            String nombre = txt2TutorApellidos.getText().trim();
            return nombre;
        } catch (Exception e) {
            return null;
        }
    }

    int leerCelularT1() {
        try {
            String aux = txt1TutorCelular.getText().trim();
            int codigo = Integer.parseInt(aux);
            return codigo;
        } catch (Exception e) {
            return -99;
        }
    }

    int leerCelularT2() {
        try {
            String aux = txt2TutorCelular.getText().trim();
            int codigo = Integer.parseInt(aux);
            return codigo;
        } catch (Exception e) {
            return -99;
        }
    }

    String leerGenero() {
        if (brMasculino.isSelected()) {
            return "M";
        } else {
            return "F";
        }
    }

    String leerAula() {
        return ((String) aula.getSelectedItem());
    }

    int leerTutor() {
        try {
            String aux = (String) Tutor1.getSelectedItem();
            int tutor = Integer.parseInt(aux);
            return tutor;
        } catch (Exception e) {
            return -99;
        }

    }

    String leerParentesco1() {
        return ((String) parentesco1.getSelectedItem());
    }

    String leerParentesco2() {
        return ((String) parentesco2.getSelectedItem());
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
        userLabel = new javax.swing.JLabel();
        txtNombreEstudiante = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        txtApellidoEstudiante = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        Tutor1 = new javax.swing.JComboBox<>();
        passLabel2 = new javax.swing.JLabel();
        txt1TutorNombre = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        passLabel = new javax.swing.JLabel();
        txt1TutorApellidos = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        txt1TutorCelular = new javax.swing.JTextField();
        jSeparator7 = new javax.swing.JSeparator();
        txt2TutorCelular = new javax.swing.JTextField();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JSeparator();
        txt2TutorApellidos = new javax.swing.JTextField();
        passLabel3 = new javax.swing.JLabel();
        jSeparator10 = new javax.swing.JSeparator();
        txt2TutorNombre = new javax.swing.JTextField();
        passLabel4 = new javax.swing.JLabel();
        aula = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        btnAbrirImagenTutor1 = new javax.swing.JPanel();
        AbrirImagenTutor1 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        ImagenTutor1 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        btnAbrirImagenTutor2 = new javax.swing.JPanel();
        AbrirImagenTutor2 = new javax.swing.JLabel();
        ImagenTutor2 = new javax.swing.JLabel();
        loginBtn = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        RFID1 = new javax.swing.JLabel();
        brMasculino = new javax.swing.JRadioButton();
        brFemenino = new javax.swing.JRadioButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        parentesco1 = new javax.swing.JComboBox<>();
        parentesco2 = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        txtCI = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        userLabel1 = new javax.swing.JLabel();
        RFID2 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        ImagenEstudiante = new javax.swing.JLabel();
        btnAbrirImagenEstudiante = new javax.swing.JPanel();
        AbrirImagenEstudiante = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        passLabel5 = new javax.swing.JLabel();
        txtCITutor1 = new javax.swing.JTextField();
        jSeparator6 = new javax.swing.JSeparator();
        txtCITutor2 = new javax.swing.JTextField();
        passLabel6 = new javax.swing.JLabel();
        jSeparator11 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        btnConectar = new javax.swing.JPanel();
        txtConectar = new javax.swing.JLabel();
        title = new javax.swing.JLabel();
        favicon = new javax.swing.JLabel();

        setBackground(new java.awt.Color(191, 240, 194));

        jPanel1.setBackground(new java.awt.Color(242, 242, 242));

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
        userLabel.setForeground(new java.awt.Color(51, 51, 51));
        userLabel.setText("Nombre del Estudiante");
        panel2.add(userLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, -1, -1));

        txtNombreEstudiante.setBackground(new java.awt.Color(232, 255, 236));
        txtNombreEstudiante.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtNombreEstudiante.setForeground(new java.awt.Color(102, 102, 102));
        txtNombreEstudiante.setText("Ingrese su nombre");
        txtNombreEstudiante.setBorder(null);
        txtNombreEstudiante.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtNombreEstudianteMousePressed(evt);
            }
        });
        txtNombreEstudiante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreEstudianteKeyTyped(evt);
            }
        });
        panel2.add(txtNombreEstudiante, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 180, 30));

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        panel2.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 190, 10));

        jLabel1.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("Apellidos del Estudiante ");
        panel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, 180, 20));

        txtApellidoEstudiante.setBackground(new java.awt.Color(232, 255, 236));
        txtApellidoEstudiante.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtApellidoEstudiante.setForeground(new java.awt.Color(102, 102, 102));
        txtApellidoEstudiante.setText("Ingrese sus apellidos");
        txtApellidoEstudiante.setBorder(null);
        txtApellidoEstudiante.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtApellidoEstudianteMousePressed(evt);
            }
        });
        txtApellidoEstudiante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidoEstudianteKeyTyped(evt);
            }
        });
        panel2.add(txtApellidoEstudiante, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 170, 30));

        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));
        panel2.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 190, 10));

        jLabel7.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText("Genero del Estudiante");
        panel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 80, 170, -1));

        Tutor1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2" }));
        Tutor1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Tutor1ActionPerformed(evt);
            }
        });
        panel2.add(Tutor1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 140, 40, 20));

        passLabel2.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        passLabel2.setForeground(new java.awt.Color(51, 51, 51));
        passLabel2.setText("Nombre 1er Tutor");
        panel2.add(passLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 280, 190, 20));

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
        panel2.add(txt1TutorNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, 180, 30));

        jSeparator4.setForeground(new java.awt.Color(0, 0, 0));
        panel2.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, 190, 20));

        passLabel.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        passLabel.setForeground(new java.awt.Color(51, 51, 51));
        passLabel.setText("Apellidos 1er Tutor");
        panel2.add(passLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 350, 140, -1));

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
        panel2.add(txt1TutorApellidos, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 370, 170, 30));

        jSeparator5.setForeground(new java.awt.Color(0, 0, 0));
        panel2.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 400, 190, 20));

        jLabel5.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("Nro. Cel. 1er Tutor");
        panel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 410, 150, -1));

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
        panel2.add(txt1TutorCelular, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 430, 180, 30));

        jSeparator7.setForeground(new java.awt.Color(0, 0, 0));
        panel2.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 460, 190, 20));

        txt2TutorCelular.setBackground(new java.awt.Color(232, 255, 236));
        txt2TutorCelular.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txt2TutorCelular.setForeground(new java.awt.Color(102, 102, 102));
        txt2TutorCelular.setText("Ingrese su numero de telefono");
        txt2TutorCelular.setBorder(null);
        txt2TutorCelular.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txt2TutorCelularMousePressed(evt);
            }
        });
        txt2TutorCelular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt2TutorCelularActionPerformed(evt);
            }
        });
        txt2TutorCelular.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt2TutorCelularKeyTyped(evt);
            }
        });
        panel2.add(txt2TutorCelular, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 430, 180, 30));

        jSeparator8.setForeground(new java.awt.Color(0, 0, 0));
        panel2.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 460, 190, 20));

        jLabel6.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("Nro. Cel. 2do Tutor");
        panel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 410, 150, -1));

        jSeparator9.setForeground(new java.awt.Color(0, 0, 0));
        panel2.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 400, 190, 20));

        txt2TutorApellidos.setBackground(new java.awt.Color(232, 255, 236));
        txt2TutorApellidos.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txt2TutorApellidos.setForeground(new java.awt.Color(102, 102, 102));
        txt2TutorApellidos.setText("Ingrese sus apellidos");
        txt2TutorApellidos.setBorder(null);
        txt2TutorApellidos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txt2TutorApellidosMousePressed(evt);
            }
        });
        txt2TutorApellidos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt2TutorApellidosKeyTyped(evt);
            }
        });
        panel2.add(txt2TutorApellidos, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 370, 170, 30));

        passLabel3.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        passLabel3.setForeground(new java.awt.Color(51, 51, 51));
        passLabel3.setText("Apellidos 2do Tutor");
        panel2.add(passLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 350, 140, -1));

        jSeparator10.setForeground(new java.awt.Color(0, 0, 0));
        panel2.add(jSeparator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 330, 190, 20));

        txt2TutorNombre.setBackground(new java.awt.Color(232, 255, 236));
        txt2TutorNombre.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txt2TutorNombre.setForeground(new java.awt.Color(102, 102, 102));
        txt2TutorNombre.setText("Ingrese su nombre");
        txt2TutorNombre.setBorder(null);
        txt2TutorNombre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txt2TutorNombreMousePressed(evt);
            }
        });
        txt2TutorNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt2TutorNombreKeyTyped(evt);
            }
        });
        panel2.add(txt2TutorNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 300, 180, 30));

        passLabel4.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        passLabel4.setForeground(new java.awt.Color(51, 51, 51));
        passLabel4.setText("Nombre 2do Tutor");
        panel2.add(passLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 280, 190, 20));

        aula.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Verde", "Amarillo", "Azul", "Rosado", "Rojo" }));
        aula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aulaActionPerformed(evt);
            }
        });
        panel2.add(aula, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 140, -1, -1));

        jLabel2.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Aula");
        panel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 140, 60, -1));

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
            .addComponent(AbrirImagenTutor1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panel2.add(btnAbrirImagenTutor1, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 50, 90, 20));

        jLabel9.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 51, 51));
        jLabel9.setText("Imagen del 1er Tutor");
        panel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 20, 160, 20));
        panel2.add(ImagenTutor1, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 80, 100, 90));

        jLabel10.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setText("Imagen del 2er Tutor");
        panel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 200, 160, -1));

        btnAbrirImagenTutor2.setBackground(new java.awt.Color(0, 102, 15));
        btnAbrirImagenTutor2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 15)));

        AbrirImagenTutor2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        AbrirImagenTutor2.setForeground(new java.awt.Color(255, 255, 255));
        AbrirImagenTutor2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        AbrirImagenTutor2.setText("Abrir Imagen");
        AbrirImagenTutor2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AbrirImagenTutor2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AbrirImagenTutor2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                AbrirImagenTutor2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                AbrirImagenTutor2MouseExited(evt);
            }
        });

        javax.swing.GroupLayout btnAbrirImagenTutor2Layout = new javax.swing.GroupLayout(btnAbrirImagenTutor2);
        btnAbrirImagenTutor2.setLayout(btnAbrirImagenTutor2Layout);
        btnAbrirImagenTutor2Layout.setHorizontalGroup(
            btnAbrirImagenTutor2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(AbrirImagenTutor2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
        );
        btnAbrirImagenTutor2Layout.setVerticalGroup(
            btnAbrirImagenTutor2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(AbrirImagenTutor2, javax.swing.GroupLayout.DEFAULT_SIZE, 18, Short.MAX_VALUE)
        );

        panel2.add(btnAbrirImagenTutor2, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 230, 90, 20));
        panel2.add(ImagenTutor2, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 260, 100, 90));

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
            .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
        );
        loginBtnLayout.setVerticalGroup(
            loginBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        panel2.add(loginBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 410, 110, -1));

        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("Tarjeta RFID Tutor 1");
        panel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 310, 120, -1));

        RFID1.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        RFID1.setForeground(new java.awt.Color(204, 0, 0));
        RFID1.setText("NO REGISTRADO");
        panel2.add(RFID1, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 330, 160, 30));

        brMasculino.setBackground(new java.awt.Color(232, 255, 236));
        buttonGroup1.add(brMasculino);
        brMasculino.setForeground(new java.awt.Color(0, 0, 0));
        brMasculino.setText("MASCULINO");
        panel2.add(brMasculino, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 100, -1, -1));

        brFemenino.setBackground(new java.awt.Color(232, 255, 236));
        buttonGroup1.add(brFemenino);
        brFemenino.setForeground(new java.awt.Color(0, 0, 0));
        brFemenino.setText("FEMENINO");
        panel2.add(brFemenino, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 100, -1, -1));

        jLabel12.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 51, 51));
        jLabel12.setText("Nro. Tutores");
        panel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, -1, 20));

        jLabel13.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 51, 51));
        jLabel13.setText("Parentesco Tutor 1");
        panel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 140, -1));

        parentesco1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Madre", "Padre", "Abuelo", "Abuela", "Tio", "Tia", "Otro" }));
        parentesco1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                parentesco1ActionPerformed(evt);
            }
        });
        panel2.add(parentesco1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 170, -1, -1));

        parentesco2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Madre", "Padre", "Abuelo", "Abuela", "Tio", "Tia", "Otro" }));
        parentesco2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                parentesco2ActionPerformed(evt);
            }
        });
        panel2.add(parentesco2, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 170, -1, -1));

        jLabel14.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(51, 51, 51));
        jLabel14.setText("Parentesco Tutor 2");
        panel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 170, 150, -1));

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
        panel2.add(txtCI, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 30, 180, 30));

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        panel2.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 60, 190, 10));

        userLabel1.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        userLabel1.setForeground(new java.awt.Color(51, 51, 51));
        userLabel1.setText("CI. del Estudiante");
        panel2.add(userLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, -1, -1));

        RFID2.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        RFID2.setForeground(new java.awt.Color(204, 0, 0));
        RFID2.setText("NO REGISTRADO");
        panel2.add(RFID2, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 410, 160, 30));

        jLabel16.setForeground(new java.awt.Color(51, 51, 51));
        jLabel16.setText("Tarjeta RFID Tutor 2");
        panel2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 390, 120, -1));
        panel2.add(ImagenEstudiante, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 80, 100, 90));

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
            .addComponent(AbrirImagenEstudiante, javax.swing.GroupLayout.DEFAULT_SIZE, 18, Short.MAX_VALUE)
        );

        panel2.add(btnAbrirImagenEstudiante, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 50, 90, 20));

        jLabel17.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(51, 51, 51));
        jLabel17.setText("Imagen del Estudiante");
        panel2.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 20, 160, 20));

        passLabel5.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        passLabel5.setForeground(new java.awt.Color(51, 51, 51));
        passLabel5.setText("CI. 1er Tutor");
        panel2.add(passLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, 190, 20));

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
        panel2.add(txtCITutor1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, 180, 30));

        jSeparator6.setForeground(new java.awt.Color(0, 0, 0));
        panel2.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, 190, 20));

        txtCITutor2.setBackground(new java.awt.Color(232, 255, 236));
        txtCITutor2.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtCITutor2.setForeground(new java.awt.Color(102, 102, 102));
        txtCITutor2.setText("Ingrese su cedula de identidad");
        txtCITutor2.setBorder(null);
        txtCITutor2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtCITutor2MousePressed(evt);
            }
        });
        txtCITutor2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCITutor2KeyTyped(evt);
            }
        });
        panel2.add(txtCITutor2, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 230, 180, 30));

        passLabel6.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        passLabel6.setForeground(new java.awt.Color(51, 51, 51));
        passLabel6.setText("CI. 2do Tutor");
        panel2.add(passLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 210, 190, 20));

        jSeparator11.setForeground(new java.awt.Color(0, 0, 0));
        panel2.add(jSeparator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 260, 190, 20));

        jLabel8.setFont(new java.awt.Font("Arial Black", 1, 13)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setText("ARDUINO :");
        panel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 210, 100, 20));

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

        panel2.add(btnConectar, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 250, 90, 30));

        title.setFont(new java.awt.Font("Cambria", 1, 36)); // NOI18N
        title.setForeground(new java.awt.Color(0, 52, 0));
        title.setText("REGISTRO DE ESTUDIANTES");

        favicon.setFont(new java.awt.Font("Roboto Black", 1, 24)); // NOI18N
        favicon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/images/favicon_1.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(favicon)
                        .addGap(33, 33, 33)
                        .addComponent(title))
                    .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, 830, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(favicon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cerrar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cerrar1ActionPerformed

    }//GEN-LAST:event_cerrar1ActionPerformed

    private void txtNombreEstudianteMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNombreEstudianteMousePressed
        if (txtNombreEstudiante.getText().equals("Ingrese su nombre")) {
            txtNombreEstudiante.setText("");
            txtNombreEstudiante.setForeground(Color.black);
        }
        if (txtApellidoEstudiante.getText().isEmpty()) {
            txtApellidoEstudiante.setText("Ingrese sus apellidos");
            txtApellidoEstudiante.setForeground(Color.gray);
        }
        if (txtCI.getText().isEmpty()) {
            txtCI.setText("Ingrese su cedula de identidad");
            txtCI.setForeground(Color.gray);
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
        if (txt2TutorNombre.getText().isEmpty()) {
            txt2TutorNombre.setText("Ingrese su nombre");
            txt2TutorNombre.setForeground(Color.gray);
        }
        if (txt2TutorApellidos.getText().isEmpty()) {
            txt2TutorApellidos.setText("Ingrese sus apellidos");
            txt2TutorApellidos.setForeground(Color.gray);
        }
        if (txt2TutorCelular.getText().isEmpty()) {
            txt2TutorCelular.setText("Ingrese su numero de telefono");
            txt2TutorCelular.setForeground(Color.gray);
        }
        if (txtCITutor1.getText().isEmpty()) {
            txtCITutor1.setText("Ingrese su cedula de identidad");
            txtCITutor1.setForeground(Color.gray);
        }
        if (txtCITutor2.getText().isEmpty()) {
            txtCITutor2.setText("Ingrese su cedula de identidad");
            txtCITutor2.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txtNombreEstudianteMousePressed

    private void txtApellidoEstudianteMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtApellidoEstudianteMousePressed
        if (txtApellidoEstudiante.getText().equals("Ingrese sus apellidos")) {
            txtApellidoEstudiante.setText("");
            txtApellidoEstudiante.setForeground(Color.black);
        }

        if (txtNombreEstudiante.getText().isEmpty()) {
            txtNombreEstudiante.setText("Ingrese su nombre");
            txtNombreEstudiante.setForeground(Color.gray);
        }
        if (txtCI.getText().isEmpty()) {
            txtCI.setText("Ingrese su cedula de identidad");
            txtCI.setForeground(Color.gray);
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
        if (txt2TutorNombre.getText().isEmpty()) {
            txt2TutorNombre.setText("Ingrese su nombre");
            txt2TutorNombre.setForeground(Color.gray);
        }
        if (txt2TutorApellidos.getText().isEmpty()) {
            txt2TutorApellidos.setText("Ingrese sus apellidos");
            txt2TutorApellidos.setForeground(Color.gray);
        }
        if (txt2TutorCelular.getText().isEmpty()) {
            txt2TutorCelular.setText("Ingrese su numero de telefono");
            txt2TutorCelular.setForeground(Color.gray);
        }
        if (txtCITutor1.getText().isEmpty()) {
            txtCITutor1.setText("Ingrese su cedula de identidad");
            txtCITutor1.setForeground(Color.gray);
        }
        if (txtCITutor2.getText().isEmpty()) {
            txtCITutor2.setText("Ingrese su cedula de identidad");
            txtCITutor2.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txtApellidoEstudianteMousePressed

    private void aulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aulaActionPerformed

    }//GEN-LAST:event_aulaActionPerformed

    private void btnGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseClicked
        try {
            registroEstudiante();
        } catch (Exception e) {
            mensajeError(" Error al registrar datos");
        }
    }//GEN-LAST:event_btnGuardarMouseClicked

    private void btnGuardarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseEntered
        loginBtn.setBackground(new Color(0, 179, 36));
    }//GEN-LAST:event_btnGuardarMouseEntered

    private void btnGuardarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseExited
        loginBtn.setBackground(new Color(0, 102, 15));
    }//GEN-LAST:event_btnGuardarMouseExited

    private void txt1TutorNombreMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt1TutorNombreMousePressed
        if (txt1TutorNombre.getText().equals("Ingrese su nombre")) {
            txt1TutorNombre.setText("");
            txt1TutorNombre.setForeground(Color.black);
        }
        if (txtNombreEstudiante.getText().isEmpty()) {
            txtNombreEstudiante.setText("Ingrese su nombre");
            txtNombreEstudiante.setForeground(Color.gray);
        }
        if (txtApellidoEstudiante.getText().isEmpty()) {
            txtApellidoEstudiante.setText("Ingrese sus apellidos");
            txtApellidoEstudiante.setForeground(Color.gray);
        }
        if (txtCI.getText().isEmpty()) {
            txtCI.setText("Ingrese su cedula de identidad");
            txtCI.setForeground(Color.gray);
        }
        if (txt1TutorApellidos.getText().isEmpty()) {
            txt1TutorApellidos.setText("Ingrese sus apellidos");
            txt1TutorApellidos.setForeground(Color.gray);
        }
        if (txt1TutorCelular.getText().isEmpty()) {
            txt1TutorCelular.setText("Ingrese su numero de telefono");
            txt1TutorCelular.setForeground(Color.gray);
        }
        if (txt2TutorNombre.getText().isEmpty()) {
            txt2TutorNombre.setText("Ingrese su nombre");
            txt2TutorNombre.setForeground(Color.gray);
        }
        if (txt2TutorApellidos.getText().isEmpty()) {
            txt2TutorApellidos.setText("Ingrese sus apellidos");
            txt2TutorApellidos.setForeground(Color.gray);
        }
        if (txt2TutorCelular.getText().isEmpty()) {
            txt2TutorCelular.setText("Ingrese su numero de telefono");
            txt2TutorCelular.setForeground(Color.gray);
        }
        if (txtCITutor1.getText().isEmpty()) {
            txtCITutor1.setText("Ingrese su cedula de identidad");
            txtCITutor1.setForeground(Color.gray);
        }
        if (txtCITutor2.getText().isEmpty()) {
            txtCITutor2.setText("Ingrese su cedula de identidad");
            txtCITutor2.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txt1TutorNombreMousePressed

    private void txt1TutorApellidosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt1TutorApellidosMousePressed
        if (txt1TutorApellidos.getText().equals("Ingrese sus apellidos")) {
            txt1TutorApellidos.setText("");
            txt1TutorApellidos.setForeground(Color.black);
        }
        if (txt1TutorNombre.getText().isEmpty()) {
            txt1TutorNombre.setText("Ingrese su nombre");
            txt1TutorNombre.setForeground(Color.gray);
        }
        if (txtNombreEstudiante.getText().isEmpty()) {
            txtNombreEstudiante.setText("Ingrese su nombre");
            txtNombreEstudiante.setForeground(Color.gray);
        }
        if (txtApellidoEstudiante.getText().isEmpty()) {
            txtApellidoEstudiante.setText("Ingrese sus apellidos");
            txtApellidoEstudiante.setForeground(Color.gray);
        }
        if (txtCI.getText().isEmpty()) {
            txtCI.setText("Ingrese su cedula de identidad");
            txtCI.setForeground(Color.gray);
        }

        if (txt1TutorCelular.getText().isEmpty()) {
            txt1TutorCelular.setText("Ingrese su numero de telefono");
            txt1TutorCelular.setForeground(Color.gray);
        }
        if (txt2TutorNombre.getText().isEmpty()) {
            txt2TutorNombre.setText("Ingrese su nombre");
            txt2TutorNombre.setForeground(Color.gray);
        }
        if (txt2TutorApellidos.getText().isEmpty()) {
            txt2TutorApellidos.setText("Ingrese sus apellidos");
            txt2TutorApellidos.setForeground(Color.gray);
        }
        if (txt2TutorCelular.getText().isEmpty()) {
            txt2TutorCelular.setText("Ingrese su numero de telefono");
            txt2TutorCelular.setForeground(Color.gray);
        }
        if (txtCITutor1.getText().isEmpty()) {
            txtCITutor1.setText("Ingrese su cedula de identidad");
            txtCITutor1.setForeground(Color.gray);
        }
        if (txtCITutor2.getText().isEmpty()) {
            txtCITutor2.setText("Ingrese su cedula de identidad");
            txtCITutor2.setForeground(Color.gray);
        }

    }//GEN-LAST:event_txt1TutorApellidosMousePressed

    private void txt1TutorCelularMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt1TutorCelularMousePressed
        if (txt1TutorCelular.getText().equals("Ingrese su numero de telefono")) {
            txt1TutorCelular.setText("");
            txt1TutorCelular.setForeground(Color.black);
        }
        if (txtNombreEstudiante.getText().isEmpty()) {
            txtNombreEstudiante.setText("Ingrese su nombre");
            txtNombreEstudiante.setForeground(Color.gray);
        }
        if (txtApellidoEstudiante.getText().isEmpty()) {
            txtApellidoEstudiante.setText("Ingrese sus apellidos");
            txtApellidoEstudiante.setForeground(Color.gray);
        }
        if (txtCI.getText().isEmpty()) {
            txtCI.setText("Ingrese su cedula de identidad");
            txtCI.setForeground(Color.gray);
        }
        if (txt1TutorNombre.getText().isEmpty()) {
            txt1TutorNombre.setText("Ingrese su nombre");
            txt1TutorNombre.setForeground(Color.gray);
        }
        if (txt1TutorApellidos.getText().isEmpty()) {
            txt1TutorApellidos.setText("Ingrese sus apellidos");
            txt1TutorApellidos.setForeground(Color.gray);
        }
        if (txt2TutorNombre.getText().isEmpty()) {
            txt2TutorNombre.setText("Ingrese su nombre");
            txt2TutorNombre.setForeground(Color.gray);
        }
        if (txt2TutorApellidos.getText().isEmpty()) {
            txt2TutorApellidos.setText("Ingrese sus apellidos");
            txt2TutorApellidos.setForeground(Color.gray);
        }
        if (txt2TutorCelular.getText().isEmpty()) {
            txt2TutorCelular.setText("Ingrese su numero de telefono");
            txt2TutorCelular.setForeground(Color.gray);
        }
        if (txtCITutor1.getText().isEmpty()) {
            txtCITutor1.setText("Ingrese su cedula de identidad");
            txtCITutor1.setForeground(Color.gray);
        }
        if (txtCITutor2.getText().isEmpty()) {
            txtCITutor2.setText("Ingrese su cedula de identidad");
            txtCITutor2.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txt1TutorCelularMousePressed

    private void txt1TutorCelularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt1TutorCelularActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt1TutorCelularActionPerformed

    private void txt2TutorCelularMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt2TutorCelularMousePressed
        if (txt2TutorCelular.getText().equals("Ingrese su numero de telefono")) {
            txt2TutorCelular.setText("");
            txt2TutorCelular.setForeground(Color.black);
        }
        if (txtNombreEstudiante.getText().isEmpty()) {
            txtNombreEstudiante.setText("Ingrese su nombre");
            txtNombreEstudiante.setForeground(Color.gray);
        }
        if (txtApellidoEstudiante.getText().isEmpty()) {
            txtApellidoEstudiante.setText("Ingrese sus apellidos");
            txtApellidoEstudiante.setForeground(Color.gray);
        }
        if (txtCI.getText().isEmpty()) {
            txtCI.setText("Ingrese su cedula de identidad");
            txtCI.setForeground(Color.gray);
        }
        if (txt1TutorNombre.getText().isEmpty()) {
            txt1TutorNombre.setText("Ingrese su nombre");
            txt1TutorNombre.setForeground(Color.gray);
        }
        if (txt1TutorApellidos.getText().isEmpty()) {
            txt1TutorApellidos.setText("Ingrese sus apellidos");
            txt1TutorApellidos.setForeground(Color.gray);
        }
        if (txt2TutorNombre.getText().isEmpty()) {
            txt2TutorNombre.setText("Ingrese su nombre");
            txt2TutorNombre.setForeground(Color.gray);
        }
        if (txt2TutorApellidos.getText().isEmpty()) {
            txt2TutorApellidos.setText("Ingrese sus apellidos");
            txt2TutorApellidos.setForeground(Color.gray);
        }
        if (txt1TutorCelular.getText().isEmpty()) {
            txt1TutorCelular.setText("Ingrese su numero de telefono");
            txt1TutorCelular.setForeground(Color.gray);
        }
        if (txtCITutor1.getText().isEmpty()) {
            txtCITutor1.setText("Ingrese su cedula de identidad");
            txtCITutor1.setForeground(Color.gray);
        }
        if (txtCITutor2.getText().isEmpty()) {
            txtCITutor2.setText("Ingrese su cedula de identidad");
            txtCITutor2.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txt2TutorCelularMousePressed

    private void txt2TutorCelularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt2TutorCelularActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt2TutorCelularActionPerformed

    private void txt2TutorApellidosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt2TutorApellidosMousePressed
        if (txt2TutorApellidos.getText().equals("Ingrese sus apellidos")) {
            txt2TutorApellidos.setText("");
            txt2TutorApellidos.setForeground(Color.black);
        }
        if (txt1TutorNombre.getText().isEmpty()) {
            txt1TutorNombre.setText("Ingrese su nombre");
            txt1TutorNombre.setForeground(Color.gray);
        }
        if (txtNombreEstudiante.getText().isEmpty()) {
            txtNombreEstudiante.setText("Ingrese su nombre");
            txtNombreEstudiante.setForeground(Color.gray);
        }
        if (txtApellidoEstudiante.getText().isEmpty()) {
            txtApellidoEstudiante.setText("Ingrese sus apellidos");
            txtApellidoEstudiante.setForeground(Color.gray);
        }
        if (txtCI.getText().isEmpty()) {
            txtCI.setText("Ingrese su cedula de identidad");
            txtCI.setForeground(Color.gray);
        }

        if (txt1TutorCelular.getText().isEmpty()) {
            txt1TutorCelular.setText("Ingrese su numero de telefono");
            txt1TutorCelular.setForeground(Color.gray);
        }
        if (txt2TutorNombre.getText().isEmpty()) {
            txt2TutorNombre.setText("Ingrese su nombre");
            txt2TutorNombre.setForeground(Color.gray);
        }
        if (txt1TutorApellidos.getText().isEmpty()) {
            txt1TutorApellidos.setText("Ingrese sus apellidos");
            txt1TutorApellidos.setForeground(Color.gray);
        }
        if (txt2TutorCelular.getText().isEmpty()) {
            txt2TutorCelular.setText("Ingrese su numero de telefono");
            txt2TutorCelular.setForeground(Color.gray);
        }
        if (txtCITutor1.getText().isEmpty()) {
            txtCITutor1.setText("Ingrese su cedula de identidad");
            txtCITutor1.setForeground(Color.gray);
        }
        if (txtCITutor2.getText().isEmpty()) {
            txtCITutor2.setText("Ingrese su cedula de identidad");
            txtCITutor2.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txt2TutorApellidosMousePressed

    private void txt2TutorNombreMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt2TutorNombreMousePressed
        if (txt2TutorNombre.getText().equals("Ingrese su nombre")) {
            txt2TutorNombre.setText("");
            txt2TutorNombre.setForeground(Color.black);
        }
        if (txtNombreEstudiante.getText().isEmpty()) {
            txtNombreEstudiante.setText("Ingrese su nombre");
            txtNombreEstudiante.setForeground(Color.gray);
        }
        if (txtApellidoEstudiante.getText().isEmpty()) {
            txtApellidoEstudiante.setText("Ingrese sus apellidos");
            txtApellidoEstudiante.setForeground(Color.gray);
        }
        if (txtCI.getText().isEmpty()) {
            txtCI.setText("Ingrese su cedula de identidad");
            txtCI.setForeground(Color.gray);
        }
        if (txt1TutorApellidos.getText().isEmpty()) {
            txt1TutorApellidos.setText("Ingrese sus apellidos");
            txt1TutorApellidos.setForeground(Color.gray);
        }
        if (txt1TutorCelular.getText().isEmpty()) {
            txt1TutorCelular.setText("Ingrese su numero de telefono");
            txt1TutorCelular.setForeground(Color.gray);
        }
        if (txt1TutorNombre.getText().isEmpty()) {
            txt1TutorNombre.setText("Ingrese su nombre");
            txt1TutorNombre.setForeground(Color.gray);
        }
        if (txt2TutorApellidos.getText().isEmpty()) {
            txt2TutorApellidos.setText("Ingrese sus apellidos");
            txt2TutorApellidos.setForeground(Color.gray);
        }
        if (txt2TutorCelular.getText().isEmpty()) {
            txt2TutorCelular.setText("Ingrese su numero de telefono");
            txt2TutorCelular.setForeground(Color.gray);
        }
        if (txtCITutor1.getText().isEmpty()) {
            txtCITutor1.setText("Ingrese su cedula de identidad");
            txtCITutor1.setForeground(Color.gray);
        }
        if (txtCITutor2.getText().isEmpty()) {
            txtCITutor2.setText("Ingrese su cedula de identidad");
            txtCITutor2.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txt2TutorNombreMousePressed

    private void Tutor1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Tutor1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Tutor1ActionPerformed

    private void AbrirImagenTutor1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AbrirImagenTutor1MouseClicked
        JFileChooser j = new JFileChooser();
        j.setCurrentDirectory(new File("Imagenes/"));
        int ap = j.showOpenDialog(this);

        if (ap == JFileChooser.APPROVE_OPTION) {
            rutaTutor1 = j.getSelectedFile().getAbsolutePath();
            ImagenTutor1.setIcon(new ImageIcon(rutaTutor1));
        }
    }//GEN-LAST:event_AbrirImagenTutor1MouseClicked

    private void AbrirImagenTutor1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AbrirImagenTutor1MouseEntered
        btnAbrirImagenTutor1.setBackground(new Color(0, 179, 36));
    }//GEN-LAST:event_AbrirImagenTutor1MouseEntered

    private void AbrirImagenTutor1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AbrirImagenTutor1MouseExited
        btnAbrirImagenTutor1.setBackground(new Color(0, 102, 15));
    }//GEN-LAST:event_AbrirImagenTutor1MouseExited

    private void AbrirImagenTutor2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AbrirImagenTutor2MouseClicked
        JFileChooser j = new JFileChooser();
        j.setCurrentDirectory(new File("Imagenes/"));
        int ap = j.showOpenDialog(this);

        if (ap == JFileChooser.APPROVE_OPTION) {
            rutaTutor2 = j.getSelectedFile().getAbsolutePath();
            ImagenTutor2.setIcon(new ImageIcon(rutaTutor2));
        }
    }//GEN-LAST:event_AbrirImagenTutor2MouseClicked

    private void AbrirImagenTutor2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AbrirImagenTutor2MouseEntered
        btnAbrirImagenTutor2.setBackground(new Color(0, 179, 36));
    }//GEN-LAST:event_AbrirImagenTutor2MouseEntered

    private void AbrirImagenTutor2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AbrirImagenTutor2MouseExited
        btnAbrirImagenTutor2.setBackground(new Color(0, 102, 15));
    }//GEN-LAST:event_AbrirImagenTutor2MouseExited

    private void parentesco1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_parentesco1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_parentesco1ActionPerformed

    private void parentesco2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_parentesco2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_parentesco2ActionPerformed

    private void txtCIMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCIMousePressed

        if (txtCI.getText().equals("Ingrese su cedula de identidad")) {
            txtCI.setText("");
            txtCI.setForeground(Color.black);
        }
        if (txtNombreEstudiante.getText().isEmpty()) {
            txtNombreEstudiante.setText("Ingrese su nombre");
            txtNombreEstudiante.setForeground(Color.gray);
        }
        if (txtApellidoEstudiante.getText().isEmpty()) {
            txtApellidoEstudiante.setText("Ingrese sus apellidos");
            txtApellidoEstudiante.setForeground(Color.gray);
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
        if (txt2TutorNombre.getText().isEmpty()) {
            txt2TutorNombre.setText("Ingrese su nombre");
            txt2TutorNombre.setForeground(Color.gray);
        }
        if (txt2TutorApellidos.getText().isEmpty()) {
            txt2TutorApellidos.setText("Ingrese sus apellidos");
            txt2TutorApellidos.setForeground(Color.gray);
        }
        if (txt2TutorCelular.getText().isEmpty()) {
            txt2TutorCelular.setText("Ingrese su numero de telefono");
            txt2TutorCelular.setForeground(Color.gray);
        }
        if (txtCITutor1.getText().isEmpty()) {
            txtCITutor1.setText("Ingrese su cedula de identidad");
            txtCITutor1.setForeground(Color.gray);
        }
        if (txtCITutor2.getText().isEmpty()) {
            txtCITutor2.setText("Ingrese su cedula de identidad");
            txtCITutor2.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txtCIMousePressed

    private void txtCIMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCIMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCIMouseReleased

    private void AbrirImagenEstudianteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AbrirImagenEstudianteMouseClicked
        JFileChooser j = new JFileChooser();
        j.setCurrentDirectory(new File("Imagenes/"));
        int ap = j.showOpenDialog(this);

        if (ap == JFileChooser.APPROVE_OPTION) {
            rutaEstudiante = j.getSelectedFile().getAbsolutePath();
            ImagenEstudiante.setIcon(new ImageIcon(rutaEstudiante));
        }
    }//GEN-LAST:event_AbrirImagenEstudianteMouseClicked

    private void AbrirImagenEstudianteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AbrirImagenEstudianteMouseEntered
        btnAbrirImagenEstudiante.setBackground(new Color(0, 179, 36));
    }//GEN-LAST:event_AbrirImagenEstudianteMouseEntered

    private void AbrirImagenEstudianteMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AbrirImagenEstudianteMouseExited
        btnAbrirImagenEstudiante.setBackground(new Color(0, 102, 15));
    }//GEN-LAST:event_AbrirImagenEstudianteMouseExited

    private void txtCITutor1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCITutor1MousePressed
        if (txtCITutor1.getText().equals("Ingrese su cedula de identidad")) {
            txtCITutor1.setText("");
            txtCITutor1.setForeground(Color.black);
        }
        if (txtNombreEstudiante.getText().isEmpty()) {
            txtNombreEstudiante.setText("Ingrese su nombre");
            txtNombreEstudiante.setForeground(Color.gray);
        }
        if (txtApellidoEstudiante.getText().isEmpty()) {
            txtApellidoEstudiante.setText("Ingrese sus apellidos");
            txtApellidoEstudiante.setForeground(Color.gray);
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
        if (txt2TutorNombre.getText().isEmpty()) {
            txt2TutorNombre.setText("Ingrese su nombre");
            txt2TutorNombre.setForeground(Color.gray);
        }
        if (txt2TutorApellidos.getText().isEmpty()) {
            txt2TutorApellidos.setText("Ingrese sus apellidos");
            txt2TutorApellidos.setForeground(Color.gray);
        }
        if (txt2TutorCelular.getText().isEmpty()) {
            txt2TutorCelular.setText("Ingrese su numero de telefono");
            txt2TutorCelular.setForeground(Color.gray);
        }
        if (txtCI.getText().isEmpty()) {
            txtCI.setText("Ingrese su cedula de identidad");
            txtCI.setForeground(Color.gray);
        }
        if (txtCITutor2.getText().isEmpty()) {
            txtCITutor2.setText("Ingrese su cedula de identidad");
            txtCITutor2.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txtCITutor1MousePressed

    private void txtCITutor2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCITutor2MousePressed
        if (txtCITutor2.getText().equals("Ingrese su cedula de identidad")) {
            txtCITutor2.setText("");
            txtCITutor2.setForeground(Color.black);
        }
        if (txtNombreEstudiante.getText().isEmpty()) {
            txtNombreEstudiante.setText("Ingrese su nombre");
            txtNombreEstudiante.setForeground(Color.gray);
        }
        if (txtApellidoEstudiante.getText().isEmpty()) {
            txtApellidoEstudiante.setText("Ingrese sus apellidos");
            txtApellidoEstudiante.setForeground(Color.gray);
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
        if (txt2TutorNombre.getText().isEmpty()) {
            txt2TutorNombre.setText("Ingrese su nombre");
            txt2TutorNombre.setForeground(Color.gray);
        }
        if (txt2TutorApellidos.getText().isEmpty()) {
            txt2TutorApellidos.setText("Ingrese sus apellidos");
            txt2TutorApellidos.setForeground(Color.gray);
        }
        if (txt2TutorCelular.getText().isEmpty()) {
            txt2TutorCelular.setText("Ingrese su numero de telefono");
            txt2TutorCelular.setForeground(Color.gray);
        }
        if (txtCI.getText().isEmpty()) {
            txtCI.setText("Ingrese su cedula de identidad");
            txtCI.setForeground(Color.gray);
        }
        if (txtCITutor1.getText().isEmpty()) {
            txtCITutor1.setText("Ingrese su cedula de identidad");
            txtCITutor1.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txtCITutor2MousePressed

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

    private void txtCIKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCIKeyTyped
        char validar = evt.getKeyChar();

        if (Character.isLetter(validar)) {
            getToolkit().beep();
            evt.consume();

            mensajeAdvertencia("¡Debe Ingresar solo Numeros!");
        }
    }//GEN-LAST:event_txtCIKeyTyped

    private void txtCITutor1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCITutor1KeyTyped
        char validar = evt.getKeyChar();

        if (Character.isLetter(validar)) {
            getToolkit().beep();
            evt.consume();

            mensajeAdvertencia("¡Debe Ingresar solo Numeros!");
        }
    }//GEN-LAST:event_txtCITutor1KeyTyped

    private void txtCITutor2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCITutor2KeyTyped
        char validar = evt.getKeyChar();

        if (Character.isLetter(validar)) {
            getToolkit().beep();
            evt.consume();

            mensajeAdvertencia("¡Debe Ingresar solo Numeros!");
        }
    }//GEN-LAST:event_txtCITutor2KeyTyped

    private void txt1TutorCelularKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt1TutorCelularKeyTyped
        char validar = evt.getKeyChar();

        if (Character.isLetter(validar)) {
            getToolkit().beep();
            evt.consume();

            mensajeAdvertencia("¡Debe Ingresar solo Numeros!");
        }
    }//GEN-LAST:event_txt1TutorCelularKeyTyped

    private void txt2TutorCelularKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt2TutorCelularKeyTyped
        char validar = evt.getKeyChar();

        if (Character.isLetter(validar)) {
            getToolkit().beep();
            evt.consume();

            mensajeAdvertencia("¡Debe Ingresar solo Numeros!");
        }
    }//GEN-LAST:event_txt2TutorCelularKeyTyped

    private void txtNombreEstudianteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreEstudianteKeyTyped
        char validar = evt.getKeyChar();

        if (Character.isDigit(validar)) {
            getToolkit().beep();
            evt.consume();

            mensajeAdvertencia("¡Debe Ingresar solo Texto!");
        }
    }//GEN-LAST:event_txtNombreEstudianteKeyTyped

    private void txtApellidoEstudianteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoEstudianteKeyTyped
        char validar = evt.getKeyChar();

        if (Character.isDigit(validar)) {
            getToolkit().beep();
            evt.consume();

            mensajeAdvertencia("¡Debe Ingresar solo Texto!");
        }
    }//GEN-LAST:event_txtApellidoEstudianteKeyTyped

    private void txt1TutorNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt1TutorNombreKeyTyped
        char validar = evt.getKeyChar();

        if (Character.isDigit(validar)) {
            getToolkit().beep();
            evt.consume();

            mensajeAdvertencia("¡Debe Ingresar solo Texto!");
        }
    }//GEN-LAST:event_txt1TutorNombreKeyTyped

    private void txt1TutorApellidosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt1TutorApellidosKeyTyped
        char validar = evt.getKeyChar();

        if (Character.isDigit(validar)) {
            getToolkit().beep();
            evt.consume();

            mensajeAdvertencia("¡Debe Ingresar solo Texto!");
        }
    }//GEN-LAST:event_txt1TutorApellidosKeyTyped

    private void txt2TutorNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt2TutorNombreKeyTyped
        char validar = evt.getKeyChar();

        if (Character.isDigit(validar)) {
            getToolkit().beep();
            evt.consume();

            mensajeAdvertencia("¡Debe Ingresar solo Texto!");
        }
    }//GEN-LAST:event_txt2TutorNombreKeyTyped

    private void txt2TutorApellidosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt2TutorApellidosKeyTyped
        char validar = evt.getKeyChar();

        if (Character.isDigit(validar)) {
            getToolkit().beep();
            evt.consume();

            mensajeAdvertencia("¡Debe Ingresar solo Texto!");
        }
    }//GEN-LAST:event_txt2TutorApellidosKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AbrirImagenEstudiante;
    private javax.swing.JLabel AbrirImagenTutor1;
    private javax.swing.JLabel AbrirImagenTutor2;
    private javax.swing.JLabel ImagenEstudiante;
    private javax.swing.JLabel ImagenTutor1;
    private javax.swing.JLabel ImagenTutor2;
    private javax.swing.JLabel RFID1;
    private javax.swing.JLabel RFID2;
    private javax.swing.JComboBox<String> Tutor1;
    private javax.swing.JComboBox<String> aula;
    private javax.swing.JRadioButton brFemenino;
    private javax.swing.JRadioButton brMasculino;
    private javax.swing.JPanel btnAbrirImagenEstudiante;
    private javax.swing.JPanel btnAbrirImagenTutor1;
    private javax.swing.JPanel btnAbrirImagenTutor2;
    private javax.swing.JPanel btnConectar;
    private javax.swing.JLabel btnGuardar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton cerrar1;
    private javax.swing.JLabel favicon;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JPanel loginBtn;
    private org.edisoncor.gui.panel.Panel panel2;
    private javax.swing.JComboBox<String> parentesco1;
    private javax.swing.JComboBox<String> parentesco2;
    private javax.swing.JLabel passLabel;
    private javax.swing.JLabel passLabel2;
    private javax.swing.JLabel passLabel3;
    private javax.swing.JLabel passLabel4;
    private javax.swing.JLabel passLabel5;
    private javax.swing.JLabel passLabel6;
    private javax.swing.JLabel title;
    private javax.swing.JTextField txt1TutorApellidos;
    private javax.swing.JTextField txt1TutorCelular;
    private javax.swing.JTextField txt1TutorNombre;
    private javax.swing.JTextField txt2TutorApellidos;
    private javax.swing.JTextField txt2TutorCelular;
    private javax.swing.JTextField txt2TutorNombre;
    private javax.swing.JTextField txtApellidoEstudiante;
    private javax.swing.JTextField txtCI;
    private javax.swing.JTextField txtCITutor1;
    private javax.swing.JTextField txtCITutor2;
    private javax.swing.JLabel txtConectar;
    private javax.swing.JTextField txtNombreEstudiante;
    private javax.swing.JLabel userLabel;
    private javax.swing.JLabel userLabel1;
    // End of variables declaration//GEN-END:variables
}
