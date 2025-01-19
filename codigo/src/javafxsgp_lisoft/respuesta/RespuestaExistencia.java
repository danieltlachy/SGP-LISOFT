/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxsgp_lisoft.respuesta;

/**
 *
 * @author alesi
 */
public class RespuestaExistencia {
    private boolean existencia;
    private int codigoRespuesta;

    public RespuestaExistencia() {
    }

    public RespuestaExistencia(boolean existencia, int codigoRespuesta) {
        this.existencia = existencia;
        this.codigoRespuesta = codigoRespuesta;
    }

    public boolean isExistencia() {
        return existencia;
    }

    public void setExistencia(boolean existencia) {
        this.existencia = existencia;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }
    
    
}
