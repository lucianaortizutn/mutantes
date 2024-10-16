package com.example.mutantes.controllers;

import com.example.mutantes.entities.Mutant;
import com.example.mutantes.services.MutantServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.util.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1")
public class MutantController extends BaseControllerImpl<Mutant, MutantServiceImpl> {

    @GetMapping("/stats")
    public ResponseEntity<?> getStats() {
        try {
            long countMutantDna = servicio.countByMutant(true);
            long countHumanDna = servicio.countByMutant(false);

            // Calcular el ratio
            double ratio = (countHumanDna == 0) ? 0 : (double) countMutantDna / countHumanDna;
            DecimalFormat df = new DecimalFormat("#.00");
            String formattedRatio = df.format(ratio);

            // Crear el objeto de respuesta
            Map<String, Object> stats = new HashMap<>();
            stats.put("count_mutant_dna", countMutantDna);
            stats.put("count_human_dna", countHumanDna);
            stats.put("ratio", formattedRatio);

            return ResponseEntity.status(HttpStatus.OK).body(stats);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al obtener las estadísticas.\"}");
        }
    }

}
