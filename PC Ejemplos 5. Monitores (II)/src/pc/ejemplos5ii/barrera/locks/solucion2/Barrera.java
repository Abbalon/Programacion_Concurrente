package pc.ejemplos5ii.barrera.locks.solucion2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Barrera {

	private boolean continuar1;
	private boolean continuar2;
	private Lock exclusion;
	private Condition condicion;

	public Barrera() {
		continuar1 = false;
		continuar2 = false;
		exclusion = new ReentrantLock();
		condicion = exclusion.newCondition();
	}

	public void esperar1() throws InterruptedException {
		exclusion.lock();
		try {
			continuar2 = true;
			condicion.signal();
			while (!continuar1) {
				condicion.await();
			}
		} finally {
			exclusion.unlock();
		}
	}

	public void esperar2() throws InterruptedException {
		exclusion.lock();
		try {
			continuar1 = true;
			condicion.signal();
			while (!continuar2) {
				condicion.await();
			}
		} finally {
			exclusion.unlock();
		}
	}
}