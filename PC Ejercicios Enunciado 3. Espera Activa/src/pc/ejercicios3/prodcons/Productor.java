package pc.ejercicios3.prodcons;

class Productor extends Thread {

    private Buffer buffer;

    public Productor(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            int valor = this.generar(i);
            buffer.meter(valor);
        }
    }

    private int generar(int i) {
        for (int j = 0; j < 10; j++) ;
        return i;
    }
}
