package com.orcafacil.api.infrastructure.persistence.jpa.visitnegotiation;

import com.orcafacil.api.infrastructure.persistence.entity.visitnegotiation.VisitNegotiationEntity;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SpringVisitNegotiationRepository extends JpaRepository<VisitNegotiationEntity, Integer> {

    @Modifying
    @Query("UPDATE VisitNegotiationEntity v SET v.accepted = true WHERE v.id = :id")
    void markAccepted(@Param("id") Integer id);

    @Query(value = """
        SELECT * FROM visit_negotiation
        WHERE service_id = :serviceId AND accepted = true
        GROUP BY visit_date
        HAVING COUNT(DISTINCT proposer) = 2
        LIMIT 1
    """, nativeQuery = true)
    Optional<VisitNegotiationEntity> findAcceptedByBothSides(@Param("serviceId") Integer serviceId);

    List<VisitNegotiationEntity> findByServiceId(Integer serviceId);
}
