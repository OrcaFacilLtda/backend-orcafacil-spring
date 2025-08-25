package com.orcafacil.api.domain.budgetrevisionrequest;

import java.util.List;

public interface BudgetRevisionRequestRepository {
    List<BudgetRevisionRequest> findByServiceId(Integer serviceId);
    BudgetRevisionRequest save(BudgetRevisionRequest budgetRevisionRequest); // <-- MÉTODO ADICIONADO

}
