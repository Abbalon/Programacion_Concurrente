package pc.ejemplos3.exclusion.intento1;

class Recurso {

	private Exclusion exclusion;

	public Recurso() {
		exclusion = new Exclusion();
	}

	public void usar1() {
		exclusion.obtener1();
		System.out.println("Inicio SC 1");
		for (int j = 0; j < (int) 10000 * Math.random(); j++) ;
		System.out.println("Fin SC 1");
		exclusion.liberar1();
	}

	public void usar2() {
		exclusion.obtener2();
		System.out.println("Inicio SC 2");
		for (int j = 0; j < (int) 10000 * Math.random(); j++) ;
		System.out.println("Fin SC 2");
		exclusion.liberar2();
	}
}
