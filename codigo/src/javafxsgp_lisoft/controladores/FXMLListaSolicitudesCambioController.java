/*
*Autor: Martinez Caixba Miguel Angel
*Fecha de creación: 02/12/2023
*Fecha de modificación: 13/12/2023
*Descripción: Controlador para analizar la soliciutd de cambio pendiente
*/
package javafxsgp_lisoft.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafxsgp_lisoft.JavaFXSGP_LISOFT;
import javafxsgp_lisoft.modelo.dao.SolicitudCambioDAO;
import javafxsgp_lisoft.modelo.pojo.ResponsableProyecto;
import javafxsgp_lisoft.modelo.pojo.SolicitudCambio;
import javafxsgp_lisoft.respuesta.ListaSolicitudCambioRespuesta;
import javafxsgp_lisoft.utils.Constantes;
import javafxsgp_lisoft.utils.Utilidades;


public class FXMLListaSolicitudesCambioController implements Initializable {
    private ObservableList<SolicitudCambio> solicitudesCambioPendientes;
    private int idSolicitudCambio;
    @FXML
    private Label lbTitulo;
    @FXML
    private TableView<SolicitudCambio> tvSolicitudesCambio;
    @FXML
    private TableColumn colNombreProyecto;
    @FXML
    private TableColumn colNombreSolicitud;
    @FXML
    private TableColumn colNombreSolicitante;
    @FXML
    private TableColumn colApellidoPaterno;
    @FXML
    private TableColumn colApellidoMaterno;
    @FXML
    private TableColumn colFechaSolicitud;
    private ResponsableProyecto usuarioResponsable;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTvSolicitudesCambio();
        cargarInformacionTvSolicitudesCambio();
    }     
    

    private void configurarTvSolicitudesCambio(){
        this.colNombreProyecto.setCellValueFactory(new PropertyValueFactory("nombreProyecto"));
        this.colNombreSolicitud.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.colNombreSolicitante.setCellValueFactory(new PropertyValueFactory("nombreDesarrollador"));
        this.colApellidoPaterno.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        this.colApellidoMaterno.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
        this.colFechaSolicitud.setCellValueFactory(new PropertyValueFactory("fechaSolicitud"));
    
    }
    
    private void cargarInformacionTvSolicitudesCambio(){
        solicitudesCambioPendientes = FXCollections.observableArrayList();
        ListaSolicitudCambioRespuesta respuestaBD = SolicitudCambioDAO.obtSolicitudesCambioPendientes();
        switch(respuestaBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexion con la base de datos",
                        "Error. No se pudo conectar con la base de datos. Inténtelo de nuevo o hágalo más tarde.",
                        Alert.AlertType.WARNING);
                Utilidades.cerrarSesionDesconexion((Stage)tvSolicitudesCambio.getScene().getWindow());
                break;
                
             case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos",
                        "Hubo un error al cargar la información, por favor intentelo mas tarde",
                        Alert.AlertType.WARNING);
                break;
                
            case Constantes.OPERACION_EXITOSA:
                    solicitudesCambioPendientes.addAll(respuestaBD.getSolicitudesCambio());
                    tvSolicitudesCambio.setItems(solicitudesCambioPendientes);
                break;
        }
    }
    

    @FXML
    private void btnAnalizar(ActionEvent event) {
        int posicion = tvSolicitudesCambio.getSelectionModel().getSelectedIndex();
        if (posicion != -1) {
            SolicitudCambio solicitudSeleccionada = solicitudesCambioPendientes.get(posicion);
            idSolicitudCambio = solicitudSeleccionada.getIdSolicitudCambio();
            try {
                FXMLLoader loader = 
                    new FXMLLoader(JavaFXSGP_LISOFT.class.getResource("vistas/FXMLEvaluarSolicitudDeCambio.fxml"));
                Parent vista = loader.load();
                Scene escena = new Scene(vista);                
                FXMLEvaluarSolicitudDeCambioController controlador = loader.getController();
                controlador.inicializarInformacion(idSolicitudCambio);                
                Stage stageActual = new Stage();
                stageActual.setScene(escena);
                stageActual.setTitle("Analizar solicitud de cambio");
                stageActual.initModality(Modality.APPLICATION_MODAL);
                stageActual.showAndWait();                
                cargarInformacionTvSolicitudesCambio();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
        } else {
            Utilidades.mostrarDialogoSimple("Solicitud no seleccionada",
                    "Selecciona una solicitud de cambio",
                    Alert.AlertType.WARNING);
        }
    }
  
    @FXML
    private void clicBtnRegresar(MouseEvent event) {
        Stage escenarioBase = (Stage)lbTitulo.getScene().getWindow();
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSGP_LISOFT.class.getResource("vistas/FXMLMenuCambiosResponsable.fxml"));
            Parent vista = accesoControlador.load();
            FXMLMenuCambiosResponsableController controler = accesoControlador.getController();
            controler.inicializarInformacion(usuarioResponsable);
            escenarioBase.setScene(new Scene (vista));
            escenarioBase.setTitle("Menú Principal");
            escenarioBase.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    void inicializarInformacion(ResponsableProyecto usuarioResponsable) {
        this.usuarioResponsable = usuarioResponsable;
    }
    
}
