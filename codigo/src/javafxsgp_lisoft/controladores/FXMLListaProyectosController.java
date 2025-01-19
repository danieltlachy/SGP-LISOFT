/*
*Autor: Mongeote Tlachy Daniel
*Fecha de creación: 10/11/2023
*Fecha de modificación: 17/11/2023
*Descripción: Controlador de la vista del menú de proyectos 
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
import javafxsgp_lisoft.JavaFXSGP_LISOFT;
import javafxsgp_lisoft.modelo.dao.ProyectoDAO;
import javafxsgp_lisoft.modelo.pojo.Proyecto;
import javafxsgp_lisoft.modelo.pojo.ResponsableProyecto;
import javafxsgp_lisoft.observador.ObservadorProyecto;
import javafxsgp_lisoft.respuesta.ListaProyectoRespuesta;
import javafxsgp_lisoft.utils.Constantes;
import javafxsgp_lisoft.utils.Utilidades;

public class FXMLListaProyectosController implements Initializable, ObservadorProyecto {

    private ObservableList<Proyecto> proyectos;
    private ResponsableProyecto usuarioResponsable;
    
    @FXML
    private TableView<Proyecto> tvListaProyectos;
    @FXML
    private Label lblTituloVista;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colMateria;
    @FXML
    private TableColumn colFechaInicio;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionTablaProyectos();
    }
    
    public void inicializarInformacion(ResponsableProyecto usuarioResponsable){
        this.usuarioResponsable = usuarioResponsable;
    }
    
    private void configurarTabla(){
        this.colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.colMateria.setCellValueFactory(new PropertyValueFactory("nombreMateria"));
        this.colFechaInicio.setCellValueFactory(new PropertyValueFactory("fechaInicio"));
    }
    
    private void cargarInformacionTablaProyectos(){
        ListaProyectoRespuesta respuestaBD = ProyectoDAO.obtenerProyectos();
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
                    proyectos = FXCollections.observableArrayList(respuestaBD.getProyectos());
                    tvListaProyectos.setItems(proyectos);
                break;
        }
    }

    @FXML
    private void clicRegresar(MouseEvent event) {
        Stage escenarioBase = (Stage) lblTituloVista.getScene().getWindow();
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSGP_LISOFT.class.getResource("vistas/FXMLMenuPrincipalResponsable.fxml"));
            Parent vista = accesoControlador.load();
            FXMLMenuPrincipalResponsableController controler = accesoControlador.getController();
            controler.inicializarInformacionResponsable(usuarioResponsable);
            escenarioBase.setScene(new Scene (vista));
            escenarioBase.setTitle("Menú Principal");
            escenarioBase.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLMenuPrincipalResponsableController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void irFormulario(){
        try {
            FXMLLoader loader = Utilidades.cargarVista("vistas/FXMLFormularioProyecto.fxml"); 
            Parent vista = loader.load();
            Scene escena = new Scene(vista);
            FXMLFormularioProyectoController controler = loader.getController();
            controler.inicializarInformacion(this); 
            Stage escenario = new Stage();
            escenario.setScene(escena);
            escenario.setTitle("Formulario proyecto");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void operacionExitosa(String tipoOperacion, String nombre) {
        cargarInformacionTablaProyectos();
    }

    @FXML
    private void clicCrearProyecto(MouseEvent event) {
        irFormulario();
    }
}
