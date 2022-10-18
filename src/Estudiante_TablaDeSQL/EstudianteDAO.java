package Estudiante_TablaDeSQL;

import Empleado_TablaDeSQL.Persona;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public interface EstudianteDAO {

    public DefaultTableModel mostrarClientes() throws SQLException;

    public int insertarPersona(Persona persona, String foto) throws SQLException;
    
    public int insertarEstudiante(Estudiante estudiante) throws SQLException;
    
    public int insertarTutor(Tutor tutor) throws SQLException;

    public int actualizar(Estudiante alumno) throws SQLException;

    public int eliminar(Estudiante alumno) throws SQLException;
    
    public void visualizar_Estudiante(JTable tabla);
    
    public void visualizar_Tutor(JTable tabla);
    
    public void exportar_Estudiante(JTable tabla); 
    
    public void exportar_Tutor(JTable tabla);
    
    public int actualizarPersona(Persona persona, String foto) throws SQLException;
    
    public int actualizarEstudiante(Estudiante estudiante) throws SQLException;
    
    public int actualizarTutor(Tutor tutor) throws SQLException;
}
