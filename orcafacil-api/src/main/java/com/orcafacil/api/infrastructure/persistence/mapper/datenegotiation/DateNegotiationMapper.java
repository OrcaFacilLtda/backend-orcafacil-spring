package com.orcafacil.api.infrastructure.persistence.mapper.datenegotiation;

import com.orcafacil.api.domain.datenegotiation.DateNegotiation;
import com.orcafacil.api.infrastructure.persistence.entity.datenegotiation.DateNegotiationEntity;
import com.orcafacil.api.infrastructure.persistence.mapper.service.ServiceMapper;
import org.springframework.stereotype.Component;

@Component // <-- Adicionado
public class DateNegotiationMapper {

    private final ServiceMapper serviceMapper; // <-- Dependência

    public DateNegotiationMapper(ServiceMapper serviceMapper) { // <-- Injeção
        this.serviceMapper = serviceMapper;
    }

    public DateNegotiationEntity toEntity(DateNegotiation domain) { // <-- 'static' removido
        if (domain == null) return null;
        return new DateNegotiationEntity(
                domain.getId(),
                serviceMapper.toEntity(domain.getService()), // <-- Chamada de instância
                domain.getPropeser(),
                domain.getStartDate(),
                domain.getEndDate(),
                domain.getSentDate(),
                domain.getAccepted()
        );
    }

    public DateNegotiation toDomain(DateNegotiationEntity entity) { // <-- 'static' removido
        if (entity == null) return null;
        return new DateNegotiation(
                entity.getId(),
                serviceMapper.toDomain(entity.getService()), // <-- Chamada de instância
                entity.getProposer(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getSentDate(),
                entity.getAccepted()
        );
    }
}