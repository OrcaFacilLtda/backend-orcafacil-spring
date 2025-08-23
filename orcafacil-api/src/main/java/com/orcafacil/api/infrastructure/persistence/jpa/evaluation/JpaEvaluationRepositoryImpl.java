package com.orcafacil.api.infrastructure.persistence.jpa.evaluation;

import com.orcafacil.api.domain.evaluation.Evaluation;
import com.orcafacil.api.domain.evaluation.EvaluationRepository;
import com.orcafacil.api.infrastructure.persistence.mapper.evaluation.EvaluationMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaEvaluationRepositoryImpl implements EvaluationRepository {

    private final SpringEvaluationRepository repository;
    private final EvaluationMapper mapper;

    public JpaEvaluationRepositoryImpl(SpringEvaluationRepository repository, EvaluationMapper mapper) { // <-- Injeção
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Evaluation> findByServiceId(Integer serviceId) {
        return repository.findByServiceId(serviceId)
                .map(mapper::toDomain);
    }
}