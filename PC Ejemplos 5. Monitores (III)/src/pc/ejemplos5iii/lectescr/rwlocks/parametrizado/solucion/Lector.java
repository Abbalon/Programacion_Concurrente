package pc.ejemplos5iii.lectescr.rwlocks.parametrizado.solucion;

abstract class Lector<Tipo> extends Thread {

	private BaseDatos<Tipo> baseDatos;

	public Lector(BaseDatos<Tipo> baseDatos) {
		this.baseDatos = baseDatos;
	}

	public void run() {
		while (true) {
//		for (int i = 0; i< 10; i++) {
			Tipo datos = baseDatos.leer();
			this.procesar(datos);
		}
	}

	protected abstract void procesar(Tipo datos);
}