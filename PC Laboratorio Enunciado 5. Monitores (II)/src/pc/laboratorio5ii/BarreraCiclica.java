package pc.laboratorio5ii;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import pc.util.concurrent.locks.UrgentQueueReentrantLock;

// TODO 4: Implementar la barrera ciclica
class BarreraCiclica {

    private int numProcesos;
    private int enBarrera;
    private Lock mutex;
    private Condition barrera;

    public BarreraCiclica(int numProcesos) {
        this.numProcesos = numProcesos;
        mutex = new UrgentQueueReentrantLock();
        barrera = mutex.newCondition();
        enBarrera = 0;
    }

    public void esperar() throws InterruptedException {

        mutex.lock();
        try {

            enBarrera++;
            if (enBarrera < numProcesos) {
                barrera.await();
            } else {
                enBarrera = 0;
                barrera.signalAll();
            }
        } finally {
            mutex.unlock();
        }
    }
}
