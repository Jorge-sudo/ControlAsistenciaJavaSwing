/*
CLASE Empleado, MANIPULA LOS DATOS PERSONALES DEL Empleado
 */
package proyectoasistenciapersonal;

public class Empleado {
    private int  Codigo;
    private String Nombre;
    private String Apellidos;
    private char Genero;
    private String Cargo;

    public Empleado() {
    }

    public Empleado(int Codigo, String Nombre, String Apellidos, char Genero, String Cargo) {
        this.Codigo = Codigo;
        this.Nombre = Nombre;
        this.Apellidos = Apellidos;
        this.Genero = Genero;
        this.Cargo = Cargo;
    }
       
    public int getCodigo()
    {
        return Codigo;
    }
    
    public void setCodigo(int codigo)
    {
        this.Codigo=codigo;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String Apellidos) {
        this.Apellidos = Apellidos;
    }

    public char getGenero() {
        return Genero;
    }

    public void setGenero(char Genero) {
        this.Genero = Genero;
    }

    public String getCargo() {
        return Cargo;
    }

    public void setCargo(String Cargo) {
        this.Cargo = Cargo;
    }
}
