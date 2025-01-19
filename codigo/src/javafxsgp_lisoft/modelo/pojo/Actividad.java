/*
*Autor: Mongeote Tlachy Daniel
*Fecha de creación: 12/11/2023
*Fecha de modificación: 12/11/2023
*Descripción: POJO de las actividades de un proyecto
*/
package javafxsgp_lisoft.modelo.pojo;

public class Actividad {
    
    private int idActividad;
    private String nombre;
    private String fechaInicio;
    private String fechaFin;
    private String descripcion;
    private int esfuerzo;    
    private boolean asignada;
    
    private int idProyecto;
    private String nombreProyecto;
    private int idDesarrollador;
    private int idSolicitudCambio;
    private int idEstadoActividad;
    private String estado;

    public Actividad() {
    }

    public Actividad(int idActividad, String nombre, String fechaInicio, String fechaFin, String descripcion, int esfuerzo, boolean asignada, int idProyecto, String nombreProyecto, int idDesarrollador, int idSolicitudCambio, int idEstadoActividad, String estado) {
        this.idActividad = idActividad;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.descripcion = descripcion;
        this.esfuerzo = esfuerzo;
        this.asignada = asignada;
        this.idProyecto = idProyecto;
        this.nombreProyecto = nombreProyecto;
        this.idDesarrollador = idDesarrollador;
        this.idSolicitudCambio = idSolicitudCambio;
        this.idEstadoActividad = idEstadoActividad;
        this.estado = estado;
    }

    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
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

    public int getEsfuerzo() {
        return esfuerzo;
    }

    public void setEsfuerzo(int esfuerzo) {
        this.esfuerzo = esfuerzo;
    }

    public boolean isAsignada() {
        return asignada;
    }

    public void setAsignada(boolean asignada) {
        this.asignada = asignada;
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public int getIdDesarrollador() {
        return idDesarrollador;
    }

    public void setIdDesarrollador(int idDesarrollador) {
        this.idDesarrollador = idDesarrollador;
    }

    public int getIdSolicitudCambio() {
        return idSolicitudCambio;
    }

    public void setIdSolicitudCambio(int idSolicitudCambio) {
        this.idSolicitudCambio = idSolicitudCambio;
    }

    public int getIdEstadoActividad() {
        return idEstadoActividad;
    }

    public void setIdEstadoActividad(int idEstadoActividad) {
        this.idEstadoActividad = idEstadoActividad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
