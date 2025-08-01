package generador;

public class GeneradorGeneticoPrefijado implements GeneradorGenetico {
	private boolean[] bits;
	private int indice;

	private int posicionMutar1;
	private int posicionMutar2;
	private int posicion;

	public GeneradorGeneticoPrefijado(String str) {
		bits = new boolean[str.length()];
		for (int i = 0; i < str.length(); i++)
			bits[i] = str.charAt(i) == '1';
		indice = 0;
	}

	public GeneradorGeneticoPrefijado(int entero1, int entero2) {
		this.posicionMutar1 = entero1;
		this.posicionMutar2 = entero2;
	}

	public GeneradorGeneticoPrefijado(int entero) {
		this.posicion = entero;
	}

	@Override
	public boolean nextBoolean() {
		return bits[indice++];
	}

	@Override
	public int nextIntMutar1(int rango) {
		return posicionMutar1;
	}

	@Override
	public int nextIntMutar2(int rango) {
		return posicionMutar2;
	}

	@Override
	public int nextIntPosicion(int rango) {
		return posicion;
	}

}
