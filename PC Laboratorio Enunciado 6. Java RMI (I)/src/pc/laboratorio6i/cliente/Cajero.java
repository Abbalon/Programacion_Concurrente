package pc.laboratorio6i.cliente;

import pc.laboratorio6i.Banco;
import pc.laboratorio6i.Cuenta;
import pc.laboratorio6i.CuentaException;
import pc.laboratorio6i.Pantalla;

public class Cajero extends Thread {

	private int idCajero;
	private Banco banco;

	public Cajero(int idCajero, Banco banco) {
		this.idCajero = idCajero;
		this.banco = banco;
	}

	public void ejecutar() {
		Pantalla pantalla = Pantalla.getPantalla();
		for (int i = 0; i < 1000; i++) {
			// Obtener cuenta
			Cuenta cuenta;
			if (i % 2 != 0) {
				cuenta = banco.getCuenta("1111111111");
			} else {
				cuenta = banco.getCuenta("2222222222");
			}
			// Obtener cantidad a ingresar
			int cantidad;
			cantidad = 10;
			// Ingresar cantidad
			try {
				cuenta.ingresar(cantidad);
			} catch (CuentaException ex) {
				pantalla.escribir(ex.getMessage());
			}
			for (int j = 0; j < (long) (2000 * Math.random()); j++);
		}
	}

	public void run() {
		this.ejecutar();
	}
}
