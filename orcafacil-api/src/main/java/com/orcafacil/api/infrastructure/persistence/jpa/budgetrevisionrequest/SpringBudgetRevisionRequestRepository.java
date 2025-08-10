package com.orcafacil.api.infrastructure.persistence.jpa.budgetrevisionrequest;

import com.orcafacil.api.infrastructure.persistence.entity.budgetrevisionrequest.BudgetRevisionRequestEntity;
import com.orcafacil.api.infrastructure.persistence.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SpringBudgetRevisionRequestRepository extends JpaRepository<BudgetRevisionRequestEntity, Integer> {
    List<BudgetRevisionRequestEntity> findByServiceId(Integer serviceId);
}
