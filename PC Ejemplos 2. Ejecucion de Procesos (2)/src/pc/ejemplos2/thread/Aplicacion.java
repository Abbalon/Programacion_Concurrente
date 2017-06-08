package pc.ejemplos2.thread;

class Aplicacion {

	public static void main(String[] args) {
		Proceso p1 = new Proceso(1);
		Proceso p2 = new Proceso(2);
		p1.start();
		p2.start();
		try {
			p1.join();
			p2.join();
		} catch (InterruptedException ex) {
			ex.printStackTrace();
			System.exit(-1);
		}
		System.out.println("\nFin del programa");
	}
}
