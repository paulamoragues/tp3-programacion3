package generador;

public class GeneradorGrillaPrefijada implements GeneradorGrilla {
	private int _filas;
	private int _columnas;
	private boolean[][] _valores;
	
	public GeneradorGrillaPrefijada(boolean [][] valores , int filas, int columnas) {
		
		_filas = filas;
		_columnas = columnas;
		_valores = valores;
		verificarCargasValida(valores);
	}
	@Override
	public boolean nextBoolean(int fila, int columna) {
		
		return _valores [fila][columna];
	}
	@Override
	public int nextIntFilas() {
		
		return _filas;
	}
	@Override
	public int nextIntColumnas() {
	
		return _columnas;
	}

	private void verificarCargasValida(boolean[][] cargas) {
		if (cargas == null) {
			throw new NullPointerException("Las cargas no pueden estar vacias");
		}
		
		if (cargas.length != _filas) 
			throw new IllegalArgumentException("Cantidad de filas no coincide.");
		
		for (int i = 0; i < _filas; i++) {
			if (cargas[i].length != _columnas) 
				throw new IllegalArgumentException("La fila " + i + " no tiene la cantidad de columnas esperadas.");
		}
	}

}
