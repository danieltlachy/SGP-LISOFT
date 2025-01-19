/*
*Autor: Torres Osorio Alesis de Jesus
*Fecha de creación: 10/11/2023
*Fecha de modificación: 17/11/2023
*Descripción: POJO que contiene varios Proyectos
*/
package javafxsgp_lisoft.respuesta;

import java.util.ArrayList;

public class ListaBitacoraRespuesta {
    private int codigoRespuesta;
    private ArrayList<Bitacora> Bitacoras;

    public ListaBitacoraRespuesta(int codigoRespuesta, ArrayList<Bitacora> Bitacoras) {
        this.codigoRespuesta = codigoRespuesta;
        this.Bitacoras = Bitacoras;
    }
    
    public ListaBitacoraRespuesta() {
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Bitacora> getBitacoras() {
        return Bitacoras;
    }

    public void setBitacoras(ArrayList<Bitacora> Bitacoras) {
        this.Bitacoras = Bitacoras;
    }
}
