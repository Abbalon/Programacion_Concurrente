package pc.laboratorio4iii;

class CuentaException extends Exception {

	public CuentaException(String codigo, String mensaje)
			throws InterruptedException {
		super("EXCEPCION en cuenta " + codigo + ": " + mensaje);
	}
}
