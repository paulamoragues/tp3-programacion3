package algoritmo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random; // Necesario para Collections.shuffle

import generador.Generador;
import logica.Camino;
import logica.Celda;
import logica.Grilla;

public class Individuo implements Comparable<Individuo> {
	// Representamos a un individuo como una secuencia de movimientos:
	// 0 = mover hacia abajo
	// 1 = mover hacia la derecha 
	private boolean[] _movimientos; // true para '1' (derecha), false para '0' (abajo)

	// La grilla asociada
	private Grilla _grilla;

	// Generador de valores aleatorios
	private static Generador _random;

	public static void setGenerador(Generador generador) {
		_random = generador;
	}

	public static Individuo aleatorio(Grilla grilla) {
		Individuo ret = new Individuo(grilla);

		int movimientosAbajo = grilla.getFilas() - 1;
		int movimientosDerecha = grilla.getColumnas() - 1;
		int totalMovimientos = movimientosAbajo + movimientosDerecha;

		// Crear una lista de booleanos con la cantidad correcta de '0' (abajo) y '1' (derecha)
		List<Boolean> listaMovimientos = new ArrayList<>();
		for (int i = 0; i < movimientosAbajo; i++) {
			listaMovimientos.add(false); // '0' para abajo
		}
		for (int i = 0; i < movimientosDerecha; i++) {
			listaMovimientos.add(true); // '1' para derecha
		}

		// Barajar la lista para obtener una secuencia aleatoria de movimientos válidos
		Collections.shuffle(listaMovimientos, new Random()); // Usa java.util.Random para el shuffle

		// Convertir la lista barajada de nuevo a un array booleano
		for (int i = 0; i < totalMovimientos; i++) {
			ret._movimientos[i] = listaMovimientos.get(i);
		}

		return ret;
	}

	private Individuo(Grilla grilla) {
		_grilla = grilla;
		int totalMovimientos = grilla.getFilas() - 1 + grilla.getColumnas() - 1;
		_movimientos = new boolean[totalMovimientos];
	}

	public void mutar() {
		if (_movimientos.length == 0) { // Grilla 1x1, no hay movimientos
			return;
		}

		// Implementación de mutación por intercambio para asegurar que el camino siga siendo válido
		// (manteniendo el número de movimientos 'abajo' y 'derecha').
		if (_movimientos.length >= 2) {
			int cambio1 = _random.nextInt(_movimientos.length);
			int cambio2 = _random.nextInt(_movimientos.length);

			// Asegurarse de que no sean el mismo índice
			while (cambio1 == cambio2) {
				cambio2 = _random.nextInt(_movimientos.length);
			}

			// Intercambiar los movimientos
			boolean aux = _movimientos[cambio1];
			_movimientos[cambio1] = _movimientos[cambio2];
			_movimientos[cambio2] = aux;
		} else if (_movimientos.length == 1) {
			// En una grilla de 1x2 o 2x1, solo hay un movimiento. Una mutación simple es una inversión.
			_movimientos[0] = !_movimientos[0];
		}
	}

	public Individuo[] recombinar(Individuo other) {
		if (_movimientos.length == 0) { // Grilla 1x1
			return new Individuo[] {new Individuo(_grilla), new Individuo(_grilla)};
		}

		// Se elige un punto de cruce aleatorio
		int k = _random.nextInt(_movimientos.length);

		Individuo hijo1 = new Individuo(_grilla);
		Individuo hijo2 = new Individuo(_grilla);

		// Copiar la primera parte de los movimientos
		for (int i = 0; i < k; i++) {
			hijo1._movimientos[i] = this._movimientos[i];
			hijo2._movimientos[i] = other._movimientos[i];
		}
		// Copiar la segunda parte de los movimientos (intercambiada)
		for (int i = k; i < _movimientos.length; i++) {
			hijo1._movimientos[i] = other._movimientos[i];
			hijo2._movimientos[i] = this._movimientos[i];
		}

		// Como se mencionó antes, el cruce de un punto puede generar hijos que no tienen el número correcto
		// de movimientos 'abajo' y 'derecha'. El fitness se encargará de penalizar esto.

		return new Individuo[] {hijo1, hijo2};
	}

	@Override
	public int compareTo(Individuo other) {
		return Integer.compare(this.fitness(), other.fitness());
	}

	// Calcula la aptitud del individuo. Un fitness de 0 es una solución óptima.
	public int fitness() {
		Camino camino = this.generarCamino();
		// Un camino válido siempre debe tener la longitud correcta para la grilla.
		int longitudEsperada = _grilla.getFilas() + _grilla.getColumnas() - 1;
		if (camino.getTamaño() != longitudEsperada) {
			// Penalización muy alta si el camino no tiene la longitud correcta
			return Integer.MAX_VALUE / 2;
		}
		int suma = 0;
		for (Celda celda : camino.getCeldas()) {
			suma += (celda.getCarga() ? 1 : -1);
		}
		return Math.abs(suma);
	}

	// Método para generar el Camino real a partir de los movimientos del individuo
	public Camino generarCamino() {
		Camino camino = new Camino();
		int filaActual = 0;
		int columnaActual = 0;

		// Asegurarse de que la celda inicial exista y agregarla
		if (filaActual < _grilla.getFilas() && columnaActual < _grilla.getColumnas()) {
			camino.agregarCelda(_grilla.getCelda(filaActual, columnaActual));
		} else {
			return camino;
		}

		for (boolean movimiento : _movimientos) {
			if (movimiento) { // true = '1' = derecha
				columnaActual++;
			} else { // false = '0' = abajo
				filaActual++;
			}

			// Verificar límites de la grilla antes de agregar la celda
			if (filaActual < _grilla.getFilas() && columnaActual < _grilla.getColumnas()) {
				camino.agregarCelda(_grilla.getCelda(filaActual, columnaActual));
			} else {
				// Si el camino se sale de los límites, detenemos la construcción.
				// El fitness se encargará de penalizar este individuo por no tener la longitud correcta.
				break;
			}
		}
		return camino;
	}
	
}
