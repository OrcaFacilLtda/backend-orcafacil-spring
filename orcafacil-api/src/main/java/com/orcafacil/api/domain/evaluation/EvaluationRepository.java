package com.orcafacil.api.domain.evaluation;

import java.util.Optional;

public interface EvaluationRepository {
    Evaluation save(Evaluation evaluation);
    Optional<Evaluation> findByServiceId(Integer serviceId);
}
