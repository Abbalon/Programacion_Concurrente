package pc.laboratorio6i.servidor;

public class BancoApl {

	public static void main(String[] args) {
		try {
			BancoRemoto banco = new BancoRemoto();
			banco.ejecutar();
		} catch (Exception ex) {
			System.out.println(
				"EXCEPCION AL EJECUTAR EL SERVIDOR DEL BANCO: " + ex);
			System.exit(-1);
		}
	}
}
