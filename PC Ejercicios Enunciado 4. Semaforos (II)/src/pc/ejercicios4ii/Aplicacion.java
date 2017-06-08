package pc.ejercicios4ii;

class Aplicacion {

    public static void main(String[] args) {
        final int NUM_MAQUINAS = 10;
        final int NUM_ROBOTS = 10;
        final int MAX_PIEZAS_ALMACEN = 10;

        Almacen almacen = new Almacen(MAX_PIEZAS_ALMACEN);
        Maquina[] maquinas = new Maquina[NUM_MAQUINAS];
        Robot[] robots = new Robot[NUM_ROBOTS];
        for (int i = 0; i < maquinas.length; i++) {
            maquinas[i] = new Maquina(i, almacen);
            maquinas[i].start();
        }
        for (int i = 0; i < robots.length; i++) {
            robots[i] = new Robot(i, almacen);
            robots[i].start();
        }
    }
}
