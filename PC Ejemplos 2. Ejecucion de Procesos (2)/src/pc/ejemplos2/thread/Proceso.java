package pc.ejemplos2.thread;

class Proceso extends Thread {

	private int id;

	public Proceso(int id) {
		this.id = id;
	}

	public void run() {
		for (int i  = 0; i < 50; i++) {
			System.out.print(id);
		}
	}
}
