/*
*Autor: Torres Osorio Alesis de Jesus
*Fecha de creación: 10/11/2023
*Fecha de modificación: 17/11/2023
*Descripción: Clase encargada de la comunicación con la BD para manipular la información de todos los cambios.
*/
package javafxsgp_lisoft.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxsgp_lisoft.modelo.ConexionBD;
import javafxsgp_lisoft.modelo.pojo.Cambio;
import javafxsgp_lisoft.respuesta.ListaCambioRespuesta;
import javafxsgp_lisoft.utils.Constantes;

public class CambioDAO {
    public static int registrarCambio(Cambio cambioRegistro) {
        int respuesta = Constantes.OPERACION_EXITOSA;
        Connection conexionBD = ConexionBD.obtenerConexion();
        
        if(conexionBD != null){
            try{
                String consulta1 ="SET FOREIGN_KEY_CHECKS=0;";
                PreparedStatement prepararSentencia1 = conexionBD.prepareStatement(consulta1);
                prepararSentencia1.executeUpdate();
                 
                String consulta = 
                        "INSERT INTO cambio (nombre, fechaCreacion, descripcion, esfuerzo, idProyecto, idDesarrollador,idSolicitudCambio,idEstadoCambio,idTipoDefecto)"
                        + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setString(1,cambioRegistro.getNombre() );
                prepararSentencia.setString(2,cambioRegistro.getFechaCreacion() );
                prepararSentencia.setString(3,cambioRegistro.getDescripcion() );
                prepararSentencia.setInt(4,cambioRegistro.getEsfuerzo() );
                prepararSentencia.setInt(5,cambioRegistro.getIdProyecto() );
                prepararSentencia.setInt(6,cambioRegistro.getIdDesarrollador());
                Integer solicitudCambio = Integer.valueOf(cambioRegistro.getIdSolicitudCambio());
                if(solicitudCambio != null ){
                    prepararSentencia.setInt(7,cambioRegistro.getIdSolicitudCambio() );
                }
                prepararSentencia.setInt(8,cambioRegistro.getIdEstadoCambio() );
                prepararSentencia.setInt(9,cambioRegistro.getIdTipoCambio() );
                
                int filasAfectadas = prepararSentencia.executeUpdate();
                
                if(solicitudCambio != 0 && solicitudCambio != null){
                    cambiarEstadoSolicitud(solicitudCambio);
                    Integer idDefecto = Integer.valueOf(cambioRegistro.getIdDefecto());
                    if(idDefecto != 0 && idDefecto != null){
                        cambiarEstadoDefecto(idDefecto);
                    }
                }
                
                if(filasAfectadas != 1){
                    respuesta = Constantes.ERROR_CONSULTA;
                }
                String consulta2 ="SET FOREIGN_KEY_CHECKS=1;";
                PreparedStatement prepararSentencia2 = conexionBD.prepareStatement(consulta2);
                prepararSentencia2.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                respuesta = Constantes.ERROR_CONSULTA;
            }

        }
        return respuesta;
    }

    public static ListaCambioRespuesta obtenerListaCambiosRealizados(int idUsuario) {
        
        ListaCambioRespuesta respuesta = new ListaCambioRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idCambio, cambio.nombre, fechaCreacion, "
                        + "descripcion, esfuerzo, idProyecto, idDesarrollador, "
                        + "idSolicitudCambio, cambio.idTipoDefecto, "
                        + "tipoDefecto.nombre, cambio.idEstadoCambio, "
                        + "estadoCambio.nombre FROM cambio "
                        + "INNER JOIN tipoDefecto "
                        + "ON cambio.idTipoDefecto = tipoDefecto.idTipoDefecto  "
                        + "INNER JOIN estadoCambio "
                        + "ON cambio.idEstadoCambio = estadoCambio.idEstadoCambio "
                        + "WHERE idDesarrollador=?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idUsuario);
                ResultSet resultado = prepararSentencia.executeQuery();
                
                ArrayList<Cambio> cambios = new ArrayList<>();
                while (resultado.next()) {
                    Cambio cambio = new Cambio();
                    cambio.setIdCambio(resultado.getInt("idCambio"));
                    cambio.setNombre(resultado.getString("cambio.nombre"));
                    cambio.setFechaCreacion(resultado.getString("fechaCreacion"));
                    cambio.setDescripcion(resultado.getString("descripcion"));
                    cambio.setEsfuerzo(resultado.getInt("esfuerzo"));
                    cambio.setTipo(resultado.getString("tipoDefecto.nombre"));
                    cambio.setIdProyecto(resultado.getInt("idProyecto"));
                    cambio.setIdDesarrollador(resultado.getInt("idDesarrollador"));
                    Integer idSolicitud = resultado.getInt("idSolicitudCambio");
                    if(idSolicitud!=null){
                        cambio.setIdSolicitudCambio(idSolicitud);
                    }
                    cambio.setIdTipoCambio(resultado.getInt("cambio.idTipoDefecto"));
                    cambio.setIdEstadoCambio(resultado.getInt("idEstadoCambio"));
                    cambio.setEstado(resultado.getString("estadoCambio.nombre"));
                    cambios.add(cambio); 
                }
                
                respuesta.setListaCambio(cambios);
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

    private static int cambiarEstadoSolicitud(Integer solicitudCambio) {
        int respuesta = Constantes.OPERACION_EXITOSA;
        Connection conexionBD = ConexionBD.obtenerConexion();
        
        if(conexionBD != null){
            try{
                String consulta = 
                        "UPDATE  solicitudCambio SET idEstadoCambio = ? "
                        +  "WHERE idSolicitudCambio = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1,Constantes.ESTADO_FINALIZADO);
                prepararSentencia.setInt(2,solicitudCambio);
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

    private static int cambiarEstadoDefecto(Integer idDefecto) {
        int respuesta = Constantes.OPERACION_EXITOSA;
        Connection conexionBD = ConexionBD.obtenerConexion();
        
        if(conexionBD != null){
            try{
                String consulta = 
                        "UPDATE  defecto SET idEstadoDefecto = ? "
                        +  "WHERE idDefecto = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1,Constantes.ESTADO_FINALIZADO);
                prepararSentencia.setInt(2,idDefecto);
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
}