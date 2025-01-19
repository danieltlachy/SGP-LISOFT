/*
*Autor: Torres Osorio Alesis de Jesus
*Fecha de creación: 10/11/2023
*Fecha de modificación: 13/12/2023
*Descripción: Controlador de la vista del menú de cambios 
*/
package javafxsgp_lisoft.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafxsgp_lisoft.JavaFXSGP_LISOFT;
import javafxsgp_lisoft.modelo.pojo.Desarrollador;
import javafxsgp_lisoft.utils.Utilidades;


public class FXMLMenuCambiosDesarrolladorController implements Initializable {

    @FXML
    private Label lblTituloVista;
    private Desarrollador usuarioDesarrollador;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }  
    
    void inicializarInformacion(Desarrollador usuarioDesarrollador) {
            this.usuarioDesarrollador = usuarioDesarrollador;
    }
    

    @FXML
    private void clicCrearSolicitudCambio(MouseEvent event) {
        try {            
            FXMLLoader loader = 
                    new FXMLLoader(JavaFXSGP_LISOFT.class.getResource("vistas/FXMLFormularioSolicitudCambio.fxml"));
            Parent vista = loader.load();
            Scene escena = new Scene(vista);
            
            FXMLFormularioSolicitudCambioController controler = loader.getController();
            controler.inicializarInformacion(usuarioDesarrollador.getIdUsuario());
          
            Stage stageActual = new Stage();
            stageActual.setScene(escena); 
            stageActual.setTitle("Formulario Solicitud Cambio");
            stageActual.initModality(Modality.APPLICATION_MODAL);
            stageActual.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void clicRegistrarCambio(MouseEvent event) {
        try {            
            FXMLLoader loader = 
                    new FXMLLoader(JavaFXSGP_LISOFT.class.getResource("vistas/FXMLRegistrarCambio.fxml"));
            Parent vista = loader.load();
            Scene escena = new Scene(vista);
            
            FXMLRegistrarCambioController controler = loader.getController();
            controler.inicializarInformacion(usuarioDesarrollador);
            
            Stage stageActual = new Stage();
            stageActual.setScene(escena); 
            stageActual.setTitle("Registrar Cambio");
            stageActual.initModality(Modality.APPLICATION_MODAL);
            stageActual.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @FXML
    private void clicRegresar(MouseEvent event) {
        try{
                Stage stageActual = (Stage) lblTituloVista.getScene().getWindow();
                FXMLLoader loader = Utilidades.cargarVista("vistas/FXMLMenuPrincipalDesarrollador.fxml");
                Parent vista = loader.load();
                Scene escena = new Scene(vista);
                FXMLMenuPrincipalDesarrolladorController controler = loader.getController();
                controler.inicializarInformacion(usuarioDesarrollador);
                stageActual.setScene(escena);
                stageActual.setTitle("Menu principal");
                stageActual.showAndWait();
            } catch (IOException ex) {
                Logger.getLogger(FXMLMenuPrincipalResponsableController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}
