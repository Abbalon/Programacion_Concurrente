package pc.ejemplos5iii.lectescr.locks.parametrizado.inanicion;

interface Datos<Tipo> {
	Tipo leer();
	void escribir(Tipo datos);
}
