package pc.ejercicios5ii.ciclicaN.intrinsecos;

class BarreraCiclica {

    private int numProcesos;
    // TODO: Declaracion de variables compartidas
    private int contIda;
    private int contVuelta;
    private int despertadosIda;
    private int despertadosVuelta;
    private boolean[] ida;
    private boolean[] vuelta;

    public BarreraCiclica(int numProcesos) {
        this.numProcesos = numProcesos;
        // TODO: Inicializacion de variables compartidas
        contIda = 0;
        contVuelta = 0;
        despertadosIda = 0;
        despertadosVuelta = 0;
        ida = new boolean[numProcesos];
        vuelta = new boolean[numProcesos];
        inicializarArray(ida);
        inicializarArray(vuelta);
    }

    // TODO: Codificar metodo
    public synchronized void esperar() throws InterruptedException {
        /**
         * Sin contemplar despertares espureos
         *
         * esperando++; if (esperando == numProcesos) { esperando = 0; this.notifyAll(); } else { this.wait(); }
         */

        if (contIda < numProcesos) {
            actualizarArray(ida, contIda);
            contIda++;

            if (contIda == numProcesos) {
                this.notifyAll();
            }
            
            while (!comprobarEjecuciones(ida)) {
                this.wait();
            }

            despertadosIda++;

            if (despertadosIda == numProcesos) {
                despertadosIda = 0;
                inicializarArray(ida);
            }
        } else {
            actualizarArray(vuelta, contVuelta);
            contVuelta++;

            if (contVuelta == numProcesos) {
                contIda = 0;
                this.notifyAll();
            }
            
            while (!comprobarEjecuciones(vuelta)) {
                this.wait();
            }

            despertadosVuelta++;

            if (despertadosVuelta == numProcesos) {
                despertadosVuelta = 0;
                inicializarArray(vuelta);
                contVuelta = 0;
            }
        }

    }

    private synchronized void inicializarArray(boolean[] vector) {
        for (int i = 0; i < vector.length; i++) {
            vector[i] = Boolean.FALSE;
        }
    }

    private synchronized boolean comprobarEjecuciones(boolean[] vector) {
        boolean resultado = Boolean.TRUE;

        for (boolean proceso : vector) {
            resultado = resultado && proceso;
            if (!resultado) {
                break;
            }
        }
        return resultado;
    }

    private synchronized void actualizarArray(boolean[] vector, int cont) {
        vector[cont] = Boolean.TRUE;
    }
}
