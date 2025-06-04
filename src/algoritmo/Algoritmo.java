package algoritmo;

import java.util.ArrayList;
import java.util.List;

import logica.Camino;
import logica.Grilla;

public abstract class Algoritmo {
	protected List<Camino> caminosValidos;
	protected Grilla grilla;
	protected int filas;
	protected int columnas;

	// Estadísticas
	protected double tiempoEjecucion;

	public Algoritmo(Grilla grilla) {
		this.caminosValidos = new ArrayList<>();
		this.grilla = grilla;
		this.filas = grilla.getFilas();
		this.columnas = grilla.getColumnas();
		this.tiempoEjecucion = 0;
	}

	// Cada subclase define su propio algoritmo de búsqueda
	public abstract List<Camino> buscarCaminos();

	public double getTiempoEjecucion() {
		return tiempoEjecucion;
	}

	public int getCantidadCaminos() {
		return caminosValidos.size();
	}

	// obtener un camino para dibujar por pantalla desde acá, en vez de por interfaz
	public Camino getCamino(int i) {
		return caminosValidos.get(i);
	}

}
