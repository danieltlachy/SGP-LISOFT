/*
*Autor: Mongeote Tlachy Daniel
*Fecha de creación: 10/11/2023
*Fecha de modificación: 17/11/2023
*Descripción: POJO que contiene varios Desarrolladores
*/
package javafxsgp_lisoft.respuesta;

import java.util.ArrayList;
import javafxsgp_lisoft.modelo.pojo.Desarrollador;

public class ListaDesarrolladorRespuesta {
    
    private int codigoRespuesta;
    private ArrayList <Desarrollador> desarrolladores;

    public ListaDesarrolladorRespuesta() {
    }

    public ListaDesarrolladorRespuesta(int codigoRespuesta, ArrayList<Desarrollador> desarrolladores) {
        this.codigoRespuesta = codigoRespuesta;
        this.desarrolladores = desarrolladores;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Desarrollador> getDesarrolladores() {
        return desarrolladores;
    }

    public void setDesarrolladores(ArrayList<Desarrollador> desarrolladores) {
        this.desarrolladores = desarrolladores;
    }
    
}
