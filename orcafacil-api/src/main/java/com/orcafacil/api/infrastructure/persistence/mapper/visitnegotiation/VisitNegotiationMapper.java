package com.orcafacil.api.infrastructure.persistence.mapper.visitnegotiation;

import com.orcafacil.api.domain.visitnegotiation.VisitNegotiation;
import com.orcafacil.api.infrastructure.persistence.entity.visitnegotiation.VisitNegotiationEntity;
import com.orcafacil.api.infrastructure.persistence.mapper.service.ServiceMapper; // caso use

public class VisitNegotiationMapper {

    public static VisitNegotiationEntity toEntity(VisitNegotiation domain) {
        if (domain == null) return null;


        return new VisitNegotiationEntity(
                domain.getId(),
                ServiceMapper.toEntity(domain.getService()),
                domain.getPropeser(),
                domain.getVisitDate(),
                domain.getSentDate(),
                domain.getAccept()
        );
    }

    public static VisitNegotiation toDomain(VisitNegotiationEntity entity) {
        if (entity == null) return null;

        return new VisitNegotiation(
                entity.getId(),
                ServiceMapper.toDomain(entity.getService()),
                entity.getProposer(),
                entity.getVisitDate(),
                entity.getSentDate(),
                entity.getAccepted()
        );
    }
}
