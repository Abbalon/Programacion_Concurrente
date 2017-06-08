package pc.laboratorio5i.intrinsecos;

class Cuenta {

    private String codigo;
    private int saldo;
    private Pantalla pantalla;
    private boolean turnoIngresar;

    public Cuenta(String codigo) {
        this.codigo = codigo;
        saldo = 0;
        pantalla = Pantalla.getPantalla();
        turnoIngresar = true;
    }

    // TODO 2: Conseguir exclusion mutua con monitores primitivos
    // TODO 10: Garantizar que se ingresa y se retira alternativamente
    //          usando monitores primitivos
    public synchronized void ingresar(int cantidad)
            throws CuentaException, InterruptedException {
        while (!turnoIngresar) {
            this.wait();
        }
        //turnoIngresar = false;
        if (cantidad < 0) {
            throw new CuentaException(this,
                    "Ingreso de cantidad " + cantidad + " negativa");
        }
        pantalla.escribir("Cuenta = " + this.getCodigo()
                + " Ingreso " + cantidad);
        saldo = saldo + cantidad;
        turnoIngresar = !turnoIngresar;
        this.notifyAll();
    }

    // TODO 2: Conseguir exclusion mutua con monitores primitivos
    // TODO 10: Garantizar que se ingresa y se retira alternativamente
    //          usando monitores primitivos
    public synchronized void retirar(int cantidad)
            throws CuentaException, InterruptedException {
        while (turnoIngresar) {
            this.wait();
        }
        //turnoIngresar = true;
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
        this.notifyAll();
    }

    // TODO 2: Conseguir exclusion mutua con monitores primitivos
    public synchronized String getCodigo() {
        return codigo;
    }

    // TODO 2: Conseguir exclusion mutua con monitores primitivos
    public synchronized int getSaldo() {
        return saldo;
    }
}
