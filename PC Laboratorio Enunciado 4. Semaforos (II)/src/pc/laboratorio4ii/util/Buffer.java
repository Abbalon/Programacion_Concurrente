package pc.laboratorio4ii.util;

class Buffer<Dato> {

	private final int capacidad;
	private Dato[] datos;
	private int ocupacion;
	private int sigMeter;
	private int sigSacar;

	public Buffer(int capacidad) {
		this.capacidad = capacidad;
		datos = (Dato[]) new Object[capacidad];
		ocupacion = 0;
		sigMeter = 0;
		sigSacar = 0;
	}

	public void meter(Dato dato) {
		datos[sigMeter] = dato;
		ocupacion = ocupacion + 1;
		sigMeter = (sigMeter + 1) % capacidad;
	}

	public Dato sacar() {
		Dato dato = datos[sigSacar];
		ocupacion = ocupacion - 1;
		sigSacar = (sigSacar + 1) % capacidad;
		return dato;
	}
}
