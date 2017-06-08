package pc.ejemplos5i.museo.locks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Museo {

	private int ocupacion;
	private Lock exclusion;
	Pantalla pantalla;

	public Museo() {
		ocupacion = 0;
		exclusion = new ReentrantLock();
		pantalla = Pantalla.getPantalla();
	}

	public void entrar() {
		exclusion.lock();
		try {
			ocupacion = ocupacion + 1;
			pantalla.escribir("Entra un visitante. Hay " + ocupacion);
		} finally {
			exclusion.unlock();
		}
	}

	public void salir() {
		exclusion.lock();
		try {
			ocupacion = ocupacion - 1;
			pantalla.escribir("Sale un visitante. Hay " + ocupacion);
		} finally {
			exclusion.unlock();
		}
	}
}
