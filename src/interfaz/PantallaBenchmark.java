package interfaz;

import java.util.Map;
import javax.swing.JFrame;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import utilidades.ChartUtils;

public class PantallaBenchmark extends JFrame{
	/**
	 * 
	 */
	public PantallaBenchmark(Map<String, Map<String, Double>> resultados) {
        setTitle("Resultados Benchmark");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JFreeChart chart = ChartUtils.crearGraficoBenchmark(resultados);
        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);
    }
}
