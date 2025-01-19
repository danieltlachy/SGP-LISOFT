/*
*Autor: Mongeote Tlachy Daniel
*Fecha de creación: 10/11/2023
*Fecha de modificación: 17/11/2023
*Descripción: POJO que contiene varios Responsables de Proyecto
*/
package javafxsgp_lisoft.respuesta;

import java.util.ArrayList;
import javafxsgp_lisoft.modelo.pojo.ResponsableProyecto;

public class RespuestaListaResponsableProyecto {
    
    private int codigoRespuesta;
    private ArrayList <ResponsableProyecto> responsables;

    public RespuestaListaResponsableProyecto() {
    }

    public RespuestaListaResponsableProyecto(int codigoRespuesta, ArrayList<ResponsableProyecto> responsables) {
        this.codigoRespuesta = codigoRespuesta;
        this.responsables = responsables;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<ResponsableProyecto> getResponsables() {
        return responsables;
    }

    public void setResponsables(ArrayList<ResponsableProyecto> responsables) {
        this.responsables = responsables;
    }
    
}
