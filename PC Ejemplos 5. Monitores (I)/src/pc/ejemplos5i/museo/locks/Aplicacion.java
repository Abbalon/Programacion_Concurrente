package pc.ejemplos5i.museo.locks;

class Aplicacion {

	public static void main(String[] args) {
		final int NUM_VISITANTES = 10;
		Museo museo = new Museo();
		Visitante[] visitantes = new Visitante[NUM_VISITANTES];
		for (int i = 0; i < visitantes.length; i++) {
			visitantes[i] = new Visitante(museo);
		}
		for (int i = 0; i < visitantes.length; i++) {
			visitantes[i].start();
		}
	}
}
