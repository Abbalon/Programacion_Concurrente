package pc.ejemplos2.runnable;

class Proceso extends Impresor implements Runnable {

	Proceso(int id) {
		super(id);
	}

	public void run() {
		for (int i  = 0; i < 50; i++) {
			this.imprimir();
		}
	}
}
