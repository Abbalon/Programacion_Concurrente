package pc.ejemplos5i.condicional.locks;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Condicion {

	private boolean continuar;
	private Lock exclusion;
	private Condition condicion;

	public Condicion() {
		continuar = false;
		exclusion = new ReentrantLock();
		condicion = exclusion.newCondition();
	}

	public void avisar() {
		exclusion.lock();
		try {
			continuar = true;
			condicion.signal();
		} finally {
			exclusion.unlock();
		}
	}

	public void esperar() throws InterruptedException {
		exclusion.lock();
		try {
			while (!continuar) {
				condicion.await();
			}
		} finally {
			exclusion.unlock();
		}
	}
}
