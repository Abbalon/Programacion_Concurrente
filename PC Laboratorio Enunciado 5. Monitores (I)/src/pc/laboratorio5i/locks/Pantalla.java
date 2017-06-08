package pc.laboratorio5i.locks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Pantalla {

    private Lock mutex;

    private static Pantalla pantalla = new Pantalla();

    public static Pantalla getPantalla() {

        return pantalla;
    }

    private Pantalla() {
        mutex = new ReentrantLock();
    }

    // TODO 7: Conseguir exclusion mutua con cerrojos
    public void escribir(String texto) {

        mutex.lock();

        try {
            System.out.println(texto);
        } finally {
            mutex.unlock();
        }
    }
}
