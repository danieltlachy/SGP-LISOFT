/*
*Autor: Mongeote Tlachy Daniel
*Fecha de creación: 20/11/2023
*Fecha de modificación: 20/11/2023
*Descripción: Controlador de la vista para la creacion de proyectos.
*/
package javafxsgp_lisoft.controladores;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafxsgp_lisoft.modelo.dao.ProyectoDAO;
import javafxsgp_lisoft.modelo.dao.ResponsableProyectoDAO;
import javafxsgp_lisoft.modelo.pojo.EstadoProyecto;
import javafxsgp_lisoft.modelo.pojo.Proyecto;
import javafxsgp_lisoft.modelo.pojo.ResponsableProyecto;
import javafxsgp_lisoft.observador.ObservadorProyecto;
import javafxsgp_lisoft.respuesta.ListaEstadoProyectoRespuesta;
import javafxsgp_lisoft.respuesta.ListaResponsableProyectoRespuesta;
import javafxsgp_lisoft.utils.Constantes;
import javafxsgp_lisoft.utils.Utilidades;
import static javafxsgp_lisoft.utils.Utilidades.caracteresValidos;

public class FXMLFormularioProyectoController implements Initializable{

    private int idResponsable;
    boolean datosValidos;
    boolean alertaMostrada;
    private ObservableList<EstadoProyecto> estadosProyecto;
    private ObservableList<ResponsableProyecto> responsables;
    private ObservadorProyecto observador;
    
    @FXML
    private Label lblTituloVista;
    @FXML
    private TextField tfNombre;
    @FXML
    private DatePicker dpFechaInicio;
    @FXML
    private DatePicker dpFechaFin;
    @FXML
    private ComboBox<EstadoProyecto> cbEstado;
    @FXML
    private ComboBox<ResponsableProyecto> cbResponsable;
    @FXML
    private TextArea taDescripcion;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       estadosProyecto = FXCollections.observableArrayList();
       cargarInformacionEstadosProyecto();
       responsables = FXCollections.observableArrayList();
       cargarInformacionResponsables();
       dpFechaInicio.setEditable(false);
       dpFechaFin.setEditable(false);
    }
    
    public void inicializarInformacion(ObservadorProyecto observador){
        this.observador = observador;
    }
    
    private void cargarInformacionEstadosProyecto(){
        ListaEstadoProyectoRespuesta respuesta = ProyectoDAO.obtenerEstadosProyecto(idResponsable);
        switch(respuesta.getCodigoRespuesta()) {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexion con la base de datos",
                        "Error. No se pudo conectar con la base de datos. Inténtelo de nuevo o hágalo más tarde.",
                        Alert.AlertType.WARNING);
                Utilidades.cerrarSesionDesconexion((Stage)tfNombre.getScene().getWindow());
                break;                
             case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos",
                        "Hubo un error al cargar la información, por favor intentelo mas tarde",
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                estadosProyecto.addAll(respuesta.getEstadosProyecto());
                cbEstado.setItems(estadosProyecto);
            break;
        }   
    }
    
    private void cargarInformacionResponsables(){
        ListaResponsableProyectoRespuesta respuesta = ResponsableProyectoDAO.obtenerResponsables(idResponsable);
        switch(respuesta.getCodigoRespuesta()) {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexion con la base de datos",
                        "Error. No se pudo conectar con la base de datos. Inténtelo de nuevo o hágalo más tarde.",
                        Alert.AlertType.WARNING);
                Utilidades.cerrarSesionDesconexion((Stage)tfNombre.getScene().getWindow());
                break;                
             case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos",
                        "Hubo un error al cargar la información, por favor intentelo mas tarde",
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                responsables.addAll(respuesta.getResponsablesProyecto());
                cbResponsable.setItems(responsables);
            break;
        }   
    }
    
    private void establecerEstiloNormal(){
        tfNombre.setStyle(Constantes.ESTILO_NORMAL);
        dpFechaInicio.setStyle(Constantes.ESTILO_NORMAL);
        dpFechaFin.setStyle(Constantes.ESTILO_NORMAL);
        cbEstado.setStyle(Constantes.ESTILO_NORMAL);
        cbResponsable.setStyle(Constantes.ESTILO_NORMAL);
        taDescripcion.setStyle(Constantes.ESTILO_NORMAL);
    }

    @FXML
    private void clicRegresar(MouseEvent event) {
        cerrarVentana();
    }
    
    private void cerrarVentana(){
        Stage escenarioActual = (Stage) lblTituloVista.getScene().getWindow();
        escenarioActual.close();
    }

    @FXML
    private void btnCrearProyecto(ActionEvent event) {
        if(validarCampos()){
            registrarProyecto();
        }
    }
    
    private boolean validarCampos(){
        establecerEstiloNormal();
        datosValidos = true;
        alertaMostrada = false;
        String nombre = tfNombre.getText();
        LocalDate fechaInicio = dpFechaInicio.getValue();
        LocalDate fechaFin = dpFechaFin.getValue();
        int posicionEstado = cbEstado.getSelectionModel().getSelectedIndex();
        int posicionResponsable = cbResponsable.getSelectionModel().getSelectedIndex();
        String descripcion = taDescripcion.getText();
        if (nombre.isEmpty() || fechaInicio == null || fechaFin == null || posicionEstado == -1 || posicionResponsable == -1 || descripcion.isEmpty()) {
            if (nombre.isEmpty()) {
                tfNombre.setStyle(Constantes.ESTILO_ERROR);
                datosValidos = false;
                alertaMostrada = true;
            }
            if (fechaInicio == null) {
                dpFechaInicio.setStyle(Constantes.ESTILO_ERROR);
                datosValidos = false;
                alertaMostrada = true;
            }
            if (fechaFin == null) {
                dpFechaFin.setStyle(Constantes.ESTILO_ERROR);
                datosValidos = false;
                alertaMostrada = true;
            }
            if (posicionEstado == -1) {
                cbEstado.setStyle(Constantes.ESTILO_ERROR);
                datosValidos = false;
                alertaMostrada = true;
            }
            if (posicionResponsable == -1) {
                cbResponsable.setStyle(Constantes.ESTILO_ERROR);
                datosValidos = false;
                alertaMostrada = true;
            }
            if (descripcion.isEmpty()) {
                taDescripcion.setStyle(Constantes.ESTILO_ERROR);
                datosValidos = false;
                alertaMostrada = true;
            }
            Utilidades.mostrarDialogoSimple("CamposVacios", "Error. Hay campos vacíos. Complételos o cámbielos para continuar", Alert.AlertType.ERROR);
        } else if (!caracteresValidos(nombre) || !caracteresValidos(descripcion)) {
            if (!caracteresValidos(nombre)) {
                tfNombre.setStyle(Constantes.ESTILO_ERROR);
                datosValidos = false;
                alertaMostrada = true;
            }
            if (!caracteresValidos(descripcion)) {
                taDescripcion.setStyle(Constantes.ESTILO_ERROR);
                datosValidos = false;
                alertaMostrada = true;
            }
            Utilidades.mostrarDialogoSimple("CamposErroneos", "Error. Hay campos erróneos. Complételos o cámbielos para continuar.", Alert.AlertType.ERROR);
        } else{
            if (fechaInicio.isAfter(fechaFin)) {
                datosValidos = false;
                alertaMostrada = true;
                dpFechaInicio.setStyle(Constantes.ESTILO_ERROR);
                Utilidades.mostrarDialogoSimple("FechaErronea", "Error. La fecha ingresada no es válida. Ingrese una nueva fecha.", Alert.AlertType.ERROR);
            }
        }
        return datosValidos;
    }
    
    private void registrarProyecto() {
        Proyecto proyectoValidado = new Proyecto();
        proyectoValidado.setNombre(tfNombre.getText());
        proyectoValidado.setFechaInicio(dpFechaInicio.getValue().toString());
        proyectoValidado.setFechaFin(dpFechaFin.getValue().toString());
        proyectoValidado.setDescripcion(taDescripcion.getText());
        proyectoValidado.setNumDesarrolladoresMax(2);
        EstadoProyecto estadoAsignada = cbEstado.getSelectionModel().getSelectedItem();
        proyectoValidado.setIdEstadoProyecto(estadoAsignada.getIdEstadoProyecto());
        int responsableAsignado = cbResponsable.getSelectionModel().getSelectedItem().getIdResponsableProyecto();
        proyectoValidado.setIdResponsableProyecto(responsableAsignado);
        guardarProyecto(proyectoValidado);
        asignarProyecto(proyectoValidado);
    }

    private void guardarProyecto(Proyecto proyectoNuevo) {
        int codigoRespuesta = ProyectoDAO.guardarProyecto(proyectoNuevo);
        switch(codigoRespuesta) {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexión", 
                        "No se pudo conectar con la base de datos. Inténtelo de nuevo o hágalo más tarde.", 
                        Alert.AlertType.ERROR);
                Utilidades.cerrarSesionDesconexion((Stage)tfNombre.getScene().getWindow());
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al registrar los datos", 
                        "Inténtelo más tarde", 
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Creación de proyecto correcta", 
                        "Se ha creado el proyecto correctamente", 
                        Alert.AlertType.INFORMATION);        
                break;
        }
    }
    
    private void asignarProyecto(Proyecto proyectoNuevo) {
        int codigoRespuesta = ProyectoDAO.asignarProyecto(proyectoNuevo);
        switch(codigoRespuesta) {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexión", 
                        "No se pudo conectar con la base de datos. Inténtelo de nuevo o hágalo más tarde.", 
                        Alert.AlertType.ERROR);
                Utilidades.cerrarSesionDesconexion((Stage)tfNombre.getScene().getWindow());
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al registrar los datos", 
                        "Inténtelo más tarde", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Asignación de proyecto correcta", 
                        "Se ha asignado el proyecto correctamente", 
                        Alert.AlertType.INFORMATION);        
                observador.operacionExitosa("Registro", proyectoNuevo.getNombre());
                cerrarVentana();
                break;
            }
    }
    
}
