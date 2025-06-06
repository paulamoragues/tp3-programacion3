package utilidades;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GrillaJson { 

	// Clase interna que representa una grilla con su descripción
	public static class GrillaConDescripcion {
		public String descripcion;
		public boolean[][] grilla;
	}
	
	public static List<GrillaConDescripcion> cargarTodas(String ruta) throws IOException {
		Gson gson = new Gson();
		Reader reader = new FileReader(ruta);

		Type tipoLista = new TypeToken<List<GrillaConDescripcion>>() {}.getType();
		List<GrillaConDescripcion> grillas = gson.fromJson(reader, tipoLista);

		reader.close();
		return grillas;
	}
}
