package pc.ejemplos5ii.ciclica.intrinsecos.solucion1;

class BarreraCiclica {

    private boolean continuar1;
    private boolean continuar2;

    public BarreraCiclica() {
        continuar1 = false;
        continuar2 = false;
    }

    public synchronized void esperar1() throws InterruptedException {
        continuar2 = true;
        if (!continuar1) {
            while (!continuar1) {
                this.wait();
            }
            continuar1 = false;
            this.notify();
        } else {
            continuar1 = false;
            this.notify();
            while (continuar2) {
                this.wait();
            }
        }
    }

    public synchronized void esperar2() throws InterruptedException {
        continuar1 = true;
        if (!continuar2) {
            while (!continuar2) {
                this.wait();
            }
            continuar2 = false;
            this.notify();
        } else {
            continuar2 = false;
            this.notify();
            while (continuar1) {
                this.wait();
            }
        }
    }
}
