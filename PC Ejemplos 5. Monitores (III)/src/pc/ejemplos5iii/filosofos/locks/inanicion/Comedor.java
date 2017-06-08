package pc.ejemplos5iii.filosofos.locks.inanicion;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Comedor {

	private final int CAPACIDAD;
	private int ocupacion;
	private Lock exclusion;
	private Condition aforo;
	private Pantalla pantalla;

	public Comedor(int capacidad) {
		CAPACIDAD = capacidad;
		ocupacion = 0;
		exclusion = new ReentrantLock();
		aforo = exclusion.newCondition();
		pantalla = Pantalla.getPantalla();
	}

	public void entrar() throws InterruptedException {
		int veces = 0;
		exclusion.lock();
		try {
			while (ocupacion == CAPACIDAD) {    // ES NECESARIO EL WHILE
				veces++;
				if (veces > 1) {
					pantalla.escribir("Filosofo " +
							Thread.currentThread().hashCode() +
							" vuelve a esperar la " + veces + " vez" +
							" ***********************");
				}
				aforo.await();
			}
			ocupacion = ocupacion + 1;
		} finally {
			exclusion.unlock();
		}
	}

	public void salir() {
		exclusion.lock();
		try {
			ocupacion = ocupacion - 1;
			aforo.signal();
		} finally {
			exclusion.unlock();
		}
	}
}
