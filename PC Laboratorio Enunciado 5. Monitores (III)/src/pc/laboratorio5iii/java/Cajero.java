package pc.laboratorio5iii.java;

class Cajero extends Thread {

	private int idCajero;
	private Banco banco;

	public Cajero(int idCajero, Banco banco) {
		this.idCajero = idCajero;
		this.banco = banco;
	}

	public void ejecutar() {
		try {
			Pantalla pantalla = Pantalla.getPantalla();
			for (int i = 0; i < 100; i++) {
				if (idCajero % 2 != 0) {
					Thread.sleep((long) (100 * Math.random()));
//					for (int j = 0; j < (int) (100000 * Math.random()); j++) ;
	
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
				} else {
//					for (int j = 0; j < (int) (1000 * Math.random()); j++) ;

					// Obtener cuenta
					Cuenta cuenta;
					if (i % 2 != 0) {
						cuenta = banco.getCuenta("1111111111");
					} else {
						cuenta = banco.getCuenta("2222222222");
					}
					// Obtener cantidad a retirar
					int cantidad;
					cantidad = 10;
					// Retirar cantidad
					try {
						cuenta.retirar(cantidad);
					} catch (CuentaException ex) {
						pantalla.escribir(ex.getMessage());
					}
				}
			}
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	public void run() {
		this.ejecutar();
	}
}
