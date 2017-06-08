package pc.ejemplos3.condicional;

class Condicion {

	private volatile boolean continuar;

	public Condicion() {
		continuar = false;
	}

	public void avisar() {
		continuar = true;
	}

	public void esperar() {
		while (!continuar) ;
	}
}
