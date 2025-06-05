package generador;

import java.util.Random;

public class GeneradorGrillaAleatoria implements GeneradorGrilla {

	private int limiteFilas = 15;
	private int limiteColumnas = 15;
	private Random random = new Random();

	@Override
	public int nextIntFilas() {

		return random.nextInt(limiteFilas) + 1;
	}

	@Override
	public int nextIntColumnas() {
		return random.nextInt(limiteColumnas) + 1;
	}

	@Override
	public boolean nextBoolean(int fila, int columna) {
		return random.nextBoolean();
	}

}
