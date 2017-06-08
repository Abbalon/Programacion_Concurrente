package pc.ejemplos5iii.lectescr.intrinsecos.listas.inanicion;

class AccesoLecturaEscritura {

	private int lectoresEsperando;
	private int lectoresTrabajando;
	private int escritoresEsperando;
	private boolean escritorTrabajando;

	public AccesoLecturaEscritura() {
		lectoresEsperando = 0;
		lectoresTrabajando = 0;
		escritoresEsperando = 0;
		escritorTrabajando = false;
	}

	public synchronized void inicioLectura() throws InterruptedException {
		// ES NECESARIO EL WHILE
		while (escritorTrabajando || escritoresEsperando > 0) {
			Pantalla.getPantalla().escribir("Lector espera");
			lectoresEsperando++;
			this.wait();
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
	}

	public synchronized void finLectura() {
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
				this.notifyAll();
			}
		}
	}

	public synchronized void inicioEscritura() throws InterruptedException {
		// ES NECESARIO EL WHILE
		while (escritorTrabajando || lectoresTrabajando > 0) {
			Pantalla.getPantalla().escribir("Escritor espera");
			escritoresEsperando++;
			this.wait();
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
	}

	public synchronized void finEscritura() {
		Pantalla.getPantalla().escribir("Fin escritura");
		escritorTrabajando = false;
		if (lectoresEsperando > 0) {
			Pantalla.getPantalla().escribir(
					"Despierto a " + lectoresEsperando + " lectores -" +
					" lt = " + lectoresTrabajando +
					" le = " + lectoresEsperando +
					" et = " + escritorTrabajando +
					" ee = " + escritoresEsperando);
			this.notifyAll();
		} else if (escritoresEsperando > 0) {
			Pantalla.getPantalla().escribir(
					"Despierto a un escritor -" +
					" lt = " + lectoresTrabajando +
					" le = " + lectoresEsperando +
					" et = " + escritorTrabajando +
					" ee = " + escritoresEsperando);
			this.notifyAll();
		}
	}
}
