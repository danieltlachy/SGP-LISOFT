/*
*Autor: Mongeote Tlachy Daniel
*Fecha de creación: 10/11/2023
*Fecha de modificación: 17/11/2023
*Descripción: POJO que contiene varios Proyectos
*/
package javafxsgp_lisoft.respuesta;

import java.util.ArrayList;
import javafxsgp_lisoft.modelo.pojo.Proyecto;


public class ProyectoRespuesta {
    
    private int codigoRespuesta;
    private ArrayList <Proyecto> proyectos;

    public ProyectoRespuesta() {
    }

    public ProyectoRespuesta(int codigoRespuesta, ArrayList<Proyecto> proyectos) {
        this.codigoRespuesta = codigoRespuesta;
        this.proyectos = proyectos;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Proyecto> getProyectos() {
        return proyectos;
    }

    public void setProyectos(ArrayList<Proyecto> proyectos) {
        this.proyectos = proyectos;
    }
}
