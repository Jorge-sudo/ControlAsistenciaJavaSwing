package Estudiante_TablaDeSQL;

import java.sql.*;

public class Conexion {

            //CON ESTA CLASE PODREMOS REUTILIZAR EL METODO DE CONECCTAR
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/ControlAsistencia?useSSL=false&useTimezone=true&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String JDBC_USER = "postgres";
    private static final String JDBC_PASSWORD = "postgres";

    public static Connection getConection() throws SQLException {
        //En este caso ya no encerramos en un try y catch sinos reportamos la Exception "throws SQLException" 
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }

    public static void close(ResultSet rs) throws SQLException {
        //En este caso ya no encerramos en un try y catch sinos reportamos la Exception "throws SQLException" 
        rs.close();
    }

    public static void close(Statement smtm) throws SQLException {
        //En este caso ya no encerramos en un try y catch sinos reportamos la Exception "throws SQLException" 
        smtm.close();
    }

    //Utilizamos preparedStatement por que es mejor que el Statement asi que lo utilizaremos 
    public static void close(PreparedStatement smtm) throws SQLException {
        //En este caso ya no encerramos en un try y catch sinos reportamos la Exception "throws SQLException" 
        smtm.close();
    }
    
    public static void close(Connection conn) throws SQLException{
        //En este caso ya no encerramos en un try y catch sinos reportamos la Exception "throws SQLException" 
        conn.close();
    }

}
