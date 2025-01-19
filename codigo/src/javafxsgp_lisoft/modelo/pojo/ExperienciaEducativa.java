/*
*Autor: Mongeote Tlachy Daniel
*Fecha de creación: 12/11/2023
*Fecha de modificación: 12/11/2023
*Descripción: POJO de la Experiencia Educativa
*/
package javafxsgp_lisoft.modelo.pojo;

public class ExperienciaEducativa {
    
    private int idExperienciaEducativa;
    private String NRC;
    private int idResponsableProyecto;
    private int idPeriodoEscolar;
    private int idMateria;

    public ExperienciaEducativa() {
    }

    public ExperienciaEducativa(int idExperienciaEducativa, String NRC, int idResponsableProyecto, int idPeriodoEscolar, int idMateria) {
        this.idExperienciaEducativa = idExperienciaEducativa;
        this.NRC = NRC;
        this.idResponsableProyecto = idResponsableProyecto;
        this.idPeriodoEscolar = idPeriodoEscolar;
        this.idMateria = idMateria;
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

    public int getIdPeriodoEscolar() {
        return idPeriodoEscolar;
    }

    public void setIdPeriodoEscolar(int idPeriodoEscolar) {
        this.idPeriodoEscolar = idPeriodoEscolar;
    }

    public int getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }
}
