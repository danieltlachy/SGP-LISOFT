/*
*Autor: Mongeote Tlachy Daniel
*Fecha de creación: 17/10/2023
*Fecha de modificación: 17/10/2023
*Descripción: Clase que contiene todas las constantes del proyecto
*/
package javafxsgp_lisoft.utils;

public class Constantes {
    
    public static final int OPERACION_EXITOSA = 200;
    public static final int ERROR_CONEXION = 500;
    public static final int ERROR_CONSULTA = 501;
    
    public static final int DESARROLLADOR = 1;
    public static final int RESPONSABLE = 2;
    public static final int ESTADO_FINALIZADO = 3;
    
    public static final String ESTILO_ERROR ="-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 2;";
    public static final String ESTILO_NORMAL ="-fx-border-width: 0;";
    public static final String FORMATO_ESPANOL= "^[a-zA-Z0-9áéíóúüñÁÉÍÓÚÜÑ\\s]+$";
    public static final String FORMATO_NUMERICO = "\\d+";
}