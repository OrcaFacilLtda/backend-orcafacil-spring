package com.orcafacil.api.infrastructure.persistence.jpa.visitnegotiation;

import com.orcafacil.api.domain.visitnegotiation.VisitNegotiation;
import com.orcafacil.api.domain.visitnegotiation.VisitNegotiationRepository;
import com.orcafacil.api.infrastructure.persistence.entity.visitnegotiation.VisitNegotiationEntity;
import com.orcafacil.api.infrastructure.persistence.mapper.visitnegotiation.VisitNegotiationMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaVisitNegotiationRepositoryImpl implements VisitNegotiationRepository {

    private final SpringVisitNegotiationRepository repository;
    private final VisitNegotiationMapper mapper;

    public JpaVisitNegotiationRepositoryImpl(SpringVisitNegotiationRepository repository, VisitNegotiationMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public VisitNegotiation save(VisitNegotiation visitNegotiation) {
        VisitNegotiationEntity entity = mapper.toEntity(visitNegotiation);
        VisitNegotiationEntity savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public void markAccepted(Integer id) {
        repository.markAccepted(id);
    }

    @Override
    public Optional<VisitNegotiation> findAcceptedByBothSides(Integer serviceId) {
        return repository.findAcceptedByBothSides(serviceId)
                .map(mapper::toDomain);
    }

    @Override
    public List<VisitNegotiation> findByServiceId(Integer serviceId) {
        return repository.findByServiceId(serviceId)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
