package pc.ejercicios4iii;

class Maquina extends Thread {

	private int idMaquina;
	private Almacen almacen;
	private int numPieza;

	public Maquina(int idMaquina, Almacen almacen) {
		this.idMaquina = idMaquina;
		this.almacen = almacen;
		numPieza = 1;
	}

	public void run() {
		try {
//			while (true) {
			for (int i = 0; i < 5; i++) {
				Pieza pieza = this.fabricar();
				almacen.depositar(idMaquina, pieza);
			}
			Pantalla.getPantalla().escribir("==========Maquina " + idMaquina +
					" TERMINA SU JORNADA");
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	private Pieza fabricar() throws InterruptedException {
//		Pantalla.getPantalla().escribir("Maquina " + idMaquina +
//				" fabricando pieza " + numPieza);
		Thread.sleep((long) (1000 * Math.random()));
		Pieza pieza = new Pieza(idMaquina + "-" + numPieza);
		numPieza++;
		return pieza;
	}
}
