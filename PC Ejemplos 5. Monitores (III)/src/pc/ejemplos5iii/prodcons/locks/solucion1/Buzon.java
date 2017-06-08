package pc.ejemplos5iii.prodcons.locks.solucion1;

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
	private int productoresEsperando;
	private int consumidoresEsperando;
	private int productoresLiberados;
	private int consumidoresLiberados;
	private Pantalla pantalla;

	public Buzon(int capacidad) {
		CAPACIDAD = capacidad;
		numDatos = 0;
		buffer = new Buffer<Dato>(CAPACIDAD);
		exclusion = new ReentrantLock();
		noVacio = exclusion.newCondition();
		noLleno = exclusion.newCondition();
		pantalla = Pantalla.getPantalla();
		productoresEsperando = 0;
		consumidoresEsperando = 0;
		productoresLiberados = 0;
		consumidoresLiberados = 0;
	}

	public void enviar(Dato dato) throws InterruptedException {
		int veces = 0;
		exclusion.lock();
		try {
			// ES NECESARIO EL WHILE POR ESPURIOS
			while (numDatos + productoresLiberados == CAPACIDAD) {
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
				productoresEsperando++;
				noLleno.await();
				if (productoresLiberados == 0) {    // DESPERTAR ESPURIO
					productoresEsperando--;
					pantalla.escribir(
							"Despertar espurio de productor" +
							" ***********************");
				} else {
					productoresLiberados--;
					break;
				}
			}
			buffer.meter(dato);
			numDatos++;
			if (consumidoresEsperando > 0) {
				consumidoresEsperando--;
				consumidoresLiberados++;
				noVacio.signal();
			}
			pantalla.escribir("Productor " +
					Thread.currentThread().hashCode() +
					" mete. Datos = " + numDatos +
					" productoresEsperando = " + productoresEsperando +
					" productoresLiberados = " + productoresLiberados +
					" consumidoresEsperando = " + consumidoresEsperando +
					" consumidoresLiberados = " + consumidoresLiberados);
		} finally {
			exclusion.unlock();
		}
	}

	public Dato recibir() throws InterruptedException {
		int veces = 0;
		exclusion.lock();
		try {
			while (numDatos - consumidoresLiberados == 0) {
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
				consumidoresEsperando++;
				noVacio.await();
				if (consumidoresLiberados == 0) {    // DESPERTAR ESPURIO
					consumidoresEsperando--;
					pantalla.escribir(
							"Despertar espurio de consumidor" +
							" ***********************");
				} else {
					consumidoresLiberados--;
					break;
				}
			}
			Dato dato = buffer.sacar();
			numDatos--;
			if (productoresEsperando > 0) {
				productoresEsperando--;
				productoresLiberados++;
				noLleno.signal();
			}
			pantalla.escribir("Consumidor " +
					Thread.currentThread().hashCode() +
					" saca. Datos = " + numDatos +
					" productoresEsperando = " + productoresEsperando +
					" productoresLiberados = " + productoresLiberados +
					" consumidoresEsperando = " + consumidoresEsperando +
					" consumidoresLiberados = " + consumidoresLiberados);
			return dato;
		} finally {
			exclusion.unlock();
		}
	}
}
