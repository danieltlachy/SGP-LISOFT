/*
*Autor: Mongeote Tlachy Daniel
*Fecha de creación: 20/11/2023
*Fecha de modificación: 20/11/2023
*Descripción: Controlador de la vista para la creacion de desarrolladores.
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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafxsgp_lisoft.modelo.dao.DesarrolladorDAO;
import javafxsgp_lisoft.modelo.pojo.Desarrollador;
import javafxsgp_lisoft.modelo.pojo.Materia;
import javafxsgp_lisoft.modelo.pojo.Proyecto;
import javafxsgp_lisoft.observador.ObservadorDesarrollador;
import javafxsgp_lisoft.respuesta.ListaMateriaRespuesta;
import javafxsgp_lisoft.respuesta.ListaProyectoRespuesta;
import javafxsgp_lisoft.utils.Constantes;
import javafxsgp_lisoft.utils.Utilidades;
import static javafxsgp_lisoft.utils.Utilidades.caracteresValidos;
import static javafxsgp_lisoft.utils.Utilidades.correoValido;
import static javafxsgp_lisoft.utils.Utilidades.matriculaValida;

public class FXMLFormularioDesarrolladorController implements Initializable {

    private int idResponsable;
    boolean datosValidos;
    boolean alertaMostrada;
    private Desarrollador desarrolladorEdicion;
    private ObservableList<Materia> materias;
    private ObservableList<Proyecto> proyectos;
    private ObservadorDesarrollador observador;
    
    
    @FXML
    private Label lblTituloVista;
    @FXML
    private ComboBox<Materia> cbMateria;
    @FXML
    private ComboBox<Proyecto> cbProyecto;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfMatricula;
    @FXML
    private TextField tfApellidoPaterno;
    @FXML
    private TextField tfApellidoMaterno;
    @FXML
    private TextField tfCorreo;
    @FXML
    private TextField tfContrasenia;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       desarrolladorEdicion = new Desarrollador();
       materias = FXCollections.observableArrayList();
       cargarInformacionMaterias();
       proyectos = FXCollections.observableArrayList();
       cargarInformacionProyectos();
    }

    public void inicializarInformacion(ObservadorDesarrollador observador, Desarrollador desarrolladorEdicion){
        this.observador = observador;
        this.desarrolladorEdicion = desarrolladorEdicion;
        if(this.desarrolladorEdicion != null){
            cargarInformacionEdicion(this.desarrolladorEdicion);
        }
    }
    
    private void cargarInformacionMaterias(){
        
        ListaMateriaRespuesta respuesta = DesarrolladorDAO.obtenerMaterias(idResponsable);
        switch (respuesta.getCodigoRespuesta()) {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexion con la base de datos",
                        "Error. No se pudo conectar con la base de datos. Inténtelo de nuevo o hágalo más tarde.",
                        Alert.AlertType.WARNING);
                Utilidades.cerrarSesionDesconexion((Stage)tfContrasenia.getScene().getWindow());
                break;                
             case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos",
                        "Hubo un error al cargar la información, por favor intentelo mas tarde",
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                        materias.addAll(respuesta.getMaterias());
                        cbMateria.setItems(materias);
                break;
        }

    }
    
    private void cargarInformacionProyectos(){
       
        ListaProyectoRespuesta respuesta = DesarrolladorDAO.obtenerProyectos(idResponsable);
        switch (respuesta.getCodigoRespuesta()) {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexion con la base de datos",
                        "Error. No se pudo conectar con la base de datos. Inténtelo de nuevo o hágalo más tarde.",
                        Alert.AlertType.WARNING);
                Utilidades.cerrarSesionDesconexion((Stage)tfContrasenia.getScene().getWindow());
                break;                
             case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos",
                        "Hubo un error al cargar la información, por favor intentelo mas tarde",
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                    proyectos.addAll(respuesta.getProyectos());
                    cbProyecto.setItems(proyectos);
                break;
        }   
    }
    
    private void cargarInformacionEdicion(Desarrollador desarrolladorEdicion){
        lblTituloVista.setText("Modificar Desarrollador");
        tfNombre.setText(desarrolladorEdicion.getNombre());
        tfApellidoPaterno.setText(desarrolladorEdicion.getApellidoPaterno());
        tfApellidoMaterno.setText(desarrolladorEdicion.getApellidoMaterno());
        tfMatricula.setText(desarrolladorEdicion.getMatricula());
        tfCorreo.setText(desarrolladorEdicion.getCorreo());
        tfContrasenia.setText(desarrolladorEdicion.getContrasena());
        int posicionEE = buscarIdExperienciaEducativa(desarrolladorEdicion.getIdExperienciaEducativa());
        cbMateria.getSelectionModel().select(posicionEE);
        int posicionProyecto = buscarIdProyecto(desarrolladorEdicion.getIdProyecto());
        cbProyecto.getSelectionModel().select(posicionProyecto);
    }
    
    private int buscarIdExperienciaEducativa(int idExperienciaEducativa){
        for (int i = 0; i < materias.size(); i++) {
            if(materias.get(i).getIdExperienciaEducativa()== idExperienciaEducativa)
                return i;
        }
        return 0;
    }
    
    private int buscarIdProyecto(int idProyecto){
        for (int i = 0; i < proyectos.size(); i++) {
            if(proyectos.get(i).getIdProyecto()== idProyecto)
                return i;
        }
        return 0;
    }
    
    private void establecerEstiloNormal(){
        tfNombre.setStyle(Constantes.ESTILO_NORMAL);
        tfApellidoPaterno.setStyle(Constantes.ESTILO_NORMAL);
        tfApellidoMaterno.setStyle(Constantes.ESTILO_NORMAL);
        tfMatricula.setStyle(Constantes.ESTILO_NORMAL);
        tfCorreo.setStyle(Constantes.ESTILO_NORMAL);
        tfContrasenia.setStyle(Constantes.ESTILO_NORMAL);
        cbMateria.setStyle(Constantes.ESTILO_NORMAL);
        cbProyecto.setStyle(Constantes.ESTILO_NORMAL);
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
    private void clicCrearDesarrollador(ActionEvent event) {
        if(validarCampos()){
            if(desarrolladorEdicion == null){
                registrarDesarrollador();
            }else{
                editarDesarrollador();
            }
        }
    }
    
    private boolean validarCampos() {
        establecerEstiloNormal();
        datosValidos = true;
        alertaMostrada = false;
        String nombre = tfNombre.getText();
        String apellidoPaterno = tfApellidoPaterno.getText();
        String apellidoMaterno = tfApellidoMaterno.getText();
        String matricula = tfMatricula.getText();
        String correo = tfCorreo.getText();
        String contrasenia = tfContrasenia.getText();
        int posicionMateria = cbMateria.getSelectionModel().getSelectedIndex();
        int posicionProyecto = cbProyecto.getSelectionModel().getSelectedIndex();
        if (nombre.isEmpty() || apellidoPaterno.isEmpty() || apellidoMaterno.isEmpty() || matricula.isEmpty() || correo.isEmpty() || contrasenia.isEmpty() || posicionMateria == -1 || posicionProyecto == -1) {
            if (nombre.isEmpty()) {
                tfNombre.setStyle(Constantes.ESTILO_ERROR);
                datosValidos = false;
                alertaMostrada = true;
            }
            if (apellidoPaterno.isEmpty()) {
                tfApellidoPaterno.setStyle(Constantes.ESTILO_ERROR);
                datosValidos = false;
                alertaMostrada = true;
            }
            if (apellidoMaterno.isEmpty()) {
                tfApellidoMaterno.setStyle(Constantes.ESTILO_ERROR);
                datosValidos = false;
                alertaMostrada = true;
            }
            if (matricula.isEmpty()) {
                tfMatricula.setStyle(Constantes.ESTILO_ERROR);
                datosValidos = false;
                alertaMostrada = true;
            }
            if (correo.isEmpty()) {
                tfCorreo.setStyle(Constantes.ESTILO_ERROR);
                datosValidos = false;
                alertaMostrada = true;
            }
            if (contrasenia.isEmpty()) {
                tfContrasenia.setStyle(Constantes.ESTILO_ERROR);
                datosValidos = false;
                alertaMostrada = true;
            }
            if (posicionMateria == -1) {
                cbMateria.setStyle(Constantes.ESTILO_ERROR);
                datosValidos = false;
                alertaMostrada = true;
            }
            if (posicionProyecto == -1) {
                cbProyecto.setStyle(Constantes.ESTILO_ERROR);
                datosValidos = false;
                alertaMostrada = true;
            }
            Utilidades.mostrarDialogoSimple("CamposVacios", "Error. Hay campos vacíos. Complételos o cámbielos para continuar", Alert.AlertType.ERROR);
        } else if (!caracteresValidos(nombre) || !caracteresValidos(apellidoPaterno) || !caracteresValidos(apellidoMaterno) || !matriculaValida(matricula) || !correoValido(correo)) {
            if (!caracteresValidos(nombre)) {
                tfNombre.setStyle(Constantes.ESTILO_ERROR);
                datosValidos = false;
                alertaMostrada = true;
            }
            if (!caracteresValidos(apellidoPaterno)) {
                tfApellidoPaterno.setStyle(Constantes.ESTILO_ERROR);
                datosValidos = false;
                alertaMostrada = true;
            }
            if (!caracteresValidos(apellidoMaterno)) {
                tfApellidoMaterno.setStyle(Constantes.ESTILO_ERROR);
                datosValidos = false;
                alertaMostrada = true;
            }
            if (!matriculaValida(matricula)) {
                tfMatricula.setStyle(Constantes.ESTILO_ERROR);
                datosValidos = false;
                alertaMostrada = true;
            }
            if (!correoValido(correo)) {
                tfCorreo.setStyle(Constantes.ESTILO_ERROR);
                datosValidos = false;
                alertaMostrada = true;
            }
            Utilidades.mostrarDialogoSimple("CamposErroneos", "Error. Hay campos erróneos. Complételos o cámbielos para continuar.", Alert.AlertType.ERROR);
        }
        return datosValidos;
    }

    private void registrarDesarrollador(){
        Desarrollador desarrolladorValidado = new Desarrollador();
        desarrolladorValidado.setNombre(tfNombre.getText());
        desarrolladorValidado.setApellidoPaterno(tfApellidoPaterno.getText());
        desarrolladorValidado.setApellidoMaterno(tfApellidoMaterno.getText());
        desarrolladorValidado.setMatricula(tfMatricula.getText());
        desarrolladorValidado.setCorreo(tfCorreo.getText());
        desarrolladorValidado.setContrasena(tfContrasenia.getText());
        desarrolladorValidado.setIdExperienciaEducativa(cbMateria.getSelectionModel().getSelectedItem().getIdExperienciaEducativa());
        desarrolladorValidado.setIdProyecto(cbProyecto.getSelectionModel().getSelectedItem().getIdProyecto());
        guardarDesarrollador(desarrolladorValidado);
    }
    
    private void editarDesarrollador(){
        Desarrollador desarrolladorValidadoEdicion = new Desarrollador();
        desarrolladorValidadoEdicion.setNombre(tfNombre.getText());
        desarrolladorValidadoEdicion.setApellidoPaterno(tfApellidoPaterno.getText());
        desarrolladorValidadoEdicion.setApellidoMaterno(tfApellidoMaterno.getText());
        desarrolladorValidadoEdicion.setMatricula(tfMatricula.getText());
        desarrolladorValidadoEdicion.setCorreo(tfCorreo.getText());
        desarrolladorValidadoEdicion.setContrasena(tfContrasenia.getText());
        desarrolladorValidadoEdicion.setIdExperienciaEducativa(cbMateria.getSelectionModel().getSelectedItem().getIdExperienciaEducativa());
        desarrolladorValidadoEdicion.setIdProyecto(cbProyecto.getSelectionModel().getSelectedItem().getIdProyecto());
        desarrolladorValidadoEdicion.setIdUsuario(desarrolladorEdicion.getIdUsuario());
        modificarDesarrollador(desarrolladorValidadoEdicion);
    }
    
    private void guardarDesarrollador(Desarrollador desarrolladorNuevo){
        int codigoRespuesta = DesarrolladorDAO.guardarDesarrollador(desarrolladorNuevo);
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexion", 
                        "No se pudo conectar con la base de datos. "
                      + "IntÃ©ntelo de nuevo o hÃ¡galo mÃ¡s tarde.", 
                        Alert.AlertType.ERROR);
                Utilidades.cerrarSesionDesconexion((Stage)tfContrasenia.getScene().getWindow());
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error registrar los datos", 
                        "Intentelo mas tarde", Alert.AlertType.WARNING);
            break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("CreacionDesarrolladorCorrecto", 
                        "Se ha creado el Desarrollador y se ha asociado a un proyecto correctamente",
                        Alert.AlertType.INFORMATION);
                observador.operacionExitosa("Registro", desarrolladorNuevo.getNombre());
                cerrarVentana();
            break;
        }
    }
    
    private void modificarDesarrollador(Desarrollador desarrolladorEditado){
        int codigoRespuesta = DesarrolladorDAO.modificarDesarrollador(desarrolladorEditado);
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
            Utilidades.mostrarDialogoSimple("Sin conexion", 
                    "No se pudo conectar con la base de datos. "
                  + "IntÃ©ntelo de nuevo o hÃ¡galo mÃ¡s tarde.",
                    Alert.AlertType.ERROR);
            Utilidades.cerrarSesionDesconexion((Stage)tfContrasenia.getScene().getWindow());    
            break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al modificar los datos", 
                        "Intentelo mas tarde", 
                        Alert.AlertType.WARNING);
            break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("ModificacionDesarrolladorCorrecto", 
                        "Se ha modificado el desarrollador correctamente" , 
                        Alert.AlertType.INFORMATION);
                observador.operacionExitosa("Registro", desarrolladorEditado.getNombre());
                cerrarVentana();
            break;
        }
    }
}
