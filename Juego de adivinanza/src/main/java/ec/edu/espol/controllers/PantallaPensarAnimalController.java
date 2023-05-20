/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.controllers;

import ec.edu.espol.model.Animal;
import ec.edu.espol.model.ArbolBinarioBusqueda;
import ec.edu.espol.proyecto2p.App;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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
public class PantallaPensarAnimalController implements Initializable {

    @FXML
    private Button btnEmpezar;
    private int cantidadPreguntas;
    private ArbolBinarioBusqueda arbolPreguntas;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void empezarJuego(MouseEvent event) {
        try{
            Stage stg = (Stage)btnEmpezar.getScene().getWindow();
            stg.close();
            FXMLLoader loader = App.loadFXML("pantallaJuego");
            Scene sc = new Scene(loader.load(), 600, 400);
            PantallaJuegoController pjc = loader.getController();
            pjc.recibirCantidadPreguntas(cantidadPreguntas);
            pjc.recibirArbolPreguntas(arbolPreguntas);
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
            Alert a = new Alert(Alert.AlertType.ERROR, "No es posible empezar");
            a.show();
        }
    }
    
    public void recibirCantidadPreguntas(int cantidadPreguntas){
        this.cantidadPreguntas = cantidadPreguntas;
    }
    
    public void recibirArbolPreguntas(ArbolBinarioBusqueda arbolPreguntas){
        this.arbolPreguntas = arbolPreguntas;
    }
}
