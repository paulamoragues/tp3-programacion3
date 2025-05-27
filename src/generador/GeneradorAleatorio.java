package generador;

import java.util.Random;

public class GeneradorAleatorio implements Generador {
	private Random random;

	public GeneradorAleatorio() {
		random = new Random();
	}

	@Override
	public boolean nextBoolean() {
		return random.nextBoolean();
	}

	@Override
	public int nextInt(int rango) {
		return random.nextInt(rango);
	}

}
