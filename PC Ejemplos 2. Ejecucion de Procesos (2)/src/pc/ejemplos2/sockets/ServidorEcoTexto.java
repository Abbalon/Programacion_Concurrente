package pc.ejemplos2.sockets;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class ServidorEcoTexto {

    ThreadPoolExecutor fondoHilos;

    public static void main(String[] args) {

        ServidorEcoTexto servidor = new ServidorEcoTexto();
        servidor.ejecutar();
    }

    private void ejecutar() {
        try {
            System.out.print("Establezca el número máximo de conexiones permitidas:  ");
            int overThreads = new Scanner(System.in).nextInt();
            poolConexion colectionThread;
            colectionThread = new poolConexion(overThreads);

            // Crear socket servidor
            ServerSocket socketServidor = new ServerSocket(2020);

            while (true) {
                System.out.println("Servidor> Esperando conexion...");
                System.out.println(colectionThread.getThreadPoolExecutor().getTaskCount());
                System.out.println(colectionThread.getThreadPoolExecutor().getActiveCount());

                // Esperar conexion del cliente
                Socket socketConexion = socketServidor.accept();

                System.out.println("Servidor> Recibida conexion de "
                        + socketConexion.getInetAddress().getHostAddress() + ":"
                        + socketConexion.getPort());

                // Procesar conexion en un hilo independiente
                //new HiloConexion(socketConexion).start();
                try {
                    colectionThread.getThreadPoolExecutor().execute(new HiloConexion(socketConexion));
                } catch (Exception ex) {
                    System.out.println("Se ha producido un desbordamiento en la cola de hilos \n" + ex.getMessage());
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private class poolConexion {

        private poolConexion(int maxPoolSize) {
            // Una cola de Runnables
            BlockingQueue<Runnable> workQueue;
            // Instancia de la cola de Runnables con SynchronousQueue.
            workQueue = new LinkedBlockingQueue<Runnable>();//<Runnable> produce un warning por redundancia
            fondoHilos = new ThreadPoolExecutor(maxPoolSize, maxPoolSize, 3, TimeUnit.SECONDS, workQueue);
        }

        private ThreadPoolExecutor getThreadPoolExecutor() {
            return fondoHilos;
        }

    }

    private class HiloConexion extends Thread {

        private Socket socketConexion;

        private HiloConexion(Socket socketConexion) {
            this.socketConexion = socketConexion;
        }

        public void run() {
            try {
                PrintWriter out = null;
                BufferedReader in = null;
                try {
                    // Obtener flujos de salida y entrada
                    OutputStream outStream = socketConexion.getOutputStream();
                    InputStream inStream = socketConexion.getInputStream();

                    // Crear flujos de escritura y lectura
                    out = new PrintWriter(outStream);
                    System.out.println("Servidor> Obtenido flujo de escritura");
                    in = new BufferedReader(new InputStreamReader(inStream));
                    System.out.println("Servidor> Obtenido flujo de lectura");

                    // Leer y escribir en los flujos
                    boolean salir = false;
                    String linea;
                    while (!salir && (linea = in.readLine()) != null) {
                        System.out.println("Servidor> Recibida linea = "
                                + linea);
                        if (linea.trim().equals("adios")) {
                            salir = true;
                        } else 
                            if(linea!=null){
                            out.println(linea);
                            out.flush();
                        }
                    }
                } finally {
                    // Cerrar flujos
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                    }
                    // Cerrar socket de la conexion
                    socketConexion.close();
                    System.out.println("Servidor> Fin de conexion");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
