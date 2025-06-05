package logica;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import generador.GeneradorGeneticoAleatorio;

public class Benchmark {
	/**
     * Ejecuta los algoritmos para varios tamaños de grilla.
     * Retorna un mapa con los tiempos en milisegundos.
     * La estructura es: Map<NombreAlgoritmo, Map<TamañoGrilla, TiempoMS>>
     */
	public Map<String, Map<String, Double>> correrBenchmark(List<Grilla> grillas) {
	    Map<String, Map<String, Double>> resultados = new HashMap<>();

	    resultados.put("Fuerza Bruta", new HashMap<>());
	    resultados.put("Backtracking", new HashMap<>());
	    resultados.put("Genético", new HashMap<>());

	    for (Grilla grilla : grillas) {
	        String nombre = grilla.getFilas() + "x" + grilla.getColumnas();

	        long start, end;

	        // Fuerza Bruta
	        start = System.nanoTime();
	        new FuerzaBruta(grilla).buscarCaminos();
	        end = System.nanoTime();
	        double tiempoFB = (end - start) / 1e6;
	        resultados.get("Fuerza Bruta").put(nombre, tiempoFB);

	        // Backtracking
	        start = System.nanoTime();
	        new BackTracking(grilla).buscarCaminos();
	        end = System.nanoTime();
	        double tiempoBT = (end - start) / 1e6;
	        resultados.get("Backtracking").put(nombre, tiempoBT);

	        // Genético
	        start = System.nanoTime();
	        new Genetico(grilla, new GeneradorGeneticoAleatorio()).buscarCaminos();
	        end = System.nanoTime();
	        double tiempoGen = (end - start) / 1e6;
	        resultados.get("Genético").put(nombre, tiempoGen);
	    }

	    return resultados;
	}
}
