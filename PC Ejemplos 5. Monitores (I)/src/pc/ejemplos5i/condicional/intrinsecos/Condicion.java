package pc.ejemplos5i.condicional.intrinsecos;

class Condicion {

	private boolean continuar;

	public Condicion() {
		continuar = false;
	}

	public synchronized void avisar() {
		continuar = true;
		this.notify();
	}

	public synchronized void esperar() throws InterruptedException {
		while (!continuar) {
			this.wait();
		}
	}
}
