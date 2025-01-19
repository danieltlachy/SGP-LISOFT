/*
*Autor: Martinez Caixba Miguel Angel
*Fecha de creación: 28/11/2023
*Fecha de modificación: 14/12/2023
*Descripción: Controlador para hacer la asignacion de la activivadad
*al desarrolador
*/

package javafxsgp_lisoft.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafxsgp_lisoft.modelo.dao.ActividadDAO;
import javafxsgp_lisoft.modelo.dao.DesarrolladorDAO;
import javafxsgp_lisoft.modelo.pojo.Actividad;
import javafxsgp_lisoft.respuesta.ListaActividadRespuesta;
import javafxsgp_lisoft.modelo.pojo.Desarrollador;
import javafxsgp_lisoft.respuesta.ListaDesarrolladorRespuesta;
import javafxsgp_lisoft.utils.Constantes;
import javafxsgp_lisoft.utils.Utilidades;


public class FXMLActividadesSinAsignarController implements Initializable {
    private ObservableList<Actividad> actividadesSinAsignar;
    private ObservableList<Desarrollador> desarrolladores;
    private int idActividad;
    @FXML
    private TableView<Actividad> tvActividadesSinAsignar;
    @FXML
    private TableColumn colNombreActividad;
    @FXML
    private TableColumn colFechaInicio;
    @FXML
    private TableColumn colFechaFin;
    @FXML
    private TableColumn colEsfuerzo;
    @FXML
    private TableColumn colEstado;    
    @FXML
    private Label lbTitulo;   
    @FXML
    private ComboBox<Desarrollador> cbDesarrolladores;   

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        desarrolladores = FXCollections.observableArrayList();
        cargarInformacionDesarrolladores();
        configurarTVActSinAsignar();
        cargarInformacionTVActSinAsignar();
    } 
    
    private void configurarTVActSinAsignar(){
        this.colNombreActividad.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        this.colFechaInicio.setCellValueFactory(new PropertyValueFactory<>("fechaInicio"));
        this.colFechaFin.setCellValueFactory(new PropertyValueFactory<>("fechaFin"));
        this.colEsfuerzo.setCellValueFactory(new PropertyValueFactory<>("esfuerzo"));
        this.colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
    }
    
    private void cargarInformacionTVActSinAsignar(){       
        actividadesSinAsignar = FXCollections.observableArrayList();
        ListaActividadRespuesta respuestaBD = ActividadDAO.obtActSinAsignar();
        switch(respuestaBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexion con la base de datos",
                        "Error. No se pudo conectar con la base de datos. Inténtelo de nuevo o hágalo más tarde.",
                        Alert.AlertType.WARNING);
                Utilidades.cerrarSesionDesconexion((Stage)tvActividadesSinAsignar.getScene().getWindow());
                break;                
             case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos",
                        "Hubo un error al cargar la información, por favor intentelo mas tarde",
                        Alert.AlertType.WARNING);
                break;                
            case Constantes.OPERACION_EXITOSA:
                    actividadesSinAsignar.addAll(respuestaBD.getActividades());
                    tvActividadesSinAsignar.setItems(actividadesSinAsignar);
                break;
        }
    }
    
    private void cargarInformacionDesarrolladores(){
        ListaDesarrolladorRespuesta respuesta = DesarrolladorDAO.obtenerDesarrolladores();
        switch(respuesta.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexion con la base de datos",
                        "Error. No se pudo conectar con la base de datos. Inténtelo de nuevo o hágalo más tarde.",
                        Alert.AlertType.WARNING);
                Utilidades.cerrarSesionDesconexion((Stage)tvActividadesSinAsignar.getScene().getWindow());
                break;                
             case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos",
                        "Hubo un error al cargar la información, por favor intentelo mas tarde",
                        Alert.AlertType.WARNING);
                break;                
            case Constantes.OPERACION_EXITOSA:
                        desarrolladores.addAll(respuesta.getDesarrolladores());
                        cbDesarrolladores.setItems(desarrolladores);
                break;
        }   
    }

    @FXML
    private void btnAsignarActividad(ActionEvent event) {
        int posicion =  tvActividadesSinAsignar.getSelectionModel().getSelectedIndex();
        if(posicion != -1){
            Actividad actividadSeleccionada = tvActividadesSinAsignar.getSelectionModel().getSelectedItem();
            idActividad = actividadSeleccionada.getIdActividad();
            Desarrollador desarrolladorSeleccionado = cbDesarrolladores.getValue();
            if (desarrolladorSeleccionado != null) {
                asignarActividadADesarrollador(idActividad, desarrolladorSeleccionado.getIdUsuario());
                
            } else {
                Utilidades.mostrarDialogoSimple("Información faltante", 
                        "Se debe seleccionar un desarrollador y una actividad para hacer la asignación", 
                        Alert.AlertType.WARNING);
            }
        } else {
            Utilidades.mostrarDialogoSimple("Información faltante", 
                    "Se debe seleccionar un desarrollador y una actividad para hacer la asignación",
                    Alert.AlertType.WARNING);
        }
        
    }
    
    private void asignarActividadADesarrollador(int idActividad, int idUsuario) {
        int codigoRespuesta = ActividadDAO.asignarActividadADesarrollador(idActividad, idUsuario);
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexion con la base de datos",
                        "Error. No se pudo conectar con la base de datos. Inténtelo de nuevo o hágalo más tarde.",
                        Alert.AlertType.ERROR);
                Utilidades.cerrarSesionDesconexion((Stage)tvActividadesSinAsignar.getScene().getWindow());
                break;                
             case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos",
                        "Hubo un error al cargar la información, por favor intentelo mas tarde",
                        Alert.AlertType.WARNING);
                break;                
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Asignacion realizada",
                        "La actividad se ha asignado correctamente al desarrollador",
                        Alert.AlertType.INFORMATION);
                cargarInformacionTVActSinAsignar();
                break;
        }
    }
    
    @FXML
    private void clicBtnRegresar(MouseEvent event) {
        Stage escenarioPrincipal = (Stage) lbTitulo.getScene().getWindow();
        escenarioPrincipal.close();
    }
    
}