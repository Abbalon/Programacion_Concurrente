package pc.ejemplos4ii.prodcons;

import java.util.concurrent.Semaphore;

class Buzon<Dato> {

	private Buffer<Dato> buffer;
	private Semaphore exclusion;
	private Semaphore numDatos;
	private Semaphore numHuecos;

	public Buzon(int capacidad) {
		exclusion = new Semaphore(1);
		buffer = new Buffer<Dato>(capacidad);
		numDatos = new Semaphore(0);
		numHuecos = new Semaphore(capacidad);
	}

	public void enviar(Dato dato) throws InterruptedException {
		numHuecos.acquire();	// INTERCAMBIAR ESTAS OPERACIONES
		exclusion.acquire();	// PRODUCE INTERBLOQUEO
		try {
			buffer.meter(dato);
		} finally {
			exclusion.release();
		}
		numDatos.release();
	}

	public Dato recibir() throws InterruptedException {
		numDatos.acquire();		// INTERCAMBIAR ESTAS OPERACIONES
		exclusion.acquire();	// PRODUCE INTERBLOQUEO
		Dato dato = null;
		try {
			dato = buffer.sacar();
		} finally {
			exclusion.release();
		}
		numHuecos.release();
		return dato;
	}
}
