package pc.ejemplos5iii.lectescr.rwlocks.parametrizado.solucion;

class DatosEntero implements Datos<Integer> {

	private Integer entero;

	public DatosEntero(Integer entero) {
		this.entero = entero;
	}

	public Integer leer() {
		return new Integer(entero);
	}

	public void escribir(Integer entero) {
		this.entero = new Integer(entero);
	}
}
