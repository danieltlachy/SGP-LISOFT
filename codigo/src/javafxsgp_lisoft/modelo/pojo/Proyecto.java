/*
*Autor: Mongeote Tlachy Daniel
*Fecha de creación: 01/11/2023
*Fecha de modificación: 17/11/2023
*Descripción: POJO del proyecto
*/
package javafxsgp_lisoft.modelo.pojo;

public class Proyecto {
    
    private int idProyecto;
    private String nombre;
    private String fechaInicio;
    private String fechaFin;
    private String descripcion;
    private int numDesarrolladoresMax;
    private int idEstadoProyecto;
    private String estado;
    private int idMateria;
    private int idResponsableProyecto;
    private String nombreMateria;

    public Proyecto() {
    }

    public Proyecto(int idProyecto, String nombre, String fechaInicio, String fechaFin, String descripcion, int numDesarrolladoresMax, int idEstadoProyecto, String estado, int idMateria, int idResponsableProyecto, String nombreMateria) {
        this.idProyecto = idProyecto;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.descripcion = descripcion;
        this.numDesarrolladoresMax = numDesarrolladoresMax;
        this.idEstadoProyecto = idEstadoProyecto;
        this.estado = estado;
        this.idMateria = idMateria;
        this.idResponsableProyecto = idResponsableProyecto;
        this.nombreMateria = nombreMateria;
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getNumDesarrolladoresMax() {
        return numDesarrolladoresMax;
    }

    public void setNumDesarrolladoresMax(int numDesarrolladoresMax) {
        this.numDesarrolladoresMax = numDesarrolladoresMax;
    }

    public int getIdEstadoProyecto() {
        return idEstadoProyecto;
    }

    public void setIdEstadoProyecto(int idEstadoProyecto) {
        this.idEstadoProyecto = idEstadoProyecto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }

    public int getIdResponsableProyecto() {
        return idResponsableProyecto;
    }

    public void setIdResponsableProyecto(int idResponsableProyecto) {
        this.idResponsableProyecto = idResponsableProyecto;
    }

    public String getNombreMateria() {
        return nombreMateria;
    }

    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }

    @Override
    public String toString() {
        return nombre;
    }
}