package com.orcafacil.api.infrastructure.persistence.mapper.datenegotiation;

import com.orcafacil.api.domain.datenegotiation.DateNegotiation;
import com.orcafacil.api.infrastructure.persistence.entity.datenegotiation.DateNegotiationEntity;
import com.orcafacil.api.infrastructure.persistence.mapper.service.ServiceMapper;

public class DateNegotiationMapper {

    public static DateNegotiationEntity toEntity(DateNegotiation domain) {
        if (domain == null) return null;
        return new DateNegotiationEntity(
                domain.getId(),
                ServiceMapper.toEntity(domain.getService()),
                domain.getPropeser(),
                domain.getStartDate(),
                domain.getEndDate(),
                domain.getSentDate(),
                domain.getAccepted()
        );
    }

    public static DateNegotiation toDomain(DateNegotiationEntity entity) {
        if (entity == null) return null;
        return new DateNegotiation(
                entity.getId(),
                ServiceMapper.toDomain(entity.getService()),
                entity.getProposer(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getSentDate(),
                entity.getAccepted()
        );
    }
}
