package logica;

import java.util.ArrayList;
import java.util.List;

public class Camino {
	private List<Celda> celdas;

	public Camino() {
		celdas = new ArrayList<>();
	}

	public Camino(Camino otro) {
		celdas = new ArrayList<>(otro.celdas); // Copia superficial
	}

	public void agregarCelda(Celda celda) {
		celdas.add(celda);
	}

	public void eliminarCelda(int i) {
		verificarRangoValido(i);
		celdas.remove(i);
	}

	public Celda getCelda(int i) {
		verificarRangoValido(i);
		return celdas.get(i);
	}

	public int getTamaño() {
		return celdas.size();
	}

	public List<Celda> getCeldas() {
		return new ArrayList<>(celdas); // Copia superficial
	}

	private void verificarRangoValido(int i) {
		if (i < 0 || i >= getTamaño()) {
			throw new IndexOutOfBoundsException("Índice fuera de rango: " + i);
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Camino camino = (Camino) o;
		// Dos caminos son iguales si tienen las mismas celdas en el mismo orden y
		// tamaño
		if (this.celdas.size() != camino.celdas.size()) {
			return false;
		}
		for (int i = 0; i < this.celdas.size(); i++) {
			Celda c1 = this.celdas.get(i);
			Celda c2 = camino.celdas.get(i);
			// Compara las celdas por su posición (fila, columna), no por la carga
			// directamente
			if (c1.getFila() != c2.getFila() || c1.getColumna() != c2.getColumna()) {
				return false;
			}
		}
		return true;
	}
}
