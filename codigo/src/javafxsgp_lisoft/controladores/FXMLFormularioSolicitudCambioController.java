/*
*Autor: Martinez Caixba Miguel Angel
*Fecha de creación: 28/11/2023
*Fecha de modificación: 13/12/2023
*Descripción: Controlador para crear el formulario de 
*la solicitud de cambio
*/
package javafxsgp_lisoft.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafxsgp_lisoft.modelo.dao.DefectoDAO;
import javafxsgp_lisoft.modelo.dao.SolicitudCambioDAO;
import javafxsgp_lisoft.modelo.pojo.Defecto;
import javafxsgp_lisoft.modelo.pojo.SolicitudCambio;
import javafxsgp_lisoft.respuesta.ListaDefectoRespuesta;
import javafxsgp_lisoft.respuesta.RespuestaExistencia;
import javafxsgp_lisoft.utils.Constantes;
import javafxsgp_lisoft.utils.Utilidades;

public class FXMLFormularioSolicitudCambioController implements Initializable {
    private int idDesarrollador;   
    private SolicitudCambio solicitudCambioEdicion;     
    private ObservableList<Defecto> defectos;
    
    @FXML
    private Label lbTitulo;
    @FXML
    private TextArea taImpactoCambio;
    @FXML
    private TextArea taDescripcionCambio;
    @FXML
    private TextArea taAccionPropuesta;
    @FXML
    private TextField tfNombreSolicitud;
    @FXML
    private TextArea taRazonCambio;
    @FXML
    private ComboBox<Defecto> cbDefecto;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        defectos = FXCollections.observableArrayList();
        cargarInformacionDefectos();        
        cbDefecto.valueProperty().addListener((observable, oldValue, newValue) -> {        
        }); 
    }    

    public void inicializarInformacion(int idDesarrollador){
        this.idDesarrollador = idDesarrollador;
    }
    
    private void cargarInformacionDefectos(){   
        ListaDefectoRespuesta respuesta = DefectoDAO.obtenerDefetosSolicitud();
        switch (respuesta.getCodigoRespuesta()) {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexion con la base de datos",
                        "Error. No se pudo conectar con la base de datos. Inténtelo de nuevo o hágalo más tarde.",
                        Alert.AlertType.WARNING);
                Utilidades.cerrarSesionDesconexion((Stage)lbTitulo.getScene().getWindow());
                break;                
             case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos",
                        "Hubo un error al cargar la información, por favor intentelo mas tarde",
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                defectos.addAll(respuesta.getDefectos());
                cbDefecto.setItems(defectos);
                break;
        }
    }
    
    @FXML
    private void btnEnviarSolicitud(ActionEvent event) {
        boolean datosValidos = validarCamposRegistro();
        if(datosValidos){
            SolicitudCambio solicitudCambioValidada = obtenerDatosSolicitudCambio();
        }
    }
    
    private SolicitudCambio obtenerDatosSolicitudCambio(){
        SolicitudCambio solicitudCambioValidada = new SolicitudCambio();
        solicitudCambioValidada.setNombre(tfNombreSolicitud.getText());
        solicitudCambioValidada.setDescripcion(taDescripcionCambio.getText());
        solicitudCambioValidada.setRazon(taRazonCambio.getText());
        solicitudCambioValidada.setImpacto(taImpactoCambio.getText());
        solicitudCambioValidada.setAccionPropuesta(taAccionPropuesta.getText());        
        return solicitudCambioValidada;        
    }
    
    private boolean validarCamposRegistro(){
        establecerEstiloNormal();
        boolean datosValidos = true;
        String nombreSolicitud = tfNombreSolicitud.getText();
        String descripcionCambio = taDescripcionCambio.getText();
        String razonCambio = taRazonCambio.getText();
        String impactoCambio = taImpactoCambio.getText();
        String accionPropuesta = taAccionPropuesta.getText();        
        if(nombreSolicitud.isEmpty()){
            tfNombreSolicitud.setStyle(Constantes.ESTILO_ERROR);
            datosValidos = false;
            
        }if(descripcionCambio.isEmpty()){
            taDescripcionCambio.setStyle(Constantes.ESTILO_ERROR);
            datosValidos = false;
            
        }if(razonCambio.isEmpty()){
            taRazonCambio.setStyle(Constantes.ESTILO_ERROR);
            datosValidos = false;
            
        }if(impactoCambio.isEmpty()){
            taImpactoCambio.setStyle(Constantes.ESTILO_ERROR);
            datosValidos = false;
            
        }if(accionPropuesta.isEmpty()){
            taAccionPropuesta.setStyle(Constantes.ESTILO_ERROR);
            datosValidos = false;
            
        }if(datosValidos){
            SolicitudCambio solicitudCambioValidada = new SolicitudCambio();
            solicitudCambioValidada.setNombre(nombreSolicitud);
            solicitudCambioValidada.setDescripcion(descripcionCambio);
            solicitudCambioValidada.setRazon(razonCambio);
            solicitudCambioValidada.setImpacto(impactoCambio);
            solicitudCambioValidada.setAccionPropuesta(accionPropuesta);
            RespuestaExistencia respuesta = SolicitudCambioDAO.existeSolicitudConNombre(solicitudCambioValidada.getNombre());
            switch(respuesta.getCodigoRespuesta()){
                case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Sin conexion con la base de datos",
                            "Error. No se pudo conectar con la base de datos. Inténtelo de nuevo o hágalo más tarde.",
                            Alert.AlertType.WARNING);
                    Utilidades.cerrarSesionDesconexion((Stage)lbTitulo.getScene().getWindow());
                    break;                
                case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error al cargar los datos",
                            "Hubo un error al cargar la información, por favor intentelo mas tarde",
                            Alert.AlertType.WARNING);
                    break;           
                case Constantes.OPERACION_EXITOSA:
                        if (!respuesta.isExistencia()) {
                                registrarSolicitudCambioConDefecto(solicitudCambioValidada);
                            } else {
                                Utilidades.mostrarDialogoSimple("Nombre duplicado",
                                        "Ya existe una solicitud de cambio con el mismo nombre. Por favor, elige otro nombre.",
                                        Alert.AlertType.WARNING);
                            }
                            if(!datosValidos){
                                Utilidades.mostrarDialogoSimple("Campos vacios",
                                        "Por favor llena los campos faltantes",
                                        Alert.AlertType.WARNING);
                            }  
                    break;
            }    
        }
        return datosValidos;
    }
    
    private void registrarSolicitudCambioConDefecto(SolicitudCambio solicitudCambioRegistro) {
        Defecto defectoSeleccionado = cbDefecto.getValue();

        if (defectoSeleccionado != null) {
            int idDefecto = defectoSeleccionado.getIdDefecto();
            int idDesarrolladorr = this.idDesarrollador;
            solicitudCambioRegistro.setIdDefecto(idDefecto);
            solicitudCambioRegistro.setIdDesarrollador(idDesarrolladorr);
            int codigoRespuesta = SolicitudCambioDAO.crearSolicitudCambio(
                    solicitudCambioRegistro, idDefecto, idDesarrolladorr);

                switch (codigoRespuesta) {
                    case Constantes.ERROR_CONEXION:
                        Utilidades.mostrarDialogoSimple("Sin conexión con la base de datos",
                                "Error. No se pudo conectar con la base de datos. Inténtelo de nuevo o hágalo más tarde.",
                                Alert.AlertType.ERROR);
                        Utilidades.cerrarSesionDesconexion((Stage)cbDefecto.getScene().getWindow());
                        break;

                    case Constantes.ERROR_CONSULTA:
                        Utilidades.mostrarDialogoSimple("Error en la información en registro de Solicitud de cambio",
                                "Ocurrio un error registrando la solicitud de cambio, por favor, intentelo mas tarde",
                                Alert.AlertType.WARNING);
                        break;

                    case Constantes.OPERACION_EXITOSA:
                        Utilidades.mostrarDialogoSimple("Solicitud de cambio registrada",
                                "La solicitud de cambio fue guardada correctamente",
                                Alert.AlertType.INFORMATION);
                        Stage escenarioPrincipal = (Stage) lbTitulo.getScene().getWindow();
                        escenarioPrincipal.close();
                        break;
                }
            
        } else {
            registrarSolicitudCambioSinDefecto(solicitudCambioRegistro);
        }

        
    }
    
    private void registrarSolicitudCambioSinDefecto(SolicitudCambio solicitudCambioRegistro) {
        int idDesarrolladorr = this.idDesarrollador;
        solicitudCambioRegistro.setIdDesarrollador(idDesarrolladorr);
        int codigoRespuesta = SolicitudCambioDAO.crearSolicitudCambioSinDefecto(
                solicitudCambioRegistro, idDesarrolladorr);
            switch (codigoRespuesta) {
                case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Sin conexión con la base de datos",
                            "Error. No se pudo conectar con la base de datos. Inténtelo de nuevo o hágalo más tarde.",
                            Alert.AlertType.ERROR);
                    Utilidades.cerrarSesionDesconexion((Stage)cbDefecto.getScene().getWindow());
                    break;

                case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error en la información en registro de Solicitud de cambio",
                                "Ocurrio un error registrando la solicitud de cambio, por favor, intentelo mas tarde",
                                Alert.AlertType.WARNING);
                        break;

                case Constantes.OPERACION_EXITOSA:
                    Utilidades.mostrarDialogoSimple("Solicitud de cambio registrada",
                            "La solicitud de cambio fue guardada correctamente",
                            Alert.AlertType.INFORMATION);
                    Stage escenarioPrincipal = (Stage) lbTitulo.getScene().getWindow();
                    escenarioPrincipal.close();
                    break;
            }
    }
 
    private void establecerEstiloNormal(){
        tfNombreSolicitud.setStyle(Constantes.ESTILO_NORMAL);
        taDescripcionCambio.setStyle(Constantes.ESTILO_NORMAL);
        taRazonCambio.setStyle(Constantes.ESTILO_NORMAL);
        taImpactoCambio.setStyle(Constantes.ESTILO_NORMAL);
        taAccionPropuesta.setStyle(Constantes.ESTILO_NORMAL);
    }

    @FXML
    private void clicBtnRegresar(MouseEvent event) {
        Stage escenarioPrincipal = (Stage) lbTitulo.getScene().getWindow();
        escenarioPrincipal.close();
    }
 
}
