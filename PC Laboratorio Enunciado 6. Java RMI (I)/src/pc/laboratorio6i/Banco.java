package pc.laboratorio6i;

import java.util.Collection;

// TODO 1: Convertir en un interfaz remoto
public interface Banco {

	Cuenta getCuenta(String codigo);

	Collection<Cuenta> getCuentas();
}
