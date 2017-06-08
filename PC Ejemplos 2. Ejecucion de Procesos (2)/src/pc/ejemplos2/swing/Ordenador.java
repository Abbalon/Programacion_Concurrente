package pc.ejemplos2.swing;

abstract class Ordenador {

    protected int[] array;
    protected PanelProceso panel;

    protected Ordenador(int[] array, PanelProceso panel) {
        this.array = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            this.array[i] = array[i];
        }
        this.panel = panel;
    }

    protected abstract void ordenar();   
}
