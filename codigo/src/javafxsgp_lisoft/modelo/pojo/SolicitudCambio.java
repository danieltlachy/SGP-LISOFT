/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxsgp_lisoft.modelo.pojo;

/**
 *
 * @author alesi
 */
public class SolicitudCambio {
    private int idSolicitudCambio;
    private String nombre;
    private String fechaSolicitud;
    private String fechaAprobacion;
    private String descripcion;
    private String razon;
    private String impacto;
    private String accionPropuesta;
    private int idProyecto;
    private String nombreProyecto;
    private int idDesarrollador;
    private String nombreDesarrollador;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private int idDefecto;
    private String nombreDefecto;
    private int idEstadoSolicitud;
    private String estado;

    public SolicitudCambio(int idSolicitudCambio, String nombre, String fechaSolicitud, String fechaAprobacion, String descripcion, String razon, String impacto, String accionPropuesta, int idProyecto, String nombreProyecto, int idDesarrollador, String nombreDesarrollador, String apellidoPaterno, String apellidoMaterno, int idDefecto, String nombreDefecto, int idEstadoSolicitud, String estado) {
        this.idSolicitudCambio = idSolicitudCambio;
        this.nombre = nombre;
        this.fechaSolicitud = fechaSolicitud;
        this.fechaAprobacion = fechaAprobacion;
        this.descripcion = descripcion;
        this.razon = razon;
        this.impacto = impacto;
        this.accionPropuesta = accionPropuesta;
        this.idProyecto = idProyecto;
        this.nombreProyecto = nombreProyecto;
        this.idDesarrollador = idDesarrollador;
        this.nombreDesarrollador = nombreDesarrollador;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.idDefecto = idDefecto;
        this.nombreDefecto = nombreDefecto;
        this.idEstadoSolicitud = idEstadoSolicitud;
        this.estado = estado;
    }

    public SolicitudCambio() {
    }

    public int getIdSolicitudCambio() {
        return idSolicitudCambio;
    }

    public void setIdSolicitudCambio(int idSolicitudCambio) {
        this.idSolicitudCambio = idSolicitudCambio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(String fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public String getFechaAprobacion() {
        return fechaAprobacion;
    }

    public void setFechaAprobacion(String fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRazon() {
        return razon;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    public String getImpacto() {
        return impacto;
    }

    public void setImpacto(String impacto) {
        this.impacto = impacto;
    }

    public String getAccionPropuesta() {
        return accionPropuesta;
    }

    public void setAccionPropuesta(String accionPropuesta) {
        this.accionPropuesta = accionPropuesta;
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

    public String getNombreDesarrollador() {
        return nombreDesarrollador;
    }

    public void setNombreDesarrollador(String nombreDesarrollador) {
        this.nombreDesarrollador = nombreDesarrollador;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public int getIdDefecto() {
        return idDefecto;
    }

    public void setIdDefecto(int idDefecto) {
        this.idDefecto = idDefecto;
    }

    public String getNombreDefecto() {
        return nombreDefecto;
    }

    public void setNombreDefecto(String nombreDefecto) {
        this.nombreDefecto = nombreDefecto;
    }

    public int getIdEstadoSolicitud() {
        return idEstadoSolicitud;
    }

    public void setIdEstadoSolicitud(int idEstadoSolicitud) {
        this.idEstadoSolicitud = idEstadoSolicitud;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    @Override
    public String toString() {
        return nombre;
    }
}