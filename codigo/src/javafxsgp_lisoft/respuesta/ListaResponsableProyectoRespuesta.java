package javafxsgp_lisoft.respuesta;

import java.util.ArrayList;
import javafxsgp_lisoft.modelo.pojo.ResponsableProyecto;


public class ListaResponsableProyectoRespuesta {
    private int codigoRespuesta;
    private ArrayList <ResponsableProyecto> ResponsablesProyecto;

    public ListaResponsableProyectoRespuesta() {
    }

    public ListaResponsableProyectoRespuesta(int codigoRespuesta, ArrayList<ResponsableProyecto> ResponsablesProyecto) {
        this.codigoRespuesta = codigoRespuesta;
        this.ResponsablesProyecto = ResponsablesProyecto;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<ResponsableProyecto> getResponsablesProyecto() {
        return ResponsablesProyecto;
    }

    public void setResponsablesProyecto(ArrayList<ResponsableProyecto> ResponsablesProyecto) {
        this.ResponsablesProyecto = ResponsablesProyecto;
    }
    
}
