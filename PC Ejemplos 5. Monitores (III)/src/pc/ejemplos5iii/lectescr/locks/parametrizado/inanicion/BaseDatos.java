package pc.ejemplos5iii.lectescr.locks.parametrizado.inanicion;

class BaseDatos<Tipo> {

	private Datos<Tipo> datos;
	private AccesoLecturaEscritura acceso;

	public BaseDatos(Datos<Tipo> datos) {
		this.datos = datos;
		acceso = new AccesoLecturaEscritura();
	}

	public Tipo leer() throws InterruptedException {
		acceso.inicioLectura();
		try {
			Tipo datos = this.datos.leer();
			return datos;
		} finally {
			acceso.finLectura();
		}
	}

	public void escribir(Tipo datos) throws InterruptedException {
		acceso.inicioEscritura();
		try {
			this.datos.escribir(datos);
		} finally {
			acceso.finEscritura();
		}
	}
}
