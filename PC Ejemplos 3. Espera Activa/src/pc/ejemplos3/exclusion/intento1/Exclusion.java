package pc.ejemplos3.exclusion.intento1;

class Exclusion {

	private volatile int turno;

	public Exclusion() {
		turno = 1;
	}

	public void obtener1() {
		while (turno != 1) ;
	}

	public void liberar1() {
		turno = 2;
	}

	public void obtener2() {
		while (turno != 2) ;
	}

	public void liberar2() {
		turno = 1;
	}
}
