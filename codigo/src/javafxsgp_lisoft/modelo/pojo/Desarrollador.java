/*
*Autor: Mongeote Tlachy Daniel
*Fecha de creación: 12/11/2023
*Fecha de modificación: 12/11/2023
*Descripción: POJO del desarrollador del proyecto
*/
package javafxsgp_lisoft.modelo.pojo;

public class Desarrollador extends Usuario {
    private String matricula;
    private String nombreProyecto;
    private int idMateria;
    private String nombreMateria;
    private String nombreCompleto;
    private int idExperienciaEducativa;

    public Desarrollador() {
    }

    public Desarrollador(String matricula, int idProyecto, String nombreProyecto, int idMateria, String nombreMateria, String nombreCompleto, int idExperienciaEducativa, int idUsuario, String nombre, String apellidoPaterno, String apellidoMaterno, String correo, String contrasena) {
        super(idUsuario, nombre, apellidoPaterno, apellidoMaterno, correo, contrasena,idProyecto);
        this.matricula = matricula;
        this.idProyecto = idProyecto;
        this.nombreProyecto = nombreProyecto;
        this.idMateria = idMateria;
        this.nombreMateria = nombreMateria;
        this.nombreCompleto = nombreCompleto;
        this.idExperienciaEducativa = idExperienciaEducativa;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public int getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }

    public String getNombreMateria() {
        return nombreMateria;
    }

    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public int getIdExperienciaEducativa() {
        return idExperienciaEducativa;
    }

    public void setIdExperienciaEducativa(int idExperienciaEducativa) {
        this.idExperienciaEducativa = idExperienciaEducativa;
    }
}

