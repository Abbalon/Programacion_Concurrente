package pc.ejemplos5iii.lectescr.locks.listas.solucion;

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
	private int lectoresLiberados;
	private boolean escritorLiberado;

	public AccesoLecturaEscritura() {
		lectoresEsperando = 0;
		lectoresTrabajando = 0;
		escritoresEsperando = 0;
		escritorTrabajando = false;
		exclusion = new ReentrantLock();
		permisoLectura = exclusion.newCondition();
		permisoEscritura = exclusion.newCondition();
		lectoresLiberados = 0;
		escritorLiberado = false;
	}

	public void inicioLectura() throws InterruptedException {
		exclusion.lock();
		try {
			// ES NECESARIO EL WHILE POR ESPURIOS
			while (escritorTrabajando || escritoresEsperando > 0 ||
					escritorLiberado) {
					Pantalla.getPantalla().escribir("Lector espera -" +
						" lt = " + lectoresTrabajando +
						" le = " + lectoresEsperando +
						" ll = " + lectoresLiberados +
						" et = " + escritorTrabajando +
						" ee = " + escritoresEsperando +
						" el = " + escritorLiberado);
				lectoresEsperando++;
				permisoLectura.await();
				if (lectoresLiberados == 0) {    // DESPERTAR ESPURIO
					lectoresEsperando--;
					Pantalla.getPantalla().escribir(
							"Despertar espurio de lector" +
							" ***********************");
				} else {
					lectoresLiberados--;
					break;
				}
				if (escritorTrabajando || escritoresEsperando > 0 ||
						escritorLiberado) {
					Pantalla.getPantalla().escribir(
							"Lector vuelve a esperar ************** -" +
							" lt = " + lectoresTrabajando +
							" le = " + lectoresEsperando +
							" ll = " + lectoresLiberados +
							" et = " + escritorTrabajando +
							" ee = " + escritoresEsperando +
							" el = " + escritorLiberado);
				}
			}
			if (escritorTrabajando || escritorLiberado) {
				throw new RuntimeException(
						"Lector entra cuando hay un escritor");
			}
			lectoresTrabajando++;
			Pantalla.getPantalla().escribir("Inicio lectura -" +
					" lt = " + lectoresTrabajando +
					" le = " + lectoresEsperando +
					" ll = " + lectoresLiberados +
					" et = " + escritorTrabajando +
					" ee = " + escritoresEsperando +
					" el = " + escritorLiberado);
		} finally {
			exclusion.unlock();
		}
	}

	public void finLectura() {
		exclusion.lock();
		try {
			lectoresTrabajando--;
			Pantalla.getPantalla().escribir("Fin lectura -" +
					" lt = " + lectoresTrabajando +
					" le = " + lectoresEsperando +
					" ll = " + lectoresLiberados +
					" et = " + escritorTrabajando +
					" ee = " + escritoresEsperando +
					" el = " + escritorLiberado);
			if (lectoresTrabajando == 0 && lectoresLiberados == 0) {
				if (escritoresEsperando > 0) {
					Pantalla.getPantalla().escribir(
							"Lector despierta a un escritor -" +
							" lt = " + lectoresTrabajando +
							" le = " + lectoresEsperando +
							" ll = " + lectoresLiberados +
							" et = " + escritorTrabajando +
							" ee = " + escritoresEsperando +
							" el = " + escritorLiberado);
					escritorLiberado = true;
					escritoresEsperando--;
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
			// ES NECESARIO EL WHILE POR ESPURIOS
			while (escritorTrabajando || lectoresTrabajando > 0 ||
					escritorLiberado || lectoresLiberados > 0) {
				Pantalla.getPantalla().escribir("Escritor espera -" +
						" lt = " + lectoresTrabajando +
						" le = " + lectoresEsperando +
						" ll = " + lectoresLiberados +
						" et = " + escritorTrabajando +
						" ee = " + escritoresEsperando +
						" el = " + escritorLiberado);
				escritoresEsperando++;
				permisoEscritura.await();
				if (!escritorLiberado) {    // DESPERTAR ESPURIO
					escritoresEsperando--;
					Pantalla.getPantalla().escribir(
							"Despertar espurio de escritor" +
							" ***********************");
				} else {
					escritorLiberado = false;
					break;
				}
				if (escritorTrabajando || lectoresTrabajando > 0 ||
						escritorLiberado || lectoresLiberados > 0) {
					Pantalla.getPantalla().escribir(
							"Escritor vuelve a esperar ************** -" +
							" lt = " + lectoresTrabajando +
							" le = " + lectoresEsperando +
							" ll = " + lectoresLiberados +
							" et = " + escritorTrabajando +
							" ee = " + escritoresEsperando +
							" el = " + escritorLiberado);
				}
			}
			if (escritorTrabajando || escritorLiberado) {
				throw new RuntimeException(
						"Escritor entra cuando hay otro escritor");
			} else if (lectoresTrabajando > 0 || lectoresLiberados > 0) {
				throw new RuntimeException(
						"Escritor entra cuando hay lectores");
			}
			escritorTrabajando = true;
			Pantalla.getPantalla().escribir("Inicio escritura -" +
					" lt = " + lectoresTrabajando +
					" le = " + lectoresEsperando +
					" ll = " + lectoresLiberados +
					" et = " + escritorTrabajando +
					" ee = " + escritoresEsperando +
					" el = " + escritorLiberado);
		} finally {
			exclusion.unlock();
		}
	}

	public void finEscritura() {
		exclusion.lock();
		try {
			escritorTrabajando = false;
			Pantalla.getPantalla().escribir("Fin escritura -" +
					" lt = " + lectoresTrabajando +
					" le = " + lectoresEsperando +
					" ll = " + lectoresLiberados +
					" et = " + escritorTrabajando +
					" ee = " + escritoresEsperando +
					" el = " + escritorLiberado);
			if (lectoresEsperando > 0) {
				Pantalla.getPantalla().escribir(
						"Escritor despierta a " + lectoresEsperando + " lectores -" +
						" lt = " + lectoresTrabajando +
						" le = " + lectoresEsperando +
						" ll = " + lectoresLiberados +
						" et = " + escritorTrabajando +
						" ee = " + escritoresEsperando +
						" el = " + escritorLiberado);
				lectoresLiberados = lectoresEsperando;
				lectoresEsperando = 0;
				permisoLectura.signalAll();
			} else if (escritoresEsperando > 0) {
				Pantalla.getPantalla().escribir(
						"Escritor despierta a otro escritor -" +
						" lt = " + lectoresTrabajando +
						" le = " + lectoresEsperando +
						" ll = " + lectoresLiberados +
						" et = " + escritorTrabajando +
						" ee = " + escritoresEsperando +
						" el = " + escritorLiberado);
				escritorLiberado = true;
				escritoresEsperando--;
				permisoEscritura.signal();
			}
		} finally {
			exclusion.unlock();
		}
	}
}
