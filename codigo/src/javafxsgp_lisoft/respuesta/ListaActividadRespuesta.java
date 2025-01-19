/*
*Autor: Torres Osorio Alesis de Jesus
*Fecha de creación: 10/11/2023
*Fecha de modificación: 17/11/2023
*Descripción: POJO que contiene varias actividades
*/
package javafxsgp_lisoft.respuesta;

import java.util.ArrayList;
import javafxsgp_lisoft.modelo.pojo.Actividad;

/**
 *
 * @author alesi
 */
public class ListaActividadRespuesta {
    private ArrayList <Actividad> actividades;
    private int codigoRespuesta;

    public ListaActividadRespuesta() {
    }

    public ListaActividadRespuesta(ArrayList<Actividad> actividades, int codigoRespuesta) {
        this.actividades = actividades;
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Actividad> getActividades() {
        return actividades;
    }

    public void setActividades(ArrayList<Actividad> actividades) {
        this.actividades = actividades;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }
    
    
}
