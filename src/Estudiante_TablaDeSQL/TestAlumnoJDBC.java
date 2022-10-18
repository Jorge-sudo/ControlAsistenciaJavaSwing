package Estudiante_TablaDeSQL;

import java.sql.*;
import java.util.*;

public class TestAlumnoJDBC {

    public static void main(String[] args) {
        Connection conexion = null;

        try {
            conexion = Conexion.getConection();
            if (conexion.getAutoCommit()) {
                //Esto hacemos para el AutoCommit no se inicialice solo ya que nosotros lo haremos manualmente 
                conexion.setAutoCommit(false);
            }
            System.out.println("Se hizo la conexion");
            
            conexion.commit();
            System.out.println("Se hizo commit en la transaccion");
        } catch (SQLException ex) {
            System.out.println("Entramos al Rollback");
            ex.printStackTrace(System.out);
            
            try {
                //Perso si sale un error entonces rollback se ejecutara y no se realizara ningun cambio
                conexion.rollback();
            } catch (SQLException ex1) {
            }
        }

    }

    public static void imprimir() throws SQLException {
        EstudianteDAO_JDBC personaDao = new EstudianteDAO_JDBC();
        List<Estudiante> personas;

//        personas = personaDao.seleccionar();
//        personas.forEach((persona) -> {
//            System.out.println(persona);
//        });
        System.out.println("");


        /*Tambien se puede imprimir con forEach
        for(Persona persona:personas){
            System.out.println("persona = " + persona);
        }*/
    }
}
