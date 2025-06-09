package benchmark;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import logica.Grilla;
import utilidades.GrillaJson;
import utilidades.GrillaServicio;

public class BenchmarkRunner {

	public static Map<String, Map<String, Double>> ejecutarBenchmarkDesdeJson(String ruta) throws IOException {
		List<GrillaJson.GrillaConDescripcion> grillasJson = GrillaJson.cargarTodas(ruta);
		List<Grilla> grillas = new ArrayList<>();

		for (int i = 0; i < grillasJson.size(); i++) {
			grillas.add(GrillaServicio.crearGrillaDesdeIndice(ruta, i));
		}

		Benchmark benchmark = new Benchmark();
		return benchmark.correrBenchmark(grillas);
	}
}
