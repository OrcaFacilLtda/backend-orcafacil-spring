package com.orcafacil.api.domain.service;

import java.util.List;
import java.util.Optional;

public interface ServiceRepository {

    Service save(Service service);

    Optional<Service> findById(Integer id);

    List<Service> findAll();

    void deleteById(Integer id);

    List<Service> findByUserId(Integer userId);
    List<Service> findByCompanyId(Integer companyId);
    long countByCompanyId(Integer companyId);
    long countByCompanyIdAndStatusNotIn(Integer companyId, List<ServiceStatus> excludedStatuses);
    Double findAverageRatingByCompanyId(Integer companyId);
    List<Service> findByCompanyIdAndStatus(Integer companyId, ServiceStatus status);
    List<Service> findByCompanyIdAndStatusIn(Integer companyId, List<ServiceStatus> statuses);
    List<Service> findAcceptedTodayByCompanyId(Integer companyId, List<ServiceStatus> statuses);

}
