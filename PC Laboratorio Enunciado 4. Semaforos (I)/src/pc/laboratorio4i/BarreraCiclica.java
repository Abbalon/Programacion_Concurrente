package pc.laboratorio4i;

// TODO 5: Implementar la barrera ciclica
import java.util.concurrent.Semaphore;

class BarreraCiclica {

    private int numProcesos;
    Semaphore[] barrera;

    public BarreraCiclica(int numProcesos) {
        this.numProcesos = numProcesos;
        this.barrera = new Semaphore[this.numProcesos];
        for (int i = 0; i < this.numProcesos; i++) {
            this.barrera[i] = new Semaphore(0);
        }
    }

    public void esperar(int idProceso) throws InterruptedException {
        //this.barrera[idProceso].acquire();
        for (int i = 0; i < this.numProcesos; i++) {
            if (i != idProceso) {
                this.barrera[i].release();
            }
        }
        for (int i = 0; i < this.numProcesos - 1; i++) {
            this.barrera[idProceso].acquire();
        }
    }
}
