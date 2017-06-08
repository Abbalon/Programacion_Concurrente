package pc.ejemplos4ii.prodcons;

abstract class Consumidor<Dato> extends Thread {

	private Buzon<Dato> buzon;

	public Consumidor(Buzon<Dato> buzon) {
		this.buzon = buzon;
	}

	public void run() {
		try {
//			while (true) {
			for (int i = 0; i < 10; i++) {
				Dato dato = buzon.recibir();
				this.consumir(dato);
			}
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	protected abstract void consumir(Dato dato);
}