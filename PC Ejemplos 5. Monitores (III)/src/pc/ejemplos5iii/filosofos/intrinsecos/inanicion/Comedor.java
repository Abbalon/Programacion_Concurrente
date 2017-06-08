package pc.ejemplos5iii.filosofos.intrinsecos.inanicion;

class Comedor {

	private final int CAPACIDAD;
	private int ocupacion;
	private Pantalla pantalla;

	public Comedor(int capacidad) {
		CAPACIDAD = capacidad;
		ocupacion = 0;
		pantalla = Pantalla.getPantalla();
	}

	public synchronized void entrar() throws InterruptedException {
		int veces = 0;
		while (ocupacion == CAPACIDAD) {    // ES NECESARIO EL WHILE
			veces++;
			if (veces > 1) {
				pantalla.escribir("Filosofo " +
						Thread.currentThread().hashCode() +
						" vuelve a esperar la " + veces + " vez" +
						" ***********************");
			}
			this.wait();
		}
		ocupacion = ocupacion + 1;
	}

	public synchronized void salir() {
		ocupacion = ocupacion - 1;
		this.notify();
	}
}
