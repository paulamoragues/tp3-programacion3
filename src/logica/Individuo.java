package logica;

import generador.GeneradorGenetico;

public class Individuo implements Comparable<Individuo> {

	// Representamos a un individuo como una secuencia de movimientos:
	// false = mover hacia abajo
	// true = mover hacia la derecha

	private boolean[] movimientos;

	private Grilla grilla;
	private static GeneradorGenetico random;

	public static void setGenerador(GeneradorGenetico generador) {
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
		this.movimientos = new boolean[totalMovimientos];
	}

	public void mutar() {
		if (movimientos.length < 2) {
			if (movimientos.length == 1) {
				set(0, !get(0));
			}
			return;
		}
		int posicionMutar1 = random.nextIntMutar1(movimientos.length);
		int posicionMutar2 = random.nextIntMutar2(movimientos.length);

		// Se asegura que sean distintas posiciones
		while (posicionMutar2 == posicionMutar1) {
			posicionMutar2 = random.nextIntMutar2(movimientos.length);
		}
		set(posicionMutar1, !get(posicionMutar1));
		set(posicionMutar2, !get(posicionMutar2));
	}

	public Individuo[] recombinar(Individuo otro) {
		// Grilla 1x1
		if (movimientos.length == 0) {
			return new Individuo[] { new Individuo(grilla), new Individuo(grilla) };
		}

		int k = random.nextIntPosicion(movimientos.length);

		Individuo hijo1 = new Individuo(grilla);
		Individuo hijo2 = new Individuo(grilla);

		for (int i = 0; i < k; i++) {
			hijo1.set(i, this.get(i));
			hijo2.set(i, otro.get(i));
		}
		for (int i = k; i < movimientos.length; i++) {
			hijo1.set(i, otro.get(i));
			hijo2.set(i, this.get(i));
		}
		
		return new Individuo[] { hijo1, hijo2 };
	}

	// Un fitness de 0 es una solución óptima
	public int fitness() {
		Camino camino = this.generarCamino();
		int longitudEsperada = grilla.getFilas() + grilla.getColumnas() - 1;
		if (camino.getTamaño() != longitudEsperada) {
			// Penalización muy alta si el camino no tiene la longitud correcta
			return Integer.MAX_VALUE / 2;
		}
		int suma = camino.calcularSumaCeldas();
		return Math.abs(suma);
	}

	// Genera camino real a partir de los movimientos
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
			if (movimiento) {
				columnaActual++;
			} else {
				filaActual++;
			}

			// Límites de la grilla
			if (filaActual < grilla.getFilas() && columnaActual < grilla.getColumnas()) {
				camino.agregarCelda(grilla.getCelda(filaActual, columnaActual));
			} else {
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
