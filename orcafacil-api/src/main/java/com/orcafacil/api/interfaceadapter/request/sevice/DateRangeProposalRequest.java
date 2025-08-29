package com.orcafacil.api.interfaceadapter.request.sevice;

import com.orcafacil.api.domain.datenegotiation.Proposer;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import java.util.Date;

public record DateRangeProposalRequest(
        @NotNull(message = "A data de início não pode ser nula.")
        @FutureOrPresent(message = "A data de início deve ser hoje ou no futuro.")
        Date startDate,

        @NotNull(message = "A data de término não pode ser nula.")
        @FutureOrPresent(message = "A data de término deve ser hoje ou no futuro.")
        Date endDate,

        @NotNull(message = "O proponente não pode ser nulo.")
        Proposer proposerRole
) {}