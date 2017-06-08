package pc.ejemplos4iii.lectescr.listas;

import java.util.concurrent.Semaphore;

class AccesoLecturaEscritura {

	private int lectoresEsperando;
	private int lectoresTrabajando;
	private int escritoresEsperando;
	private boolean escritorTrabajando;
	private Semaphore exclusion;
	private Semaphore permisoLectura;
	private Semaphore permisoEscritura;

	public AccesoLecturaEscritura() {
		lectoresEsperando = 0;
		lectoresTrabajando = 0;
		escritoresEsperando = 0;
		escritorTrabajando = false;
		exclusion = new Semaphore(1);
		permisoLectura = new Semaphore(0);
		permisoEscritura = new Semaphore(0);
	}

	public void inicioLectura() throws InterruptedException {
		exclusion.acquire();
		if (escritorTrabajando || escritoresEsperando > 0) {
			Pantalla.getPantalla().escribir("Lector espera");
			lectoresEsperando++;
			exclusion.release();
			permisoLectura.acquire();
		} else {
			lectoresTrabajando++;
			exclusion.release();
		}
		Pantalla.getPantalla().escribir("Inicio lectura");
	}

	public void finLectura() throws InterruptedException {
		exclusion.acquire();
		Pantalla.getPantalla().escribir("Fin lectura");
		lectoresTrabajando--;
		if (lectoresTrabajando == 0) {
			if (escritoresEsperando > 0) {
				escritoresEsperando--;
				escritorTrabajando = true;
				permisoEscritura.release();
			}
		}
		exclusion.release();
	}

	public void inicioEscritura() throws InterruptedException {
		exclusion.acquire();
		if (escritorTrabajando || lectoresTrabajando > 0) {
			Pantalla.getPantalla().escribir("Escritor espera");
			escritoresEsperando++;
			exclusion.release();
			permisoEscritura.acquire();
		} else {
			escritorTrabajando = true;
			exclusion.release();
		}
		Pantalla.getPantalla().escribir("Inicio escritura");
	}

	public void finEscritura() throws InterruptedException {
		exclusion.acquire();
		Pantalla.getPantalla().escribir("Fin escritura");
		escritorTrabajando = false;
		if (lectoresEsperando > 0) {
			while (lectoresEsperando > 0) {
				lectoresEsperando--;
				lectoresTrabajando++;
				permisoLectura.release();
			}
		} else if (escritoresEsperando > 0) {
			escritoresEsperando--;
			escritorTrabajando = true;
			permisoEscritura.release();
		}
		exclusion.release();
	}
}
