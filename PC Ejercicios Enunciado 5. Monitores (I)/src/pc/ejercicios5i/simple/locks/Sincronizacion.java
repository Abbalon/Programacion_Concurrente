package pc.ejercicios5i.simple.locks;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Sincronizacion {

    private int numProcesos;
    // TODO: Declaracion de variables compartidas
    // TODO: Declaracion de locks y conditions
    private int contA, contB;
    private Lock mutex;
    private Condition terminadoA, terminadoB;

    public Sincronizacion(int numProcesos) {
        this.numProcesos = numProcesos;
        // TODO: Inicializacion de variables compartidas
        // TODO: Inicializacion de locks y conditions
        contA = 0;
        contB = 0;
        mutex = new ReentrantLock();
        terminadoA = mutex.newCondition();
        terminadoB = mutex.newCondition();
    }

    public void finA(int idProceso) throws InterruptedException {
        // TODO: Codigo de sincronizacion que asegure que P0 no comienza a
        // ejecutar B hasta que todos los procesos hayan terminado de ejecutar A
        mutex.lock();
        contA++;
        try {
            if (idProceso == 0) {
                if (contA < numProcesos) {
                    terminadoA.await();
                }
            } else {
                if (contA == numProcesos) {
                    terminadoA.signal();
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
        contB++;
        try {
            while (contB < numProcesos) {
                if (idProceso == 0) {
                    break;
                } else {
                    terminadoB.await();
                }
            }
            contB = numProcesos;
            terminadoB.signalAll();
        } finally {
            mutex.unlock();
        }
    }
}
