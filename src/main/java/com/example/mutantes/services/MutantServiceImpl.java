package com.example.mutantes.services;

import com.example.mutantes.entities.Mutant;
import com.example.mutantes.repositories.MutantRepository;
import com.example.mutantes.repositories.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MutantServiceImpl extends BaseServiceImpl<Mutant, Long> implements MutantService {
    private final MutantRepository mutantRepository;

    @Autowired
    public MutantServiceImpl(BaseRepository<Mutant, Long> baseRepository, MutantRepository mutantRepository) {
        super(baseRepository);
        this.mutantRepository = mutantRepository;
    }

    @Override
    public Long countByMutant(boolean isMutant) {
        return mutantRepository.countByMutant(isMutant);
    }
}
