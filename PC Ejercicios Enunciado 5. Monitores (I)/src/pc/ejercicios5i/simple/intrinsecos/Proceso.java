package pc.ejercicios5i.simple.intrinsecos;

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
            // Bloque A
            for (int j = 0; j < (int) 10000 * (5 - idProceso) * Math.random();
                    j++) ;
            pantalla.escribir("A" + idProceso + " ");

            // Sincronizacion finA
            sincro.finA(idProceso);

            // Bloque B
            for (int j = 0; j < (int) 100 * Math.random(); j++) ;
            if(idProceso==0)
                System.out.println("\n");
            pantalla.escribir("B" + idProceso + " ");

            // Sincronizacion finB
            sincro.finB(idProceso);

            // Bloque C
            for (int j = 0; j < (int) 100 * Math.random(); j++) ;
            pantalla.escribir("C" + idProceso + " ");
        } catch (InterruptedException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }
    }
}
