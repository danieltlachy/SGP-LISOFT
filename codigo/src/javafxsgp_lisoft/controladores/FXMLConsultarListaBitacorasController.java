/*
*Autor: Torres Osorio Alesis de Jesus
*Fecha de creación: 10/11/2023
*Fecha de modificación: 17/11/2023
*Descripción: Controlador de la vista de la lista de bitacoras
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafxsgp_lisoft.modelo.dao.CatalogoDAO;
import javafxsgp_lisoft.respuesta.Bitacora;
import javafxsgp_lisoft.modelo.pojo.Desarrollador;
import javafxsgp_lisoft.modelo.pojo.ResponsableProyecto;
import javafxsgp_lisoft.respuesta.ListaBitacoraRespuesta;
import javafxsgp_lisoft.utils.Constantes;
import javafxsgp_lisoft.utils.Utilidades;

public class FXMLConsultarListaBitacorasController implements Initializable {
    private Desarrollador usuarioDesarrollador;
    private ResponsableProyecto usuarioResponsable;
    private ObservableList<Bitacora> bitacoras;
    @FXML
    private TextField tfBusqueda;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colTipo;
    @FXML
    private TableColumn colFechaModificacion;
    @FXML
    private TableView<Bitacora> tvBitacoras;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTablaBitacoras();
    }
    
    public void inicializarInformacion(Desarrollador usuarioDesarrollador, ResponsableProyecto usuarioResponsable){
        this.usuarioDesarrollador = usuarioDesarrollador;
        this.usuarioResponsable = usuarioResponsable;
        bitacoras = FXCollections.observableArrayList();
        if(usuarioResponsable != null){
            cargarBitacoraCambiosResponsable();
            cargarBitacoraActividadesResponsable();
            tvBitacoras.setItems(bitacoras);
        }else{
            cargarBitacoraCambiosDesarrollador();
            cargarBitacoraActividadesDesarrollador();
            tvBitacoras.setItems(bitacoras);
        }
        configurarBusquedaTabla();
    }


    private void configurarTablaBitacoras() {
        this.colNombre.setCellValueFactory(new PropertyValueFactory("nombreDesarrollador"));
        this.colTipo.setCellValueFactory(new PropertyValueFactory("tipo"));
        this.colFechaModificacion.setCellValueFactory(new PropertyValueFactory("ultimaModificacion"));
    }

    private void cargarBitacoraCambiosResponsable() {
        ListaBitacoraRespuesta respuestaBD;
        respuestaBD = CatalogoDAO.obtenerBitacorasCambioProyecto(usuarioResponsable.getIdProyecto());
        switch(respuestaBD.getCodigoRespuesta()) {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexion con la base de datos",
                        "Error. No se pudo conectar con la base de datos. Inténtelo de nuevo o hágalo más tarde.",
                        Alert.AlertType.ERROR);
                Utilidades.cerrarSesionDesconexion((Stage)tvBitacoras.getScene().getWindow());
                break;                
             case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos",
                        "Hubo un error al cargar la información, por favor intentelo mas tarde",
                        Alert.AlertType.WARNING);
                break;                
            case Constantes.OPERACION_EXITOSA:
                bitacoras.addAll(respuestaBD.getBitacoras( ));
                break;
        }
    }
    private void cargarBitacoraActividadesResponsable() {
        ListaBitacoraRespuesta respuestaBD;
        respuestaBD = CatalogoDAO.obtenerBitacorasActividadProyecto(usuarioResponsable.getIdProyecto());
        switch(respuestaBD.getCodigoRespuesta()) {
                case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexion con la base de datos",
                        "Error. No se pudo conectar con la base de datos. Inténtelo de nuevo o hágalo más tarde.",
                        Alert.AlertType.ERROR);
                Utilidades.cerrarSesionDesconexion((Stage)tvBitacoras.getScene().getWindow());
                break;                
             case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos",
                        "Hubo un error al cargar la información, por favor intentelo mas tarde",
                        Alert.AlertType.WARNING);
                break;                
            case Constantes.OPERACION_EXITOSA:
                bitacoras.addAll(respuestaBD.getBitacoras( ));
                break;
        }
    }
   
    @FXML
    private void clcRegresar(MouseEvent event) {
        if(usuarioDesarrollador!=null){
            Stage stageActual = (Stage) tvBitacoras.getScene().getWindow();
            try {
                  FXMLLoader loader = Utilidades.cargarVista("vistas/FXMLMenuPrincipalDesarrollador.fxml");
                  Parent vista = loader.load();
                  Scene escena = new Scene(vista);
                  FXMLMenuPrincipalDesarrolladorController controler = loader.getController();
                  controler.inicializarInformacion(usuarioDesarrollador);
                  stageActual.setScene(escena);
                  stageActual.setTitle("Menu principal");
                  stageActual.show();
              } catch (IOException ex) {
                  Logger.getLogger(FXMLMenuPrincipalResponsableController.class.getName()).log(Level.SEVERE, null, ex);
              }  
        }else{
            try{
                Stage stageActual = (Stage) tvBitacoras.getScene().getWindow();
                FXMLLoader loader = Utilidades.cargarVista("vistas/FXMLMenuPrincipalResponsable.fxml");
                Parent vista = loader.load();
                Scene escena = new Scene(vista);
                FXMLMenuPrincipalResponsableController controler = loader.getController();
                controler.inicializarInformacionResponsable(usuarioResponsable);
                stageActual.setScene(escena);
                stageActual.setTitle("Menu Principal");
                stageActual.showAndWait();
            } catch (IOException ex) {
                Logger.getLogger(FXMLMenuPrincipalResponsableController.class.getName()).log(Level.SEVERE, null, ex);
            }  
        }
        
    }
    
    private void configurarBusquedaTabla(){
        if(bitacoras.size()>0){
            FilteredList<Bitacora> filtradoBitacoras = new FilteredList<>(bitacoras, p -> true);
            tfBusqueda.textProperty().addListener(new ChangeListener<String>(){
                public void changed(ObservableValue<? extends String> observable,
                String oldValue, String newValue){
                filtradoBitacoras.setPredicate(bitacoraFiltro ->{
                    if(newValue == null || newValue.isEmpty()){
                        return true;
                    }
                    String lowerNewValue = newValue.toLowerCase();
                    if(bitacoraFiltro.getNombreDesarrollador().toLowerCase().contains(lowerNewValue)){
                        return true;
                    }
                    return false;
                });
            }
            });
            SortedList<Bitacora> sortedListaBitacora = new SortedList<>(filtradoBitacoras);
            sortedListaBitacora.comparatorProperty().bind(tvBitacoras.comparatorProperty());
            tvBitacoras.setItems(sortedListaBitacora);
        }
    }

    private void cargarBitacoraCambiosDesarrollador() {
       ListaBitacoraRespuesta respuestaBD;
        respuestaBD = CatalogoDAO.obtenerBitacorasCambioDesarrollador(usuarioDesarrollador.getIdUsuario());
        switch(respuestaBD.getCodigoRespuesta()) {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexion con la base de datos",
                        "Error. No se pudo conectar con la base de datos. Inténtelo de nuevo o hágalo más tarde.",
                        Alert.AlertType.ERROR);
                Utilidades.cerrarSesionDesconexion((Stage)tvBitacoras.getScene().getWindow());
                break;                
             case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos",
                        "Hubo un error al cargar la información, por favor intentelo mas tarde",
                        Alert.AlertType.WARNING);
                break;                
            case Constantes.OPERACION_EXITOSA:
                bitacoras.addAll(respuestaBD.getBitacoras( ));
                break;
        }
    }

    private void cargarBitacoraActividadesDesarrollador() {
        ListaBitacoraRespuesta respuestaBD;
        respuestaBD = CatalogoDAO.obtenerBitacorasActividadDesarrollador(usuarioDesarrollador.getIdUsuario());
        switch(respuestaBD.getCodigoRespuesta()) {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexion con la base de datos",
                        "Error. No se pudo conectar con la base de datos. Inténtelo de nuevo o hágalo más tarde.",
                        Alert.AlertType.ERROR);
                Utilidades.cerrarSesionDesconexion((Stage)tvBitacoras.getScene().getWindow());
                break;                
             case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos",
                        "Hubo un error al cargar la información, por favor intentelo mas tarde",
                        Alert.AlertType.WARNING);
                break;                
            case Constantes.OPERACION_EXITOSA:
                bitacoras.addAll(respuestaBD.getBitacoras( ));
                break;
        }
    }

    @FXML
    private void clcConsultarBitacora(MouseEvent event) {
        Bitacora bitacoraSeleccionada = new Bitacora();
        bitacoraSeleccionada = tvBitacoras.getSelectionModel().getSelectedItem();
        if(bitacoraSeleccionada != null){
            try {
                Stage stageActual = (Stage) tvBitacoras.getScene().getWindow();
                FXMLLoader loader = Utilidades.cargarVista("vistas/FXMLBitacoraDesarrollador.fxml");
                Parent vista = loader.load();
                Scene escena = new Scene(vista);
                FXMLBitacoraDesarrolladorController controler = loader.getController();
                controler.inicializarInformacion(usuarioDesarrollador , usuarioResponsable, bitacoraSeleccionada);
                stageActual.setScene(escena);
                stageActual.setTitle("Bitacora del desarrollador");
                stageActual.showAndWait();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }else{
            Utilidades.mostrarDialogoSimple("No hay bitacora seleccionada", 
                    "Por favor, selecciona una bitacora antes de continuar", Alert.AlertType.WARNING);
        }
    }
}
