package pc.ejemplos5iii.filosofos.intrinsecos.solucion1;

import java.util.concurrent.Semaphore;

class Pantalla {

	private static Pantalla pantalla = new Pantalla();

	public static Pantalla getPantalla() {
		return pantalla;
	}

	private Semaphore exclusion;

	private Pantalla() {
		exclusion = new Semaphore(1);
	}

	public void escribir(String texto) throws InterruptedException {
		exclusion.acquire();
		System.out.println(texto);
		exclusion.release();
	}
}
