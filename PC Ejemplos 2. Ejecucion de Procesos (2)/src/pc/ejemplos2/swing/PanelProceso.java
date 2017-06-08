package pc.ejemplos2.swing;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

class PanelProceso extends JPanel {

	private JLabel label;

	public PanelProceso() {
		label = new JLabel("");
		this.add(label);
	}

	public void escribir(int[] array) {
		String cadena = "";
		for (int i = 0; i < array.length; i++) {
			cadena = cadena + array[i] + " ";
		}
		final String aux = cadena;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				label.setText(aux);
			}
		});
	}
}
