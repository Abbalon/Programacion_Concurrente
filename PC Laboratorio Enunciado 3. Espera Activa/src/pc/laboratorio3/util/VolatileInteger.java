package pc.laboratorio3.util;

public class VolatileInteger {

	private volatile int valor;

	public VolatileInteger(int valor) {
		this.set(valor);
	}

	public int get() {
		return valor;
	}

	public void set(int valor) {
		this.valor = valor;
	}
}
