package pc.ejemplos5ii.generalizada.intrinsecos.solucion1;

class Visitante extends Thread {

	private Museo museo;

	public Visitante(Museo museo) {
		this.museo = museo;
	}

	public void run() {
		Pantalla pantalla = Pantalla.getPantalla();
		try {
			while (true) {
//			for (int i  = 0; i < 10; i++) {
				museo.entrar();
				pantalla.escribir("Viendo el museo");
				for (int j = 0; j < (int) 10000 * Math.random(); j++) ;
				museo.salir();
				pantalla.escribir("Otras cosas");
				for (int j = 0; j < (int) 10000 * Math.random(); j++) ;
			}
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
}
