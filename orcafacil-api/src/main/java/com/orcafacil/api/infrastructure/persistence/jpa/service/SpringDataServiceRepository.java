package com.orcafacil.api.infrastructure.persistence.jpa.service;

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

    List<ServiceEntity> findByStatus(String status);

    boolean existsByClientIdAndStatus(Integer clientId, String status);

    boolean existsByCompanyIdAndStatus(Integer companyId, String status);

    @Modifying
    @Query(value = "UPDATE service SET status = :status WHERE id = :id", nativeQuery = true)
    void updateStatus(@Param("id") Integer id, @Param("status") String status);

    @Modifying
    @Query(value = "UPDATE service SET visit_confirmed = true, status = 'Visita Confirmada' WHERE id = :id", nativeQuery = true)
    void confirmTechnicalVisit(@Param("id") Integer id);
}