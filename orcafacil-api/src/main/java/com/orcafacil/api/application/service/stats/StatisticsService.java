package com.orcafacil.api.application.service.stats;

import com.orcafacil.api.application.service.service.BusinessServiceService;
import com.orcafacil.api.application.service.user.UserService;
import com.orcafacil.api.domain.service.ServiceStatus;
import com.orcafacil.api.interfaceadapter.response.AdminDashboardStats;
import com.orcafacil.api.interfaceadapter.response.ProviderStats;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class StatisticsService {

    // 1. Dependências corretas: apenas outros serviços, NENHUM repositório.
    private final BusinessServiceService businessService;
    private final UserService userService;

    // 2. Construtor ajustado para injetar os serviços.
    public StatisticsService(BusinessServiceService businessService, UserService userService) {
        this.businessService = businessService;
        this.userService = userService;
    }

    @Transactional(readOnly = true)
    public AdminDashboardStats getAdminDashboardStats() {
        AdminDashboardStats stats = new AdminDashboardStats();

        // 3. Chama os métodos do UserService para obter dados de usuários.
        stats.setTotalUsers(userService.countTotalUsers());
        stats.setActiveProviders(userService.countActiveProviders());

        // Esta parte precisaria de um método `countCompletedServices` em BusinessServiceService
        // para ficar 100% desacoplada, mas vamos focar em resolver o erro principal primeiro.
        // long completedServices = businessService.countServicesByStatus(ServiceStatus.COMPLETED);
        // stats.setCompletedServicesThisMonth(completedServices);

        return stats;
    }

    @Transactional(readOnly = true)
    public ProviderStats getProviderStats(Integer companyId) {
        ProviderStats stats = new ProviderStats();

        // 4. Todas as chamadas são para o BusinessServiceService, que encapsula a lógica de acesso a dados de serviço.
        long totalServices = businessService.countServicesByCompanyId(companyId);
        stats.setTotalServices(totalServices);

        List<ServiceStatus> excludedStatuses = Arrays.asList(ServiceStatus.REJECTED, ServiceStatus.REQUEST_SENT);
        long acceptedServices = businessService.countServicesByCompanyIdAndStatusNotIn(companyId, excludedStatuses);

        if (totalServices > 0) {
            stats.setAcceptanceRate(((double) acceptedServices / totalServices) * 100);
        } else {
            stats.setAcceptanceRate(0.0);
        }

        stats.setAverageRating(businessService.getAverageRatingByCompanyId(companyId));
        stats.setNewRequests(businessService.findServicesByCompanyIdAndStatus(companyId, ServiceStatus.REQUEST_SENT));

        List<ServiceStatus> acceptedStatuses = Arrays.asList(
                ServiceStatus.VISIT_CONFIRMED, ServiceStatus.DATES_CONFIRMED, ServiceStatus.IN_PROGRESS
        );
        stats.setAcceptedToday(businessService.findServicesAcceptedTodayByCompanyId(companyId, acceptedStatuses));

        List<ServiceStatus> pendingStatuses = Arrays.asList(
                ServiceStatus.NEGOTIATING_VISIT,
                ServiceStatus.NEGOTIATING_DATES,
                ServiceStatus.BUDGET_IN_NEGOTIATION,
                ServiceStatus.BUDGET_REVISION_REQUESTED
        );
        stats.setPendingServices(businessService.findServicesByCompanyIdAndStatusIn(companyId, pendingStatuses));

        return stats;
    }
}