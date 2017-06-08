package pc.laboratorio5i.intrinsecos;

class Pantalla {

	private static Pantalla pantalla = new Pantalla();

	public static Pantalla getPantalla() {
		return pantalla;
	}

	private Pantalla() {
	}

	// TODO 3: Conseguir exclusion mutua con monitores primitivos
	public synchronized void escribir(String texto) {
		System.out.println(texto);
	}
}
