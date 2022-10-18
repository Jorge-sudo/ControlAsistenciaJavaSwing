/*
 REGISTRAR LAS ASISTENCIAS DE INGRESO DEL PRESONAL.
 */
package proyectoasistenciapersonal;

import java.util.Date;

/**
 *
 * @author katya
 */
public class Ingreso {
    
    private Date Fecha;
    private Date horaIngreso;
    private int codigo;
    private int minutosAtraso;

    public Ingreso() {
    }

    public Ingreso(Date Fecha, Date horaIngreso, int codigo, int minutosAtraso) {
        this.Fecha = Fecha;
        this.horaIngreso = horaIngreso;
        this.codigo = codigo;
        this.minutosAtraso = minutosAtraso;
    }

    public Date getFecha() {
        return Fecha;
    }

    public void setFecha(Date Fecha) {
        this.Fecha = Fecha;
    }

    public Date getHoraIngreso() {
        return horaIngreso;
    }

    public void setHoraIngreso(Date horaIngreso) {
        this.horaIngreso = horaIngreso;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getMinutosAtraso() {
        return minutosAtraso;
    }

    public void setMinutosAtraso(int minutosAtraso) {
        this.minutosAtraso = minutosAtraso;
    }
  
}
