package pc.ejemplos5iii.lectescr.intrinsecos.parametrizado.inanicion;

abstract class Escritor<Tipo> extends Thread {

	private BaseDatos<Tipo> baseDatos;

	public Escritor(BaseDatos<Tipo> baseDatos) {
		this.baseDatos = baseDatos;
	}

	public void run() {
		try {
			while (true) {
//			for (int i = 0; i< 10; i++) {
				Tipo datos = this.generar();
				baseDatos.escribir(datos);
			}
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	protected abstract Tipo generar();
}
