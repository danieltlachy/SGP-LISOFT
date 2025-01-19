/*
*Autor: Torres Osorio Alesis de Jesus
*Fecha de creación: 10/11/2023
*Fecha de modificación: 17/11/2023
*Descripción: Controlador de la vista de la bitacora del desarrollador
*/
package javafxsgp_lisoft.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.stage.Stage;
import javafxsgp_lisoft.modelo.dao.ActividadDAO;
import javafxsgp_lisoft.modelo.dao.CambioDAO;
import javafxsgp_lisoft.modelo.pojo.Actividad;
import javafxsgp_lisoft.modelo.pojo.Cambio;
import javafxsgp_lisoft.modelo.pojo.Desarrollador;
import javafxsgp_lisoft.modelo.pojo.ResponsableProyecto;
import javafxsgp_lisoft.respuesta.Bitacora;
import javafxsgp_lisoft.respuesta.ListaActividadRespuesta;
import javafxsgp_lisoft.respuesta.ListaCambioRespuesta;
import javafxsgp_lisoft.utils.Constantes;
import javafxsgp_lisoft.utils.Utilidades;

public class FXMLBitacoraDesarrolladorController implements Initializable {
    private Desarrollador usuarioDesarrollador;
    private ResponsableProyecto usuarioResponsable;
    private Bitacora bitacoraSeleccionada;
    private ObservableList<Actividad> actividades;
    private ObservableList<Cambio> cambios;
    
    @FXML
    private Label lbTitulo;
    @FXML
    private Label lbDesarrollador;
    
    @FXML
    private TableView<Cambio> tvBitacoraCambio;
    @FXML
    private TableColumn colNombreCambio;
    @FXML
    private TableColumn  colEstadoCambio;
    @FXML
    private TableColumn  colModificacionCambio;

    @FXML
    private TableView<Actividad> tvBitacoraActividad;
    @FXML
    private TableColumn colNombreActividad;
    @FXML
    private TableColumn colEstadoActividad;
    @FXML
    private TableColumn colModificacionActividad;
    @FXML
    private TableColumn colTipoCambio;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    void inicializarInformacion(Desarrollador usuarioDesarrollador,
        ResponsableProyecto usuarioResponsable, Bitacora bitacoraSeleccionada) {
        this.usuarioDesarrollador = usuarioDesarrollador;
        this.bitacoraSeleccionada = bitacoraSeleccionada;
        this.usuarioResponsable = usuarioResponsable;
        
        lbDesarrollador.setText("Bitacora del desarrollador " + bitacoraSeleccionada.getNombreDesarrollador());
        if(bitacoraSeleccionada.getTipo().equals("Cambios")){
            lbTitulo.setText("Bitacora de cambios");
            tvBitacoraCambio.setVisible(true);
            configurarBitacoraCambio();
            cargarInformacionCambio(bitacoraSeleccionada.getDesarrollador().getIdUsuario());
        }else{
            lbTitulo.setText("Bitacora de Actividades");
            tvBitacoraActividad.setVisible(true);
            configurarBitacoraActividad();
            cargarInformacionActividad(bitacoraSeleccionada.getDesarrollador().getIdUsuario());
        }
    }

    private void configurarBitacoraActividad() {
        colNombreActividad.setCellValueFactory(
                new PropertyValueFactory("nombre")
        );
        colEstadoActividad.setCellValueFactory(
                new PropertyValueFactory("estado")
        );
        colModificacionActividad.setCellValueFactory(
                new PropertyValueFactory("fechaFin")
        );
    }

    private void cargarInformacionActividad(int idUsuario) {
        actividades = FXCollections.observableArrayList();
        ListaActividadRespuesta respuestaBD;
        respuestaBD = ActividadDAO.obtenerActividadPorDesarrollador(idUsuario);
        switch(respuestaBD.getCodigoRespuesta()) {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexion con la base de datos",
                        "Error. No se pudo conectar con la base de datos. Inténtelo de nuevo o hágalo más tarde.",
                        Alert.AlertType.ERROR);
                Utilidades.cerrarSesionDesconexion((Stage)tvBitacoraActividad.getScene().getWindow());
                break;                
             case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos",
                        "Hubo un error al cargar la información, por favor intentelo mas tarde",
                        Alert.AlertType.WARNING);
                break;                
            case Constantes.OPERACION_EXITOSA:
                actividades.addAll(respuestaBD.getActividades( ));
                tvBitacoraActividad.setItems(actividades);
                break;
        }
    }

    private void configurarBitacoraCambio() {
        colNombreCambio.setCellValueFactory(
                new PropertyValueFactory("nombre")
        );
        colEstadoCambio.setCellValueFactory(
                new PropertyValueFactory("estado")
        );
        colTipoCambio.setCellValueFactory( new PropertyValueFactory("tipo"));
        colModificacionCambio.setCellValueFactory(
                new PropertyValueFactory("fechaCreacion")
        );
    }

    private void cargarInformacionCambio(int idUsuario) {
        cambios = FXCollections.observableArrayList();
        ListaCambioRespuesta respuestaBD;
        respuestaBD = CambioDAO.obtenerListaCambiosRealizados(idUsuario);
        switch(respuestaBD.getCodigoRespuesta()) {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexion con la base de datos",
                        "Error. No se pudo conectar con la base de datos. Inténtelo de nuevo o hágalo más tarde.",
                        Alert.AlertType.ERROR);
                Utilidades.cerrarSesionDesconexion((Stage)tvBitacoraCambio.getScene().getWindow());
                break;                
             case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos",
                        "Hubo un error al cargar la información, por favor intentelo mas tarde",
                        Alert.AlertType.WARNING);
                break;                
            case Constantes.OPERACION_EXITOSA:
                cambios.addAll(respuestaBD.getListaCambio());
                tvBitacoraCambio.setItems(cambios);
                break;
        }
    }

    @FXML
    private void clcRegresar(MouseEvent event) {
        try {
                Stage stageActual = (Stage) lbTitulo.getScene().getWindow();
                FXMLLoader loader = Utilidades.cargarVista("vistas/FXMLConsultarListaBitacoras.fxml");
                Parent vista = loader.load();
                Scene escena = new Scene(vista);
                FXMLConsultarListaBitacorasController controler = loader.getController();
                controler.inicializarInformacion(usuarioDesarrollador,usuarioResponsable);
                stageActual.setScene(escena);
                stageActual.setTitle("Bitacora del desarrollador");
                stageActual.showAndWait();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
    }
    
}
