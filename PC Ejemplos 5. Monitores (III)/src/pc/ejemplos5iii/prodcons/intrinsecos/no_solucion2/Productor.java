package pc.ejemplos5iii.prodcons.intrinsecos.no_solucion2;

abstract class Productor<Dato> extends Thread {

	private Buzon<Dato> buzon;

	public Productor(Buzon<Dato> buzon) {
		this.buzon = buzon;
	}

	public void run() {
		try {
			while (true) {
//			for (int i = 0; i < 10; i++) {
				Dato dato = this.producir();
				buzon.enviar(dato);
			}
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	protected abstract Dato producir();
}
