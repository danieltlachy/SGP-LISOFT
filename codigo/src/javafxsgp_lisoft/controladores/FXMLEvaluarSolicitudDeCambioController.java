/*
*Autor: Martínez Caixba Miguel Ángel
*Fecha de creación: 10/11/2023
*Fecha de modificación: 17/11/2023
*Descripción: Controlador de la vista de la bitacora del desarrollador
*/
package javafxsgp_lisoft.controladores;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafxsgp_lisoft.modelo.dao.SolicitudCambioDAO;
import javafxsgp_lisoft.modelo.pojo.SolicitudCambio;
import javafxsgp_lisoft.modelo.pojo.SolicitudCambioRespuesta;
import javafxsgp_lisoft.respuesta.ListaSolicitudCambioRespuesta;
import javafxsgp_lisoft.utils.Constantes;
import javafxsgp_lisoft.utils.Utilidades;

/**
 *
 * @author Miguel Caixba
 */
public class FXMLEvaluarSolicitudDeCambioController implements Initializable{
private int idSolicitudCambio;
    boolean datosValidos = true;
    
    @FXML
    private Label lbNombreDesarrollador;
    @FXML
    private Label lbFechaSolicitud;
    @FXML
    private Label lbNombreSolicitud;
    @FXML
    private TextArea taImpactoCambio;
    @FXML
    private TextArea taDescripcionCambio;
    @FXML
    private TextArea taAccionPropuesta;
    @FXML
    private TextArea taRazonCambio;
    @FXML
    private Label lbNombreDefecto;    
    @FXML
    private Label lbtitulo;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }     
    
    public void inicializarInformacion(int idSolicitudCambio){
        this.idSolicitudCambio = idSolicitudCambio;  
        configurarCampos();
    }
    
    private void configurarCampos() {
        SolicitudCambioRespuesta solicitudCambioRespuesta = obtenerSolicitudCambioPorId(idSolicitudCambio);
        if (solicitudCambioRespuesta.getCodigoRespuesta() == Constantes.OPERACION_EXITOSA) {
            List<SolicitudCambio> solicitudesCambio = solicitudCambioRespuesta.getSolicitudesCambio();
            if (!solicitudesCambio.isEmpty()) {
                SolicitudCambio solicitudCambio = solicitudesCambio.get(0);
                String nombreCompleto = solicitudCambio.getNombreDesarrollador() + " " +
                                    solicitudCambio.getApellidoPaterno() + " " +
                                    solicitudCambio.getApellidoMaterno();
                lbNombreDefecto.setText(solicitudCambio.getNombreDefecto());
                lbNombreDesarrollador.setText(nombreCompleto);
                lbFechaSolicitud.setText(solicitudCambio.getFechaSolicitud());
                lbNombreSolicitud.setText(solicitudCambio.getNombre());
                taImpactoCambio.setText(solicitudCambio.getImpacto());
                taDescripcionCambio.setText(solicitudCambio.getDescripcion());
                taAccionPropuesta.setText(solicitudCambio.getAccionPropuesta());
                taRazonCambio.setText(solicitudCambio.getRazon());
                deshabilitarCampos();
            }
        } else {
            Utilidades.mostrarDialogoSimple("Error en la operación",
                    "Hubo un error en la operación. Por favor, inténtelo de nuevo.",
                    Alert.AlertType.ERROR);
        }
    }
    
    private void deshabilitarCampos(){
        lbNombreDefecto.setDisable(true);
        lbNombreDesarrollador.setDisable(true);
        lbFechaSolicitud.setDisable(true);
        lbNombreSolicitud.setDisable(true);
        taDescripcionCambio.setDisable(true);
        taRazonCambio.setDisable(true);

    }
    
    private SolicitudCambioRespuesta obtenerSolicitudCambioPorId(int idSolicitudCambio) { 
        SolicitudCambioRespuesta respuesta = SolicitudCambioDAO.obtSolicitudSeleccionada(idSolicitudCambio);
        switch (respuesta.getCodigoRespuesta()) {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexion con la base de datos",
                        "Error. No se pudo conectar con la base de datos. Inténtelo de nuevo o hágalo más tarde.",
                        Alert.AlertType.ERROR);
                Utilidades.cerrarSesionDesconexion((Stage)taDescripcionCambio.getScene().getWindow());
                break;                
             case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos",
                        "Hubo un error al cargar la información, por favor intentelo mas tarde",
                        Alert.AlertType.WARNING);
                break;                                
            case Constantes.OPERACION_EXITOSA:                
            break;
        }
        return respuesta;
    }
    
    @FXML
    private void btnAprobarSolicitud(ActionEvent event) {
        validarCamposRegistro();
        String impacto = taImpactoCambio.getText();
        String accionPropuesta = taAccionPropuesta.getText();
        if (datosValidos) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Preguntar aceptación solicitud");
            alert.setContentText("¿Estás seguro de aceptar esta solicitud?");
            ButtonType btnAceptar = new ButtonType("Aceptar");
            ButtonType btnCancelar = new ButtonType("Cancelar");            
            alert.getButtonTypes().setAll(btnAceptar, btnCancelar);

            ButtonType resultado = alert.showAndWait().get();
            if (resultado == btnAceptar) {
                ListaSolicitudCambioRespuesta respuesta = SolicitudCambioDAO.aprobarSolicitudCambio(idSolicitudCambio, impacto, accionPropuesta);
                switch (respuesta.getCodigoRespuesta()) {
                    case Constantes.ERROR_CONEXION:
                        Utilidades.mostrarDialogoSimple("Sin conexion con la base de datos",
                                "Error. No se pudo conectar con la base de datos. Inténtelo de nuevo o hágalo más tarde.",
                                Alert.AlertType.ERROR);
                        Utilidades.cerrarSesionDesconexion((Stage)taDescripcionCambio.getScene().getWindow());
                        break;                
                     case Constantes.ERROR_CONSULTA:
                        Utilidades.mostrarDialogoSimple("Error al cargar los datos",
                                "Hubo un error al cargar la información, por favor intentelo mas tarde",
                                Alert.AlertType.WARNING);
                        break;               
                    case Constantes.OPERACION_EXITOSA:
                        Utilidades.mostrarDialogoSimple("Solicitud aprobada", 
                                "Se ha aprobado la solicitud",
                                Alert.AlertType.INFORMATION);
                        Stage escenarioActual = (Stage) lbNombreDesarrollador.getScene().getWindow();
                        escenarioActual.close();
                        break;
                }
            }
        }
    }
    
    private void validarCamposRegistro(){
        establecerEstiloNormal();
        datosValidos = true;  
        String impacto = taImpactoCambio.getText();
        String accionPropuesta = taAccionPropuesta.getText();
        if(impacto.isEmpty()){
            taImpactoCambio.setStyle(Constantes.ESTILO_ERROR);
            datosValidos = false;          
            
        }if(accionPropuesta.isEmpty()){
            taAccionPropuesta.setStyle(Constantes.ESTILO_ERROR);
            datosValidos = false;         
            
        }if(!datosValidos){
            Utilidades.mostrarDialogoSimple("Campos vacios", 
                    "Por favor llena los campos faltantes", 
                    Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void btnRechazarSolicitud(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Rechazar solicitud");
        alert.setContentText("¿Seguro que quieres rechazar la solicitud de cambio?");
        ButtonType btnAceptar = new ButtonType("Aceptar");
        ButtonType btnCancelar = new ButtonType("Cancelar");
        alert.getButtonTypes().setAll(btnAceptar, btnCancelar);
        ButtonType resultado = alert.showAndWait().get();

        if (resultado == btnAceptar) {
            int respuestaBD = SolicitudCambioDAO.rechazarSolicitudCambio(idSolicitudCambio);
            switch(respuestaBD) {
                case Constantes.ERROR_CONEXION:
                        Utilidades.mostrarDialogoSimple("Sin conexion con la base de datos",
                                "Error. No se pudo conectar con la base de datos. Inténtelo de nuevo o hágalo más tarde.",
                                Alert.AlertType.ERROR);
                        Utilidades.cerrarSesionDesconexion((Stage)taDescripcionCambio.getScene().getWindow());
                        break;                
                     case Constantes.ERROR_CONSULTA:
                        Utilidades.mostrarDialogoSimple("Error al cargar los datos",
                                "Hubo un error al cargar la información, por favor intentelo mas tarde",
                                Alert.AlertType.WARNING);
                        break; 
                case Constantes.OPERACION_EXITOSA:
                    Utilidades.mostrarDialogoSimple("Solicitud rechazada", 
                        "La solicitud fue rechazada",
                        Alert.AlertType.INFORMATION);
                    Stage escenarioActual = (Stage) lbNombreDesarrollador.getScene().getWindow();
                    escenarioActual.close();
                break;
        }
        }        
    }
    
    private void establecerEstiloNormal(){
        taImpactoCambio.setStyle(Constantes.ESTILO_NORMAL);
        taAccionPropuesta.setStyle(Constantes.ESTILO_NORMAL);
    }

    @FXML
    private void clicBtnRegresar(MouseEvent event) {
        Stage escenarioPrincipal = (Stage) lbtitulo.getScene().getWindow();
        escenarioPrincipal.close();
    }
}
