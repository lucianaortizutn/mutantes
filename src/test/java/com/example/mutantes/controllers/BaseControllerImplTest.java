package com.example.mutantes.controllers;

import com.example.mutantes.entities.Mutant;
import com.example.mutantes.services.BaseServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BaseControllerImplTest {
    @Mock
    private BaseServiceImpl<Mutant, Long> service;

    private BaseControllerImpl<Mutant, BaseServiceImpl<Mutant, Long>> controller;

    @BeforeAll
    public static void initAll() {
        // Se ejecuta una sola vez, antes de todas las pruebas
        System.out.println("Inicio de las pruebas");
    }

    @BeforeEach
    public void init(TestInfo testInfo) throws Exception {
        // Se ejecuta antes de cada prueba
        MockitoAnnotations.openMocks(this);
        controller = new BaseControllerImpl<Mutant, BaseServiceImpl<Mutant, Long>>() {
            // Implementación concreta para la clase abstracta
        };
        controller.servicio = service;
        System.out.println("Inicia " + testInfo.getDisplayName());
    }

    @Test
    void testGetAll() throws Exception {
        List<Mutant> mutants = Arrays.asList(new Mutant(), new Mutant());
        when(service.findAll()).thenReturn(mutants);

        ResponseEntity<?> response = controller.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mutants, response.getBody());
    }

    @Test
    void testGetAllWithException() throws Exception {
        when(service.findAll()).thenThrow(new RuntimeException("Error"));

        ResponseEntity<?> response = controller.getAll();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("{\"error\":\"Error. Por favor intente más tarde.\"}", response.getBody());
    }

    @Test
    void testSave() throws Exception {
        Mutant mutant = new Mutant();
        List<String> dna = Arrays.asList("ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG");
        mutant.setDna(dna);

        when(service.existsByDna(dna)).thenReturn(false);
        when(service.save(any(Mutant.class))).thenReturn(mutant);

        ResponseEntity<?> response = controller.save(mutant);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(service).save(mutant);
    }

    @Test
    void testSaveExistingDNA() throws Exception {
        Mutant mutant = new Mutant();
        List<String> dna = Arrays.asList("ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG");
        mutant.setDna(dna);

        when(service.existsByDna(dna)).thenReturn(true);

        ResponseEntity<?> response = controller.save(mutant);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("{\"error\":\"Este ADN ya fue analizado previamente.\"}", response.getBody());
    }

    @Test
    void testIsMutant() {
        List<String> mutantDNA = Arrays.asList(
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        );
        assertTrue(controller.isMutant(mutantDNA));

        List<String> humanDNA = Arrays.asList(
                "ATGCGA",
                "CAGTGC",
                "TTATTT",
                "AGACGG",
                "GCGTCA",
                "TCACTG"
        );
        assertFalse(controller.isMutant(humanDNA));
    }

    @Test
    void testAddSequenceIfMutant() {
        // Usamos reflexión para acceder al método privado
        java.lang.reflect.Method method;
        try {
            method = BaseControllerImpl.class.getDeclaredMethod("addSequenceIfMutant", String.class, java.util.Set.class);
            method.setAccessible(true);

            java.util.Set<String> foundSequences = new java.util.HashSet<>();

            assertEquals(1, method.invoke(controller, "AAAA", foundSequences));
            assertEquals(0, method.invoke(controller, "AAAA", foundSequences)); // Ya existe
            assertEquals(0, method.invoke(controller, "ATCG", foundSequences)); // No todos iguales

        } catch (Exception e) {
            fail("Error al acceder al método privado: " + e.getMessage());
        }
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
