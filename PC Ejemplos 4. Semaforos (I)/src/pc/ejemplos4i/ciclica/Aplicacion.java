package pc.ejemplos4i.ciclica;

class Aplicacion {

	public static void main(String[] args) {
		BarreraCiclica barrera = new BarreraCiclica();
		Proceso1 proceso1 = new Proceso1(barrera);
		Proceso2 proceso2 = new Proceso2(barrera);

		proceso1.start();
		proceso2.start();
	}
}

