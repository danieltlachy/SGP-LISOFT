/*
*Autor: Mongeote Tlachy Daniel
*Fecha de creación: 10/11/2023
*Fecha de modificación: 17/11/2023
*Descripción: Clase encargada de la comunicación con la BD para manipular la información de todos los responsables de proyectos.
*/
package javafxsgp_lisoft.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxsgp_lisoft.modelo.ConexionBD;
import javafxsgp_lisoft.modelo.pojo.ResponsableProyecto;
import javafxsgp_lisoft.respuesta.ListaResponsableProyectoRespuesta;
import javafxsgp_lisoft.utils.Constantes;

public class ResponsableProyectoDAO {
    
    public static ListaResponsableProyectoRespuesta obtenerResponsables(int idResponsable){
        ListaResponsableProyectoRespuesta respuesta = new ListaResponsableProyectoRespuesta();
        Connection conexionBD = ConexionBD.obtenerConexion();
        if(conexionBD != null){
            try{
                String consulta = "SELECT r.idResponsableProyecto, u.nombre, u.apellidoPaterno, u.apellidoMaterno\n" +
                    "FROM usuario u\n" +
                    "JOIN responsableproyecto r ON u.idUsuario = r.idResponsableProyecto\n" +
                    "WHERE r.idProyecto IS NULL";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<ResponsableProyecto> responsables = new ArrayList();
                while(resultado.next()){
                    ResponsableProyecto tipos = new ResponsableProyecto();
                    tipos.setIdResponsableProyecto(resultado.getInt("idResponsableProyecto"));
                    tipos.setNombre(resultado.getString("nombre"));
                    tipos.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    tipos.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    String nombreCompleto = resultado.getString("nombre") + " " +
                        resultado.getString("apellidoPaterno") + " " +
                        resultado.getString("apellidoMaterno");
                    tipos.setNombreCompleto(nombreCompleto);
                    responsables.add(tipos);
                }
                respuesta.setResponsablesProyecto(responsables);
                conexionBD.close();
                respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
            }catch(SQLException e){
               e.printStackTrace();
               respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        }else{
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
    
}
