package com.orcafacil.api.infrastructure.persistence.mapper.evaluation;

import com.orcafacil.api.domain.evaluation.Evaluation;
import com.orcafacil.api.infrastructure.persistence.entity.evaluation.EvaluationEntity;
import com.orcafacil.api.infrastructure.persistence.mapper.service.ServiceMapper;

public class EvaluationMapper {

    public static EvaluationEntity toEntity(Evaluation domain) {
        if (domain == null) return null;
        return new EvaluationEntity(
                domain.getId(),
                ServiceMapper.toEntity(domain.getService()),
                domain.getStars(),
                domain.getEvaluationDate()
        );
    }

    public static Evaluation toDomain(EvaluationEntity entity) {
        if (entity == null) return null;
        return new Evaluation(
                entity.getId(),
                ServiceMapper.toDomain(entity.getService()),
                entity.getStars(),
                entity.getEvaluationDate()
        );
    }
}
