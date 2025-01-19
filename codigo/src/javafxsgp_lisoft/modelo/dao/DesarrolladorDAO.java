/*
*Autor: Mongeote Tlachy Daniel
*Fecha de creación: 10/11/2023
*Fecha de modificación: 17/11/2023
*Descripción: Clase encargada de la comunicación con la BD para manipular la información de todos los desarrolladores.
*/
package javafxsgp_lisoft.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxsgp_lisoft.modelo.ConexionBD;
import javafxsgp_lisoft.modelo.pojo.Actividad;
import javafxsgp_lisoft.modelo.pojo.Desarrollador;
import javafxsgp_lisoft.respuesta.ListaDesarrolladorRespuesta;
import javafxsgp_lisoft.modelo.pojo.Materia;
import javafxsgp_lisoft.modelo.pojo.Proyecto;
import javafxsgp_lisoft.respuesta.ListaActividadRespuesta;
import javafxsgp_lisoft.respuesta.ListaMateriaRespuesta;
import javafxsgp_lisoft.respuesta.ListaProyectoRespuesta;
import javafxsgp_lisoft.utils.Constantes;

public class DesarrolladorDAO {
    
    public static ListaDesarrolladorRespuesta obtenerDetallesDesarrollador(){
        ListaDesarrolladorRespuesta respuesta = new ListaDesarrolladorRespuesta();
        Connection conexionBD = ConexionBD.obtenerConexion();
        if(conexionBD != null){
            try{
                String consulta = "SELECT u.idUsuario, u.nombre, u.apellidoPaterno, u.apellidoMaterno, u.correo, u.contrasenia, d.matricula, d.idProyecto, mt.idMateria, mt.nombre AS nombreMateria\n" +
                    " FROM usuario u\n" +
                    " JOIN desarrollador d ON u.idUsuario = d.idDesarrollador\n" +
                    " JOIN inscripcionexperienciaeducativa ie ON d.idDesarrollador = ie.idDesarrollador\n" +
                    " JOIN experienciaeducativa e ON ie.idExperienciaEducativa = e.idExperienciaEducativa\n" +
                    " JOIN materia mt ON e.idMateria = mt.idMateria\n" +
                    " WHERE d.idDesarrollador IS NOT NULL;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Desarrollador> desarrolladores = new ArrayList();
                while(resultado.next()){
                    Desarrollador desarrollador = new Desarrollador();
                    desarrollador.setIdUsuario(resultado.getInt("idUsuario"));
                    desarrollador.setNombre(resultado.getString("nombre"));
                    desarrollador.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    desarrollador.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    desarrollador.setCorreo(resultado.getString("correo"));
                    desarrollador.setContrasena(resultado.getString("contrasenia"));
                    desarrollador.setMatricula(resultado.getString("matricula"));
                    desarrollador.setIdProyecto(resultado.getInt("idProyecto"));
                    desarrollador.setIdMateria(resultado.getInt("idMateria"));
                    desarrollador.setNombreMateria(resultado.getString("nombreMateria"));
                    String nombreCompleto = resultado.getString("nombre") + " " +
                        resultado.getString("apellidoPaterno") + " " +
                        resultado.getString("apellidoMaterno");
                    desarrollador.setNombreCompleto(nombreCompleto);
                    desarrolladores.add(desarrollador);
                }
                respuesta.setDesarrolladores(desarrolladores);
                conexionBD.close();
                respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
            } catch(SQLException e){
                e.printStackTrace();
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        }else{
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
    
    public static ListaMateriaRespuesta obtenerMaterias(int idResponsable){
        ListaMateriaRespuesta respuesta = new ListaMateriaRespuesta();
        Connection conexionBD = ConexionBD.obtenerConexion();
        respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        if(conexionBD != null){
            try{
                String consulta = "SELECT pe.idPeriodoEscolar, MAX(pe.fechaInicio), pe.fechaFin, ee.idExperienciaEducativa, ee.NRC, ee.idResponsableProyecto, m.idMateria, m.nombre \n" +
                    "FROM periodoescolar pe\n" +
                    "INNER JOIN experienciaeducativa ee ON pe.idPeriodoEscolar = ee.idPeriodoEscolar\n" +
                    "INNER JOIN materia m ON ee.idMateria = m.idMateria\n" +
                    "GROUP BY pe.idPeriodoEscolar, pe.fechaFin, ee.idExperienciaEducativa, ee.idResponsableProyecto, m.idMateria, m.nombre;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Materia> materias = new ArrayList<>();
                while(resultado.next()){
                    Materia tipos = new Materia();
                    tipos.setIdPeriodoEscolar(resultado.getInt("idPeriodoEscolar"));
                    tipos.setFechaInicio(resultado.getString("MAX(pe.fechaInicio)"));
                    tipos.setFechaFin(resultado.getString("fechaFin"));
                    tipos.setIdExperienciaEducativa(resultado.getInt("idExperienciaEducativa"));
                    tipos.setNRC(resultado.getString("NRC"));
                    tipos.setIdResponsableProyecto(resultado.getInt("idResponsableProyecto"));
                    tipos.setIdMateria(resultado.getInt("idMateria"));
                    tipos.setNombre(resultado.getString("nombre"));
                    materias.add(tipos);
                }
                respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                respuesta.setMaterias(materias);
                conexionBD.close();
            }catch(SQLException e){
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
                e.printStackTrace();
            }
        }
        return respuesta;
    }
    
    public static ListaProyectoRespuesta obtenerProyectos(int idResponsable){
        Connection conexionBD = ConexionBD.obtenerConexion();
        ListaProyectoRespuesta respuesta = new ListaProyectoRespuesta();
        respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        if(conexionBD != null){
            try{
                String consulta = "SELECT idProyecto, nombre FROM spglisoft.proyecto;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ArrayList<Proyecto> proyectos = new ArrayList<>();
                ResultSet resultado = prepararSentencia.executeQuery();
                while(resultado.next()){
                    Proyecto tipos = new Proyecto();
                    tipos.setIdProyecto(resultado.getInt("idProyecto"));
                    tipos.setNombre(resultado.getString("nombre"));
                    proyectos.add(tipos);
                }
                respuesta.setProyectos(proyectos);
                respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                conexionBD.close();
            }catch(SQLException e){
                e.printStackTrace();
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        }
        return respuesta;
    }
    
    public static int guardarDesarrollador(Desarrollador desarrollador){
        int respuesta;
        Connection conexionBD = ConexionBD.obtenerConexion();
        if(conexionBD != null){
            try{
                String sentenciaUsuario = "INSERT INTO usuario(nombre, apellidoPaterno, apellidoMaterno, correo, contrasenia) VALUES (?, ?, ?, ?, ?);";
                PreparedStatement prepararSentenciaUsuario = conexionBD.prepareStatement(sentenciaUsuario);
                prepararSentenciaUsuario.setString(1, desarrollador.getNombre());
                prepararSentenciaUsuario.setString(2, desarrollador.getApellidoPaterno());
                prepararSentenciaUsuario.setString(3, desarrollador.getApellidoMaterno());
                prepararSentenciaUsuario.setString(4, desarrollador.getCorreo());
                prepararSentenciaUsuario.setString(5, desarrollador.getContrasena());
                int filasAfectadasUsuario = prepararSentenciaUsuario.executeUpdate();

                if (filasAfectadasUsuario == 1) {
                    String sentenciaDesarrollador = "INSERT INTO desarrollador(idDesarrollador, matricula, idProyecto) VALUES (LAST_INSERT_ID(), ?, ?);";
                    PreparedStatement prepararSentenciaDesarrollador = conexionBD.prepareStatement(sentenciaDesarrollador);
                    prepararSentenciaDesarrollador.setString(1, desarrollador.getMatricula());
                    prepararSentenciaDesarrollador.setInt(2, desarrollador.getIdProyecto());
                    int filasAfectadasDesarrollador = prepararSentenciaDesarrollador.executeUpdate();

                    String sentenciaInscripcion = "INSERT INTO inscripcionexperienciaeducativa(idExperienciaEducativa, idDesarrollador) VALUES (?, LAST_INSERT_ID());";
                    PreparedStatement prepararSentenciaInscripcion = conexionBD.prepareStatement(sentenciaInscripcion);
                    prepararSentenciaInscripcion.setInt(1, desarrollador.getIdExperienciaEducativa());
                    int filasAfectadasInscripcion = prepararSentenciaInscripcion.executeUpdate();

                    if (filasAfectadasDesarrollador == 1 && filasAfectadasInscripcion == 1) {
                        respuesta = Constantes.OPERACION_EXITOSA;
                    } else {
                        respuesta = Constantes.ERROR_CONSULTA;
                    }
                } else {
                    respuesta = Constantes.ERROR_CONSULTA;
                }

                conexionBD.close();
            } catch(SQLException e){
                e.printStackTrace();
                respuesta = Constantes.ERROR_CONSULTA;
            }
        } else {
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
    
    public static int modificarDesarrollador(Desarrollador desarrollador) {
        int respuesta;
        Connection conexionBD = ConexionBD.obtenerConexion();

        if (conexionBD != null) {
            try {
                String sentenciaUsuario = "UPDATE usuario SET nombre=?, apellidoPaterno=?, apellidoMaterno=?, correo=?, contrasenia=? WHERE idUsuario=?";
                PreparedStatement prepararSentenciaUsuario = conexionBD.prepareStatement(sentenciaUsuario);
                prepararSentenciaUsuario.setString(1, desarrollador.getNombre());
                prepararSentenciaUsuario.setString(2, desarrollador.getApellidoPaterno());
                prepararSentenciaUsuario.setString(3, desarrollador.getApellidoMaterno());
                prepararSentenciaUsuario.setString(4, desarrollador.getCorreo());
                prepararSentenciaUsuario.setString(5, desarrollador.getContrasena());
                prepararSentenciaUsuario.setInt(6, desarrollador.getIdUsuario());
                int filasAfectadasUsuario = prepararSentenciaUsuario.executeUpdate();

                String sentenciaDesarrollador = "UPDATE desarrollador SET matricula=?, idProyecto=? WHERE idDesarrollador=?";
                PreparedStatement prepararSentenciaDesarrollador = conexionBD.prepareStatement(sentenciaDesarrollador);
                prepararSentenciaDesarrollador.setString(1, desarrollador.getMatricula());
                prepararSentenciaDesarrollador.setInt(2, desarrollador.getIdProyecto());
                prepararSentenciaDesarrollador.setInt(3, desarrollador.getIdUsuario());
                int filasAfectadasDesarrollador = prepararSentenciaDesarrollador.executeUpdate();

                String sentenciaInscripcion = "UPDATE inscripcionexperienciaeducativa SET idExperienciaEducativa=? WHERE idDesarrollador=?";
                PreparedStatement prepararSentenciaInscripcion = conexionBD.prepareStatement(sentenciaInscripcion);
                prepararSentenciaInscripcion.setInt(1, desarrollador.getIdExperienciaEducativa());
                prepararSentenciaInscripcion.setInt(2, desarrollador.getIdUsuario());
                int filasAfectadasInscripcion = prepararSentenciaInscripcion.executeUpdate();

                if (filasAfectadasUsuario == 1 && filasAfectadasDesarrollador == 1 && filasAfectadasInscripcion == 1) {
                    respuesta = Constantes.OPERACION_EXITOSA;
                } else {
                    respuesta = Constantes.ERROR_CONSULTA;
                }

                conexionBD.close();
            } catch(SQLException e){
                e.printStackTrace();
                respuesta = Constantes.ERROR_CONSULTA;
            }
        } else {
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
    public static ListaDesarrolladorRespuesta obtenerDesarrolladores(){
        ListaDesarrolladorRespuesta respuesta = new ListaDesarrolladorRespuesta();
        respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if(conexionBD != null){
            try{
                String consulta = "SELECT " +
                    "u.idUsuario, u.nombre, u.apellidoPaterno, u.apellidoMaterno, u.correo " +
                    "FROM usuario u " +
                    "JOIN desarrollador d ON u.idUsuario = d.idDesarrollador " +
                    "WHERE d.idDesarrollador IS NOT NULL;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Desarrollador> desarrolladores = new ArrayList<>();
                while(resultado.next()){
                    Desarrollador deslldor = new Desarrollador();
                    deslldor.setNombre(resultado.getString("u.nombre"));
                    deslldor.setApellidoPaterno(resultado.getString("u.apellidoPaterno"));
                    deslldor.setApellidoMaterno(resultado.getString("u.apellidoMaterno"));
                    deslldor.setIdUsuario(resultado.getInt("u.idUsuario"));
                    desarrolladores.add(deslldor);                    
                }
                respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                respuesta.setDesarrolladores(desarrolladores);
                conexionBD.close();
            }catch(SQLException e){
                e.printStackTrace();
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        }
        return respuesta;
    }
    
    public static ListaActividadRespuesta obtenerActividadesPorDesarrollador(int idUsuario){
        ListaActividadRespuesta respuesta = new ListaActividadRespuesta();
        Connection conexionBD = ConexionBD.obtenerConexion();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if(conexionBD != null){
            try{
               String consulta = "SELECT a.nombre, DATE_FORMAT(a.fechaInicio,'%d/%m/%Y') AS fechaInicio, " +
                "DATE_FORMAT(a.fechaFin, '%d/%m/%Y') AS fechaFin, a.esfuerzo, ea.nombre, a.idActividad " +
                "FROM actividad a " +
                "INNER JOIN estadoActividad ea ON a.idEstadoActividad = ea.idEstadoActividad " +
                "JOIN desarrollador d ON a.idDesarrollador = d.idDesarrollador " +
                "JOIN usuario u ON d.idDesarrollador = u.idUsuario " +
                "WHERE u.idUsuario = ? && ea.idEstadoActividad = 2; ";
               PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
               prepararSentencia.setInt(1, idUsuario);
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
}
