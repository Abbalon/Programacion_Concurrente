package pc.ejemplos4i.condicional;

import java.util.concurrent.Semaphore;

class Condicion {

	private Semaphore continuar;

	public Condicion() {
		continuar = new Semaphore(0);
	}

	public void avisar() {
		continuar.release();
	}

	public void esperar() throws InterruptedException {
		continuar.acquire();
	}
}
