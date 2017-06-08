package pc.ejemplos5iii.lectescr.rwlocks.parametrizado.solucion;

abstract class Escritor<Tipo> extends Thread {

	private BaseDatos<Tipo> baseDatos;

	public Escritor(BaseDatos<Tipo> baseDatos) {
		this.baseDatos = baseDatos;
	}

	public void run() {
		while (true) {
//		for (int i = 0; i< 10; i++) {
			Tipo datos = this.generar();
			baseDatos.escribir(datos);
		}
	}

	protected abstract Tipo generar();
}
