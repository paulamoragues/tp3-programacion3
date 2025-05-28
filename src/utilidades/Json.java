package utilidades;

import java.io.BufferedReader;
import java.io.FileReader;

import com.google.gson.Gson;

import logica.Grilla;

public class Json {

	public static Grilla cargarDesdeJSON(String archivo) {
		Grilla grilla = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(archivo));
			Gson gson = new Gson();
			grilla = gson.fromJson(br, Grilla.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return grilla;
	}

}
