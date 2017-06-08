package pc.ejemplos5ii.generalizada.intrinsecos.inanicion;

class Museo {

	private final int CAPACIDAD;
	private int ocupacion;
	private Pantalla pantalla;

	public Museo(int capacidad) {
		CAPACIDAD = capacidad;
		ocupacion = 0;
		pantalla = Pantalla.getPantalla();
	}

	public synchronized void entrar() throws InterruptedException {
		int veces = 0;
		while (ocupacion == CAPACIDAD) {    // ES NECESARIO EL WHILE
			veces++;
			if (veces == 1) {
				pantalla.escribir("Visitante " +
						Thread.currentThread().hashCode() +
						" espera");
			} else {
				pantalla.escribir("Visitante " +
						Thread.currentThread().hashCode() +
						" vuelve a esperar la " + veces + " vez" +
						" ***********************");
			}
			this.wait();
		}
		ocupacion = ocupacion + 1;
		pantalla.escribir("Entra el visitante " +
				Thread.currentThread().hashCode() +
				". Hay = " + ocupacion);
		if (ocupacion > CAPACIDAD) {
			throw new RuntimeException("La ocupacion excede del aforo");
		}
	}

	public synchronized void salir() {
		ocupacion = ocupacion - 1;
		pantalla.escribir("Sale un visitante. Hay " + ocupacion);
		this.notify();
	}
}
