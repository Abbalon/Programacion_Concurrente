package pc.laboratorio5ii;

import java.util.concurrent.locks.Lock;
import pc.util.concurrent.locks.UrgentQueueReentrantLock;

class Pantalla {

	private static Pantalla pantalla = new Pantalla();

	public static Pantalla getPantalla() {
		return pantalla;
	}

	private Lock exclusion;

	private Pantalla() {
		exclusion = new UrgentQueueReentrantLock();
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
