package pc.ejercicios4ii;

class Almacen {

    private Buffer<Pieza> buffer;
    private AccesoAlmacen acceso;
    private Pantalla pantalla;

    public Almacen(int maxPiezas) {
        buffer = new Buffer<Pieza>(maxPiezas);
        acceso = new AccesoAlmacen(maxPiezas);
        pantalla = Pantalla.getPantalla();
    }

    public void depositar(int idMaquina, Pieza pieza)
            throws InterruptedException {
        acceso.inicioDepositar(idMaquina);
        try {
            buffer.meter(pieza);
            pantalla.escribir("Maquina " + idMaquina
                    + " empieza a depositar pieza " + pieza.getCodigo());
            Thread.sleep((long) (100 * Math.random()));
            pantalla.escribir("Maquina " + idMaquina
                    + " termina de depositar pieza " + pieza.getCodigo());
        } finally {
            acceso.finDepositar(idMaquina);
        }
    }

    public Pieza recoger(int idRobot) throws InterruptedException {
        acceso.inicioRecoger(idRobot);
        try {
            Pieza pieza = buffer.sacar();
            pantalla.escribir("Robot " + idRobot
                    + " empieza a recoger pieza " + pieza.getCodigo());
            Thread.sleep((long) (100 * Math.random()));
            pantalla.escribir("Robot " + idRobot
                    + " termina de recoger pieza " + pieza.getCodigo());
            return pieza;
        } finally {
            acceso.finRecoger(idRobot);
        }
    }
}
