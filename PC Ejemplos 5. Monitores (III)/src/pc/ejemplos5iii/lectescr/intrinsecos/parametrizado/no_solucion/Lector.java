package pc.ejemplos5iii.lectescr.intrinsecos.parametrizado.no_solucion;

abstract class Lector<Tipo> extends Thread {

	private BaseDatos<Tipo> baseDatos;

	public Lector(BaseDatos<Tipo> baseDatos) {
		this.baseDatos = baseDatos;
	}

	public void run() {
		try {
			while (true) {
//			for (int i = 0; i< 10; i++) {
				Tipo datos = baseDatos.leer();
				this.procesar(datos);
			}
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	protected abstract void procesar(Tipo datos);
}