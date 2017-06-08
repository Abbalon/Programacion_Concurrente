package pc.laboratorio6i.cliente;

public class OficinaApl {

	public static void main(String[] args) {
		try {
			Oficina oficina = new Oficina();
			oficina.ejecutar();
		} catch (Exception ex) {
			System.out.println(
				"EXCEPCION AL EJECUTAR EL CLIENTE DE LA OFICINA: " + ex);
			System.exit(-1);
		}
	}
}
