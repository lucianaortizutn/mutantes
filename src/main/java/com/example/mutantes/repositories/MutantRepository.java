package com.example.mutantes.repositories;

import com.example.mutantes.entities.Mutant;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MutantRepository extends BaseRepository<Mutant, Long> {
    boolean existsByDna(List<String> dna);
    Long countByMutant(boolean mutant);
}
