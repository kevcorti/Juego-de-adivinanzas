/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.controllers;

import ec.edu.espol.model.Animal;
import ec.edu.espol.model.ArchivoNullException;
import ec.edu.espol.model.ArchivoVacioException;
import ec.edu.espol.model.CantidadIncorrectaException;
import ec.edu.espol.model.PanelVacioException;
import ec.edu.espol.model.ArbolBinarioBusqueda;
import ec.edu.espol.proyecto2p.App;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Kevin Castro, Richard Pérez
 */
public class PantallaIngresarDatosController implements Initializable {

    @FXML
    private Button btnPreguntas;
    @FXML
    private Button btnRespuestas;
    @FXML
    private TextField infoCantidad;
    @FXML
    private Button btnSiguiente;
    private File archivoPreguntas;
    private File archivoRespuestas;
    private int cantidadPreguntas;
    private int cantidadRespuestasArchivo;
    private int cantidadPreguntasArchivo;
    private ArrayList<Animal> listaAnimales;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cantidadPreguntasArchivo = 0;
        cantidadRespuestasArchivo = 0;
        listaAnimales = new ArrayList<>();
    }    

    @FXML
    private void subirArchivoPreguntas(MouseEvent event) {
        FileChooser fc = new FileChooser();
            fc.setTitle("Buscar archivo de Preguntas");
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files","*.txt"));
            Stage stage = (Stage)btnPreguntas.getScene().getWindow();
            archivoPreguntas = fc.showOpenDialog(stage);
            if(archivoPreguntas != null){
                try(BufferedReader bf = new BufferedReader(new FileReader(archivoPreguntas))){
                    String linea;
                    while((linea = bf.readLine()) != null){
                        cantidadPreguntasArchivo++;
                    }
                } 
                catch (IOException ex) {
                    Alert a = new Alert(Alert.AlertType.ERROR,"No es posible leer el archivo de preguntas");
                    a.show();
                }
            }
    }

    @FXML
    private void subirArchivoRespuestas(MouseEvent event) {
        FileChooser fc = new FileChooser();
            fc.setTitle("Buscar archivo de Respuestas");
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files","*.txt"));
            Stage stage = (Stage)btnRespuestas.getScene().getWindow();
            archivoRespuestas = fc.showOpenDialog(stage);
            if(archivoRespuestas != null){
                try(BufferedReader bf = new BufferedReader(new FileReader(archivoRespuestas))){
                    String linea;
                    while((linea = bf.readLine()) != null){
                        cantidadRespuestasArchivo++;
                    }
                } 
                catch (IOException ex) {
                    Alert a = new Alert(Alert.AlertType.ERROR,"No es posible leer el archivo de respuestas");
                    a.show();
                }
            }
    }

    @FXML
    private void siguienteVentana(MouseEvent event) {
        try{
            if(Objects.equals(infoCantidad.getText(),""))
                throw new PanelVacioException("Ingresar la cantidad de preguntas a realizar");
            if(archivoPreguntas == null || archivoRespuestas == null)
                throw new ArchivoNullException("Subir los archivos correspondientes");
            if(cantidadPreguntasArchivo == 0 || cantidadRespuestasArchivo == 0)
                throw new ArchivoVacioException("Algún archivo está vacío. Por favor subir correctamente");
            cantidadPreguntas = Integer.parseInt(infoCantidad.getText());
            if(cantidadPreguntas <= 0)
                throw new CantidadIncorrectaException("Ingresar cantidad correcta");
            if(cantidadPreguntas > cantidadPreguntasArchivo)
                throw new CantidadIncorrectaException("Cantidad mayor a preguntas existentes en el archivo. Ingresar cantidad menor o igual por favor");
            ArbolBinarioBusqueda arbolPreguntas = new ArbolBinarioBusqueda(null); 
            try(BufferedReader bf = new BufferedReader(new FileReader(archivoPreguntas))){
                    String linea;
                    while((linea = bf.readLine()) != null){
                        if(arbolPreguntas.altura() == 0)
                            arbolPreguntas.contenido = linea;
                        else{
                            arbolPreguntas.insertarPregunta(linea);
                        }
                    }
            } 
            catch (IOException ex) {
                Alert a = new Alert(Alert.AlertType.ERROR,"No es posible leer el archivo de preguntas");
                a.show();
            }
            try(BufferedReader bf = new BufferedReader(new FileReader(archivoRespuestas))){
                    String linea;
                    while((linea = bf.readLine()) != null){
                        ArrayList<Boolean> combinacion = new ArrayList();
                        String[] info = linea.split(" ");
                        for(int i = 1; i < info.length; i++){
                            if(Objects.equals("SI",info[i]) || Objects.equals("si",info[i]) ||
                                    Objects.equals("SÍ",info[i]) || Objects.equals("sí",info[i]))
                                combinacion.add(true);
                            else{
                                combinacion.add(false);
                            }
                        }
                        listaAnimales.add(new Animal(info[0].toUpperCase(), combinacion));
                    }
            } 
            catch (IOException ex) {
                Alert a = new Alert(Alert.AlertType.ERROR,"No es posible leer el archivo de respuestas");
                a.show();
            }
            for(Animal animal: listaAnimales){
                arbolPreguntas.anadirAnimal(animal, 0);
            }
            Stage stg = (Stage)btnSiguiente.getScene().getWindow();
            FXMLLoader loader = App.loadFXML("pantallaPensarAnimal");
            Scene sc = new Scene(loader.load(), 600, 400);
            PantallaPensarAnimalController ppac = loader.getController();
            ppac.recibirCantidadPreguntas(cantidadPreguntas);
            ppac.recibirArbolPreguntas(arbolPreguntas);
            stg.setScene(sc);
            stg.setTitle("Animalator 1.0");
            String rut = System.getProperty("user.dir") + "/src/main/resources/img/icono.jpg";
            Path ruta = Paths.get(rut);
            Image imagen = new Image("file:" + ruta);
            stg.getIcons().add(imagen);
            stg.show();
        }
        catch(PanelVacioException | ArchivoNullException | ArchivoVacioException ex){
            Alert a = new Alert(AlertType.ERROR, ex.getMessage());
            a.show();
        }
        catch(NumberFormatException ex){
            Alert a = new Alert(Alert.AlertType.ERROR, "Ingresar número correcto");
            a.show();
        }
        catch(CantidadIncorrectaException ex){
            Alert a = new Alert(AlertType.ERROR, ex.getMessage());
            a.show();
        }
        catch(IOException ex){
            Alert a = new Alert(Alert.AlertType.ERROR, "No es posible ingresar");
            a.show();
        }
    }
    
}
