package pc.laboratorio6i;

public class CuentaException extends Exception {

	private Cuenta cuenta;

	public CuentaException(Cuenta cuenta, String mensaje) {
		super("EXCEPCION en cuenta " + cuenta.getCodigo() + ": " + mensaje);
		this.cuenta = cuenta;
	}
}
