package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import generador.GeneradorPrefijado;
import logica.Camino;
import logica.Grilla;
import logica.Individuo;

public class IndividuoTest {

	private Grilla _grilla;
	private boolean[][] cargas;

	@Before
	public void inicializar() {
		generarGrilla();
	}

	@Test
	public void generarCaminoCorrectoTest() {
		Individuo individuo = crearIndividuo("11100");
		Camino camino = individuo.generarCamino();
		assertTrue(camino.esCaminoValido(_grilla));
	}

	@Test
	public void generarCaminoConSumaIncorrectaTest() {
		Individuo individuo = crearIndividuo("10110");
		Camino camino = individuo.generarCamino();
		assertFalse(camino.esCaminoValido(_grilla));
	}

	@Test
	public void generarCaminoDestinoIncorrectoTest() {
		Individuo individuo = crearIndividuo("10111");
		Camino camino = individuo.generarCamino();
		assertFalse(camino.esCaminoValido(_grilla));
	}

	@Test
	public void fitnessSumaCorrectaTest() {
		Individuo individuo = crearIndividuo("11100");
		assertEquals(0, individuo.fitness());
	}

	@Test
	public void fitnessExcedidoTest() {
		Individuo individuo = crearIndividuo("01110");
		assertEquals(4, individuo.fitness());
	}

	@Test
	public void fitnessSumaExcedPorDosTest() {
		Individuo individuo = crearIndividuo("10011");
		assertEquals(2, individuo.fitness());
	}

	@Test
	public void mutarPrimerosTest() {
		Individuo individuo = crearIndividuo("01010");
		mutar(individuo, 0, 1);
		assertIndividuo("10010", individuo);
	}

	@Test
	public void mutarUltimosTest() {
		Individuo individuo = crearIndividuo("01011");
		mutar(individuo, 4, 3);
		assertIndividuo("01000", individuo);
	}

	@Test
	public void mutarCerosTest() {
		Individuo individuo = crearIndividuo("00000");
		mutar(individuo, 2, 3);
		assertIndividuo("00110", individuo);
	}

	@Test
	public void mutarTest() {
		Individuo individuo = crearIndividuo("01010");
		mutar(individuo, 0, 4);
		assertIndividuo("11011", individuo);
	}

	@Test
	public void recombinarAlInicioTest() {
		Individuo individuo1 = crearIndividuo("11111");
		Individuo individuo2 = crearIndividuo("00000");
		Individuo[] hijos = recombinar(individuo1, individuo2, 0);
		assertIndividuo("00000", hijos[0]);
		assertIndividuo("11111", hijos[1]);
	}

	@Test
	public void recombinarAlFinalTest() {
		Individuo individuo1 = crearIndividuo("11111");
		Individuo individuo2 = crearIndividuo("00000");
		Individuo[] hijos = recombinar(individuo1, individuo2, 4);
		assertIndividuo("11110", hijos[0]);
		assertIndividuo("00001", hijos[1]);
	}

	@Test
	public void recombinarTest() {
		Individuo individuo1 = crearIndividuo("11111");
		Individuo individuo2 = crearIndividuo("00000");
		Individuo[] hijos = recombinar(individuo1, individuo2, 3);
		assertIndividuo("11100", hijos[0]);
		assertIndividuo("00011", hijos[1]);
	}

	private Individuo crearIndividuo(String str) {
		Individuo.setGenerador(new GeneradorPrefijado(str));
		Individuo individuo = Individuo.aleatorio(_grilla);
		return individuo;
	}

	private void mutar(Individuo individuo, int bit1, int bit2) {
		Individuo.setGenerador(new GeneradorPrefijado(bit1, bit2));
		individuo.mutar();
	}

	private Individuo[] recombinar(Individuo individuo1, Individuo individuo2, int bit) {
		Individuo.setGenerador(new GeneradorPrefijado(bit));
		return individuo1.recombinar(individuo2);
	}

	private void assertIndividuo(String str, Individuo individuo) {
		for (int i = 0; i < str.length(); i++)
			assertEquals(str.charAt(i) == '1', individuo.get(i));
	}

	public void generarGrilla() {
		_grilla = new Grilla(3, 4);
		cargas = new boolean[3][4];
		cargas[0][0] = true;
		cargas[0][1] = true;
		cargas[0][2] = true;
		cargas[0][3] = false;
		cargas[1][0] = false;
		cargas[1][1] = false;
		cargas[1][2] = false;
		cargas[1][3] = false;
		cargas[2][0] = true;
		cargas[2][1] = true;
		cargas[2][2] = true;
		cargas[2][3] = false;
		_grilla.generarGrillaPrefijada(cargas);
	}

}
