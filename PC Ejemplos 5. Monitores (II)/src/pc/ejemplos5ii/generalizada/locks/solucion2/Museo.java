package pc.ejemplos5ii.generalizada.locks.solucion2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Museo {

	private final int CAPACIDAD;
	private int ocupacion;
	private Lock exclusion;
	private Condition aforo;
	private Pantalla pantalla;
	private int esperando;
	private int liberados;

	public Museo(int capacidad) {
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
			while (ocupacion == CAPACIDAD || liberados > 0) {
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
				esperando++;
				aforo.await();
				if (liberados == 0) {    // DESPERTAR ESPURIO
					esperando--;
					pantalla.escribir(
							"Despertar espurio" +
							" ***********************");
				} else {
					liberados--;
					if (liberados == 0) {
						int maxLiberar = CAPACIDAD - ocupacion - 1;
						while (esperando > 0 && liberados < maxLiberar) {
							esperando--;
							liberados++;
							aforo.signal();
						}
					}
					break;
				}
			}
			ocupacion = ocupacion + 1;
			pantalla.escribir("Entra el visitante " +
					Thread.currentThread().hashCode() +
					". Hay = " + ocupacion +
					" Esperando = " + esperando +
					" Liberados = " + liberados);
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
