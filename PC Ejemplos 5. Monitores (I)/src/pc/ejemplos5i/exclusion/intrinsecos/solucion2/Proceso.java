package pc.ejemplos5i.exclusion.intrinsecos.solucion2;

class Proceso extends Thread {

	private Recurso recurso;

	public Proceso(Recurso recurso) {
		this.recurso = recurso;
	}

	public void run() {
		for (int i = 0; i < 10; i++) {
			// Seccion NO CRITICA
			for (int j = 0; j < (int) 100 * Math.random(); j++) ;

			// Seccion CRITICA
			recurso.usar();
		}
	}
}
