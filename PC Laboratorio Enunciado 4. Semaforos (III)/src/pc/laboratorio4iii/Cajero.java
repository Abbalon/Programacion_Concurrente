package pc.laboratorio4iii;

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
					Thread.sleep(200);
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
						System.exit(-1);
					}
					for (int j = 0; j < (long) (2000 * Math.random()); j++) ;
				} else {
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
						System.exit(-1);
					}
					for (int j = 0; j < (long) (1000 * Math.random()); j++) ;
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
