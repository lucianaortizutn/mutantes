package com.example.mutantes.services;

import com.example.mutantes.entities.Base;
import com.example.mutantes.repositories.BaseRepository;
import com.example.mutantes.repositories.MutantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BaseServiceImplTest {

    @Mock
    private BaseRepository<TestEntity, Long> baseRepository;

    @Mock
    private MutantRepository mutantRepository;

    private BaseServiceImpl<TestEntity, Long> baseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        baseService = new BaseServiceImpl<TestEntity, Long>(baseRepository) {
            // Implementación concreta para la clase abstracta
        };
        baseService.mutantRepository = mutantRepository;
    }

    @Test
    void testExistsByDna() {
        List<String> dna = new ArrayList<>();
        when(mutantRepository.existsByDna(dna)).thenReturn(true);

        assertTrue(baseService.existsByDna(dna));
        verify(mutantRepository).existsByDna(dna);
    }

    @Test
    void testFindAll() throws Exception {
        List<TestEntity> entities = new ArrayList<>();
        entities.add(new TestEntity());
        when(baseRepository.findAll()).thenReturn(entities);

        List<TestEntity> result = baseService.findAll();

        assertEquals(entities, result);
        verify(baseRepository).findAll();
    }

    @Test
    void testFindAllWithException() {
        when(baseRepository.findAll()).thenThrow(new RuntimeException("Error"));

        Exception exception = assertThrows(Exception.class, () -> baseService.findAll());
        assertEquals("Error", exception.getMessage());
    }

    @Test
    void testSave() throws Exception {
        TestEntity entity = new TestEntity();
        when(baseRepository.save(entity)).thenReturn(entity);

        TestEntity result = baseService.save(entity);

        assertEquals(entity, result);
        verify(baseRepository).save(entity);
    }

    @Test
    void testSaveWithException() {
        TestEntity entity = new TestEntity();
        when(baseRepository.save(entity)).thenThrow(new RuntimeException("Error al guardar"));

        Exception exception = assertThrows(Exception.class, () -> baseService.save(entity));
        assertEquals("Error al guardar", exception.getMessage());
    }

    // Clase de entidad de prueba
    private static class TestEntity extends Base {
        // Implementación mínima para la prueba
    }
}