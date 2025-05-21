package logica;

import java.util.List;

public class BackTracking extends Algoritmo {

	public BackTracking(Grilla grilla) {
		super(grilla);
	}

	@Override
	public List<Camino> buscarCaminos() {
		caminosValidos.clear();
		llamadas = 0;
		caminoActual = new Camino();

		if ((filas + columnas - 1) % 2 != 0) {
			return caminosValidos;
		}

		Celda inicio = grilla.getCelda(0, 0);
		caminoActual.agregarCelda(inicio);

		buscarConPoda(0, 0, cargaComoEntero(inicio));

		return caminosValidos;
	}

	private void buscarConPoda(int fila, int columna, int suma) {
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
			buscarConPoda(fila + 1, columna, suma + cargaComoEntero(abajo));
			caminoActual.eliminarCelda(caminoActual.getTamaño() - 1);
		}
		
		// Derecha
		if (columna + 1 < columnas) {
			Celda derecha = grilla.getCelda(fila, columna + 1);
			caminoActual.agregarCelda(derecha);
			buscarConPoda(fila, columna + 1, suma + cargaComoEntero(derecha));
			caminoActual.eliminarCelda(caminoActual.getTamaño() - 1);
		}
	}
}
