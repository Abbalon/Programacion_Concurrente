package pc.ejemplos3.exclusion.intento1;

class Aplicacion {

	public static void main(String[] args) {
		Recurso recurso = new Recurso();
		Proceso1 proceso1 = new Proceso1(recurso);
		Proceso2 proceso2 = new Proceso2(recurso);

		proceso1.start();
		proceso2.start();
	}
}