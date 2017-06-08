package pc.ejemplos5iii.lectescr.rwlocks.listas.solucion;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class AccesoLecturaEscritura {

	private ReadWriteLock exclusion;

	public AccesoLecturaEscritura() {
		exclusion = new ReentrantReadWriteLock();
	}

	public void inicioLectura() {
		exclusion.readLock().lock();
		Pantalla.getPantalla().escribir("Inicio lectura");
	}

	public void finLectura() {
		Pantalla.getPantalla().escribir("Fin lectura");
		exclusion.readLock().unlock();
	}

	public void inicioEscritura() {
		exclusion.writeLock().lock();
		Pantalla.getPantalla().escribir("Inicio escritura");
	}

	public void finEscritura() {
		Pantalla.getPantalla().escribir("Fin escritura");
		exclusion.writeLock().unlock();
	}
}
