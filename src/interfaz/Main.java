package interfaz;

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
		FuerzaBruta algoritmo = new FuerzaBruta(grilla);

		// Estadisticas
		long inicio = System.currentTimeMillis();
		algoritmo.buscarCaminosMinimos();
		long fin = System.currentTimeMillis();

		System.out.println("Caminos encontrados: " + algoritmo.getCantidadCaminos());
		System.out.println("Llamadas recursivas: " + algoritmo.getLlamadas());
		System.out.println("Tiempo de ejecución: " + (fin - inicio) + " ms");

		algoritmo.imprimirCaminos();
	}

}