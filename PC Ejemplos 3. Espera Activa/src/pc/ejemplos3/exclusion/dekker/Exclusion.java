package pc.ejemplos3.exclusion.dekker;

class Exclusion {

	private volatile boolean peticion1;
	private volatile boolean peticion2;
	private volatile int turno;

	public Exclusion() {
		peticion1 = false;
		peticion2 = false;
		turno = 1;
	}

	public void obtener1() {
		peticion1 = true;
		while (peticion2) {
			if (turno != 1) {
				peticion1 = false;
				while (turno != 1) ;
				peticion1 = true;
			}
		}
	}

	public void liberar1() {
		peticion1 = false;
		turno = 2;
	}

	public void obtener2() {
		peticion2 = true;
		while (peticion1) {
			if (turno != 2) {
				peticion2 = false;
				while (turno != 2) ;
				peticion2 = true;
			}
		}
	}

	public void liberar2() {
		peticion2 = false;
		turno = 1;
	}
}
