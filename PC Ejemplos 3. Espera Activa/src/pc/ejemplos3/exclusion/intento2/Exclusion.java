package pc.ejemplos3.exclusion.intento2;

class Exclusion {

	private volatile boolean dentro1;
	private volatile boolean dentro2;

	public Exclusion() {
		dentro1 = false;
		dentro2 = false;
	}

	public void obtener1() {
		while (dentro2) ;
		for (int j = 0; j < 100000; j++) ;
		dentro1 = true;
	}

	public void liberar1() {
		dentro1 = false;
	}

	public void obtener2() {
		while (dentro1) ;
		for (int j = 0; j < 100000; j++) ;
		dentro2 = true;
	}

	public void liberar2() {
		dentro2 = false;
	}
}
