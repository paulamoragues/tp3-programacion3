package logica;

import java.util.List;

import algoritmo.AlgoritmoRecursivo;

public class BackTracking extends AlgoritmoRecursivo {

	public BackTracking(Grilla grilla) {
		super(grilla);
	}

	@Override
	public List<Camino> buscarCaminos() {
		long tiempoInicial = System.nanoTime();

		reiniciarValores();

		if ((filas + columnas - 1) % 2 != 0) {
			return caminosValidos;
		}

		Celda inicio = grilla.getCelda(0, 0);
		caminoActual.agregarCelda(inicio);
		buscar(0, 0, inicio.getCargaEntero());

		long tiempoFinal = System.nanoTime();
		tiempoEjecucion = (tiempoFinal - tiempoInicial) / 1_000_000.0; // En milisegundos

		return caminosValidos;
	}

	private void buscar(int fila, int columna, int suma) {
		llamadas++;

		int pasosRestantes = (filas - fila - 1) + (columnas - columna - 1);
		if (Math.abs(suma) > pasosRestantes)
			return;

		if (llegoAlDestino(fila, columna)) {
			if (suma == 0)
				caminosValidos.add(new Camino(caminoActual));
			return;
		}

		// Abajo
		if (fila + 1 < filas) {
			Celda abajo = grilla.getCelda(fila + 1, columna);

			caminoActual.agregarCelda(abajo);
			buscar(fila + 1, columna, suma + abajo.getCargaEntero());
			caminoActual.eliminarCelda(caminoActual.getTamaño() - 1);
		}

		// Derecha
		if (columna + 1 < columnas) {
			Celda derecha = grilla.getCelda(fila, columna + 1);

			caminoActual.agregarCelda(derecha);
			buscar(fila, columna + 1, suma + derecha.getCargaEntero());
			caminoActual.eliminarCelda(caminoActual.getTamaño() - 1);
		}
	}
}
