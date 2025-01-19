/*
*Autor: Torres Osorio Alesis de Jesus
*Fecha de creación: 10/11/2023
*Fecha de modificación: 17/11/2023
*Descripción: Controlador de la vista del registro del defecto
*/
package javafxsgp_lisoft.controladores;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafxsgp_lisoft.modelo.dao.CatalogoDAO;
import javafxsgp_lisoft.modelo.dao.DefectoDAO;
import javafxsgp_lisoft.modelo.pojo.Defecto;
import javafxsgp_lisoft.modelo.pojo.Desarrollador;
import javafxsgp_lisoft.modelo.pojo.TipoCambio;
import javafxsgp_lisoft.respuesta.ListaTipoCambioRespuesta;
import javafxsgp_lisoft.utils.Constantes;
import javafxsgp_lisoft.utils.Utilidades;

public class FXMLRegistrarDefectoController implements Initializable {
    private ObservableList<TipoCambio> tiposDefecto;
    private Defecto defectoRegistro = new Defecto();
    private boolean camposValidos;
    private Desarrollador desarrolladorActivo;
    private String mensajeCamposInvalidos;
    
    @FXML
    private TextField tfNombre;
    @FXML
    private TextArea taDescripcion;
    @FXML
    private ComboBox<TipoCambio> cbTipoDefecto;
    @FXML
    private TextField tfEsfuerzoEstimado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tiposDefecto = FXCollections.observableArrayList();
        cargarComboTiposDefecto();
        
    }
    public void inicializarInformacion(Desarrollador desarrolladorActivo){
        this.desarrolladorActivo = desarrolladorActivo;
    }
    private boolean validarCampos() {
        mensajeCamposInvalidos = "Por favor, verifique los siguientes campos";
        camposValidos = true;
        int posicionTipoDefecto = cbTipoDefecto.getSelectionModel().getSelectedIndex();
        String nombre = tfNombre.getText();
        String esfuerzo = (tfEsfuerzoEstimado.getText());
        String descripcion = taDescripcion.getText();
        Pattern formatoNumero = Pattern.compile(Constantes.FORMATO_NUMERICO);
        Matcher matcherEsfuerzo = formatoNumero.matcher(esfuerzo);
        Pattern formatoEspanol = Pattern.compile(Constantes.FORMATO_ESPANOL);
        Matcher matcherLenguajeNombre = formatoEspanol.matcher(nombre);
        Matcher matcherLenguajeDescripcion = formatoEspanol.matcher(descripcion);
        if(posicionTipoDefecto == -1){
            cbTipoDefecto.setStyle(Constantes.ESTILO_ERROR);
            camposValidos = false;
            mensajeCamposInvalidos+= "\n -Tipo de Defecto";
        }
        if(nombre.isEmpty()|| !matcherLenguajeNombre.matches()){
            tfNombre.setStyle(Constantes.ESTILO_ERROR);
            camposValidos = false;
            mensajeCamposInvalidos+= "\n -Nombre";
        }
        if(esfuerzo.isEmpty() || !matcherEsfuerzo.matches()){
            tfEsfuerzoEstimado.setStyle(Constantes.ESTILO_ERROR);
            camposValidos = false;
            mensajeCamposInvalidos+= "\n -Esfuerzo (introduzca valores númericos)";
        }
        if(descripcion.isEmpty()|| !matcherLenguajeDescripcion.matches()){
            taDescripcion.setStyle(Constantes.ESTILO_ERROR);
            camposValidos = false;
            mensajeCamposInvalidos+= "\n -Descripcion";
        }
        return camposValidos;
    }
    
    private void registroDefecto() {
        establecerDefecto();
        int codigoRespuesta = DefectoDAO.registrarDefecto(defectoRegistro);
        switch (codigoRespuesta) {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion",
                        "Error en la conexión con la base de datos.",
                        Alert.AlertType.ERROR);
                Utilidades.cerrarSesionDesconexion((Stage)tfNombre.getScene().getWindow());
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error en registro",
                        "No sé pudo realizar el registro del defecto.",
                        Alert.AlertType.INFORMATION);
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Defecto registrado", 
                        "La informacion de defecto fue registrada correctamente",
                        Alert.AlertType.INFORMATION);
                regresarPantalla();
                break;
        }
    }
    private void establecerDefecto(){
        defectoRegistro.setNombre(tfNombre.getText());
        defectoRegistro.setIdTipoDefecto(cbTipoDefecto.getSelectionModel().getSelectedItem().getIdTipoCambio());
        defectoRegistro.setEsfuerzo(Integer.valueOf(tfEsfuerzoEstimado.getText()));
        defectoRegistro.setDescripcion(taDescripcion.getText());
        defectoRegistro.setIdProyecto(desarrolladorActivo.getIdProyecto());
        defectoRegistro.setIdDesarrollador(desarrolladorActivo.getIdUsuario());
        defectoRegistro.setFechaDeteccion(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        defectoRegistro.setIdEstadoDefecto(2);//TODO definir constante 
    }
        
    

    @FXML
    private void btnRegistrarDefecto(ActionEvent event) {
        if(validarCampos()){
            registroDefecto();
        }else{
            Utilidades.mostrarDialogoSimple("Campos erroneos", mensajeCamposInvalidos, Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void clcRegresar(MouseEvent event) {
        boolean respuestaDialogo = Utilidades.mostrarDialogoConfirmacion("Cancelar registro de cambio", 
                "Está seguro que desea cancelar el registro del cambio? "
                + "Toda la información ingresada será eliminada permanentemente");
        if(respuestaDialogo)
            regresarPantalla();
    }

    @FXML
    private void btnCancelar(ActionEvent event) {
        boolean respuestaDialogo = Utilidades.mostrarDialogoConfirmacion("Regresar pantalla", 
                "Está seguro que desea regresar? "
                + "Toda la información ingresada será eliminada permanentemente");
        if(respuestaDialogo)
            regresarPantalla();
    }

    private void cargarComboTiposDefecto() {
        ListaTipoCambioRespuesta respuesta = CatalogoDAO.obtenerTiposCambio();
        switch (respuesta.getCodigoRespuesta()) {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexion con la base de datos",
                        "Error. No se pudo conectar con la base de datos. Inténtelo de nuevo o hágalo más tarde.",
                        Alert.AlertType.WARNING);
                Utilidades.cerrarSesionDesconexion((Stage)cbTipoDefecto.getScene().getWindow());
                break;
                
             case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos",
                        "Hubo un error al cargar la información, por favor intentelo mas tarde",
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                tiposDefecto.addAll(respuesta.getTiposCambio());
                cbTipoDefecto.setItems(tiposDefecto);
                break;
        }
    }


    private void regresarPantalla() {
        Stage escenarioActual = (Stage) taDescripcion.getScene().getWindow();
        escenarioActual.close();
    }

    @FXML
    private void mcNombre(MouseEvent event) {
        tfNombre.setStyle(Constantes.ESTILO_NORMAL);
    }

    @FXML
    private void mcDescripcion(MouseEvent event) {
        taDescripcion.setStyle(Constantes.ESTILO_NORMAL);
    }

    @FXML
    private void mcTipoDefecto(MouseEvent event) {
        cbTipoDefecto.setStyle(Constantes.ESTILO_NORMAL);
    }

    @FXML
    private void mcEsfuerzo(MouseEvent event) {
        tfEsfuerzoEstimado.setStyle(Constantes.ESTILO_NORMAL);
    }

}
