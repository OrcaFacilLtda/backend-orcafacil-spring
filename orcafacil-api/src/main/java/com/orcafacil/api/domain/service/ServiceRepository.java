package com.orcafacil.api.domain.service;

import java.util.List;
import java.util.Optional;


public interface ServiceRepository {
    List<Service> findByClientId(Integer clientId);
    List<Service> findByCompanyId(Integer companyId);
    List<Service> findByStatus(String status);
    Optional<Service> findById(Integer id);
    boolean existsByClientIdAndStatus(Integer clientId, String status);
    boolean existsByCompanyIdAndStatus(Integer companyId, String status);
    void updateStatus(Integer id, String status);
    void confirmTechnicalVisit(Integer id);
}