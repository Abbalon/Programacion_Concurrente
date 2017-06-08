package pc.ejercicios4i.ciclico;

import java.util.concurrent.Semaphore;

class Sincronizacion {

    private int numProcesos;
    // TODO: Declaracion de los semaforos
    Semaphore[] cuelloProcesoN;
    Semaphore[] cuelloProceso0;

    public Sincronizacion(int numProcesos) {

        this.numProcesos = numProcesos-1;
        this.cuelloProceso0 = new Semaphore[this.numProcesos];//Control al hilo 0
        this.cuelloProcesoN = new Semaphore[this.numProcesos];//control al resto de hilos
        // TODO: Inicializacion de los semaforos
        for (int i = 0; i < this.numProcesos; i++) {
            this.cuelloProcesoN[i] = new Semaphore(0);
            this.cuelloProceso0[i] = new Semaphore(0);
        }
    }

    public void finA(int idProceso) throws InterruptedException {
        // TODO: Codigo de sincronizacion que asegure que P0 no comienza a
        // ejecutar B hasta que todos los procesos hayan terminado de ejecutar A
        if (idProceso != 0) {
            this.cuelloProceso0[idProceso-1].release();
        } else {
            for (int i = 0; i < this.numProcesos; i++) {
                this.cuelloProceso0[i].acquire();
            }
        }
    }

    public void finB(int idProceso) throws InterruptedException {
        // TODO: Codigo de sincronizacion que asegure que los Pi (i = 1..N-1)
        // no comienza a ejecutar C hasta que P0 haya terminado de ejecutar B
        if (idProceso == 0) {
            for (int i = 0; i < this.numProcesos; i++) {
                this.cuelloProcesoN[i].release();
            }
        } else {
            this.cuelloProcesoN[idProceso-1].acquire();
        }
    }
}