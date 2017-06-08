package pc.ejemplos5iii.lectescr.locks.listas.inanicion;

class Escritor extends Thread {

	private BaseDatos datos;

	public Escritor(BaseDatos datos) {
		this.datos = datos;
	}

	public void run() {
		try {
			while (true) {
//			for (int i = 0; i< 10; i++) {
				int[] lista = this.generar();
				datos.escribir(lista);
			}
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	private int[] generar() {
		int[] lista = new int[10];
		Integer entero = new Integer((int) (10 * Math.random()) + 1);
		String cadena = "[";
		for (int i = 0; i < lista.length; i++) {
			lista[i] = entero;
			cadena = cadena + " " + lista[i];
		}
		cadena = cadena + " ]";
		Pantalla.getPantalla().escribir("Genero " + cadena);
		try {
			Thread.sleep((long) (1000 * Math.random()));
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
		return lista;
	}
}