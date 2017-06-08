package pc.ejemplos5iii.lectescr.locks.parametrizado.inanicion;

class AplicacionEnteros {

	public static void main(String[] args) {
		final int NUM_ESCRITORES = 10;
		final int NUM_LECTORES = 10;

		DatosEntero datosEntero = new DatosEntero(new Integer(0));
		BaseDatos<Integer> baseDatos =
				new BaseDatos<Integer>(datosEntero);

		EscritorEnteros[] escritores = new EscritorEnteros[NUM_ESCRITORES];
		LectorEnteros[] lectores = new LectorEnteros[NUM_LECTORES];
		for (int i = 0; i < escritores.length; i++) {
			escritores[i] = new EscritorEnteros(baseDatos);
			escritores[i].start();
		}
		for (int i = 0; i < lectores.length; i++) {
			lectores[i] = new LectorEnteros(baseDatos);
			lectores[i].start();
		}
	}
}
