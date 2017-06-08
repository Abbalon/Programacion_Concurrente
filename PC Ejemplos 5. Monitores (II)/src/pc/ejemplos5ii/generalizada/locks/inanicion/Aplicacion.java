package pc.ejemplos5ii.generalizada.locks.inanicion;

class Aplicacion {

	public static void main(String[] args) {
		final int NUM_VISITANTES = 10;
		final int AFORO = 4;
		Museo museo = new Museo(AFORO);
		Visitante[] visitantes = new Visitante[NUM_VISITANTES];
		for (int i = 0; i < visitantes.length; i++) {
			visitantes[i] = new Visitante(museo);
		}
		for (int i = 0; i < visitantes.length; i++) {
			visitantes[i].start();
		}
	}
}
