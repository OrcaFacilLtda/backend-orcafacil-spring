package com.orcafacil.api.infrastructure.persistence.jpa.evaluation;

import com.orcafacil.api.domain.evaluation.Evaluation;
import com.orcafacil.api.domain.evaluation.EvaluationRepository;
import com.orcafacil.api.infrastructure.persistence.mapper.evaluation.EvaluationMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaEvaluationRepositoryImpl implements EvaluationRepository {

    private final SpringEvaluationRepository repository;

    public JpaEvaluationRepositoryImpl(SpringEvaluationRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Evaluation> findByServiceId(Integer serviceId) {
        return repository.findByServiceId(serviceId)
                .map(EvaluationMapper::toDomain);
    }
}
