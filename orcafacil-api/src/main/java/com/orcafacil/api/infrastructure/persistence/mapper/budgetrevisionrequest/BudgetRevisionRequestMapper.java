package com.orcafacil.api.infrastructure.persistence.mapper.budgetrevisionrequest;

import com.orcafacil.api.domain.budgetrevisionrequest.BudgetRevisionRequest;
import com.orcafacil.api.infrastructure.persistence.entity.budgetrevisionrequest.BudgetRevisionRequestEntity;
import com.orcafacil.api.infrastructure.persistence.mapper.service.ServiceMapper;
import com.orcafacil.api.infrastructure.persistence.mapper.user.UserMapper;

public class BudgetRevisionRequestMapper {

    public static BudgetRevisionRequestEntity toEntity(BudgetRevisionRequest domain) {
        if (domain == null) return null;
        return new BudgetRevisionRequestEntity(
                domain.getId(),
                ServiceMapper.toEntity(domain.getService()),
                UserMapper.toEntity(domain.getClient()),
                domain.getRequestDate()
        );
    }

    public static BudgetRevisionRequest toDomain(BudgetRevisionRequestEntity entity) {
        if (entity == null) return null;
        return new BudgetRevisionRequest(
                entity.getId(),
                ServiceMapper.toDomain(entity.getService()),
                UserMapper.toDomain(entity.getClient()),
                entity.getRequestDate()
        );
    }
}
