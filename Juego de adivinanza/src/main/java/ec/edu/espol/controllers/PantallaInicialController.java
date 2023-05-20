/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.controllers;

import ec.edu.espol.proyecto2p.App;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Kevin Castro, Richard Pérez
 */
public class PantallaInicialController implements Initializable {

    @FXML
    private Button btnAcceder;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void accederJuego(MouseEvent event) {
        try{
            Stage stg = (Stage)btnAcceder.getScene().getWindow();
            stg.close();
            FXMLLoader loader = App.loadFXML("pantallaIngresarDatos");
            Scene sc = new Scene(loader.load(), 600, 400);
            Stage sg = new Stage();
            sg.setScene(sc);
            sg.setTitle("Animalator 1.0");
            String rut = System.getProperty("user.dir") + "/src/main/resources/img/icono.jpg";
            Path ruta = Paths.get(rut);
            Image imagen = new Image("file:" + ruta);
            sg.getIcons().add(imagen);
            sg.show();
        }
        catch(IOException ex){
            Alert a = new Alert(Alert.AlertType.ERROR, "No es posible ingresar");
            a.show();
        }
    }
    
}
