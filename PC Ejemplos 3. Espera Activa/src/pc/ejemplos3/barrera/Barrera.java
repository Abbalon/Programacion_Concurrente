package pc.ejemplos3.barrera;

class Barrera {

	private volatile boolean continuar1;
	private volatile boolean continuar2;

	public Barrera() {
		continuar1 = false;
		continuar2 = false;
	}

	public void esperar1() {
		continuar2 = true;
		while (!continuar1) ;
	}

	public void esperar2() {
		continuar1 = true;
		while (!continuar2) ;
	}
}

