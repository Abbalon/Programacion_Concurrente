package pc.ejemplos5ii.ciclica.locks.solucion1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class BarreraCiclica {

	private boolean continuar1;
	private boolean continuar2;
	private Lock exclusion;
	private Condition condicion;

	public BarreraCiclica() {
		continuar1 = false;
		continuar2 = false;
		exclusion = new ReentrantLock();
		condicion = exclusion.newCondition();
	}

	public void esperar1() throws InterruptedException {
		exclusion.lock();
		try {
			continuar2 = true;
			if (!continuar1) {
				while (!continuar1) {
					condicion.await();
				}
				continuar1 = false;
				condicion.signal();
			} else {
				continuar1 = false;
				condicion.signal();
				while (continuar2) {
					condicion.await();
				}
			}
		} finally {
			exclusion.unlock();
		}
	}

	public void esperar2() throws InterruptedException {
		exclusion.lock();
		try {
			continuar1 = true;
			if (!continuar2) {
				while (!continuar2) {
					condicion.await();
				}
				continuar2 = false;
				condicion.signal();
			} else {
				continuar2 = false;
				condicion.signal();
				while (continuar1) {
					condicion.await();
				}
			}
		} finally {
			exclusion.unlock();
		}
	}
}
