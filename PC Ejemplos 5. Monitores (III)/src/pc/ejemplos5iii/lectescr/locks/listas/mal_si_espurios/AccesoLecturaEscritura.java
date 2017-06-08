package pc.ejemplos5iii.lectescr.locks.listas.mal_si_espurios;

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
		exclusion = new ReentrantLock();
		permisoLectura = exclusion.newCondition();
		permisoEscritura = exclusion.newCondition();
	}

	public void inicioLectura() throws InterruptedException {
		exclusion.lock();
		try {
			if (escritorTrabajando || escritoresEsperando > 0) {
				do {
					Pantalla.getPantalla().escribir("Lector espera");
					lectoresEsperando++;
					permisoLectura.await();
					if (escritorTrabajando) {
						Pantalla.getPantalla().escribir(
								"Lector vuelve a esperar ************** -" +
								" lt = " + lectoresTrabajando +
								" le = " + lectoresEsperando +
								" et = " + escritorTrabajando +
								" ee = " + escritoresEsperando);
					}
				} while (escritorTrabajando);
			} else {
				lectoresTrabajando++;
			}
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
					escritoresEsperando--;
					escritorTrabajando = true;
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
			if (escritorTrabajando || lectoresTrabajando > 0) {
				do {
					Pantalla.getPantalla().escribir("Escritor espera");
					escritoresEsperando++;
					permisoEscritura.await();
					if (lectoresTrabajando > 0) {
						Pantalla.getPantalla().escribir(
								"Escritor vuelve a esperar ************** -" +
								" lt = " + lectoresTrabajando +
								" le = " + lectoresEsperando +
								" et = " + escritorTrabajando +
								" ee = " + escritoresEsperando);
					}
				} while (lectoresTrabajando > 0);
			} else {
				escritorTrabajando = true;
			}
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
				lectoresTrabajando = lectoresEsperando;
				lectoresEsperando = 0;
				permisoLectura.signalAll();
			} else if (escritoresEsperando > 0) {
				Pantalla.getPantalla().escribir(
						"Despierto a un escritor -" +
						" lt = " + lectoresTrabajando +
						" le = " + lectoresEsperando +
						" et = " + escritorTrabajando +
						" ee = " + escritoresEsperando);
				escritoresEsperando--;
				escritorTrabajando = true;
				permisoEscritura.signal();
			}
		} finally {
			exclusion.unlock();
		}
	}
}
