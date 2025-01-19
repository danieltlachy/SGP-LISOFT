/*
*Autor: Martinez Caixba Miguel Angel, Mongeote Tlachy Daniel, Torres Osorio Alesis de Jesus
*Fecha de creación: 15/10/2023
*Fecha de modificación: 15/10/2023
*Descripción: Archivo base del proyecto
*/
package javafxsgp_lisoft;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaFXSGP_LISOFT extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("vistas/FXMLLogin.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Inicio de Sesión");
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
