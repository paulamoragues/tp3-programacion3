package benchmark;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import generador.GeneradorGeneticoAleatorio;
import logica.BackTracking;
import logica.FuerzaBruta;
import logica.Genetico;
import logica.Grilla;

public class Benchmark {

	public Map<String, Map<String, Double>> correrBenchmark(List<Grilla> grillas) {
		Map<String, Map<String, Double>> resultados = new HashMap<>();

		resultados.put("Fuerza Bruta", new HashMap<>());
		resultados.put("Backtracking", new HashMap<>());
		resultados.put("Genético", new HashMap<>());

		for (Grilla grilla : grillas) {
			String nombre = grilla.getFilas() + "x" + grilla.getColumnas();

			// Fuerza Bruta
			FuerzaBruta fb = new FuerzaBruta(grilla);
			fb.buscarCaminos();
			double tiempoFB = fb.getTiempoEjecucion();
			resultados.get("Fuerza Bruta").put(nombre, tiempoFB);

			// Backtracking
			BackTracking bt = new BackTracking(grilla);
			bt.buscarCaminos();
			double tiempoBT = bt.getTiempoEjecucion();
			resultados.get("Backtracking").put(nombre, tiempoBT);

			// Genético
			Genetico gen = new Genetico(grilla, new GeneradorGeneticoAleatorio());
			gen.buscarCaminos();
			double tiempoGen = gen.getTiempoEjecucion();
			resultados.get("Genético").put(nombre, tiempoGen);
		}

		return resultados;
	}
}