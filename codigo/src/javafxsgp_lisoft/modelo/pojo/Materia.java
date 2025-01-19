/*
*Autor: Mongeote Tlachy Daniel
*Fecha de creación: 12/11/2023
*Fecha de modificación: 12/11/2023
*Descripción: POJO de los materias pertenecientes a una lista de Experiencia Educativas disponibles en el sistema
*/
package javafxsgp_lisoft.modelo.pojo;

public class Materia {
    
    private int idMateria;
    private String nombre;
    private int idPeriodoEscolar;
    private String fechaInicio;
    private String fechaFin;
    private int idExperienciaEducativa;
    private String NRC;
    private int idResponsableProyecto;

    public Materia() {
    }

    public Materia(int idMateria, String nombre, int idPeriodoEscolar, String fechaInicio, String fechaFin, int idExperienciaEducativa, String NRC, int idResponsableProyecto) {
        this.idMateria = idMateria;
        this.nombre = nombre;
        this.idPeriodoEscolar = idPeriodoEscolar;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.idExperienciaEducativa = idExperienciaEducativa;
        this.NRC = NRC;
        this.idResponsableProyecto = idResponsableProyecto;
    }

    public int getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public int getIdExperienciaEducativa() {
        return idExperienciaEducativa;
    }

    public void setIdExperienciaEducativa(int idExperienciaEducativa) {
        this.idExperienciaEducativa = idExperienciaEducativa;
    }

    public String getNRC() {
        return NRC;
    }

    public void setNRC(String NRC) {
        this.NRC = NRC;
    }

    public int getIdResponsableProyecto() {
        return idResponsableProyecto;
    }

    public void setIdResponsableProyecto(int idResponsableProyecto) {
        this.idResponsableProyecto = idResponsableProyecto;
    }
    
    @Override
    public String toString() {
        return nombre + " (NRC:" + NRC + ")";
    }
}
