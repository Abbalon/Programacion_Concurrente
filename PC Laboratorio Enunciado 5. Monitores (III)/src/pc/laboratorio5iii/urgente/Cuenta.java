package pc.laboratorio5iii.urgente;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import pc.util.concurrent.locks.UrgentQueueReentrantLock;

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

    public Cuenta(String codigo) {
        this.codigo = codigo;
        saldo = 0;
        exclusion = new UrgentQueueReentrantLock();
        enCola = 0;
        cola = exclusion.newCondition();
        enSilla = false;
        silla = exclusion.newCondition();
        pantalla = Pantalla.getPantalla();
    }

    public void ingresar(int cantidad)
            throws CuentaException, InterruptedException {
        exclusion.lock();
        try {
            if (cantidad < 0) {
                throw new CuentaException(this,
                        "Ingreso de cantidad " + cantidad + " negativa");
            }
            saldo = saldo + cantidad;
            pantalla.escribir(codigo + " - "
                    + "Ingresar: Meto -> Hay " + saldo);
            // TODO 1.1: Despertar al de la silla si saldo >= cantidadACubrir
            saldo += cantidad;
            if(enSilla
                    && cantidad >= cantidadACubrir){
                enSilla = false;
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
            // TODO 1.2: Esperar en la cola si hay alguien en la silla
            if (enSilla) {
                enCola++;
                cola.await();
            }

            // TODO 1.3: Esperar en la silla si no hay saldo suficiente
            //         haciendo previamente cantidadACubrir = cantidad
            if(saldo < cantidad){
                cantidadACubrir = cantidad; 
                enSilla = true;
                silla.await();
            }
            saldo -= cantidad;
            
            saldo = saldo - cantidad;
            if (saldo < 0) {
                throw new RuntimeException(
                        "ERROR FATAL: Saldo " + saldo + " negativo");
            }
            pantalla.escribir(codigo + " - "
                    + "Retirar: Saco -> Hay " + saldo);

            // TODO 1.4: Despertar a uno de la cola
            cola.signal();
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
