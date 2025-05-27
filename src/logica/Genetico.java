package logica;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Genetico extends Algoritmo {

	// Los individuos de la poblacion
	private ArrayList<Individuo> _individuos;
	
	// Generador de numeros aleatorios
	private Generador _random; 

	// Parametros de la poblacion
	private int _tamano = 10; 
	private int _mutadosPorIteracion = 2; 
	private int _recombinadosPorIteracion = 4; 
	private int _eliminadosPorIteracion = 14;
	private int _maxIteraciones = 100; 

	// Estadisticas
	private int _iteracion;
	
	public Genetico(Grilla grilla) {
		super(grilla);
		_random = new GeneradorAleatorio();
		Individuo.setGenerador(_random); // Configurar el generador para la clase Individuo
	}

	@Override
	public List<Camino> buscarCaminos() {
	    caminosValidos.clear(); // Limpiar caminos válidos de búsquedas anteriores
		long tiempoInicial = System.nanoTime(); 
		generarIndividuos(); 
		_iteracion = 0; 
		
		// Bucle principal del algoritmo genético
		while (!satisfactoria()) {
			mutarAlgunos();
			recombinarAlgunos();	
			eliminarPeores();
			agregarNuevos(); 
			agregarIndividuosValidos(); // Verificar y agregar soluciones encontradas
			_iteracion++;

		}
		long tiempoFinal = System.nanoTime();
		tiempoEjecucion = (tiempoFinal - tiempoInicial) / 1_000_000.0; // Calcular tiempo en milisegundos

		

		return caminosValidos;
	}

	private void agregarIndividuosValidos() {

		for (Individuo individuo : _individuos) {
			// Un fitness de 0 significa que la suma de cargas del camino es 0 Y que tiene la longitud correcta
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
		_individuos = new ArrayList<Individuo>(_tamano);
		for (int i = 0; i < _tamano; i++) {
			_individuos.add(Individuo.aleatorio(grilla)); // Crea individuos aleatorios para la población inicial
		}		
	}
	
	private boolean satisfactoria() {
		return _iteracion >= _maxIteraciones;
	}
	
	private void mutarAlgunos() {
		for (int j = 0; j < _mutadosPorIteracion; j++) {
			// Asegurarse de que haya individuos en la población para mutar
			if (!_individuos.isEmpty()) { 
				individuoAlAzar().mutar();
			}
		}
	}
	
	private void recombinarAlgunos() {
		for (int j = 0; j < _recombinadosPorIteracion; j++) {
			// Necesitamos al menos dos individuos para la recombinación
			if (_individuos.size() >= 2) { 
				Individuo padre1 = individuoAlAzar();
				Individuo padre2 = individuoAlAzar();

				// Asegurarse de que los padres sean diferentes 
				while (padre1 == padre2 && _individuos.size() > 1) {
					padre2 = individuoAlAzar();
				}

				for (Individuo hijo : padre1.recombinar(padre2)) {
					_individuos.add(hijo); // Añadir los hijos generados a la población
				}
			}
		}
	}
	
	private void eliminarPeores() {
		Collections.sort(_individuos); // Ordena la población por fitness (los mejores primero)

		for (int i = 0; i < _eliminadosPorIteracion; i++) {
			_individuos.remove(_individuos.size() - 1); 
		}
	}
	
	private void agregarNuevos() {
		// Rellena la población con nuevos individuos aleatorios hasta alcanzar el tamaño deseado
		while (_individuos.size() < _tamano) {
			_individuos.add(Individuo.aleatorio(grilla));
		}
	}
	
	// Método auxiliar para seleccionar un individuo aleatorio de la población
	private Individuo individuoAlAzar() {
		if (_individuos.isEmpty()) {
			return null; // O lanzar una excepción si la población está vacía
		}
		int i = _random.nextInt(_individuos.size());
		return _individuos.get(i);
	}

	public int getIteracion() {
		return _iteracion;
	}

}