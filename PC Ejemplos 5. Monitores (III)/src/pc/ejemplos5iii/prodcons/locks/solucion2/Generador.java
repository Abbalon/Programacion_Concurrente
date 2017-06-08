package pc.ejemplos5iii.prodcons.locks.solucion2;

import java.util.ArrayList;
import java.util.List;

class Generador extends Productor<List<Integer>> {

	public Generador(Buzon<List<Integer>> buzon) {
		super(buzon);
	}

	protected List<Integer> producir() {
		List<Integer> lista = new ArrayList<Integer>();
		for (int i = 0; i < 10; i++) {
			lista.add((int) (10 * Math.random()));
		}
		Pantalla.getPantalla().escribir("Generada = " + lista);
		return lista;
	}
}
