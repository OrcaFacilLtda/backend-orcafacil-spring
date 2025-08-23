package com.orcafacil.api.infrastructure.persistence.mapper.service;

import com.orcafacil.api.domain.service.Service;
import com.orcafacil.api.infrastructure.persistence.entity.service.ServiceEntity;
import com.orcafacil.api.infrastructure.persistence.mapper.company.CompanyMapper;
import com.orcafacil.api.infrastructure.persistence.mapper.user.UserMapper;
import org.springframework.stereotype.Component;

@Component
public class ServiceMapper {

    public ServiceEntity toEntity(Service service) {
        if (service == null) {
            return null;
        }

        ServiceEntity entity = new ServiceEntity();
        entity.setId(service.getId());
        if (service.getUser() != null) {
            entity.setClient(UserMapper.toEntity(service.getUser()));
        }
        if (service.getCompany() != null) {
            entity.setCompany(CompanyMapper.toEntity(service.getCompany()));
        }

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
        if (entity == null) {
            return null;
        }

        // A lógica de "hidratação" que implementamos no JpaServiceRepositoryImpl
        // irá preencher os objetos User e Company posteriormente.
        return new Service(
                entity.getId(),
                null, // Será preenchido na camada de repositório
                null, // Será preenchido na camada de repositório
                entity.getDescription(),
                entity.getStatus(),
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