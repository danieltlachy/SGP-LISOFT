/*
*Autor: Mongeote Tlachy Daniel
*Fecha de creación: 20/11/2023
*Fecha de modificación: 20/11/2023
*Descripción: Controlador de la vista para la creacion de actividades.
*/
package javafxsgp_lisoft.controladores;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafxsgp_lisoft.modelo.dao.ActividadDAO;
import javafxsgp_lisoft.modelo.pojo.Actividad;
import javafxsgp_lisoft.observador.ObservadorActividad;
import javafxsgp_lisoft.utils.Constantes;
import javafxsgp_lisoft.utils.Utilidades;
import static javafxsgp_lisoft.utils.Utilidades.caracteresValidos;

public class FXMLFormularioActividadController implements Initializable{

    private int idResponsable;
    private ObservadorActividad observador;
    boolean datosValidos;
    boolean alertaMostrada;
    
    @FXML
    private Label lblTituloVista;
    @FXML
    private TextField tfNombreActividad;
    @FXML
    private TextField tfEsfuerzoActividad;
    @FXML
    private DatePicker dpFechaInicio;
    @FXML
    private DatePicker dpFechaFin;
    @FXML
    private TextArea taDescripcion;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       dpFechaInicio.setEditable(false);
       dpFechaFin.setEditable(false);
    }
    
    private void establecerEstiloNormal(){
        tfNombreActividad.setStyle(Constantes.ESTILO_NORMAL);
        dpFechaInicio.setStyle(Constantes.ESTILO_NORMAL);
        dpFechaFin.setStyle(Constantes.ESTILO_NORMAL);
        tfEsfuerzoActividad.setStyle(Constantes.ESTILO_NORMAL);
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
    private void btnCrearActividad(ActionEvent event) {
        if(validarCampos()){
            registrarActividad();
        }
    }
    
    private boolean validarCampos(){
        establecerEstiloNormal();
        datosValidos = true;
        alertaMostrada = false;
        String nombre = tfNombreActividad.getText();
        LocalDate fechaInicio = dpFechaInicio.getValue();
        LocalDate fechaFin = dpFechaFin.getValue();
        String esfuerzo = (tfEsfuerzoActividad.getText());
        Pattern formatoNumero = Pattern.compile(Constantes.FORMATO_NUMERICO);
        Matcher matchEsfuerzo = formatoNumero.matcher(esfuerzo);
        String descripcion = taDescripcion.getText();
        if (nombre.isEmpty() || fechaInicio == null || fechaFin == null || esfuerzo.isEmpty() || !matchEsfuerzo.matches() || descripcion.isEmpty()) {
            if (nombre.isEmpty()) {
                tfNombreActividad.setStyle(Constantes.ESTILO_ERROR);
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
            if (esfuerzo.isEmpty() || !matchEsfuerzo.matches()) {
                tfEsfuerzoActividad.setStyle(Constantes.ESTILO_ERROR);
                datosValidos = false;
                alertaMostrada = true;
            }
            if (descripcion.isEmpty()) {
                taDescripcion.setStyle(Constantes.ESTILO_ERROR);
                datosValidos = false;
                alertaMostrada = true;
            }
            Utilidades.mostrarDialogoSimple("CamposVacios", "Error. Hay campos vacíos. "
                    + "Complételos o cámbielos para continuar", 
                    Alert.AlertType.ERROR);
        } else {
            if (!caracteresValidos(nombre) || !caracteresValidos(descripcion)) {
                if (!caracteresValidos(nombre)) {
                    tfNombreActividad.setStyle(Constantes.ESTILO_ERROR);
                    datosValidos = false;
                    alertaMostrada = true;
                }
                if (!caracteresValidos(descripcion)) {
                    taDescripcion.setStyle(Constantes.ESTILO_ERROR);
                    datosValidos = false;
                    alertaMostrada = true;
                }
                Utilidades.mostrarDialogoSimple("CamposErroneos", 
                        "Error. Hay campos erróneos. Complételos o cámbielos para continuar.", 
                        Alert.AlertType.ERROR);
            } else{
                if (fechaInicio.isAfter(fechaFin)) {
                    datosValidos = false;
                    alertaMostrada = true;
                    dpFechaInicio.setStyle(Constantes.ESTILO_ERROR);
                    Utilidades.mostrarDialogoSimple("FechaErronea", 
                            "Error. La fecha ingresada no es válida. Ingrese una nueva fecha.", 
                            Alert.AlertType.ERROR);
                }
            }
        }
        return datosValidos;
    }
    
    private void registrarActividad(){
        Actividad actividadValidada = new Actividad();
        actividadValidada.setNombre(tfNombreActividad.getText());
        actividadValidada.setFechaInicio(dpFechaInicio.getValue().toString());
        actividadValidada.setFechaFin(dpFechaFin.getValue().toString());
        actividadValidada.setEsfuerzo(Integer.parseInt(tfEsfuerzoActividad.getText()));
        actividadValidada.setDescripcion(taDescripcion.getText());
        actividadValidada.setIdProyecto(1);
        guardarActividad(actividadValidada);
    }
    
    private void guardarActividad(Actividad actividadNueva){
        int codigoRespuesta = ActividadDAO.guardarActividad(actividadNueva);
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexion", 
                        "No se pudo conectar con la base de datos. IntÃ©ntelo de nuevo o hÃ¡galo mÃ¡s tarde.", 
                        Alert.AlertType.ERROR);
                Utilidades.cerrarSesionDesconexion((Stage)tfNombreActividad.getScene().getWindow());
                break;             
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error enviar los datos", 
                        "Intentelo mas tarde", 
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("CreacionActividadCorrecto", 
                        "Se ha creado la actividad correctamente", 
                        Alert.AlertType.INFORMATION);        
                cerrarVentana();
            break;
        }
    }
}
