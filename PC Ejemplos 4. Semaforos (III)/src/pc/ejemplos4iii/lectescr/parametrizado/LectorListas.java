package pc.ejemplos4iii.lectescr.parametrizado;

import java.util.ArrayList;

class LectorListas extends Lector<ArrayList<Integer>> {

	public LectorListas(BaseDatos<ArrayList<Integer>> baseDatos) {
		super(baseDatos);
	}

	protected void procesar(ArrayList<Integer> lista) {
		try {
			Pantalla.getPantalla().escribir("Proceso = " + lista);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
}
