package com.orcafacil.api.infrastructure.persistence.jpa.service;

import com.orcafacil.api.domain.service.ServiceStatus;
import com.orcafacil.api.infrastructure.persistence.entity.service.ServiceEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SpringDataServiceRepository extends JpaRepository<ServiceEntity, Integer> {

    List<ServiceEntity> findByClientId(Integer clientId);

    List<ServiceEntity> findByCompanyId(Integer companyId);

    List<ServiceEntity> findByServiceStatus(ServiceStatus status);

    boolean existsByClientIdAndServiceStatus(Integer clientId, ServiceStatus status);

    boolean existsByCompanyIdAndServiceStatus(Integer companyId, ServiceStatus status);

    @Modifying
    @Query("UPDATE ServiceEntity s SET s.serviceStatus = :status WHERE s.id = :id")
    void updateStatus(@Param("id") Integer id, @Param("status") ServiceStatus status);

    @Modifying
    @Query(value = "UPDATE service SET visit_confirmed = true, status = 'Visita Confirmada' WHERE id = :id", nativeQuery = true)
    void confirmTechnicalVisit(@Param("id") Integer id);

    @Query("SELECT s FROM ServiceEntity s WHERE s.client.id = :userId OR s.company.id = :userId")
    List<ServiceEntity> findByUserId(@Param("userId") Integer userId);

    List<ServiceEntity> findByDescriptionContainingIgnoreCase(String description);

}
