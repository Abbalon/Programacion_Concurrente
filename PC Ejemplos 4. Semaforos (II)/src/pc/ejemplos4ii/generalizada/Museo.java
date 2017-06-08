package pc.ejemplos4ii.generalizada;

import java.util.concurrent.Semaphore;

class Museo {

	private int ocupacion;
	private Semaphore exclusion;
	private Semaphore aforo;
	private Pantalla pantalla;

	public Museo(int capacidad) {
		ocupacion = 0;
		exclusion = new Semaphore(1);
		aforo = new Semaphore(capacidad);
		pantalla = Pantalla.getPantalla();
	}

	public void entrar() throws InterruptedException {
		aforo.acquire();		// INTERCAMBIAR ESTAS OPERACIONES
		exclusion.acquire();	// PRODUCE INTERBLOQUEO
		try {
			ocupacion = ocupacion + 1;
			pantalla.escribir("Entra un visitante. Hay " + ocupacion);
		} finally {
			exclusion.release();
		}
	}

	public void salir() throws InterruptedException {
		exclusion.acquire();
		try {
			ocupacion = ocupacion - 1;
			pantalla.escribir("Sale un visitante. Hay " + ocupacion);
		} finally {
			exclusion.release();
		}
		aforo.release();
	}
}
