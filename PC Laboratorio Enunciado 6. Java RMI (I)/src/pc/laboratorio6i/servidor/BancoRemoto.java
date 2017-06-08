package pc.laboratorio6i.servidor;

import pc.laboratorio6i.Banco;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import pc.laboratorio6i.Cuenta;

// TODO 3: Convertir en una clase remota
class BancoRemoto implements Banco {

	private Map<String, Cuenta> mapaCuentas;

	public BancoRemoto() {
		mapaCuentas = new TreeMap<String, Cuenta>();
		Cuenta cuenta1 = new CuentaRemota("1111111111");
		mapaCuentas.put("1111111111", cuenta1);
		Cuenta cuenta2 = new CuentaRemota("2222222222");
		mapaCuentas.put("2222222222", cuenta2);
	}

	public void ejecutar() {
		// TODO 3: Registrar el servidor del banco

		System.out.println("BancoRemoto::ejecutar -> " +
				"Servidor registrado en //localhost:2020/Banco");
	}

	public void terminar() {
		// TODO 3: Desregistrar el servidor, desexportar el objeto
		// y terminar todas sus cuentas
	}

	public Cuenta getCuenta(String codigo) {
		return mapaCuentas.get(codigo);
	}

	public Collection<Cuenta> getCuentas() {
		return new ArrayList<Cuenta>(mapaCuentas.values());
	}
}
