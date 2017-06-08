package pc.ejemplos4i.condicional;

class Aplicacion {

	public static void main(String[] args) {
		Condicion condicion = new Condicion();
		Proceso1 proceso1 = new Proceso1(condicion);
		Proceso2 proceso2 = new Proceso2(condicion);

		proceso1.start();
		proceso2.start();
	}
}