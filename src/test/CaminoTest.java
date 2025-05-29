package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import logica.Camino;
import logica.Celda;

public class CaminoTest {

	private Camino camino;
	private Celda celda1;
	//private Celda celda2;

	@Before
	public void setUp() {
		camino = new Camino();
		celda1 = new Celda(0, 0, false);
		//celda2 = new Celda(1, 1, true);
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
	
	// Faltan másss

}