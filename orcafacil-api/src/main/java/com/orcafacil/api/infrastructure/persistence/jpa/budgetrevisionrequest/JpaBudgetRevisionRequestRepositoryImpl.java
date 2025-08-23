package com.orcafacil.api.infrastructure.persistence.jpa.budgetrevisionrequest;

import com.orcafacil.api.domain.budgetrevisionrequest.BudgetRevisionRequest;
import com.orcafacil.api.domain.budgetrevisionrequest.BudgetRevisionRequestRepository;
import com.orcafacil.api.infrastructure.persistence.mapper.budgetrevisionrequest.BudgetRevisionRequestMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class JpaBudgetRevisionRequestRepositoryImpl implements BudgetRevisionRequestRepository {

    private final SpringBudgetRevisionRequestRepository repository;
    private final BudgetRevisionRequestMapper mapper;

    public JpaBudgetRevisionRequestRepositoryImpl(SpringBudgetRevisionRequestRepository repository, BudgetRevisionRequestMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<BudgetRevisionRequest> findByServiceId(Integer serviceId) {
        return repository.findByServiceId(serviceId)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
