package pc.laboratorio2;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

class Banco {

	private Map<String, Cuenta> mapaCuentas;
	private Cajero[] cajeros;
	private final int NUM_CAJEROS = 10;

	private Banco() {
		mapaCuentas = new TreeMap<String, Cuenta>();
		Cuenta cuenta1 = new Cuenta("1111111111");
		mapaCuentas.put("1111111111", cuenta1);
		Cuenta cuenta2 = new Cuenta("2222222222");
		mapaCuentas.put("2222222222", cuenta2);

		cajeros = new Cajero[NUM_CAJEROS];
		for (int i = 0; i < cajeros.length; i++) {
			cajeros[i] = new Cajero(this);
		}
	}

	public Cuenta getCuenta(String codigo) {
		return mapaCuentas.get(codigo);
	}

	private void ejecutar() {
		for (int i = 0; i < cajeros.length; i++) {
			// TODO 2: Ejecutar como hilos concurrentes
			cajeros[i].ejecutar();
		}
		
		Collection<Cuenta> coleccionCuentas = mapaCuentas.values();
		for (Cuenta cuenta : coleccionCuentas) {
			System.out.println("Cuenta = " + cuenta.getCodigo() +
				" Saldo = " + cuenta.getSaldo());
		}
	}

	public static void main(String[] args) {
		Banco banco = new Banco();
		banco.ejecutar();
	}
}
