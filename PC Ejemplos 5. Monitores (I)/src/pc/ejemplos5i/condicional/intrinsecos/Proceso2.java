package pc.ejemplos5i.condicional.intrinsecos;

class Proceso2 extends Thread {

	private Condicion condicion;

	public Proceso2(Condicion condicion) {
		this.condicion = condicion;
	}

	public void run() {
		for (int i = 0; i < 10; i++) {              // Bloque C
			System.out.print('C');
			for (int j = 0; j < (int) 10000 * Math.random(); j++) ;
		}

		condicion.avisar();                        // SINCRONIZACION

		for (int i = 0; i < 10; i++) {              // Bloque D
			System.out.print('D');
			for (int j = 0; j < (int) 10000 * Math.random(); j++) ;
		}
	}
}
