package pc.ejemplos5i.museo.intrinsecos;

class Museo {

	private int ocupacion;
	Pantalla pantalla;

	public Museo() {
		ocupacion = 0;
		pantalla = Pantalla.getPantalla();
	}

	public synchronized void entrar() {
		ocupacion = ocupacion + 1;
		pantalla.escribir("Entra un visitante. Hay " + ocupacion);
	}

	public synchronized void salir() {
		ocupacion = ocupacion - 1;
		pantalla.escribir("Sale un visitante. Hay " + ocupacion);
	}
}
