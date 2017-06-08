package pc.laboratorio5iii.java;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Cuenta {

    private String codigo;
    private int saldo;
    private Lock exclusion;
    private int cantidadACubrir;
    private int enCola;
    private Condition cola;
    private boolean enSilla;
    private Condition silla;
    private Pantalla pantalla;
    boolean libSilla, libCola;

    public Cuenta(String codigo) {
        this.codigo = codigo;
        saldo = 0;
        exclusion = new ReentrantLock(true);
        enCola = 0;
        cola = exclusion.newCondition();
        enSilla = false;
        silla = exclusion.newCondition();
        pantalla = Pantalla.getPantalla();
        libSilla = false;
        libCola = false;
    }

    public void ingresar(int cantidad)
            throws CuentaException, InterruptedException {
        exclusion.lock();
        try {
            if (cantidad < 0) {
                throw new CuentaException(this,
                        "Ingreso de cantidad " + cantidad + " negativa");
            }
           // saldo = saldo + cantidad;
            pantalla.escribir(codigo + " - "
                    + "Ingresar: Meto -> Hay " + saldo);
            // TODO 2.1: Despertar al de la silla si saldo >= cantidadACubrir
            saldo += cantidad;
            if (enSilla
                    && cantidad >= cantidadACubrir) {
                enSilla = false;
                libSilla = true;
                silla.signal();
            }
        } finally {
            exclusion.unlock();
        }
    }

    public void retirar(int cantidad)
            throws CuentaException, InterruptedException {
        exclusion.lock();
        try {
            if (cantidad < 0) {
                throw new CuentaException(this,
                        "Retirada de cantidad " + cantidad + " negativa");
            }
            // TODO 2.2: Esperar en la cola si hay alguien en la silla
            while (enSilla
                    || libCola
                    || libSilla) {
                enCola++;
                cola.await();
                if (!libCola) {
                    enCola--;
                } else {
                    libCola = false;
                    break;
                }
            }
            // TODO 2.3: Esperar en la silla si no hay saldo suficiente
            //         haciendo previamente cantidadACubrir = cantidad
            while (saldo < cantidad) {
                cantidadACubrir = cantidad;
                enSilla = true;
                silla.await();
                if (!libSilla) {

                } else {
                    libSilla = false;
                    break;
                }
            }
            //saldo -= cantidad;
            saldo = saldo - cantidad;
            if (saldo < 0) {
                throw new RuntimeException(
                        "ERROR FATAL: Saldo " + saldo + " negativo");
            }
            pantalla.escribir(codigo + " - "
                    + "Retirar: Saco -> Hay " + saldo);

            // TODO 2.4: Despertar a uno de la cola
            if (enCola > 0) {
                enCola--;
                libCola = true;
                cola.signal();
            }
        } finally {
            exclusion.unlock();
        }
    }

    public String getCodigo() throws InterruptedException {
        exclusion.lock();
        try {
            return codigo;
        } finally {
            exclusion.unlock();
        }
    }

    public int getSaldo() throws InterruptedException {
        exclusion.lock();
        try {
            return saldo;
        } finally {
            exclusion.unlock();
        }
    }
}
