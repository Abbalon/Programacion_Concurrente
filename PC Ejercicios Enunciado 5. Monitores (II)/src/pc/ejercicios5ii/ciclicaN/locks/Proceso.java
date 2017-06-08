package pc.ejercicios5ii.ciclicaN.locks;

class Proceso extends Thread {

	private BarreraCiclica barrera;

	public Proceso(BarreraCiclica barrera) {
		this.barrera = barrera;
	}

	public void run() {
		Pantalla pantalla = Pantalla.getPantalla();
		try {
			for (int i = 0; i < 10; i++) {
				pantalla.escribir("" + i % 10);    // Bloque A
				for (int j = 0; j < (int) 10000 * Math.random(); j++) ;

				barrera.esperar();                 // SINCRONIZACION
			}
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
}
