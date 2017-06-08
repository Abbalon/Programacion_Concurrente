package pc.ejemplos4iii.filosofos.deadlock;

class Filosofo extends Thread {

	private int idFilosofo;
	private Palillo[] palillos;

	public Filosofo(int idFilosofo, Palillo[] palillos) {
		this.idFilosofo = idFilosofo;
		this.palillos = palillos;
	}

	public void run() {
		try {
//			while (true) {
			for (int i = 0; i < 10; i++) {
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
		palillos[idFilosofo].coger();
		// Este retardo provoca interbloqueo
		Thread.sleep((long) (100 * Math.random()));
		palillos[(idFilosofo + 1) % palillos.length].coger();
	}

	private void postprotocolo() throws InterruptedException {
		palillos[idFilosofo].dejar();
		palillos[(idFilosofo + 1) % palillos.length].dejar();
	}
}
