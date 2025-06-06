package utilidades;

import java.io.IOException;
import java.util.List;

import com.google.gson.JsonSyntaxException;

import generador.GeneradorGrillaPrefijada;
import logica.Grilla;

public class GrillaServicio {
	// ??????

	public static List<GrillaJson.GrillaConDescripcion> cargarTodasLasGrillas(String ruta)
			throws IOException, JsonSyntaxException {
		return GrillaJson.cargarTodas(ruta);
	}

	public static Grilla crearGrillaDesdeIndice(String ruta, int indice) throws IOException {
		List<GrillaJson.GrillaConDescripcion> grillas = GrillaJson.cargarTodas(ruta);

		if (grillas == null || grillas.isEmpty()) {
			throw new IllegalStateException("No hay grillas disponibles en el archivo.");
		}

		if (indice < 0 || indice >= grillas.size()) { 
			throw new IndexOutOfBoundsException("Índice de grilla fuera de rango.");
		}

		GrillaJson.GrillaConDescripcion seleccionada = grillas.get(indice);
		boolean[][] matriz = seleccionada.grilla;
		Grilla grilla = new Grilla(new GeneradorGrillaPrefijada(seleccionada.grilla, matriz.length, matriz[0].length));
		grilla.generarGrilla();
		return grilla;
	}
}
