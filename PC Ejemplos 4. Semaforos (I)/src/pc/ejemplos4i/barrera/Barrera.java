package pc.ejemplos4i.barrera;

import java.util.concurrent.Semaphore;

class Barrera {

	private Semaphore continuar1;
	private Semaphore continuar2;

	public Barrera() {
		continuar1 = new Semaphore(0);
		continuar2 = new Semaphore(0);
	}

	public void esperar1() throws InterruptedException {
		continuar2.release();
		continuar1.acquire();
	}

	public void esperar2() throws InterruptedException {
		continuar1.release();
		continuar2.acquire();
	}
}

