/*
*Autor: Martinez Caixba Miguel Angel
*Fecha de creación: 10/11/2023
*Fecha de modificación: 17/11/2023
*Descripción: Controlador de la vista del menú principal del desarrollador
*/
package javafxsgp_lisoft.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafxsgp_lisoft.JavaFXSGP_LISOFT;
import javafxsgp_lisoft.modelo.pojo.Desarrollador;

public class FXMLMenuPrincipalDesarrolladorController implements Initializable {
    //
    private Desarrollador usuarioDesarrollador;
    @FXML
    private Label lblTituloVista;    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void clicCerrarSesion(MouseEvent event) {
        Stage escenarioBase = (Stage)lblTituloVista.getScene().getWindow();
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSGP_LISOFT.class.getResource("vistas/FXMLLogin.fxml"));
            Parent vista = accesoControlador.load();
            escenarioBase.setScene(new Scene (vista));
            escenarioBase.setTitle("Inicio Sesión");
            escenarioBase.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    
    public void inicializarInformacion(Desarrollador usuarioDesarrollador){
        this.usuarioDesarrollador = usuarioDesarrollador;
    }
    
    @FXML
    private void clcMenuActividades(MouseEvent event) {
        Stage escenarioBase = (Stage)lblTituloVista.getScene().getWindow();
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSGP_LISOFT.class.getResource("vistas/FXMLMenuActividadesDesarrollador.fxml"));
            Parent vista = accesoControlador.load();
            FXMLMenuActividadesDesarrolladorController controler = accesoControlador.getController();
            controler.inicializarInformacion(usuarioDesarrollador);
            escenarioBase.setScene(new Scene (vista));
            escenarioBase.setTitle("Menu de Actividades");
            escenarioBase.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void clcMenuCambios(MouseEvent event) {
        Stage escenarioBase = (Stage)lblTituloVista.getScene().getWindow();
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSGP_LISOFT.class.getResource("vistas/FXMLMenuCambiosDesarrollador.fxml"));
            Parent vista = accesoControlador.load();
            FXMLMenuCambiosDesarrolladorController controler = accesoControlador.getController();
            controler.inicializarInformacion(usuarioDesarrollador);
            escenarioBase.setScene(new Scene (vista));
            escenarioBase.setTitle("Menu de Cambios");
            escenarioBase.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void clcMenuDefectos(MouseEvent event) {
        Stage escenarioBase = (Stage)lblTituloVista.getScene().getWindow();
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSGP_LISOFT.class.getResource("vistas/FXMLMenuDefectosDesarrollador.fxml"));
            Parent vista = accesoControlador.load();
            FXMLMenuDefectosDesarrolladorController controler = accesoControlador.getController();
            controler.inicializarInformacion(usuarioDesarrollador);
            escenarioBase.setScene(new Scene (vista));
            escenarioBase.setTitle("Menu de Defectos");
            escenarioBase.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void clcMenuBitacora(MouseEvent event) {
        Stage escenarioBase = (Stage)lblTituloVista.getScene().getWindow();
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSGP_LISOFT.class.getResource("vistas/FXMLConsultarListaBitacoras.fxml"));
            Parent vista = accesoControlador.load();
            FXMLConsultarListaBitacorasController controler = accesoControlador.getController();
            controler.inicializarInformacion(usuarioDesarrollador, null);
            escenarioBase.setScene(new Scene (vista));
            escenarioBase.setTitle("Lista de Bitacoras");
            escenarioBase.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}


