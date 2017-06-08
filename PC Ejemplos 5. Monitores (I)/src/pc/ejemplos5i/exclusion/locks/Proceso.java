package pc.ejemplos5i.exclusion.locks;

class Proceso extends Thread {

	private Recurso recurso;

	public Proceso(Recurso recurso) {
		this.recurso = recurso;
	}

	public void run() {
		for (int i = 0; i < 10; i++) {
			// Seccion NO CRITICA
			for (int j = 0; j < (int) 10000 * Math.random(); j++) ;

			// Seccion CRITICA
			recurso.usar();
		}
	}
}
