package pc.laboratorio5iii.urgente;

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

	public void escribir(String texto) throws InterruptedException {
		exclusion.lock();
		try {
			System.out.println(texto);
		} finally {
			exclusion.unlock();
		}
	}
}
