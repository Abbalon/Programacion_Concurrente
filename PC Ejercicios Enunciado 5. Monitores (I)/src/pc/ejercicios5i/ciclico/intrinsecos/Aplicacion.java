package pc.ejercicios5i.ciclico.intrinsecos;

class Aplicacion {

	public static void main(String[] args) {
		final int NUM_PROCESOS = 3;
		Sincronizacion sincro = new Sincronizacion(NUM_PROCESOS);
		Proceso[] procesos = new Proceso[NUM_PROCESOS];
                System.out.println("ciclico.intrinsecos");
		for (int i = 0; i < procesos.length; i++) {
			procesos[i] = new Proceso(i, sincro);
		}
		for (int i = 0; i < procesos.length; i++) {
			procesos[i].start();
		}
	}
}