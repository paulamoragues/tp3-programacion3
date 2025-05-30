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
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Camino otro = (Camino) obj;
		
		if (this.celdas.size() != otro.celdas.size()) {
			return false;
		}
		for (int i = 0; i < this.celdas.size(); i++) {
			Celda c1 = this.celdas.get(i);
			Celda c2 = otro.celdas.get(i);
		
			if (!c1.equals(c2)) { 
				return false;
			}
		}
		return true;
	}
	
	@Override
    public int hashCode() {
        return Objects.hash(celdas); // Genera el hashcode basado en la lista de celdas
    }
	public boolean esCaminoValido(Grilla grilla) {
		if (celdas.isEmpty()) {
			return false;
		}

		Celda ultimaCelda = celdas.get(getTamaño() - 1);
		
		// 1. Verificar si llega al destino
		boolean llegoAlDestino = (ultimaCelda.getFila() == grilla.getFilas() - 1) &&
								 (ultimaCelda.getColumna() == grilla.getColumnas() - 1);
		
		// 2. Verificar la suma de cargas
		boolean sumaCorrecta = (calcularSumaCargas() == 0);
		
		return llegoAlDestino && sumaCorrecta;
	}

	public int calcularSumaCargas() {
		int suma = 0;
		for (Celda celda : celdas) {
			suma += (celda.getCarga() ? 1 : -1);
		}
		return suma;
	}
}
