package logica;

import java.util.ArrayList;
import java.util.List;

public class FuerzaBruta {

	private List<Camino> caminosValidos;
	private Camino caminoActual;
	private Grilla grilla;
	private int filas;
	private int columnas;
	
	// Estadisticas
	private int llamadas;

	public FuerzaBruta(Grilla grilla) {
		this.grilla = grilla;
		this.filas = grilla.getFilas();
		this.columnas = grilla.getColumnas();
		this.caminosValidos = new ArrayList<>();
		this.caminoActual = new Camino();
		this.llamadas = 0;
	}

	public List<Camino> buscarCaminosMinimos() {
		if ((filas + columnas - 1) % 2 != 0) {
			return caminosValidos;
		}
		Celda inicio = grilla.getCelda(0, 0);
		caminoActual.agregarCelda(inicio);
		
		buscar(0, 0, cargaComoEntero(inicio));
		
		return caminosValidos;
	}

	private void buscar(int fila, int columna, int suma) {
		llamadas++;
		
		// caso base
		if (llegoAlDestino(fila, columna)) {
			if (suma == 0) {
				caminosValidos.add(new Camino(caminoActual));
			}
			return;
		}

		// Abajo
		if (fila + 1 < filas) {
			Celda abajo = grilla.getCelda(fila + 1, columna);
			
			caminoActual.agregarCelda(abajo);
			buscar(fila + 1, columna, suma + cargaComoEntero(abajo));
			caminoActual.eliminarCelda(caminoActual.getTamaño() - 1);
		}

		// Derecha
		if (columna + 1 < columnas) {
			Celda derecha = grilla.getCelda(fila, columna + 1);
			
			caminoActual.agregarCelda(derecha);
			buscar(fila, columna + 1, suma + cargaComoEntero(derecha));
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
	public int getLlamadas() {
		return llamadas;
	}
	
	public int getCantidadCaminos() {
		return caminosValidos.size();
	}

	public void imprimirCaminos() {
	    for (Camino camino : caminosValidos) {
	        for (int i = 0; i < camino.getTamaño(); i++) {
	            System.out.print(camino.getCelda(i) + " ");
	        }
	        System.out.println("\n-----");
	    }
	}

	
}
