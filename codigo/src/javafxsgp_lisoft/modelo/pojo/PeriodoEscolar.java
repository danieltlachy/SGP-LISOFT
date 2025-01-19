/*
*Autor: Mongeote Tlachy Daniel
*Fecha de creación: 12/11/2023
*Fecha de modificación: 12/11/2023
*Descripción: POJO de los periodos escolares que se contemplan en el proyecto
*/
package javafxsgp_lisoft.modelo.pojo;

public class PeriodoEscolar {
    
    private int idPeriodoEscolar;
    private String fechaInicio;
    private String fechaFin;

    public PeriodoEscolar() {
    }

    public PeriodoEscolar(int idPeriodoEscolar, String fechaInicio, String fechaFin) {
        this.idPeriodoEscolar = idPeriodoEscolar;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public int getIdPeriodoEscolar() {
        return idPeriodoEscolar;
    }

    public void setIdPeriodoEscolar(int idPeriodoEscolar) {
        this.idPeriodoEscolar = idPeriodoEscolar;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }
    
}
