package com.orcafacil.api.infrastructure.persistence.mapper.service;

import com.orcafacil.api.domain.service.Service;
import com.orcafacil.api.infrastructure.persistence.entity.service.ServiceEntity;
import com.orcafacil.api.infrastructure.persistence.mapper.user.UserMapper;
import com.orcafacil.api.infrastructure.persistence.mapper.company.CompanyMapper;

public class ServiceMapper {

    public static Service toDomain(ServiceEntity entity) {
        if (entity == null) return null;

        return new Service(
                entity.getId(),
                UserMapper.toDomain(entity.getClient()),
                CompanyMapper.toDomain(entity.getCompany()),
                entity.getDescription(),
                entity.getServiceStatus(),
                entity.getRequestDate(),
                entity.getTechnicalVisitDate(),
                entity.getVisitConfirmed(),
                entity.getNegotiatedStartDate(),
                entity.getNegotiatedEndDate(),
                entity.getLaborCost(),
                entity.getBudgetFinalized()
        );
    }

    public static ServiceEntity toEntity(Service domain) {
        if (domain == null) return null;

        return new ServiceEntity(
                domain.getId(),
                UserMapper.toEntity(domain.getUser()),
                CompanyMapper.toEntity(domain.getCompany()),
                domain.getDescription(),
                domain.getServiceStatus(),
                domain.getRequestDate(),
                domain.getTechnicalVisitDate(),
                domain.getVisitConfirmed(),
                domain.getNegotiatedStartDate(),
                domain.getNegotiatedEndDate(),
                domain.getLaborCost(),
                domain.getBudgetFinalized()
        );
    }
}
