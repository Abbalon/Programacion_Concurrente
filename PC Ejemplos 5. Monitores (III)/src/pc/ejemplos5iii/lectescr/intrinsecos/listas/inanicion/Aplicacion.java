package pc.ejemplos5iii.lectescr.intrinsecos.listas.inanicion;

class Aplicacion {

	public static void main(String[] args) {
		final int NUM_LECTORES = 10;
		final int NUM_ESCRITORES = 10;

		BaseDatos datos = new BaseDatos();
		Lector[] lectores = new Lector[NUM_LECTORES];
		Escritor[] escritores = new Escritor[NUM_ESCRITORES];
		for (int i = 0; i < lectores.length; i++) {
			lectores[i] = new Lector(datos);
			lectores[i].start();
		}
		for (int i = 0; i < escritores.length; i++) {
			escritores[i] = new Escritor(datos);
			escritores[i].start();
		}
	}
}
