/*
*Autor: Torres Osorio Alesis de Jesus
*Fecha de creación: 10/11/2023
*Fecha de modificación: 17/11/2023
*Descripción: POJO que determina la respuesta para el usuario de inicio de sesión
*/
package javafxsgp_lisoft.respuesta;

public class RespuestaLogin {
    private boolean error;
    private String mensaje;
    private boolean existencia;

    public RespuestaLogin(boolean error, String mensaje, boolean existencia) {
        this.error = error;
        this.mensaje = mensaje;
        this.existencia = existencia;
    }

    public RespuestaLogin() {
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

    public boolean isExistencia() {
        return existencia;
    }

    public void setExistencia(boolean existencia) {
        this.existencia = existencia;
    }
}