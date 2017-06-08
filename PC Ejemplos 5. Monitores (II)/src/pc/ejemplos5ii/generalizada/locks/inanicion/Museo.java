package pc.ejemplos5ii.generalizada.locks.inanicion;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Museo {

	private final int CAPACIDAD;
	private int ocupacion;
	private Lock exclusion;
	private Condition aforo;
	private Pantalla pantalla;

	public Museo(int capacidad) {
		CAPACIDAD = capacidad;
		ocupacion = 0;
		exclusion = new ReentrantLock(true);    // AUN SIENDO JUSTO
		aforo = exclusion.newCondition();
		pantalla = Pantalla.getPantalla();
	}

	public void entrar() throws InterruptedException {
		int veces = 0;
		exclusion.lock();
		try {
			while (ocupacion == CAPACIDAD) {    // ES NECESARIO EL WHILE
				veces++;
				if (veces == 1) {
					pantalla.escribir("Visitante " +
							Thread.currentThread().hashCode() +
							" espera");
				} else {
					pantalla.escribir("Visitante " +
							Thread.currentThread().hashCode() +
							" vuelve a esperar la " + veces + " vez" +
							" ***********************");
				}
				aforo.await();
			}
			ocupacion = ocupacion + 1;
			pantalla.escribir("Entra el visitante " +
					Thread.currentThread().hashCode() +
					". Hay = " + ocupacion);
			if (ocupacion > CAPACIDAD) {
				throw new RuntimeException("La ocupacion excede del aforo");
			}
		} finally {
			exclusion.unlock();
		}
	}

	public void salir() {
		exclusion.lock();
		try {
			ocupacion = ocupacion - 1;
			pantalla.escribir("Sale un visitante. Hay " + ocupacion);
			aforo.signal();
		} finally {
			exclusion.unlock();
		}
	}
}
