package interfaz;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class GraficoChart {
	public static JFreeChart crearGraficoChart(Map<String, Map<String, Double>> resultados) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		// Obtener todos los tamaños únicos (como strings) y ordenarlos numéricamente
		Set<String> claves = new TreeSet<>(Comparator.comparingInt(s -> Integer.parseInt(s.replace("x", ""))));

		for (Map<String, Double> tiempos : resultados.values()) {
			claves.addAll(tiempos.keySet());
		}

		// Agregar datos en orden
		for (String algoritmo : resultados.keySet()) {
			Map<String, Double> data = resultados.get(algoritmo);
			for (String clave : claves) {
				Double valor = data.get(clave);
				if (valor != null) {
					dataset.addValue(valor, algoritmo, clave);
				}
			}
		}

		JFreeChart chart = ChartFactory.createLineChart("Tiempos de Ejecución por Tamaño de Grilla", "Tamaño de Grilla",
				"Tiempo (ms)", dataset, PlotOrientation.VERTICAL, true, true, false);

		return chart;
	}
}
