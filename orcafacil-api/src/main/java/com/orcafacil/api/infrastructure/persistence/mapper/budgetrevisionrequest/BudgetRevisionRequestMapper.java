package com.orcafacil.api.infrastructure.persistence.mapper.budgetrevisionrequest;

import com.orcafacil.api.domain.budgetrevisionrequest.BudgetRevisionRequest;
import com.orcafacil.api.infrastructure.persistence.entity.budgetrevisionrequest.BudgetRevisionRequestEntity;
import com.orcafacil.api.infrastructure.persistence.mapper.service.ServiceMapper;
import com.orcafacil.api.infrastructure.persistence.mapper.user.UserMapper;
import org.springframework.stereotype.Component;

@Component // <-- Adicionado
public class BudgetRevisionRequestMapper {

    private final ServiceMapper serviceMapper; // <-- Dependência
    // UserMapper pode continuar static pois não tem dependências

    public BudgetRevisionRequestMapper(ServiceMapper serviceMapper) { // <-- Injeção
        this.serviceMapper = serviceMapper;
    }

    public BudgetRevisionRequestEntity toEntity(BudgetRevisionRequest domain) { // <-- 'static' removido
        if (domain == null) return null;
        return new BudgetRevisionRequestEntity(
                domain.getId(),
                serviceMapper.toEntity(domain.getService()), // <-- Chamada de instância
                UserMapper.toEntity(domain.getClient()),
                domain.getRequestDate()
        );
    }

    public BudgetRevisionRequest toDomain(BudgetRevisionRequestEntity entity) { // <-- 'static' removido
        if (entity == null) return null;
        return new BudgetRevisionRequest(
                entity.getId(),
                serviceMapper.toDomain(entity.getService()), // <-- Chamada de instância
                UserMapper.toDomain(entity.getClient()),
                entity.getRequestDate()
        );
    }
}