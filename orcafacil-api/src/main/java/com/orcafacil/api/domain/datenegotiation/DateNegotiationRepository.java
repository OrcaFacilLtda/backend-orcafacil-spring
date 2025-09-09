package com.orcafacil.api.domain.datenegotiation;

import java.util.List;
import java.util.Optional;

public interface DateNegotiationRepository {
    DateNegotiation save(DateNegotiation dateNegotiation);
    void markAccepted(Integer id);
    Optional<DateNegotiation> findAcceptedByBothSides(Integer serviceId);
    List<DateNegotiation> findByServiceId(Integer serviceId);
}