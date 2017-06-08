package pc.ejemplos5iii.filosofos.intrinsecos.deadlock;

class Aplicacion {

	public static void main(String[] args) {
		final int NUM_FILOSOFOS = 5;
		Palillo[] palillos = new Palillo[NUM_FILOSOFOS];
		for (int i = 0; i < palillos.length; i++) {
			palillos[i] = new Palillo();
		}
		Filosofo[] filosofos = new Filosofo[NUM_FILOSOFOS];
		for (int i = 0; i < filosofos.length; i++) {
			filosofos[i] = new Filosofo(i, palillos);
			filosofos[i].start();
		}
	}
}
