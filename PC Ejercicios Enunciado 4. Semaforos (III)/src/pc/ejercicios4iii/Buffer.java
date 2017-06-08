package pc.ejercicios4iii;

import java.util.concurrent.Semaphore;

class Buffer<Dato> {

	private final int CAPACIDAD;
	private Dato[] datos;
	private int ocupacion;
	private int sigMeter;
	private int sigSacar;
	private Semaphore exclusion;

	public Buffer(int capacidad) {
		this.CAPACIDAD = capacidad;
		datos = (Dato[]) new Object[capacidad];
		ocupacion = 0;
		sigMeter = 0;
		sigSacar = 0;
		exclusion = new Semaphore(1);
	}

	public void meter(Dato dato) {
		exclusion.acquireUninterruptibly();
		try {
			if (ocupacion == CAPACIDAD) {
				throw new RuntimeException("El buffer esta lleno");
			}
			datos[sigMeter] = dato;
			ocupacion = ocupacion + 1;
			sigMeter = (sigMeter + 1) % CAPACIDAD;
		} finally {
			exclusion.release();
		}
	}

	public Dato sacar() {
		exclusion.acquireUninterruptibly();
		try {
			if (ocupacion == 0) {
				throw new RuntimeException("El buffer esta vacio");
			}
			Dato dato = datos[sigSacar];
			ocupacion = ocupacion - 1;
			sigSacar = (sigSacar + 1) % CAPACIDAD;
			return dato;
		} finally {
			exclusion.release();
		}
	}
}
