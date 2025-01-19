/*
*Autor: Torres Osorio Alesis de Jesus
*Fecha de creación: 10/11/2023
*Fecha de modificación: 17/11/2023
*Descripción: Clase encargada de la comunicación con la BD para manipular la información de todas las actividades.
*/
package javafxsgp_lisoft.respuesta;

import javafxsgp_lisoft.modelo.pojo.Desarrollador;

public class Bitacora {
    private Desarrollador desarrollador;
    private String nombreDesarrollador;
    private String ultimaModificacion;
    private String tipo;

    public Bitacora() {
    }

    public Bitacora(Desarrollador desarrollador, String nombreDesarrollador, String ultimaModificacion, String tipo) {
        this.desarrollador = desarrollador;
        this.nombreDesarrollador = nombreDesarrollador;
        this.ultimaModificacion = ultimaModificacion;
        this.tipo = tipo;
    }

    public Desarrollador getDesarrollador() {
        return desarrollador;
    }

    public void setDesarrollador(Desarrollador desarrollador) {
        this.desarrollador = desarrollador;
    }

    public String getNombreDesarrollador() {
        return nombreDesarrollador;
    }

    public void setNombreDesarrollador(String nombreDesarrollador) {
        this.nombreDesarrollador = nombreDesarrollador;
    }

    public String getUltimaModificacion() {
        return ultimaModificacion;
    }

    public void setUltimaModificacion(String ultimaModificacion) {
        this.ultimaModificacion = ultimaModificacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
