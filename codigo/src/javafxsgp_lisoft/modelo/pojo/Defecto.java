/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxsgp_lisoft.modelo.pojo;

/**
 *
 * @author alesi
 */
public class Defecto {
    private int idDefecto;
    private String nombre;
    private String fechaDeteccion;
    private String descripcion;
    private String estado;
    private int esfuerzo;
    private int idProyecto;
    private int idDesarrollador;
    private String nombreDesarrollador;
    private int idEstadoDefecto;
    private int idTipoDefecto;
    private String tipo;
    
    

    public Defecto() {
    }

    public Defecto(int idDefecto, String nombre, String fechaDeteccion, String descripcion, String estado, int esfuerzo, int idProyecto, int idDesarrollador, String nombreDesarrollador, int idEstadoDefecto, int idTipoDefecto, String tipo) {
        this.idDefecto = idDefecto;
        this.nombre = nombre;
        this.fechaDeteccion = fechaDeteccion;
        this.descripcion = descripcion;
        this.estado = estado;
        this.esfuerzo = esfuerzo;
        this.idProyecto = idProyecto;
        this.idDesarrollador = idDesarrollador;
        this.nombreDesarrollador = nombreDesarrollador;
        this.idEstadoDefecto = idEstadoDefecto;
        this.idTipoDefecto = idTipoDefecto;
        this.tipo = tipo;
    }

    public int getIdDefecto() {
        return idDefecto;
    }

    public void setIdDefecto(int idDefecto) {
        this.idDefecto = idDefecto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaDeteccion() {
        return fechaDeteccion;
    }

    public void setFechaDeteccion(String fechaDeteccion) {
        this.fechaDeteccion = fechaDeteccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public String getNombreDesarrollador() {
        return nombreDesarrollador;
    }

    public void setNombreDesarrollador(String nombreDesarrollador) {
        this.nombreDesarrollador = nombreDesarrollador;
    }

    public int getIdEstadoDefecto() {
        return idEstadoDefecto;
    }

    public void setIdEstadoDefecto(int idEstadoDefecto) {
        this.idEstadoDefecto = idEstadoDefecto;
    }

    public int getIdTipoDefecto() {
        return idTipoDefecto;
    }

    public void setIdTipoDefecto(int idTipoDefecto) {
        this.idTipoDefecto = idTipoDefecto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "" + nombre ;
    }
    
}