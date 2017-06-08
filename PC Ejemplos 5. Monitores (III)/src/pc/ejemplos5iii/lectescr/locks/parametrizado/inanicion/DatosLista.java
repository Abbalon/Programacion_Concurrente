package pc.ejemplos5iii.lectescr.locks.parametrizado.inanicion;

import java.util.ArrayList;

class DatosLista<Tipo> implements Datos<ArrayList<Tipo>> {

	private ArrayList<Tipo> lista;
	Integer i;

	public DatosLista(ArrayList<Tipo> lista) {
		this.lista = lista;
	}

	public ArrayList<Tipo> leer() {
		return new ArrayList<Tipo>(lista);
	}

	public void escribir(ArrayList<Tipo> lista) {
		this.lista = new ArrayList<Tipo>(lista);
	}
}
