package algoritmo;

import logica.Camino;
import logica.Grilla;

public abstract class AlgoritmoRecursivo extends Algoritmo {
	protected Camino caminoActual;

	// Estadísticas
	protected int llamadas;

	public AlgoritmoRecursivo(Grilla grilla) {
		super(grilla);
		this.caminoActual = new Camino();
		this.llamadas = 0;
	}

	public int getCantidadLlamadas() {
		return llamadas;
	}

	protected void reiniciarValores() {
		caminosValidos.clear();
		caminoActual = new Camino();
		llamadas = 0;
	}

	protected boolean llegoAlDestino(int fila, int columna) {
		return fila == filas - 1 && columna == columnas - 1;
	}

}
