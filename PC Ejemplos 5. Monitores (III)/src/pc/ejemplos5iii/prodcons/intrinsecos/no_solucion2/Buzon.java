package pc.ejemplos5iii.prodcons.intrinsecos.no_solucion2;

class Buzon<Dato> {

	private final int CAPACIDAD;
	private int numDatos;
	private Buffer<Dato> buffer;
	private Pantalla pantalla;
	private int productoresEsperando;
	private int consumidoresEsperando;
	private int productoresLiberados;
	private int consumidoresLiberados;

	public Buzon(int capacidad) {
		CAPACIDAD = capacidad;
		numDatos = 0;
		buffer = new Buffer<Dato>(CAPACIDAD);
		pantalla = Pantalla.getPantalla();
		productoresEsperando = 0;
		consumidoresEsperando = 0;
		productoresLiberados = 0;
		consumidoresLiberados = 0;
	}

	public synchronized void enviar(Dato dato) throws InterruptedException {
		int veces = 0;
		// ES NECESARIO EL WHILE
		while (numDatos == CAPACIDAD || productoresLiberados > 0) {
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
			productoresEsperando++;
			this.wait();
			if (productoresLiberados == 0) {    // DESPERTAR ESPURIO
				productoresEsperando--;
				pantalla.escribir(
						"Despertar espurio de productor" +
						" ***********************");
			} else {
				productoresLiberados--;
				if (productoresLiberados == 0) {
					int maxLiberar = CAPACIDAD - numDatos - 1;
					if (productoresEsperando > 0 && maxLiberar > 0) {
						while (productoresEsperando > 0
								&& productoresLiberados < maxLiberar) {
							productoresEsperando--;
							productoresLiberados++;
						}
						this.notifyAll();
					}
				}
				break;
			}
		}
		buffer.meter(dato);
		numDatos++;
		if (consumidoresEsperando > 0) {
			consumidoresEsperando = 0;
			consumidoresLiberados++;
			this.notifyAll();
		}
		pantalla.escribir("Productor " +
				Thread.currentThread().hashCode() +
				" mete. Datos = " + numDatos +
				" productoresEsperando = " + productoresEsperando +
				" productoresLiberados = " + productoresLiberados +
				" consumidoresEsperando = " + consumidoresEsperando +
				" consumidoresLiberados = " + consumidoresLiberados);
	}

	public synchronized Dato recibir() throws InterruptedException {
		int veces = 0;
		// ES NECESARIO EL WHILE
		while (numDatos == 0 || consumidoresLiberados > 0) {
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
			consumidoresEsperando++;
			this.wait();
			if (consumidoresLiberados == 0) {    // DESPERTAR ESPURIO
				consumidoresEsperando--;
				pantalla.escribir(
						"Despertar espurio de consumidor" +
						" ***********************");
			} else {
				consumidoresLiberados--;
				if (consumidoresLiberados == 0) {
					int maxLiberar = numDatos - 1;
					if (consumidoresEsperando > 0 && maxLiberar > 0) {
						while (consumidoresEsperando > 0
								&& consumidoresLiberados < maxLiberar) {
							consumidoresEsperando--;
							consumidoresLiberados++;
						}
						this.notifyAll();
					}
				}
				break;
			}
		}
		Dato dato = buffer.sacar();
		numDatos--;
		if (productoresEsperando > 0) {
			productoresEsperando = 0;
			productoresLiberados++;
			this.notifyAll();
		}
		pantalla.escribir("Consumidor " +
				Thread.currentThread().hashCode() +
				" saca. Datos = " + numDatos +
				" productoresEsperando = " + productoresEsperando +
				" productoresLiberados = " + productoresLiberados +
				" consumidoresEsperando = " + consumidoresEsperando +
				" consumidoresLiberados = " + consumidoresLiberados);
		return dato;
	}
}
