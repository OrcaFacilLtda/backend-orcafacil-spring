package com.orcafacil.api.infrastructure.persistence.jpa.service;

import com.orcafacil.api.domain.service.Service;
import com.orcafacil.api.domain.service.ServiceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import com.orcafacil.api.infrastructure.persistence.mapper.service.ServiceMapper;

import java.util.stream.Collectors;

@Repository
public class JpaServiceRepositoryImpl implements ServiceRepository {

    private final SpringDataServiceRepository repository;

    public JpaServiceRepositoryImpl(SpringDataServiceRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Service> findByClientId(Integer clientId) {
        return repository.findByClientId(clientId)
                .stream()
                .map(ServiceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Service> findByCompanyId(Integer companyId) {
        return repository.findByCompanyId(companyId)
                .stream()
                .map(ServiceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Service> findByStatus(String status) {
        return repository.findByStatus(status)
                .stream()
                .map(ServiceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Service> findById(Integer id) {
        return repository.findById(id)
                .map(ServiceMapper::toDomain);
    }

    @Override
    public boolean existsByClientIdAndStatus(Integer clientId, String status) {
        return repository.existsByClientIdAndStatus(clientId, status);
    }

    @Override
    public boolean existsByCompanyIdAndStatus(Integer companyId, String status) {
        return repository.existsByCompanyIdAndStatus(companyId, status);
    }

    @Override
    public void updateStatus(Integer id, String status) {
        repository.updateStatus(id, status);
    }

    @Override
    public void confirmTechnicalVisit(Integer id) {
        repository.confirmTechnicalVisit(id);
    }
}
