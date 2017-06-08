package pc.ejercicios5i.ciclico.locks;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Sincronizacion {

    private int numProcesos;
    // TODO: Declaracion de variables compartidas
    // TODO: Declaracion de locks y conditions
    private int contA;
    private boolean[] contB;
    private Lock mutex;
    private Condition terminadoA;
    private Condition terminadoB;

    public Sincronizacion(int numProcesos) {
        this.numProcesos = numProcesos;
        // TODO: Inicializacion de variables compartidas
        // TODO: Inicializacion de locks y conditions
        contA = 0;
        contB = new boolean[numProcesos];
        mutex = new ReentrantLock();

        terminadoB = mutex.newCondition();
        terminadoA = mutex.newCondition();

    }

    public void finA(int idProceso) throws InterruptedException {
        // TODO: Codigo de sincronizacion que asegure que P0 no comienza a
        // ejecutar B hasta que todos los procesos hayan terminado de ejecutar A
        mutex.lock();
        contA++;
        contB[idProceso] = Boolean.FALSE;
        try {
            if (idProceso == 0) {
                while (contA < numProcesos) {
                    terminadoA.await();
                }
            } else {
                if (contA == numProcesos) {
                    terminadoA.signalAll();
                }
            }
        } finally {
            mutex.unlock();
        }
    }

    public void finB(int idProceso) throws InterruptedException {
        // TODO: Codigo de sincronizacion que asegure que los Pi (i = 1..N-1)
        // no comienza a ejecutar C hasta que P0 haya terminado de ejecutar B
        mutex.lock();
        try {
            if (idProceso == 0) {
                contA = 0;
                for (int i = 0; i < contB.length; i++) {
                    contB[i] = Boolean.TRUE;
                }
                terminadoB.signalAll();
            } else {
                while (!contB[idProceso]) {
                    terminadoB.await();
                }

            }
        } finally {
            mutex.unlock();
        }
    }
}
