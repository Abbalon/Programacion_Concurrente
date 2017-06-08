package pc.ejemplos3.exclusion.intento4;

class Exclusion {

	private volatile boolean peticion1;
	private volatile boolean peticion2;

	public Exclusion() {
		peticion1 = false;
		peticion2 = false;
	}

	public void obtener1() {
		peticion1 = true;
		while (peticion2) {
			peticion1 = false;
			peticion1 = true;
		}
	}

	public void liberar1() {
		peticion1 = false;
	}

	public void obtener2() {
		peticion2 = true;
		while (peticion1) {
			peticion2 = false;
			peticion2 = true;
		}
	}

	public void liberar2() {
		peticion2 = false;
	}
}
