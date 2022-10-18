package Empleado_TablaDeSQL;

public class Profesor extends Persona {

    private int id_Profesor;
    private String aula;

    public Profesor() {
    }

    public Profesor(int id_profesor ,long CI, String aula) {
        super(CI);
        this.id_Profesor = id_profesor;
        this.aula = aula;
    }

    public Profesor(int id_Profesor, String aula, long CI) {
        super(CI);
        this.id_Profesor = id_Profesor;
        this.aula = aula;
    }
    
    
    
    public Profesor(long CI, String aula) {
        super(CI);
        this.aula = aula;
    }

    public Profesor(int id_Persona, long CI, String nombre, String apellidos, int id_Profesor, String aula) {
        super(id_Persona, CI, nombre, apellidos);
        this.id_Profesor = id_Profesor;
        this.aula = aula;
    }

    public int getId_Profesor() {
        return id_Profesor;
    }

    public void setId_Profesor(int id_Profesor) {
        this.id_Profesor = id_Profesor;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

}
