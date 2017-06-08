package pc.ejemplos5ii.ciclica.locks.solucion2;

class Proceso1 extends Thread {

	private BarreraCiclica barrera;

	public Proceso1(BarreraCiclica barrera) {
		this.barrera = barrera;
	}

	public void run() {
		try {
			for (int i = 0; i < 100; i++) {
				System.out.print(i % 10);           // Bloque A
				for (int j = 0; j < (int) 10000 * Math.random(); j++) ;

				barrera.esperar1();                 // SINCRONIZACION
			}
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
}
