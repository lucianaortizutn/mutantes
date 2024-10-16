package com.example.mutantes.services;

import com.example.mutantes.entities.Mutant;

public interface MutantService extends BaseService<Mutant, Long> {

    Long countByMutant(boolean mutant);
}
