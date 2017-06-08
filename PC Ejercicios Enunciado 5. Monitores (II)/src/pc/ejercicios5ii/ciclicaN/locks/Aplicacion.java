package pc.ejercicios5ii.ciclicaN.locks;

class Aplicacion {

	public static void main(String[] args) {
		final int NUM_PROCESOS = 5;
		BarreraCiclica barrera = new BarreraCiclica(NUM_PROCESOS);
		Proceso[] procesos = new Proceso[NUM_PROCESOS];
		for (int i = 0; i < procesos.length; i++) {
			procesos[i] = new Proceso(barrera);
		}
		for (int i = 0; i < procesos.length; i++) {
			procesos[i].start();
		}
	}
}

