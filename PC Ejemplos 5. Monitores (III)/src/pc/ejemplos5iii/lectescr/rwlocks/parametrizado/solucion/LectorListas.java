package pc.ejemplos5iii.lectescr.rwlocks.parametrizado.solucion;

import java.util.ArrayList;

class LectorListas extends Lector<ArrayList<Integer>> {

	public LectorListas(BaseDatos<ArrayList<Integer>> baseDatos) {
		super(baseDatos);
	}

	protected void procesar(ArrayList<Integer> lista) {
		Pantalla.getPantalla().escribir("Proceso = " + lista);
	}
}
