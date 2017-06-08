package pc.laboratorio4ii;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import pc.laboratorio4ii.util.Buzon;

// TODO 2: Convertir en una clase de hilos concurrentes
class Logger extends Thread {

    //final private int TAMANO=10;
    private PrintWriter out;
    // TODO 3: Sustituir por un buzon con capacidad para 10 mensajes
    //private Semaphore exclusion;
    Buzon<String> exclusion;

    public Logger(String ruta) throws IOException {
        out = new PrintWriter(ruta);
        //exclusion = new Semaphore(1);
        exclusion = new Buzon(10);
    }

    public void escribir(String mensaje) throws InterruptedException {
        // TODO 4: Sustituir por enviar un mensaje al buzon
        //exclusion.acquire();
        exclusion.enviar(mensaje);
    }

    private void _escribir(String mensaje) {
        out.println(mensaje);
    }

    public void cerrar() {
        //out.close();
        this.interrupt();
    }

    // TODO 5: Escribir el código del metodo run()
    public void run() {
        String Dato = new String();

        try {
            while (true) {
                Dato = exclusion.recibir();
                this._escribir(Dato);
            }
        } catch (InterruptedException ex) {
            //java.util.logging.Logger.getLogger(Logger.class.getName()).log(Level.SEVERE, null, ex);
            out.close();
            System.out.print("Logger cerrado");
        }

    }
}
