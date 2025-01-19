/*
*Autor: Torres Osorio Alesis de Jesus
*Fecha de creación: 20/11/2023
*Fecha de modificación: 20/11/2023
*Descripción: Controlador de la vista del inicio de sesión
*/
package javafxsgp_lisoft.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafxsgp_lisoft.modelo.dao.UsuarioDAO;
import javafxsgp_lisoft.modelo.pojo.Desarrollador;
import javafxsgp_lisoft.modelo.pojo.ResponsableProyecto;
import javafxsgp_lisoft.respuesta.RespuestaDesarrollador;
import javafxsgp_lisoft.respuesta.RespuestaResponsable;
import javafxsgp_lisoft.utils.Constantes;
import javafxsgp_lisoft.utils.Utilidades;

public class FXMLLoginController implements Initializable {
    private boolean camposAceptados = false;
    private boolean nulos = false;
    private String tipoLogin;
    
    @FXML
    private Label lbConsultaVacia;
    @FXML
    private Label lbErrorFormato;
    @FXML
    private Label lbContrasenaVacia;
    @FXML
    private Label lbUsuarioVacio;
    @FXML
    private Label lbIdentificador;
    @FXML
    private TextField tfUsuario;
    @FXML
    private PasswordField pfContrasena;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void ingresarTextoUsuario(KeyEvent event) {
        tfUsuario.setStyle(Constantes.ESTILO_NORMAL);
        lbErrorFormato.setVisible(false);
        lbUsuarioVacio.setVisible(false);
    }

    @FXML
    private void clicBtnIniciarSesion(ActionEvent event) {
        validarCampos();
    }

    @FXML
    private void ingresarTextoContrasena(KeyEvent event) {
        pfContrasena.setStyle(Constantes.ESTILO_NORMAL);
        lbContrasenaVacia.setVisible(false);
    }
    
    private void validarFormato(String usuario) {
        tipoLogin = "Invalido";
        Pattern patronResponsable = Pattern.compile("\\d{5}");
        Pattern patronDesarrollador = Pattern.compile("[sS]\\d{8}");
        Matcher usuarioResponsable = patronResponsable.matcher(usuario);
        Matcher usuarioDesarrollador = patronDesarrollador.matcher(usuario);
        if(usuarioResponsable.matches()){
            tipoLogin ="Responsable";
        }else{
            if(usuarioDesarrollador.matches()){
                tipoLogin ="Desarrollador";
            }
            else{
            tipoLogin = "Invalido";
            }
        }
    }
    
    private void ocultarEstilosUsuario(){
        tfUsuario.setStyle(Constantes.ESTILO_NORMAL);
        lbUsuarioVacio.setVisible(false);
        lbConsultaVacia.setVisible(false);
        lbErrorFormato.setVisible(false);
    }
    private void ocultarEstilosContrasena(){
        pfContrasena.setStyle(Constantes.ESTILO_NORMAL);
        lbContrasenaVacia.setVisible(false);
    }
     private void validarCampos() {
        camposAceptados = false;
        nulos = false;
        if(pfContrasena.getText().isEmpty()) {
            lbContrasenaVacia.setVisible(true);
            pfContrasena.setStyle(Constantes.ESTILO_ERROR);
            nulos = true;
        }
        if(tfUsuario.getText().isEmpty()) {
            lbUsuarioVacio.setVisible(true);
            tfUsuario.setStyle(Constantes.ESTILO_ERROR);
            nulos = true;
        }
        if(!nulos){
            validarFormato(tfUsuario.getText());
            if(tipoLogin == "Invalido"){
                Utilidades.mostrarDialogoSimple("Formato incorrecto", 
                        "Ingrese un formato valido para login. (ej. s21000000 para Desarrollador y 12345 para Responsable de proyecto", 
                        AlertType.WARNING);
                camposError();
            }else{
                    accesoCuenta(tfUsuario.getText().toLowerCase(),pfContrasena.getText());
            }    
        }
    }


    private void accesoCuenta(String matricula, String contrasena) {
        
        if(tipoLogin=="Desarrollador"){
            RespuestaDesarrollador usuarioAutenticado = UsuarioDAO.IniciarSesionDesarrollador(matricula, contrasena);
            if(!usuarioAutenticado.isError()){
                Desarrollador desarrolladorLogin = usuarioAutenticado.getDesarrollador();                
                Utilidades.mostrarDialogoSimple("Login de Desarrollador ", 
                        "Bienvenido "+desarrolladorLogin.getNombre() + " " + desarrolladorLogin.getApellidoPaterno()+ " "+desarrolladorLogin.getApellidoMaterno(), AlertType.INFORMATION);        
                cargarInicioDesarrollador(usuarioAutenticado.getDesarrollador());
            }else{
               Utilidades.mostrarDialogoSimple("Error al iniciar sesion", usuarioAutenticado.getMensaje(), AlertType.ERROR);
               camposError();
            }
        }else{
            RespuestaResponsable usuarioAutenticado = UsuarioDAO.IniciarSesionResponsable(matricula, contrasena);
            if(!usuarioAutenticado.isError()){
                ResponsableProyecto responsableLogin = usuarioAutenticado.getResponsable();
                Utilidades.mostrarDialogoSimple("Login de Responsable ", 
                    "Bienvenido "+responsableLogin.getNombre() + " " + responsableLogin.getApellidoPaterno()+ " "+responsableLogin.getApellidoMaterno(), AlertType.INFORMATION); 
                cargarInicioResponsable(responsableLogin);
            }else{
                Utilidades.mostrarDialogoSimple("Error al iniciar sesion", usuarioAutenticado.getMensaje(), AlertType.ERROR);
                camposError();
            }
        }
    }

    private void cargarInicioDesarrollador(Desarrollador desarrolladorLogin){        
            try {
                Stage stageActual = (Stage) tfUsuario.getScene().getWindow();
                FXMLLoader loader = 
                        Utilidades.cargarVista("vistas/FXMLMenuPrincipalDesarrollador.fxml");
                Parent vista = loader.load();
                Scene escena = new Scene(vista);
                FXMLMenuPrincipalDesarrolladorController controler = loader.getController();
                controler.inicializarInformacion(desarrolladorLogin);
                stageActual.setScene(escena);
                stageActual.setTitle("Menú Principal");
                stageActual.show();

            } catch (IOException ex) {
                    ex.printStackTrace();
                }
    }
    
    private void cargarInicioResponsable(ResponsableProyecto usuarioAutenticado) {
        try {
            Stage stageActual = (Stage) tfUsuario.getScene().getWindow();
            FXMLLoader loader = Utilidades.cargarVista("vistas/FXMLMenuPrincipalResponsable.fxml");
            Parent vista = loader.load();
            Scene escena = new Scene(vista);
            FXMLMenuPrincipalResponsableController controler = loader.getController();
            controler.inicializarInformacionResponsable(usuarioAutenticado);
            stageActual.setScene(escena);
            stageActual.setTitle("Menú Principal");
            stageActual.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private void camposError(){
        tfUsuario.setStyle(Constantes.ESTILO_ERROR);
        pfContrasena.setStyle(Constantes.ESTILO_ERROR);
    }
}
    
   

