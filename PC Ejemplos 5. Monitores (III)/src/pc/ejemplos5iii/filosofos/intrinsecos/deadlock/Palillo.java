package pc.ejemplos5iii.filosofos.intrinsecos.deadlock;

class Palillo {

	private boolean libre;

	public Palillo() {
		libre = true;
	}

	public synchronized void coger() throws InterruptedException {
		while (!libre) {
			this.wait();
		}
		libre = false;
	}

	public synchronized void dejar() {
		libre = true;
		this.notify();
	}
}
