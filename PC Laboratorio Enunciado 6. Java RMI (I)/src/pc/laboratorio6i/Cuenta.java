package pc.laboratorio6i;

// TODO 2:  Convertir en un interfaz remoto
public interface Cuenta {

	void ingresar(int cantidad) throws CuentaException;

	void retirar(int cantidad) throws CuentaException;

	String getCodigo();

	int getSaldo();
}
