package pc.laboratorio5ii;

import java.util.concurrent.locks.Lock;
import pc.util.concurrent.locks.UrgentQueueReentrantLock;

class Cuenta {

	private String codigo;
	private int saldo;
	private Lock exclusion;

	public Cuenta(String codigo) {
		this.codigo = codigo;
		saldo = 0;
		exclusion = new UrgentQueueReentrantLock();
	}

	public void ingresar(int cantidad) throws CuentaException {
		exclusion.lock();
		try {
			if (cantidad < 0) {
				throw new CuentaException(this,
						"Ingreso de cantidad " + cantidad + " negativa");
			}
			saldo = saldo + cantidad;
		} finally {
			exclusion.unlock();
		}
	}

	public void retirar(int cantidad) throws CuentaException {
		exclusion.lock();
		try {
			if (cantidad < 0) {
				throw new CuentaException(this,
						"Retirada de cantidad " + cantidad + " negativa");
			}
			if (cantidad > saldo) {
				throw new CuentaException(this,
						"Saldo " + saldo + " insuficiente" +
						" para retirada de " + cantidad);
			}
			saldo = saldo - cantidad;
			if (saldo < 0)  {
				throw new CuentaException(this,
						"ERROR FATAL: Saldo " + saldo + " negativo");
			}
		} finally {
			exclusion.unlock();
		}
	}

	public String getCodigo() {
		exclusion.lock();
		try {
			return codigo;
		} finally {
			exclusion.unlock();
		}
	}

	public int getSaldo() {
		exclusion.lock();
		try {
			return saldo;
		} finally {
			exclusion.unlock();
		}
	}
}
