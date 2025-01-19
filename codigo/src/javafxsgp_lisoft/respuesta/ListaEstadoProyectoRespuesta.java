package javafxsgp_lisoft.respuesta;

import java.util.ArrayList;
import javafxsgp_lisoft.modelo.pojo.EstadoProyecto;


public class ListaEstadoProyectoRespuesta {
    private int codigoRespuesta;
    private ArrayList <EstadoProyecto> EstadosProyecto;

    public ListaEstadoProyectoRespuesta() {
    }

    public ListaEstadoProyectoRespuesta(int codigoRespuesta, ArrayList<EstadoProyecto> EstadosProyecto) {
        this.codigoRespuesta = codigoRespuesta;
        this.EstadosProyecto = EstadosProyecto;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<EstadoProyecto> getEstadosProyecto() {
        return EstadosProyecto;
    }

    public void setEstadosProyecto(ArrayList<EstadoProyecto> EstadosProyecto) {
        this.EstadosProyecto = EstadosProyecto;
    }
    
}
