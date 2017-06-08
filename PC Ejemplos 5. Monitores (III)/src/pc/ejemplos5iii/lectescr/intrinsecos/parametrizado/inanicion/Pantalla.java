package pc.ejemplos5iii.lectescr.intrinsecos.parametrizado.inanicion;

class Pantalla {

	private static Pantalla pantalla = new Pantalla();

	public static Pantalla getPantalla() {
		return pantalla;
	}

	private Pantalla() {
	}

	public synchronized void escribir(String texto) {
		System.out.println(texto);
	}
}
