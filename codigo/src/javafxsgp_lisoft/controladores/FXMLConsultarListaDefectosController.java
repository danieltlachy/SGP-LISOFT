/*
*Autor: Torres Osorio Alesis de Jesus
*Fecha de creación: 10/11/2023
*Fecha de modificación: 17/11/2023
*Descripción: Controlador de la vista de la lista de defectos
*/
package javafxsgp_lisoft.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafxsgp_lisoft.JavaFXSGP_LISOFT;
import javafxsgp_lisoft.modelo.dao.DefectoDAO;
import javafxsgp_lisoft.modelo.pojo.Defecto;
import javafxsgp_lisoft.modelo.pojo.Desarrollador;
import javafxsgp_lisoft.modelo.pojo.ResponsableProyecto;
import javafxsgp_lisoft.respuesta.ListaDefectoRespuesta;
import javafxsgp_lisoft.utils.Constantes;
import javafxsgp_lisoft.utils.Utilidades;

public class FXMLConsultarListaDefectosController implements Initializable {
    private Desarrollador usuarioDesarrollador;
    private ResponsableProyecto usuarioResponsable;

    private ObservableList<Defecto> defectos;
    
    @FXML
    private TextField tfBusqueda;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colTipo;
    @FXML
    private TableView<Defecto> tvDefectos;
    @FXML
    private TableColumn  colFechaDeteccion;
    @FXML
    private TableColumn colResponsableReporte;
    @FXML
    private Button btnBusqueda;
    @FXML
    private TableColumn colEstado;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTablaDefectos();
    }
    
    public void inicializarInformacion(Desarrollador usuarioDesarrollador, ResponsableProyecto usuarioResponsable){
        this.usuarioDesarrollador = usuarioDesarrollador;
        this.usuarioResponsable= usuarioResponsable;
        defectos = FXCollections.observableArrayList();
        if(usuarioDesarrollador != null){
            cargarInformacionTablaDesarrollador();
            tvDefectos.setItems(defectos); 
        }else{
            cargarInformacionTablaResponsable();
            tvDefectos.setItems(defectos);     
        }
        configurarBusquedaTabla();
    }


    private void configurarTablaDefectos() {
        this.colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.colTipo.setCellValueFactory(new PropertyValueFactory("tipo"));
        this.colEstado.setCellValueFactory(new PropertyValueFactory("estado"));
        this.colFechaDeteccion.setCellValueFactory(new PropertyValueFactory("fechaDeteccion"));
        this.colResponsableReporte.setCellValueFactory(new PropertyValueFactory("nombreDesarrollador"));
    }

    private void cargarInformacionTablaResponsable() {
        ListaDefectoRespuesta respuestaBD;
        respuestaBD = DefectoDAO.obtenerListaDefecto(usuarioResponsable.getIdProyecto());
        switch(respuestaBD.getCodigoRespuesta()) {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexion con la base de datos",
                        "Error. No se pudo conectar con la base de datos. Inténtelo de nuevo o hágalo más tarde.",
                        Alert.AlertType.ERROR);
                Utilidades.cerrarSesionDesconexion((Stage)tvDefectos.getScene().getWindow());
                break;                
             case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos",
                        "Hubo un error al cargar la información, por favor intentelo mas tarde",
                        Alert.AlertType.WARNING);
                break;                
            case Constantes.OPERACION_EXITOSA:
                defectos.addAll(respuestaBD.getDefectos());
                break;
        }
    }

    @FXML
    private void clcRegresar(MouseEvent event) {
        if(usuarioDesarrollador!=null){
            Stage stageActual = (Stage) tfBusqueda.getScene().getWindow();
            try {
                  FXMLLoader loader = Utilidades.cargarVista("vistas/FXMLMenuDefectosDesarrollador.fxml");
                  Parent vista = loader.load();
                  Scene escena = new Scene(vista);
                  FXMLMenuDefectosDesarrolladorController controler = loader.getController();
                  controler.inicializarInformacion(usuarioDesarrollador);
                  stageActual.setScene(escena);
                  stageActual.setTitle("Menú Principal");
                  stageActual.show();
              } catch (IOException ex) {
                  Logger.getLogger(FXMLMenuPrincipalResponsableController.class.getName()).log(Level.SEVERE, null, ex);
              }  
        }else{
            try{
                Stage stageActual = (Stage) tfBusqueda.getScene().getWindow();
                FXMLLoader loader = Utilidades.cargarVista("vistas/FXMLMenuPrincipalResponsable.fxml");
                Parent vista = loader.load();
                Scene escena = new Scene(vista);
                FXMLMenuPrincipalResponsableController controler = loader.getController();
                controler.inicializarInformacionResponsable(usuarioResponsable);
                stageActual.setScene(escena);
                stageActual.setTitle("Menú Principal");
                stageActual.showAndWait();
            } catch (IOException ex) {
                Logger.getLogger(FXMLMenuPrincipalResponsableController.class.getName()).log(Level.SEVERE, null, ex);
            }    
        }
    }
    
    @FXML
    private void btnDetalleDefecto(ActionEvent event) {
        Defecto defectoSeleccionado = tvDefectos.getSelectionModel().getSelectedItem();
        if(defectoSeleccionado != null){
            try {
                FXMLLoader loader = 
                    new FXMLLoader(JavaFXSGP_LISOFT.class.getResource("vistas/FXMLDetalleDefecto.fxml"));
                Parent vista = loader.load();
                Scene escena = new Scene(vista);
                
                FXMLDetalleDefectoController controlador = loader.getController();
                controlador.inicializarInformacion(defectoSeleccionado);
                
                Stage stageActual = new Stage();
                stageActual.setScene(escena);
                stageActual.setTitle("Analizar solicitud de cambio");
                stageActual.initModality(Modality.APPLICATION_MODAL);
                stageActual.showAndWait();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }else{
            Utilidades.mostrarDialogoSimple("Defecto no seleccionado", "Por favor, seleccione un defecto antes de continuar", Alert.AlertType.WARNING);
        }
    }
    
    private void configurarBusquedaTabla(){
        if(defectos.size()>0){
            FilteredList<Defecto> filtradoDefectos = new FilteredList<>(defectos, p -> true);
            tfBusqueda.textProperty().addListener(new ChangeListener<String>(){
                public void changed(ObservableValue<? extends String> observable,
                String oldValue, String newValue){
                filtradoDefectos.setPredicate(defectoFiltro ->{
                    if(newValue == null || newValue.isEmpty()){
                        return true;
                    }
                    String lowerNewValue = newValue.toLowerCase();
                    if(defectoFiltro.getNombre().toLowerCase().contains(lowerNewValue)){
                        return true;
                    }
                    return false;
                });
            }
            });
            SortedList<Defecto> sortedListaDefecto = new SortedList<>(filtradoDefectos);
            sortedListaDefecto.comparatorProperty().bind(tvDefectos.comparatorProperty());
            tvDefectos.setItems(sortedListaDefecto);
        }
    }

    private void cargarInformacionTablaDesarrollador() {
        ListaDefectoRespuesta respuestaBD;
        respuestaBD = DefectoDAO.obtenerListaDefectoDesarrollador(usuarioDesarrollador.getIdUsuario());
        switch(respuestaBD.getCodigoRespuesta()) {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexion con la base de datos",
                        "Error. No se pudo conectar con la base de datos. Inténtelo de nuevo o hágalo más tarde.",
                        Alert.AlertType.ERROR);
                Utilidades.cerrarSesionDesconexion((Stage)tvDefectos.getScene().getWindow());
                break;                
             case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos",
                        "Hubo un error al cargar la información, por favor intentelo mas tarde",
                        Alert.AlertType.WARNING);
                break;                
            case Constantes.OPERACION_EXITOSA:
                defectos.addAll(respuestaBD.getDefectos());
                break;
        }
    }

}
