package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import generador.GeneradorGrillaPrefijada;
import logica.Celda;
import logica.Grilla;

public class GrillaTest {
	private Grilla grilla;

	@Before
	public void setUp() {
		boolean[][] cargas = { { false, true }, { false, true }, { true, true } };
		grilla = new Grilla(new GeneradorGrillaPrefijada(cargas, 3, 2));
		grilla.generarGrilla();
	}

	@Test
	public void filasConstructorTest() {
		assertEquals(3, grilla.getFilas());
	}

	@Test
	public void columnasConstructorTest() {
		assertEquals(2, grilla.getColumnas());
	}

	@Test
	public void generarGrillaNoNulaTest() {
		for (int fila = 0; fila < grilla.getFilas(); fila++) {
			for (int col = 0; col < grilla.getColumnas(); col++) {
				assertNotNull(grilla.getCelda(fila, col));
			}
		}
	}

	@Test(expected = Exception.class)
	public void generarGrillaNulaTest() {
		Grilla grilla = new Grilla(new GeneradorGrillaPrefijada(null, 2, 1));
		grilla.generarGrilla();
	}

	@Test
	public void generarGrillaCeldasPosicionCorrectaTest() {
		for (int fila = 0; fila < grilla.getFilas(); fila++) {
			for (int col = 0; col < grilla.getColumnas(); col++) {
				Celda c = grilla.getCelda(fila, col);
				assertEquals(fila, c.getFila());
				assertEquals(col, c.getColumna());
			}
		}
	}

	@Test
	public void generarGrillaPosicionIncorrectaTest() {
		for (int fila = 0; fila < grilla.getFilas(); fila++) {
			for (int col = 0; col < grilla.getColumnas(); col++) {
				Celda c = grilla.getCelda(fila, col);
				assertFalse(c.getFila() < 0 || c.getFila() >= grilla.getFilas());
				assertFalse(c.getColumna() < 0 || c.getColumna() >= grilla.getColumnas());
			}
		}
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void getCeldaFilaIncorrectaTest() {
		grilla.getCelda(3, 1);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void getCeldaColumaIncorrectaTest() {
		grilla.getCelda(2, 2);
	}

	@Test
	public void getCeldaEspecificaTest() {
		Celda celda = grilla.getCelda(0, 0);
		assertEquals(0, celda.getFila());
		assertEquals(0, celda.getColumna());
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void getCargaCeldaFilaIncorrectaTest() {
		grilla.getCargaCelda(3, 1);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void getCargaCeldaColumnaIncorrectaTest() {
		grilla.getCargaCelda(2, 2);
	}

	@Test
	public void getCargaCeldaCorrectaTest() {
		int carga = grilla.getCargaCelda(0, 0);
		assertEquals(-1, carga);
	}

}