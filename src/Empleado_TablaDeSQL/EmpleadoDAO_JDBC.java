package Empleado_TablaDeSQL;

import static Estudiante_TablaDeSQL.Conexion.close;
import static Estudiante_TablaDeSQL.Conexion.getConection;
import Estudiante_TablaDeSQL.EstudianteDAO_JDBC;
import java.awt.Component;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.*;

public class EmpleadoDAO_JDBC implements EmpleadoDAO {

    private Connection conexionTransaccional;

    private static final String SQL_SELECT_EMPLEADO = "SELECT empleado.id_empleado, empleado.ci, persona.nombre||' '||persona.apellidos, empleado.celular,\n"
            + "empleado.genero, empleado.cargo, persona.foto\n"
            + "FROM persona INNER JOIN empleado ON persona.ci=empleado.ci\n"
            + "ORDER BY empleado.id_empleado;";

    private static final String SQL_SELECT_PROFESOR = "SELECT empleado.id_empleado, empleado.ci, profesor.aula,\n"
            + "persona.nombre||' '||persona.apellidos, empleado.celular, empleado.genero, empleado.cargo, persona.foto\n"
            + "FROM (persona INNER JOIN empleado ON persona.ci=empleado.ci)\n"
            + "	INNER JOIN profesor ON profesor.ci=empleado.ci\n"
            + "ORDER BY empleado.id_empleado;";

    private static final String SQL_SELECT_EMPLEADO_EXPORTAR = "SELECT empleado.id_empleado, empleado.ci, profesor.aula,\n"
            + "persona.nombre, persona.apellidos, empleado.celular, empleado.genero, empleado.cargo\n"
            + "FROM (persona INNER JOIN empleado ON persona.ci=empleado.ci)\n"
            + "	INNER JOIN profesor ON profesor.ci=empleado.ci\n"
            + "ORDER BY persona.nombre;";

    private static final String SQL_SELECT_ASISTENCIA = "SELECT asistencia.id_asistencia, persona.ci, persona.nombre||' '||persona.apellidos, asistencia.hora, asistencia.fecha, asistencia.asistencia,\n"
            + "	asistencia.ocacion\n"
            + "FROM persona INNER JOIN asistencia ON persona.ci = asistencia.ci\n"
            + "ORDER BY asistencia.id_asistencia;";

    private static String SQL_INSERT_PERSONA = "INSERT INTO persona(\n"
            + "       ci, nombre, apellidos, foto)\n"
            + "	VALUES (?, ?, ?, ?);";

    private static String SQL_INSERT_EMPLEADO = "INSERT INTO empleado(\n"
            + "       ci, codtarjeta, usuario, \"contraseña\", codrecuperacion, celular, genero, cargo)\n"
            + "	VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

    private static String SQL_INSERT_PROFESOR = "INSERT INTO profesor(\n"
            + "       ci, aula)\n"
            + "	VALUES (?, ?);";

    private static String SQL_INSERT_ASISTENCIA = "INSERT INTO asistencia(\n"
            + "       ci, hora, fecha, asistencia, ocacion)\n"
            + "	VALUES (?, ?, ?, ?, ?);";

    private static String SQL_INSERT_LICENCIA = "INSERT INTO licencia(\n"
            + "       ci, descripcion)\n"
            + "	VALUES (?, ?);";

    private static final String SQL_UPDATE_PERSONA = "UPDATE persona\n"
            + "	SET ci=?, nombre=?, apellidos=?, foto=?\n"
            + "	WHERE id_persona = ?;";

    private static final String SQL_UPDATE_PERSONA_2 = "UPDATE persona\n"
            + "	SET ci=?, nombre=?, apellidos=?\n"
            + "	WHERE id_persona = ?;";

    private static final String SQL_UPDATE_EMPLEADO = "UPDATE empleado\n"
            + "	SET ci=?, codtarjeta=?, usuario=?, \"contraseña\"=?, codrecuperacion=?, celular=?, genero=?, cargo=?\n"
            + "	WHERE id_empleado = ?;";

    private static final String SQL_UPDATE_PROFESOR = "UPDATE profesor\n"
            + "	SET ci=?, aula=?\n"
            + "	WHERE id_profesor = ?;";

    private static final String SQL_DELETE = "DELETE FROM alumno WHERE CODIGO = ?;";

    public EmpleadoDAO_JDBC() {
    }

    //Este constructor es para manejar la transaccion desde una clase externa cuando se hara comit o rolbak
    public EmpleadoDAO_JDBC(Connection conexionTransaccional) {
        this.conexionTransaccional = conexionTransaccional;
    }

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
//            Aqui recibimos el obejto persona Sy lo añadimos a la tabla persona de SQL con el metodo get
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
    public int insertarEmpleado(Empleado empleado) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        int registros = 0;
        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : getConection();

//            SQL_INSERT = SQL_INSERT + "VALUES("+Estudiante.getCodigo() +",'"+Estudiante.getNombre()
//                    +"','"+Estudiante.getApellidos()+"','"+Estudiante.getGenero()+"','"+Estudiante.getCargo()+"');";
            ps = conn.prepareStatement(SQL_INSERT_EMPLEADO);
//            Aqui recibimos el obejto persona y lo añadimos a la tabla persona de SQL con el metodo get
            ps.setLong(1, empleado.getCI());
            ps.setString(2, empleado.getCodTarjeta());
            ps.setString(3, empleado.getUsuario());
            ps.setString(4, empleado.getContraseña());
            ps.setString(5, empleado.getRecuperacion());
            ps.setInt(6, empleado.getCelular());
            ps.setString(7, empleado.getGenero());
            ps.setString(8, empleado.getCargo());

//            Esto es para que actualice el estado de la base de datos 
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
    public int insertarProfesor(Profesor profesor) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        int registros = 0;
        try {
            con = this.conexionTransaccional != null ? this.conexionTransaccional : getConection();

//            SQL_INSERT = SQL_INSERT + "VALUES("+Estudiante.getCodigo() +",'"+Estudiante.getNombre()
//                    +"','"+Estudiante.getApellidos()+"','"+Estudiante.getGenero()+"','"+Estudiante.getCargo()+"');";
            ps = con.prepareStatement(SQL_INSERT_PROFESOR);
//            Aqui recibimos el obejto persona y lo añadimos a la tabla persona de SQL con el metodo get
            ps.setLong(1, profesor.getCI());
            ps.setString(2, profesor.getAula());
//            Esto es para que actualice el estado de la base de datos 
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
    public int registrarAsistencia(Empleado_TablaDeSQL.Asistencia asistencia) throws SQLException {

        Connection con = null;
        PreparedStatement ps = null;
        int registros = 0;
        try {
            con = this.conexionTransaccional != null ? this.conexionTransaccional : getConection();

            ps = con.prepareStatement(SQL_INSERT_ASISTENCIA);

            ps.setLong(1, asistencia.getCI());
            ps.setTime(2, asistencia.getHora());
            ps.setDate(3, asistencia.getFecha());
            ps.setString(4, asistencia.getAsistencia());
            ps.setString(5, asistencia.getOcasion());

            registros = ps.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Error al agregar usuario " + ex.getMessage());
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
    public int registrarLicencia(long ci, String descripcion) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        int registros = 0;
        try {
            con = this.conexionTransaccional != null ? this.conexionTransaccional : getConection();

            ps = con.prepareStatement(SQL_INSERT_LICENCIA);

            ps.setLong(1, ci);
            ps.setString(2, descripcion);

            registros = ps.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Error al agregar usuario " + ex.getMessage());
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
    public int actualizarEmpleado(Empleado empleado) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : getConection();
            stmt = conn.prepareStatement(SQL_UPDATE_EMPLEADO);
            //Sustituimos los parametros de la misma forma que insertar

            stmt.setLong(1, empleado.getCI());
            stmt.setString(2, empleado.getCodTarjeta());
            stmt.setString(3, empleado.getUsuario());
            stmt.setString(4, empleado.getContraseña());
            stmt.setString(5, empleado.getRecuperacion());
            stmt.setInt(6, empleado.getCelular());
            stmt.setString(7, empleado.getGenero());
            stmt.setString(8, empleado.getCargo());
            stmt.setInt(9, empleado.getIdempleado());

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
    public int actualizarProfesor(Profesor profesor) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : getConection();
            stmt = conn.prepareStatement(SQL_UPDATE_PROFESOR);
            //Sustituimos los parametros de la misma forma que insertar

            stmt.setLong(1, profesor.getCI());
            stmt.setString(2, profesor.getAula());
            stmt.setInt(3, profesor.getId_Profesor());
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
    public void visualizar_Empleado(JTable tabla) throws SQLException {
        String[] nombresColumnas = {"ID", "CI", "NOMBRE", "CELULAR", "GENERO", "CARGO", "FOTO"};

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
            pst = cn.prepareStatement(SQL_SELECT_EMPLEADO);
            rs = pst.executeQuery();
            while (rs.next()) {

                fila[0] = rs.getObject(1);
                fila[1] = rs.getObject(2);
                fila[2] = rs.getObject(3);
                fila[3] = rs.getObject(4);
                fila[4] = rs.getObject(5);
                fila[5] = rs.getObject(6);

                is = rs.getBinaryStream(7);
                if (is != null) {
                    try {
                        BufferedImage bi = ImageIO.read(is);
                        foto = new ImageIcon(bi);
                        Image img = foto.getImage();
                        //(ancho,alto,mensaje)
                        Image newimg = img.getScaledInstance(65, 60, java.awt.Image.SCALE_SMOOTH);

                        ImageIcon newicon = new ImageIcon(newimg);
                        fila[6] = new JLabel(newicon);
                    } catch (IOException ex) {
                        fila[6] = "No Imagen";
                    }
                } else {
                    fila[6] = "No Imagen";
                }

                dt.addRow(fila);
            }

            tabla.setModel(dt);
            tabla.setRowHeight(64);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            close(rs);
            close(pst);
            if (this.conexionTransaccional == null) {
                close(cn);
            }
        }
    }

    @Override
    public void visualizar_Profesor(JTable tabla) throws SQLException {

        String[] nombresColumnas = {"ID", "CI", "AULA", "NOMBRE", "CELULAR", "GENERO", "CARGO", "FOTO"};

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
            pst = cn.prepareStatement(SQL_SELECT_PROFESOR);
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
                    fila[8] = "No Imagen";
                }

                dt.addRow(fila);
            }

            tabla.setModel(dt);
            tabla.setRowHeight(64);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            close(rs);
            close(pst);
            if (this.conexionTransaccional == null) {
                close(cn);
            }
        }
    }

    @Override
    public void visualizar_Asistencias(JTable tabla) {
        String[] nombresColumnas = {"ID", "CI", "NOMBRE", "HORA", "FECHA", "ASISTENCIA", "OCASION"};

        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        //Image img = null;   
        DefaultTableModel dt = new DefaultTableModel(null, nombresColumnas);
        Object[] fila = new Object[11];
        try {
            cn = this.conexionTransaccional != null ? this.conexionTransaccional : getConection();
            pst = cn.prepareStatement(SQL_SELECT_ASISTENCIA);
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
                System.out.println("ex = " + ex);
            }

        }
    }

    @Override
    public void exportar_Empleado(JTable tabla) {

        String[] nombresColumnas = {"ID", "CI", "AULA", "NOMBRE", "APELLIDOS", "CELULAR", "GENERO", "CARGO"};
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        //Image img = null;   
        DefaultTableModel dt = new DefaultTableModel(null, nombresColumnas);
        Object[] fila = new Object[11];
        try {
            cn = this.conexionTransaccional != null ? this.conexionTransaccional : getConection();
            pst = cn.prepareStatement(SQL_SELECT_EMPLEADO_EXPORTAR);
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
