package benchmark;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import logica.Grilla;
import utilidades.GrillaJson;

public class BenchmarkRunner {

	public static Map<String, Map<String, Double>> ejecutarBenchmarkDesdeJson(String ruta) throws IOException {
		List<Grilla> grillas = GrillaJson.cargarTodas(ruta);

		Benchmark benchmark = new Benchmark(); 
		return benchmark.correrBenchmark(grillas);
	}
}
