package pc.ejemplos5iii.filosofos.intrinsecos.inanicion;

class Aplicacion {

	public static void main(String[] args) {
		final int NUM_FILOSOFOS = 5;
		Palillo[] palillos = new Palillo[NUM_FILOSOFOS];
		for (int i = 0; i < palillos.length; i++) {
			palillos[i] = new Palillo();
		}
		Comedor comedor = new Comedor(NUM_FILOSOFOS - 1);
		Filosofo[] filosofos = new Filosofo[NUM_FILOSOFOS];
		for (int i = 0; i < filosofos.length; i++) {
			filosofos[i] = new Filosofo(i, palillos, comedor);
			filosofos[i].start();
		}
	}
}
