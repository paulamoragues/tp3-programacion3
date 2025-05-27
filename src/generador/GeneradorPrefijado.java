package generador;

public class GeneradorPrefijado implements Generador {
	private boolean[] bits;
	private int indice;
	private int entero;

	public GeneradorPrefijado(String str) {
		bits = new boolean[str.length()];
		for (int i = 0; i < str.length(); i++)
			bits[i] = str.charAt(i) == '1';
		indice = 0;
	}

	public GeneradorPrefijado(int entero) {
		this.entero = entero;
	}

	@Override
	public boolean nextBoolean() {
		return bits[indice++];
	}

	@Override
	public int nextInt(int rango) {
		return entero;
	}

}
