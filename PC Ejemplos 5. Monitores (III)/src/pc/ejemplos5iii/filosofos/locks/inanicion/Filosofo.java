package pc.ejemplos5iii.filosofos.locks.inanicion;

class Filosofo extends Thread {

	private int idFilosofo;
	private Palillo[] palillos;
	private Comedor comedor;

	public Filosofo(int idFilosofo, Palillo[] palillos, Comedor comedor) {
		this.idFilosofo = idFilosofo;
		this.palillos = palillos;
		this.comedor = comedor;
	}

	public void run() {
		try {
			while (true) {
//			for (int i = 0; i < 10; i++) {
				this.pensar();
				this.preprotocolo();
				this.comer();
				this.postprotocolo();
			}
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	private void pensar() throws InterruptedException {
		Thread.sleep((long) (100 * Math.random()));
	}

	private void comer() throws InterruptedException {
		Pantalla pantalla = Pantalla.getPantalla();
		pantalla.escribir("El filosofo " + (idFilosofo + 1) +
				" empieza a comer");
		Thread.sleep((long) (100 * Math.random()));
		pantalla.escribir("El filosofo " + (idFilosofo + 1) +
				" termina de comer");
	}

	private void preprotocolo() throws InterruptedException {
		comedor.entrar();
		palillos[idFilosofo].coger();
		// Este retardo NO provoca interbloqueo
		Thread.sleep((long) (100 * Math.random()));
		palillos[(idFilosofo + 1) % palillos.length].coger();
	}

	private void postprotocolo() throws InterruptedException {
		palillos[idFilosofo].dejar();
		palillos[(idFilosofo + 1) % palillos.length].dejar();
		comedor.salir();
	}
}
