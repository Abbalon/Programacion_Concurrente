package pc.laboratorio5ii;

class Cajero extends Thread {

	private int idCajero;
	private Banco banco;
	private BarreraCiclica barrera;

	public Cajero(int idCajero, Banco banco, BarreraCiclica barrera) {
		this.idCajero = idCajero;
		this.banco = banco;
		this.barrera = barrera;
	}

	public void ejecutar() {
		try {
			Pantalla pantalla = Pantalla.getPantalla();
			for (int i = 0; i < 10; i++) {
				//-------------------------------------------------------------
				// Ingresar
				int vecesIngresar;
				if (idCajero % 2 != 0) {
					vecesIngresar = 1000;
				} else {
					vecesIngresar = 10;
				}
				for (int j = 0; j < vecesIngresar; j++) {
					// Obtener cuenta
					Cuenta cuenta;
					if (idCajero % 2 != 0) {
						cuenta = banco.getCuenta("1111111111");
					} else {
						cuenta = banco.getCuenta("2222222222");
					}

					// Obtener cantidad a ingresar
					int cantidad;
					if (idCajero % 2 != 0) {
						cantidad = 1;
					} else {
						cantidad = 10;
					}

					// Ingresar cantidad
					try {
						cuenta.ingresar(cantidad);
					} catch (CuentaException ex) {
						System.out.println(ex.getMessage());
						pantalla.escribir(ex.getMessage());
					}
				}

				//-------------------------------------------------------------
				// Barrera
				barrera.esperar();

				//-------------------------------------------------------------
				// Retirar
				int vecesRetirar = idCajero % 2 != 0 ? 10 : 10;
				if (idCajero % 2 != 0) {
					vecesRetirar = 10;
				} else {
					vecesRetirar = 10;
				}
				for (int j = 0; j < vecesRetirar; j++) {
					// Obtener cuenta
					Cuenta cuenta;
					if (idCajero % 2 != 0) {
						cuenta = banco.getCuenta("2222222222");
					} else {
						cuenta = banco.getCuenta("1111111111");
					}

					// Obtener cantidad a retirar
					int cantidad;
					if (idCajero % 2 != 0) {
						cantidad = 10;
					} else {
						cantidad = 100;
					}

					// Retirar cantidad
					try {
						cuenta.retirar(cantidad);
					} catch (CuentaException ex) {
						System.out.println(ex.getMessage());
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
