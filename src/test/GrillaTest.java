package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import logica.Celda;
import logica.Grilla;

public class GrillaTest {

	private Grilla grilla;

	@Before
	public void setUp() {
		grilla = new Grilla(3, 4);
	}

	@Test
	public void filasConstructorTest() {
		assertEquals(3, grilla.getFilas());
	}

	@Test
	public void columnasConstructorTest() {
		assertEquals(4, grilla.getColumnas());
	}

	@Test
	public void generarGrillaNoNulaTest() {
		grilla.generarGrillaAleatoria();
		for (int fila = 0; fila < grilla.getFilas(); fila++) {
			for (int col = 0; col < grilla.getColumnas(); col++) {
				assertNotNull(grilla.getCelda(fila, col));
			}
		}
	}

	@Test
	public void generarGrillaNulaTest() {
		for (int fila = 0; fila < grilla.getFilas(); fila++) {
			for (int col = 0; col < grilla.getColumnas(); col++) {
				assertNull(grilla.getCelda(fila, col));
			}
		}
	}

	@Test
	public void generarGrillaCeldasConPosicionCorrectaTest() {
		grilla.generarGrillaAleatoria();
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
		grilla.generarGrillaAleatoria();
		for (int fila = 0; fila < grilla.getFilas(); fila++) {
			for (int col = 0; col < grilla.getColumnas(); col++) {
				Celda c = grilla.getCelda(fila, col);
				assertFalse(c.getFila() < 0 || c.getFila() >= grilla.getFilas());
				assertFalse(c.getColumna() < 0 || c.getColumna() >= grilla.getColumnas());
			}
		}
	}

	@Test
	public void getCeldaEspecificaTest() {
		grilla.generarGrillaAleatoria();
		Celda celda = grilla.getCelda(0, 0);
		assertEquals(0, celda.getFila());
		assertEquals(0, celda.getColumna());
	}

	@Test
	public void getCeldaPosicionIncorrectaTest() {
		grilla.generarGrillaAleatoria();
		Celda celda = grilla.getCelda(0, 0);
		assertFalse(celda.getFila() != 0 || celda.getColumna() != 0);
	}

}
