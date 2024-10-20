package com.example.mutantes.config;

import com.example.mutantes.entities.audit.Revision;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CustomRevisionListenerTest {

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
    void testNewRevision() {
        CustomRevisionListener listener = new CustomRevisionListener();
        Revision mockRevision = Mockito.mock(Revision.class);

        listener.newRevision(mockRevision);

        assertTrue(true, "newRevision should execute without throwing an exception");
    }

    @Test
    void testNewRevisionWithNullRevisionEntity() {
        CustomRevisionListener listener = new CustomRevisionListener();

        assertThrows(ClassCastException.class, () -> {
            listener.newRevision(new Object());
        });
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
