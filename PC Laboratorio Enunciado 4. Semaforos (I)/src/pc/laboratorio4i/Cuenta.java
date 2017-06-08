package pc.laboratorio4i;

// TODO 2: Conseguir exclusion mutua en el acceso a la cuenta
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

class Cuenta {

    private String codigo;
    private int saldo;
    Semaphore accesoCuenta = new Semaphore(1);

    public Cuenta(String codigo) {
        this.codigo = codigo;
        saldo = 0;
    }

    public void ingresar(int cantidad) throws CuentaException {
        try {
            accesoCuenta.acquire();
            if (cantidad < 0) {
                throw new CuentaException(codigo,
                        "Ingreso de cantidad " + cantidad + " negativa");
            }
            saldo = saldo + cantidad;
        } catch (InterruptedException ex) {
            Logger.getLogger(Cuenta.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            accesoCuenta.release();
        }
    }

    public void retirar(int cantidad) throws CuentaException {
        try {
            accesoCuenta.acquire();
            if (cantidad < 0) {
                throw new CuentaException(codigo,
                        "Retirada de cantidad " + cantidad + " negativa");
            }
            if (cantidad > saldo) {
                throw new CuentaException(codigo,
                        "Saldo " + saldo + " insuficiente"
                        + " para retirada de " + cantidad);
            }
            saldo = saldo - cantidad;
            if (saldo < 0) {
                throw new CuentaException(codigo,
                        "ERROR FATAL: Saldo " + saldo + " negativo");
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Cuenta.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            accesoCuenta.release();
        }
    }

    public String getCodigo() {
        String copia = new String();
        try {
            accesoCuenta.acquire();
            copia = this.codigo;
            accesoCuenta.release();
        } catch (InterruptedException ex) {
            Logger.getLogger(Cuenta.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return copia;
        }
    }

    public int getSaldo() {
        int copia = 0;
        try {
            accesoCuenta.acquire();
            copia = this.saldo;
            accesoCuenta.release();
        } catch (InterruptedException ex) {
            Logger.getLogger(Cuenta.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return copia;
        }
    }
}
