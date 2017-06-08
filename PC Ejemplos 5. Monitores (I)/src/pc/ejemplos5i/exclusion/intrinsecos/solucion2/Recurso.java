package pc.ejemplos5i.exclusion.intrinsecos.solucion2;

class Recurso {

	public synchronized void usar() {
		System.out.println("Inicio SC");
		for (int j = 0; j < (int) 10000 * Math.random(); j++) ;
		System.out.println("Fin SC");
	}
}
