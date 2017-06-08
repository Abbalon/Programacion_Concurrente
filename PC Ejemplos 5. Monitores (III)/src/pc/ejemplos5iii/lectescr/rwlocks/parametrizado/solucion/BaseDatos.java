package pc.ejemplos5iii.lectescr.rwlocks.parametrizado.solucion;

class BaseDatos<Tipo> {

	private Datos<Tipo> datos;
	private AccesoLecturaEscritura acceso;

	public BaseDatos(Datos<Tipo> datos) {
		this.datos = datos;
		acceso = new AccesoLecturaEscritura();
	}

	public Tipo leer() {
		acceso.inicioLectura();
		try {
			Tipo datos = this.datos.leer();
			try {
				Thread.sleep((long) (10 * Math.random()));
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			return datos;
		} finally {
			acceso.finLectura();
		}
	}

	public void escribir(Tipo datos) {
		acceso.inicioEscritura();
		try {
			this.datos.escribir(datos);
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
