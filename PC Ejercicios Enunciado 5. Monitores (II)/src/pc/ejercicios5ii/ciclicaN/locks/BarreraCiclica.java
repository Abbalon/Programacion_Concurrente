package pc.ejercicios5ii.ciclicaN.locks;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class BarreraCiclica {

    private int numProcesos;
    // TODO: Declaracion de variables compartidas
    private int espera;
    // TODO: Declaracion de locks y conditions
    private Lock mutex;
    private Condition condicionA;
    private Condition condicionB;
    private boolean switx, continuarA, continuarB;
    int cont;

    public BarreraCiclica(int numProcesos) {
        this.numProcesos = numProcesos;
        // TODO: Inicializacion de variables compartidas
        int espera = 0;
        // TODO: Inicializacion de locks y conditions
        mutex = new ReentrantLock();
        condicionA = mutex.newCondition();
        condicionB = mutex.newCondition();
        switx = true;
        continuarA = false;
        continuarB = false;
        cont = 0;
    }

    // TODO: Codificar metodo
    public void esperar() throws InterruptedException {
        mutex.lock();
        try {
            if (switx) {
                cont++;
                if (cont == numProcesos) {
                    continuarA = true;
                    continuarB = false;
                    switx = false;
                    cont = 0;
                    condicionA.signalAll();
                }
                while (!continuarA) {
                    condicionA.await();
                }
            } else {
                cont++;
                if (cont == numProcesos) {
                    continuarB = true;
                    continuarA = false;
                    switx = true;
                    cont = 0;
                    condicionB.signalAll();
                }
                while (!continuarB) {
                    condicionB.await();
                }
            }

        } finally {
            mutex.unlock();
        }
    }
}
