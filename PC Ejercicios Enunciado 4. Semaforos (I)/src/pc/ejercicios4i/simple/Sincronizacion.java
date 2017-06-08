package pc.ejercicios4i.simple;

import java.util.concurrent.Semaphore;

class Sincronizacion {

    private int numProcesos;
    // TODO: Declaracion de los semaforos
    Semaphore[] cuelloBotella;

    public Sincronizacion(int numProcesos) {

        this.numProcesos = numProcesos;
        this.cuelloBotella = new Semaphore[numProcesos];
        // TODO: Inicializacion de los semaforos
        this.cuelloBotella[0] = new Semaphore(0);
        for (int i = 1; i < numProcesos; i++) {
            this.cuelloBotella[i] = new Semaphore(0);
        }
    }

    public void finA(int idProceso) throws InterruptedException {
        // TODO: Codigo de sincronizacion que asegure que P0 no comienza a
        // ejecutar B hasta que todos los procesos hayan terminado de ejecutar A
        if (idProceso != 0) {
            this.cuelloBotella[idProceso].release();
        } else {
            for (int i = 1; i < this.numProcesos-1; i++) {
                this.cuelloBotella[i].acquire();
            }
        }
    }

    public void finB(int idProceso) throws InterruptedException {
        // TODO: Codigo de sincronizacion que asegure que los Pi (i = 1..N-1)
        // no comienza a ejecutar C hasta que P0 haya terminado de ejecutar B
        if (idProceso == 0) {
                for (int j = this.numProcesos; j > 1; j--) {
                    this.cuelloBotella[0].release();
                }
        } else{
            this.cuelloBotella[0].acquire();
        }
    }
}
