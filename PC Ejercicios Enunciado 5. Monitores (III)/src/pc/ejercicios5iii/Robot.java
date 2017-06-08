package pc.ejercicios5iii;

class Robot extends Thread {

	private int idRobot;
	private Almacen almacen;

	public Robot(int idRobot, Almacen almacen) {
		this.idRobot = idRobot;
		this.almacen = almacen;
	}

	public void run() {
		try {
//			while (true) {
			for (int i = 0; i< 5; i++) {
				Thread.sleep(1000);
				Pieza pieza = almacen.recoger(idRobot);
				this.transportar(pieza);
			}
			Pantalla.getPantalla().escribir("\nRobot " + idRobot +
					" TERMINA SU JORNADA");
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	private void transportar(Pieza pieza) throws InterruptedException {
//		Pantalla.getPantalla().escribir("Robot " + idRobot +
//				" transportando pieza " + pieza.getCodigo());
		Thread.sleep((long) (100 * Math.random()));
	}
}