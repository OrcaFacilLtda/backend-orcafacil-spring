package com.orcafacil.api.infrastructure.persistence.jpa.datenegotiation;

import com.orcafacil.api.infrastructure.persistence.entity.datenegotiation.DateNegotiationEntity;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SpringDateNegotiationRepository extends JpaRepository<DateNegotiationEntity, Integer> {

    @Modifying
    @Query("UPDATE DateNegotiationEntity d SET d.accepted = true WHERE d.id = :id")
    void markAccepted(@Param("id") Integer id);

    @Query(value = """
        SELECT * FROM date_negotiation
        WHERE service_id = :serviceId AND accepted = true
        GROUP BY start_date, end_date
        HAVING COUNT(DISTINCT proposer) = 2
        LIMIT 1
    """, nativeQuery = true)
    Optional<DateNegotiationEntity> findAcceptedByBothSides(@Param("serviceId") Integer serviceId);

    List<DateNegotiationEntity> findByServiceId(Integer serviceId);
}
