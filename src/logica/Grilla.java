package logica;

public class Grilla {
	private Celda[][] matriz;
	private int filas;
	private int columnas;

	public Grilla(boolean[][] cargas) {
		this.filas = cargas.length;
		this.columnas = cargas[0].length;
		this.matriz = new Celda[filas][columnas];

		for (int i = 0; i < filas; i++) {
			for (int j = 0; j < columnas; j++) {
				matriz[i][j] = new Celda(i, j, cargas[i][j]);
			}
		}
	}

	public Celda getCelda(int fila, int columna) {
		return matriz[fila][columna];
	}

	public int getFilas() {
		return filas;
	}

	public int getColumnas() {
		return columnas;
	}
	
}