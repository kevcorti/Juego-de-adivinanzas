/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.model;

import java.util.ArrayList;

/**
 *
 * @author Kevin Castro, Richard Pérez
 */
public class Animal {
    private String nombre;
    private ArrayList<Boolean> combinacion;
    
    public Animal(String nombre, ArrayList<Boolean> combinacion){
        this.nombre = nombre.toUpperCase();
        this.combinacion = combinacion;
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList getCombinacion() {
        return combinacion;
    }
    
    public String toString(){
        return nombre;
    }
    
}
