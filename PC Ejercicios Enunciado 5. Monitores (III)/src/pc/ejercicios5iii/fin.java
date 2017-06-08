package pc.ejercicios5iii;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class fin {

    private final int MAX_PIEZAS;
    private int numPiezas;
    private int maquinasEsperando;
    private int maquinasDepositando;
    private int robotsEsperando;
    private boolean robotRecogiendo;
    // TODO: Definir las variables necesarias
    // TODO: Definir los cerrojos y condiciones necesarios
    private Lock mutex;
    private Condition barreraMaquinas, barreraRobots;

    public fin(int maxPiezas) {
        MAX_PIEZAS = maxPiezas;
        numPiezas = 0;
        maquinasEsperando = 0;
        maquinasDepositando = 0;
        robotsEsperando = 0;
        robotRecogiendo = false;
        // TODO: Inicializar las variables necesarias
        // TODO: Incializar los cerrojos y condiciones necesarios
        mutex = new ReentrantLock(true);
        barreraMaquinas = mutex.newCondition();
        barreraRobots = mutex.newCondition();
    }

    public int getNumPiezas() {
        return numPiezas;
    }

    public void inicioDepositar() throws InterruptedException {
        // TODO: Implementar el preprotocolo
        // <Si hay un robot recogiendo o el almacen esta lleno, entonces esperar>
        // <Hay una maquina mas depositando>
        // <Hay una pieza mas en el almacen>
        mutex.lock();
        try {
            while (robotRecogiendo
                    || (numPiezas == MAX_PIEZAS)) {
                maquinasEsperando++;//add
                barreraMaquinas.await();
            }
            maquinasDepositando++;
            numPiezas++;
        } finally {
            mutex.unlock();
        }
    }

    public void finDepositar() {
        // TODO: Implementar el postprotocolo
        // <Hay una maquina menos depositando>
        // <Si no quedan maquinas depositando, entonces liberar a un robot>
        mutex.lock();
        try {
            maquinasDepositando--;
            if (maquinasDepositando == 0) {
                barreraRobots.signal();
            }
        } finally {
            mutex.unlock();
        }
    }

    public void inicioRecoger() throws InterruptedException {
        // TODO: Implementar el preprotocolo
        // <Si hay un robot recogiendo o hay maquinas depositando o el almacen
        //  esta vacio, entonces esperar>
        // <Hay un robot recogiendo>
        // <Hay una pieza menos en el almacen>
        mutex.lock();
        try {
            while (robotRecogiendo
                    || (maquinasDepositando > 0)
                    || (numPiezas == 0)) {
                robotsEsperando++;//add
                barreraRobots.await();
            }
            robotRecogiendo = true;
            numPiezas--;
        } finally {
            mutex.unlock();
        }
    }

    public void finRecoger() {
        // TODO: Implementar el postprotocolo
        // <No hay un robot recogiendo>
        // <Si hay robots esperando y quedan piezas, liberar a un robot>
        // <En otro caso, mientras haya maquinas esperando y queden huecos,
        //  liberar a una maquina>
        mutex.lock();
        try {
            robotRecogiendo = false;
            if ((robotsEsperando > 0) && (numPiezas > 0)) {
                robotsEsperando--;//add
                barreraRobots.signal();
            } else {
                while ((maquinasEsperando > 0)
                        && (numPiezas < MAX_PIEZAS)) {
                    maquinasEsperando--;//add
                    barreraMaquinas.signal();
                }
            }
        } finally {
            mutex.unlock();
        }
    }
}