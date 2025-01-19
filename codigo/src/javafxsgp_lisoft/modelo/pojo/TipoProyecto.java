/*
*Autor: Mongeote Tlachy Daniel
*Fecha de creación: 01/11/2023
*Fecha de modificación: 17/11/2023
*Descripción: POJO de los tipos de un proyecto
*/
package javafxsgp_lisoft.modelo.pojo;

public class TipoProyecto {
    private Integer idTipoProyecto;
    private String nombre;

    public TipoProyecto() {
    }

    public TipoProyecto(Integer idTipoProyecto, String nombre) {
        this.idTipoProyecto = idTipoProyecto;
        this.nombre = nombre;
    }

    public Integer getIdTipoProyecto() {
        return idTipoProyecto;
    }

    public void setIdTipoProyecto(Integer idTipoProyecto) {
        this.idTipoProyecto = idTipoProyecto;
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
