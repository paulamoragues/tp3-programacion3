package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import logica.Camino;
import logica.Celda;

public class CaminoTest {

	private Camino camino;
	private Celda celda1;
	// private Celda celda2;

	@Before
	public void setUp() {
		camino = new Camino();
		celda1 = new Celda(0, 0, false);
		// celda2 = new Celda(1, 1, true);
	}

	@Test
	public void testAgregarCelda() {
		camino.agregarCelda(celda1);
		assertEquals(1, camino.getTamaño());
		assertEquals(celda1, camino.getCelda(0));
	}

	@Test
	public void testEliminarCelda() {
		camino.agregarCelda(celda1);
		camino.eliminarCelda(0);
		assertEquals(0, camino.getTamaño());
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testEliminarCeldaFueraDeRango() {
		camino.eliminarCelda(0); // sin agregar nada
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testGetCeldaFueraDeRango() {
		camino.getCelda(0); // sin agregar nada
	}

	@Test
	public void getCeldaIndiceValidoTest() {
		camino.agregarCelda(celda1);
		assertEquals(celda1, camino.getCelda(0));
	}


	@Test
	public void equalsTest() {
		Camino camino2 = new Camino();

		Celda celda2 = new Celda(0, 1, true);

		camino.agregarCelda(celda1);
		camino2.agregarCelda(celda2);

		// Misma posición pero cargas distintas, debe ser igual
		camino2.agregarCelda(new Celda(0, 0, false));
		camino2.agregarCelda(new Celda(0, 1, true));

		assertFalse(camino.equals(camino2));
	}

	@Test
	public void equalsDistintoTamanioTest() {
		Camino camino2 = new Camino();

		camino.agregarCelda(new Celda(0, 0, true));
		camino2.agregarCelda(new Celda(0, 0, true));
		camino2.agregarCelda(new Celda(0, 1, false));

		assertFalse(camino.equals(camino2));
	}

	@Test
	public void equalsDistintaPosicionTest() {
		Camino camino2 = new Camino();

		camino.agregarCelda(new Celda(0, 0, true));
		camino2.agregarCelda(new Celda(1, 0, true)); // distinta fila

		assertFalse(camino.equals(camino2));
	}

	// Faltan más

}