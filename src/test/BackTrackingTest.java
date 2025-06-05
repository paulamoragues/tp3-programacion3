package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import generador.GeneradorGrillaPrefijada;
import logica.BackTracking;
import logica.Grilla;

public class BackTrackingTest {
	private Grilla grilla;

	@Before
	public void setUp() {
		boolean[][] cargas = { { true, false, false, false }, { false, true, true, false },
				{ true, true, true, false } }; 

		grilla = new Grilla(new GeneradorGrillaPrefijada(cargas, 3, 4));
		grilla.generarGrilla();
	} 

	@Test
	public void cantidadCaminosCorrectoTest() {
		BackTracking bt = new BackTracking(grilla);
		bt.buscarCaminos();

		assertEquals(bt.getCantidadCaminos(), 3);
	}

	@Test
	public void cantidadCaminosIncorrectoTest() {
		BackTracking bt = new BackTracking(grilla);
		bt.buscarCaminos();

		assertNotEquals(bt.getCantidadCaminos(), 2);
	}

	@Test
	public void cantidadLlamadasCorrectasTest() {
		BackTracking bt = new BackTracking(grilla);
		bt.buscarCaminos();

		assertEquals(bt.getCantidadLlamadas(), 28);
	}

	@Test
	public void cantidadLlamadasIncorrectasTest() {
		BackTracking bt = new BackTracking(grilla);
		bt.buscarCaminos();

		assertNotEquals(bt.getCantidadLlamadas(), 27);
	}

	// testear caminos encontrados

}
