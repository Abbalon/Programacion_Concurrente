package pc.ejemplos5i.exclusion.locks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Recurso {

	private Lock exclusion;

	public Recurso() {
		exclusion = new ReentrantLock();
	}

	public void usar() {
		exclusion.lock();
		try {
			System.out.println("Inicio SC");
			for (int j = 0; j < (int) 10000 * Math.random(); j++) ;
			System.out.println("Fin SC");
		} finally {
			exclusion.unlock();
		}
	}
}
