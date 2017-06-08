package pc.laboratorio3;

class Cajero extends Thread {

	private int idCajero;
	private Banco banco;

	public Cajero(int idCajero, Banco banco) {
		this.idCajero = idCajero;
		this.banco = banco;
	}

	public void ejecutar() {
		for (int i = 0; i < 500; i++) {
			// Obtener cuenta
			for (int j = 0; j < 1000; j++) ;
			Cuenta cuenta1 = banco.getCuenta("1111111111");

			// Obtener cantidad a ingresar
			for (int j = 0; j < 1000; j++) ;
			int cantidad1 = 10;

			// Ingresar cantidad
			try {
				cuenta1.ingresar(idCajero, cantidad1);
			} catch (CuentaException ex) {
				ex.printStackTrace();
			}

			//---------------------------------------------------------------

			// Obtener cuenta
			for (int j = 0; j < 1000; j++) ;
			Cuenta cuenta2 = banco.getCuenta("2222222222");

			// Obtener cantidad a ingresar
			for (int j = 0; j < 1000; j++) ;
			int cantidad2 = 20;

			// Ingresar cantidad
			try {
				cuenta2.ingresar(idCajero, cantidad2);
			} catch (CuentaException ex) {
				ex.printStackTrace();
			}
		}
	}

	public void run() {
		this.ejecutar();
	}
}
