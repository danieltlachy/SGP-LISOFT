/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxsgp_lisoft.respuesta;

import java.util.ArrayList;
import javafxsgp_lisoft.modelo.pojo.Cambio;

/**
 *
 * @author alesi
 */
public class ListaCambioRespuesta {
    private ArrayList<Cambio> listaCambio;
    private int codigoRespuesta;

    public ListaCambioRespuesta(ArrayList<Cambio> listaCambio, int codigoRespuesta) {
        this.listaCambio = listaCambio;
        this.codigoRespuesta = codigoRespuesta;
    }

    public ListaCambioRespuesta() {
    }
    
    
    public ArrayList<Cambio> getListaCambio() {
        return listaCambio;
    }

    public void setListaCambio(ArrayList<Cambio> listaCambio) {
        this.listaCambio = listaCambio;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }
    
    
}
