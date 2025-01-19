/*
*Autor: Torres Osorio Alesis de Jesus
*Fecha de creación: 10/11/2023
*Fecha de modificación: 17/11/2023
*Descripción: POJO que contiene varios defectos
*/
package javafxsgp_lisoft.respuesta;

import javafxsgp_lisoft.modelo.pojo.Defecto;

public class RespuestaDefecto {
    private int codigoRespuesta;
    private Defecto defecto;

    public RespuestaDefecto(int codigoRespuesta, Defecto defecto) {
        this.codigoRespuesta = codigoRespuesta;
        this.defecto = defecto;
    }

    public RespuestaDefecto() {
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public Defecto getDefecto() {
        return defecto;
    }

    public void setDefecto(Defecto defecto) {
        this.defecto = defecto;
    }
}
