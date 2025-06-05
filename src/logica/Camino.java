package logica;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Camino {
	private List<Celda> celdas;

	public Camino() {
		celdas = new ArrayList<>(); 
	}

	public Camino(Camino otro) {
		celdas = new ArrayList<>(otro.celdas);
	}

	public void agregarCelda(Celda celda) {
		celdas.add(celda);
	}

	public void eliminarCelda(int i) {
		verificarRangoValido(i);
		celdas.remove(i);
	}

	public int calcularSumaCeldas() {
		int suma = 0;
		for (Celda celda : celdas) {
			suma += (celda.getCargaEntero());
		}
		return suma;
	}

	public boolean esValido(int filas, int columnas) {
		if (celdas.isEmpty()) {
			return false;
		}
		Celda ultimaCelda = celdas.get(getTamaño() - 1);

		boolean llegoAlDestino = (ultimaCelda.getFila() == filas - 1) && 
				(ultimaCelda.getColumna() == columnas - 1);
		boolean sumaCorrecta = (calcularSumaCeldas() == 0);

		return llegoAlDestino && sumaCorrecta;
	}

	public Celda getCelda(int i) {
		verificarRangoValido(i);
		return celdas.get(i);
	}

	public int getTamaño() {
		return celdas.size();
	}

	private void verificarRangoValido(int i) {
		if (i < 0 || i >= getTamaño()) {
			throw new IndexOutOfBoundsException("Índice fuera de rango: " + i);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Camino))
			return false;
		Camino otro = (Camino) obj;
		return celdas.equals(otro.celdas);
	}

	@Override
	public int hashCode() {
		return Objects.hash(celdas);
	}

}
