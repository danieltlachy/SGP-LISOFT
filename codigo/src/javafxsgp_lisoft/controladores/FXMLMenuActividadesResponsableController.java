/*
*Autor: Mongeote Tlachy Daniel
*Fecha de creación: 10/11/2023
*Fecha de modificación: 17/11/2023
*Descripción: Controlador de la vista del menú de actividades 
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
import javafxsgp_lisoft.modelo.pojo.ResponsableProyecto;
import javafxsgp_lisoft.utils.Utilidades;

public class FXMLMenuActividadesResponsableController implements Initializable {

    private ResponsableProyecto usuarioResponsable;
    
    @FXML
    private Label lblTituloVista;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TODO
    }

    public void inicializarInformacion(ResponsableProyecto usuarioResponsable){
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
    
    private void irFormulario(ResponsableProyecto usuarioResponsable){
        try {
            FXMLLoader loader = Utilidades.cargarVista("vistas/FXMLFormularioActividad.fxml"); 
            Parent vista = loader.load();
            Scene escena = new Scene(vista);
            FXMLFormularioActividadController controler = loader.getController();
            Stage escenario = new Stage();
            escenario.setScene(escena);
            escenario.setTitle("Formulario Actividad");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void clcCrearActividad(MouseEvent event) {
        irFormulario(null);
    }

    @FXML
    private void clcAsignarActividad(MouseEvent event) {
        try {
            FXMLLoader loader = 
                    Utilidades.cargarVista("vistas/FXMLActividadesSinAsignar.fxml");
            Parent vista = loader.load();
            Stage escenarioFormulario = new Stage();
            escenarioFormulario.setScene(new Scene(vista));
            escenarioFormulario.setTitle("Asignar actividad");
            escenarioFormulario.initModality(Modality.APPLICATION_MODAL);
            escenarioFormulario.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void clcDesasignarActividad(MouseEvent event) {
        try {
            FXMLLoader loader = 
                    Utilidades.cargarVista("vistas/FXMLActividadesAsignadas.fxml");
            Parent vista = loader.load();
            Stage escenarioFormulario = new Stage();
            escenarioFormulario.setScene(new Scene(vista));
            escenarioFormulario.setTitle("Desasignar actividad");
            escenarioFormulario.initModality(Modality.APPLICATION_MODAL);
            escenarioFormulario.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
