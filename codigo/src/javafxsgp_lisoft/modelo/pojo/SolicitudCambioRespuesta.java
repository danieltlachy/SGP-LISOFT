/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxsgp_lisoft.modelo.pojo;

import java.util.ArrayList;

/**
 *
 * @author Miguel Caixba
 */
public class SolicitudCambioRespuesta {
    private int codigoRespuesta;
    private ArrayList<SolicitudCambio> solicitudesCambio;

    public SolicitudCambioRespuesta() {
    }

    public SolicitudCambioRespuesta(int codigoRespuesta, ArrayList<SolicitudCambio> solicitudesCambio) {
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
