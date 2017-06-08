package pc.laboratorio4ii;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

class Banco {

    private Map<String, Cuenta> mapaCuentas;
    private Cajero[] cajeros;
    private final int NUM_CAJEROS = 10;
    private Logger logger;

    private Banco() throws IOException {
        mapaCuentas = new TreeMap<String, Cuenta>();
        Cuenta cuenta1 = new Cuenta("1111111111");
        mapaCuentas.put("1111111111", cuenta1);
        Cuenta cuenta2 = new Cuenta("2222222222");
        mapaCuentas.put("2222222222", cuenta2);

        logger = new Logger("banco.log");

        cajeros = new Cajero[NUM_CAJEROS];
        for (int i = 0; i < cajeros.length; i++) {
            cajeros[i] = new Cajero(i, this, logger);
        }
    }

    public Cuenta getCuenta(String codigo) {
        return mapaCuentas.get(codigo);
    }

    private void ejecutar() {
        // TODO 6: Ejecutar el logger en un hilo independiente
        logger.start();
        for (int i = 0; i < cajeros.length; i++) {
            cajeros[i].start();
        }
        try {
            for (int i = 0; i < cajeros.length; i++) {
                cajeros[i].join();
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
            System.exit(-1);
        } finally {
            logger.cerrar();
        }

        Collection<Cuenta> coleccionCuentas = mapaCuentas.values();
        for (Cuenta cuenta : coleccionCuentas) {
            try {
                Pantalla.getPantalla().escribir(
                        "Cuenta = " + cuenta.getCodigo()
                        + " Saldo = " + cuenta.getSaldo());
            } catch (InterruptedException ex) {
                System.out.println("EXCEPCION al escribir saldo: "
                        + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        try {
            Banco banco = new Banco();
            banco.ejecutar();
        } catch (IOException ex) {
            System.out.println("EXCEPCION al crear el banco: "
                    + ex.getMessage());
        }
    }
}
