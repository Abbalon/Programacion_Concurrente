package pc.ejemplos5iii.lectescr.locks.parametrizado.solucion;

class EscritorEnteros extends Escritor<Integer> {

	public EscritorEnteros(BaseDatos<Integer> baseDatos) {
		super(baseDatos);
	}

	protected Integer generar() {
		Integer entero = new Integer((int) (10 * Math.random()) + 1);
		Pantalla.getPantalla().escribir("Genero = " + entero);
		try {
			Thread.sleep((long) (1000 * Math.random()));
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
		return entero;
	}
}
