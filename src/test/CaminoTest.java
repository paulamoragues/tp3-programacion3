package test;

import static org.junit.Assert.*;

import java.util.List;

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
	public void constructorCopiaTest() {
		camino.agregarCelda(celda1);

		Camino caminoCopia = new Camino(camino);
		assertEquals(camino.getTamaño(), caminoCopia.getTamaño());
		assertEquals(camino.getCelda(0), caminoCopia.getCelda(0));

		assertNotSame(camino.getCeldas(), caminoCopia.getCeldas());
	}

	@Test
	public void getCeldasDevuelveCopiaTest() {
		camino.agregarCelda(celda1);

		List<Celda> listaCeldas = camino.getCeldas();
		assertEquals(1, listaCeldas.size());
		listaCeldas.clear();

		// La lista interna no debería cambiar al modificar la copia
		assertEquals(1, camino.getTamaño());
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

	// Faltan másss

}