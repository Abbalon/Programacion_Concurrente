package pc.ejemplos5iii.lectescr.rwlocks.parametrizado.solucion;

interface Datos<Tipo> {
	Tipo leer();
	void escribir(Tipo datos);
}
