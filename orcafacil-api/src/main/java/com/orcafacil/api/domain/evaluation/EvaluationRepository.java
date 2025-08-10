package com.orcafacil.api.domain.evaluation;

import java.util.Optional;

public interface EvaluationRepository {
    Optional<Evaluation> findByServiceId(Integer serviceId);
}
