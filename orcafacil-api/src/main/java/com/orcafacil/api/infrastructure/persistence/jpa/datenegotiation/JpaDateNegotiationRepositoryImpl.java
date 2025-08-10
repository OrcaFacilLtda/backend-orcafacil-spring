package com.orcafacil.api.infrastructure.persistence.jpa.datenegotiation;

import com.orcafacil.api.domain.datenegotiation.DateNegotiation;
import com.orcafacil.api.domain.datenegotiation.DateNegotiationRepository;
import com.orcafacil.api.infrastructure.persistence.mapper.datenegotiation.DateNegotiationMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaDateNegotiationRepositoryImpl implements DateNegotiationRepository {

    private final SpringDateNegotiationRepository repository;

    public JpaDateNegotiationRepositoryImpl(SpringDateNegotiationRepository repository) {
        this.repository = repository;
    }

    @Override
    public void markAccepted(Integer id) {
        repository.markAccepted(id);
    }

    @Override
    public Optional<DateNegotiation> findAcceptedByBothSides(Integer serviceId) {
        return repository.findAcceptedByBothSides(serviceId)
                .map(DateNegotiationMapper::toDomain);
    }

    @Override
    public List<DateNegotiation> findByServiceId(Integer serviceId) {
        return repository.findByServiceId(serviceId)
                .stream()
                .map(DateNegotiationMapper::toDomain)
                .collect(Collectors.toList());
    }
}
