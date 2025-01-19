/*
*Autor: Mongeote Tlachy Daniel
*Fecha de creación: 12/11/2023
*Fecha de modificación: 12/11/2023
*Descripción: POJO del responsable del proyecto
*/
package javafxsgp_lisoft.modelo.pojo;

public class ResponsableProyecto extends Usuario {
    
    private int idResponsableProyecto;
    private String numeroPersonal;
    private String nombreCompleto;


    public ResponsableProyecto() {
    }

    public ResponsableProyecto(int idResponsableProyecto, String numeroPersonal, String nombreCompleto, int idProyecto, int idUsuario, String nombre, String apellidoPaterno, String apellidoMaterno, String correo, String contrasena) {
        super(idUsuario, nombre, apellidoPaterno, apellidoMaterno, correo, contrasena,idProyecto);
        this.idResponsableProyecto = idResponsableProyecto;
        this.numeroPersonal = numeroPersonal;
        this.nombreCompleto = nombreCompleto;
        this.idProyecto = idProyecto;
    }

    public int getIdResponsableProyecto() {
        return idResponsableProyecto;
    }

    public void setIdResponsableProyecto(int idResponsableProyecto) {
        this.idResponsableProyecto = idResponsableProyecto;
    }

    public String getNumeroPersonal() {
        return numeroPersonal;
    }

    public void setNumeroPersonal(String numeroPersonal) {
        this.numeroPersonal = numeroPersonal;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
    
    @Override
    public String toString() {
        return nombreCompleto;
    }
    
}
