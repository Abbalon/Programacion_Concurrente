package pc.ejemplos5iii.lectescr.intrinsecos.listas.inanicion;

class BaseDatos {

	private int[] lista;
	private AccesoLecturaEscritura acceso;

	public BaseDatos() {
		lista = new int[0];
		acceso = new AccesoLecturaEscritura();
	}

	public int[] leer() throws InterruptedException {
		acceso.inicioLectura();
		try {
			int[] lista = new int[this.lista.length];
			for (int i = 0; i < lista.length; i++) {
				lista[i] = this.lista[i];
			}
			return lista;
		} finally {
			acceso.finLectura();
		}
	}

	public void escribir(int[] lista) throws InterruptedException {
		acceso.inicioEscritura();
		try {
			this.lista = new int[lista.length];
			for (int i = 0; i < lista.length; i++) {
				this.lista[i] = lista[i];
			}
		} finally {
			acceso.finEscritura();
		}
	}
}
