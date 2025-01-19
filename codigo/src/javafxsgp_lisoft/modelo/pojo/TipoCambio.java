/*
*Autor: Torres Osorio Alesis de Jesus
*Fecha de creación: 01/11/2023
*Fecha de modificación: 17/11/2023
*Descripción: POJO de los tipos de un cambio
*/
package javafxsgp_lisoft.modelo.pojo;

public class TipoCambio {
    private int idTipoCambio;
    private String nombre;

    public TipoCambio(int idTipoCambio, String nombre) {
        this.idTipoCambio = idTipoCambio;
        this.nombre = nombre;
    }

    public TipoCambio() {
    }

    public int getIdTipoCambio() {
        return idTipoCambio;
    }

    public void setIdTipoCambio(int idTipoCambio) {
        this.idTipoCambio = idTipoCambio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    @Override
    public String toString() {
        return nombre;
    }
    
}
