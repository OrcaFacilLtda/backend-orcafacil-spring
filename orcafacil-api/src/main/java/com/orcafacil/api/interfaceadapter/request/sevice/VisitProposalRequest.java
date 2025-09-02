package com.orcafacil.api.interfaceadapter.request.sevice;

import com.orcafacil.api.domain.visitnegotiation.Propeser;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import java.util.Date;

public record VisitProposalRequest(
        @NotNull(message = "A data não pode ser nula.")
        @FutureOrPresent(message = "A data de início deve ser hoje ou no futuro.")
        Date date,

        @NotNull(message = "O proponente não pode ser nulo.")
        Propeser proposerRole
) {}