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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Kevin Castro, Richard Pérez
 */
public class PantallaJuegoController implements Initializable {

    @FXML
    private Label lblCantidadPreguntas;
    @FXML
    private Button btnSI;
    @FXML
    private Button btnNO;
    @FXML
    private Label lblPregunta;
    private int cantidadPreguntas;
    private ArbolBinarioBusqueda arbolPreguntas;
    @FXML
    private Button btnVisualizar;
    @FXML
    private Button btnJugarAgain;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnVisualizar.setDisable(true);
    }    
    
    @FXML
    private void seleccionarSI(MouseEvent event) {
        cantidadPreguntas--;
        if(cantidadPreguntas != 0){
            arbolPreguntas = ArbolBinarioBusqueda.respuestaSI(arbolPreguntas);
            lblCantidadPreguntas.setText("Preguntas disponibles: " + cantidadPreguntas);
            lblPregunta.setText((String)arbolPreguntas.contenido);
        }
        else{
            arbolPreguntas = ArbolBinarioBusqueda.respuestaSI(arbolPreguntas);
            lblCantidadPreguntas.setText("");
            lblPregunta.setText("Click en visualizar");
            btnSI.setDisable(true);
            btnNO.setDisable(true);
            btnVisualizar.setDisable(false);
        }      
    }

    @FXML
    private void seleccionarNO(MouseEvent event) {
        cantidadPreguntas--;
        if(cantidadPreguntas != 0){
            arbolPreguntas = ArbolBinarioBusqueda.respuestaNO(arbolPreguntas);
            lblCantidadPreguntas.setText("Preguntas disponibles: " + cantidadPreguntas);
            lblPregunta.setText((String)arbolPreguntas.contenido);
        }
        else{
            arbolPreguntas = ArbolBinarioBusqueda.respuestaNO(arbolPreguntas);
            lblCantidadPreguntas.setText("");
            lblPregunta.setText("Click en visualizar");
            btnSI.setDisable(true);
            btnNO.setDisable(true);
            btnVisualizar.setDisable(false);
        }
    }
    
    public void recibirCantidadPreguntas(int cantidadPreguntas){
        this.cantidadPreguntas = cantidadPreguntas;
        lblCantidadPreguntas.setText("Preguntas disponibles: " + cantidadPreguntas);
    }
    
    public void recibirArbolPreguntas(ArbolBinarioBusqueda arbolPreguntas){
        this.arbolPreguntas = arbolPreguntas;
        lblPregunta.setText((String)arbolPreguntas.contenido);
    }

    @FXML
    private void visualizarResultado(MouseEvent event) {
        if(arbolPreguntas != null){
            ArrayList<String> posiblesAnimales = arbolPreguntas.almacenarHojas(new ArrayList());
            if(posiblesAnimales.size() == 1){
                Alert a = new Alert(AlertType.INFORMATION, "El animal en el que pensaste es...... " + posiblesAnimales.get(0) + " :)");
                a.show();
            }
            else{
                Alert a = new Alert(AlertType.INFORMATION, "Tengo varias opciones de animales posibles.... " + posiblesAnimales.toString());
                a.show();
            }
        }
        else{
            Alert a = new Alert(AlertType.INFORMATION, "No pude adivinar el animal :(");
            a.show();
        }
        btnVisualizar.setDisable(true);
        lblPregunta.setText("Puedes jugar de nuevo :)");
    }

    @FXML
    private void jugarAgain(MouseEvent event) {
        try{
            Stage stg = (Stage)btnJugarAgain.getScene().getWindow();
            stg.close();
            FXMLLoader loader = App.loadFXML("pantallaInicial");
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
            Alert a = new Alert(Alert.AlertType.ERROR, "No es posible jugar de nuevo");
            a.show();
        }
    }
}
