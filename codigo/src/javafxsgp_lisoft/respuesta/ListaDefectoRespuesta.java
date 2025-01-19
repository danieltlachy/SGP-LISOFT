/*
*Autor: Torres Osorio Alesis de Jesus
*Fecha de creación: 10/11/2023
*Fecha de modificación: 17/11/2023
*Descripción: POJO que contiene varios defectos
*/
package javafxsgp_lisoft.respuesta;

import java.util.ArrayList;
import javafxsgp_lisoft.modelo.pojo.Defecto;

public class ListaDefectoRespuesta {
    private int codigoRespuesta;
    private ArrayList<Defecto> defectos;

    public ListaDefectoRespuesta(int codigoRespuesta, ArrayList<Defecto> defectos) {
        this.codigoRespuesta = codigoRespuesta;
        this.defectos = defectos;
    }

    public ListaDefectoRespuesta() {
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Defecto> getDefectos() {
        return defectos;
    }

    public void setDefectos(ArrayList<Defecto> defectos) {
        this.defectos = defectos;
    }
}
