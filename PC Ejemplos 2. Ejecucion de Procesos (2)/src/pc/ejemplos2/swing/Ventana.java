package pc.ejemplos2.swing;

import java.awt.Dimension;
import javax.swing.JFrame;

class Ventana extends JFrame {

	public Ventana() {
		PanelContenido panelContenido = new PanelContenido();
		this.setContentPane(panelContenido);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Ordenador");
		this.setLocation(150, 150);
		this.setPreferredSize(new Dimension(500, 150));
		this.pack();
		this.setResizable(true);
		this.setVisible(true);
	}
}

