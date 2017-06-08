package pc.ejemplos3.exclusion.intento3;

class Exclusion {

	private volatile boolean peticion1;
	private volatile boolean peticion2;

	public Exclusion() {
		peticion1 = false;
		peticion2 = false;
	}

	public void obtener1() {
		peticion1 = true;
		for (int j = 0; j < 100000; j++) ;
		while (peticion2) ;
	}

	public void liberar1() {
		peticion1 = false;
	}

	public void obtener2() {
		peticion2 = true;
		for (int j = 0; j < 100000; j++) ;
		while (peticion1) ;
	}

	public void liberar2() {
		peticion2 = false;
	}
}
