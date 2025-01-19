/*
*Autor: Mongeote Tlachy Daniel
*Fecha de creación: 10/11/2023
*Fecha de modificación: 17/11/2023
*Descripción: Clase encargada de la comunicación con la BD para manipular la información de todas las actividades.
*/
package javafxsgp_lisoft.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafxsgp_lisoft.modelo.ConexionBD;
import javafxsgp_lisoft.respuesta.ListaActividadRespuesta;
import javafxsgp_lisoft.modelo.pojo.Actividad;
import javafxsgp_lisoft.modelo.pojo.Proyecto;
import javafxsgp_lisoft.utils.Constantes;

public class ActividadDAO {

    public static int guardarActividad(Actividad actividad){
        int respuesta;
        Connection conexionBD = ConexionBD.obtenerConexion();
        if(conexionBD != null){
            try{
                String sentencia = "INSERT INTO actividad (nombre, fechaInicio, fechaFin, descripcion, esfuerzo, idProyecto) VALUES (?, ?, ?, ?, ?, ?);";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, actividad.getNombre());
                prepararSentencia.setString(2, actividad.getFechaInicio());
                prepararSentencia.setString(3, actividad.getFechaFin());
                prepararSentencia.setString(4, actividad.getDescripcion());
                prepararSentencia.setInt(5, actividad.getEsfuerzo());
                prepararSentencia.setInt(6, actividad.getIdProyecto());
                int filasAfectadas = prepararSentencia.executeUpdate();
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : Constantes.ERROR_CONSULTA;
                conexionBD.close();
            }catch(SQLException e){
                e.printStackTrace();
                respuesta = Constantes.ERROR_CONSULTA;
            }
        }else{
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
    public static ListaActividadRespuesta obtenerActividadPorDesarrollador(
            int idDesarrollador
    ){
        
        ListaActividadRespuesta respuesta = new ListaActividadRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idActividad, actividad.nombre, fechaInicio, fechaFin, "
                        + "descripcion, esfuerzo, idProyecto, idDesarrollador, "
                        + "actividad.idEstadoActividad, estadoActividad.nombre FROM actividad "
                        + "INNER JOIN estadoActividad "
                        + "ON actividad.idEstadoActividad = estadoActividad.idEstadoActividad "
                        + "WHERE idDesarrollador=?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idDesarrollador);
                ResultSet resultado = prepararSentencia.executeQuery();
                
                ArrayList<Actividad> actividades = new ArrayList();
                while (resultado.next()) {
                    Actividad actividad = new Actividad();
                    actividad.setIdActividad(resultado.getInt("idActividad"));
                    actividad.setNombre(resultado.getString("nombre"));
                    actividad.setFechaInicio(resultado.getString("fechaInicio"));
                    actividad.setFechaFin(resultado.getString("fechaFin"));
                    actividad.setDescripcion(resultado.getString("descripcion"));
                    actividad.setEsfuerzo(resultado.getInt("esfuerzo"));
                    actividad.setIdProyecto(resultado.getInt("idProyecto"));
                    actividad.setIdDesarrollador(resultado.getInt("idDesarrollador"));
                    actividad.setIdEstadoActividad(resultado.getInt("idEstadoActividad"));
                    actividad.setEstado(resultado.getString("estadoActividad.nombre"));
                    actividades.add(actividad); 
                }
                
                respuesta.setActividades(actividades);
                conexionBD.close();
            } catch (SQLException e) {
                e.printStackTrace();
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
                System.err.println("Error consulta: " + e.getMessage());
            }
        } else {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }

    
    public static ListaActividadRespuesta obtActSinAsignar(){        
        ListaActividadRespuesta respuesta = new ListaActividadRespuesta();
        Connection conexionBD = ConexionBD.obtenerConexion();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if(conexionBD != null){
            try{
                String consulta = "SELECT a.nombre, DATE_FORMAT(a.fechaInicio,'%d/%m/%Y') AS fechaInicio, DATE_FORMAT(a.fechaFin, '%d/%m/%Y') AS fechaFin, " +
                    "a.esfuerzo, ea.nombre, a.idActividad " +
                    "FROM actividad a " +
                    "INNER JOIN estadoActividad ea ON a.idEstadoActividad = ea.idEstadoActividad " +
                    "LEFT JOIN desarrollador d ON a.idDesarrollador = d.idDesarrollador " +
                    "WHERE d.idDesarrollador IS NULL; ";
                    PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                    ResultSet resultado = prepararSentencia.executeQuery();
                    ArrayList<Actividad>actividadConsulta = new ArrayList();
                    while(resultado.next()){
                        Actividad actividad = new Actividad();
                        actividad.setNombre(resultado.getString("a.nombre"));
                        actividad.setFechaInicio(resultado.getString("fechaInicio"));
                        actividad.setFechaFin(resultado.getString("fechaFin"));
                        actividad.setEsfuerzo(resultado.getInt("a.esfuerzo"));
                        actividad.setEstado(resultado.getString("ea.nombre"));
                        actividad.setIdActividad(resultado.getInt("a.idActividad"));
                        actividadConsulta.add(actividad);
                    }
                    respuesta.setActividades(actividadConsulta);
                    conexionBD.close();
            }catch(SQLException e){
                e.printStackTrace();
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);                
            }
        }else{
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
            return respuesta;
    }
    
    public static int asignarActividadADesarrollador(int idActividad, int idUsuario){
       int respuesta;
       Connection conexionBD = ConexionBD.obtenerConexion();
       if(conexionBD != null){
           try{
               String sentencia = "UPDATE actividad a " +
                "SET idDesarrollador = ?, " +
                "idEstadoActividad = 2 " +
                "WHERE idActividad = ?; ";
               PreparedStatement prepararSentencia =conexionBD.prepareStatement(sentencia);
               
               prepararSentencia.setInt(1, idUsuario);
               prepararSentencia.setInt(2, idActividad);
               int filasAfectadas = prepararSentencia.executeUpdate();
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA :
                        Constantes.ERROR_CONSULTA;
                conexionBD.close();
           }catch(SQLException e){
               e.printStackTrace();
                respuesta = Constantes.ERROR_CONSULTA; 
           }
       }else{
           respuesta = Constantes.ERROR_CONEXION;
       }        
        return respuesta;
    }
    
    public static int desasignarActividadADesarrollador(int idActividad, int idUsuario){
        int respuesta;
        Connection conexionBD = ConexionBD.obtenerConexion();
        if(conexionBD != null){
            try{
                String sentencia = "UPDATE actividad " +
                    "SET idDesarrollador = NULL, idEstadoActividad = 1 " +
                    "WHERE idActividad = ? AND idDesarrollador IN ( " +
                    "SELECT d.idDesarrollador " +
                    "FROM desarrollador d " +
                    "INNER JOIN usuario u ON d.idDesarrollador = u.idUsuario " +
                    "WHERE u.idUsuario = ?);";
                PreparedStatement prepararSentencia =conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, idActividad);
                prepararSentencia.setInt(2, idUsuario);
                int filasAfectadas = prepararSentencia.executeUpdate();
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA :
                        Constantes.ERROR_CONSULTA;
                conexionBD.close();
            }catch(SQLException e){
                e.printStackTrace();
                respuesta = Constantes.ERROR_CONSULTA; 
            }
        }else{
            respuesta = Constantes.ERROR_CONEXION;
        }
         return respuesta;
    }
    
    
    public static ListaActividadRespuesta obtActAsignadasADesarrollador(int idDesarrollador){        
        ListaActividadRespuesta respuesta = new ListaActividadRespuesta();
        Connection conexionBD = ConexionBD.obtenerConexion();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if(conexionBD != null){
            try{
                String consulta = "Select nombre, DATE_FORMAT(fechaInicio, '%d/%m/%Y') AS fechaInicio, " +
                    "DATE_FORMAT(fechaFin, '%d/%m/%Y') AS fechaFin, esfuerzo, idActividad " +
                    "FROM actividad " +
                    "WHERE idDesarrollador = ? AND idEstadoActividad = 2; ";
                    PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                    prepararSentencia.setInt(1, idDesarrollador);
                    ResultSet resultado = prepararSentencia.executeQuery();
                    ArrayList<Actividad>actividadConsulta = new ArrayList();
                    while(resultado.next()){
                        Actividad actividad = new Actividad();
                        actividad.setNombre(resultado.getString("nombre"));
                        actividad.setFechaInicio(resultado.getString("fechaInicio"));
                        actividad.setFechaFin(resultado.getString("fechaFin"));
                        actividad.setEsfuerzo(resultado.getInt("esfuerzo"));
                        actividad.setIdActividad(resultado.getInt("idActividad"));
                        actividadConsulta.add(actividad);
                    }
                    respuesta.setActividades(actividadConsulta);
                    conexionBD.close();
            }catch(SQLException e){
                e.printStackTrace();
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
                
            }
        }else{
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
            return respuesta;
    }
    
    public static int finalizarActividad(int idActividad){
       int respuesta;
       Connection conexionBD = ConexionBD.obtenerConexion();
       if(conexionBD != null){
           try{
               String sentencia = "UPDATE actividad " +
               "SET idEstadoActividad = 3 " +
               "WHERE idActividad = ?; ";
               PreparedStatement prepararSentencia =conexionBD.prepareStatement(sentencia);
               prepararSentencia.setInt(1, idActividad);
               int filasAfectadas = prepararSentencia.executeUpdate();
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA :
                        Constantes.ERROR_CONSULTA;
                conexionBD.close();
           }catch(SQLException e){
               e.printStackTrace();
                respuesta = Constantes.ERROR_CONSULTA; 
           }
       }else{
           respuesta = Constantes.ERROR_CONEXION;
       }
        
        return respuesta;
    }
}
