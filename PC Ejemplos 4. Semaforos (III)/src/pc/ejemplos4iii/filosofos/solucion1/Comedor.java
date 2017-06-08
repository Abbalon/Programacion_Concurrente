package pc.ejemplos4iii.filosofos.solucion1;

import java.util.concurrent.Semaphore;

class Comedor {

	private Semaphore aforo;

	public Comedor(int capacidad) {
		aforo = new Semaphore(capacidad);
	}

	public void entrar() throws InterruptedException {
		aforo.acquire();
	}

	public void salir() {
		aforo.release();
	}
}
