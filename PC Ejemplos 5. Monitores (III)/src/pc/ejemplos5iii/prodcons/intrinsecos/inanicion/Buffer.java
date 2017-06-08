package pc.ejemplos5iii.prodcons.intrinsecos.inanicion;

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
		if (ocupacion == CAPACIDAD) {
			throw new RuntimeException("El buffer esta lleno");
		}
		datos[sigMeter] = dato;
		ocupacion = ocupacion + 1;
		sigMeter = (sigMeter + 1) % CAPACIDAD;
	}

	public Dato sacar() {
		if (ocupacion == 0) {
			throw new RuntimeException("El buffer esta vacio");
		}
		Dato dato = datos[sigSacar];
		ocupacion = ocupacion - 1;
		sigSacar = (sigSacar + 1) % CAPACIDAD;
		return dato;
	}
}
