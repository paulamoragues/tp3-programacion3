package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import generador.GeneradorGrillaPrefijada;
import logica.FuerzaBruta;
import logica.Grilla;
import logica.Camino;

public class FuerzaBrutaTest {
	private Grilla grilla;
	private List<Camino> caminosValidos;

	@Before
	public void setUp() {
		boolean[][] cargas = { { false, true }, { false, true }, { true, true } };
		grilla = new Grilla(new GeneradorGrillaPrefijada(cargas, 3, 2));
		grilla.generarGrilla();

		caminosValidos = crearCaminos();
	}

	private List<Camino> crearCaminos() {
		List<Camino> caminosValidos = new ArrayList<Camino>();

		Camino camino1 = new Camino();
		camino1.agregarCelda(grilla.getCelda(0, 0));
		camino1.agregarCelda(grilla.getCelda(1, 0));
		camino1.agregarCelda(grilla.getCelda(2, 0));
		camino1.agregarCelda(grilla.getCelda(2, 1));
		caminosValidos.add(camino1);

		Camino camino2 = new Camino();
		camino2.agregarCelda(grilla.getCelda(0, 0));
		camino2.agregarCelda(grilla.getCelda(1, 0));
		camino2.agregarCelda(grilla.getCelda(1, 1));
		camino2.agregarCelda(grilla.getCelda(2, 1));
		caminosValidos.add(camino2);

		return caminosValidos;
	}

	@Test
	public void cantidadCaminosCorrectoTest() {
		FuerzaBruta fb = new FuerzaBruta(grilla);
		fb.buscarCaminos();

		assertEquals(fb.getCantidadCaminos(), 2);
	}

	@Test
	public void cantidadCaminosIncorrectoTest() {
		FuerzaBruta fb = new FuerzaBruta(grilla);
		fb.buscarCaminos();

		assertNotEquals(fb.getCantidadCaminos(), 1);
	}

	@Test
	public void buscarCaminosCorrectoTest() {
		FuerzaBruta fb = new FuerzaBruta(grilla);
		List<Camino> caminosEncontrados = fb.buscarCaminos();
		for (Camino camino : caminosValidos) {
			assertTrue(caminosEncontrados.contains(camino));
		}
	}

}
