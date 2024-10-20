package com.example.mutantes;

import com.example.mutantes.config.CustomRevisionListener;
import com.example.mutantes.controllers.BaseControllerImpl;
import com.example.mutantes.entities.Mutant;
import com.example.mutantes.entities.audit.Revision;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class MutantesApplicationTests {
	@Autowired
	private BaseControllerImpl controller;

	@BeforeAll
	public static void initAll() {
		// Se ejecuta una sola vez, antes de todas las pruebas
		System.out.println("Inicio de las pruebas");
	}

	@BeforeEach
	public void init(TestInfo testInfo) {
		// Se ejecuta antes de cada prueba
		System.out.println("Inicia " + testInfo.getDisplayName());
	}

	// tests funcionalidad
	@Test
	public void testIsMutant() {
		// Caso donde el ADN es mutante
		List<String> mutantDna = Arrays.asList(
				"ATGCGA",
				"CAGTGC",
				"TTATGT",
				"AGAAGG",
				"CCCCTA",
				"TCACTG"
		);
		assertTrue(controller.isMutant(mutantDna), "El ADN debería ser mutante");
		System.out.println(controller.isMutant(mutantDna));
	}

	@Test
	public void testIsNotMutant() {
		// Caso donde el ADN no es mutante
		List<String> humanDna = Arrays.asList(
				"AAAT",
				"AACC",
				"AAAC",
				"CGGG"
		);
		assertFalse(controller.isMutant(humanDna), "El ADN no debería ser mutante");
		System.out.println(controller.isMutant(humanDna));
	}

	// tests MutantesApplication

	@Test
	void contextLoads() {
		// Test para ver si inicializa correctamente la aplicación
	}

	@Test
	void mainMethodStartsApplication() {
		MutantesApplication.main(new String[]{});
	}

	@AfterEach
	public void tearDown(TestInfo testInfo) {
		// Se ejecuta después de cada prueba
		System.out.println("Finaliza " + testInfo.getDisplayName());
	}

	@AfterAll
	public static void tearDownAll() {
		// Se ejecuta una sola vez, al final de todas las pruebas
		System.out.println("Finalización de todas las pruebas");
	}


}
