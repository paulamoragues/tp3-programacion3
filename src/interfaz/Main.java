package interfaz;

import logica.BackTracking;
import logica.FuerzaBruta;
import logica.Grilla;

public class Main {

	// provisorio
	public static void main(String[] args) {
		boolean[][] cargas = { 
				{ true, false, false, false }, 
				{ false, true, true, false },
				{ true, true, true, false }, 
		};

		Grilla grilla = new Grilla(cargas);
		FuerzaBruta algoritmoSinPoda = new FuerzaBruta(grilla);

		// Estadisticas
		long inicioSinPoda = System.currentTimeMillis();
		algoritmoSinPoda.buscarCaminosMinimosSinPoda();
		long finSinPoda = System.currentTimeMillis();
		
		System.out.println("Caminos encontrados sin poda: " + algoritmoSinPoda.getCantidadCaminosSinPoda());
		System.out.println("Llamadas recursivas sin poda: " + algoritmoSinPoda.getLlamadasSinPoda());
		System.out.println("Tiempo de ejecución sin poda: " + (finSinPoda - inicioSinPoda) + " ms");
		algoritmoSinPoda.imprimirCaminosSinPoda();
		
		BackTracking algoritmoConPoda = new BackTracking(grilla);
		long inicioConPoda = System.currentTimeMillis();
		algoritmoConPoda.buscarCaminosMinimosConPoda();
		long finConPoda = System.currentTimeMillis();



		System.out.println("Caminos encontrados con poda: " + algoritmoConPoda.getCantidadCaminosConPoda());
		System.out.println("Llamadas recursivas con poda: " + algoritmoConPoda.getLlamadasConPoda());
		System.out.println("Tiempo de ejecución con poda: " + (finConPoda - inicioConPoda) + " ms");
		algoritmoConPoda.imprimirCaminosConPoda();
		
	}

}
