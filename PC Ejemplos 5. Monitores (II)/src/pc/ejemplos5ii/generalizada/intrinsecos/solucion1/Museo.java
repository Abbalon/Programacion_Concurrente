package pc.ejemplos5ii.generalizada.intrinsecos.solucion1;

class Museo {

	private final int CAPACIDAD;
	private int ocupacion;
	private Pantalla pantalla;
	private int esperando;
	private int liberados;

	public Museo(int capacidad) {
		CAPACIDAD = capacidad;
		ocupacion = 0;
		pantalla = Pantalla.getPantalla();
		esperando = 0;
		liberados = 0;
	}

	public synchronized void entrar() throws InterruptedException {
		int veces = 0;
		// ES NECESARIO EL WHILE POR ESPURIOS
		while (ocupacion + liberados == CAPACIDAD) {
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
			esperando++;
			this.wait();
			if (liberados == 0) {    // DESPERTAR ESPURIO
				esperando--;
				pantalla.escribir(
						"Despertar espurio" +
						" ***********************");
			} else {
				liberados--;
				break;
			}
		}
		ocupacion = ocupacion + 1;
		pantalla.escribir("Entra el visitante " +
				Thread.currentThread().hashCode() +
				". Hay = " + ocupacion +
				" Esperando = " + esperando +
				" Liberados = " + liberados);
		if (ocupacion > CAPACIDAD) {
			throw new RuntimeException("La ocupacion excede del aforo");
		}
	}

	public synchronized void salir() {
		ocupacion = ocupacion - 1;
		pantalla.escribir("Sale un visitante. Hay " + ocupacion);
		if (esperando > 0) {
			esperando--;
			liberados++;
			this.notify();
		}
	}
}
