package pc.ejemplos2.swing;

class Seleccion extends Ordenador implements Runnable {
    
    ControlOrdenador extension= new ControlOrdenador();

    protected Seleccion(int[] array, PanelProceso panel) {
        super(array, panel);
    }

    protected void ordenar() {
        run();
    }

    public void run() {
        panel.escribir(array);
        extension.esperar();
        for (int i = 0; i < array.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[min]) {
                    min = j;
                }
            }
            if (i != min) {
                extension.intercambiar(array, i, min);
                /*int aux = array[i];
                array[i] = array[min];
                array[min] = aux;*/
                panel.escribir(array);
                extension.esperar();
            }
        }
    }
}
