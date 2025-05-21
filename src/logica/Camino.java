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

	public int getTamaño() {
		return celdas.size();
	}

	public void eliminarCelda(int i) {
		celdas.remove(i);
	}

	public Celda getCelda(int i) {
		return celdas.get(i);
	}
}