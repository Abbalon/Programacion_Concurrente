package pc.ejemplos5iii.lectescr.intrinsecos.listas.no_solucion;

class AccesoLecturaEscritura {

	private int lectoresEsperando;
	private int lectoresTrabajando;
	private int escritoresEsperando;
	private boolean escritorTrabajando;
	private int lectoresLiberados;
	private boolean escritorLiberado;

	public AccesoLecturaEscritura() {
		lectoresEsperando = 0;
		lectoresTrabajando = 0;
		escritoresEsperando = 0;
		escritorTrabajando = false;
		lectoresLiberados = 0;
		escritorLiberado = false;
	}

	public synchronized void inicioLectura() throws InterruptedException {
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
			this.wait();
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
	}

	public synchronized void finLectura() {
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
				lectoresEsperando = 0;
				escritoresEsperando = 0;
				this.notifyAll();
			}
		}
	}

	public synchronized void inicioEscritura() throws InterruptedException {
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
			this.wait();
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
	}

	public synchronized void finEscritura() {
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
			escritoresEsperando = 0;
			this.notifyAll();
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
			lectoresEsperando = 0;
			escritoresEsperando = 0;
			this.notifyAll();
		}
	}
}
