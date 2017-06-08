package pc.ejemplos5iii.lectescr.intrinsecos.parametrizado.inanicion;

class LectorEnteros extends Lector<Integer> {

	public LectorEnteros(BaseDatos<Integer> baseDatos) {
		super(baseDatos);
	}

	protected void procesar(Integer entero) {
		Pantalla.getPantalla().escribir("Proceso = " + entero);
	}
}
