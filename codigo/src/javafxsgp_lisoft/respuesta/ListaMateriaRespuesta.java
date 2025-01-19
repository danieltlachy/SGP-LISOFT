/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxsgp_lisoft.respuesta;

import java.util.ArrayList;
import javafxsgp_lisoft.modelo.pojo.Materia;

/**
 *
 * @author alesi
 */
public class ListaMateriaRespuesta {
    private int codigoRespuesta;
    private ArrayList<Materia> materias;

    public ListaMateriaRespuesta() {
    }

    public ListaMateriaRespuesta(int codigoRespuesta, ArrayList<Materia> materias) {
        this.codigoRespuesta = codigoRespuesta;
        this.materias = materias;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(ArrayList<Materia> materias) {
        this.materias = materias;
    }
    
    
}
