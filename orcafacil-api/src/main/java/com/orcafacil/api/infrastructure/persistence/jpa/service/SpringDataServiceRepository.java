package com.orcafacil.api.infrastructure.persistence.jpa.service;

import com.orcafacil.api.domain.service.ServiceStatus;
import com.orcafacil.api.infrastructure.persistence.entity.service.ServiceEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataServiceRepository extends JpaRepository<ServiceEntity, Integer> {

    List<ServiceEntity> findByClientId(Integer clientId);

    List<ServiceEntity> findByCompanyId(Integer companyId);

    List<ServiceEntity> findByStatus(ServiceStatus status);

    boolean existsByClientIdAndStatus(Integer clientId, ServiceStatus status);

    boolean existsByCompanyIdAndStatus(Integer companyId, ServiceStatus status);

    @Modifying
    @Query("UPDATE ServiceEntity s SET s.status = :status WHERE s.id = :id")
    void updateStatus(@Param("id") Integer id, @Param("status") ServiceStatus status);

    @Modifying
    @Query(value = "UPDATE service SET visit_confirmed = true, status = 'Visita Confirmada' WHERE id = :id", nativeQuery = true)
    void confirmTechnicalVisit(@Param("id") Integer id);

    @Query("SELECT s FROM ServiceEntity s WHERE s.client.id = :userId OR s.company.id = :userId")
    List<ServiceEntity> findByUserId(@Param("userId") Integer userId);
    long countByCompanyId(Integer companyId);

    long countByCompanyIdAndStatusNotIn(Integer companyId, List<ServiceStatus> excludedStatuses);

    @Query("SELECT AVG(e.stars) FROM EvaluationEntity e WHERE e.service.company.id = :companyId")
    Double findAverageRatingByCompanyId(@Param("companyId") Integer companyId);

    List<ServiceEntity> findByCompanyIdAndStatus(Integer companyId, ServiceStatus status);

    List<ServiceEntity> findByCompanyIdAndStatusIn(Integer companyId, List<ServiceStatus> statuses);

    @Query("SELECT s FROM ServiceEntity s WHERE s.company.id = :companyId AND s.status IN :statuses AND FUNCTION('DATE', s.requestDate) = CURRENT_DATE")
    List<ServiceEntity> findByCompanyIdAndStatusInAndDate(@Param("companyId") Integer companyId, @Param("statuses") List<ServiceStatus> statuses);

}
