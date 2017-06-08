package pc.ejemplos4ii.prodcons;

class Buffer<Dato> {

	private final int CAPACIDAD;
	private Dato[] datos;
	private int ocupacion;
	private int sigMeter;
	private int sigSacar;

	public Buffer(int capacidad) {
		CAPACIDAD = capacidad;
		datos = (Dato[]) new Object[CAPACIDAD];
		ocupacion = 0;
		sigMeter = 0;
		sigSacar = 0;
	}

	public void meter(Dato dato) {
		datos[sigMeter] = dato;
		ocupacion = ocupacion + 1;
		sigMeter = (sigMeter + 1) % CAPACIDAD;
	}

	public Dato sacar() {
		Dato dato = datos[sigSacar];
		ocupacion = ocupacion - 1;
		sigSacar = (sigSacar + 1) % CAPACIDAD;
		return dato;
	}
}
