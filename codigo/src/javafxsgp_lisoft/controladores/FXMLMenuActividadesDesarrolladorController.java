/*
*Autor: Torres Osorio Alesis de Jesus
*Fecha de creación: 10/11/2023
*Fecha de modificación: 13/12/2023
*Descripción: Controlador de la vista del menú de actividades 
*/
package javafxsgp_lisoft.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
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

public class FXMLMenuActividadesDesarrolladorController implements Initializable {
    private Desarrollador usuarioDesarrollador;
    
    @FXML
    private Label lblTituloVista;    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    void inicializarInformacion(Desarrollador usuarioDesarrollador) {
        this.usuarioDesarrollador = usuarioDesarrollador;
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

    @FXML
    private void clcRegistrarActividad(MouseEvent event) {
        try {
            FXMLLoader loader = 
                    new FXMLLoader(JavaFXSGP_LISOFT.class.getResource("vistas/FXMLRegistrarActividad.fxml"));
            Parent vista = loader.load();
            Scene escena = new Scene(vista);
            FXMLRegistrarActividadController controler = loader.getController();
            controler.inicializarInformacion(usuarioDesarrollador.getIdUsuario());
            
            Stage stageActual = new Stage();
            stageActual.setScene(escena);
            stageActual.setTitle("Registrar actividad");
            stageActual.initModality(Modality.APPLICATION_MODAL);
            stageActual.showAndWait();         
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
