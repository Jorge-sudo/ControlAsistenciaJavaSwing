
package Estudiante_TablaDeSQL;

import Empleado_TablaDeSQL.Persona;

public class Tutor extends Persona{
    private int id_Tutor;
    private long ci_Estudiante;
    private int tutor;
    private String codTarjeta;
    private int celular;
    private String parentesco;

    public Tutor() {
    }

    public Tutor(int id_Tutor, String codTarjeta, int celular, String parentesco, long CI) {
        super(CI);
        this.id_Tutor = id_Tutor;
        this.codTarjeta = codTarjeta;
        this.celular = celular;
        this.parentesco = parentesco;
    }

    
    
    public Tutor(long CI, String nombre, String apellidos,long ci_Estudiante, 
            int tutor, String codTarjeta, int celular, String parentesco) {
        super(CI, nombre, apellidos);
        this.ci_Estudiante = ci_Estudiante;
        this.tutor = tutor;
        this.codTarjeta = codTarjeta;
        this.celular = celular;
        this.parentesco = parentesco;
    }

    public Tutor(int id_Tutor, long ci_Estudiante, int tutor, String codTarjeta, int celular, String parentesco
            , int id_Persona, long CI, String nombre, String apellidos) {
        super(id_Persona, CI, nombre, apellidos);
        this.id_Tutor = id_Tutor;
        this.ci_Estudiante = ci_Estudiante;
        this.tutor = tutor;
        this.codTarjeta = codTarjeta;
        this.celular = celular;
        this.parentesco = parentesco;
    }

    public int getId_Tutor() {
        return id_Tutor;
    }

    public void setId_Tutor(int id_Tutor) {
        this.id_Tutor = id_Tutor;
    }

    public long getCi_Estudiante() {
        return ci_Estudiante;
    }

    public void setCi_Estudiante(long ci_Estudiante) {
        this.ci_Estudiante = ci_Estudiante;
    }

    public int getTutor() {
        return tutor;
    }

    public void setTutor(int tutor) {
        this.tutor = tutor;
    }

    public String getCodTarjeta() {
        return codTarjeta;
    }

    public void setCodTarjeta(String codTarjeta) {
        this.codTarjeta = codTarjeta;
    }

    public int getCelular() {
        return celular;
    }

    public void setCelular(int celular) {
        this.celular = celular;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }
    
    
    
}
