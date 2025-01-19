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
import javafx.stage.Stage;
import javafxsgp_lisoft.modelo.pojo.ResponsableProyecto;
import javafxsgp_lisoft.utils.Utilidades;

public class FXMLMenuCambiosResponsableController implements Initializable {
    private ResponsableProyecto usuarioResponsable;
    
    @FXML
    private Label lblTituloVista;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    void inicializarInformacion(ResponsableProyecto usuarioResponsable) {
        this.usuarioResponsable = usuarioResponsable;
    }
    @FXML
    private void clicRegresar(MouseEvent event) {
        try{
                Stage stageActual = (Stage) lblTituloVista.getScene().getWindow();
                FXMLLoader loader = Utilidades.cargarVista("vistas/FXMLMenuPrincipalResponsable.fxml");
                Parent vista = loader.load();
                Scene escena = new Scene(vista);
                FXMLMenuPrincipalResponsableController controler = loader.getController();
                controler.inicializarInformacionResponsable(usuarioResponsable);
                stageActual.setScene(escena);
                stageActual.setTitle("Menú Principal");
                stageActual.showAndWait();
            } catch (IOException ex) {
                Logger.getLogger(FXMLMenuPrincipalResponsableController.class.getName()).log(Level.SEVERE, null, ex);
            } 
    }

    @FXML
    private void clicAnalizarSolicitudCambio(MouseEvent event) {
        try{
                Stage stageActual = (Stage) lblTituloVista.getScene().getWindow();
                FXMLLoader loader = Utilidades.cargarVista("vistas/FXMLListaSolicitudesCambio.fxml");
                Parent vista = loader.load();
                Scene escena = new Scene(vista);
                FXMLListaSolicitudesCambioController controler = loader.getController();
                controler.inicializarInformacion(usuarioResponsable);
                stageActual.setScene(escena);
                stageActual.setTitle("Analizar solciitud de cambio");
                stageActual.showAndWait();
            } catch (IOException ex) {
                Logger.getLogger(FXMLMenuPrincipalResponsableController.class.getName()).log(Level.SEVERE, null, ex);
            } 
    }

    
}
