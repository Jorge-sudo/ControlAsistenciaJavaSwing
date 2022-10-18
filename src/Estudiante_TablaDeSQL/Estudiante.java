package Estudiante_TablaDeSQL;

import Empleado_TablaDeSQL.Persona;

public class Estudiante extends Persona{
    
    private int id_Estudiante;
    private String aula;
    private int tutor;
    private String genero;
    

    public Estudiante() {
    }

    public Estudiante(int id_Estudiante, String aula, int tutor, String genero, long CI) {
        super(CI);
        this.id_Estudiante = id_Estudiante;
        this.aula = aula;
        this.tutor = tutor;
        this.genero = genero;
    }

    
    
    public Estudiante(long CI, String nombre, String apellidos, String aula, int tutor, String genero) {
        super(CI, nombre, apellidos);
        this.aula = aula;
        this.tutor = tutor;
        this.genero = genero;
    }

    public Estudiante(int id_Estudiante, String aula, int tutor, String genero, int id_Persona, long CI, String nombre,
            String apellidos) {
        super(id_Persona, CI, nombre, apellidos);
        this.id_Estudiante = id_Estudiante;
        this.aula = aula;
        this.tutor = tutor;
        this.genero = genero;
    }


    public int getId_Estudiante() {
        return id_Estudiante;
    }

    public void setId_Estudiante(int id_Estudiante) {
        this.id_Estudiante = id_Estudiante;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public int getTutor() {
        return tutor;
    }

    public void setTutor(int tutor) {
        this.tutor = tutor;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    
    
}