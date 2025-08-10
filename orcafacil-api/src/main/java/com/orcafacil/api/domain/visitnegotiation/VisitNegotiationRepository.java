package com.orcafacil.api.domain.visitnegotiation;

import java.util.List;
import java.util.Optional;

public interface VisitNegotiationRepository {
    void markAccepted(Integer id);
    Optional<VisitNegotiation> findAcceptedByBothSides(Integer serviceId);
    List<VisitNegotiation> findByServiceId(Integer serviceId);
}
