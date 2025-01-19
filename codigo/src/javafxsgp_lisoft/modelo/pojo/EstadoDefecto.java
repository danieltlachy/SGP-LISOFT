/*
*Autor: Mongeote Tlachy Daniel
*Fecha de creación: 12/11/2023
*Fecha de modificación: 12/11/2023
*Descripción: POJO de los estados de una lista de defectos reportados de un proyecto
*/
package javafxsgp_lisoft.modelo.pojo;

public class EstadoDefecto {
    
    private int idEstadoDefecto;
    private String nombre;

    public EstadoDefecto() {
    }

    public EstadoDefecto(int idEstadoDefecto, String nombre) {
        this.idEstadoDefecto = idEstadoDefecto;
        this.nombre = nombre;
    }

    public int getIdEstadoDefecto() {
        return idEstadoDefecto;
    }

    public void setIdEstadoDefecto(int idEstadoDefecto) {
        this.idEstadoDefecto = idEstadoDefecto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
