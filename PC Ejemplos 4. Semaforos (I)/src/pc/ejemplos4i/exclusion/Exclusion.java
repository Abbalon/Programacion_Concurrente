package pc.ejemplos4i.exclusion;

import java.util.concurrent.Semaphore;

class Exclusion {

	private Semaphore exclusion;

	public Exclusion() {
		exclusion = new Semaphore(1);
	}

	public void obtener() throws InterruptedException {
		exclusion.acquire();
	}

	public void liberar() {
		exclusion.release();
	}
}
