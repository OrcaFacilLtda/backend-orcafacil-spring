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

    public JpaBudgetRevisionRequestRepositoryImpl(SpringBudgetRevisionRequestRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<BudgetRevisionRequest> findByServiceId(Integer serviceId) {
        return repository.findByServiceId(serviceId)
                .stream()
                .map(BudgetRevisionRequestMapper::toDomain)
                .collect(Collectors.toList());
    }
}
