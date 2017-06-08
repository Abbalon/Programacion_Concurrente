package pc.ejemplos5ii.barrera.intrinsecos.solucion2;

class Proceso2 extends Thread {

	private Barrera barrera;

	public Proceso2(Barrera barrera) {
		this.barrera = barrera;
	}

	public void run() {
		try {
			for (int i = 0; i < 10; i++) {          // Bloque C
				System.out.print('C');
				for (int j = 0; j < (int) 10000 * Math.random(); j++) ;
			}

			barrera.esperar2();                     // SINCRONIZACION

			for (int i = 0; i < 10; i++) {          // Bloque D
				System.out.print('D');
				for (int j = 0; j < (int) 10000 * Math.random(); j++) ;
			}
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
}
