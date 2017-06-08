package pc.ejemplos2.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

class PanelContenido extends JPanel implements ActionListener {

	private JButton iniciar;
	private PanelProceso panelBurbuja;
	private PanelProceso panelSeleccion;

	public PanelContenido() {
		iniciar = new JButton("Iniciar");
		iniciar.addActionListener(this);
		panelBurbuja = new PanelProceso();
		panelBurbuja.setBorder(BorderFactory.createTitledBorder(" Burbuja "));
		panelSeleccion = new PanelProceso();
		panelSeleccion.setBorder(BorderFactory.createTitledBorder(" Selección "));

		GroupLayout distribuidor = new GroupLayout(this);
		this.setLayout(distribuidor);
		distribuidor.setAutoCreateGaps(true);
		distribuidor.setAutoCreateContainerGaps(true);

		distribuidor.setHorizontalGroup(
				distribuidor.createParallelGroup(GroupLayout.Alignment.CENTER)
					.addGroup(distribuidor.createSequentialGroup()
						.addComponent(panelBurbuja)
						.addComponent(panelSeleccion))
					.addComponent(iniciar));
		distribuidor.setVerticalGroup(
				distribuidor.createSequentialGroup()
					.addGroup(distribuidor.createParallelGroup(
							GroupLayout.Alignment.CENTER)
						.addComponent(panelBurbuja)
						.addComponent(panelSeleccion))
					.addComponent(iniciar));
	}

	public void actionPerformed(ActionEvent ev) {
		iniciar.setEnabled(false);
		int[] array = new int[10];
		for (int i = 0; i < array.length; i++) {
			array[i] = (int) (10.0 * Math.random());
		}
		final Ordenador burbuja = new Burbuja(array, panelBurbuja);
		final Ordenador seleccion = new Seleccion(array, panelSeleccion);
		final Thread hiloBurbuja = new Thread((Runnable) burbuja);
    final Thread hiloSeleccion = new Thread((Runnable) seleccion);
    hiloBurbuja.start();
		hiloSeleccion.start();
		new Thread() {
			public void run() {
				try {
					hiloBurbuja.join();
					hiloSeleccion.join();
				} catch (InterruptedException ex) {
					ex.printStackTrace();
					System.exit(-1);
				}
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						iniciar.setEnabled(true);
					}
				});
			}
		}.start();
	}
}
