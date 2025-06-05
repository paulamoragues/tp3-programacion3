package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import generador.GeneradorGrillaPrefijada;
import logica.BackTracking;
import logica.Camino;
import logica.Grilla;

public class BackTrackingTest {
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
		BackTracking bt = new BackTracking(grilla);
		bt.buscarCaminos();

		assertEquals(bt.getCantidadCaminos(), 2);
	}

	@Test
	public void cantidadCaminosIncorrectoTest() {
		BackTracking bt = new BackTracking(grilla);
		bt.buscarCaminos();

		assertNotEquals(bt.getCantidadCaminos(), 1);
	}

	@Test
	public void buscarCaminosCorrectoTest() {
		BackTracking bt = new BackTracking(grilla);
		List<Camino> caminosEncontrados = bt.buscarCaminos();
		for (Camino camino : caminosValidos) {
			assertTrue(caminosEncontrados.contains(camino));
		}
	}

}
