package logica;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import algoritmo.Algoritmo;
import generador.GeneradorGenetico;

public class Genetico extends Algoritmo {
	private ArrayList<Individuo> individuos;
	private GeneradorGenetico random;

	// Parámetros de la población
	private int tamaño = 100;
	private int mutadosPorIteracion = 10;
	private int recombinadosPorIteracion = 20;
	private int eliminadosPorIteracion = 60;
	private int maxIteraciones = 1000;

	// Estadísticas
	private int iteracion;

	public Genetico(Grilla grilla, GeneradorGenetico generador) {
		super(grilla);
		this.random = generador;

		// Configurar el generador para la clase Individuo
		Individuo.setGenerador(random);
	}

	@Override
	public List<Camino> buscarCaminos() {
		long tiempoInicial = System.nanoTime();

		caminosValidos.clear();
		generarIndividuos();
		iteracion = 0;

		while (!satisfactoria()) {
			mutarAlgunos();
			recombinarAlgunos();
			eliminarPeores();
			agregarNuevos();

			agregarCaminosValidos();
			iteracion++;
		}

		long tiempoFinal = System.nanoTime();
		tiempoEjecucion = (tiempoFinal - tiempoInicial) / 1_000_000.0;
		return caminosValidos;
	}

	private void generarIndividuos() {
		individuos = new ArrayList<Individuo>(tamaño);
		for (int i = 0; i < tamaño; i++) {
			individuos.add(Individuo.aleatorio(grilla));
		}
	}

	private boolean satisfactoria() {
		return iteracion >= maxIteraciones;
	}

	private void mutarAlgunos() {
		for (int j = 0; j < mutadosPorIteracion; j++) {
			if (!individuos.isEmpty()) {
				individuoAleatorio().mutar();
			}
		}
	}

	private void recombinarAlgunos() {
		for (int j = 0; j < recombinadosPorIteracion; j++) {
			if (individuos.size() >= 2) {
				Individuo padre1 = individuoAleatorio();
				Individuo padre2 = individuoAleatorio();

				// Padres diferentes
				while (padre1 == padre2 && individuos.size() > 1) {
					padre2 = individuoAleatorio();
				}

				for (Individuo hijo : padre1.recombinar(padre2)) {
					individuos.add(hijo);
				}
			}
		}
	}

	private Individuo individuoAleatorio() {
		int i = random.nextIntPosicion(individuos.size());
		return individuos.get(i);
	}

	private void eliminarPeores() {
		// Los mejores primero
		Collections.sort(individuos);

		for (int i = 0; i < eliminadosPorIteracion; i++) {
			individuos.remove(individuos.size() - 1);
		}
	}

	private void agregarNuevos() {
		while (individuos.size() < tamaño) {
			individuos.add(Individuo.aleatorio(grilla));
		}
	}

	private void agregarCaminosValidos() {
		for (Individuo individuo : individuos) {
			Camino caminoEncontrado = individuo.generarCamino();

			// Evitar añadir caminos duplicados a la lista de soluciones
			if (caminoEncontrado.esValido(filas, columnas) && !caminosValidos.contains(caminoEncontrado)) {
				caminosValidos.add(caminoEncontrado);
			}
		}
	}

	public int getIteracion() {
		return iteracion;
	}

}
