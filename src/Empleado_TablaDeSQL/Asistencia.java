
package Empleado_TablaDeSQL;

import java.sql.Time;
import java.util.Date;


public class Asistencia {
    private int ID;
    private long CI;
    private java.sql.Time hora ;
    private java.sql.Date Fecha;
    private String Asistencia;
    private String Ocasion;

    public Asistencia() {
    }

    public Asistencia(long CI, Time hora, java.sql.Date Fecha, String Asistencia, String Ocasion) {
        this.CI = CI;
        this.hora = hora;
        this.Fecha = Fecha;
        this.Asistencia = Asistencia;
        this.Ocasion = Ocasion;
    }

    public Asistencia(int ID, long CI, Time hora, java.sql.Date Fecha, String Asistencia, String Puntualidad, String Ocasion) {
        this.ID = ID;
        this.CI = CI;
        this.hora = hora;
        this.Fecha = Fecha;
        this.Asistencia = Asistencia;
        this.Ocasion = Ocasion;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public long getCI() {
        return CI;
    }

    public void setCI(long CI) {
        this.CI = CI;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public java.sql.Date getFecha() {
        return Fecha;
    }

    public void setFecha(java.sql.Date Fecha) {
        this.Fecha = Fecha;
    }

    public String getAsistencia() {
        return Asistencia;
    }

    public void setAsistencia(String Asistencia) {
        this.Asistencia = Asistencia;
    }

    public String getOcasion() {
        return Ocasion;
    }

    public void setOcasion(String Ocasion) {
        this.Ocasion = Ocasion;
    }
    
    
}
