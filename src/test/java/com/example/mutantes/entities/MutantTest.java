package com.example.mutantes.entities;

import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MutantTest {
    private Mutant mutant;

    @BeforeAll
    public static void initAll() {
        // Se ejecuta una sola vez, antes de todas las pruebas
        System.out.println("Inicio de las pruebas");
    }

    @BeforeEach
    public void init(TestInfo testInfo) {
        // Se ejecuta antes de cada prueba
        System.out.println("Inicia " + testInfo.getDisplayName());
        List<String> dnaSample = Arrays.asList("ATCG", "CAGT", "TTAT", "AGAA");
        mutant = Mutant.builder()
                .dna(dnaSample)
                .mutant(true)
                .build();
    }

    @Test
    public void testGetDna() {
        List<String> expectedDna = Arrays.asList("ATCG", "CAGT", "TTAT", "AGAA");
        assertEquals(expectedDna, mutant.getDna());
    }

    @Test
    public void testIsMutant() {
        assertTrue(mutant.isMutant());
    }

    @Test
    public void testSetDna() {
        List<String> newDna = Arrays.asList("GATT", "ACGG", "TTAA", "CGGA");
        mutant.setDna(newDna);
        assertEquals(newDna, mutant.getDna());
    }

    @Test
    public void testSetMutant() {
        mutant.setMutant(false);
        assertFalse(mutant.isMutant());
    }

    @Test
    public void testBuilder() {
        List<String> dnaSample = Arrays.asList("GATT", "ACGG", "TTAA", "CGGA");
        Mutant newMutant = Mutant.builder()
                .dna(dnaSample)
                .mutant(false)
                .build();
        assertNotNull(newMutant);
        assertEquals(dnaSample, newMutant.getDna());
        assertFalse(newMutant.isMutant());
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
