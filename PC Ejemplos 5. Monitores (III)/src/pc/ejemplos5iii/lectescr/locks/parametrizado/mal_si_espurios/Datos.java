package pc.ejemplos5iii.lectescr.locks.parametrizado.mal_si_espurios;

interface Datos<Tipo> {
	Tipo leer();
	void escribir(Tipo datos);
}
