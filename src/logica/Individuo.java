package logica;

import generador.Generador;

public class Individuo implements Comparable<Individuo> {
	// Representamos a un individuo como una secuencia de movimientos:
	// 0 = mover hacia abajo
	// 1 = mover hacia la derecha 
	private boolean[] movimientos; // true para '1' (derecha), false para '0' (abajo)

	// La grilla asociada
	private Grilla grilla;

	// Generador de valores aleatorios
	private static Generador random;

	public static void setGenerador(Generador generador) {
		random = generador;
	}

	


	public static Individuo aleatorio(Grilla grilla) {
		Individuo ret = new Individuo(grilla);

		int movimientosAbajo = grilla.getFilas() - 1;
		int movimientosDerecha = grilla.getColumnas() - 1;
		int totalMovimientos = movimientosAbajo + movimientosDerecha;

		for (int i = 0; i < totalMovimientos; i++) {
			ret.set(i, random.nextBoolean());
		}
	
		return ret;
	}




	private Individuo(Grilla grilla) {
		this.grilla = grilla;
		int totalMovimientos = grilla.getFilas() - 1 + grilla.getColumnas() - 1;
		movimientos = new boolean[totalMovimientos];
	}

	public void mutar() {
		// El bit que se muta se decide aleatoriamente
		int k = random.nextIntMutar1(movimientos.length);
		int j = random.nextIntMutar2(movimientos.length);
		set(k, !get(k));
		set(j, !get(j));
	}


	public Individuo[] recombinar(Individuo other) {
		if (movimientos.length == 0) { // Grilla 1x1
			return new Individuo[] {new Individuo(grilla), new Individuo(grilla)};
		}

		// Se elige un punto de cruce 
		int k = random.nextInt(movimientos.length);

		Individuo hijo1 = new Individuo(grilla);
		Individuo hijo2 = new Individuo(grilla);

		// Copiar la primera parte de los movimientos
		for (int i = 0; i < k; i++) {
			hijo1.movimientos[i] = this.movimientos[i];
			hijo2.movimientos[i] = other.movimientos[i];
		}
		// Copiar la segunda parte de los movimientos (intercambiada)
		for (int i = k; i < movimientos.length; i++) {
			hijo1.movimientos[i] = other.movimientos[i];
			hijo2.movimientos[i] = this.movimientos[i];
		}

		// Como se mencionó antes, el cruce de un punto puede generar hijos que no tienen el número correcto
		// de movimientos 'abajo' y 'derecha'. El fitness se encargará de penalizar esto.

		return new Individuo[] {hijo1, hijo2};
	}


	// Calcula la aptitud del individuo. Un fitness de 0 es una solución óptima.
	public int fitness() {
		Camino camino = this.generarCamino();
		// Un camino válido siempre debe tener la longitud correcta para la grilla.
		int longitudEsperada = grilla.getFilas() + grilla.getColumnas() - 1;
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
		if (filaActual < grilla.getFilas() && columnaActual < grilla.getColumnas()) {
			camino.agregarCelda(grilla.getCelda(filaActual, columnaActual));
		} else {
			return camino;
		}

		for (boolean movimiento : movimientos) {
			if (movimiento) { // true = '1' = derecha
				columnaActual++;
			} else { // false = '0' = abajo
				filaActual++;
			}

			// Verificar límites de la grilla antes de agregar la celda
			if (filaActual < grilla.getFilas() && columnaActual < grilla.getColumnas()) {
				camino.agregarCelda(grilla.getCelda(filaActual, columnaActual));
			} else {
				// Si el camino se sale de los límites, detenemos la construcción.
				// El fitness se encargará de penalizar este individuo por no tener la longitud correcta.
				break;
			}
		}
		return camino;
	}

	public boolean get(int i) {
		return movimientos[i];
	}
	private void set(int i, boolean valor) {
		movimientos[i] = valor;
	
}

	@Override
	public int compareTo(Individuo other) {
		return Integer.compare(this.fitness(), other.fitness());
	}
	
}
