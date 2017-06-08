package pc.ejemplos5i.exclusion.locks;

class Aplicacion {

	public static void main(String[] args) {
		Recurso recurso = new Recurso();
		final int NUM_PROCESOS = 5;
		Proceso[] procesos = new Proceso[NUM_PROCESOS];
		for (int i = 0; i < procesos.length; i++) {
			procesos[i] = new Proceso(recurso);
		}
		for (int i = 0; i < procesos.length; i++) {
			procesos[i].start();
		}
	}
}