package pc.ejercicios4ii;

import java.util.concurrent.Semaphore;

class AccesoAlmacen {

    //Supongo que el almacen inicialmente está vacio
    private final int MAX_PIEZAS;
    // TODO: Definir los semaforos necesarios
    Semaphore mutexRobot, mutexAlmacen, mutexMaquina;
    
    public AccesoAlmacen(int maxPiezas) {
        MAX_PIEZAS = maxPiezas;
        // TODO: Inicializar los semaforos necesarios
        //Contabiliza las piezas del almacen
        this.mutexRobot = new Semaphore(0);
        this.mutexMaquina = new Semaphore(this.MAX_PIEZAS);
        //Vigila la exclusión de acceso al almacen
        this.mutexAlmacen = new Semaphore(1);
    }

    public void inicioDepositar(int llamada) throws InterruptedException {
        // TODO: Implementar el preprotocolo
        this.mutexMaquina.acquire();
        this.mutexAlmacen.acquire();
    }

    public void finDepositar(int llamada) {
        // TODO: Implementar el postprotocolo
        this.mutexAlmacen.release();
        this.mutexRobot.release();
    }

    public void inicioRecoger(int llamada) throws InterruptedException {
        // TODO: Implementar el preprotocolo
        this.mutexRobot.acquire();
        this.mutexAlmacen.acquire();
    }

    public void finRecoger(int llamada) {
        // TODO: Implementar el postprotocolo
        this.mutexAlmacen.release();
        this.mutexMaquina.release();
    }
}
