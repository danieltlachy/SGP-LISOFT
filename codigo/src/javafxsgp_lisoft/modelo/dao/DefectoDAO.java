/*
*Autor: Torres Osorio Alesis de Jesus
*Fecha de creación: 10/11/2023
*Fecha de modificación: 17/11/2023
*Descripción: Clase encargada de la comunicación con la BD para manipular la información de todos los defectos.
*/
package javafxsgp_lisoft.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxsgp_lisoft.modelo.ConexionBD;
import javafxsgp_lisoft.modelo.pojo.Defecto;
import javafxsgp_lisoft.respuesta.ListaDefectoRespuesta;
import javafxsgp_lisoft.respuesta.RespuestaDefecto;
import javafxsgp_lisoft.utils.Constantes;

public class DefectoDAO {

    public static ListaDefectoRespuesta obtenerListaDefecto(int idProyecto) {
        
        ListaDefectoRespuesta respuesta = new ListaDefectoRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idDefecto, defecto.nombre, fechaDeteccion, "
                        + "descripcion, esfuerzo, idProyecto, defecto.idDesarrollador, "
                        + "usuario.nombre, usuario.apellidoPaterno, usuario.apellidoMaterno, "
                        + "defecto.idEstadoDefecto, estadoDefecto.nombre, defecto.idTipoDefecto, "
                        + "tipoDefecto.nombre "
                        + "FROM defecto "
                        + "INNER JOIN tipoDefecto "
                        + "ON defecto.idTipoDefecto = tipoDefecto.idTipoDefecto "
                        + "INNER JOIN usuario "
                        + "ON defecto.idDesarrollador = usuario.idUsuario "
                        + "INNER JOIN estadoDefecto "
                        + "ON estadoDefecto.idEstadoDefecto = defecto.idEstadoDefecto "
                        + "WHERE idProyecto = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idProyecto);
                ResultSet resultado = prepararSentencia.executeQuery();
                
                ArrayList<Defecto> defectosProyecto = new ArrayList();
                while (resultado.next()) {
                    Defecto defecto = new Defecto();
                    defecto.setIdDefecto(resultado.getInt("idDefecto"));
                    defecto.setNombre(resultado.getString("defecto.nombre"));
                    defecto.setFechaDeteccion(resultado.getString("fechaDeteccion"));
                    defecto.setTipo(resultado.getString("tipoDefecto.nombre"));
                    defecto.setDescripcion(resultado.getString("descripcion"));
                    defecto.setEsfuerzo(resultado.getInt("esfuerzo"));
                    defecto.setEstado(resultado.getString("estadoDefecto.nombre"));
                    defecto.setIdProyecto(resultado.getInt("idProyecto"));
                    defecto.setIdDesarrollador(resultado.getInt("idDesarrollador"));
                    defecto.setIdEstadoDefecto(resultado.getInt("idEstadoDefecto"));
                    defecto.setIdTipoDefecto(resultado.getInt("idTipoDefecto"));
                    defecto.setNombreDesarrollador(
                            resultado.getString("usuario.nombre")+" "+
                            resultado.getString("usuario.apellidoPaterno")+" "+
                            resultado.getString("usuario.apellidoMaterno")
                            );                    
                    defectosProyecto.add(defecto); 
                }
                
                respuesta.setDefectos(defectosProyecto);
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
    
    
    public static int registrarDefecto(Defecto registroDefecto){
        int respuesta = Constantes.OPERACION_EXITOSA;
        Connection conexionBD = ConexionBD.obtenerConexion();
        
        if(conexionBD != null){
            try{
                String consulta = 
                        "INSERT INTO defecto ( nombre, fechaDeteccion, descripcion, esfuerzo, idProyecto, idDesarrollador, idEstadoDefecto, idTipoDefecto)"
                        + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)"; 
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setString(1,registroDefecto.getNombre() );
                prepararSentencia.setString(2,registroDefecto.getFechaDeteccion() );
                prepararSentencia.setString(3,registroDefecto.getDescripcion() );
                prepararSentencia.setInt(4,registroDefecto.getEsfuerzo() );
                prepararSentencia.setInt(5,registroDefecto.getIdProyecto() );
                prepararSentencia.setInt(6,registroDefecto.getIdDesarrollador());
                prepararSentencia.setInt(7,registroDefecto.getIdEstadoDefecto() );
                prepararSentencia.setInt(8,registroDefecto.getIdTipoDefecto() );

                
                int filasAfectadas = prepararSentencia.executeUpdate();
                if(filasAfectadas != 1){
                    respuesta = Constantes.ERROR_CONSULTA;
                }  
            } catch (SQLException e) {
                e.printStackTrace();
                respuesta = Constantes.ERROR_CONSULTA;
            }

        }
        return respuesta;
    }

    public static RespuestaDefecto obtenerDetallesDefecto(int idDefecto) {
       RespuestaDefecto respuesta = new RespuestaDefecto();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        
        Connection conexionDB = ConexionBD.obtenerConexion();
        if(conexionDB != null) {
            try {
                String consulta = "SELECT defecto.nombre, fechaDeteccion, "
                        + "tipoDefecto.nombre, descripcion, esfuerzo, idProyecto, "
                        + "idDesarrollador, idEstadoDefecto, defecto.idTipoDefecto "
                        + "FROM defecto "
                        + "INNER JOIN tipoDefecto  "
                        + "ON defecto.idTipoDefecto = tipoDefecto.idTipoDefecto "
                        + "WHERE idDefecto = ?";
                
                PreparedStatement sentenciaPreparada 
                    = conexionDB.prepareStatement(consulta);
                sentenciaPreparada.setInt(1, idDefecto);
                
                ResultSet resultado = sentenciaPreparada.executeQuery();
                if(resultado.next()) {
                    Defecto defecto = new Defecto();
                    defecto.setIdDefecto(idDefecto);
                    defecto.setNombre(resultado.getString("defecto.nombre"));
                    defecto.setFechaDeteccion(resultado.getString("fechaDeteccion"));
                    defecto.setTipo(resultado.getString("tipoDefecto.nombre"));
                    defecto.setDescripcion(resultado.getString("descripcion"));
                    defecto.setEsfuerzo(resultado.getInt("esfuerzo"));
                    defecto.setIdProyecto(resultado.getInt("idProyecto"));
                    defecto.setIdDesarrollador(resultado.getInt("idDesarrollador"));
                    defecto.setIdEstadoDefecto(resultado.getInt("idEstadoDefecto"));
                    defecto.setIdTipoDefecto(resultado.getInt("defecto.idTipoDefecto"));
                    respuesta.setDefecto(defecto);
                }
                conexionDB.close();
            } catch (SQLException e) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        
        return respuesta;
    }
    public static ListaDefectoRespuesta obtenerDefetosSolicitud(){
        ListaDefectoRespuesta respuesta = new ListaDefectoRespuesta();
        Connection conexionBD = ConexionBD.obtenerConexion();
        if(conexionBD != null){
            try{
                String consulta = "SELECT idDefecto, nombre " +
                "FROM defecto;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Defecto> defectos =  new ArrayList<>();
                while(resultado.next()){
                    Defecto defecto =  new Defecto();
                    defecto.setIdDefecto(resultado.getInt("idDefecto"));
                    defecto.setNombre(resultado.getString("nombre"));
                    defectos.add(defecto);
                }
                respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                respuesta.setDefectos(defectos);
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

    public static ListaDefectoRespuesta obtenerListaDefectoDesarrollador(int idUsuario) {
        ListaDefectoRespuesta respuesta = new ListaDefectoRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idDefecto, defecto.nombre, fechaDeteccion, "
                        + "descripcion, esfuerzo, idProyecto, defecto.idDesarrollador, "
                        + "usuario.nombre, usuario.apellidoPaterno, usuario.apellidoMaterno, "
                        + "defecto.idEstadoDefecto, estadoDefecto.nombre, defecto.idTipoDefecto, "
                        + "tipoDefecto.nombre "
                        + "FROM defecto "
                        + "INNER JOIN tipoDefecto "
                        + "ON defecto.idTipoDefecto = tipoDefecto.idTipoDefecto "
                        + "INNER JOIN usuario "
                        + "ON defecto.idDesarrollador = usuario.idUsuario "
                        + "INNER JOIN estadoDefecto "
                        + "ON estadoDefecto.idEstadoDefecto = defecto.idEstadoDefecto "
                        + "WHERE idDesarrollador = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idUsuario);
                ResultSet resultado = prepararSentencia.executeQuery();
                
                ArrayList<Defecto> defectosProyecto = new ArrayList();
                while (resultado.next()) {
                    Defecto defecto = new Defecto();
                    defecto.setIdDefecto(resultado.getInt("idDefecto"));
                    defecto.setNombre(resultado.getString("defecto.nombre"));
                    defecto.setFechaDeteccion(resultado.getString("fechaDeteccion"));
                    defecto.setTipo(resultado.getString("tipoDefecto.nombre"));
                    defecto.setDescripcion(resultado.getString("descripcion"));
                    defecto.setEsfuerzo(resultado.getInt("esfuerzo"));
                    defecto.setEstado(resultado.getString("estadoDefecto.nombre"));
                    defecto.setIdProyecto(resultado.getInt("idProyecto"));
                    defecto.setIdDesarrollador(resultado.getInt("idDesarrollador"));
                    defecto.setIdEstadoDefecto(resultado.getInt("idEstadoDefecto"));
                    defecto.setIdTipoDefecto(resultado.getInt("idTipoDefecto"));
                    defecto.setNombreDesarrollador(
                            resultado.getString("usuario.nombre")+" "+
                            resultado.getString("usuario.apellidoPaterno")+" "+
                            resultado.getString("usuario.apellidoMaterno")
                            );                    
                    defectosProyecto.add(defecto); 
                }
                
                respuesta.setDefectos(defectosProyecto);
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
}
    
