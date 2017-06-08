package pc.laboratorio2;

class Cuenta {

	private String codigo;
	// TODO 4: Convertir a AtomicInteger
	private int saldo;

	public Cuenta(String codigo) {
		this.codigo = codigo;
		this.saldo = 0;
	}

	public void ingresar(int cantidad) throws CuentaException {
		if (cantidad < 0) {
			throw new CuentaException(codigo,
					"Ingreso de cantidad " + cantidad + " negativa");
		}
		saldo = saldo + cantidad;
	}

	public String getCodigo() {
		return codigo;
	}

	public int getSaldo() {
		return saldo;
	}
}
