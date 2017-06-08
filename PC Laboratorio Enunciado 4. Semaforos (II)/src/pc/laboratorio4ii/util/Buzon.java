package pc.laboratorio4ii.util;

import java.util.concurrent.Semaphore;

public class Buzon<Dato> {

	private Buffer<Dato> buffer;
	private Semaphore numDatos;
	private Semaphore numHuecos;
	private Semaphore exclusion;

	public Buzon(int capacidad) {
		buffer = new Buffer<Dato>(capacidad);
		numDatos = new Semaphore(0);
		numHuecos = new Semaphore(capacidad);
		exclusion = new Semaphore(1);
	}

	public void enviar(Dato dato) throws InterruptedException {
		numHuecos.acquire();
		exclusion.acquire();
		buffer.meter(dato);
		exclusion.release();
		numDatos.release();
	}

	public Dato recibir() throws InterruptedException {
		numDatos.acquire();
		exclusion.acquire();
		Dato dato = buffer.sacar();
		exclusion.release();
		numHuecos.release();
		return dato;
	}
}
