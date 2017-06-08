package pc.ejercicios4i.ciclico;

import java.util.concurrent.Semaphore;

class Pantalla {

    private static Pantalla pantalla = new Pantalla();

    public static Pantalla getPantalla() {
        return pantalla;
    }

    private Semaphore exclusion;

    private Pantalla() {
        exclusion = new Semaphore(1);
    }

    public void escribir(String texto) throws InterruptedException {
        exclusion.acquire();
        try {
            System.out.print(texto);
        } finally {
            exclusion.release();
        }
    }
}
