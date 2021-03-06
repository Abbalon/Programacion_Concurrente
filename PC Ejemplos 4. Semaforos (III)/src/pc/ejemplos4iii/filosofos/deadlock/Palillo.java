package pc.ejemplos4iii.filosofos.deadlock;

import java.util.concurrent.Semaphore;

class Palillo {

	private Semaphore exclusion;

	public Palillo() {
		exclusion = new Semaphore(1);
	}

	public void coger() throws InterruptedException {
		exclusion.acquire();
	}

	public void dejar() {
		exclusion.release();
	}
}
