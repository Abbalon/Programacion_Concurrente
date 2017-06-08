package pc.laboratorio5i.locks;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Cuenta {

    private String codigo;
    private int saldo;
    private Pantalla pantalla;

    private boolean turnoIngresar;
    private Lock mutex;
    private Condition condIngresar, condRetirar;

    public Cuenta(String codigo) {
        this.codigo = codigo;
        saldo = 0;
        pantalla = Pantalla.getPantalla();

        turnoIngresar = true;
        mutex = new ReentrantLock();
        condIngresar = mutex.newCondition();
        condRetirar = mutex.newCondition();
    }

    // TODO 6: Conseguir exclusion mutua con cerrojos
    // TODO 12: Garantizar que se ingresa y se retira alternativamente
    //          usando cerrojos y condiciones
    public void ingresar(int cantidad)
            throws CuentaException, InterruptedException {
        mutex.lock();

        try {
            while (!turnoIngresar) {
                condIngresar.await();
            }
            //ocupado = false;
            if (cantidad < 0) {
                throw new CuentaException(this,
                        "Ingreso de cantidad " + cantidad + " negativa");
            }
            pantalla.escribir("Cuenta = " + this.getCodigo()
                    + " Ingreso " + cantidad);
            saldo = saldo + cantidad;

            turnoIngresar = !turnoIngresar;
            condIngresar.signal();
        } finally {
            mutex.unlock();
        }
    }

    // TODO 6: Conseguir exclusion mutua con cerrojos
    // TODO 12: Garantizar que se ingresa y se retira alternativamente
    //          usando cerrojos y condiciones
    public void retirar(int cantidad)
            throws CuentaException, InterruptedException {
        mutex.lock();

        try {
            while (turnoIngresar) {
                condRetirar.await();
            }
            //turnoIngresar = false;
            if (cantidad < 0) {
                throw new CuentaException(this,
                        "Retirada de cantidad " + cantidad + " negativa");
            }
            if (cantidad > saldo) {
                throw new CuentaException(this,
                        "Saldo " + saldo + " insuficiente"
                        + " para retirada de " + cantidad);
            }
            pantalla.escribir("Cuenta = " + this.getCodigo()
                    + " Retiro " + cantidad);
            saldo = saldo - cantidad;
            if (saldo < 0) {
                throw new CuentaException(this,
                        "ERROR FATAL: Saldo " + saldo + " negativo");
            }

            turnoIngresar = !turnoIngresar;
            condRetirar.signal();
        } finally {
            mutex.unlock();
        }
    }

    // TODO 6: Conseguir exclusion mutua con cerrojos
    public String getCodigo() {
        mutex.lock();

        try {
            return codigo;
        } finally {
            mutex.unlock();
        }
    }

    // TODO 6: Conseguir exclusion mutua con cerrojos
    public int getSaldo() {
        mutex.lock();

        try {
            return saldo;
        } finally {
            mutex.unlock();
        }
    }
}
