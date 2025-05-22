package logica;

import java.util.List;

public class BackTracking extends Algoritmo {

	public BackTracking(Grilla grilla) {
		super(grilla);
	}

	@Override
	public List<Camino> buscarCaminos() {
		long tiempoInicial = System.nanoTime();
		
		caminosValidos.clear();
		llamadas = 0;
		caminoActual = new Camino();
		if ((filas + columnas - 1) % 2 != 0) {
			return caminosValidos;
		}

		Celda inicio = grilla.getCelda(0, 0);
		caminoActual.agregarCelda(inicio);
		buscar(0, 0, cargaComoEntero(inicio));
		
		long tiempoFinal = System.nanoTime();
		tiempoEjecucion = (tiempoFinal - tiempoInicial) / 1_000_000.0; // en milisegundos

		return caminosValidos;
	}

	private void buscar(int fila, int columna, int suma) {
		llamadas++;

		int restantes = (filas - fila - 1) + (columnas - columna - 1);
		if (suma + restantes < 0 || suma - restantes > 0) {
			return;
		}

		if (llegoAlDestino(fila, columna)) {
			if (suma == 0) {
				caminosValidos.add(new Camino(caminoActual));
			}
			return;
		}
		
		// Abajo
		if (fila + 1 < filas) {
			Celda abajo = grilla.getCelda(fila + 1, columna);
			caminoActual.agregarCelda(abajo);
			buscar(fila + 1, columna, suma + cargaComoEntero(abajo));
			caminoActual.eliminarCelda(caminoActual.getTamaño() - 1);
		}
		
		// Derecha
		if (columna + 1 < columnas) {
			Celda derecha = grilla.getCelda(fila, columna + 1);
			caminoActual.agregarCelda(derecha);
			buscar(fila, columna + 1, suma + cargaComoEntero(derecha));
			caminoActual.eliminarCelda(caminoActual.getTamaño() - 1);
		}
	}
}
