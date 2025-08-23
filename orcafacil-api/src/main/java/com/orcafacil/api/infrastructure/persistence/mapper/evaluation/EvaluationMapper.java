package com.orcafacil.api.infrastructure.persistence.mapper.evaluation;

import com.orcafacil.api.domain.evaluation.Evaluation;
import com.orcafacil.api.infrastructure.persistence.entity.evaluation.EvaluationEntity;
import com.orcafacil.api.infrastructure.persistence.mapper.service.ServiceMapper;
import org.springframework.stereotype.Component;

@Component // <-- Adicionado
public class EvaluationMapper {

    private final ServiceMapper serviceMapper; // <-- Dependência

    public EvaluationMapper(ServiceMapper serviceMapper) { // <-- Injeção
        this.serviceMapper = serviceMapper;
    }

    public EvaluationEntity toEntity(Evaluation domain) { // <-- 'static' removido
        if (domain == null) return null;
        return new EvaluationEntity(
                domain.getId(),
                serviceMapper.toEntity(domain.getService()), // <-- Chamada de instância
                domain.getStars(),
                domain.getEvaluationDate()
        );
    }

    public Evaluation toDomain(EvaluationEntity entity) { // <-- 'static' removido
        if (entity == null) return null;
        return new Evaluation(
                entity.getId(),
                serviceMapper.toDomain(entity.getService()), // <-- Chamada de instância
                entity.getStars(),
                entity.getEvaluationDate()
        );
    }
}