/*
*Autor: Torres Osorio Alesis de Jesus
*Fecha de creación: 10/11/2023
*Fecha de modificación: 17/11/2023
*Descripción: Controlador de la vista del detalle del defecto
*/
package javafxsgp_lisoft.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafxsgp_lisoft.modelo.pojo.Defecto;
import javafxsgp_lisoft.modelo.pojo.Desarrollador;
import javafxsgp_lisoft.modelo.pojo.ResponsableProyecto;


public class FXMLDetalleDefectoController implements Initializable {
    Desarrollador usuarioDesarrollador;

    @FXML
    private TextArea taDescripcion;
    @FXML
    private Label lbNombre;
    @FXML
    private Label lbTipo;
    @FXML
    private Label lbEsfuerzo;
    @FXML
    private Label lbResponsable;
    private Defecto defectoSeleccionado;
    private boolean esDesarrollador;
    private ResponsableProyecto usuarioResponsable;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    void inicializarInformacion(Defecto defectoSeleccionado) {
        this.defectoSeleccionado = defectoSeleccionado;
        lbNombre.setText(defectoSeleccionado.getNombre());
        lbTipo.setText(defectoSeleccionado.getTipo());
        lbEsfuerzo.setText(Integer.toString(defectoSeleccionado.getEsfuerzo()));
        lbResponsable.setText(defectoSeleccionado.getNombreDesarrollador());
        taDescripcion.setText(defectoSeleccionado.getDescripcion());
        taDescripcion.setEditable(false);
        
    }

    @FXML
    private void clcRegresar(MouseEvent event) {
        Stage escenarioPrincipal = (Stage) lbTipo.getScene().getWindow();
        escenarioPrincipal.close();
    }

    
}
