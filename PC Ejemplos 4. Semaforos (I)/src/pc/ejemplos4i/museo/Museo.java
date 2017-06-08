package pc.ejemplos4i.museo;

import java.util.concurrent.Semaphore;

class Museo {

	private int ocupacion;
	private Semaphore exclusion;
	private Pantalla pantalla;

	public Museo() {
		ocupacion = 0;
		exclusion = new Semaphore(1);
		pantalla = Pantalla.getPantalla();
	}

	public void entrar() throws InterruptedException {
		exclusion.acquire();
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
	}
}
