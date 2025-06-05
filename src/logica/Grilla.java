package logica;

import generador.GeneradorGrilla;

public class Grilla {
	private Celda[][] matriz;
	private int filas;
	private int columnas;
	private static GeneradorGrilla random;

	public void setGenerador(GeneradorGrilla generador) {
		random = generador;  
	}

	public Grilla(GeneradorGrilla generador) {
		random = generador;
		this.filas = random.nextIntFilas();
		this.columnas = random.nextIntColumnas();
		this.matriz = new Celda[filas][columnas];
	}

	public void generarGrilla() {
		for (int fila = 0; fila < filas; fila++) {
			for (int col = 0; col < columnas; col++) {
				boolean carga = random.nextBoolean(fila, col);
				matriz[fila][col] = new Celda(fila, col, carga);
			}
		}  
	}

	public int getCargaCelda(int fila, int columna) {
		verificarFilaValida(fila);
		verificarColumnaValida(columna);
		return matriz[fila][columna].getCargaEntero(); 
	}

	public Celda getCelda(int fila, int columna) {
		verificarFilaValida(fila);
		verificarColumnaValida(columna);
		return matriz[fila][columna]; 
	}

	public int getFilas() {
		return filas;
	}

	public int getColumnas() {
		return columnas;
	}

	private void verificarFilaValida(int fila) {
		if (fila < 0 || fila >= filas) {
			throw new IndexOutOfBoundsException("Fila fuera de rango: " + fila);
		}
	}

	private void verificarColumnaValida(int columna) {
		if (columna < 0 || columna >= columnas) {
			throw new IndexOutOfBoundsException("Columna fuera de rango: " + columna);
		}
	}

}
