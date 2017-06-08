package pc.ejemplos4iii.lectescr.parametrizado;

interface Datos<Tipo> {
	Tipo leer();
	void escribir(Tipo datos);
}
