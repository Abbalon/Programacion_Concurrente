package pc.ejercicios3.prodcons;

class Consumidor extends Thread {

    private Buffer buffer;

    public Consumidor(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            int valor = buffer.sacar();
            this.procesar(valor);
        }
    }

    private void procesar(int valor) {
        System.out.println("Consumidor: procesar " + valor);
        for (int j = 0; j < 10000; j++) ;
    }
}
