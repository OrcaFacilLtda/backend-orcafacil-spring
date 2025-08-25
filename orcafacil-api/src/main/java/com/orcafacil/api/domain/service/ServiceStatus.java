package com.orcafacil.api.domain.service;

public enum ServiceStatus {
    REQUEST_SENT,               // Cliente enviou a solicitação
    REJECTED,                   // Prestador recusou a solicitação
    NEGOTIATING_VISIT,          // Em negociação da visita (aguardando confirmação de ambos)
    VISIT_CONFIRMED,            // Visita confirmada por ambos
    NEGOTIATING_DATES,          // Em negociação das datas (aguardando confirmação de ambos)
    DATES_CONFIRMED,            // Datas da obra confirmadas por ambos
    BUDGET_IN_NEGOTIATION,      // Orçamento enviado, aguardando aceite ou revisão do cliente
    BUDGET_REVISION_REQUESTED,  // Cliente pediu revisão do orçamento
    IN_PROGRESS,                // Orçamento aprovado, serviço em execução
    COMPLETED                   // Serviço concluído e avaliado
}