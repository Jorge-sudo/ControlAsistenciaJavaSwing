package Empleado_TablaDeSQL;

public class Persona {

    private int id_Persona;
    long CI;
    private String nombre;
    private String apellidos;

    public Persona() {
    }

    public Persona(long CI) {
        this.CI = CI;
    }

    public Persona(long CI, String nombre, String apellidos) {
        this.CI = CI;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    public Persona(int id_Persona, long CI, String nombre, String apellidos) {
        this.id_Persona = id_Persona;
        this.CI = CI;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    public int getId_Persona() {
        return id_Persona;
    }

    public void setId_Persona(int id_Persona) {
        this.id_Persona = id_Persona;
    }

    public long getCI() {
        return CI;
    }

    public void setCI(long CI) {
        this.CI = CI;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

}
