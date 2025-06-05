package generador;

import java.util.Random;

public class GeneradorGeneticoAleatorio implements GeneradorGenetico {
	private Random random;

	public GeneradorGeneticoAleatorio() {
		random = new Random();
	}

	@Override
	public boolean nextBoolean() {
		return random.nextBoolean();
	}

	@Override
	public int nextIntMutar1(int rango) {
		return random.nextInt(rango);
	}

	@Override
	public int nextIntMutar2(int rango) {
		return random.nextInt(rango);
	}

	@Override
	public int nextIntPosicion(int rango) {
		return random.nextInt(rango);
	}

}
