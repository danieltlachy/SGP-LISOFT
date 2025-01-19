/*
*Autor: Mongeote Tlachy Daniel
*Fecha de creación: 10/11/2023
*Fecha de modificación: 17/11/2023
*Descripción: Clase que contiene métodos simples y reutilizables
*/
package javafxsgp_lisoft.utils;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafxsgp_lisoft.JavaFXSGP_LISOFT;

public class Utilidades {
    

    
    public static FXMLLoader cargarVista(String rutaFXML) throws IOException{ 
        URL url = javafxsgp_lisoft.JavaFXSGP_LISOFT.class.getResource(rutaFXML);
        return new FXMLLoader(url);
    }
    
    public static void mostrarDialogoSimple (String titulo, String mensaje, Alert.AlertType tipo){
        Alert alertaSimple = new Alert (tipo);
        alertaSimple.setTitle(titulo);
        alertaSimple.setContentText(mensaje);
        alertaSimple.setHeaderText(null);
        alertaSimple.setAlertType(tipo);
        alertaSimple.showAndWait();
    }
    
    public static boolean mostrarDialogoConfirmacion(String titulo, String mensaje){
        Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        alertaConfirmacion.setTitle(titulo);
        alertaConfirmacion.setContentText(mensaje);
        alertaConfirmacion.setHeaderText(null);
        Optional<ButtonType> botonClic = alertaConfirmacion.showAndWait();
        return (botonClic.get() == ButtonType.OK);
    }
    
    public static boolean correoValido(String correo){
        if(correo != null && !correo.isEmpty()){
            Pattern patronCorreo = Pattern.compile("([a-z0-9]+(\\.?[a-z0-9])*)+@(([a-z]+)\\.([a-z]+))+"); 
            Matcher matchPatron = patronCorreo.matcher(correo);
            return matchPatron.find();
        }else{
            return false;
        }
    }
    
    public static boolean caracteresValidos(String texto) {
        String regex = Constantes.FORMATO_ESPANOL;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(texto);
        return matcher.matches();
    }
    
    public static boolean matriculaValida(String matricula) {
        String regex = "^S\\d{8}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(matricula);
        return matcher.matches();
    }
    
    public static boolean validarNumeros(String texto){
        if(texto != null || !texto.isEmpty()){
            Pattern patronNumeros = Pattern.compile("\\d+");
            Matcher matchPatron = patronNumeros.matcher(texto); 
            return matchPatron.matches();
        } else {
            return false;
        }
    }
    public static void cerrarSesionDesconexion(Stage escenarioBase){
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSGP_LISOFT.class.getResource("vistas/FXMLLogin.fxml"));
            Parent vista = accesoControlador.load();
            escenarioBase.setScene(new Scene (vista));
            escenarioBase.setTitle("Inicio Sesión");
            escenarioBase.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }}
}
