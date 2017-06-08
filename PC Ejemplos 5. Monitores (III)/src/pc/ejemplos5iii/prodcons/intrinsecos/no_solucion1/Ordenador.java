package pc.ejemplos5iii.prodcons.intrinsecos.no_solucion1;

import java.util.Collections;
import java.util.List;

class Ordenador extends Consumidor<List<Integer>> {

	public Ordenador(Buzon<List<Integer>> buzon) {
		super(buzon);
	}

	protected void consumir(List<Integer> lista) {
		Collections.sort(lista);
		Pantalla.getPantalla().escribir("Ordenada = " + lista);
	}
}
