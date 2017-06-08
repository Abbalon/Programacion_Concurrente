package pc.ejemplos5iii.lectescr.locks.parametrizado.solucion;

import java.util.ArrayList;

class AplicacionListas {

	public static void main(String[] args) {
		final int NUM_ESCRITORES = 10;
		final int NUM_LECTORES = 10;

		ArrayList<Integer> lista = new ArrayList<Integer>();
		DatosLista<Integer> datosLista = new DatosLista<Integer>(lista);
		BaseDatos<ArrayList<Integer>> baseDatos =
				new BaseDatos<ArrayList<Integer>>(datosLista);

		EscritorListas[] escritores = new EscritorListas[NUM_ESCRITORES];
		LectorListas[] lectores = new LectorListas[NUM_LECTORES];
		for (int i = 0; i < escritores.length; i++) {
			escritores[i] = new EscritorListas(baseDatos);
			escritores[i].start();
		}
		for (int i = 0; i < lectores.length; i++) {
			lectores[i] = new LectorListas(baseDatos);
			lectores[i].start();
		}
	}
}
