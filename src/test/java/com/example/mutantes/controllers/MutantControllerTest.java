package com.example.mutantes.controllers;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.Mockito.when;

import com.example.mutantes.services.MutantServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;


@WebMvcTest(MutantController.class)
@ExtendWith(MockitoExtension.class)
public class MutantControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MutantServiceImpl mutantService;

    @InjectMocks
    private MutantController mutantController;

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

    @Test
    public void testGetStats() throws Exception {
        when(mutantService.countByMutant(true)).thenReturn(40L);
        when(mutantService.countByMutant(false)).thenReturn(100L);

        mockMvc.perform(get("/api/v1/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count_mutant_dna").value(40))
                .andExpect(jsonPath("$.count_human_dna").value(100))
                .andExpect(jsonPath("$.ratio").value(",40"));
    }

    @Test
    public void testGetStatsInternalServerError() throws Exception {
        doThrow(new RuntimeException("Simulated service error")).when(mutantService).countByMutant(true);

        mockMvc.perform(get("/api/v1/stats"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("Error al obtener las estadísticas."));
    }

    @Test
    public void testGetStatsZeroHumanDna() throws Exception {
        when(mutantService.countByMutant(true)).thenReturn(40L);
        when(mutantService.countByMutant(false)).thenReturn(0L);

        mockMvc.perform(get("/api/v1/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count_mutant_dna").value(40))
                .andExpect(jsonPath("$.count_human_dna").value(0))
                .andExpect(jsonPath("$.ratio").value(",00"));
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
