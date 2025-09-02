package com.orcafacil.api.interfaceadapter.request.sevice;

import com.orcafacil.api.domain.datenegotiation.Proposer;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import java.util.Date;

public record DateProposalRequest(
        @NotNull(message = "A data não pode ser nula.") @FutureOrPresent(message = "A data de início deve ser hoje ou no futuro.")
        Date date,
        @NotNull(message = "O proponente não pode ser nulo.") Proposer proposerRole,
        @NotNull(message = "O tipo não pode ser nulo.") String type) {}