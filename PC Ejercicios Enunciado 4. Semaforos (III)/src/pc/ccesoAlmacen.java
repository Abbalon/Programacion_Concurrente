package pc.ejercicios4iii;

import java.util.concurrent.Semaphore;

class AccesoAlmacen {

    private final int MAX_PIEZAS;
    private int numPiezas;
    private int maquinasEsperando;
    private int maquinasDepositando;
    private int robotsEsperando;
    private boolean robotRecogiendo;
    // TODO: Definir los semaforos necesarios
    Semaphore mutex; //Exlusion mutua para los robots
    Semaphore permisoDepositar;
    Semaphore permisoRecoger;
    //Semaphore mutexRobot;

    int numRobotsBloqueados, numMaquinasBloqueadas;

    public AccesoAlmacen(int maxPiezas) {
        MAX_PIEZAS = maxPiezas;
        numPiezas = 0;
        maquinasEsperando = 0;
        maquinasDepositando = 0;
        robotsEsperando = 0;
        robotRecogiendo = false;
        // TODO: Inicializar los semaforos necesarios
        mutex = new Semaphore(1);
        permisoDepositar = new Semaphore(0);
        permisoRecoger = new Semaphore(0);
    }

    public void inicioDepositar() throws InterruptedException {
        // TODO: Implementar el preprotocolo
        // <Si hay un robot recogiendo o el almacen esta lleno, entonces esperar>
        // <Hay una maquina mas depositando>
        // <Hay una pieza mas en el almacen>
        mutex.acquire();
        if (robotRecogiendo || numPiezas == MAX_PIEZAS) {
            maquinasEsperando++;
            mutex.release();
            permisoDepositar.acquire();
            mutex.acquire();
        }
        maquinasDepositando++;
        numPiezas++;
        mutex.release();
    }

    public void finDepositar() throws InterruptedException {
        // TODO: Implementar el postprotocolo
        // <Hay una maquina menos depositando>
        // <Si no quedan maquinas depositando, entonces liberar a un robot>
        mutex.acquire();
        maquinasDepositando--;
        if (maquinasDepositando == 0 && robotsEsperando > 0) {
            robotsEsperando--;
            robotRecogiendo = true;
            permisoRecoger.release();//cuantas piezas puedes sacar
        }
        mutex.release();
    }

    public void inicioRecoger() throws InterruptedException {
        // TODO: Implementar el preprotocolo
        // <Si hay un robot recogiendo o hay maquinas depositando o el almacen
        //  esta vacio, entonces esperar>
        // <Hay un robot recogiendo>
        // <Hay una pieza menos en el almacen>
        mutex.acquire();
        if (maquinasDepositando > 0 || robotRecogiendo || numPiezas == 0) {
            robotsEsperando++;
            mutex.release();
            permisoRecoger.acquire();
            mutex.acquire();
        }
        robotRecogiendo = true;
        numPiezas--;
        mutex.release();
    }

    public void finRecoger() throws InterruptedException {
        // TODO: Implementar el postprotocolo
        // <No hay un robot recogiendo>
        // <Si hay robots esperando y quedan piezas, liberar a un robot>
        // <En otro caso, mientras haya maquinas esperando y queden huecos,
        //  liberar a una maquina>
        mutex.acquire();
        robotRecogiendo = false;
        if (robotsEsperando > 0 && numPiezas > 0) {
            robotsEsperando--;
            permisoRecoger.release();
        } else {
            int aux = MAX_PIEZAS - numPiezas;
            while (maquinasEsperando > 0 && (aux--) > 0) {
                maquinasEsperando--;
                permisoDepositar.release();
            }
        }
        //permisoRecoger.release();
        mutex.release();
    }
}
