package pc.ejemplos4ii.prodcons;

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
		try {
			Pantalla.getPantalla().escribir("Generada = " + lista);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
		return lista;
	}
}
