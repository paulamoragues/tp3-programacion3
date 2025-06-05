package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import generador.GeneradorGrillaPrefijada;
import logica.Camino;
import logica.Grilla;

public class CaminoTest {
	private Grilla grilla;
	private Camino camino;

	@Before
	public void setUp() {
		boolean[][] cargas = { { false, true }, { false, true }, { true, true } };
		grilla = new Grilla(new GeneradorGrillaPrefijada(cargas, 3, 2));
		grilla.generarGrilla();

		camino = new Camino();
	}

	@Test
	public void agregarCeldaTest() {
		camino.agregarCelda(grilla.getCelda(0, 0));

		assertEquals(1, camino.getTamaño());
	} 

	@Test(expected = IndexOutOfBoundsException.class)
	public void eliminarCeldaIncorrectaTest() {
		camino.eliminarCelda(0);
	}

	@Test
	public void eliminarCeldaCorrectaTest() {
		camino.agregarCelda(grilla.getCelda(0, 0));
		camino.eliminarCelda(0);

		assertEquals(0, camino.getTamaño());
	}

	@Test
	public void calcularSumaCargasTest() {
		camino.agregarCelda(grilla.getCelda(0, 0));
		camino.agregarCelda(grilla.getCelda(0, 1));

		assertEquals(0, camino.calcularSumaCeldas());
	}

	@Test
	public void caminoValidoVacioTest() {
		assertFalse(camino.esValido(grilla.getFilas(), grilla.getColumnas()));
	}

	@Test
	public void caminoValidoTest() {
		camino.agregarCelda(grilla.getCelda(0, 0));
		camino.agregarCelda(grilla.getCelda(1, 0));
		camino.agregarCelda(grilla.getCelda(2, 0));
		camino.agregarCelda(grilla.getCelda(2, 1));

		assertTrue(camino.esValido(grilla.getFilas(), grilla.getColumnas()));
	}

	@Test
	public void caminoIncorrectoSumaTest() {
		camino.agregarCelda(grilla.getCelda(0, 0));
		camino.agregarCelda(grilla.getCelda(0, 1));
		camino.agregarCelda(grilla.getCelda(1, 1));
		camino.agregarCelda(grilla.getCelda(2, 1));

		assertFalse(camino.esValido(grilla.getFilas(), grilla.getColumnas()));
	}

	@Test
	public void caminoIncorrectoPosicionTest() {
		camino.agregarCelda(grilla.getCelda(1, 0));
		camino.agregarCelda(grilla.getCelda(0, 0));

		assertFalse(camino.esValido(grilla.getFilas(), grilla.getColumnas()));
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void getCeldaIncorrecta() {
		camino.getCelda(0);
	}

	@Test
	public void constructorCopiaTest() {
		camino.agregarCelda(grilla.getCelda(0, 0));
		Camino copia = new Camino(camino);

		assertEquals(camino, copia);
		assertNotSame(camino, copia);
	}

}
