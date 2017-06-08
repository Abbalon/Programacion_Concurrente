package pc.ejemplos5iii.lectescr.rwlocks.listas.solucion;

class BaseDatos {

	private int[] lista;
	private AccesoLecturaEscritura acceso;

	public BaseDatos() {
		lista = new int[0];
		acceso = new AccesoLecturaEscritura();
	}

	public int[] leer() {
		acceso.inicioLectura();
		try {
			int[] lista = new int[this.lista.length];
			for (int i = 0; i < lista.length; i++) {
				lista[i] = this.lista[i];
			}
			try {
				Thread.sleep((long) (10 * Math.random()));
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			return lista;
		} finally {
			acceso.finLectura();
		}
	}

	public void escribir(int[] lista) {
		acceso.inicioEscritura();
		try {
			this.lista = new int[lista.length];
			for (int i = 0; i < lista.length; i++) {
				this.lista[i] = lista[i];
			}
			try {
				Thread.sleep((long) (10 * Math.random()));
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		} finally {
			acceso.finEscritura();
		}
	}
}
