package generador;

public interface GeneradorGenetico {
	boolean nextBoolean();

	int nextIntPosicion(int rango);

	int nextIntMutar1(int rango);

	int nextIntMutar2(int rango);
}
