/*
*Autor: Mongeote Tlachy Daniel
*Fecha de creación: 10/11/2023
*Fecha de modificación: 17/11/2023
*Descripción: Controlador de la vista del menú de desarrolladores 
*/
package javafxsgp_lisoft.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafxsgp_lisoft.modelo.dao.DesarrolladorDAO;
import javafxsgp_lisoft.modelo.pojo.Desarrollador;
import javafxsgp_lisoft.modelo.pojo.ResponsableProyecto;
import javafxsgp_lisoft.observador.ObservadorDesarrollador;
import javafxsgp_lisoft.respuesta.ListaDesarrolladorRespuesta;
import javafxsgp_lisoft.utils.Constantes;
import javafxsgp_lisoft.utils.Utilidades;

public class FXMLListaDesarrolladoresController implements Initializable, ObservadorDesarrollador {

    private ObservableList<Desarrollador> desarrolladores;
    private ResponsableProyecto usuarioResponsable;
    
    @FXML
    private Label lblTituloVista;
    @FXML
    private TableView<Desarrollador> tvListaDesarrolladores;
    @FXML
    private TableColumn colNombreDesarrollador;
    @FXML
    private TableColumn colMatriculaDesarrollador;
    @FXML
    private TableColumn colEEDesarrollador;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarInformacionTablaDesarrolladores();
        configurarTabla();
    }

    public void inicializarInformacion(ResponsableProyecto usuarioResponsable){
        this.usuarioResponsable = usuarioResponsable;
    }
    
    private void configurarTabla(){
        this.colNombreDesarrollador.setCellValueFactory(new PropertyValueFactory("nombreCompleto"));
        this.colMatriculaDesarrollador.setCellValueFactory(new PropertyValueFactory("matricula"));
        this.colEEDesarrollador.setCellValueFactory(new PropertyValueFactory("nombreMateria"));
    }
    
    private void cargarInformacionTablaDesarrolladores(){
        ListaDesarrolladorRespuesta respuestaBD = DesarrolladorDAO.obtenerDetallesDesarrollador();
        switch(respuestaBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Error Conexión", "No se pudo conectar con la base de datos. Inténtelo de nuevo o hágalo más tarde", Alert.AlertType.ERROR);
                    Utilidades.cerrarSesionDesconexion((Stage)lblTituloVista.getScene().getWindow());
                    break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error al cargar los datos", "Hubo un error al cargar la información por favor inténtelo más tarde", 
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                    desarrolladores = FXCollections.observableArrayList(respuestaBD.getDesarrolladores());
                    tvListaDesarrolladores.setItems(desarrolladores);
                break;
        }
    }
    
    @Override
    public void operacionExitosa(String tipoOperacion, String nombre){
        cargarInformacionTablaDesarrolladores();
    }

    @FXML
    private void clicRegresar(MouseEvent event) {
         try{
                Stage stageActual = (Stage) lblTituloVista.getScene().getWindow();
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

    private void irFormulario(Desarrollador desarrollador){
        try {
            FXMLLoader loader = Utilidades.cargarVista("vistas/FXMLFormularioDesarrollador.fxml");
            Parent vista = loader.load();
            FXMLFormularioDesarrolladorController controlador = loader.getController();
            controlador.inicializarInformacion(this, desarrollador); 
            Stage escenarioFormulario = new Stage();
            escenarioFormulario.setScene(new Scene(vista));
            escenarioFormulario.setTitle("Formulario Desarrollador");
            escenarioFormulario.initModality(Modality.APPLICATION_MODAL);
            escenarioFormulario.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void clcCrearDesarrollador(MouseEvent event) {
        irFormulario(null);
    }

    @FXML
    private void clicModificarDesarrollador(MouseEvent event) {
        Desarrollador desarrolladorSeleccionado = tvListaDesarrolladores.getSelectionModel().getSelectedItem();
        if(desarrolladorSeleccionado != null){
            irFormulario(desarrolladorSeleccionado);
        }else{
            Utilidades.mostrarDialogoSimple("Error. Seleciona un desarrollador", "Debes seleccionar un desarrollador de la tabla para su edición.", Alert.AlertType.WARNING);
        }
    }
}
