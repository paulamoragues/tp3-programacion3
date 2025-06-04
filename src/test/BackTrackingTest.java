package test;

import static org.junit.Assert.*;

import org.junit.Test;

import logica.BackTracking;
import logica.Grilla;

public class BackTrackingTest {
	
	public Grilla generarGrilla() {
		boolean[][] cargas = { 
				{ true, false, false, false }, 
				{ false, true, true, false }, 
				{ true, true, true, false } 
		};
		
		Grilla grilla = new Grilla(3, 4);
		grilla.generarGrillaPrefijada(cargas);
		return grilla;
	}

	@Test
	public void cantidadCaminosCorrectoTest() {
		Grilla grilla = generarGrilla();
		BackTracking bt = new BackTracking(grilla);
		bt.buscarCaminos();
		
		assertEquals(bt.getCantidadCaminos(), 3);
	}
	
	@Test
	public void cantidadCaminosIncorrectoTest() {
		Grilla grilla = generarGrilla();
		BackTracking bt = new BackTracking(grilla);
		bt.buscarCaminos();
		
		assertNotEquals(bt.getCantidadCaminos(), 2);
	}

	@Test
	public void cantidadLlamadasCorrectasTest() {
		Grilla grilla = generarGrilla();
		BackTracking bt = new BackTracking(grilla);
		bt.buscarCaminos();
		
		assertEquals(bt.getCantidadLlamadas(), 28);
	}
	
	@Test
	public void cantidadLlamadasIncorrectasTest() {
		Grilla grilla = generarGrilla();
		BackTracking bt = new BackTracking(grilla);
		bt.buscarCaminos();
		
		assertNotEquals(bt.getCantidadLlamadas(), 27);
	}
	
	// testear caminos encontrados

}
