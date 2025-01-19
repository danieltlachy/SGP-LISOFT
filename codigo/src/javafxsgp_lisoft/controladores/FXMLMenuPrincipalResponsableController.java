/*
*Autor: Mongeote Tlachy Daniel
*Fecha de creación: 10/11/2023
*Fecha de modificación: 17/11/2023
*Descripción: Controlador de la vista del menú principal del responsable del proyecto
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
import javafxsgp_lisoft.modelo.pojo.ResponsableProyecto;

public class FXMLMenuPrincipalResponsableController implements Initializable {

    private ResponsableProyecto usuarioResponsable;
    @FXML
    private Label lblTituloVista;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
    
    public void inicializarInformacionResponsable(ResponsableProyecto usuarioResponsable){
        this.usuarioResponsable = usuarioResponsable;
    }

    @FXML
    private void clcMenuProyectos(MouseEvent event) {
        Stage escenarioBase = (Stage)lblTituloVista.getScene().getWindow();
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSGP_LISOFT.class.getResource("vistas/FXMLListaProyectos.fxml"));
            Parent vista = accesoControlador.load();
            FXMLListaProyectosController controler = accesoControlador.getController();
            controler.inicializarInformacion(usuarioResponsable);
            
            escenarioBase.setScene(new Scene (vista));
            escenarioBase.setTitle("Proyectos");
            escenarioBase.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void clcMenuActividades(MouseEvent event) {
        Stage escenarioBase = (Stage)lblTituloVista.getScene().getWindow();
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSGP_LISOFT.class.getResource("vistas/FXMLMenuActividadesResponsable.fxml"));
            Parent vista = accesoControlador.load();
            FXMLMenuActividadesResponsableController controler = accesoControlador.getController();
            controler.inicializarInformacion(usuarioResponsable);
            escenarioBase.setScene(new Scene (vista));
            escenarioBase.setTitle("Menu actividad");
            escenarioBase.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void clcMenuDesarrolladores(MouseEvent event) {
        Stage escenarioBase = (Stage)lblTituloVista.getScene().getWindow();
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSGP_LISOFT.class.getResource("vistas/FXMLListaDesarrolladores.fxml"));
            Parent vista = accesoControlador.load();
            FXMLListaDesarrolladoresController controler = accesoControlador.getController();
            controler.inicializarInformacion(usuarioResponsable);
            escenarioBase.setScene(new Scene (vista));
            escenarioBase.setTitle("Desarrolladores");
            escenarioBase.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void clcMenuCambios(MouseEvent event) {
        Stage escenarioBase = (Stage)lblTituloVista.getScene().getWindow();
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSGP_LISOFT.class.getResource("vistas/FXMLMenuCambiosResponsable.fxml"));
            Parent vista = accesoControlador.load();
            FXMLMenuCambiosResponsableController controler = accesoControlador.getController();
            controler.inicializarInformacion(usuarioResponsable);
            escenarioBase.setScene(new Scene (vista));
            escenarioBase.setTitle("Lista de cambios");
            escenarioBase.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void clcMenuDefectos(MouseEvent event) {
        Stage escenarioBase = (Stage)lblTituloVista.getScene().getWindow();
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSGP_LISOFT.class.getResource("vistas/FXMLConsultarListaDefectos.fxml"));
            Parent vista = accesoControlador.load();
            FXMLConsultarListaDefectosController controler = accesoControlador.getController();
            controler.inicializarInformacion(null, usuarioResponsable);
            escenarioBase.setScene(new Scene (vista));
            escenarioBase.setTitle("Lista de defectos");
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
            controler.inicializarInformacion(null, usuarioResponsable);
            escenarioBase.setScene(new Scene (vista));
            escenarioBase.setTitle("Lista de bitácoras");
            escenarioBase.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
