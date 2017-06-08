package pc.ejemplos2.swing;

class Aplicacion {

	private static Ventana ventana;
	public static Ventana getVentana() {
		return ventana;
	}

	public Aplicacion() {
		ventana = new Ventana();
	}

	public static void main(String[] args) {
		new Aplicacion();
	}
}
