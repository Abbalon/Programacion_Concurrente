package pc.ejemplos5ii.ciclica.intrinsecos.solucion2;

class Proceso2 extends Thread {

	private BarreraCiclica barrera;

	public Proceso2(BarreraCiclica barrera) {
		this.barrera = barrera;
	}

	public void run() {
		try {
			for (int i = 0; i < 100; i++) {
				System.out.print(i % 10);           // Bloque B
				for (int j = 0; j < (int) 10000 * Math.random(); j++) ;

				barrera.esperar2();                 // SINCRONIZACION
			}
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
}
