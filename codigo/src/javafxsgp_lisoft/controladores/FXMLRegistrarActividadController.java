/*
*Autor: Martinez Caixba Miguel Angel
*Fecha de creación: 04/12/2023
*Fecha de modificación: 09/12/2023
*Descripción: Controlador para finalizar la actividad
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafxsgp_lisoft.modelo.dao.ActividadDAO;
import javafxsgp_lisoft.modelo.pojo.Actividad;
import javafxsgp_lisoft.respuesta.ListaActividadRespuesta;
import javafxsgp_lisoft.utils.Constantes;
import javafxsgp_lisoft.utils.Utilidades;

public class FXMLRegistrarActividadController implements Initializable {
    private int idDesarrollador;   
    private ObservableList<Actividad> actividadesAsignadas;
    private int idActividad;
    
    @FXML
    private Label lbTitulo;
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTvActividadesAsignadasADesarrollador();
        
    }    
    
    public void inicializarInformacion(int idDesarrollador){
        this.idDesarrollador = idDesarrollador;
        cargarInfoTvActividadesAsignadasADesarrollador();
    }
    
    private void configurarTvActividadesAsignadasADesarrollador(){
        this.colNombreActividad.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        this.colFechaInicio.setCellValueFactory(new PropertyValueFactory<>("fechaInicio"));
        this.colFechaFin.setCellValueFactory(new PropertyValueFactory<>("fechaFin"));
        this.colEsfuerzo.setCellValueFactory(new PropertyValueFactory<>("esfuerzo"));
    }
    
    private void cargarInfoTvActividadesAsignadasADesarrollador(){
        actividadesAsignadas = FXCollections.observableArrayList();        
        ListaActividadRespuesta respuestaBD = ActividadDAO.obtActAsignadasADesarrollador(idDesarrollador);
        switch(respuestaBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexion con la base de datos",
                        "Error. No se pudo conectar con la base de datos. Inténtelo de nuevo o hágalo más tarde.",
                        Alert.AlertType.WARNING);
                break;                
             case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos",
                        "Hubo un error al cargar la información, por favor intentelo mas tarde",
                        Alert.AlertType.WARNING);
                break;                
            case Constantes.OPERACION_EXITOSA:
                    actividadesAsignadas.addAll(respuestaBD.getActividades());
                    tvActividadesAsignadas.setItems(actividadesAsignadas);
                break;
        }
    }
    
    @FXML
    private void btnFinalizarActividad(ActionEvent event) {
        int posicion = tvActividadesAsignadas.getSelectionModel().getSelectedIndex();
        if(posicion != -1){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Preguntar finalizacion");
            alert.setContentText("¿Seguro que quieres finalizar la actividad?");
            ButtonType btnAceptar = new ButtonType("Aceptar");
            ButtonType btnCancelar = new ButtonType("Cancelar");
            alert.getButtonTypes().setAll(btnAceptar, btnCancelar);
            ButtonType resultado = alert.showAndWait().get();

            if (resultado == btnAceptar) {
                Actividad actividadSeleccionada = tvActividadesAsignadas.getSelectionModel().getSelectedItem();
                idActividad = actividadSeleccionada.getIdActividad();
                finalizarActividadDeDesarrollador(idActividad);
                Stage escenarioActual = (Stage) lbTitulo.getScene().getWindow();
            }else if(resultado == btnCancelar){
                Utilidades.mostrarDialogoSimple("Finalización cancelada", 
                            "Se canceló la acción", 
                            Alert.AlertType.INFORMATION);
            }
        }else{
            Utilidades.mostrarDialogoSimple("Actividad no seleccionada ", 
                        "Selecciona una actividad", 
                        Alert.AlertType.WARNING);
        }
    }
    
    private void finalizarActividadDeDesarrollador(int idActividad){
        int codigoRespuesta = ActividadDAO.finalizarActividad(idActividad);
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexion con la base de datos",
                        "Error. No se pudo conectar con la base de datos. Inténtelo de nuevo o hágalo más tarde.",
                        Alert.AlertType.ERROR);
                break;                
             case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos",
                        "Hubo un error al registrar la información, por favor intentelo mas tarde",
                        Alert.AlertType.WARNING);
                break;                
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Registro correcto",
                        "Los cambios se han guardado correctamente",
                        Alert.AlertType.INFORMATION);
                cargarInfoTvActividadesAsignadasADesarrollador();
                break;
        }
    }
    
    

    @FXML
    private void clicRegresar(MouseEvent event) {
        Stage escenarioPrincipal = (Stage) lbTitulo.getScene().getWindow();
        escenarioPrincipal.close();
    }
    
}
