package interfaz;

import javax.swing.*;
import javax.swing.table.*;

import com.google.gson.JsonSyntaxException;

import algoritmo.Algoritmo;
import generador.GeneradorAleatorio;

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
import utilidades.JsonGrilla;

public class Pantalla {
	private static final int ANCHO = 1000;
	private static final int ALTO = 600;
	private int limitePosiblesFilas = 15;
	private int limitePosiblesColumnas = 15;

	private JFrame ventana;
	private JTable tablaResultados;
	private DefaultTableModel modeloResultados;
	private JScrollPane scrollResultados;

	private JPanel panelGrilla;
	private Set<Point> celdasCamino;
	private Grilla grillaActual;

	public Pantalla() {
		inicializarPantalla();
		inicializarTablaResultados();
		inicializarPanelGrilla();
		inicializarBotonGenerarGrilla();
		inicializarBotonCargarGrilla();
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
		botonEjecutar.setBounds(510, 142, 200, 40);
		botonEjecutar.addActionListener(e -> generarGrillaAleatoria());
		ventana.getContentPane().add(botonEjecutar);
	}

	// ..
	private void generarGrillaAleatoria() {
		Random rand = new Random();
		int cantFilas = rand.nextInt(limitePosiblesFilas) + 1;
		int cantColumnas = rand.nextInt(limitePosiblesColumnas) + 1;

		grillaActual = new Grilla(cantFilas, cantColumnas);
		grillaActual.generarGrillaAleatoria();

		ejecutarMediciones();
	}

	private void ejecutarMediciones() {
		modeloResultados.setRowCount(0);
		celdasCamino = new HashSet<>();

		FuerzaBruta algoritmoFB = new FuerzaBruta(grillaActual);
		BackTracking algoritmoBT = new BackTracking(grillaActual);
		Genetico algoritmoGenetico = new Genetico(grillaActual, new GeneradorAleatorio());

		algoritmoFB.buscarCaminos();
		algoritmoBT.buscarCaminos();
		algoritmoGenetico.buscarCaminos();

		guardarPrimerCaminoEncontrado(algoritmoGenetico);

		modeloResultados.addRow(new Object[] { grillaActual.getFilas() + "x" + grillaActual.getColumnas(),
				algoritmoFB.getTiempoEjecucion(), algoritmoBT.getTiempoEjecucion(),
				algoritmoGenetico.getTiempoEjecucion(), algoritmoFB.getCantidadCaminos(),
				algoritmoBT.getCantidadCaminos(), algoritmoGenetico.getCantidadCaminos(),
				algoritmoFB.getCantidadLlamadas(), algoritmoBT.getCantidadLlamadas() });

		actualizarPanelGrilla();
		tablaResultados.repaint();
		panelGrilla.repaint();
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

		Color colorFondo = Color.WHITE;

		if (celdasCamino.contains(new Point(fila, columna))) {
			colorFondo = new Color(144, 238, 144);
		}
		panel.setBackground(colorFondo);

		String textoCarga = celda.getCargaEntero() + "";
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

	private void guardarPrimerCaminoEncontrado(Algoritmo algoritmo) {
		if (algoritmo.getCantidadCaminos() > 0) {
			Camino camino = algoritmo.getCamino(0);
			for (int i = 0; i < camino.getTamaño(); i++) {
				Celda celda = camino.getCelda(i);
				celdasCamino.add(new Point(celda.getFila(), celda.getColumna()));
			}
		}
	}
	// ..

	private void inicializarBotonCargarGrilla() {
		JButton botonCargar = new JButton("Cargar Grilla");
		botonCargar.setBounds(290, 142, 200, 40);
		botonCargar.addActionListener(e -> cargarGrillaDesdeArchivo());
		ventana.getContentPane().add(botonCargar);
	}

	// Paula revisa 🙏
	private void cargarGrillaDesdeArchivo() {
		try {
			List<JsonGrilla.GrillaConDescripcion> grillas = GrillaServicio.cargarTodasLasGrillas("grilla.json");

			if (grillas == null || grillas.isEmpty()) {
				JOptionPane.showMessageDialog(null, "No hay grillas disponibles en el archivo.");
				return;
			}

			String[] opciones = new String[grillas.size()];
			for (int i = 0; i < grillas.size(); i++) {
				opciones[i] = (i + 1) + " - " + grillas.get(i).descripcion;
			}

			String seleccion = (String) JOptionPane.showInputDialog(
					null,
					"Seleccioná una grilla para cargar:",
					"Seleccionar Grilla",
					JOptionPane.PLAIN_MESSAGE,
					null,
					opciones,
					opciones[0]);

			if (seleccion != null) {
				int indiceSeleccionado = Integer.parseInt(seleccion.split(" ")[0]) - 1;
				grillaActual = GrillaServicio.crearGrillaDesdeIndice("grilla.json", indiceSeleccionado);

				ejecutarMediciones();
				JOptionPane.showMessageDialog(null, "Grilla cargada correctamente.");
			}

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "No se pudo leer el archivo: " + e.getMessage(), "Error de lectura",
					JOptionPane.ERROR_MESSAGE);
		} catch (JsonSyntaxException e) {
			JOptionPane.showMessageDialog(null, "El archivo JSON está malformado: " + e.getMessage(),
					"Error de formato", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error inesperado: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

}
