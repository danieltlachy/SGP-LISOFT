/*
*Autor: Torres Osorio Alesis de Jesus
*Fecha de creación: 20/11/2023
*Fecha de modificación: 20/11/2023
*Descripción: Clase encargada de la comunicación con la BD, especificamente para manipular la información de los usuarios
*/
package javafxsgp_lisoft.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafxsgp_lisoft.modelo.ConexionBD;
import javafxsgp_lisoft.modelo.pojo.Desarrollador;
import javafxsgp_lisoft.modelo.pojo.ResponsableProyecto;
import javafxsgp_lisoft.respuesta.RespuestaDesarrollador;
import javafxsgp_lisoft.respuesta.RespuestaLogin;
import javafxsgp_lisoft.respuesta.RespuestaResponsable;

public class UsuarioDAO {
    public static RespuestaDesarrollador IniciarSesionDesarrollador(String matricula, String password){
    RespuestaDesarrollador respuestaLogin = new RespuestaDesarrollador();
    respuestaLogin.setError(true);
    Connection conexionBD = ConexionBD.obtenerConexion();
    if(conexionBD != null){
        try{
            String consulta = "SELECT idUsuario, nombre, apellidoPaterno,  "
                    + "apellidoMaterno, correo, matricula, idProyecto  "
                    + "FROM usuario  "
                    + "INNER JOIN desarrollador "
                    + "ON usuario.idUsuario = desarrollador.idDesarrollador "
                    + "WHERE desarrollador.matricula = ? "
                    + "AND usuario.contrasenia = ?";
            PreparedStatement sentenciaPreparada 
                = conexionBD.prepareStatement(consulta);
            sentenciaPreparada.setString(1, matricula);
            sentenciaPreparada.setString(2, password);
            ResultSet resultado = sentenciaPreparada.executeQuery();
            if(resultado.next()) {
                respuestaLogin.setError(false);
                respuestaLogin.setMensaje("CredencialesCorrectas");
                Desarrollador desarrollador = new Desarrollador();
                desarrollador.setIdUsuario(resultado.getInt("idUsuario"));
                desarrollador.setNombre(resultado.getString("nombre"));
                desarrollador.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                desarrollador.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                desarrollador.setCorreo(resultado.getString("correo"));
                desarrollador.setIdProyecto(resultado.getInt("idProyecto"));
                desarrollador.setMatricula(resultado.getString("matricula"));

                respuestaLogin.setDesarrollador(desarrollador);
            }else{
                respuestaLogin.setMensaje("Numero de matricula y/o contrasena"
                        + " incorrectas, por favor verifique las credenciales");
            }
            conexionBD.close();
        } catch (SQLException e) {
            e.printStackTrace();
            respuestaLogin.setMensaje("Error en la recuperacion de informacion, por favor, intentelo mas tarde.");
        }
    }else{
        respuestaLogin.setMensaje("Por el momento no hay conexion, por favor intentelo mas tarde");
    }
    return respuestaLogin;
    }
    
    public static RespuestaResponsable IniciarSesionResponsable(String nPersonal, String password){
    RespuestaResponsable respuestaLogin = new RespuestaResponsable();
    respuestaLogin.setError(true);
    Connection conexionBD = ConexionBD.obtenerConexion();
    if(conexionBD != null){
        try{
            String consulta = "SELECT idUsuario, nombre, apellidoPaterno,  "
                    + "apellidoMaterno, correo, numeroPersonal, "
                    + "idProyecto  FROM usuario  "
                    + "INNER JOIN responsableProyecto "
                    + "ON usuario.idUsuario = responsableproyecto.idResponsableProyecto "
                    + "WHERE responsableproyecto.numeroPersonal = ? "
                    + "AND usuario.contrasenia = ?";
            PreparedStatement sentenciaPreparada 
                = conexionBD.prepareStatement(consulta);
            sentenciaPreparada.setString(1, nPersonal);
            sentenciaPreparada.setString(2, password);
            ResultSet resultado = sentenciaPreparada.executeQuery();
            if(resultado.next()) {
                respuestaLogin.setError(false);
                respuestaLogin.setMensaje("CredencialesCorrectas");
                ResponsableProyecto responsable = new ResponsableProyecto();
                responsable.setIdUsuario(resultado.getInt("idUsuario"));
                responsable.setNombre(resultado.getString("nombre"));
                responsable.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                responsable.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                responsable.setCorreo(resultado.getString("correo"));
                responsable.setIdProyecto(resultado.getInt("idProyecto"));
                responsable.setNumeroPersonal(resultado.getString("numeroPersonal"));

                respuestaLogin.setResponsable(responsable);
            }else{
                respuestaLogin.setMensaje("Numero de numero de personal y/o contrasena"
                        + " incorrectas, por favor verifique las credenciales");
            }
            conexionBD.close();
        } catch (SQLException e) {
            e.printStackTrace();
            respuestaLogin.setMensaje("Error en la recuperacion de informacion, por favor, intentelo mas tarde.");
        }
    }else{
        respuestaLogin.setMensaje("Por el momento no hay conexion, por favor intentelo mas tarde");
    }
    return respuestaLogin;
    }
}
