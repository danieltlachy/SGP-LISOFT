/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxsgp_lisoft.respuesta;

import java.util.ArrayList;
import javafxsgp_lisoft.modelo.pojo.TipoCambio;

public class ListaTipoCambioRespuesta {
    private int codigoRespuesta;
    private ArrayList<TipoCambio> tiposCambio;

    public ListaTipoCambioRespuesta() {
    }

    public ListaTipoCambioRespuesta(int codigoRespuesta, ArrayList<TipoCambio> tiposCambio) {
        this.codigoRespuesta = codigoRespuesta;
        this.tiposCambio = tiposCambio;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<TipoCambio> getTiposCambio() {
        return tiposCambio;
    }

    public void setTiposCambio(ArrayList<TipoCambio> tiposCambio) {
        this.tiposCambio = tiposCambio;
    }
    
    
}
