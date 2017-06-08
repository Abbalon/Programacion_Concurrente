package pc.ejemplos4ii.prodcons;

import java.util.Collections;
import java.util.List;

class Ordenador extends Consumidor<List<Integer>> {

	public Ordenador(Buzon<List<Integer>> buzon) {
		super(buzon);
	}

	protected void consumir(List<Integer> lista) {
		Collections.sort(lista);
		try {
			Pantalla.getPantalla().escribir("Ordenada = " + lista);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
}
