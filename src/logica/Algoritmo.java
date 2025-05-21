package logica;

import java.util.ArrayList;
import java.util.List;

public abstract class Algoritmo {
	protected List<Camino> caminosValidos;
	protected Camino caminoActual;
	
	protected Grilla grilla;
	protected int filas;
	protected int columnas;
	
	// Estadisticas
	protected int llamadas;
	// tiempo de ejecucion se maneja acá
	// ...
	
	public Algoritmo(Grilla grilla) {
		this.grilla = grilla;
		this.filas = grilla.getFilas();
		this.columnas = grilla.getColumnas();
		this.caminoActual = new Camino();
		this.caminosValidos = new ArrayList<>();
		this.llamadas = 0;
	}

	public int getCantidadLlamadas() {
		return llamadas;
	}

	public int getCantidadCaminos() {
		return caminosValidos.size();
	}

	// Cada subclase define su propio algoritmo de búsqueda
	public abstract List<Camino> buscarCaminos();
	
	protected int cargaComoEntero(Celda celda) {
		return celda.getCarga() ? 1 : -1;
	}

	protected boolean llegoAlDestino(int fila, int columna) {
		return fila == filas - 1 && columna == columnas - 1;
	}
}