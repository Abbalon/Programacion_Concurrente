package pc.laboratorio6i.cliente;

import java.util.Collection;
import pc.laboratorio6i.Banco;
import pc.laboratorio6i.Cuenta;
import pc.laboratorio6i.Pantalla;

public class Oficina {

	private Banco banco;
	private Cajero[] cajeros;
	private final int NUM_CAJEROS = 10;

	public void ejecutar() {
		// TODO 5: Obtener del registro el servidor del banco

		System.out.println("Oficina::ejecutar -> " +
				"Conectado al servidor registrado en " +
				"//localhost:2020/Banco");

		cajeros = new Cajero[NUM_CAJEROS];
		for (int i = 0; i < cajeros.length; i++) {
			cajeros[i] = new Cajero(i, banco);
		}

		for (int i = 0; i < cajeros.length; i++) {
			cajeros[i].start();
		}
		try {
			for (int i = 0; i < cajeros.length; i++) {
				cajeros[i].join();
			}
		} catch (InterruptedException ex) {
			ex.printStackTrace();
			System.exit(-1);
		}

		Collection<Cuenta> coleccionCuentas = banco.getCuentas();
		for (Cuenta cuenta : coleccionCuentas) {
			Pantalla.getPantalla().escribir(
					"Cuenta = " + cuenta.getCodigo() +
					" Saldo = " + cuenta.getSaldo());
		}
	}

}
