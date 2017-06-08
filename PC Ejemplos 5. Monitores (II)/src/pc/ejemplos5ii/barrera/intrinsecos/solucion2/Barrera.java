package pc.ejemplos5ii.barrera.intrinsecos.solucion2;

class Barrera {

	private boolean continuar1;
	private boolean continuar2;

	public Barrera() {
		continuar1 = false;
		continuar2 = false;
	}

	public synchronized void esperar1() throws InterruptedException {
		continuar2 = true;
		this.notify();
		while (!continuar1) {
			this.wait();
		}
	}

	public synchronized void esperar2() throws InterruptedException {
		continuar1 = true;
		this.notify();
		while (!continuar2) {
			this.wait();
		}
	}
}

