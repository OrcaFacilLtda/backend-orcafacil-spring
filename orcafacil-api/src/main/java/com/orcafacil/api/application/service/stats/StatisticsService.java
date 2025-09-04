package com.orcafacil.api.application.service.stats;

import com.orcafacil.api.application.service.service.BusinessServiceService;
import com.orcafacil.api.application.service.user.UserService;
import com.orcafacil.api.domain.provider.Provider;
import com.orcafacil.api.domain.service.Service;
import com.orcafacil.api.domain.service.ServiceStatus;
import com.orcafacil.api.domain.user.User;
import com.orcafacil.api.interfaceadapter.response.AdminChartData;
import com.orcafacil.api.interfaceadapter.response.AdminDashboardStats;
import com.orcafacil.api.interfaceadapter.response.ProviderChartData;
import com.orcafacil.api.interfaceadapter.response.ProviderStats;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class StatisticsService {

    private final BusinessServiceService businessService;
    private final UserService userService;

    public StatisticsService(BusinessServiceService businessService, UserService userService) {
        this.businessService = businessService;
        this.userService = userService;
    }

    @Transactional(readOnly = true)
    public AdminDashboardStats getAdminDashboardStats() {
        AdminDashboardStats stats = new AdminDashboardStats();
        stats.setTotalUsers(userService.countTotalUsers());
        stats.setActiveProviders(userService.countActiveProviders());

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime firstDayOfMonth = now.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime lastDayOfMonth = now.withDayOfMonth(now.toLocalDate().lengthOfMonth())
                .withHour(23).withMinute(59).withSecond(59).withNano(999_999_999);

        Date startDate = Date.from(firstDayOfMonth.atZone(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(lastDayOfMonth.atZone(ZoneId.systemDefault()).toInstant());

        long completedThisMonth = businessService.countServicesByStatusAndDateRange(ServiceStatus.COMPLETED, startDate, endDate);
        stats.setCompletedServicesThisMonth(completedThisMonth);

        return stats;
    }

    @Transactional(readOnly = true)
    public AdminChartData getAdminChartData() {
        AdminChartData chartData = new AdminChartData();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM/yyyy");

        List<User> allUsers = userService.findAll();
        if (allUsers != null && !allUsers.isEmpty()) {
            Map<String, Long> usersByMonth = allUsers.stream()
                    .filter(user -> user.getBirthDate() != null)
                    .collect(Collectors.groupingBy(
                            user -> sdf.format(user.getBirthDate()),
                            Collectors.counting()
                    ));
            chartData.setUserRegistrationLabels(new ArrayList<>(usersByMonth.keySet()));
            chartData.setUserRegistrationData(new ArrayList<>(usersByMonth.values()));
        }

        List<Service> allServices = businessService.findAll();
        if (allServices != null && !allServices.isEmpty()) {
             Map<String, Long> servicesByCategory = allServices.stream()
                    .map(service -> {
                         Provider provider = businessService.findProviderByCompanyId(service.getCompany().getId());
                        return provider;
                    })
                    .filter(provider -> provider != null && provider.getCategory() != null)
                    .collect(Collectors.groupingBy(
                            provider -> provider.getCategory().getName(),
                            Collectors.counting()
                    ));
            chartData.setServicesByCategoryLabels(new ArrayList<>(servicesByCategory.keySet()));
            chartData.setServicesByCategoryData(new ArrayList<>(servicesByCategory.values()));
        }
        return chartData;
    }

    @Transactional(readOnly = true)
    public ProviderStats getProviderStats(Integer companyId) {
        ProviderStats stats = new ProviderStats();
        long totalServices = businessService.countServicesByCompanyId(companyId);
        stats.setTotalServices(totalServices);

        List<ServiceStatus> excludedStatuses = Arrays.asList(ServiceStatus.REJECTED, ServiceStatus.REQUEST_SENT);
        long acceptedServices = businessService.countServicesByCompanyIdAndStatusNotIn(companyId, excludedStatuses);
        stats.setAcceptanceRate(totalServices > 0 ? ((double) acceptedServices / totalServices) * 100 : 0.0);

        stats.setAverageRating(businessService.getAverageRatingByCompanyId(companyId));
        stats.setNewRequests(businessService.findServicesByCompanyIdAndStatus(companyId, ServiceStatus.REQUEST_SENT));

        List<ServiceStatus> acceptedStatuses = Arrays.asList(ServiceStatus.VISIT_CONFIRMED, ServiceStatus.DATES_CONFIRMED, ServiceStatus.IN_PROGRESS);
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

    @Transactional(readOnly = true)
    public ProviderChartData getProviderChartData(Integer companyId) {
        ProviderChartData chartData = new ProviderChartData();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM/yy");
        List<Service> providerServices = businessService.findServicesByCompanyIdAndStatusIn(companyId, Arrays.asList(ServiceStatus.COMPLETED));

        if(providerServices != null && !providerServices.isEmpty()){
            Map<String, Long> servicesByMonth = providerServices.stream()
                    .collect(Collectors.groupingBy(service -> sdf.format(service.getRequestDate()), Collectors.counting()));
            chartData.setServicesByMonthLabels(new ArrayList<>(servicesByMonth.keySet()));
            chartData.setServicesByMonthData(new ArrayList<>(servicesByMonth.values()));

            Map<String, Double> earningsByMonth = providerServices.stream()
                    .filter(service -> service.getLaborCost() != null)
                    .collect(Collectors.groupingBy(service -> sdf.format(service.getRequestDate()),
                            Collectors.summingDouble(service -> service.getLaborCost().doubleValue())));
            chartData.setEarningsByMonthLabels(new ArrayList<>(earningsByMonth.keySet()));
            chartData.setEarningsByMonthData(new ArrayList<>(earningsByMonth.values()));
        }
        return chartData;
    }
}