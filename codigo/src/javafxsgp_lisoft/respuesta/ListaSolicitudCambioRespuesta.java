/*
*Autor: Martinez Caixba Miguel Angel
*Fecha de creación: 02/12/2023
*Fecha de modificación: 02/12/2023
*Descripción: Manejador de respuestas de las solicitudes de cambio
*/
package javafxsgp_lisoft.respuesta;

import java.util.ArrayList;
import javafxsgp_lisoft.modelo.pojo.SolicitudCambio;

public class ListaSolicitudCambioRespuesta {
    private int codigoRespuesta;
    private ArrayList<SolicitudCambio> solicitudesCambio;

    public ListaSolicitudCambioRespuesta() {
    }

    public ListaSolicitudCambioRespuesta(int codigoRespuesta, ArrayList<SolicitudCambio> solicitudesCambio) {
        this.codigoRespuesta = codigoRespuesta;
        this.solicitudesCambio = solicitudesCambio;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<SolicitudCambio> getSolicitudesCambio() {
        return solicitudesCambio;
    }

    public void setSolicitudesCambio(ArrayList<SolicitudCambio> solicitudesCambio) {
        this.solicitudesCambio = solicitudesCambio;
    }
    
    
}
