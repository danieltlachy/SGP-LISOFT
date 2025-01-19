/*
*Autor: Mongeote Tlachy Daniel
*Fecha de creación: 10/11/2023
*Fecha de modificación: 17/11/2023
*Descripción: Clase encargada de la comunicación con la BD para manipular la información de todos los proyectos.
*/
package javafxsgp_lisoft.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxsgp_lisoft.modelo.ConexionBD;
import javafxsgp_lisoft.modelo.pojo.EstadoProyecto;
import javafxsgp_lisoft.modelo.pojo.Proyecto;
import javafxsgp_lisoft.respuesta.ListaProyectoRespuesta;
import javafxsgp_lisoft.respuesta.ListaEstadoProyectoRespuesta;
import javafxsgp_lisoft.utils.Constantes;

public class ProyectoDAO {
    
    public static ListaProyectoRespuesta obtenerProyectos(){
        ListaProyectoRespuesta respuesta = new ListaProyectoRespuesta();
        Connection conexionBD = ConexionBD.obtenerConexion();
        if(conexionBD != null){
            try{
                String consulta = "SELECT p.idProyecto, p.nombre, mt.nombre, DATE_FORMAT(p.fechaInicio,'%d/%m/%Y') AS fechaInicio\n" +
                    "FROM proyecto p\n" +
                    "JOIN responsableproyecto rp ON p.idProyecto = rp.idProyecto\n" +
                    "JOIN experienciaeducativa ee ON rp.idResponsableProyecto = ee.idResponsableProyecto\n" +
                    "JOIN materia mt ON ee.idMateria = mt.idMateria\n" +
                    "WHERE rp.idProyecto IS NOT NULL";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Proyecto> proyectos = new ArrayList();
                while(resultado.next()){
                    Proyecto proyecto = new Proyecto();
                    proyecto.setIdProyecto(resultado.getInt("idProyecto"));
                    proyecto.setNombre(resultado.getString("nombre"));
                    proyecto.setNombreMateria(resultado.getString("mt.nombre"));
                    proyecto.setFechaInicio(resultado.getString("fechaInicio"));
                    proyectos.add(proyecto);
                }
                respuesta.setProyectos(proyectos);
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
    
    public static ListaEstadoProyectoRespuesta obtenerEstadosProyecto(int idResponsable){
        ListaEstadoProyectoRespuesta respuesta = new ListaEstadoProyectoRespuesta();
        Connection conexionBD = ConexionBD.obtenerConexion();
        if(conexionBD != null){
            try{
                String consulta = "SELECT e.idEstadoProyecto, e.nombre\n" +
                    " FROM estadoproyecto e \n" +
                    " WHERE e.idEstadoProyecto IS NOT NULL;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<EstadoProyecto> estadosProyecto = new ArrayList();
                while(resultado.next()){
                    EstadoProyecto tipos = new EstadoProyecto();
                    tipos.setIdEstadoProyecto(resultado.getInt("idEstadoProyecto"));
                    tipos.setNombre(resultado.getString("nombre"));
                    estadosProyecto.add(tipos);
                }
                respuesta.setEstadosProyecto(estadosProyecto);
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
    
    public static int guardarProyecto(Proyecto proyectoNuevo) {
        int respuesta;
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {
                String sentenciaProyecto = "INSERT INTO proyecto(nombre, fechaInicio, fechaFin, descripcion, numeroDesarrolladoresMaximo, idEstadoProyecto) VALUES (?, ?, ?, ?, ?, ?);";
                PreparedStatement prepararSentenciaProyecto = conexionBD.prepareStatement(sentenciaProyecto);
                prepararSentenciaProyecto.setString(1, proyectoNuevo.getNombre());
                prepararSentenciaProyecto.setString(2, proyectoNuevo.getFechaInicio());
                prepararSentenciaProyecto.setString(3, proyectoNuevo.getFechaFin());
                prepararSentenciaProyecto.setString(4, proyectoNuevo.getDescripcion());
                prepararSentenciaProyecto.setInt(5, proyectoNuevo.getNumDesarrolladoresMax());
                prepararSentenciaProyecto.setInt(6, proyectoNuevo.getIdEstadoProyecto());
                prepararSentenciaProyecto.executeUpdate();
                respuesta = Constantes.OPERACION_EXITOSA;
                conexionBD.close();
            } catch (SQLException e) {
                e.printStackTrace();
                respuesta = Constantes.ERROR_CONSULTA;
            }
        } else {
            respuesta = Constantes.ERROR_CONEXION;
        }

        return respuesta;
    }
    
    public static int asignarProyecto(Proyecto proyectoNuevo) {
        int respuesta;
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {
                String sentenciaResponsable = "UPDATE responsableProyecto SET idProyecto=(SELECT idProyecto FROM proyecto WHERE idProyecto = (SELECT max(idProyecto) FROM proyecto)) WHERE idResponsableProyecto=?";
                PreparedStatement prepararSentenciaResponsable = conexionBD.prepareStatement(sentenciaResponsable);
                prepararSentenciaResponsable.setInt(1, proyectoNuevo.getIdResponsableProyecto());
                prepararSentenciaResponsable.executeUpdate();
                respuesta = Constantes.OPERACION_EXITOSA;
                conexionBD.close();
            } catch (SQLException e) {
                e.printStackTrace();
                respuesta = Constantes.ERROR_CONSULTA;
            }
        } else {
            respuesta = Constantes.ERROR_CONEXION;
        }

        return respuesta;
    }
    
}
