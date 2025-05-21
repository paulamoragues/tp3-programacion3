package logica;

import java.util.ArrayList;
import java.util.List;

public class BackTracking {
	private List<Camino> caminosValidosConPoda;
	private Camino caminoActual;
	private Grilla grilla;
	private int filas;
	private int columnas;

	// Estadisticas

	private int llamadasConPoda;
	
	
	public BackTracking(Grilla grilla) {
		this.grilla = grilla;
		this.filas = grilla.getFilas();
		this.columnas = grilla.getColumnas();
		this.caminoActual= new Camino();	
		this.caminosValidosConPoda = new ArrayList<>();
		this.llamadasConPoda = 0;

}
	
	public List<Camino> buscarCaminosMinimosConPoda() {
	    caminosValidosConPoda.clear();   // Limpiar resultados anteriores
	    llamadasConPoda = 0;             // Reiniciar contador
	    caminoActual = new Camino();     // Reiniciar camino actual

	    // Validar solo después de reiniciar para que funcione bien en varias llamadas
	    if ((filas + columnas - 1) % 2 != 0) {
	        return caminosValidosConPoda;
	    }

	    Celda inicio = grilla.getCelda(0, 0);
	    caminoActual.agregarCelda(inicio);

	    buscarConPoda(0, 0, cargaComoEntero(inicio));

	    return caminosValidosConPoda;
	}



	private void buscarConPoda(int fila, int columna, int suma) {
		llamadasConPoda++;

		int restantes = (filas - fila - 1) + (columnas - columna - 1);
		if (suma + restantes < 0 || suma - restantes > 0) {
			return;  // Poda si sabe que no puede llegar a suma == 0
		}
		
		//caso base
		if (fila == filas - 1 && columna == columnas - 1) {
			if (suma == 0) {
				caminosValidosConPoda.add(new Camino(caminoActual));
			}
			return;
		}

		//  abajo
		if (fila + 1 < filas) {
			Celda abajo = grilla.getCelda(fila + 1, columna);
			caminoActual.agregarCelda(abajo);
			buscarConPoda(fila + 1, columna, suma + cargaComoEntero(abajo));
			caminoActual.eliminarCelda(caminoActual.getTamaño() - 1);
		}

		//  derecha
		if (columna + 1 < columnas) {
			Celda derecha = grilla.getCelda(fila, columna + 1);
			caminoActual.agregarCelda(derecha);
			buscarConPoda(fila, columna + 1, suma + cargaComoEntero(derecha));
			caminoActual.eliminarCelda(caminoActual.getTamaño() - 1);
		}
	}
	
	public boolean llegoAlDestino(int fila, int columna) {
		return fila == filas - 1 && columna == columnas - 1;
	}

	private int cargaComoEntero(Celda celda) {
	 
	    return celda.getCarga() ? 1 : -1;
	}


	// para ver en el main

	public int getLlamadasConPoda() {
		return llamadasConPoda;
	}


	public int getCantidadCaminosConPoda() {
		return caminosValidosConPoda.size();
	}


	public void imprimirCaminosConPoda() {
		for (Camino camino : caminosValidosConPoda) {
			for (int i = 0; i < camino.getTamaño(); i++) {
				System.out.print(camino.getCelda(i) + " ");
			}
			System.out.println("\n-----");
		}
	}


}
