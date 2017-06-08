package pc.laboratorio5iii.java;

class CuentaException extends Exception {

	private Cuenta cuenta;

	public CuentaException(Cuenta cuenta, String mensaje)
			throws InterruptedException {
		super("EXCEPCION en cuenta " + cuenta.getCodigo() + ": " + mensaje);
		this.cuenta = cuenta;
	}
}
