package logica;

import java.util.ArrayList;
import java.util.List;

public abstract class Algoritmo {
	protected List<Camino> caminosValidos;
	protected Camino caminoActual;
	protected Grilla grilla;
	protected int filas;
	protected int columnas;

	// Estadísticas
	protected int llamadas;
	protected double tiempoEjecucion;

	public Algoritmo(Grilla grilla) {
		this.caminosValidos = new ArrayList<>();
		this.caminoActual = new Camino();
		this.grilla = grilla;
		this.filas = grilla.getFilas();
		this.columnas = grilla.getColumnas();
		this.llamadas = 0;
		this.tiempoEjecucion = 0;
	}
	
	// Cada subclase define su propio algoritmo de búsqueda
	public abstract List<Camino> buscarCaminos();

	public int getCantidadLlamadas() {
		return llamadas;
	}

	public double getTiempoEjecucion() {
		return tiempoEjecucion;
	}
	
	public int getCantidadCaminos() {
		return caminosValidos.size();
	}
	
	public Camino getCamino(int i) {
		return caminosValidos.get(i);
	}
	
	protected void reiniciarValores() {
		caminosValidos.clear();
		llamadas = 0;
		caminoActual = new Camino();
	}

	protected int cargaComoEntero(Celda celda) {
		return celda.getCarga() ? 1 : -1;
	}

	protected boolean llegoAlDestino(int fila, int columna) {
		return fila == filas - 1 && columna == columnas - 1;
	}
	
}
