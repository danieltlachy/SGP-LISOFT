/*
*Autor: Torres Osorio Alesis de Jesus
*Fecha de creación: 10/11/2023
*Fecha de modificación: 17/11/2023
*Descripción: Clase encargada de la comunicación con la BD para manipular la información de todas las solicitudes de cambio.
*/
package javafxsgp_lisoft.modelo.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxsgp_lisoft.modelo.ConexionBD;
import javafxsgp_lisoft.modelo.pojo.SolicitudCambio;
import javafxsgp_lisoft.modelo.pojo.SolicitudCambioRespuesta;
import javafxsgp_lisoft.respuesta.ListaSolicitudCambioRespuesta;
import javafxsgp_lisoft.respuesta.RespuestaExistencia;
import javafxsgp_lisoft.utils.Constantes;

public class SolicitudCambioDAO {

    public static ListaSolicitudCambioRespuesta obtenerSolicitudesPendientes() {
        ListaSolicitudCambioRespuesta respuesta = new ListaSolicitudCambioRespuesta();
        Connection conexionBD = ConexionBD.obtenerConexion();
       if(conexionBD != null){
           try{
               String consulta = "select solicitudCambio.idSolicitudCambio, solicitudCambio.nombre, "
                       + "fechaSolicitud, fechaAprobacion, solicitudCambio.descripcion, "
                       + "razon, impacto, accionPropuesta, solicitudCambio.idProyecto, "
                       + "solicitudCambio.idDesarrollador, idDefecto, idEstadoSolicitud "
                       + "FROM solicitudCambio "
                       + "LEFT JOIN cambio "
                       + "ON solicitudCambio.idSolicitudCambio = cambio.idSolicitudCambio "
                       + "WHERE cambio.idSolicitudCambio IS NULL;";
               PreparedStatement prepararSentencia  = conexionBD.prepareStatement(consulta);
               ResultSet resultado = prepararSentencia.executeQuery();
               ArrayList<SolicitudCambio> solicitudesPendientes = new ArrayList();
               while(resultado.next()){
                   SolicitudCambio solicitud = new SolicitudCambio();
                   solicitud.setIdSolicitudCambio(resultado.getInt("idSolicitudCambio"));
                   solicitud.setNombre(resultado.getString("nombre"));
                   solicitud.setFechaSolicitud(resultado.getString("fechaSolicitud"));
                   solicitud.setFechaAprobacion(resultado.getString("fechaAprobacion"));
                   solicitud.setDescripcion(resultado.getString("descripcion"));
                   solicitud.setRazon(resultado.getString("razon"));
                   solicitud.setImpacto(resultado.getString("impacto"));
                   solicitud.setAccionPropuesta(resultado.getString("accionPropuesta"));
                   solicitud.setIdProyecto(resultado.getInt("idProyecto"));
                   solicitud.setIdDesarrollador(resultado.getInt("idDesarrollador"));
                   Integer idDefectoSolicitud = resultado.getInt("idDefecto");
                   if(idDefectoSolicitud != null){
                       solicitud.setIdDefecto(resultado.getInt("idDefecto"));
                       solicitud.setIdEstadoSolicitud(resultado.getInt("idEstadoSolicitud"));
                   }
                   
                   solicitudesPendientes.add(solicitud);
               }
               respuesta.setSolicitudesCambio(solicitudesPendientes);
               respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
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
    
    public static ListaSolicitudCambioRespuesta obtSolicitudesCambioPendientes(){
        ListaSolicitudCambioRespuesta respuesta = new ListaSolicitudCambioRespuesta();
        Connection conexionBD = ConexionBD.obtenerConexion();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if(conexionBD != null){
            try{
                String consulta = "SELECT " +
                "p.nombre AS nombreProyecto, sc.nombre AS nombre, u.nombre As nombreDesarrollador, u.apellidoPaterno, u.apellidoMaterno, " +
                "DATE_FORMAT(sc.fechaSolicitud, '%d/%m/%Y') AS fechaSolicitud, sc.idDefecto, sc.idSolicitudCambio " +
                "FROM solicitudCambio sc " +
                "JOIN proyecto p ON sc.idProyecto = p.idProyecto " +
                "JOIN usuario u ON sc.idDesarrollador = u.idUsuario " +
                "WHERE sc.idEstadoSolicitud = 1; ";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<SolicitudCambio>solicitudCambioConsulta = new ArrayList();
                while(resultado.next()){
                    SolicitudCambio solicitudCambio = new SolicitudCambio();
                    solicitudCambio.setNombreProyecto(resultado.getString("nombreProyecto"));
                    solicitudCambio.setNombre(resultado.getString("nombre"));
                    solicitudCambio.setNombreDesarrollador(resultado.getString("nombreDesarrollador"));
                    solicitudCambio.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    solicitudCambio.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    solicitudCambio.setFechaSolicitud(resultado.getString("fechaSolicitud"));
                    solicitudCambio.setIdSolicitudCambio(resultado.getInt("idSolicitudCambio"));
                    
                    solicitudCambioConsulta.add(solicitudCambio);
                }
                respuesta.setSolicitudesCambio(solicitudCambioConsulta);
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
    
    public static SolicitudCambioRespuesta obtSolicitudSeleccionada(int idSolicitudCambio){
       SolicitudCambioRespuesta respuesta  = new SolicitudCambioRespuesta();
       Connection conexionBD = ConexionBD.obtenerConexion();
       respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
       
       if(conexionBD != null){
            try{
               String consulta = "SELECT " +
                "p.nombre AS nombreProyecto, " +
                "sc.nombre AS nombre, u.nombre As nombreDesarrollador, u.apellidoPaterno, u.apellidoMaterno, " +
                "DATE_FORMAT(sc.fechaSolicitud, '%d/%m/%Y') AS fechaSolicitud, " +
                "d.nombre AS nombreDefecto, " +
                "sc.descripcion, " +
                "sc.razon, " +
                "sc.impacto, " +
                "sc.accionPropuesta " +
                "FROM solicitudCambio sc " +
                "JOIN proyecto p ON sc.idProyecto = p.idProyecto " +
                "JOIN usuario u ON sc.idDesarrollador = u.idUsuario " +
                "LEFT JOIN defecto d ON sc.idDefecto = d.idDefecto " +
                "WHERE sc.idSolicitudCambio = ?; ";
               PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
               prepararSentencia.setInt(1, idSolicitudCambio);
               ResultSet resultado = prepararSentencia.executeQuery();
               ArrayList<SolicitudCambio>solicitudCambioConsulta = new ArrayList();
               while(resultado.next()){
                   SolicitudCambio solicitudCambio = new SolicitudCambio();
                   solicitudCambio.setNombreProyecto(resultado.getString("nombreProyecto"));
                   solicitudCambio.setNombre(resultado.getString("nombre"));
                   solicitudCambio.setNombreDesarrollador(resultado.getString("nombreDesarrollador"));
                   solicitudCambio.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                   solicitudCambio.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                   solicitudCambio.setFechaSolicitud(resultado.getString("fechaSolicitud"));
                   solicitudCambio.setNombreDefecto(resultado.getString("nombreDefecto"));
                   solicitudCambio.setDescripcion(resultado.getString("descripcion"));
                   solicitudCambio.setRazon(resultado.getString("razon"));
                   solicitudCambio.setImpacto(resultado.getString("impacto"));
                   solicitudCambio.setAccionPropuesta(resultado.getString("accionPropuesta"));
                   solicitudCambioConsulta.add(solicitudCambio);
               }                
                respuesta.setSolicitudesCambio(solicitudCambioConsulta);
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
   
   public static ListaSolicitudCambioRespuesta aprobarSolicitudCambio(int idSolicitudCambio, String impacto, String accionPropuesta){
       ListaSolicitudCambioRespuesta respuesta  = new ListaSolicitudCambioRespuesta();
       Connection conexionBD = ConexionBD.obtenerConexion();
       respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
       if(conexionBD != null){
           try{
               String sentencia = "UPDATE solicitudCambio " +
                "SET idEstadoSolicitud = 2, fechaAprobacion = CURDATE(), " +
                "impacto = ?, accionPropuesta = ? " +
                "WHERE idSolicitudCambio = ?; ";
               PreparedStatement prepararSentencia =conexionBD.prepareStatement(sentencia);
               prepararSentencia.setString(1, impacto);
               prepararSentencia.setString(2, accionPropuesta);
               prepararSentencia.setInt(3, idSolicitudCambio);

               int filasAfectadas = prepararSentencia.executeUpdate();
           }catch(SQLException e){
               e.printStackTrace();
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
           }
       }else{
           respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
       }
       return respuesta;
   }
   
   public static int rechazarSolicitudCambio(int idSolicitudCambio){
       int respuesta;
       Connection conexionBD = ConexionBD.obtenerConexion();
       respuesta = Constantes.OPERACION_EXITOSA;
       if(conexionBD != null){
           try{
               String sentencia = "UPDATE solicitudCambio " +
                "SET idEstadoSolicitud = 3 " +
                "WHERE idSolicitudCambio = ?; ";
               PreparedStatement prepararSentencia =conexionBD.prepareStatement(sentencia);              
               prepararSentencia.setInt(1, idSolicitudCambio);
               int filasAfectadas = prepararSentencia.executeUpdate();
           }catch(SQLException e){
               e.printStackTrace();
                respuesta = Constantes.ERROR_CONSULTA;
           }
       }else{
          respuesta = Constantes.ERROR_CONEXION;
       }
       return respuesta;
   }
   
      
   
    public static int crearSolicitudCambio(SolicitudCambio solicitudCambioNueva, int idDefecto, int idDesarrollador) {
        
        int respuesta = Constantes.OPERACION_EXITOSA;
        try (Connection conexionBD = ConexionBD.obtenerConexion()) {
            String procedimiento = "{CALL sp_InsertarSolicitudCambio(?, ?, ?, ?, ?, ?, ?)}";
            try (CallableStatement llamadaProcedimiento = conexionBD.prepareCall(procedimiento)) {
                llamadaProcedimiento.setString(1, solicitudCambioNueva.getNombre());
                llamadaProcedimiento.setString(2, solicitudCambioNueva.getDescripcion());
                llamadaProcedimiento.setString(3, solicitudCambioNueva.getRazon());
                llamadaProcedimiento.setString(4, solicitudCambioNueva.getImpacto());
                llamadaProcedimiento.setString(5, solicitudCambioNueva.getAccionPropuesta());
                llamadaProcedimiento.setInt(6, idDesarrollador);
                llamadaProcedimiento.setInt(7, idDefecto);
                llamadaProcedimiento.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            respuesta = Constantes.ERROR_CONSULTA;
        }
        return respuesta;
    }
    
    public static int crearSolicitudCambioSinDefecto(SolicitudCambio solicitudCambioNueva, int idDesarrollador) {
        int respuesta = Constantes.ERROR_CONEXION;
        try (Connection conexionBD = ConexionBD.obtenerConexion()) {
            String procedimiento = "{CALL sp_InsertarSolicitudCambioSinDefecto(?, ?, ?, ?, ?, ?)}";
            try (CallableStatement llamadaProcedimiento = conexionBD.prepareCall(procedimiento)) {
                llamadaProcedimiento.setString(1, solicitudCambioNueva.getNombre());
                llamadaProcedimiento.setString(2, solicitudCambioNueva.getDescripcion());
                llamadaProcedimiento.setString(3, solicitudCambioNueva.getRazon());
                llamadaProcedimiento.setString(4, solicitudCambioNueva.getImpacto());
                llamadaProcedimiento.setString(5, solicitudCambioNueva.getAccionPropuesta());
                llamadaProcedimiento.setInt(6, idDesarrollador);
                llamadaProcedimiento.execute();
                respuesta = Constantes.OPERACION_EXITOSA;
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
            respuesta = Constantes.ERROR_CONSULTA;
        }
        return respuesta;
    }
    
   public static RespuestaExistencia existeSolicitudConNombre(String nombreSolicitud) {
       RespuestaExistencia respuesta = new RespuestaExistencia();
       respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        try (Connection conexionBD = ConexionBD.obtenerConexion();
            PreparedStatement prepararSentencia = conexionBD.prepareStatement("SELECT EXISTS (SELECT 1 FROM solicitudCambio WHERE nombre = ?) AS existeSolicitud;")) {
            prepararSentencia.setString(1, nombreSolicitud);
            ResultSet resultado = prepararSentencia.executeQuery();
            if(resultado.next()) {
                respuesta.setExistencia(
                    resultado.getBoolean("existeSolicitud")
                );
             }else{
                respuesta.setExistencia(false);
            }
            respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        } catch (SQLException e) {
            e.printStackTrace();
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
        }
        return respuesta;
    }
}
