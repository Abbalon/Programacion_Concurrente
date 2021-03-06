package pc.ejemplos5iii.prodcons.intrinsecos.inanicion;

import java.util.List;

class Aplicacion {

	public static void main(String[] args) {
		final int NUM_GENERADORES = 2;
		final int NUM_ORDENADORES = 2;
		final int CAPACIDAD = 1;
		Buzon<List<Integer>> buzon = new Buzon<List<Integer>>(CAPACIDAD);
		Generador[] generadores = new Generador[NUM_GENERADORES];
		Ordenador[] ordenadores = new Ordenador[NUM_ORDENADORES];
		for (int i = 0; i < generadores.length; i++) {
			generadores[i] = new Generador(buzon);
			generadores[i].start();
		}
		for (int i = 0; i < ordenadores.length; i++) {
			ordenadores[i] = new Ordenador(buzon);
			ordenadores[i].start();
		}
	}
}
