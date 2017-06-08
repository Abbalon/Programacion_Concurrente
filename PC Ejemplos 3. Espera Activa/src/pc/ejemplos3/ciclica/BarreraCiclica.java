package pc.ejemplos3.ciclica;

class BarreraCiclica {

	private volatile boolean continuar1;
	private volatile boolean continuar2;

	public BarreraCiclica() {
		continuar1 = false;
		continuar2 = false;
	}

	public void esperar1() {
		continuar2 = true;
		while (!continuar1) ;
		continuar1 = false;
		while (continuar2) ;
	}

	public void esperar2() {
		continuar1 = true;
		while (!continuar2) ;
		continuar2 = false;
		while (continuar1) ;
	}
}
