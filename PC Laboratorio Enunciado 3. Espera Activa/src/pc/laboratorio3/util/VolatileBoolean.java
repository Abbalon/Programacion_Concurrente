package pc.laboratorio3.util;

public class VolatileBoolean {

	private volatile boolean valor;

	public VolatileBoolean(boolean valor) {
		this.set(valor);
	}

	public boolean get() {
		return valor;
	}

	public void set(boolean valor) {
		this.valor = valor;
	}
}
