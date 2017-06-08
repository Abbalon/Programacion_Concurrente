package pc.ejemplos5iii.filosofos.locks.solucion1;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Palillo {

	private Lock exclusion;

	public Palillo() {
		exclusion = new ReentrantLock();
	}

	public void coger() {
		exclusion.lock();
	}

	public void dejar() {
		exclusion.unlock();
	}
}
