package com.orcafacil.api.infrastructure.persistence.jpa.visitnegotiation;

import com.orcafacil.api.domain.visitnegotiation.VisitNegotiation;
import com.orcafacil.api.domain.visitnegotiation.VisitNegotiationRepository;
import com.orcafacil.api.infrastructure.persistence.mapper.visitnegotiation.VisitNegotiationMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaVisitNegotiationRepositoryImpl implements VisitNegotiationRepository {

    private final SpringVisitNegotiationRepository repository;

    public JpaVisitNegotiationRepositoryImpl(SpringVisitNegotiationRepository repository) {
        this.repository = repository;
    }

    @Override
    public void markAccepted(Integer id) {
        repository.markAccepted(id);
    }

    @Override
    public Optional<VisitNegotiation> findAcceptedByBothSides(Integer serviceId) {
        return repository.findAcceptedByBothSides(serviceId)
                .map(VisitNegotiationMapper::toDomain);
    }

    @Override
    public List<VisitNegotiation> findByServiceId(Integer serviceId) {
        return repository.findByServiceId(serviceId)
                .stream()
                .map(VisitNegotiationMapper::toDomain)
                .collect(Collectors.toList());
    }
}
