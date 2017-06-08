package pc.ejercicios5iii;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class AccesoAlmacen {

    private final int MAX_PIEZAS;
    private int numPiezas;
    private int maquinasEsperando;
    private int maquinasDepositando;
    private int robotsEsperando;
    private boolean robotRecogiendo;
    // TODO: Definir las variables necesarias
    int maquinasLiberadas;
    boolean robotsLiberados;
    // TODO: Definir los cerrojos y condiciones necesarios
    private Lock mutex;
    private Condition barreraMaquinas, barreraRobots;

    public AccesoAlmacen(int maxPiezas) {
        MAX_PIEZAS = maxPiezas;
        numPiezas = 0;
        maquinasEsperando = 0;
        maquinasDepositando = 0;
        robotsEsperando = 0;
        robotRecogiendo = false;
        // TODO: Inicializar las variables necesarias
        robotsLiberados = false;
        maquinasLiberadas = 0;
        // TODO: Incializar los cerrojos y condiciones necesarios
        mutex = new ReentrantLock(true);
        barreraMaquinas = mutex.newCondition();
        barreraRobots = mutex.newCondition();
    }

    public void inicioDepositar() throws InterruptedException {
        // TODO: Implementar el preprotocolo
        // <Si hay un robot recogiendo o el almacen esta lleno, entonces esperar>
        // <Hay una maquina mas depositando>
        // <Hay una pieza mas en el almacen>
        mutex.lock();
        try {
            while (robotRecogiendo
                    || numPiezas == MAX_PIEZAS) {
                /*while (robotRecogiendo
                    || robotLiberado
                || numPiezas + maquinasLiberadas = MAX_PIEZAS) {
                 */
                maquinasEsperando++;
                barreraMaquinas.await();

                if (maquinasLiberadas == 0) {
                    maquinasEsperando--;
                } else {
                    maquinasLiberadas--;
                    break;
                }
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
            if (maquinasDepositando == 0
                    && maquinasLiberadas == 0) {
            //    if (robotsEsperando > 0) {
                    robotsLiberados = true;
                    robotsEsperando--;
                    barreraRobots.signal();
              //  }
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
                    || (numPiezas == 0) //robotLiberado
                    ) {
                robotsEsperando++;//add
                barreraRobots.await();

                if (!robotsLiberados) {
                    robotsEsperando--;
                } else {
                    robotsLiberados = true;
                    break;
                }
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
            if ((robotsEsperando > 0)
                    && (numPiezas > 0)) {
                robotsEsperando--;//add
                System.out.println("----> Hay " + robotsEsperando + " robots Esperando");
                robotsLiberados = true;
                barreraRobots.signal();
            } else {//if(maquinasEsperando > 0) { int huecos = MAX_PIEZAS - numPiuezas;
                
                while ((maquinasEsperando > 0)//&& huecos>0) huecos--;
                        && (numPiezas < MAX_PIEZAS)) {
                    maquinasEsperando--;//add
                    maquinasLiberadas++;
                    barreraMaquinas.signal();
                }
            }
        } finally {
            mutex.unlock();
        }
    }
}
