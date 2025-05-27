package logica;

import java.util.Random;

public class GeneradorAleatorio implements Generador {

	private Random _random;
	
	public GeneradorAleatorio() {
		_random = new Random();
	}
	
	@Override
	public boolean nextBoolean() {
		// TODO Auto-generated method stub
		return _random.nextBoolean();
	}

	@Override
	public int nextInt(int rango) {
		// TODO Auto-generated method stub
		return _random.nextInt(rango);
	}

}
