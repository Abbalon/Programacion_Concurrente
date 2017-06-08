package pc.laboratorio4iii;

import java.util.concurrent.Semaphore;

class Cuenta {

    private String codigo;
    private int saldo;
    private Semaphore exclusion;
    private int cantidadACubrir;
    private int enCola;
    private Semaphore cola;
    private boolean enSilla;
    private Semaphore silla;
    private Pantalla pantalla;
    
    //
    private boolean freeStack;
    private boolean freeWait;

    public Cuenta(String codigo) {
        this.codigo = codigo;
        saldo = 0;
        exclusion = new Semaphore(1);
        enCola = 0;
        cola = new Semaphore(0);
        enSilla = false;
        silla = new Semaphore(0);
        pantalla = Pantalla.getPantalla();
        
        freeStack = false;//=======================
        freeWait = false;//=======================
    }

    public void ingresar(int cantidad)
            throws CuentaException, InterruptedException {
        if (cantidad < 0) {
            throw new CuentaException(codigo,
                    "Ingreso de cantidad " + cantidad + " negativa");
        }
        exclusion.acquire();
        saldo = saldo + cantidad;
        pantalla.escribir(codigo + " - "
                + "Ingresar: Meto -> Hay " + saldo);
        if (saldo >= cantidadACubrir && enSilla) {
            enSilla = false;
            pantalla.escribir(codigo + " - "
                    + "Ingresar: Despierto al de la silla");
            //avisa que hay alguien en la silla
            freeWait = true;//=======================
            silla.release();//=======================
        }
        exclusion.release();
    }

    public void retirar(int cantidad)
            throws CuentaException, InterruptedException {
        if (cantidad < 0) {
            throw new CuentaException(codigo,
                    "Retirada de cantidad " + cantidad + " negativa");
        }
        exclusion.acquire();
        if (enSilla  || freeStack || freeWait) {//=======================
            enCola++;//=======================
            pantalla.escribir(codigo + " - "
                    + "Retirar: En cola hay " + enCola);
            //Libera exclusion mutua
            exclusion.release();
            cola.acquire();
            //vuelve a coger exclusion mutua
            exclusion.acquire();
            pantalla.escribir(codigo + " - "
                    + "Retirar: Abandono la cola");
            freeStack = false;//=======================
        }
        if (cantidad > saldo) {
            cantidadACubrir = cantidad;
            enSilla = true;//=======================
            pantalla.escribir(codigo + " - "
                    + "Retirar: En silla: Quiero " + cantidad);
            //Libera exclusion mutua
            exclusion.release();
            silla.acquire();
            Thread.sleep((long) (1000 * Math.random()));
            //vuelve a coger exclusion mutua
            exclusion.acquire();
            freeWait = false;//=======================
        }
        saldo = saldo - cantidad;
        pantalla.escribir(codigo + " - "
                + "Retirar: Saco -> Hay " + saldo);
        if (enCola > 0) {
            enCola--;
            freeStack = true;//=======================
            pantalla.escribir(codigo + " - "
                    + "Retirar: Despierto al de la cola");
            pantalla.escribir(codigo + " - "
                    + "Retirar: En cola hay " + enCola);
            cola.release();
        }
        pantalla.escribir(codigo + " - "
                + "Retirar: Adios");
        try {
            if (saldo < 0) {
                throw new CuentaException(codigo,
                        "ERROR FATAL: Saldo " + saldo + " negativo");
            }
        } finally {
            exclusion.release();
        }
    }

    public String getCodigo() throws InterruptedException {
        exclusion.acquire();
        try {
            return codigo;
        } finally {
            exclusion.release();
        }
    }

    public int getSaldo() throws InterruptedException {
        exclusion.acquire();
        try {
            return saldo;
        } finally {
            exclusion.release();
        }
    }
}
