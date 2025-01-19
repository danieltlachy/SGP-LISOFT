package javafxsgp_lisoft.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxsgp_lisoft.modelo.ConexionBD;
import javafxsgp_lisoft.respuesta.Bitacora;
import javafxsgp_lisoft.modelo.pojo.Desarrollador;
import javafxsgp_lisoft.modelo.pojo.TipoCambio;
import javafxsgp_lisoft.respuesta.ListaBitacoraRespuesta;
import javafxsgp_lisoft.respuesta.ListaTipoCambioRespuesta;
import javafxsgp_lisoft.utils.Constantes;

public class CatalogoDAO {

    public static ListaTipoCambioRespuesta obtenerTiposCambio() {
        ListaTipoCambioRespuesta respuesta = new ListaTipoCambioRespuesta();
        Connection conexionBD = ConexionBD.obtenerConexion();
        ArrayList<TipoCambio> tiposCambio = new ArrayList();
        if(conexionBD != null){
           try{
               String consulta = "select idTipoDefecto, nombre from tipoDefecto";
               PreparedStatement prepararSentencia  = conexionBD.prepareStatement(consulta);
               ResultSet resultado = prepararSentencia.executeQuery();
               while(resultado.next()){
                   TipoCambio tipoCambio = new TipoCambio();
                   tipoCambio.setIdTipoCambio(resultado.getInt("idTipoDefecto"));
                   tipoCambio.setNombre(resultado.getString("nombre"));
                   tiposCambio.add(tipoCambio);
               }
               respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
               respuesta.setTiposCambio(tiposCambio);
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


    public static ListaBitacoraRespuesta obtenerBitacorasCambioProyecto(int idProyecto) {
        ListaBitacoraRespuesta respuesta = new ListaBitacoraRespuesta();
        Connection conexionBD = ConexionBD.obtenerConexion();
        if(conexionBD != null){
           try{
               String consulta = "SELECT us.idUsuario, us.nombre, us.apellidoPaterno, "
                       + "us.apellidoMaterno, us.correo, des.matricula, des.idProyecto, "
                       + "MAX(cambio.fechaCreacion) "
                       + "AS ultimoCambio "
                       + "FROM usuario us "
                       + "INNER JOIN desarrollador des "
                       + "ON us.idUsuario = des.idDesarrollador "
                       + "INNER JOIN cambio "
                       + "ON des.idDesarrollador = cambio.idDesarrollador "
                       + "WHERE des.idProyecto = ? "
                       + "AND cambio.fechaCreacion >= DATE_SUB(NOW(), INTERVAL 1 YEAR) "
                       + "GROUP BY us.idUsuario, us.nombre, us.apellidoPaterno, "
                       + "us.apellidoMaterno, us.correo, des.matricula, des.idProyecto";
               PreparedStatement prepararSentencia  = conexionBD.prepareStatement(consulta);
               prepararSentencia.setInt(1, idProyecto);
               ResultSet resultado = prepararSentencia.executeQuery();
               ArrayList<Bitacora> bitacoras = new ArrayList();
               while(resultado.next()){
                   Bitacora bitacora = new Bitacora();
                   Desarrollador desarrolladorBitacora = new Desarrollador();
                   desarrolladorBitacora.setIdUsuario(resultado.getInt("idUsuario"));
                   desarrolladorBitacora.setNombre(resultado.getString("us.nombre"));
                   desarrolladorBitacora.setApellidoPaterno(resultado.getString("us.apellidoPaterno"));
                   desarrolladorBitacora.setApellidoMaterno(resultado.getString("us.apellidoMaterno"));
                   desarrolladorBitacora.setCorreo(resultado.getString("us.correo"));
                   desarrolladorBitacora.setMatricula(resultado.getString("des.matricula"));
                   desarrolladorBitacora.setIdProyecto(resultado.getInt("des.idProyecto"));
                   bitacora.setDesarrollador(desarrolladorBitacora);
                   bitacora.setTipo("Cambios");
                   bitacora.setUltimaModificacion(resultado.getString("ultimoCambio"));
                   bitacora.setNombreDesarrollador(
                           desarrolladorBitacora.getNombre() + 
                                   " " + desarrolladorBitacora.getApellidoPaterno()+ 
                                   " " + desarrolladorBitacora.getApellidoMaterno());
                   bitacoras.add(bitacora);
               }
               respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
               respuesta.setBitacoras(bitacoras);
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

    public static ListaBitacoraRespuesta obtenerBitacorasCambioDesarrollador(int idUsuario) {
        ListaBitacoraRespuesta respuesta = new ListaBitacoraRespuesta();
        Connection conexionBD = ConexionBD.obtenerConexion();
        if(conexionBD != null){
           try{
               String consulta = "SELECT us.idUsuario, us.nombre, us.apellidoPaterno, "
                       + "us.apellidoMaterno, us.correo, des.matricula, des.idProyecto, "
                       + "MAX(cambio.fechaCreacion) "
                       + "AS ultimoCambio "
                       + "FROM usuario us "
                       + "INNER JOIN desarrollador des "
                       + "ON us.idUsuario = des.idDesarrollador "
                       + "INNER JOIN cambio "
                       + "ON des.idDesarrollador = cambio.idDesarrollador "
                       + "WHERE idUsuario = ? "
                       + "AND cambio.fechaCreacion >= DATE_SUB(NOW(), INTERVAL 1 YEAR) "
                       + "GROUP BY us.idUsuario, us.nombre, us.apellidoPaterno, "
                       + "us.apellidoMaterno, us.correo, des.matricula, des.idProyecto";
               PreparedStatement prepararSentencia  = conexionBD.prepareStatement(consulta);
               prepararSentencia.setInt(1, idUsuario);
               ResultSet resultado = prepararSentencia.executeQuery();
               ArrayList<Bitacora> bitacoras = new ArrayList();
               while(resultado.next()){
                   Bitacora bitacora = new Bitacora();
                   Desarrollador desarrolladorBitacora = new Desarrollador();
                   desarrolladorBitacora.setIdUsuario(resultado.getInt("idUsuario"));
                   desarrolladorBitacora.setNombre(resultado.getString("us.nombre"));
                   desarrolladorBitacora.setApellidoPaterno(resultado.getString("us.apellidoPaterno"));
                   desarrolladorBitacora.setApellidoMaterno(resultado.getString("us.apellidoMaterno"));
                   desarrolladorBitacora.setCorreo(resultado.getString("us.correo"));
                   desarrolladorBitacora.setMatricula(resultado.getString("des.matricula"));
                   desarrolladorBitacora.setIdProyecto(resultado.getInt("des.idProyecto"));
                   bitacora.setDesarrollador(desarrolladorBitacora);
                   bitacora.setTipo("Cambios");
                   bitacora.setUltimaModificacion(resultado.getString("ultimoCambio"));
                   bitacora.setNombreDesarrollador(
                           desarrolladorBitacora.getNombre() + 
                                   " " + desarrolladorBitacora.getApellidoPaterno()+ 
                                   " " + desarrolladorBitacora.getApellidoMaterno());
                   bitacoras.add(bitacora);
               }
               respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
               respuesta.setBitacoras(bitacoras);
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

    public static ListaBitacoraRespuesta obtenerBitacorasActividadProyecto(int idProyecto) {
        ListaBitacoraRespuesta respuesta = new ListaBitacoraRespuesta();
        Connection conexionBD = ConexionBD.obtenerConexion();
        if(conexionBD != null){
           try{
               String consulta = "SELECT us.idUsuario, us.nombre, us.apellidoPaterno, "
                       + "us.apellidoMaterno, us.correo, des.matricula, des.idProyecto, "
                       + "MAX(actividad.fechaFin) "
                       + "AS ultimoCambio "
                       + "FROM usuario us "
                       + "INNER JOIN desarrollador des "
                       + "ON us.idUsuario = des.idDesarrollador "
                       + "INNER JOIN actividad "
                       + "ON des.idDesarrollador = actividad.idDesarrollador "
                       + "WHERE des.idProyecto = ? "
                       + "AND actividad.fechaFin >= DATE_SUB(NOW(), INTERVAL 1 YEAR) "
                       + "GROUP BY us.idUsuario, us.nombre, us.apellidoPaterno, "
                       + "us.apellidoMaterno, us.correo, des.matricula, des.idProyecto";
               PreparedStatement prepararSentencia  = conexionBD.prepareStatement(consulta);
               prepararSentencia.setInt(1, idProyecto);
               ResultSet resultado = prepararSentencia.executeQuery();
               ArrayList<Bitacora> bitacoras = new ArrayList();
               while(resultado.next()){
                   Bitacora bitacora = new Bitacora();
                   Desarrollador desarrolladorBitacora = new Desarrollador();
                   desarrolladorBitacora.setIdUsuario(resultado.getInt("idUsuario"));
                   desarrolladorBitacora.setNombre(resultado.getString("us.nombre"));
                   desarrolladorBitacora.setApellidoPaterno(resultado.getString("us.apellidoPaterno"));
                   desarrolladorBitacora.setApellidoMaterno(resultado.getString("us.apellidoMaterno"));
                   desarrolladorBitacora.setCorreo(resultado.getString("us.correo"));
                   desarrolladorBitacora.setMatricula(resultado.getString("des.matricula"));
                   desarrolladorBitacora.setIdProyecto(resultado.getInt("des.idProyecto"));
                   bitacora.setDesarrollador(desarrolladorBitacora);
                   bitacora.setTipo("Actividad");
                   bitacora.setUltimaModificacion(resultado.getString("ultimoCambio"));
                   bitacora.setNombreDesarrollador(
                           desarrolladorBitacora.getNombre() + 
                                   " " + desarrolladorBitacora.getApellidoPaterno()+ 
                                   " " + desarrolladorBitacora.getApellidoMaterno());
                   bitacoras.add(bitacora);
               }
               respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
               respuesta.setBitacoras(bitacoras);
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

    public static ListaBitacoraRespuesta obtenerBitacorasActividadDesarrollador(int idUsuario) {
        ListaBitacoraRespuesta respuesta = new ListaBitacoraRespuesta();
        Connection conexionBD = ConexionBD.obtenerConexion();
        if(conexionBD != null){
           try{
               String consulta = "SELECT us.idUsuario, us.nombre, us.apellidoPaterno, "
                       + "us.apellidoMaterno, us.correo, des.matricula, des.idProyecto, "
                       + "MAX(actividad.fechaFin) "
                       + "AS ultimoCambio "
                       + "FROM usuario us "
                       + "INNER JOIN desarrollador des  "
                       + "ON us.idUsuario = des.idDesarrollador "
                       + "INNER JOIN actividad "
                       + "ON des.idDesarrollador = actividad.idDesarrollador "
                       + "WHERE idUsuario = ? "
                       + "AND actividad.fechaFin >= DATE_SUB(NOW(), INTERVAL 1 YEAR)  "
                       + "GROUP BY us.idUsuario, us.nombre, us.apellidoPaterno,  "
                       + "us.apellidoMaterno, us.correo, des.matricula, des.idProyecto";
               PreparedStatement prepararSentencia  = conexionBD.prepareStatement(consulta);
               prepararSentencia.setInt(1, idUsuario);
               ResultSet resultado = prepararSentencia.executeQuery();
               ArrayList<Bitacora> bitacoras = new ArrayList();
               while(resultado.next()){
                   Bitacora bitacora = new Bitacora();
                   Desarrollador desarrolladorBitacora = new Desarrollador();
                   desarrolladorBitacora.setIdUsuario(resultado.getInt("idUsuario"));
                   desarrolladorBitacora.setNombre(resultado.getString("us.nombre"));
                   desarrolladorBitacora.setApellidoPaterno(resultado.getString("us.apellidoPaterno"));
                   desarrolladorBitacora.setApellidoMaterno(resultado.getString("us.apellidoMaterno"));
                   desarrolladorBitacora.setCorreo(resultado.getString("us.correo"));
                   desarrolladorBitacora.setMatricula(resultado.getString("des.matricula"));
                   desarrolladorBitacora.setIdProyecto(resultado.getInt("des.idProyecto"));
                   bitacora.setDesarrollador(desarrolladorBitacora);
                   bitacora.setTipo("Actividades");
                   bitacora.setUltimaModificacion(resultado.getString("ultimoCambio"));
                   bitacora.setNombreDesarrollador(
                           desarrolladorBitacora.getNombre() + 
                                   " " + desarrolladorBitacora.getApellidoPaterno()+ 
                                   " " + desarrolladorBitacora.getApellidoMaterno());
                   bitacoras.add(bitacora);
               }
               respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
               respuesta.setBitacoras(bitacoras);
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
}
