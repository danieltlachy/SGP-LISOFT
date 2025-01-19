/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxsgp_lisoft.modelo.pojo;

/**
 *
 * @author alesi
 */
public class Cambio {

    private int idCambio;
    private String nombre;
    private String fechaCreacion;
    private String descripcion;
    private String tipo;
    private int esfuerzo;
    private int idProyecto;
    private int idDesarrollador;
    private int idSolicitudCambio;
    private int idEstadoCambio;
    private String estado;
    private int idTipoCambio;
    private int idDefecto;

    public Cambio(int idCambio, String nombre, String fechaCreacion, String descripcion, String tipo, int esfuerzo, int idProyecto, int idDesarrollador, int idSolicitudCambio, int idEstadoCambio, String estado, int idTipoCambio,int idDefecto) {
        this.idCambio = idCambio;
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.esfuerzo = esfuerzo;
        this.idProyecto = idProyecto;
        this.idDesarrollador = idDesarrollador;
        this.idSolicitudCambio = idSolicitudCambio;
        this.idEstadoCambio = idEstadoCambio;
        this.estado = estado;
        this.idTipoCambio = idTipoCambio;
        this.idDefecto = idDefecto;
    }

    public Cambio() {
    }

    public int getIdCambio() {
        return idCambio;
    }

    public void setIdCambio(int idCambio) {
        this.idCambio = idCambio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getEsfuerzo() {
        return esfuerzo;
    }

    public void setEsfuerzo(int esfuerzo) {
        this.esfuerzo = esfuerzo;
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
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

    public int getIdEstadoCambio() {
        return idEstadoCambio;
    }

    public void setIdEstadoCambio(int idEstadoCambio) {
        this.idEstadoCambio = idEstadoCambio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdTipoCambio() {
        return idTipoCambio;
    }

    public void setIdTipoCambio(int idTipoCambio) {
        this.idTipoCambio = idTipoCambio;
    }

    public int getIdDefecto() {
        return idDefecto;
    }

    public void setIdDefecto(int idDefecto) {
        this.idDefecto = idDefecto;
    }
    
}