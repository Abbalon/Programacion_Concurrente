package pc.ejemplos2.runnable;

class Aplicacion {

	public static void main(String[] args) {
		Proceso p1 = new Proceso(1);
		Proceso p2 = new Proceso(2);
		Thread h1 = new Thread(p1);
		Thread h2 = new Thread(p2);
		h1.start();
		h2.start();
		try {
			h1.join();
			h2.join();
		} catch (InterruptedException ex) {
			ex.printStackTrace();
			System.exit(-1);
		}
		System.out.println("\nFin del programa");
	}
}
