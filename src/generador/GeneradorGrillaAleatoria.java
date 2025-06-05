package generador;

import java.util.Random;

public class GeneradorGrillaAleatoria implements GeneradorGrilla {
	
	private int _limiteFilas = 15;
	private int _limiteColumnas = 15;
	private Random random= new Random();
	
	
	@Override
	public int nextIntFilas() {
		
		return random.nextInt(_limiteFilas)+1 ;
	}

	@Override
	public int nextIntColumnas() {
		return random.nextInt(_limiteColumnas)+1 ;
	}


	@Override
	public boolean nextBoolean(int fila, int columna) {
		return random.nextBoolean();
	}

}
