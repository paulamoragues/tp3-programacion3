package logica;

public class GeneradorPrefijado implements Generador {

	private boolean[] _bits;
	private int _indice;
	private int _entero;
	
	public GeneradorPrefijado(String str) {
		_bits = new boolean[str.length()];
		for (int i=0; i<str.length(); i++)
			_bits[i] = str.charAt(i)=='1';
		_indice = 0;
	}

	public GeneradorPrefijado(int entero) {
		_entero = entero;
	}

	@Override
	public boolean nextBoolean() {
		// TODO Auto-generated method stub
		return _bits[_indice++];
	}

	@Override
	public int nextInt(int rango) {
		// TODO Auto-generated method stub
		return _entero;
	}

}
