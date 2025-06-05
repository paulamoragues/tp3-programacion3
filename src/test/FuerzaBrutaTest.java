package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import generador.GeneradorGrillaPrefijada;
import logica.FuerzaBruta;
import logica.Grilla;

public class FuerzaBrutaTest {
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
		FuerzaBruta fb = new FuerzaBruta(grilla);
		fb.buscarCaminos();

		assertEquals(fb.getCantidadCaminos(), 3);
	}

	@Test
	public void cantidadCaminosIncorrectoTest() {
		FuerzaBruta fb = new FuerzaBruta(grilla);
		fb.buscarCaminos();

		assertNotEquals(fb.getCantidadCaminos(), 2);
	}

	@Test
	public void cantidadLlamadasCorrectasTest() {
		FuerzaBruta fb = new FuerzaBruta(grilla);
		fb.buscarCaminos();

		assertEquals(fb.getCantidadLlamadas(), 34);
	}

	@Test
	public void cantidadLlamadasIncorrectasTest() {
		FuerzaBruta fb = new FuerzaBruta(grilla);
		fb.buscarCaminos();

		assertNotEquals(fb.getCantidadLlamadas(), 33);
	}

	// testear caminos encontrados

}
