/*
*Autor: Mongeote Tlachy Daniel
*Fecha de creación: 15/10/2023
*Fecha de modificación: 15/10/2023
*Descripción: Clase encargada de la conexión a la base de datos
*/
package javafxsgp_lisoft.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    
    public static final String DRIVER = "com.mysql.jdbc.Driver";
    public static final String NOMBRE_BD = "spglisoft";
    public static final String HOSTNAME = "localhost";
    public static final String PUERTO = "3306";
    public static final String USUARIO = "adminsgplisoft";
    public static final String PASSWORD = "sgp_1234";
    private static String urlConexion = "jdbc:mysql://"+HOSTNAME+":"+PUERTO+"/"+NOMBRE_BD+"?allowPublicKeyRetrieval=true&useSSL=false";
    
    public static Connection obtenerConexion(){
        Connection conexionBD = null;
        try{
            Class.forName(DRIVER);
            conexionBD = DriverManager.getConnection(urlConexion, USUARIO, PASSWORD);
        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println("Error de conexion con BD:" + ex.getMessage());
            ex.printStackTrace();
        }
        return conexionBD;
    }
}
