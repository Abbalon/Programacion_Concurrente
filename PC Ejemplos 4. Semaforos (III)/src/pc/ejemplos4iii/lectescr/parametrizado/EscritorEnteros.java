package pc.ejemplos4iii.lectescr.parametrizado;

class EscritorEnteros extends Escritor<Integer> {

	public EscritorEnteros(BaseDatos<Integer> baseDatos) {
		super(baseDatos);
	}

	protected Integer generar() {
		Integer entero = new Integer((int) (10 * Math.random()) + 1);
		try {
			Pantalla.getPantalla().escribir("Genero = " + entero);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
		return entero;
	}
}
