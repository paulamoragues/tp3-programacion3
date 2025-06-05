package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import generador.GeneradorGrillaPrefijada;
import logica.Camino;
import logica.Celda;
import logica.Grilla;

public class CaminoTest {
	private Camino camino;

	@Before
	public void setUp() {
		camino = new Camino();
	}

	@Test
	public void agregarCeldaTest() {
		Celda c = new Celda(0, 0, true);
		camino.agregarCelda(c);
		assertEquals(1, camino.getTamaño());
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void eliminarCeldaIncorrectaTest() {
		camino.eliminarCelda(0);
	}

	@Test
	public void eliminarCeldaCorrectaTest() {
		Celda c = new Celda(1, 1, true);
		camino.agregarCelda(c);
		camino.eliminarCelda(0);
		assertEquals(0, camino.getTamaño());
	}

	@Test
	public void calcularSumaCargasTest() {
		camino.agregarCelda(new Celda(0, 0, true)); // +1
		camino.agregarCelda(new Celda(0, 1, false)); // -1
		assertEquals(0, camino.calcularSumaCeldas());
	}

	// ???
	@Test
	public void caminoValidoTest() {
		Grilla grilla = crearGrilla();

		camino.agregarCelda(grilla.getCelda(0, 0));
		camino.agregarCelda(grilla.getCelda(1, 0));
		camino.agregarCelda(grilla.getCelda(2, 0));
		camino.agregarCelda(grilla.getCelda(2, 1));

		assertTrue(camino.esValido(grilla.getFilas(), grilla.getColumnas()));
	}

	@Test
	public void caminoIncorrectoSumaTest() {
		Grilla grilla = crearGrilla();

		camino.agregarCelda(grilla.getCelda(0, 0));
		camino.agregarCelda(grilla.getCelda(0, 1));
		camino.agregarCelda(grilla.getCelda(1, 1));
		camino.agregarCelda(grilla.getCelda(2, 1));

		assertFalse(camino.esValido(grilla.getFilas(), grilla.getColumnas()));
	}

	@Test
	public void caminoIncorrectoPosicionTest() {
		Grilla grilla = crearGrilla();
		camino.agregarCelda(grilla.getCelda(1, 0));
		camino.agregarCelda(grilla.getCelda(0, 0));

		assertFalse(camino.esValido(grilla.getFilas(), grilla.getColumnas()));
	}

	@Test
	public void constructorCopiaTest() {
		camino.agregarCelda(new Celda(0, 0, true));
		Camino copia = new Camino(camino);

		assertEquals(camino, copia); // Mismo camino
		assertNotSame(camino, copia); // Distinto objeto
	}

	private Grilla crearGrilla() {

		boolean[][] cargas = { { false, true }, { false, true }, { true, true } };
		Grilla grilla = new Grilla(new GeneradorGrillaPrefijada(cargas, 3, 2));
		grilla.generarGrilla();
		return grilla;
	}
}
