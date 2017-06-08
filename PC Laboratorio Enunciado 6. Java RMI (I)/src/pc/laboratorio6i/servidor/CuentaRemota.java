package pc.laboratorio6i.servidor;

import pc.laboratorio6i.Pantalla;
import pc.laboratorio6i.Cuenta;
import pc.laboratorio6i.CuentaException;

// TODO 4: Convertir en una clase remota
public class CuentaRemota implements Cuenta {

	private String codigo;
	private int saldo;
	private Pantalla pantalla;

	public CuentaRemota(String codigo) {
		this.codigo = codigo;
		saldo = 0;
		pantalla = Pantalla.getPantalla();
	}

	public void terminar() {
		// TODO 4: Desexportar el objeto
	}

	public synchronized void ingresar(int cantidad) throws CuentaException {
		if (cantidad < 0) {
			throw new CuentaException(this,
					"Ingreso de cantidad " + cantidad + " negativa");
		}
		pantalla.escribir("Cuenta = " + this.getCodigo() +
				" Ingreso " + cantidad);
		saldo = saldo + cantidad;
	}

	public synchronized void retirar(int cantidad) throws CuentaException {
		if (cantidad < 0) {
			throw new CuentaException(this,
					"Retirada de cantidad " + cantidad + " negativa");
		}
		if (cantidad > saldo) {
			throw new CuentaException(this,
					"Saldo " + saldo + " insuficiente" +
					" para retirada de " + cantidad);
		}
		pantalla.escribir("Cuenta = " + this.getCodigo() +
				" Retiro " + cantidad);
		saldo = saldo - cantidad;
		if (saldo < 0)  {
			throw new CuentaException(this,
					"ERROR FATAL: Saldo " + saldo + " negativo");
		}
	}

	public synchronized String getCodigo() {
		return codigo;
	}

	public synchronized int getSaldo() {
		return saldo;
	}
}
