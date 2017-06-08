package pc.ejemplos4iii.lectescr.listas;

class Lector extends Thread {

	private BaseDatos datos;

	public Lector(BaseDatos datos) {
		this.datos = datos;
	}

	public void run() {
		try {
//			while (true) {
			for (int i = 0; i< 10; i++) {
				int[] lista = datos.leer();
				this.procesar(lista);
			}
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	private void procesar(int[] lista) {
		String cadena = "[";
		for (int i = 0; i < lista.length; i++) {
			cadena = cadena + " " + lista[i];
		}
		cadena = cadena + " ]";
		try {
			Pantalla.getPantalla().escribir("Proceso " + cadena);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
}
