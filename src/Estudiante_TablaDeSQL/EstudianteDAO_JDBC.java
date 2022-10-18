package Estudiante_TablaDeSQL;

import static Estudiante_TablaDeSQL.Conexion.*;
import Empleado_TablaDeSQL.Empleado;
import Empleado_TablaDeSQL.EmpleadoDAO_JDBC;
import Empleado_TablaDeSQL.Persona;
import Empleado_TablaDeSQL.Profesor;
import java.awt.Component;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class EstudianteDAO_JDBC implements EstudianteDAO {

    //Este atributo es para manejar la transaccion desde una clase externa 
    private Connection conexionTransaccional;

    private static final String SQL_SELECT_ESTUDIANTE = "SELECT estudiante.id_estudiante, estudiante.ci, persona.nombre, persona.apellidos,estudiante.tutor,\n"
            + "	estudiante.genero, estudiante.aula, persona.foto\n"
            + "FROM persona INNER JOIN estudiante ON persona.ci=estudiante.ci\n"
            + "ORDER BY estudiante.id_estudiante;";

    private static final String SQL_SELECT_ESTUDIANTE_EXPORTAR = "SELECT estudiante.id_estudiante, estudiante.ci, persona.nombre, persona.apellidos,estudiante.tutor,\n"
            + "	estudiante.genero, estudiante.aula\n"
            + "FROM persona INNER JOIN estudiante ON persona.ci=estudiante.ci\n"
            + "ORDER BY estudiante.id_estudiante;";
    
    private static final String SQL_SELECT = "SELECT * FROM public.alumnos";
    
    private static final String SQL_SELECT_TUTOR_EXPORTAR = "SELECT tutor.id_tutor, tutor.ci,tutor.ci_estudiante, tutor.tutor, persona.nombre, persona.apellidos,\n"
            + "	tutor.celular, tutor.parentesco\n"
            + "FROM persona INNER JOIN tutor ON persona.ci=tutor.ci\n"
            + "ORDER BY tutor.id_tutor;";

    private static final String SQL_SELECT_TUTOR = "SELECT tutor.id_tutor, tutor.ci,tutor.ci_estudiante, tutor.tutor, persona.nombre, persona.apellidos,\n"
            + "	tutor.celular, tutor.parentesco, persona.foto\n"
            + "FROM persona INNER JOIN tutor ON persona.ci=tutor.ci\n"
            + "ORDER BY tutor.id_tutor;";

    private static String SQL_INSERT_PERSONA = "INSERT INTO persona(\n"
            + "       ci, nombre, apellidos, foto)\n"
            + "	VALUES (?, ?, ?, ?);";

    private static String SQL_INSERT_ESTUDIANTE = "INSERT INTO estudiante(\n"
            + "       ci, aula, tutor, genero)\n"
            + "	VALUES (?, ?, ?, ?);";

    private static String SQL_INSERT_TUTOR = "INSERT INTO tutor(\n"
            + "       ci_estudiante, ci, tutor, codtarjeta, celular, parentesco)\n"
            + "	VALUES (?, ?, ?, ?, ?, ?);";

    private static final String SQL_UPDATE_PERSONA = "UPDATE persona\n"
            + "	SET ci=?, nombre=?, apellidos=?, foto=?\n"
            + "	WHERE id_persona = ?;";

    private static final String SQL_UPDATE_PERSONA_2 = "UPDATE persona\n"
            + "	SET ci=?, nombre=?, apellidos=?\n"
            + "	WHERE id_persona = ?;";

    private static final String SQL_UPDATE_ESTUDIANTE = "UPDATE estudiante\n"
            + "	SET ci=?, aula=?, tutor=?, genero=?\n"
            + "	WHERE id_estudiante = ?;";

    private static final String SQL_UPDATE_TUTOR = "UPDATE tutor\n"
            + "	SET ci=?, codtarjeta=?, celular=?, parentesco=?\n"
            + "	WHERE id_tutor=?;";

    private static final String SQL_UPDATE = "UPDATE public.alumno SET NOMBRE = ?,APELLIDOS = ?,GENERO = ?, CARGO = ? WHERE CODIGO = ?;";
    private static final String SQL_DELETE = "DELETE FROM public.alumno WHERE CODIGO = ?;";

    public EstudianteDAO_JDBC() {
    }

    //Este constructor es para manejar la transaccion desde una clase externa cuando se hara comit o rolbak
    public EstudianteDAO_JDBC(Connection conexionTransaccional) {
        this.conexionTransaccional = conexionTransaccional;
    }

    //Este metodo es INSERT=Nos permite agregar informacion a la tabla en SQL y en aqui sera insertar
    //recibiremos un objeto de tipo persona y es ese objeto que se guardara en la base de datos
    //Int para ver cuantos registros hay 
    @Override
    public int insertarPersona(Persona persona, String foto) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        int registros = 0;
        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : getConection();

            FileInputStream fot;
            File file = new File(foto);
            fot = new FileInputStream(file);

//            SQL_INSERT = SQL_INSERT + "VALUES("+Estudiante.getCodigo() +",'"+Estudiante.getNombre()
//                    +"','"+Estudiante.getApellidos()+"','"+Estudiante.getGenero()+"','"+Estudiante.getCargo()+"');";
            ps = conn.prepareStatement(SQL_INSERT_PERSONA);
//            Aqui recibimos el obejto persona y lo añadimos a la tabla persona de SQL con el metodo get
            ps.setLong(1, persona.getCI());
            ps.setString(2, persona.getNombre());
            ps.setString(3, persona.getApellidos());
            ps.setBinaryStream(4, fot);
//            Esto es para que actualice el estado de la base de datos 
            registros = ps.executeUpdate();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EstudianteDAO_JDBC.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                //Debemos cerrar la sesion de la manera inversa a la cual se abrio
                //Como ya importamos los metodos staticos cerramos sin la necesidad 
                //de poner la palabra Conexion.close(conn);
                close(ps);
                if (this.conexionTransaccional == null) {
                    close(conn);
                }
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }

        }
        return registros;
    }

    @Override
    public int insertarEstudiante(Estudiante estudiante) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        int registros = 0;
        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : getConection();

            ps = conn.prepareStatement(SQL_INSERT_ESTUDIANTE);

            ps.setLong(1, estudiante.getCI());
            ps.setString(2, estudiante.getAula());
            ps.setInt(3, estudiante.getTutor());
            ps.setString(4, estudiante.getGenero());

            registros = ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(EstudianteDAO_JDBC.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                //Debemos cerrar la sesion de la manera inversa a la cual se abrio
                //Como ya importamos los metodos staticos cerramos sin la necesidad 
                //de poner la palabra Conexion.close(conn);
                close(ps);
                if (this.conexionTransaccional == null) {
                    close(conn);
                }
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }

        }
        return registros;
    }

    @Override
    public int insertarTutor(Tutor tutor) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        int registros = 0;
        try {
            con = this.conexionTransaccional != null ? this.conexionTransaccional : getConection();

            ps = con.prepareStatement(SQL_INSERT_TUTOR);

            ps.setLong(1, tutor.getCi_Estudiante());
            ps.setLong(2, tutor.getCI());
            ps.setInt(3, tutor.getTutor());
            ps.setString(4, tutor.getCodTarjeta());
            ps.setInt(5, tutor.getCelular());
            ps.setString(6, tutor.getParentesco());

            registros = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EstudianteDAO_JDBC.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                //Debemos cerrar la sesion de la manera inversa a la cual se abrio
                //Como ya importamos los metodos staticos cerramos sin la necesidad 
                //de poner la palabra Conexion.close(conn);
                close(ps);
                if (this.conexionTransaccional == null) {
                    close(con);
                }
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }

        }
        return registros;
    }

    @Override
    public DefaultTableModel mostrarClientes() throws SQLException {
        String[] nombresColumnas = {"ID", "NOMBRE", "APELLIDOS", "GENERO", "CARGO"};
        String[] registros = new String[5];

        DefaultTableModel modelo = new DefaultTableModel(null, nombresColumnas);

        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            cn = Conexion.getConection();
            pst = cn.prepareStatement(SQL_SELECT);
            rs = pst.executeQuery();
            while (rs.next()) {
                registros[0] = rs.getString("CODIGO");
                registros[1] = rs.getString("NOMBRE");
                registros[2] = rs.getString("APELLIDOS");
                registros[3] = rs.getString("GENERO");
                registros[4] = rs.getString("CARGO");
                modelo.addRow(registros);
            }
        } finally {
            try {
                //Debemos cerrar la sesion de la manera inversa a la cual se abrio
                //Como ya importamos los metodos staticos cerramos sin la necesidad 
                //de poner la palabra Conexion.close(rs);
                close(rs);
                close(pst);
                if (this.conexionTransaccional == null) {
                    close(cn);
                }
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
        }
        return modelo;
    }

    //Este metodo es UPDATE=Nos permitira actualizar informacion de nuestra tabla
    //Regresara un numero entero para verificar cuantos registros se an actualizado
    @Override
    public int actualizar(Estudiante alumno) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : getConection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            //Sustituimos los parametros de la misma forma que insertar
            stmt.setString(1, alumno.getNombre());
            stmt.setString(2, alumno.getApellidos());
            stmt.setString(3, alumno.getGenero());
//            ps.setString(4, alumno.getCargo());
//            ps.setInt(5, alumno.getCodigo());
            //Esto es para que actualice el estado de la base de datos 
            registros = stmt.executeUpdate();
        } finally {
            try {
                close(stmt);
                if (this.conexionTransaccional == null) {
                    close(conn);
                }
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }

        }
        return registros;
    }

    //Este metodo es para DELETE=Eliminar informacion de nuestra tabla
    //Regresara un numero entero para ver cuantos elementos fueron eliminados
    @Override
    public int eliminar(Estudiante alumno) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : getConection();
            stmt = conn.prepareStatement(SQL_DELETE);
            //Sustituimos los parametros de la misma forma que insertar
//            ps.setInt(1, alumno.getCodigo());
            //Esto es para que actualice el estado de la base de datos 
            registros = stmt.executeUpdate();
        } finally {
            try {
                close(stmt);
                if (this.conexionTransaccional == null) {
                    close(conn);
                }
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }

        }
        return registros;
    }

    @Override
    public void visualizar_Estudiante(JTable tabla) {

        String[] nombresColumnas = {"ID", "CI", "NOMBRE", "APELLIDOS", "TUTOR", "GENERO", "AULA", "FOTO"};

        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        //Image img = null;   
        DefaultTableModel dt = new DefaultTableModel(null, nombresColumnas);
        tabla.setDefaultRenderer(Object.class, new TablaImagen());
        Object[] fila = new Object[11];
        InputStream is;
        ImageIcon foto;
        try {
            cn = this.conexionTransaccional != null ? this.conexionTransaccional : getConection();
            pst = cn.prepareStatement(SQL_SELECT_ESTUDIANTE);
            rs = pst.executeQuery();
            while (rs.next()) {

                fila[0] = rs.getObject(1);
                fila[1] = rs.getObject(2);
                fila[2] = rs.getObject(3);
                fila[3] = rs.getObject(4);
                fila[4] = rs.getObject(5);
                fila[5] = rs.getObject(6);
                fila[6] = rs.getObject(7);

                is = rs.getBinaryStream(8);
                if (is != null) {
                    try {
                        BufferedImage bi = ImageIO.read(is);
                        foto = new ImageIcon(bi);
                        Image img = foto.getImage();
                        //(ancho,alto,mensaje)
                        Image newimg = img.getScaledInstance(65, 60, java.awt.Image.SCALE_SMOOTH);

                        ImageIcon newicon = new ImageIcon(newimg);
                        fila[7] = new JLabel(newicon);
                    } catch (IOException ex) {
                        fila[7] = "No Imagen";
                    }
                } else {
                    fila[7] = "No Imagen";
                }

                dt.addRow(fila);
            }

            tabla.setModel(dt);
            tabla.setRowHeight(64);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                close(rs);
                close(pst);
                if (this.conexionTransaccional == null) {
                    close(cn);
                }
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }

        }
    }
    
    @Override
    public void exportar_Estudiante(JTable tabla){
        String[] nombresColumnas = {"ID", "CI", "NOMBRE", "APELLIDOS", "TUTOR", "GENERO", "AULA"};

        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        //Image img = null;   
        DefaultTableModel dt = new DefaultTableModel(null, nombresColumnas);
        tabla.setDefaultRenderer(Object.class, new TablaImagen());
        Object[] fila = new Object[11];
        InputStream is;
        ImageIcon foto;
        try {
            cn = this.conexionTransaccional != null ? this.conexionTransaccional : getConection();
            pst = cn.prepareStatement(SQL_SELECT_ESTUDIANTE_EXPORTAR);
            rs = pst.executeQuery();
            while (rs.next()) {

                fila[0] = rs.getObject(1);
                fila[1] = rs.getObject(2);
                fila[2] = rs.getObject(3);
                fila[3] = rs.getObject(4);
                fila[4] = rs.getObject(5);
                fila[5] = rs.getObject(6);
                fila[6] = rs.getObject(7);

                dt.addRow(fila);
            }

            tabla.setModel(dt);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                close(rs);
                close(pst);
                if (this.conexionTransaccional == null) {
                    close(cn);
                }
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }

        }
    }
    
    @Override
    public void exportar_Tutor(JTable tabla){
        String[] nombresColumnas = {"ID", "CI", "CI ESTUDIANTE", "TUTOR", "NOMBRE", "APELLIDOS", "CELULAR", "PARENTESCO"};

        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        //Image img = null;   
        DefaultTableModel dt = new DefaultTableModel(null, nombresColumnas);
        tabla.setDefaultRenderer(Object.class, new TablaImagen());
        Object[] fila = new Object[11];
        InputStream is;
        ImageIcon foto;
        try {
            cn = this.conexionTransaccional != null ? this.conexionTransaccional : getConection();
            pst = cn.prepareStatement(SQL_SELECT_TUTOR);
            rs = pst.executeQuery();
            while (rs.next()) {

                fila[0] = rs.getObject(1);
                fila[1] = rs.getObject(2);
                fila[2] = rs.getObject(3);
                fila[3] = rs.getObject(4);
                fila[4] = rs.getObject(5);
                fila[5] = rs.getObject(6);
                fila[6] = rs.getObject(7);
                fila[7] = rs.getObject(8);

                dt.addRow(fila);
            }

            tabla.setModel(dt);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                close(rs);
                close(pst);
                if (this.conexionTransaccional == null) {
                    close(cn);
                }
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }

        }
    }

    @Override
    public void visualizar_Tutor(JTable tabla) {

        String[] nombresColumnas = {"ID", "CI", "CI ESTUDIANTE", "TUTOR", "NOMBRE", "APELLIDOS", "CELULAR", "PARENTESCO", "FOTO"};

        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        //Image img = null;   
        DefaultTableModel dt = new DefaultTableModel(null, nombresColumnas);
        tabla.setDefaultRenderer(Object.class, new TablaImagen());
        Object[] fila = new Object[11];
        InputStream is;
        ImageIcon foto;
        try {
            cn = this.conexionTransaccional != null ? this.conexionTransaccional : getConection();
            pst = cn.prepareStatement(SQL_SELECT_TUTOR);
            rs = pst.executeQuery();
            while (rs.next()) {

                fila[0] = rs.getObject(1);
                fila[1] = rs.getObject(2);
                fila[2] = rs.getObject(3);
                fila[3] = rs.getObject(4);
                fila[4] = rs.getObject(5);
                fila[5] = rs.getObject(6);
                fila[6] = rs.getObject(7);
                fila[7] = rs.getObject(8);

                is = rs.getBinaryStream(9);
                if (is != null) {
                    try {
                        BufferedImage bi = ImageIO.read(is);
                        foto = new ImageIcon(bi);
                        Image img = foto.getImage();
                        //(ancho,alto,mensaje)
                        Image newimg = img.getScaledInstance(65, 60, java.awt.Image.SCALE_SMOOTH);

                        ImageIcon newicon = new ImageIcon(newimg);
                        fila[8] = new JLabel(newicon);
                    } catch (IOException ex) {
                        fila[8] = "No Imagen";
                    }
                } else {
                    fila[8] = "No Imagen";
                }

                dt.addRow(fila);
            }

            tabla.setModel(dt);
            tabla.setRowHeight(64);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                close(rs);
                close(pst);
                if (this.conexionTransaccional == null) {
                    close(cn);
                }
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }

        }
    }

    @Override
    public int actualizarPersona(Persona persona, String foto) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : getConection();

            if (foto == null || foto.equals("")) {
                stmt = conn.prepareStatement(SQL_UPDATE_PERSONA_2);
                stmt.setLong(1, persona.getCI());
                stmt.setString(2, persona.getNombre());
                stmt.setString(3, persona.getApellidos());
                stmt.setInt(4, persona.getId_Persona());
            } else {
                FileInputStream fot;
                File file = new File(foto);
                fot = new FileInputStream(file);
                stmt = conn.prepareStatement(SQL_UPDATE_PERSONA);
                stmt.setLong(1, persona.getCI());
                stmt.setString(2, persona.getNombre());
                stmt.setString(3, persona.getApellidos());
                stmt.setBinaryStream(4, fot);
                stmt.setInt(5, persona.getId_Persona());
            }

            //Esto es para que actualice el estado de la base de datos 
            registros = stmt.executeUpdate();
        } catch (FileNotFoundException ex) {
            System.out.println("ex1 = " + ex);
        } finally {
            try {
                close(stmt);
                if (this.conexionTransaccional == null) {
                    close(conn);
                }
            } catch (SQLException ex) {
                System.out.println("ex = " + ex);
            }

        }
        return registros;
    }

    @Override
    public int actualizarEstudiante(Estudiante estudiante) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : getConection();
            stmt = conn.prepareStatement(SQL_UPDATE_ESTUDIANTE);
            //Sustituimos los parametros de la misma forma que insertar

            stmt.setLong(1, estudiante.getCI());
            stmt.setString(2, estudiante.getAula());
            stmt.setInt(3, estudiante.getTutor());
            stmt.setString(4, estudiante.getGenero());
            stmt.setInt(5, estudiante.getId_Estudiante());

            //Esto es para que actualice el estado de la base de datos 
            registros = stmt.executeUpdate();
        } finally {
            try {
                close(stmt);
                if (this.conexionTransaccional == null) {
                    close(conn);
                }
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }

        }
        return registros;
    }

    @Override
    public int actualizarTutor(Tutor tutor) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : getConection();
            stmt = conn.prepareStatement(SQL_UPDATE_TUTOR);
            //Sustituimos los parametros de la misma forma que insertar

            stmt.setLong(1, tutor.getCI());
            stmt.setString(2, tutor.getCodTarjeta());
            stmt.setInt(3, tutor.getCelular());
            stmt.setString(4, tutor.getParentesco());
            stmt.setInt(5, tutor.getId_Tutor());

            //Esto es para que actualice el estado de la base de datos 
            registros = stmt.executeUpdate();
        } finally {
            try {
                close(stmt);
                if (this.conexionTransaccional == null) {
                    close(conn);
                }
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }

        }
        return registros;
    }

    class TablaImagen extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof JLabel) {
                JLabel lbl = (JLabel) value;
                return lbl;
            }
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
        //super.getTableCellRenderComponen //TRUCO  
    }
}
