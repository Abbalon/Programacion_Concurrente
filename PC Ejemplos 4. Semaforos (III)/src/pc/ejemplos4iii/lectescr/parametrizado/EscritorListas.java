package pc.ejemplos4iii.lectescr.parametrizado;

import java.util.ArrayList;

class EscritorListas extends Escritor<ArrayList<Integer>> {

	public EscritorListas(BaseDatos<ArrayList<Integer>> baseDatos) {
		super(baseDatos);
	}

	protected ArrayList<Integer> generar() {
		Integer entero = new Integer((int) (10 * Math.random()));
		ArrayList<Integer> lista = new ArrayList<Integer>();
		for (int i = 0; i < 10; i++) {
			lista.add(entero);
		}
		try {
			Pantalla.getPantalla().escribir("Genero = " + lista);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
		return lista;
	}
}
