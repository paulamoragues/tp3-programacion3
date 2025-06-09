package interfaz;

import java.util.Map;
import javax.swing.JFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

public class PantallaChart {
	private JFrame ventana;

	public PantallaChart(Map<String, Map<String, Double>> resultados) {
		ventana = new JFrame("Resultados Benchmark");
		ventana.setSize(800, 600);
		ventana.setLocationRelativeTo(null);
		ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JFreeChart chart = GraficoChart.crearGraficoChart(resultados);
		ChartPanel panel = new ChartPanel(chart);
		ventana.setContentPane(panel);
	}

	public void mostrar() {
		ventana.setVisible(true);
	}

	public void cerrar() {
		ventana.dispose();
	}
}