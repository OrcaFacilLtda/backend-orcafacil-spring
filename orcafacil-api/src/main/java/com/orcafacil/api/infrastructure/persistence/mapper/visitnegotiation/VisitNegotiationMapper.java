package com.orcafacil.api.infrastructure.persistence.mapper.visitnegotiation;

import com.orcafacil.api.domain.visitnegotiation.VisitNegotiation;
import com.orcafacil.api.infrastructure.persistence.entity.visitnegotiation.VisitNegotiationEntity;
import com.orcafacil.api.infrastructure.persistence.mapper.service.ServiceMapper;
import org.springframework.stereotype.Component;

@Component
public class VisitNegotiationMapper {

    private final ServiceMapper serviceMapper;

    public VisitNegotiationMapper(ServiceMapper serviceMapper) { // <-- Injeção
        this.serviceMapper = serviceMapper;
    }

    public VisitNegotiationEntity toEntity(VisitNegotiation domain) {
        if (domain == null) return null;
        return new VisitNegotiationEntity(
                domain.getId(),
                serviceMapper.toEntity(domain.getService()),
                domain.getPropeser(),
                domain.getVisitDate(),
                domain.getSentDate(),
                domain.getAccept()
        );
    }

    public VisitNegotiation toDomain(VisitNegotiationEntity entity) {
        if (entity == null) return null;
        return new VisitNegotiation(
                entity.getId(),
                serviceMapper.toDomain(entity.getService()),
                entity.getProposer(),
                entity.getVisitDate(),
                entity.getSentDate(),
                entity.getAccepted()
        );
    }
}