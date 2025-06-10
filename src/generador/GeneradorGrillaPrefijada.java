package generador;

public class GeneradorGrillaPrefijada implements GeneradorGrilla {
	private int filas;
	private int columnas;
	private boolean[][] valores;

	public GeneradorGrillaPrefijada(boolean[][] valores, int filas, int columnas) {
		this.filas = filas;
		this.columnas = columnas;

		verificarCargasValida(valores);
		this.valores = valores;
	}

	@Override
	public boolean nextBoolean(int fila, int columna) {
		return valores[fila][columna];
	}

	@Override
	public int nextIntFilas() {
		return filas;
	}

	@Override
	public int nextIntColumnas() {
		return columnas;
	}

	private void verificarCargasValida(boolean[][] cargas) {
		if (cargas == null)
			throw new NullPointerException("Las cargas no pueden estar vacias");

		if (cargas.length != filas)
			throw new IllegalArgumentException("Cantidad de filas no coincide.");

		for (int i = 0; i < filas; i++)
			if (cargas[i].length != columnas)
				throw new IllegalArgumentException("La fila " + i + " no tiene la cantidad de columnas esperadas.");
	}

}
