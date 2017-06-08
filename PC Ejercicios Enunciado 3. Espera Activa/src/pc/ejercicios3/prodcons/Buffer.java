package pc.ejercicios3.prodcons;

class Buffer {

    private volatile int valor;
    private WatchDog guardiaBuffer;

    public Buffer() {
        this.guardiaBuffer = new WatchDog();
    }
    
    public void meter(int valor) {
        this.guardiaBuffer.waitWatchDogProduccer();
        System.out.println("Buffer: meter " + valor);
        this.valor = valor;
        this.guardiaBuffer.warn();
    }

    public int sacar() {
        this.guardiaBuffer.waitWatchDogCostumer();
        System.out.println("Buffer: sacar " + valor);
        this.guardiaBuffer.done();
        return valor;
    }

    public WatchDog getGuardia() {
        return this.guardiaBuffer;
    }
}
