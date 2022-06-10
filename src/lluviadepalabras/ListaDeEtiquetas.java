/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lluviadepalabras;

import javax.swing.JLabel;


public class ListaDeEtiquetas {
    private JLabel etiqueta;
    private ListaDeEtiquetas siguiente;
    
    public ListaDeEtiquetas(JLabel etiqueta){
        this.etiqueta=etiqueta;
    }
    
    public ListaDeEtiquetas(){}
    
    public JLabel getEtiqueta(){
        return etiqueta;
    }
    
    public ListaDeEtiquetas getSiguiente(){
        return siguiente;
    }
    
    public void setEtiqueta(JLabel etiqueta){
        this.etiqueta=etiqueta;
    }
    
    public void setSiguiente(ListaDeEtiquetas siguiente){
        this.siguiente=siguiente;
    }
    
}
