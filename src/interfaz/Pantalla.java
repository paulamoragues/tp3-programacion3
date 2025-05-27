package interfaz;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import logica.*;

public class Pantalla extends JFrame {
	private static final int ANCHO = 1000;
	private static final int ALTO = 600;

	private JTable tablaResultados;
	private DefaultTableModel modeloResultados;

	private JPanel panelGrilla;
	private JButton botonEjecutar;
	private JScrollPane scrollResultados;

	private Set<Point> celdasCamino;
	private Grilla grillaActual;
	private int _limitePosiblesFilas = 50;
	private int _limitePosiblesColumnas = 10;

	public Pantalla() {
		setTitle("Comparación de Métodos de Búsqueda");
		setSize(ANCHO, ALTO);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		inicializarTablaResultados();
		inicializarPanelGrilla();
		inicializarBotonGenerarGrilla();
		setLocationRelativeTo(null);
	}

	private void inicializarTablaResultados() {
		crearTablaResultados();
		configurarTablaResultados();
		agregarTablaResultadosAScrollPane();
	}

	private void crearTablaResultados() {
		
		String[] columnas = { "Tamaño de Grilla", "Tiempo Fuerza Bruta (ms)", "Tiempo Backtracking (ms)",
				"Tiempo Genético (ms)", 
				"# Caminos Fuerza Bruta", "# Caminos Backtracking", "# Caminos Genético", 
				"# Llamadas Fuerza Bruta", "# Llamadas Backtracking" };
		modeloResultados = new DefaultTableModel(columnas, 0);
		tablaResultados = new JTable(modeloResultados);
	}

	private void configurarTablaResultados() {
		tablaResultados.setRowHeight(27);
		tablaResultados.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tablaResultados.setShowGrid(true);
		tablaResultados.setGridColor(Color.LIGHT_GRAY);

		centrarCeldas();
		configurarEncabezado();

	}

	private void configurarEncabezado() {
		// Estilo del encabezado
		JTableHeader header = tablaResultados.getTableHeader();
		DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) header.getDefaultRenderer();
		headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		header.setFont(header.getFont().deriveFont(Font.BOLD));
	}

	private void centrarCeldas() {
		// Centrado de celdas
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		for (int i = 0; i < tablaResultados.getColumnCount(); i++) {
			tablaResultados.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
	}

	private void agregarTablaResultadosAScrollPane() {
		scrollResultados = new JScrollPane(tablaResultados);
		scrollResultados.setBounds(22, 75, 934, 50);
		getContentPane().add(scrollResultados);
	}

	private void ejecutarMediciones() {
		modeloResultados.setRowCount(0);
		celdasCamino = new HashSet<>();
		generarGrillaAleatoria();

		FuerzaBruta algoritmoSinPoda = new FuerzaBruta(grillaActual);
		BackTracking algoritmoConPoda = new BackTracking(grillaActual);
		Genetico algoritmoGenetico = new Genetico(grillaActual); 

		guardarPrimerCaminoEncontrado(); 

		algoritmoSinPoda.buscarCaminos();
		algoritmoConPoda.buscarCaminos();
		algoritmoGenetico.buscarCaminos(); // Ejecutar Algoritmo Genético

		modeloResultados.addRow(new Object[] { grillaActual.getFilas() + "x" + grillaActual.getColumnas(),
				algoritmoSinPoda.getTiempoEjecucion(), algoritmoConPoda.getTiempoEjecucion(),
				algoritmoGenetico.getTiempoEjecucion(), // Tiempo del Algoritmo Genético
				algoritmoSinPoda.getCantidadCaminos(), algoritmoConPoda.getCantidadCaminos(),
				algoritmoGenetico.getCantidadCaminos(), // Cantidad de caminos del Algoritmo Genético
				algoritmoSinPoda.getCantidadLlamadas(), algoritmoConPoda.getCantidadLlamadas() });

		actualizarPanelGrilla();
		tablaResultados.repaint();
		panelGrilla.repaint();
	}

	private void inicializarPanelGrilla() {
		panelGrilla = new JPanel();
		panelGrilla.setBounds(241, 199, 500, 300); 
		panelGrilla.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY)); 
		getContentPane().add(panelGrilla);
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
				panelGrilla.add(crearPanelCelda(grillaActual.getCelda(fila, col), fila, col));
			}
		}
	}

	private JPanel crearPanelCelda(Celda celda, int fila, int columna) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		// Color de fondo por defecto
		Color colorFondo = Color.WHITE;

		// Cambia color si la celda forma parte del camino
		if (celdasCamino.contains(new Point(fila, columna))) {
			colorFondo = new Color(144, 238, 144); // Verde claro
		}
		panel.setBackground(colorFondo);

		// Texto que indica la carga ("1" o "-1")
		String textoCarga = celda.getCarga() ? "1" : "-1";
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

	private void guardarPrimerCaminoEncontrado() {
		// Se usa el algoritmo genetico para obtener un camino y mostrarlo en la grilla
		var caminos = new Genetico(grillaActual).buscarCaminos();
		if (!caminos.isEmpty()) {
			Camino camino = caminos.get(0);
			for (int i = 0; i < camino.getTamaño(); i++) {
				Celda celda = camino.getCelda(i);
				celdasCamino.add(new Point(celda.getFila(), celda.getColumna()));
			}
		}
	}

	private void inicializarBotonGenerarGrilla() {
		botonEjecutar = new JButton("Generar Grilla Aleatoria");
		botonEjecutar.setBounds(389, 142, 200, 40);
		botonEjecutar.addActionListener(e -> ejecutarMediciones());
		getContentPane().add(botonEjecutar);
	}

	private void generarGrillaAleatoria() {
		Random rand = new Random();
		
		int cantFilas = rand.nextInt(_limitePosiblesFilas);
		int cantColumnas = rand.nextInt(_limitePosiblesColumnas);
		
		grillaActual = new Grilla(cantFilas, cantColumnas);
//		grillaActual = new Grilla(10, 21);
		grillaActual.generarGrillaAleatoria();
	}

}
