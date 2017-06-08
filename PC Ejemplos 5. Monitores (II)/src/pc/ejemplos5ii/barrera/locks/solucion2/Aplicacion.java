package pc.ejemplos5ii.barrera.locks.solucion2;

class Aplicacion {

	public static void main(String[] args) {
		Barrera barrera = new Barrera();
		Proceso1 proceso1 = new Proceso1(barrera);
		Proceso2 proceso2 = new Proceso2(barrera);

		proceso1.start();
		proceso2.start();
	}
}

