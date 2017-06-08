/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pc.ejercicios3.prodcons;

/**
 *
 * @author montbs
 */
public class WatchDog {
    private volatile boolean go;
    //false = buffer vacio
    //true = buffer lleno

    public WatchDog() {
        go=false;
    }
    
    /**
     *  Avisa que el buffer está lleno
     */
    public void warn(){
        go=true;
    }
    
    /**
     *  Avisa que el buffer está vacio
     */
    public void done(){
        go=false;
    }
    
    /**
     *  Espera si el buffer está vacio
     */
    public void waitWatchDogProduccer(){
        while(go);
    }
    
    /**
     *  Espera si el buffer está lleno
     */
    public void waitWatchDogCostumer(){
        while(!go);
    }
}
