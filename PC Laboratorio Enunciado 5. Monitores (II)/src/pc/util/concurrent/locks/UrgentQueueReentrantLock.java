package pc.util.concurrent.locks;

/**
 * @author Adolfo Yela - adolfo.yela@upm.es
 */
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class UrgentQueueReentrantLock implements Lock {

    private boolean justicia;
    private Semaphore exclusionEntrada;
    private boolean libre;
    private List<Semaphore> colaEntrada;
    private int enEntrada;
    private List<Semaphore> colaUrgente;
    private int enUrgente;
    private volatile Thread hiloActual;
    private List<Semaphore> colaInterrumpidos;
    private int enInterrumpidos;
    private Semaphore ultimoLiberadoEntrada;

    private class NodoHilo {

        private Thread hilo;
        private int contador;

        private NodoHilo(Thread hilo, int contador) {
            this.hilo = hilo;
            this.contador = contador;
        }
    }

    private int contadorActual;
    private List<NodoHilo> listaBloqueados;

    public UrgentQueueReentrantLock() {
        this(false);
    }

    public UrgentQueueReentrantLock(boolean justicia) {
        this.justicia = justicia;
        exclusionEntrada = new Semaphore(1, true);
        libre = true;
        colaEntrada = new LinkedList<Semaphore>();
        enEntrada = 0;
        colaUrgente = new LinkedList<Semaphore>();
        enUrgente = 0;
        hiloActual = null;
        contadorActual = 0;
        listaBloqueados = new LinkedList<NodoHilo>();
        colaInterrumpidos = new LinkedList<Semaphore>();
        enInterrumpidos = 0;
        ultimoLiberadoEntrada = null;
    }

    public void lock() {
        if (hiloActual != Thread.currentThread()) {
            exclusionEntrada.acquireUninterruptibly();
            if (!libre) {
                Semaphore siguienteEntrada = new Semaphore(0);
                colaEntrada.add(siguienteEntrada);
                enEntrada++;
                exclusionEntrada.release();
                siguienteEntrada.acquireUninterruptibly();
                this.postLock();
            } else {
                libre = false;
                exclusionEntrada.release();
            }
            hiloActual = Thread.currentThread();
            contadorActual = 1;
        } else {
            contadorActual++;
        }
//		System.out.println(
//				"lock " + this.hashCode() + ":" +
//				" Hilo actual = " + hiloActual.hashCode() +
//				" Contador = " + contadorActual);
    }

    public void lockInterruptibly() throws InterruptedException {
        if (hiloActual != Thread.currentThread()) {
            exclusionEntrada.acquireUninterruptibly();
            if (!libre) {
                Semaphore siguienteEntrada = new Semaphore(0);
                colaEntrada.add(siguienteEntrada);
                enEntrada++;
                exclusionEntrada.release();
                try {
                    siguienteEntrada.acquire();
                } catch (InterruptedException ex) {
                    this.interrumpidoLock(siguienteEntrada);
                    throw ex;
                }
                this.postLock();
            } else {
                libre = false;
                exclusionEntrada.release();
            }
            hiloActual = Thread.currentThread();
            contadorActual = 1;
        } else {
            contadorActual++;
        }
//		System.out.println(
//				"lockInterruptibly " + this.hashCode() + ":" +
//				" Hilo actual = " + hiloActual.hashCode() +
//				" Contador = " + contadorActual);
    }

    public boolean tryLock() {
        boolean resultado = false;
        if (hiloActual != Thread.currentThread()) {
            exclusionEntrada.acquireUninterruptibly();
            if (!libre) {
                resultado = false;
            } else {
                libre = false;
                resultado = true;
            }
            exclusionEntrada.release();
            if (resultado) {
                hiloActual = Thread.currentThread();
                contadorActual = 1;
            }
        } else {
            resultado = true;
            contadorActual++;
        }
//		if (resultado) {
//			System.out.println(
//					"tryLock " + this.hashCode() + ":" +
//					" Hilo actual = " + hiloActual.hashCode() +
//					" Contador = " + contadorActual);
//		}
        return resultado;
    }

    public boolean tryLock(long time, TimeUnit unit)
            throws InterruptedException {
        boolean resultado = false;
        if (hiloActual != Thread.currentThread()) {
            exclusionEntrada.acquireUninterruptibly();
            if (!libre) {
                Semaphore siguienteEntrada = new Semaphore(0);
                colaEntrada.add(siguienteEntrada);
                enEntrada++;
                exclusionEntrada.release();
                resultado = siguienteEntrada.tryAcquire(time, unit);
                if (!resultado) {
                    this.interrumpidoLock(siguienteEntrada);
                    return resultado;
                }
                this.postLock();
            } else {
                libre = false;
                exclusionEntrada.release();
            }
            if (resultado) {
                hiloActual = Thread.currentThread();
                contadorActual = 1;
            }
        } else {
            resultado = true;
            contadorActual++;
        }
//		if (resultado) {
//			System.out.println(
//					"tryLock " + this.hashCode() + ":" +
//					" Hilo actual = " + hiloActual.hashCode() +
//					" Contador = " + contadorActual);
//		}
        return resultado;
    }

    public void unlock() {
        if (hiloActual != Thread.currentThread()) {
//			System.out.println(
//					"unlock " + this.hashCode() + ":" +
//					" Hilo actual = " + hiloActual.hashCode() +
//					" Contador = " + contadorActual);
//			System.out.println(
//					"unlock " + this.hashCode() + ":" +
//					" Thread.currentThread() = "
//					+ Thread.currentThread().hashCode());
            throw new IllegalMonitorStateException();
        }
        contadorActual--;
//		System.out.println(
//				"unlock " + this.hashCode() + ":" +
//				" Hilo actual = " + hiloActual.hashCode() +
//				" Contador = " + contadorActual);
        if (contadorActual == 0) {
            hiloActual = null;
//			System.out.println(
//					"unlock " + this.hashCode() + ":" +
//					" Hilo actual = " + null +
//					" Contador = " + contadorActual);
            exclusionEntrada.acquireUninterruptibly();
            if (enInterrumpidos > 0) {
                Semaphore siguienteInterrumpidos
                        = this.siguienteInterrumpidos();
                siguienteInterrumpidos.release();
            } else if (enUrgente > 0) {
                Semaphore siguienteUrgente = colaUrgente.remove(0);
                siguienteUrgente.release();
            } else if (enEntrada > 0) {
                Semaphore siguienteEntrada = this.siguienteEntrada();
                ultimoLiberadoEntrada = siguienteEntrada;
                siguienteEntrada.release();
            } else {
                libre = true;
            }
            exclusionEntrada.release();
        }
    }

    public Condition newCondition() {
        return new ConditionObject();
    }

    protected void postLock() {
        exclusionEntrada.acquireUninterruptibly();
        enEntrada--;
        ultimoLiberadoEntrada = null;
        exclusionEntrada.release();
    }

    protected void interrumpidoLock(Semaphore siguienteEntrada) {
        exclusionEntrada.acquireUninterruptibly();
        enEntrada--;
        if (ultimoLiberadoEntrada == siguienteEntrada) {
            if (enEntrada > 0) {
                Semaphore otraEntrada = this.siguienteEntrada();
                ultimoLiberadoEntrada = otraEntrada;
                otraEntrada.release();
            }
        } else {
            colaEntrada.remove(siguienteEntrada);
        }
        exclusionEntrada.release();
    }

    protected Semaphore siguienteEntrada() {
        int aLiberar;
        if (!justicia) {
            aLiberar = (int) (enEntrada * Math.random());
        } else {
            aLiberar = 0;
        }
        return colaEntrada.remove(aLiberar);
    }

    protected Semaphore siguienteInterrumpidos() {
        int aLiberar;
        if (!justicia) {
            aLiberar = (int) (enInterrumpidos * Math.random());
        } else {
            aLiberar = 0;
        }
        return colaInterrumpidos.remove(aLiberar);
    }

    protected class ConditionObject implements Condition {

        private List<Semaphore> colaCondicion;
        private int enCondicion;
        private Semaphore exclusionInterrumpidos;
        private Semaphore ultimoLiberado;

        ConditionObject() {
            colaCondicion = new LinkedList<Semaphore>();
            enCondicion = 0;
            exclusionInterrumpidos = new Semaphore(1, true);
            ultimoLiberado = null;
        }

        protected Semaphore preAwait() {
            if (hiloActual != Thread.currentThread()) {
//				System.out.println(
//						"awaitUninterruptibly " +
//						UrgentQueueReentrantLock.this.hashCode() + ":" +
//						" Hilo actual = " + hiloActual.hashCode() +
//						" Contador = " + contadorActual);
//				System.out.println(
//						"awaitUninterruptibly " +
//						UrgentQueueReentrantLock.this.hashCode() + ":" +
//						" Thread.currentThread() = "
//						+ Thread.currentThread().hashCode());
                throw new IllegalMonitorStateException();
            }
            Semaphore siguienteCondicion = new Semaphore(0);
            exclusionInterrumpidos.acquireUninterruptibly();
            colaCondicion.add(siguienteCondicion);
            enCondicion++;
            exclusionInterrumpidos.release();
            listaBloqueados.add(new NodoHilo(hiloActual, contadorActual));
//			System.out.println(
//					"awaitUninterruptibly " +
//					UrgentQueueReentrantLock.this.hashCode() + ":" +
//					" Hilo actual = " + hiloActual.hashCode() +
//					" Contador = " + contadorActual);
            hiloActual = null;
            contadorActual = 0;
//			System.out.println(
//					"awaitUninterruptibly " +
//					UrgentQueueReentrantLock.this.hashCode() + ":" +
//					" Hilo actual = " + null +
//					" Contador = " + contadorActual);
            exclusionEntrada.acquireUninterruptibly();
            if (enInterrumpidos > 0) {
                Semaphore siguienteInterrumpidos
                        = UrgentQueueReentrantLock.this.siguienteInterrumpidos();
                siguienteInterrumpidos.release();
            } else if (enUrgente > 0) {
                Semaphore siguienteUrgente = colaUrgente.remove(0);
                siguienteUrgente.release();
            } else if (enEntrada > 0) {
                Semaphore siguienteEntrada
                        = UrgentQueueReentrantLock.this.siguienteEntrada();
                UrgentQueueReentrantLock.this.ultimoLiberadoEntrada
                        = siguienteEntrada;
                siguienteEntrada.release();
            } else {
                libre = true;
            }
            exclusionEntrada.release();
            return siguienteCondicion;
        }

        protected void postAwait() {
            exclusionInterrumpidos.acquireUninterruptibly();
            enCondicion--;
            ultimoLiberado = null;
            exclusionInterrumpidos.release();
            this.hiloReanudado();
        }

//		protected void interrumpidoAwait(Semaphore siguienteCondicion) {
//			exclusionInterrumpidos.acquireUninterruptibly();
//			enCondicion--;
//			if (ultimoLiberado == siguienteCondicion) {
//				ultimoLiberado = null;
//				exclusionInterrumpidos.release();
//			} else {
//				colaCondicion.remove(siguienteCondicion);
//				exclusionInterrumpidos.release();
//				exclusion.acquireUninterruptibly();
//				if (!libre) {
//					Semaphore siguienteInterrumpidos = new Semaphore(0);
//					colaInterrumpidos.add(siguienteInterrumpidos);
//					enInterrumpidos++;
//					exclusion.release();
//					siguienteInterrumpidos.acquireUninterruptibly();
//				} else {
//					libre = false;
//					exclusion.release();
//				}
//			}
//			this.hiloReanudado();
//		}
        protected void interrumpidoAwait(Semaphore siguienteCondicion) {
            exclusionInterrumpidos.acquireUninterruptibly();
            enCondicion--;
            if (ultimoLiberado == siguienteCondicion) {
                if (enCondicion > 0) {
                    Semaphore otraCondicion = this.siguienteCondicion();
                    ultimoLiberado = otraCondicion;
                    otraCondicion.release();
                } else {
                    ultimoLiberado = null;
                    Semaphore siguienteUrgente = colaUrgente.remove(
                            colaUrgente.size() - 1);
                    siguienteUrgente.release();
                }
            } else {
                colaCondicion.remove(siguienteCondicion);
            }
            exclusionInterrumpidos.release();
            exclusionEntrada.acquireUninterruptibly();
            if (!libre) {
                Semaphore siguienteInterrumpidos = new Semaphore(0);
                colaInterrumpidos.add(siguienteInterrumpidos);
                enInterrumpidos++;
                exclusionEntrada.release();
                siguienteInterrumpidos.acquireUninterruptibly();
            } else {
                libre = false;
                exclusionEntrada.release();
            }
            this.hiloReanudado();
        }

        public void await() throws InterruptedException {
            Semaphore siguienteCondicion = this.preAwait();
            try {
                siguienteCondicion.acquire();
            } catch (InterruptedException ex) {
                this.interrumpidoAwait(siguienteCondicion);
                throw ex;
            }
            this.postAwait();
        }

        public void awaitUninterruptibly() {
            Semaphore siguienteCondicion = this.preAwait();
            siguienteCondicion.acquireUninterruptibly();
            this.postAwait();
        }

        public long awaitNanos(long nanosTimeout) throws InterruptedException {
            long resultado = 0;
            Semaphore siguienteCondicion = this.preAwait();
            try {
                long inicio = System.nanoTime();
                boolean exito = siguienteCondicion.tryAcquire(nanosTimeout,
                        TimeUnit.NANOSECONDS);
                if (!exito) {
                    resultado = inicio + nanosTimeout - System.nanoTime();
                    if (resultado <= 0) {
                        resultado = 1;
                    }
                    this.interrumpidoAwait(siguienteCondicion);
                    return resultado;
                }
            } catch (InterruptedException ex) {
                this.interrumpidoAwait(siguienteCondicion);
                throw ex;
            }
            this.postAwait();
            return resultado;
        }

        public boolean await(long time, TimeUnit unit)
                throws InterruptedException {
            boolean resultado = false;
            Semaphore siguienteCondicion = this.preAwait();
            try {
                resultado = siguienteCondicion.tryAcquire(time, unit);
                if (!resultado) {
                    this.interrumpidoAwait(siguienteCondicion);
                    return resultado;
                }
            } catch (InterruptedException ex) {
                this.interrumpidoAwait(siguienteCondicion);
                throw ex;
            }
            this.postAwait();
            return resultado;
        }

        public boolean awaitUntil(Date deadline) throws InterruptedException {
            boolean resultado = false;
            Semaphore siguienteCondicion = this.preAwait();
            try {
                long inicio = System.currentTimeMillis();
                long fin = deadline.getTime();
                resultado = siguienteCondicion.tryAcquire(fin - inicio,
                        TimeUnit.MILLISECONDS);
                if (!resultado) {
                    this.interrumpidoAwait(siguienteCondicion);
                    return resultado;
                }
            } catch (InterruptedException ex) {
                this.interrumpidoAwait(siguienteCondicion);
                throw ex;
            }
            this.postAwait();
            return resultado;
        }

        public void signal() {
            if (hiloActual != Thread.currentThread()) {
//				System.out.println(
//						"signal " +
//						UrgentQueueReentrantLock.this.hashCode() + ":" +
//						" Hilo actual = " + null +
//						" Contador = " + contadorActual);
//				System.out.println(
//						"signal " +
//						UrgentQueueReentrantLock.this.hashCode() + ":" +
//						" Thread.currentThread() = "
//						+ Thread.currentThread().hashCode());
                throw new IllegalMonitorStateException();
            }
            Semaphore siguienteUrgente = this.preSignal();
            if (siguienteUrgente != null) {
                siguienteUrgente.acquireUninterruptibly();
                this.postSignal();
            }
        }

        public void signalAll() {
            if (hiloActual != Thread.currentThread()) {
//				System.out.println(
//						"signalAll " +
//						UrgentQueueReentrantLock.this.hashCode() + ":" +
//						" Hilo actual = " + null +
//						" Contador = " + contadorActual);
//				System.out.println(
//						"signalAll " +
//						UrgentQueueReentrantLock.this.hashCode() + ":" +
//						" Thread.currentThread() = "
//						+ Thread.currentThread().hashCode());
                throw new IllegalMonitorStateException();
            }
            Semaphore siguienteUrgente;
            while ((siguienteUrgente = this.preSignal()) != null) {
                siguienteUrgente.acquireUninterruptibly();
                this.postSignal();
            }
        }

        protected Semaphore preSignal() {
            Semaphore siguienteUrgente = null;
            exclusionInterrumpidos.acquireUninterruptibly();
            if (enCondicion > 0) {
                Semaphore siguienteCondicion = this.siguienteCondicion();
                ultimoLiberado = siguienteCondicion;
                siguienteUrgente = new Semaphore(0);
                colaUrgente.add(siguienteUrgente);
                listaBloqueados.add(new NodoHilo(hiloActual, contadorActual));
                enUrgente++;
                hiloActual = null;
                contadorActual = 0;

                siguienteCondicion.release();
            }
            exclusionInterrumpidos.release();
            return siguienteUrgente;
        }

        protected void postSignal() {
            enUrgente--;
            this.hiloReanudado();
        }

        protected void hiloReanudado() {
            Thread hilo = Thread.currentThread();
            Iterator<NodoHilo> iterador = listaBloqueados.iterator();
            boolean encontrado = false;
            while (iterador.hasNext()) {
                NodoHilo nodoHilo = iterador.next();
                if (nodoHilo.hilo == hilo) {
                    encontrado = true;
                    hiloActual = nodoHilo.hilo;
                    contadorActual = nodoHilo.contador;
                    iterador.remove();
                    break;
                }
            }
            if (!encontrado) {
//				System.out.println(
//						"hiloReanudado " +
//						UrgentQueueReentrantLock.this.hashCode() + ":" +
//						" Hilo " + hilo.hashCode() +
//						" no encontrado");
                throw new IllegalMonitorStateException();
            }
//			System.out.println("----------------------------");
//			System.out.println(
//					"hiloReanudado " +
//					UrgentQueueReentrantLock.this.hashCode() + ":" +
//					" Hilo actual = " + hiloActual.hashCode() +
//					" Contador = " + contadorActual);
//			System.out.println("Lista hilos =");
//			iterador = listaBloqueados.iterator();
//			while (iterador.hasNext()) {
//				NodoHilo nodoHilo = iterador.next();
//				System.out.println("Hilo = " +
//					nodoHilo.hilo.hashCode());
//			}
//			System.out.println("----------------------------");
        }

        protected Semaphore siguienteCondicion() {
            int aLiberar;
            if (!justicia) {
                aLiberar = (int) (enCondicion * Math.random());
            } else {
                aLiberar = 0;
            }
            return colaCondicion.remove(aLiberar);
        }
    }
}
