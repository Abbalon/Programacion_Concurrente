package pc.ejemplos5ii.barrera.locks.solucion1;

class Proceso1 extends Thread {

	private Barrera barrera;

	public Proceso1(Barrera barrera) {
		this.barrera = barrera;
	}

	public void run() {
		try {
			for (int i = 0; i < 10; i++) {          // Bloque A
				System.out.print('A');
				for (int j = 0; j < (int) 10000 * Math.random(); j++) ;
			}

			barrera.esperar1();                     // SINCRONIZACION

			for (int i = 0; i < 10; i++) {          // Bloque B
				System.out.print('B');
				for (int j = 0; j < (int) 10000 * Math.random(); j++) ;
			}
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
}
