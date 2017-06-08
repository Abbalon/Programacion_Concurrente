package pc.ejemplos5iii.prodcons.intrinsecos.inanicion;

class Buzon<Dato> {

	private final int CAPACIDAD;
	private int numDatos;
	private Buffer<Dato> buffer;
	private Pantalla pantalla;

	public Buzon(int capacidad) {
		CAPACIDAD = capacidad;
		numDatos = 0;
		buffer = new Buffer<Dato>(CAPACIDAD);
		pantalla = Pantalla.getPantalla();
	}

	public synchronized void enviar(Dato dato) throws InterruptedException {
		int veces = 0;
		while (numDatos == CAPACIDAD) {    // ES NECESARIO EL WHILE
			veces++;
			if (veces == 1) {
				pantalla.escribir("Productor " +
						Thread.currentThread().hashCode() +
						" espera");
			} else {
				pantalla.escribir("Productor " +
						Thread.currentThread().hashCode() +
						" vuelve a esperar la " + veces + " vez" +
						" ***********************");
			}
			this.wait();
		}
		buffer.meter(dato);
		numDatos++;
		this.notifyAll();	// SE DEBE LIBERAR A TODOS
		pantalla.escribir("Productor " +
				Thread.currentThread().hashCode() +
				" mete. Datos = " + numDatos);
	}

	public synchronized Dato recibir() throws InterruptedException {
		int veces = 0;
		while (numDatos == 0) {            // ES NECESARIO EL WHILE
			veces++;
			if (veces == 1) {
				pantalla.escribir("Consumidor " +
						Thread.currentThread().hashCode() +
						" espera");
			} else {
				pantalla.escribir("Consumidor " +
						Thread.currentThread().hashCode() +
						" vuelve a esperar la " + veces + " vez" +
						" ***********************");
			}
			this.wait();
		}
		Dato dato = buffer.sacar();
		numDatos--;
		this.notifyAll();	// SE DEBE LIBERAR A TODOS
		pantalla.escribir("Consumidor " +
				Thread.currentThread().hashCode() +
				" saca. Datos = " + numDatos);
		return dato;
	}
}
