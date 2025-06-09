package interfaz;

import javax.swing.*;
import javax.swing.table.*;

import com.google.gson.JsonSyntaxException;

import algoritmo.Algoritmo;
import benchmark.BenchmarkRunner;
import generador.GeneradorGeneticoAleatorio;
import generador.GeneradorGrillaAleatoria;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.io.IOException;
import java.util.*;

import logica.*;
import utilidades.GrillaServicio;
import utilidades.GrillaJson;

public class PantallaPrincipal {
	private static final int ANCHO = 1000;
	private static final int ALTO = 600;

	private JFrame ventana;
	private JTable tablaResultados;
	private DefaultTableModel modeloResultados;
	private JScrollPane scrollResultados;

	private JPanel panelGrilla;
	private Grilla grillaActual;

	private Set<Point> celdasCamino;

	public PantallaPrincipal() {
		inicializarPantalla();
		inicializarTablaResultados();
		inicializarPanelGrilla();
		inicializarBotonGenerarGrilla();
		inicializarBotonCargarGrilla();
		inicializarBotonBenchmark();
		ventana.setVisible(true);
	}

	private void inicializarPantalla() {
		ventana = new JFrame("Comparación de Métodos de Búsqueda");
		ventana.setSize(ANCHO, ALTO);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.getContentPane().setLayout(null);
		ventana.setLocationRelativeTo(null);
	}

	private void inicializarTablaResultados() {
		crearTablaResultados();
		configurarTablaResultados();
		agregarTablaResultados();
	}

	private void crearTablaResultados() {
		String[] columnas = { "Tamaño de Grilla", "Tiempo Fuerza Bruta (ms)", "Tiempo Backtracking (ms)",
				"Tiempo Genético (ms)", "Caminos Fuerza Bruta", "Caminos Backtracking", "Caminos Genético",
				"Llamadas Fuerza Bruta", "Llamadas Backtracking" };
		modeloResultados = new DefaultTableModel(columnas, 0);
		tablaResultados = new JTable(modeloResultados);
	}

	private void configurarTablaResultados() {
		tablaResultados.setRowHeight(27);
		tablaResultados.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tablaResultados.setShowGrid(true);
		tablaResultados.setGridColor(Color.LIGHT_GRAY);

		centrarCeldasTabla();
		configurarEncabezadoTabla();
	}

	private void centrarCeldasTabla() {
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		for (int i = 0; i < tablaResultados.getColumnCount(); i++) {
			tablaResultados.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
	}

	private void configurarEncabezadoTabla() {
		JTableHeader header = tablaResultados.getTableHeader();
		DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) header.getDefaultRenderer();
		headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		header.setFont(header.getFont().deriveFont(Font.BOLD));
	}

	private void agregarTablaResultados() {
		scrollResultados = new JScrollPane(tablaResultados);
		scrollResultados.setBounds(22, 75, 934, 50);
		ventana.getContentPane().add(scrollResultados);
	}

	private void inicializarPanelGrilla() {
		panelGrilla = new JPanel();
		panelGrilla.setBounds(241, 199, 500, 300);
		panelGrilla.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		ventana.getContentPane().add(panelGrilla);
	}

	private void inicializarBotonGenerarGrilla() {
		JButton botonEjecutar = new JButton("Generar Grilla Aleatoria");
		botonEjecutar.setBounds(400, 142, 200, 40);
		botonEjecutar.addActionListener(e -> generarGrillaAleatoria());
		ventana.getContentPane().add(botonEjecutar);
	}

	private void generarGrillaAleatoria() {
		grillaActual = new Grilla(new GeneradorGrillaAleatoria());
		grillaActual.generarGrilla();

		ejecutarMediciones();
	}

	private void ejecutarMediciones() {
		modeloResultados.setRowCount(0);

		FuerzaBruta algoritmoFB = new FuerzaBruta(grillaActual);
		BackTracking algoritmoBT = new BackTracking(grillaActual);
		Genetico algoritmoGenetico = new Genetico(grillaActual, new GeneradorGeneticoAleatorio());

		algoritmoFB.buscarCaminos();
		algoritmoBT.buscarCaminos();
		algoritmoGenetico.buscarCaminos();

		obtenerPrimerCaminoEncontrado(algoritmoGenetico);

		modeloResultados.addRow(new Object[] { grillaActual.getFilas() + "x" + grillaActual.getColumnas(),
				algoritmoFB.getTiempoEjecucion(), algoritmoBT.getTiempoEjecucion(),
				algoritmoGenetico.getTiempoEjecucion(), algoritmoFB.getCantidadCaminos(),
				algoritmoBT.getCantidadCaminos(), algoritmoGenetico.getCantidadCaminos(),
				algoritmoFB.getCantidadLlamadas(), algoritmoBT.getCantidadLlamadas() });

		actualizarPanelGrilla();
	}

	private void actualizarPanelGrilla() {
		int filas = grillaActual.getFilas();
		int columnas = grillaActual.getColumnas();

		panelGrilla.removeAll();
		panelGrilla.setLayout(new GridLayout(filas, columnas));

		llenarPanelConCeldas(filas, columnas);
		ajustarDimensionPanel(filas, columnas);

		panelGrilla.revalidate();
		panelGrilla.repaint();
	}

	private void llenarPanelConCeldas(int filas, int columnas) {
		for (int fila = 0; fila < filas; fila++) {
			for (int col = 0; col < columnas; col++) {
				int carga = grillaActual.getCargaCelda(fila, col);
				panelGrilla.add(crearPanelCelda(fila, col, carga));
			}
		}
	}

	private JPanel crearPanelCelda(int fila, int columna, int carga) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		if (celdasCamino.contains(new Point(fila, columna)))
			panel.setBackground(Color.ORANGE);
		else
			panel.setBackground(Color.WHITE);

		String textoCarga = carga + "";
		JLabel labelCarga = new JLabel(textoCarga, SwingConstants.CENTER);
		labelCarga.setForeground(Color.BLACK);

		panel.add(labelCarga, BorderLayout.CENTER);
		return panel;
	}

	private void ajustarDimensionPanel(int filas, int columnas) {
		int alturaCelda = 40;
		int anchoCelda = 40;
		panelGrilla.setPreferredSize(new Dimension(columnas * anchoCelda, filas * alturaCelda));
	}

	// modificar
	private void obtenerPrimerCaminoEncontrado(Algoritmo algoritmo) {
		// deberia ser algoritmoGenetico.primerCaminoEncontrado();
		celdasCamino = new HashSet<>();
		if (algoritmo.getCantidadCaminos() > 0) {
			Camino camino = algoritmo.getCamino(0);
			for (int i = 0; i < camino.getTamaño(); i++) {
				Celda celda = camino.getCelda(i);
				celdasCamino.add(new Point(celda.getFila(), celda.getColumna()));
			}
		}
	}

	private void inicializarBotonCargarGrilla() {
		JButton botonCargar = new JButton("Cargar Grilla");
		botonCargar.setBounds(180, 142, 200, 40);
		botonCargar.addActionListener(e -> cargarGrillaDesdeArchivo());
		ventana.getContentPane().add(botonCargar);
	}

	private void cargarGrillaDesdeArchivo() {
		try {
			List<GrillaJson.GrillaConDescripcion> grillas = GrillaServicio.cargarTodasLasGrillas("grilla.json");

			String[] opciones = new String[grillas.size()];
			for (int i = 0; i < grillas.size(); i++) {
				opciones[i] = (i + 1) + " - " + grillas.get(i).descripcion;
			}

			String seleccion = (String) JOptionPane.showInputDialog(null, "Seleccioná una grilla para cargar:",
					"Seleccionar Grilla", JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);

			if (seleccion != null) {
				int indiceSeleccionado = Integer.parseInt(seleccion.split(" ")[0]) - 1;
				grillaActual = GrillaServicio.crearGrillaDesdeIndice("grilla.json", indiceSeleccionado);

				mostrarMensaje("Grilla cargada correctamente.", JOptionPane.INFORMATION_MESSAGE);
				ejecutarMediciones();
			}
		} catch (IOException e) {
			mostrarMensaje("No se pudo leer el archivo.", JOptionPane.ERROR_MESSAGE);
		} catch (JsonSyntaxException e) {
			mostrarMensaje("El archivo JSON está mal escrito.", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			mostrarMensaje("Error inesperado.", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void mostrarMensaje(String mensaje, int tipoMensaje) {
		JOptionPane.showMessageDialog(null, mensaje, "Información", tipoMensaje);
	}

	private void inicializarBotonBenchmark() {
		JButton botonBenchmark = new JButton("Ejecutar Benchmark");
		botonBenchmark.setBounds(620, 142, 200, 40);
		botonBenchmark.addActionListener(e -> ejecutarBenchmark());
		ventana.getContentPane().add(botonBenchmark);
	}

	private void ejecutarBenchmark() {
		SwingWorker<Void, Void> worker = new SwingWorker<>() {
			@Override
			protected Void doInBackground() {
				try {
					Map<String, Map<String, Double>> resultados = BenchmarkRunner
							.ejecutarBenchmarkDesdeJson("grilla.json");

					SwingUtilities.invokeLater(() -> {
						PantallaChart ventana = new PantallaChart(resultados);
						ventana.mostrar();
					});

				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		};
		worker.execute();
	}

}
