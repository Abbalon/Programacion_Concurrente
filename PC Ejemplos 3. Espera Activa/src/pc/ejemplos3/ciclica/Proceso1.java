package pc.ejemplos3.ciclica;

class Proceso1 extends Thread {

	private BarreraCiclica barrera;

	public Proceso1(BarreraCiclica barrera) {
		this.barrera = barrera;
	}

	public void run() {
		for (int i = 0; i < 100; i++) {
			System.out.print(i % 10);           // Bloque A
			for (int j = 0; j < (int) 10000 * Math.random(); j++) ;

			barrera.esperar1();                 // SINCRONIZACION
		}
	}
}
