package com.orcafacil.api.infrastructure.persistence.jpa.evaluation;

import com.orcafacil.api.infrastructure.persistence.entity.evaluation.EvaluationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringEvaluationRepository extends JpaRepository<EvaluationEntity, Integer> {
    Optional<EvaluationEntity> findByServiceId(Integer serviceId);
}
