package pc.laboratorio3;

class Cuenta {

	private String codigo;
	private int saldo;
	// TODO 3.1: Definir atributos para conseguir exclusion mutua

	public Cuenta(String codigo, int numCajeros) {
		this.codigo = codigo;
		saldo = 0;
	}

	// TODO 3.2: Conseguir exclusion mutua
	public void ingresar(int idCajero, int cantidad) throws CuentaException {
		if (cantidad < 0) {
			throw new CuentaException(codigo,
					"Ingreso de cantidad " + cantidad + " negativa");
		}
		saldo = saldo + cantidad;
	}

	// TODO 3.2: Conseguir exclusion mutua
	public void retirar(int idCajero, int cantidad) throws CuentaException {
		if (cantidad < 0) {
			throw new CuentaException(codigo,
					"Retirada de cantidad " + cantidad + " negativa");
		}
		if (cantidad > saldo) {
			throw new CuentaException(codigo,
					"Saldo " + saldo + " insuficiente" +
					" para retirada de " + cantidad);
		}
		saldo = saldo - cantidad;
	}

	// TODO 3.2: Conseguir exclusion mutua
	public String getCodigo(int idCajero) {
		return codigo;
	}

	// TODO 3.2: Conseguir exclusion mutua
	public int getSaldo(int idCajero) {
		return saldo;
	}
}
