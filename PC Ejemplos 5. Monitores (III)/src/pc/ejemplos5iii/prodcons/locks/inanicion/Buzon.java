package pc.ejemplos5iii.prodcons.locks.inanicion;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Buzon<Dato> {

	private final int CAPACIDAD;
	private int numDatos;
	private Buffer<Dato> buffer;
	private Lock exclusion;
	private Condition noVacio;
	private Condition noLleno;
	private Pantalla pantalla;

	public Buzon(int capacidad) {
		CAPACIDAD = capacidad;
		numDatos = 0;
		buffer = new Buffer<Dato>(CAPACIDAD);
		exclusion = new ReentrantLock(true);    // AUN SIENDO JUSTO
		noVacio = exclusion.newCondition();
		noLleno = exclusion.newCondition();
		pantalla = Pantalla.getPantalla();
	}

	public void enviar(Dato dato) throws InterruptedException {
		int veces = 0;
		exclusion.lock();
		try {
			// ES NECESARIO EL WHILE AUN SIENDO JUSTO EL CERROJO
			while (numDatos == CAPACIDAD) {
				veces++;
				if (veces == 1) {
					pantalla.escribir("Productor " +
							Thread.currentThread().hashCode() +
							" espera");
				} else {
					pantalla.escribir("Productor " +
							Thread.currentThread().hashCode() +
							" vuelve a esperar la " + veces + " vez" +
							" ***********************");
				}
				noLleno.await();
			}
			buffer.meter(dato);
			numDatos++;
			noVacio.signal();
			pantalla.escribir("Productor " +
					Thread.currentThread().hashCode() +
					" mete. Datos = " + numDatos);
		} finally {
			exclusion.unlock();
		}
	}

	public Dato recibir() throws InterruptedException {
		int veces = 0;
		exclusion.lock();
		try {
			// ES NECESARIO EL WHILE AUN SIENDO JUSTO EL CERROJO
			while (numDatos == 0) {
				veces++;
				if (veces == 1) {
					pantalla.escribir("Consumidor " +
							Thread.currentThread().hashCode() +
							" espera");
				} else {
					pantalla.escribir("Consumidor " +
							Thread.currentThread().hashCode() +
							" vuelve a esperar la " + veces + " vez" +
							" ***********************");
				}
				noVacio.await();
			}
			Dato dato = buffer.sacar();
			numDatos--;
			noLleno.signal();
			pantalla.escribir("Consumidor " +
					Thread.currentThread().hashCode() +
					" saca. Datos = " + numDatos);
			return dato;
		} finally {
			exclusion.unlock();
		}
	}
}
