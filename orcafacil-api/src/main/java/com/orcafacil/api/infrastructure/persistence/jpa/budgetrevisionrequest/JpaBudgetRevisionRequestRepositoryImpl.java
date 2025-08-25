package com.orcafacil.api.infrastructure.persistence.jpa.budgetrevisionrequest;

import com.orcafacil.api.domain.budgetrevisionrequest.BudgetRevisionRequest;
import com.orcafacil.api.domain.budgetrevisionrequest.BudgetRevisionRequestRepository;
import com.orcafacil.api.infrastructure.persistence.entity.budgetrevisionrequest.BudgetRevisionRequestEntity;
import com.orcafacil.api.infrastructure.persistence.mapper.budgetrevisionrequest.BudgetRevisionRequestMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class JpaBudgetRevisionRequestRepositoryImpl implements BudgetRevisionRequestRepository {

    private final SpringBudgetRevisionRequestRepository springRepository;
    private final BudgetRevisionRequestMapper mapper;

    public JpaBudgetRevisionRequestRepositoryImpl(SpringBudgetRevisionRequestRepository springRepository, BudgetRevisionRequestMapper mapper) {
        this.springRepository = springRepository;
        this.mapper = mapper;
    }

    @Override
    public BudgetRevisionRequest save(BudgetRevisionRequest budgetRevisionRequest) {
        BudgetRevisionRequestEntity entity = mapper.toEntity(budgetRevisionRequest);
        BudgetRevisionRequestEntity savedEntity = springRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public List<BudgetRevisionRequest> findByServiceId(Integer serviceId) {
        return springRepository.findByServiceId(serviceId)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}