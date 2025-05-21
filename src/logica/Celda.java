package logica;

public class Celda {
	private int fila;
	private int columna;
	private boolean carga;

	public Celda(int fila, int columna, boolean carga) {
		this.fila = fila;
		this.columna = columna;
		this.carga = carga;
	}

	public int getFila() {
		return fila;
	}

	public int getColumna() {
		return columna;
	}

	public boolean getCarga() {
		return carga;
	}

}