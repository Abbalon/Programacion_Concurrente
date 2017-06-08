package pc.ejemplos5iii.lectescr.locks.parametrizado.solucion;

interface Datos<Tipo> {
	Tipo leer();
	void escribir(Tipo datos);
}
