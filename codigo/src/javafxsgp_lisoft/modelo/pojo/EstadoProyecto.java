/*
*Autor: Mongeote Tlachy Daniel
*Fecha de creación: 12/11/2023
*Fecha de modificación: 12/11/2023
*Descripción: POJO de los estados asignados para un proyecto.
*/
package javafxsgp_lisoft.modelo.pojo;

public class EstadoProyecto {
    
    private int idEstadoProyecto;
    private String nombre;

    public EstadoProyecto() {
    }

    public EstadoProyecto(int idEstadoProyecto, String nombre) {
        this.idEstadoProyecto = idEstadoProyecto;
        this.nombre = nombre;
    }

    public int getIdEstadoProyecto() {
        return idEstadoProyecto;
    }

    public void setIdEstadoProyecto(int idEstadoProyecto) {
        this.idEstadoProyecto = idEstadoProyecto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
