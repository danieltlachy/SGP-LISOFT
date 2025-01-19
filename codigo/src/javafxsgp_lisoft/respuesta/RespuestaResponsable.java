/*
*Autor: Torres Osorio Alesis de Jesus
*Fecha de creación: 10/11/2023
*Fecha de modificación: 17/11/2023
*Descripción: POJO que contiene la respuesta para el usuario Responsable de proyecto
*/
package javafxsgp_lisoft.respuesta;

import javafxsgp_lisoft.modelo.pojo.ResponsableProyecto;

public class RespuestaResponsable {
    private boolean error;
    private String mensaje;
    private ResponsableProyecto responsable;

    public RespuestaResponsable(boolean error, String mensaje, ResponsableProyecto responsable) {
        this.error = error;
        this.mensaje = mensaje;
        this.responsable = responsable;
    }

    public RespuestaResponsable() {
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

    public ResponsableProyecto getResponsable() {
        return responsable;
    }

    public void setResponsable(ResponsableProyecto responsable) {
        this.responsable = responsable;
    }
}
