package com.orcafacil.api.infrastructure.persistence.jpa.evaluation;

import com.orcafacil.api.domain.evaluation.Evaluation;
import com.orcafacil.api.domain.evaluation.EvaluationRepository;
import com.orcafacil.api.infrastructure.persistence.entity.evaluation.EvaluationEntity;
import com.orcafacil.api.infrastructure.persistence.mapper.evaluation.EvaluationMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaEvaluationRepositoryImpl implements EvaluationRepository {

    private final SpringEvaluationRepository springRepository;
    private final EvaluationMapper mapper;

    public JpaEvaluationRepositoryImpl(SpringEvaluationRepository springRepository, EvaluationMapper mapper) {
        this.springRepository = springRepository;
        this.mapper = mapper;
    }

    @Override
    public Evaluation save(Evaluation evaluation) {
        EvaluationEntity entity = mapper.toEntity(evaluation);
        EvaluationEntity savedEntity = springRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Evaluation> findByServiceId(Integer serviceId) {
        return springRepository.findByServiceId(serviceId)
                .map(mapper::toDomain);
    }
}