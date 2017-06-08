package pc.ejemplos4i.exclusion;

class Recurso {

	private Exclusion exclusion;

	public Recurso() {
		exclusion = new Exclusion();
	}

	public void usar() throws InterruptedException {
		exclusion.obtener();
		try {
			System.out.println("Inicio SC");
			for (int j = 0; j < (int) 10000 * Math.random(); j++) ;
			System.out.println("Fin SC");
		} finally {
			exclusion.liberar();
		}
	}
}
