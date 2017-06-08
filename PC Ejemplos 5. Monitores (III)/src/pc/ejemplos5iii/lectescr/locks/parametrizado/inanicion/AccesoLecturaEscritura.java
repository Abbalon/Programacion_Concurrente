package pc.ejemplos5iii.lectescr.locks.parametrizado.inanicion;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class AccesoLecturaEscritura {

	private int lectoresEsperando;
	private int lectoresTrabajando;
	private int escritoresEsperando;
	private boolean escritorTrabajando;
	private Lock exclusion;
	private Condition permisoLectura;
	private Condition permisoEscritura;

	public AccesoLecturaEscritura() {
		lectoresEsperando = 0;
		lectoresTrabajando = 0;
		escritoresEsperando = 0;
		escritorTrabajando = false;
		exclusion = new ReentrantLock(true);         // AUN SIENDO JUSTO
		permisoLectura = exclusion.newCondition();
		permisoEscritura = exclusion.newCondition();
	}

	public void inicioLectura() throws InterruptedException {
		exclusion.lock();
		try {
			// ES NECESARIO EL WHILE AUN SIENDO JUSTO EL CERROJO
			while (escritorTrabajando || escritoresEsperando > 0) {
				Pantalla.getPantalla().escribir("Lector espera");
				lectoresEsperando++;
				permisoLectura.await();
				lectoresEsperando--;
				if (!escritorTrabajando) {
					break;
				} else {
					Pantalla.getPantalla().escribir(
							"Lector vuelve a esperar ************** -" +
							" lt = " + lectoresTrabajando +
							" le = " + lectoresEsperando +
							" et = " + escritorTrabajando +
							" ee = " + escritoresEsperando);
				}
			}
			if (escritorTrabajando) {
				throw new RuntimeException(
						"Lector entra cuando hay un escritor");
			}
			lectoresTrabajando++;
			Pantalla.getPantalla().escribir("Inicio lectura");
		} finally {
			exclusion.unlock();
		}
	}

	public void finLectura() {
		exclusion.lock();
		try {
			Pantalla.getPantalla().escribir("Fin lectura");
			lectoresTrabajando--;
			if (lectoresTrabajando == 0) {
				if (escritoresEsperando > 0) {
					Pantalla.getPantalla().escribir(
							"Despierto a un escritor -" +
							" lt = " + lectoresTrabajando +
							" le = " + lectoresEsperando +
							" et = " + escritorTrabajando +
							" ee = " + escritoresEsperando);
					permisoEscritura.signal();
				}
			}
		} finally {
			exclusion.unlock();
		}
	}

	public void inicioEscritura() throws InterruptedException {
		exclusion.lock();
		try {
			// ES NECESARIO EL WHILE AUN SIENDO JUSTO EL CERROJO
			while (escritorTrabajando || lectoresTrabajando > 0) {
				Pantalla.getPantalla().escribir("Escritor espera");
				escritoresEsperando++;
				permisoEscritura.await();
				escritoresEsperando--;
				if (escritorTrabajando || lectoresTrabajando > 0) {
					Pantalla.getPantalla().escribir(
							"Escritor vuelve a esperar ************** -" +
							" lt = " + lectoresTrabajando +
							" le = " + lectoresEsperando +
							" et = " + escritorTrabajando +
							" ee = " + escritoresEsperando);
				}
			}
			if (escritorTrabajando) {
				throw new RuntimeException(
						"Escritor entra cuando hay otro escritor");
			} else if (lectoresTrabajando > 0) {
				throw new RuntimeException(
						"Escritor entra cuando hay lectores");
			}
			escritorTrabajando = true;
			Pantalla.getPantalla().escribir("Inicio escritura");
		} finally {
			exclusion.unlock();
		}
	}

	public void finEscritura() {
		exclusion.lock();
		try {
			Pantalla.getPantalla().escribir("Fin escritura");
			escritorTrabajando = false;
			if (lectoresEsperando > 0) {
				Pantalla.getPantalla().escribir(
						"Despierto a " + lectoresEsperando + " lectores -" +
						" lt = " + lectoresTrabajando +
						" le = " + lectoresEsperando +
						" et = " + escritorTrabajando +
						" ee = " + escritoresEsperando);
				permisoLectura.signalAll();
			} else if (escritoresEsperando > 0) {
				Pantalla.getPantalla().escribir(
						"Despierto a un escritor -" +
						" lt = " + lectoresTrabajando +
						" le = " + lectoresEsperando +
						" et = " + escritorTrabajando +
						" ee = " + escritoresEsperando);
				permisoEscritura.signal();
			}
		} finally {
			exclusion.unlock();
		}
	}
}
