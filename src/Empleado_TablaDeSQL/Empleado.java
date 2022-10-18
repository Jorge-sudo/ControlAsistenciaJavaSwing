package Empleado_TablaDeSQL;

public class Empleado extends Persona{
    
    private int id_Empleado;
    private String codTarjeta;
    private String usuario;
    private String contraseña;
    private String recuperacion;
    private int celular;
    private String genero;
    private String cargo;

    public Empleado() {
    }

    public Empleado(int id_Empleado, long CI, String codTarjeta, String usuario, String contraseña, String recuperacion, int celular, String genero, String cargo) {
        super(CI);
        this.id_Empleado = id_Empleado;
        this.codTarjeta = codTarjeta;
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.recuperacion = recuperacion;
        this.celular = celular;
        this.genero = genero;
        this.cargo = cargo;
    }

    

    
    
    public Empleado(long CI, String nombre, String apellidos, String codTarjeta, String usuario, String contraseña, 
            String recuperacion, int celular, String genero, String cargo) {
        super(CI, nombre, apellidos);
        this.codTarjeta = codTarjeta;
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.recuperacion = recuperacion;
        this.celular = celular;
        this.genero = genero;
        this.cargo = cargo;
    }

    public Empleado(int id_empleado, long CI, String nombre, String apellidos, String codTarjeta, String usuario, String contraseña, 
            String recuperacion, int celular, String genero, String cargo) {
        super( CI, nombre, apellidos);
        this.id_Empleado = id_empleado;
        this.codTarjeta = codTarjeta;
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.recuperacion = recuperacion;
        this.celular = celular;
        this.genero = genero;
        this.cargo = cargo;
    }

    public int getIdempleado() {
        return id_Empleado;
    }

    public void setIdempleado(int idempleado) {
        this.id_Empleado = idempleado;
    }

    public String getCodTarjeta() {
        return codTarjeta;
    }

    public void setCodTarjeta(String codTarjeta) {
        this.codTarjeta = codTarjeta;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getRecuperacion() {
        return recuperacion;
    }

    public void setRecuperacion(String recuperacion) {
        this.recuperacion = recuperacion;
    }

    public int getCelular() {
        return celular;
    }

    public void setCelular(int celular) {
        this.celular = celular;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    
}   
    
  