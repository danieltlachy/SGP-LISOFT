/*
*Autor: Mongeote Tlachy Daniel
*Fecha de creación: 12/11/2023
*Fecha de modificación: 12/11/2023
*Descripción: POJO de los estados de una lista de actividades de un proyecto
*/
package javafxsgp_lisoft.modelo.pojo;

public class EstadoActividad {
    
    private int idEstadoActividad;
    private String nombre;

    public EstadoActividad() {
    }

    public EstadoActividad(int idEstadoActividad, String nombre) {
        this.idEstadoActividad = idEstadoActividad;
        this.nombre = nombre;
    }

    public int getIdEstadoActividad() {
        return idEstadoActividad;
    }

    public void setIdEstadoActividad(int idEstadoActividad) {
        this.idEstadoActividad = idEstadoActividad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
