package pc.ejemplos5iii.lectescr.locks.parametrizado.solucion;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Pantalla {

	private static Pantalla pantalla = new Pantalla();

	public static Pantalla getPantalla() {
		return pantalla;
	}

	private Lock exclusion;

	private Pantalla() {
		exclusion = new ReentrantLock();
	}

	public void escribir(String texto) {
		exclusion.lock();
		try {
			System.out.println(texto);
		} finally {
			exclusion.unlock();
		}
	}
}
