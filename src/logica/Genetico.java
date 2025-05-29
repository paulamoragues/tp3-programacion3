package logica;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import generador.Generador;
import generador.GeneradorAleatorio;

public class Genetico extends Algoritmo {
	private ArrayList<Individuo> individuos;
	private Generador random;

	// Parametros de la población
	private int tamaño = 100;
	private int mutadosPorIteracion = 10;
	private int recombinadosPorIteracion = 20;
	private int eliminadosPorIteracion = 60;
	private int maxIteraciones = 1000;

	// Estadísticas
	private int iteracion;

	public Genetico(Grilla grilla) {
		super(grilla);
		random = new GeneradorAleatorio();
		Individuo.setGenerador(random); // Configurar el generador para la clase Individuo
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
			agregarIndividuosValidos(); // Verificar y agregar soluciones encontradas
			iteracion++;

		}

		long tiempoFinal = System.nanoTime();
		tiempoEjecucion = (tiempoFinal - tiempoInicial) / 1_000_000.0;
		return caminosValidos;
	}

	private void agregarIndividuosValidos() {

		for (Individuo individuo : individuos) {
			// Un fitness de 0 significa que la suma de cargas del camino es 0 Y que tiene
			// la longitud correcta
			if (individuo.fitness() == 0) {
				Camino caminoEncontrado = individuo.generarCamino();
				// Evitar añadir caminos duplicados a la lista final de soluciones
				if (!caminosValidos.contains(caminoEncontrado)) {
					caminosValidos.add(caminoEncontrado);
				}
			}
		}
	}

	private void generarIndividuos() {
		individuos = new ArrayList<Individuo>(tamaño);
		for (int i = 0; i < tamaño; i++) {
			individuos.add(Individuo.aleatorio(grilla)); // Crea individuos aleatorios para la población inicial
		}
	}

	private boolean satisfactoria() {
		return iteracion >= maxIteraciones;
	}

	private void mutarAlgunos() {
		for (int j = 0; j < mutadosPorIteracion; j++) {
			// Asegurarse de que haya individuos en la población para mutar
			if (!individuos.isEmpty()) {
				individuoAleatorio().mutar();
			}
		}
	}

	private void recombinarAlgunos() {
		for (int j = 0; j < recombinadosPorIteracion; j++) {
			// Necesitamos al menos dos individuos para la recombinación
			if (individuos.size() >= 2) {
				Individuo padre1 = individuoAleatorio();
				Individuo padre2 = individuoAleatorio();

				// Asegurarse de que los padres sean diferentes
				while (padre1 == padre2 && individuos.size() > 1) {
					padre2 = individuoAleatorio();
				}

				for (Individuo hijo : padre1.recombinar(padre2)) {
					individuos.add(hijo); // Añadir los hijos generados a la población
				}
			}
		}
	}

	private void eliminarPeores() {
		Collections.sort(individuos); // Los mejores primero

		for (int i = 0; i < eliminadosPorIteracion; i++) {
			individuos.remove(individuos.size() - 1);
		}
	}

	private void agregarNuevos() {
		while (individuos.size() < tamaño) {
			individuos.add(Individuo.aleatorio(grilla));
		}
	}

	private Individuo individuoAleatorio() {
		verificarIndividuos();
		int i = random.nextInt(individuos.size());
		return individuos.get(i);
	}

	public int getIteracion() {
		return iteracion;
	}

	private void verificarIndividuos() {
		if (individuos.isEmpty()) {
			throw new IllegalStateException("La población está vacía.");
		}
	}

}
