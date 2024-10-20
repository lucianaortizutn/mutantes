package com.example.mutantes.services;

import com.example.mutantes.entities.Mutant;
import com.example.mutantes.repositories.BaseRepository;
import com.example.mutantes.repositories.MutantRepository;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MutantServiceImplTest {

    @Mock
    private BaseRepository<Mutant, Long> baseRepository;

    @Mock
    private MutantRepository mutantRepository;

    private MutantServiceImpl mutantService;

    @BeforeAll
    public static void initAll() {
        // Se ejecuta una sola vez, antes de todas las pruebas
        System.out.println("Inicio de las pruebas");
    }

    @BeforeEach
    public void init(TestInfo testInfo) {
        // Se ejecuta antes de cada prueba
        MockitoAnnotations.openMocks(this);
        mutantService = new MutantServiceImpl(baseRepository, mutantRepository);
        System.out.println("Inicia " + testInfo.getDisplayName());
    }

    @Test
    public void testCountByMutant() {
        when(mutantRepository.countByMutant(true)).thenReturn(5L);

        Long result = mutantService.countByMutant(true);

        assertEquals(5L, result);
        verify(mutantRepository).countByMutant(true);
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
