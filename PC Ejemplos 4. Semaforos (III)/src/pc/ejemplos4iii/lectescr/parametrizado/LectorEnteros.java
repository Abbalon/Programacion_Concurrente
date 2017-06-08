package pc.ejemplos4iii.lectescr.parametrizado;

class LectorEnteros extends Lector<Integer> {

	public LectorEnteros(BaseDatos<Integer> baseDatos) {
		super(baseDatos);
	}

	protected void procesar(Integer entero) {
		try {
			Pantalla.getPantalla().escribir("Proceso = " + entero);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
}
