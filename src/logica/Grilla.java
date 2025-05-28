package logica;

import java.util.Random;

public class Grilla {
	private Celda[][] matriz;
	private int filas;
	private int columnas;

	public Grilla() {}

	public Grilla(int filas, int columnas) {
		this.filas = filas;
		this.columnas = columnas;
		this.matriz = new Celda[filas][columnas];
	}

	public void generarGrillaAleatoria() {
		Random rand = new Random();
		for (int fila = 0; fila < filas; fila++) {
			for (int col = 0; col < columnas; col++) {
				boolean carga = rand.nextBoolean();
				matriz[fila][col] = new Celda(fila, col, carga);
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
