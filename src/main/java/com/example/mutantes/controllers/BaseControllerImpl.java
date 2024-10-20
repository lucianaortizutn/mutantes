package com.example.mutantes.controllers;

import com.example.mutantes.entities.Mutant;
import com.example.mutantes.services.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class BaseControllerImpl<E extends Mutant, S extends BaseServiceImpl<E, Long>> implements BaseController<E, Long> {
    @Autowired
    protected S servicio;

    @GetMapping("/mutant")
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
        }
    }

    @PostMapping("/mutant")
    public ResponseEntity<?> save(@RequestBody E entity){
        try {
            if (servicio.existsByDna(entity.getDna())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("{\"error\":\"Este ADN ya fue analizado previamente.\"}");
            }
            List<String> dna = entity.getDna();
            boolean mutant = isMutant(dna);
            entity.setMutant(mutant);

            Mutant savedEntity = servicio.save(entity);

            HttpStatus status = mutant ? HttpStatus.OK : HttpStatus.FORBIDDEN;

            return ResponseEntity.status(status).body(servicio.save(entity));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error al analizar ADN.\"}");
        }
    }

    private static final int SEQUENCE_LENGTH = 4;

    public boolean isMutant(List<String> dna) {
        int sequencesFound = checkMutantDNA(dna);
        return sequencesFound > 1; // Retorna true si hay más de una secuencia encontrada
    }

    private int checkMutantDNA(List<String> dna) {
        int n = dna.size();
        int sequenceCount = 0;

        Set<String> foundSequences = new HashSet<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (j <= n - SEQUENCE_LENGTH) {
                    // Verificación horizontal
                    String horizontal = dna.get(i).substring(j, j + SEQUENCE_LENGTH);
                    sequenceCount += addSequenceIfMutant(horizontal, foundSequences);
                }
                if (i <= n - SEQUENCE_LENGTH) {
                    // Verificación vertical
                    String vertical = getVerticalSequence(dna, i, j);
                    sequenceCount += addSequenceIfMutant(vertical, foundSequences);
                }
                if (i <= n - SEQUENCE_LENGTH && j <= n - SEQUENCE_LENGTH) {
                    // Verificación diagonal (izquierda a derecha)
                    String diagonalRight = getDiagonalSequence(dna, i, j, true);
                    sequenceCount += addSequenceIfMutant(diagonalRight, foundSequences);
                }
                if (i <= n - SEQUENCE_LENGTH && j >= SEQUENCE_LENGTH - 1) {
                    // Verificación diagonal (derecha a izquierda)
                    String diagonalLeft = getDiagonalSequence(dna, i, j, false);
                    sequenceCount += addSequenceIfMutant(diagonalLeft, foundSequences);
                }
            }
        }
        return sequenceCount;
    }

    // Verifica si la secuencia contiene 4 caracteres iguales consecutivos
    private int addSequenceIfMutant(String sequence, Set<String> foundSequences) {
        if (!foundSequences.contains(sequence) && hasAllEqualCharacters(sequence)) {
            foundSequences.add(sequence);
            return 1; // Devuelve 1 si encuentra una secuencia válida
        }
        return 0;
    }

    private String getVerticalSequence(List<String> dna, int row, int col) {
        StringBuilder vertical = new StringBuilder();
        for (int i = 0; i < SEQUENCE_LENGTH; i++) {
            vertical.append(dna.get(row + i).charAt(col));
        }
        return vertical.toString();
    }

    private String getDiagonalSequence(List<String> dna, int row, int col, boolean right) {
        StringBuilder diagonal = new StringBuilder();
        for (int i = 0; i < SEQUENCE_LENGTH; i++) {
            if (right) {
                diagonal.append(dna.get(row + i).charAt(col + i));
            } else {
                diagonal.append(dna.get(row + i).charAt(col - i));
            }
        }
        return diagonal.toString();
    }

    // Verifica si todos los caracteres en la secuencia son iguales
    private boolean hasAllEqualCharacters(String sequence) {
        char firstChar = sequence.charAt(0);
        for (int i = 1; i < sequence.length(); i++) {
            if (sequence.charAt(i) != firstChar) {
                return false;
            }
        }
        return true;
    }
}
