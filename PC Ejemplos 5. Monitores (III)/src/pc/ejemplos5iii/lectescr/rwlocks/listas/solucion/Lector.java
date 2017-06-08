package pc.ejemplos5iii.lectescr.rwlocks.listas.solucion;

class Lector extends Thread {

	private BaseDatos datos;

	public Lector(BaseDatos datos) {
		this.datos = datos;
	}

	public void run() {
		while (true) {
//		for (int i = 0; i< 10; i++) {
			int[] lista = datos.leer();
			this.procesar(lista);
		}
	}

	private void procesar(int[] lista) {
		String cadena = "[";
		for (int i = 0; i < lista.length; i++) {
			cadena = cadena + " " + lista[i];
		}
		cadena = cadena + " ]";
		Pantalla.getPantalla().escribir("Proceso " + cadena);
	}
}
