package com.orcafacil.api.infrastructure.persistence.mapper.service;

import com.orcafacil.api.domain.service.Service;
import com.orcafacil.api.domain.service.ServiceStatus;
import com.orcafacil.api.infrastructure.persistence.entity.service.ServiceEntity;
import org.springframework.stereotype.Component;

@Component
public class ServiceMapper {

    public ServiceEntity toEntity(Service service) {
        ServiceEntity entity = new ServiceEntity();
        entity.setId(service.getId());
        entity.setClientId(service.getUser().getId());
        entity.setCompanyId(service.getCompany().getId());
        entity.setDescription(service.getDescription());
        entity.setStatus(service.getServiceStatus());
        entity.setRequestDate(service.getRequestDate());
        entity.setTechnicalVisitDate(service.getTechnicalVisitDate());
        entity.setClientVisitConfirmed(service.getClientVisitConfirmed());
        entity.setProviderVisitConfirmed(service.getProviderVisitConfirmed());
        entity.setClientDatesConfirmed(service.getClientDatesConfirmed());
        entity.setProviderDatesConfirmed(service.getProviderDatesConfirmed());
        entity.setClientMaterialsConfirmed(service.getClientMaterialsConfirmed());
        entity.setProviderMaterialsConfirmed(service.getProviderMaterialsConfirmed());
        entity.setNegotiatedStartDate(service.getNegotiatedStartDate());
        entity.setNegotiatedEndDate(service.getNegotiatedEndDate());
        entity.setLaborCost(service.getLaborCost());
        entity.setBudgetFinalized(service.getBudgetFinalized());
        return entity;
    }

    public Service toDomain(ServiceEntity entity) {
        return new Service(
                entity.getId(),
                null, // usuário será resolvido em outra camada
                null, // empresa idem
                entity.getDescription(),
                entity.getStatus() != null ? entity.getStatus() : ServiceStatus.CREATED,
                entity.getRequestDate(),
                entity.getTechnicalVisitDate(),
                entity.getClientVisitConfirmed(),
                entity.getProviderVisitConfirmed(),
                entity.getClientDatesConfirmed(),
                entity.getProviderDatesConfirmed(),
                entity.getClientMaterialsConfirmed(),
                entity.getProviderMaterialsConfirmed(),
                entity.getNegotiatedStartDate(),
                entity.getNegotiatedEndDate(),
                entity.getLaborCost(),
                entity.getBudgetFinalized()
        );
    }
}
