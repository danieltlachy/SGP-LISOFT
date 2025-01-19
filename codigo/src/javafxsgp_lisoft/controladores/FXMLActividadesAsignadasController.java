/*
*Autor: Martinez Caixba Miguel Angel
*Fecha de creación: 30/11/2023
*Fecha de modificación: 10/12/2023
*Descripción: Controlador para hacer la desasignación de la activivadad
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

public class FXMLActividadesAsignadasController implements Initializable {
    @FXML
    private TableView<Actividad> tvActividadesAsignadas;
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
    
    private ObservableList<Desarrollador> desarrolladores;
    private ObservableList<Actividad> actividadesAsignadas;
    
    private int idActividad;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        desarrolladores = FXCollections.observableArrayList();
        cargarInformacionDesarrolladores();
        configurarTVActAsignadas();
        cbDesarrolladores.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cargarActividadesDelDesarrollador(newValue);
            }
        });
        
    }    
    
    private void cargarInformacionDesarrolladores(){
        ListaDesarrolladorRespuesta respuesta = DesarrolladorDAO.obtenerDesarrolladores();
        switch(respuesta.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexion con la base de datos",
                        "Error. No se pudo conectar con la base de datos. Inténtelo de nuevo o hágalo más tarde.",
                        Alert.AlertType.WARNING);
                Utilidades.cerrarSesionDesconexion((Stage)tvActividadesAsignadas.getScene().getWindow());
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
    
    private void cargarActividadesDelDesarrollador(Desarrollador desarrollador) {
        int idUsuario = desarrollador.getIdUsuario();
        cargarInformacionTVActAsignadas(idUsuario);
    }
    
    private void configurarTVActAsignadas(){
        this.colNombreActividad.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        this.colFechaInicio.setCellValueFactory(new PropertyValueFactory<>("fechaInicio"));
        this.colFechaFin.setCellValueFactory(new PropertyValueFactory<>("fechaFin"));
        this.colEsfuerzo.setCellValueFactory(new PropertyValueFactory<>("esfuerzo"));
        this.colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
    }
    
    private void cargarInformacionTVActAsignadas(int idUsuario){
        actividadesAsignadas = FXCollections.observableArrayList();
        ListaActividadRespuesta codigoRespuesta = DesarrolladorDAO.obtenerActividadesPorDesarrollador(idUsuario);
        switch(codigoRespuesta.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexion con la base de datos",
                        "Error. No se pudo conectar con la base de datos. Inténtelo de nuevo o hágalo más tarde.",
                        Alert.AlertType.WARNING);
                Utilidades.cerrarSesionDesconexion((Stage)tvActividadesAsignadas.getScene().getWindow());
                break;                
             case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos",
                        "Hubo un error al cargar la información, por favor intentelo mas tarde",
                        Alert.AlertType.WARNING);
                break;                
            case Constantes.OPERACION_EXITOSA:
                    actividadesAsignadas.addAll(codigoRespuesta.getActividades());
                    tvActividadesAsignadas.setItems(actividadesAsignadas);
                break;
        }        
    }
    

    @FXML
    private void btnDesasignarActividad(ActionEvent event) {
        int posicion = tvActividadesAsignadas.getSelectionModel().getSelectedIndex();
        if(posicion != -1){
            Actividad actividadSeleccionada = tvActividadesAsignadas.getSelectionModel().getSelectedItem();
            idActividad = actividadSeleccionada.getIdActividad();
            Desarrollador desarrolladorSeleccionado = cbDesarrolladores.getValue();
            if(desarrolladorSeleccionado != null){
                desasignarActividadADesarrollador(idActividad, desarrolladorSeleccionado.getIdUsuario());
            }
        }else{
            Utilidades.mostrarDialogoSimple("Información faltante", 
                        "Se debe seleccionar un desarrollador y una actividad para hacer la desasignación", 
                        Alert.AlertType.WARNING);
        }
    }
    
    private void desasignarActividadADesarrollador(int idActividad, int idUsuario){
        int codigoRespuesta = ActividadDAO.desasignarActividadADesarrollador(idActividad, idUsuario);
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexion con la base de datos",
                        "Error. No se pudo conectar con la base de datos. Inténtelo de nuevo o hágalo más tarde.",
                        Alert.AlertType.ERROR);
                Utilidades.cerrarSesionDesconexion((Stage)tvActividadesAsignadas.getScene().getWindow());
                break;
                
             case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos",
                        "Hubo un error al cargar la información, por favor intentelo mas tarde",
                        Alert.AlertType.WARNING);
                break;
                
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Desasignación realizada",
                        "Actividad desasignada al desarrollador",
                        Alert.AlertType.INFORMATION);
                cargarInformacionTVActAsignadas(idUsuario);                
                break;
        }
    }

    @FXML
    private void clicBtnRegresar(MouseEvent event) {
        Stage escenarioPrincipal = (Stage) lbTitulo.getScene().getWindow();
        escenarioPrincipal.close();
    }
    
}
