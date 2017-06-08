package pc.ejemplos4i.exclusion;

class Proceso extends Thread {

	private Recurso recurso;

	public Proceso(Recurso recurso) {
		this.recurso = recurso;
	}

	public void run() {
		try {
			for (int i = 0; i < 10; i++) {
				// Seccion NO CRITICA
				for (int j = 0; j < (int) 10000 * Math.random(); j++) ;

				// Seccion CRITICA
				recurso.usar();
			}
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
}
