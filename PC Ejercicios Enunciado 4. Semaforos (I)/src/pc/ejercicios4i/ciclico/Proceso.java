package pc.ejercicios4i.ciclico;

class Proceso extends Thread {

    private int idProceso;
    private Sincronizacion sincro;

    public Proceso(int idProceso, Sincronizacion sincro) {
        this.idProceso = idProceso;
        this.sincro = sincro;
    }

    public void run() {
        Pantalla pantalla = Pantalla.getPantalla();
        try {
            for (int i = 0; i < 10; i++) {
                // Bloque A
                for (int j = 0;
                        j < (int) 10000 * (5 - idProceso) * Math.random();
                        j++) ;
                pantalla.escribir("A" + idProceso + i + " - ");

                // Sincronizacion finA
                sincro.finA(idProceso);

                // Bloque B
                for (int j = 0; j < (int) 100 * Math.random(); j++) ;
                pantalla.escribir("B" + idProceso + i + " - ");

                // Sincronizacion finB
                sincro.finB(idProceso);

                // Bloque C
                for (int j = 0; j < (int) 100 * Math.random(); j++) ;
                pantalla.escribir("C" + idProceso + i + " - \n");
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }
    }
}