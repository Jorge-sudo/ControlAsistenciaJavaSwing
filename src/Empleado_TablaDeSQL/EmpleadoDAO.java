package Empleado_TablaDeSQL;

//DAO = Dato de Acceso al Obejto=Empleado
import java.sql.SQLException;
import javax.swing.JTable;

public interface EmpleadoDAO {

    public void visualizar_Empleado(JTable tabla) throws SQLException;

    public void exportar_Empleado(JTable tabla);

    public int insertarProfesor(Profesor profesor) throws SQLException;

    public int insertarPersona(Persona persona, String foto) throws SQLException;

    public int insertarEmpleado(Empleado empleado) throws SQLException;

    public int registrarAsistencia(Empleado_TablaDeSQL.Asistencia asistencia) throws SQLException;

    public int registrarLicencia(long ci, String descripcion) throws SQLException;

    public int actualizarPersona(Persona persona, String foto) throws SQLException;

    public int actualizarEmpleado(Empleado empleado) throws SQLException;

    public int actualizarProfesor(Profesor profesor) throws SQLException;

    public void visualizar_Asistencias(JTable tabla) throws SQLException;

    public void visualizar_Profesor(JTable tabla) throws SQLException;
}
