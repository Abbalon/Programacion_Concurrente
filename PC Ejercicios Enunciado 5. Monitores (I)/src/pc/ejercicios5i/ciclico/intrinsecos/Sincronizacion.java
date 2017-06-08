package pc.ejercicios5i.ciclico.intrinsecos;

class Sincronizacion {

    private int numProcesos;
    private boolean[] terminadoB;
    private int cont;

    public Sincronizacion(int numProcesos) {
        this.numProcesos = numProcesos;
        terminadoB = new boolean[numProcesos];
        cont = 0;
    }

    public synchronized void finA(int idProceso) throws InterruptedException {
        // TODO: Codigo de sincronizacion que asegure que P0 no comienza a
        // ejecutar B hasta que todos los procesos hayan terminado de ejecutar A
        cont++;
        terminadoB[idProceso] = Boolean.FALSE;
        if (idProceso == 0) {
            while (cont < numProcesos) {
                this.wait();
            }
        }
        if (cont == numProcesos) {
            this.notifyAll();
        }
    }

    public synchronized void finB(int idProceso) throws InterruptedException {
        // TODO: Codigo de sincronizacion que asegure que los Pi (i = 1..N-1)
        // no comienza a ejecutar C hasta que P0 haya terminado de ejecutar B
        if (idProceso == 0) {
            cont = 0;
            for (int i = 0; i < terminadoB.length; i++) {
                terminadoB[i] = Boolean.TRUE;
            }
            this.notifyAll();
        } else {
            while (!terminadoB[idProceso]) {
                this.wait();
            }
        }
    }
}
