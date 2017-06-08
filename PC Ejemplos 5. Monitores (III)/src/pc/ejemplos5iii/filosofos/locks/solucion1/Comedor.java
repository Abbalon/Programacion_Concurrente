package pc.ejemplos5iii.filosofos.locks.solucion1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Comedor {

	private final int CAPACIDAD;
	private int ocupacion;
	private Lock exclusion;
	private Condition aforo;
	private Pantalla pantalla;
	private int esperando;
	private int liberados;

	public Comedor(int capacidad) {
		CAPACIDAD = capacidad;
		ocupacion = 0;
		exclusion = new ReentrantLock();
		aforo = exclusion.newCondition();
		pantalla = Pantalla.getPantalla();
		esperando = 0;
		liberados = 0;
	}

	public void entrar() throws InterruptedException {
		int veces = 0;
		exclusion.lock();
		try {
			// ES NECESARIO EL WHILE POR ESPURIOS
			while (ocupacion + liberados == CAPACIDAD) {
				veces++;
				if (veces > 1) {
					pantalla.escribir("Filosofo " +
							Thread.currentThread().hashCode() +
							" vuelve a esperar la " + veces + " vez" +
							" ***********************");
				}
				esperando++;
				aforo.await();
				if (liberados == 0) {    // DESPERTAR ESPURIO
					esperando--;
					pantalla.escribir(
							"Despertar espurio" +
							" ***********************");
				} else {
					liberados--;
					break;
				}
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
			if (esperando > 0) {
				esperando--;
				liberados++;
				aforo.signal();
			}
		} finally {
			exclusion.unlock();
		}
	}
}
