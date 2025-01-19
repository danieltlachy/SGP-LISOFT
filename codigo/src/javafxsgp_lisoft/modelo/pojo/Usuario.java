/*
*Autor: Mongeote Tlachy Daniel
*Fecha de creación: 01/11/2023
*Fecha de modificación: 17/11/2023
*Descripción: POJO del usuario
*/
package javafxsgp_lisoft.modelo.pojo;

public class Usuario {
    protected int idUsuario;
    protected String nombre;
    protected String apellidoPaterno;
    protected String apellidoMaterno;
    protected String correo;
    protected String contrasena;
    protected int idProyecto;

    public Usuario(int idUsuario, String nombre, String apellidoPaterno, String apellidoMaterno, String correo, String contrasena, int idProyecto) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.correo = correo;
        this.contrasena = contrasena;
        this.idProyecto = idProyecto;
    }

    public Usuario() {
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }


    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }
    
    @Override
    public String toString() {
        return " " + nombre + " " + apellidoPaterno + " " + apellidoMaterno;
    }
}
