package test;

import static org.junit.Assert.*;

import org.junit.Test;

import logica.FuerzaBruta;
import logica.Grilla;

public class FuerzaBrutaTest {

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
		FuerzaBruta fb = new FuerzaBruta(grilla);
		fb.buscarCaminos();
		
		assertEquals(fb.getCantidadCaminos(), 3);
	}
	
	@Test
	public void cantidadCaminosIncorrectoTest() {
		Grilla grilla = generarGrilla();
		FuerzaBruta fb = new FuerzaBruta(grilla);
		fb.buscarCaminos();
		
		assertNotEquals(fb.getCantidadCaminos(), 2);
	}

	@Test
	public void cantidadLlamadasCorrectasTest() {
		Grilla grilla = generarGrilla();
		FuerzaBruta fb = new FuerzaBruta(grilla);
		fb.buscarCaminos();
		
		assertEquals(fb.getCantidadLlamadas(), 34);
	}
	
	@Test
	public void cantidadLlamadasIncorrectasTest() {
		Grilla grilla = generarGrilla();
		FuerzaBruta fb = new FuerzaBruta(grilla);
		fb.buscarCaminos();
		
		assertNotEquals(fb.getCantidadLlamadas(), 33);
	}
	
	// testear caminos encontrados

}
