package utilidades;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import logica.Grilla;

public class GrillaJson {

	public static List<Grilla> cargarTodas(String ruta) throws IOException {
		Gson gson = new Gson();
		Reader reader = new FileReader(ruta);

		Type tipoLista = new TypeToken<List<Grilla>>() {}.getType();
		List<Grilla> grillas = gson.fromJson(reader, tipoLista);

		reader.close();
		return grillas;
	}
}
