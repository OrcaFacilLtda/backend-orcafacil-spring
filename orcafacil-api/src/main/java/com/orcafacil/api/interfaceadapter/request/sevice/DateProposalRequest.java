package com.orcafacil.api.interfaceadapter.request.sevice;

import com.orcafacil.api.domain.datenegotiation.Proposer;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.util.Date;

public record DateProposalRequest(
        @NotNull(message = "A data não pode ser nula.") @Future(message = "A data deve ser no futuro.") Date date,
        @NotNull(message = "O proponente não pode ser nulo.") Proposer proposerRole,
        @NotNull(message = "O tipo não pode ser nulo.") String type) {}