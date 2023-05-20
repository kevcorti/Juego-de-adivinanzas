package ec.edu.espol.model;

import java.util.ArrayList;
import java.util.Objects;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kevin Castro, Richard Pérez
 */
public class ArbolBinarioBusqueda<E> {
    public E  contenido;
    public ArbolBinarioBusqueda izquierdo;
    public ArbolBinarioBusqueda derecho;
    
    public ArbolBinarioBusqueda(E contenido){
        this.contenido = contenido;
        izquierdo = null;
        derecho = null;
    }
    
    public boolean estaVacio(){
        if(contenido == null)
            return true;
        if((izquierdo == null) && (derecho == null))
            return true;
        return false;
    }
    
    public int altura(){
        if(contenido == null)
            return 0;
        int alturaIzquierda = 0;
        int alturaDerecha = 0;
        if(izquierdo != null)
            alturaIzquierda = izquierdo.altura();
        if(derecho != null)
            alturaDerecha = derecho.altura();
        return (1 + Math.max(alturaIzquierda, alturaDerecha));
    }
    
    public boolean esHoja(){
        if(this.izquierdo==null && this.derecho==null){
            return true;
        }
        return false;
    }
    
    public void insertarPregunta(E contenido){
        if(izquierdo != null)
            izquierdo.insertarPregunta(contenido);
        else{
            ArbolBinarioBusqueda temp = new ArbolBinarioBusqueda(contenido);
            this.izquierdo = temp;
        }
        if(derecho != null)
            derecho.insertarPregunta(contenido);
        else{
            ArbolBinarioBusqueda temp = new ArbolBinarioBusqueda(contenido);
            this.derecho = temp;
        }
    }
    
    public static ArbolBinarioBusqueda respuestaSI(ArbolBinarioBusqueda arbol){
        return arbol.izquierdo;
    }
    
    public static ArbolBinarioBusqueda respuestaNO(ArbolBinarioBusqueda arbol){
        return arbol.derecho;
    }
    
    public void anadirAnimal(Animal animal, int n){
        if(!(this.esHoja())){
            if(Objects.equals(animal.getCombinacion().get(n), true)){
                this.izquierdo.anadirAnimal(animal, n+1);
            }
            else{
                this.derecho.anadirAnimal(animal, n+1);
            }
        }
        else{
            if(Objects.equals(animal.getCombinacion().get(n), true)){
                this.izquierdo = new ArbolBinarioBusqueda(animal.getNombre());
            }
            else{
                this.derecho = new ArbolBinarioBusqueda(animal.getNombre());
            }
        }
    }
    
    public ArrayList<E> almacenarHojas(ArrayList<E> lista){
        if(this.izquierdo != null){
            lista = this.izquierdo.almacenarHojas(lista);
        }
        if(this.derecho != null){
            lista = this.derecho.almacenarHojas(lista);
        }
        if(this.esHoja())
            lista.add(this.contenido);
        return lista;
    }
}
