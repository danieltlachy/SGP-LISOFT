/*
*Autor: Torres Osorio Alesis de Jesus
*Fecha de creación: 10/11/2023
*Fecha de modificación: 17/11/2023
*Descripción: POJO que contiene varios desarrolladores
*/
package javafxsgp_lisoft.respuesta;

import javafxsgp_lisoft.modelo.pojo.Desarrollador;

public class RespuestaDesarrollador {
    private boolean error;
    private String mensaje;
    private Desarrollador desarrollador;

    public RespuestaDesarrollador(boolean error, String mensaje, Desarrollador desarrollador) {
        this.error = error;
        this.mensaje = mensaje;
        this.desarrollador = desarrollador;
    }

    public RespuestaDesarrollador() {
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Desarrollador getDesarrollador() {
        return desarrollador;
    }

    public void setDesarrollador(Desarrollador desarrollador) {
        this.desarrollador = desarrollador;
    }
}
