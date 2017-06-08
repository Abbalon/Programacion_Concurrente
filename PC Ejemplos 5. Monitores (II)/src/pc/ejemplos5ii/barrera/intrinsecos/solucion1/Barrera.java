package pc.ejemplos5ii.barrera.intrinsecos.solucion1;

class Barrera {

	private boolean continuar1;
	private boolean continuar2;

	public Barrera() {
		continuar1 = false;
		continuar2 = false;
	}

	public synchronized void esperar1() throws InterruptedException {
		continuar2 = true;
		if (!continuar1) {
			while (!continuar1) {
				this.wait();
			}
		} else {
			this.notify();
		}
	}

	public synchronized void esperar2() throws InterruptedException {
		continuar1 = true;
		if (!continuar2) {
			while (!continuar2) {
				this.wait();
			}
		} else {
			this.notify();
		}
	}
}

