/*
*Autor: Mongeote Tlachy Daniel
*Fecha de creación: 12/11/2023
*Fecha de modificación: 12/11/2023
*Descripción: POJO de la Inscripcion a la Experiencia Educativa
*/
package javafxsgp_lisoft.modelo.pojo;

public class InscripcionExperienciaEducativa {
    
    private int idInscripcion;
    private int idExperienciaEducativa;
    private int idDesarrollador;

    public InscripcionExperienciaEducativa() {
    }

    public InscripcionExperienciaEducativa(int idInscripcion, int idExperienciaEducativa, int idDesarrollador) {
        this.idInscripcion = idInscripcion;
        this.idExperienciaEducativa = idExperienciaEducativa;
        this.idDesarrollador = idDesarrollador;
    }

    public int getIdInscripcion() {
        return idInscripcion;
    }

    public void setIdInscripcion(int idInscripcion) {
        this.idInscripcion = idInscripcion;
    }

    public int getIdExperienciaEducativa() {
        return idExperienciaEducativa;
    }

    public void setIdExperienciaEducativa(int idExperienciaEducativa) {
        this.idExperienciaEducativa = idExperienciaEducativa;
    }

    public int getIdDesarrollador() {
        return idDesarrollador;
    }

    public void setIdDesarrollador(int idDesarrollador) {
        this.idDesarrollador = idDesarrollador;
    }
}
