/*
*Autor: Torres Osorio Alesis de Jesus
*Fecha de creación: 10/11/2023
*Fecha de modificación: 17/11/2023
*Descripción: Controlador de la vista del registro del cambio
*/
package javafxsgp_lisoft.controladores;

import javafxsgp_lisoft.modelo.dao.CatalogoDAO;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafxsgp_lisoft.modelo.dao.CambioDAO;
import javafxsgp_lisoft.modelo.dao.DefectoDAO;
import javafxsgp_lisoft.modelo.dao.SolicitudCambioDAO;
import javafxsgp_lisoft.modelo.pojo.Desarrollador;
import javafxsgp_lisoft.modelo.pojo.SolicitudCambio;
import javafxsgp_lisoft.modelo.pojo.TipoCambio;
import javafxsgp_lisoft.modelo.pojo.Cambio;
import javafxsgp_lisoft.respuesta.ListaSolicitudCambioRespuesta;
import javafxsgp_lisoft.respuesta.ListaTipoCambioRespuesta;
import javafxsgp_lisoft.respuesta.RespuestaDefecto;
import javafxsgp_lisoft.utils.Constantes;
import javafxsgp_lisoft.utils.Utilidades;


public class FXMLRegistrarCambioController implements Initializable {
    
    private Desarrollador desarrolladorActivo;
    private boolean camposValidos;
    private ObservableList<SolicitudCambio> solicitudesCambio;
    private ObservableList<TipoCambio> tiposCambio;

    private Cambio cambioRegistro = new Cambio();
    private SolicitudCambio solicitudSeleccionada = new SolicitudCambio();
   
    private String mensajeCamposInvalidos;
    
    @FXML
    private TextField tfNombreCambio;
    @FXML
    private TextArea taDescripcion;
    @FXML
    private ComboBox<TipoCambio> cbTipo;
    @FXML
    private TextField tfEsfuerzoEstimado;
    @FXML
    private ComboBox<SolicitudCambio> cbSolicitudes;
    @FXML
    private ImageView ivRegresar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        solicitudesCambio = FXCollections.observableArrayList();
        tiposCambio = FXCollections.observableArrayList();
        cargarComboSolicitudes();
        configurarListenerSolicitud();
        cargarComboTiposCambio();
        
    }    

    public void inicializarInformacion(Desarrollador desarrolladorActivo){
        this.desarrolladorActivo = desarrolladorActivo;
    }
    private void cargarComboSolicitudes() {
        List<SolicitudCambio> resultado = new ArrayList<>();
        SolicitudCambio solicitudNula = new SolicitudCambio();
        solicitudNula.setNombre("Ninguna");
        resultado.add(solicitudNula);
        ListaSolicitudCambioRespuesta respuesta = SolicitudCambioDAO.obtenerSolicitudesPendientes();
        switch (respuesta.getCodigoRespuesta()) {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexion con la base de datos",
                        "Error. No se pudo conectar con la base de datos. Inténtelo de nuevo o hágalo más tarde.",
                        Alert.AlertType.WARNING);
                Utilidades.cerrarSesionDesconexion((Stage)cbSolicitudes.getScene().getWindow());
                break;
                
             case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos",
                        "Hubo un error al cargar la información, por favor intentelo mas tarde",
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                resultado.addAll(respuesta.getSolicitudesCambio());
                solicitudesCambio.addAll(resultado);
                cbSolicitudes.setItems(solicitudesCambio);
                break;
        }    
    }
    private void cargarComboTiposCambio() {
        ListaTipoCambioRespuesta respuesta = CatalogoDAO.obtenerTiposCambio();
        switch (respuesta.getCodigoRespuesta()) {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexion con la base de datos",
                        "Error. No se pudo conectar con la base de datos. Inténtelo de nuevo o hágalo más tarde.",
                        Alert.AlertType.WARNING);
                Utilidades.cerrarSesionDesconexion((Stage)cbSolicitudes.getScene().getWindow());
                break;
                
             case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos",
                        "Hubo un error al cargar la información, por favor intentelo mas tarde",
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                tiposCambio.addAll(respuesta.getTiposCambio());
                cbTipo.setItems(tiposCambio);
                break;
        }
        
    }
    
    private void configurarListenerSolicitud(){
        cbSolicitudes.valueProperty().addListener(new ChangeListener<SolicitudCambio>() {
            @Override
            public void changed(ObservableValue<? extends SolicitudCambio> observable,
                    SolicitudCambio oldValue, SolicitudCambio newValue) {
                if(!newValue.getNombre().equals("Ninguna") && newValue != null){
                    cargarInformacionSolicitud(newValue);
                    solicitudSeleccionada = newValue;
                }else{
                    tfNombreCambio.setEditable(true);
                    taDescripcion.setEditable(true);
                    tfNombreCambio.setText("");
                    cbTipo.getSelectionModel().select(-1);
                    tfEsfuerzoEstimado.setText("");
                    taDescripcion.setText("");
                    solicitudSeleccionada = null;
                }

            }
        });
    }
    
    private void cargarInformacionSolicitud(SolicitudCambio solicitudCambio){
        tfNombreCambio.setText(solicitudCambio.getNombre());
        tfNombreCambio.setEditable(false);
        taDescripcion.setText(solicitudCambio.getDescripcion());
        taDescripcion.setEditable(false);
        
        if(Integer.valueOf(solicitudCambio.getIdDefecto()) != null){
            int idDefecto = solicitudCambio.getIdDefecto();
            RespuestaDefecto respuesta = DefectoDAO.obtenerDetallesDefecto(idDefecto);
            switch (respuesta.getCodigoRespuesta()) {
                case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexion con la base de datos",
                        "Error. No se pudo conectar con la base de datos. Inténtelo de nuevo o hágalo más tarde.",
                        Alert.AlertType.WARNING);
                Utilidades.cerrarSesionDesconexion((Stage)cbSolicitudes.getScene().getWindow());
                break;
                
             case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos",
                        "Hubo un error al cargar la información, por favor intentelo mas tarde",
                        Alert.AlertType.WARNING);
                break;
                case Constantes.OPERACION_EXITOSA:
                    int posicionTipo = obtenerPosicionTipo(respuesta.getDefecto().getIdTipoDefecto());
                    cbTipo.getSelectionModel().select(posicionTipo);
                    tfEsfuerzoEstimado.setText(Integer.toString(respuesta.getDefecto().getEsfuerzo()));
                    break;
            }  
        }        
    }

    private boolean validarCampos() {
        camposValidos = true;
        mensajeCamposInvalidos = "Por favor, verifique los siguientes campos";
        int posicionTipoCambio = cbTipo.getSelectionModel().getSelectedIndex();
        String nombre = tfNombreCambio.getText();
        String esfuerzo = (tfEsfuerzoEstimado.getText());
        String descripcion = taDescripcion.getText();
        Pattern formatoNumero = Pattern.compile(Constantes.FORMATO_NUMERICO);
        Matcher matcherEsfuerzo = formatoNumero.matcher(esfuerzo);
        Pattern formatoEspanol = Pattern.compile(Constantes.FORMATO_ESPANOL);
        Matcher matcherLenguajeNombre = formatoEspanol.matcher(nombre);
        Matcher matcherLenguajeDescripcion = formatoEspanol.matcher(descripcion);
        if(posicionTipoCambio == -1){
            cbTipo.setStyle(Constantes.ESTILO_ERROR);
            camposValidos = false;
            mensajeCamposInvalidos+= "\n -Tipo de cambio";
        }
        if(nombre.isEmpty()|| !matcherLenguajeNombre.matches()){
            tfNombreCambio.setStyle(Constantes.ESTILO_ERROR);
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

    private void registroCambio() {
        establecerCambio();
        int codigoRespuesta = CambioDAO.registrarCambio(cambioRegistro);
        switch (codigoRespuesta) {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion",
                        "Error en la conexión con la base de datos.",
                        Alert.AlertType.ERROR);
               Utilidades.cerrarSesionDesconexion((Stage)tfNombreCambio.getScene().getWindow());
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error en registro",
                        "No sé pudo realizar el registro del cambio.",
                        Alert.AlertType.INFORMATION);
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Cambio registrado", 
                        "La informacion de cambio fue registrada correctamente",
                        Alert.AlertType.INFORMATION);
                regresarPantalla();
                break;
        }
        
    }

    private int obtenerPosicionTipo(int idTipoDefecto) {
       for (int i = 0; i < tiposCambio.size(); i++) {
            if (tiposCambio.get(i).getIdTipoCambio() == idTipoDefecto) {
                return i;
            }
        }
        return 0;
    }
    
    private void establecerCambio(){
        cambioRegistro.setNombre(tfNombreCambio.getText());
        cambioRegistro.setTipo(cbTipo.getSelectionModel().getSelectedItem().getNombre());
        cambioRegistro.setIdTipoCambio(cbTipo.getSelectionModel().getSelectedItem().getIdTipoCambio());
        cambioRegistro.setEsfuerzo(Integer.valueOf(tfEsfuerzoEstimado.getText()));
        cambioRegistro.setDescripcion(taDescripcion.getText());
        cambioRegistro.setIdProyecto(desarrolladorActivo.getIdProyecto());
        cambioRegistro.setIdDesarrollador(desarrolladorActivo.getIdUsuario());
        cambioRegistro.setFechaCreacion(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        cambioRegistro.setIdEstadoCambio(2);
        if(Integer.valueOf(solicitudSeleccionada.getIdSolicitudCambio()) != null){
            cambioRegistro.setIdSolicitudCambio(solicitudSeleccionada.getIdSolicitudCambio());
            if(Integer.valueOf(solicitudSeleccionada.getIdDefecto()) != null){
                cambioRegistro.setIdDefecto(solicitudSeleccionada.getIdDefecto());
            }
        }
    }

    
    private void regresarPantalla() {
        Stage escenarioActual = (Stage) taDescripcion.getScene().getWindow();
        escenarioActual.close();
    }

    @FXML
    private void mcNombreCambio(MouseEvent event) {
        tfNombreCambio.setStyle("");
    }

    @FXML
    private void mcDescripcion(MouseEvent event) {
        taDescripcion.setStyle("");
    }

    @FXML
    private void mcTipoCambio(MouseEvent event) {
        cbTipo.setStyle("");
    }

    @FXML
    private void mcEsfuerzo(MouseEvent event) {
        tfEsfuerzoEstimado.setStyle("");
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
    private void btnRegistrarCambio(ActionEvent event) {
        if(validarCampos()){
            registroCambio();
        }else{
            Utilidades.mostrarDialogoSimple("Campos erroneos", mensajeCamposInvalidos, Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void btnCancelar(ActionEvent event) {
        boolean respuestaDialogo = Utilidades.mostrarDialogoConfirmacion("Regresar pantalla", 
                "Está seguro que desea regresar? "
                + "Toda la información ingresada será eliminada permanentemente");
        if(respuestaDialogo)
            regresarPantalla();
    }

}
    