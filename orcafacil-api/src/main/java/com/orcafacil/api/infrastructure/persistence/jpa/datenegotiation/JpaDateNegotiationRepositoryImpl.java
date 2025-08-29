package com.orcafacil.api.infrastructure.persistence.jpa.datenegotiation;

import com.orcafacil.api.domain.datenegotiation.DateNegotiation;
import com.orcafacil.api.domain.datenegotiation.DateNegotiationRepository;
import com.orcafacil.api.infrastructure.persistence.entity.datenegotiation.DateNegotiationEntity;
import com.orcafacil.api.infrastructure.persistence.mapper.datenegotiation.DateNegotiationMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaDateNegotiationRepositoryImpl implements DateNegotiationRepository {

    private final SpringDateNegotiationRepository repository;
    private final DateNegotiationMapper mapper;

    public JpaDateNegotiationRepositoryImpl(SpringDateNegotiationRepository repository, DateNegotiationMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public DateNegotiation save(DateNegotiation dateNegotiation) {
        DateNegotiationEntity entity = mapper.toEntity(dateNegotiation);
        DateNegotiationEntity savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public void markAccepted(Integer id) {
        repository.markAccepted(id);
    }

    @Override
    public Optional<DateNegotiation> findAcceptedByBothSides(Integer serviceId) {
        return repository.findAcceptedByBothSides(serviceId)
                .map(mapper::toDomain);
    }

    @Override
    public List<DateNegotiation> findByServiceId(Integer serviceId) {
        return repository.findByServiceId(serviceId)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
