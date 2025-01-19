/*
*Autor: Mongeote Tlachy Daniel
*Fecha de creación: 12/11/2023
*Fecha de modificación: 12/11/2023
*Descripción: POJO de los estados de una lista de solicitudes de un proyecto
*/
package javafxsgp_lisoft.modelo.pojo;

public class EstadoSolicitud {
    
    private int idEstadoSolicitud;
    private String nombre;

    public EstadoSolicitud() {
    }

    public EstadoSolicitud(int idEstadoSolicitud, String nombre) {
        this.idEstadoSolicitud = idEstadoSolicitud;
        this.nombre = nombre;
    }

    public int getIdEstadoSolicitud() {
        return idEstadoSolicitud;
    }

    public void setIdEstadoSolicitud(int idEstadoSolicitud) {
        this.idEstadoSolicitud = idEstadoSolicitud;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
