package com.orcafacil.api.infrastructure.persistence.jpa.service;

import com.orcafacil.api.domain.service.Service;
import com.orcafacil.api.domain.service.ServiceRepository;
import com.orcafacil.api.domain.service.ServiceStatus;
import com.orcafacil.api.infrastructure.persistence.entity.service.ServiceEntity;
import com.orcafacil.api.infrastructure.persistence.mapper.service.ServiceMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@org.springframework.stereotype.Repository
public class JpaServiceRepositoryImpl implements ServiceRepository {

    private final SpringDataServiceRepository repository;
    private final ServiceMapper mapper;

    public JpaServiceRepositoryImpl(SpringDataServiceRepository repository, ServiceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Service save(Service service) {
        ServiceEntity entity = mapper
                .toEntity(service);
        return mapper
                .toDomain(repository.save(entity));
    }

    @Override
    public Optional<Service> findById(Integer id) {
        return repository
                .findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Service> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Integer id) {}

    @Override
    public List<Service> findByUserId(Integer userId) {
        return repository
                .findByClientId(userId)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public long countByCompanyId(Integer companyId) {
        return repository.countByCompanyId(companyId);
    }

    @Override
    public long countByCompanyIdAndStatusNotIn(Integer companyId, List<ServiceStatus> excludedStatuses) {
        return repository.countByCompanyIdAndStatusNotIn(companyId, excludedStatuses);
    }

    @Override
    public Double findAverageRatingByCompanyId(Integer companyId) {
        return repository.findAverageRatingByCompanyId(companyId);
    }

    @Override
    public List<Service> findByCompanyIdAndStatus(Integer companyId, ServiceStatus status) {
        return repository.findByCompanyIdAndStatus(companyId, status)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Service> findByCompanyIdAndStatusIn(Integer companyId, List<ServiceStatus> statuses) {
        return repository.findByCompanyIdAndStatusIn(companyId, statuses)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Service> findAcceptedTodayByCompanyId(Integer companyId, List<ServiceStatus> statuses) {
        return repository.findByCompanyIdAndStatusInAndDate(companyId, statuses)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

}
