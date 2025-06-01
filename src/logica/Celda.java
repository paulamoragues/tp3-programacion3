package logica;

import java.util.Objects;

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

	public int getCargaEntero() {
		return carga ? 1 : -1;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Celda celda = (Celda) o;
		return fila == celda.fila && columna == celda.columna && carga == celda.carga;
	}

	@Override
	public int hashCode() {
		return Objects.hash(fila, columna, carga);
	}

}
