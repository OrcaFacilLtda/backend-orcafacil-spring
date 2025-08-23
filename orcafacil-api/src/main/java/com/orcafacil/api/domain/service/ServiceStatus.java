package com.orcafacil.api.domain.service;

public enum ServiceStatus {
    CREATED,                        // Solicitação criada
    PENDING_VISIT,                   // Aguardando confirmação de visita técnica
    VISIT_CONFIRMED,                 // Visita técnica confirmada por ambos
    WAITING_DATE_CONFIRMATION,       // Aguardando confirmação das datas da obra
    DATES_CONFIRMED,                 // Datas da obra confirmadas por ambos
    WAITING_MATERIAL_CONFIRMATION,   // Aguardando confirmação da lista de materiais pelo cliente
    MATERIALS_CONFIRMED,             // Lista de materiais confirmada pelo cliente
    WAITING_NEW_MATERIAL_LIST,       // Cliente solicitou nova lista de materiais
    FINISHED                         // Serviço finalizado e avaliado
}
