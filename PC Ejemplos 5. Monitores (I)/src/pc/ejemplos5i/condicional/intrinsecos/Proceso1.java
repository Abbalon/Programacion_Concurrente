package pc.ejemplos5i.condicional.intrinsecos;

class Proceso1 extends Thread {

	private Condicion condicion;

	public Proceso1(Condicion condicion) {
		this.condicion = condicion;
	}

	public void run() {
		try {
			for (int i = 0; i < 10; i++) {          // Bloque A
				System.out.print('A');
				for (int j = 0; j < (int) 10000 * Math.random(); j++) ;
			}

			condicion.esperar();                    // SINCRONIZACION

			for (int i = 0; i < 10; i++) {          // Bloque B
				System.out.print('B');
				for (int j = 0; j < (int) 10000 * Math.random(); j++) ;
			}
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
}
