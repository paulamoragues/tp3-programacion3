package interfaz;

import javax.swing.SwingUtilities;

//import logica.BackTracking;
//import logica.FuerzaBruta;
//import logica.Grilla;

public class Main {
//
//	// provisorio
//	public static void main(String[] args) {
//		int filas = 6;
//		int columnas = 5;
//		Grilla grilla = new Grilla(filas, columnas);
//		grilla.generarGrillaAleatoria();
//		FuerzaBruta algoritmoSinPoda = new FuerzaBruta(grilla);
//
//		// Estadisticas
//		long inicioSinPoda = System.currentTimeMillis();
//		algoritmoSinPoda.buscarCaminosMinimosSinPoda();
//		long finSinPoda = System.currentTimeMillis();
//		
//		System.out.println("Caminos encontrados sin poda: " + algoritmoSinPoda.getCantidadCaminosSinPoda());
//		System.out.println("Llamadas recursivas sin poda: " + algoritmoSinPoda.getLlamadasSinPoda());
//		System.out.println("Tiempo de ejecución sin poda: " + (finSinPoda - inicioSinPoda) + " ms");
//		algoritmoSinPoda.imprimirCaminosSinPoda();
//		
//		BackTracking algoritmoConPoda = new BackTracking(grilla);
//		long inicioConPoda = System.currentTimeMillis();
//		algoritmoConPoda.buscarCaminosMinimosConPoda();
//		long finConPoda = System.currentTimeMillis();
//
//
//
//		System.out.println("Caminos encontrados con poda: " + algoritmoConPoda.getCantidadCaminosConPoda());
//		System.out.println("Llamadas recursivas con poda: " + algoritmoConPoda.getLlamadasConPoda());
//		System.out.println("Tiempo de ejecución con poda: " + (finConPoda - inicioConPoda) + " ms");
//		algoritmoConPoda.imprimirCaminosConPoda();
//		
//	}
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			Pantalla pantalla = new Pantalla();
			pantalla.setVisible(true);
		});
	}

}
