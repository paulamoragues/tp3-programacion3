package utilidades;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import logica.Grilla;

public class JsonGrilla {

//	public static Grilla cargarDesdeJSON(String archivo) {
//		Gson gson = new Gson();
//		Grilla grilla = null;
//
//		try {
//			BufferedReader br = new BufferedReader(new FileReader(archivo));
//			grilla = gson.fromJson(br, Grilla.class);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return grilla;
//	}

	public static Grilla cargarDesdeJSON(String archivo) throws IOException, JsonSyntaxException {
		Gson gson = new Gson();
		try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
			return gson.fromJson(br, Grilla.class);
		}
	}

}
