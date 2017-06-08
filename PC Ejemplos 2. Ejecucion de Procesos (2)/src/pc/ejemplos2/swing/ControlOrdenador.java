/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pc.ejemplos2.swing;

/**
 *
 * @author montbs
 */
public class ControlOrdenador {

    public ControlOrdenador() {
    }
    
     public void esperar() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
     
    public void intercambiar(int[] array, int a, int b){
        int aux = array[a];
        array[a] = array[b];
        array[b] = aux;
    }
}
