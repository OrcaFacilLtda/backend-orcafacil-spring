package com.orcafacil.api.infrastructure.persistence.mapper.company;

import com.orcafacil.api.domain.company.Company;
import com.orcafacil.api.infrastructure.persistence.entity.company.CompanyEntity;
import com.orcafacil.api.infrastructure.persistence.mapper.address.AddressMapper;

public class CompanyMapper {

    public static CompanyEntity toEntity(Company company) {
        return new CompanyEntity(
                company.getId(),
                company.getLegalName(),
                company.getCnpj(),
                AddressMapper.toEntity(company.getAddress()),
                company.getCreatedAt()
        );
    }

    public static Company toDomain(CompanyEntity entity) {
        if (entity == null) return null;


        return new Company(
                entity.getId(),
                entity.getLegalName(),
                entity.getCnpj(),
                AddressMapper.toDomain(entity.getAddress()),
                entity.getCreatedAt()
        );
    }
}
