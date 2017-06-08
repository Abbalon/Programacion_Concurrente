package pc.ejemplos2.swing;

class Burbuja extends Ordenador implements Runnable {

    ControlOrdenador extension= new ControlOrdenador();
    
    protected Burbuja(int[] array, PanelProceso panel) {
        super(array, panel);
    }

    protected void ordenar() {
        run();
    }
    
    public void run(){
        panel.escribir(array);
        extension.esperar();
        for (int i = 1; i < array.length; i++) {
            for (int k = array.length - 1; k >= i; k--) {
                if (array[k] < array[k - 1]) {
                    extension.intercambiar(array, k, k-1);
                    /*int aux = array[k];
                    array[k] = array[k - 1];
                    array[k - 1] = aux;*/
                    panel.escribir(array);
                    extension.esperar();
                }
            }
        }
    }
}
