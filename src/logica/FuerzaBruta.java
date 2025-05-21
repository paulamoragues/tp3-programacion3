package logica;

import java.util.ArrayList;
import java.util.List;

public class FuerzaBruta {

	private List<Camino> caminosValidosSinPoda;
	private Camino caminoActual;
	private Grilla grilla;
	private int filas;
	private int columnas;
	// Estadisticas
	private int llamadasSinPoda;

	public FuerzaBruta(Grilla grilla) {
		this.grilla = grilla;
		this.filas = grilla.getFilas();
		this.columnas = grilla.getColumnas();
		this.caminosValidosSinPoda = new ArrayList<>();
		this.caminoActual = new Camino();
		this.llamadasSinPoda = 0;
	}

	public List<Camino> buscarCaminosMinimosSinPoda() {
		// Limpiar resultados y reiniciar contador antes de cada búsqueda
		caminosValidosSinPoda.clear();
		llamadasSinPoda = 0;
		caminoActual = new Camino();

		Celda inicio = grilla.getCelda(0, 0);
		caminoActual.agregarCelda(inicio);

		buscarSinPoda(0, 0, cargaComoEntero(inicio));

		return caminosValidosSinPoda;
	}

	private void buscarSinPoda(int fila, int columna, int suma) {
		llamadasSinPoda++;

		// caso base
		if (llegoAlDestino(fila, columna)) {
			if (suma == 0) {
				caminosValidosSinPoda.add(new Camino(caminoActual));
			}
			return;
		}
		// Abajo
		if (fila + 1 < filas) {
			Celda abajo = grilla.getCelda(fila + 1, columna);

			caminoActual.agregarCelda(abajo);
			buscarSinPoda(fila + 1, columna, suma + cargaComoEntero(abajo));
			caminoActual.eliminarCelda(caminoActual.getTamaño() - 1);
		}
		// Derecha
		if (columna + 1 < columnas) {
			Celda derecha = grilla.getCelda(fila, columna + 1);

			caminoActual.agregarCelda(derecha);
			buscarSinPoda(fila, columna + 1, suma + cargaComoEntero(derecha));
			caminoActual.eliminarCelda(caminoActual.getTamaño() - 1);
		}
	}

	public boolean llegoAlDestino(int fila, int columna) {
		return fila == filas - 1 && columna == columnas - 1;
	}

	private int cargaComoEntero(Celda celda) {
		return celda.getCarga() ? 1 : -1;
	}

	// para ver en el main
	public int getLlamadasSinPoda() {
		return llamadasSinPoda;
	}

	public int getCantidadCaminosSinPoda() {
		return caminosValidosSinPoda.size();
	}

}
